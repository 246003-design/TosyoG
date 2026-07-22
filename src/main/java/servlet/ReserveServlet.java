package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import dao.BookDAO;
import dao.BookInfoDAO;
import dao.DBManager;
import dao.LendDAO;
import dao.ReservationDAO;
import dao.UserDAO;
import entity.Book;
import entity.BookInfo;
import entity.Lend;
import entity.Reservation;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ReservationLogic;

@WebServlet("/ReserveServlet")
public class ReserveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
//予約
    /**
     * 【予約・キャンセル処理（POST）】
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action"); 
        String bookIdStr = request.getParameter("bookId");
        
        HttpSession session = request.getSession();
        
        // ログインチェック
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        int userId = loginUser.getId();

        if (bookIdStr == null || bookIdStr.isEmpty()) {
            session.setAttribute("errorMsg", "図書IDが指定されていません。");
            response.sendRedirect(request.getContextPath() + "/LibrarySearchServlet");
            return;
        }

        int bookId = Integer.parseInt(bookIdStr);
        ReservationLogic logic = new ReservationLogic();

        try {
            // ==========================================
            // 1. 予約・キャンセル処理（DBの更新 ＋ syncBorrowCount が実行される）
            // ==========================================
            if ("reserve".equals(action)) {
                logic.executeReservation(userId, bookId);
                session.setAttribute("successMessage", "予約手続きが完了しました。");

            } else if ("cancel".equals(action)) {
                logic.executeCancellation(userId, bookId);
                session.setAttribute("successMessage", "予約をキャンセルしました。");
            }

            // ==========================================
            // 2. 🌟 DBから最新のUser情報を再取得してセッションを更新
            // ==========================================
            try (Connection conn = DBManager.getConnection()) {
                UserDAO userDAO = new UserDAO(conn);
                User updatedUser = userDAO.findById(userId);
                if (updatedUser != null) {
                    session.setAttribute("loginUser", updatedUser); // 最新のユーザー情報でセッションを上書き
                }
            }

        } catch (Exception e) {
            session.setAttribute("errorMsg", e.getMessage());
        }
        
        // 詳細画面（GET）へリダイレクト
        response.sendRedirect(request.getContextPath() + "/ReserveServlet?bookId=" + bookId);
    }
    
    /**
     * 【画面表示処理（GET）】
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String bookIdStr = request.getParameter("bookId");
        
        HttpSession session = request.getSession();
        
        // GET（詳細画面表示）でも「loginUser」オブジェクトからIDを取得する
        User loginUser = (User) session.getAttribute("loginUser");
        Integer loginUserId = (loginUser != null) ? loginUser.getId() : null;
        
        // リダイレクト先から届いたメッセージをリクエストに詰め替える
        String successMessage = (String) session.getAttribute("successMessage");
        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage"); 
        }
        
        String errorMsg = (String) session.getAttribute("errorMsg");
        if (errorMsg != null) {
            request.setAttribute("errorMsg", errorMsg);
            session.removeAttribute("errorMsg"); // 💡 修正: "removeAttribute" だったキー名のタイポを修正
        }

        if (bookIdStr != null && !bookIdStr.isEmpty()) {
            try (Connection conn = DBManager.getConnection()) {
                
                BookDAO bookDAO = new BookDAO(conn);
                int bookId = Integer.parseInt(bookIdStr);
                
                Optional<Book> bookOpt = bookDAO.findById(bookId);
                
                if (bookOpt.isPresent()) {
                    Book book = bookOpt.get();
                    
                    // BookInfoDAOを使って詳細を補填
                    BookInfoDAO bookInfoDAO = new BookInfoDAO(conn);
                    BookInfo info = bookInfoDAO.findDetailById(book.getBookInfoId());
                    book.setBookInfo(info);
                    
                    request.setAttribute("book", book);
                    
                    // --- 予約情報の取得と判定 ---
                    ReservationDAO reservationDAO = new ReservationDAO(conn);
                    List<Reservation> allReservations = reservationDAO.findAll();
                    
                    boolean isReservedByCurrentUser = false;
                    Map<Integer, Integer> bookReserverMap = new HashMap<>();

                    if (allReservations != null) {
                        for (Reservation r : allReservations) {
                            if (r.getStatus() == 1 || r.getStatus() == 2) { 
                                bookReserverMap.put(r.getBookId(), r.getUserId());
                                
                                // 現在のログインユーザーがこの本を予約しているか判定
                                if (loginUserId != null && r.getUserId() == (int)loginUserId && r.getBookId() == bookId) {
                                    isReservedByCurrentUser = true; 
                                }
                            }
                        }
                    }

                    // --- 貸出情報の取得と判定 ---
                    LendDAO lendDAO = new LendDAO(conn);
                    Lend currentLend = lendDAO.findCurrentLendByBookId(bookId);
                    boolean isLentOut = (currentLend != null);
                    
                    request.setAttribute("isLentOut", isLentOut);
                    if (isLentOut) {
                        request.setAttribute("currentLend", currentLend);
                    }

                    // エンティティにフラグを仕込む (重複行を1行削除)
                    book.setReservedByCurrentUser(isReservedByCurrentUser);
                    
                    // JSPへデータを送る
                    request.setAttribute("isReserved", isReservedByCurrentUser);
                    request.setAttribute("bookReserverMap", bookReserverMap);

                } else {
                    request.setAttribute("errorMsg", "該当する図書データが見つかりません。");
                }

                // JSP側の互換性のために、loginUserIdもリクエストスコープに載せておく
                request.setAttribute("loginUserId", loginUserId);

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMsg", "図書情報の取得中にエラーが発生しました。");
            }
        }

        // JSPへフォワード
        String viewPath = "/WEB-INF/JSP/customer/customer_book_detail.jsp";
        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}
