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
import dao.ReservationDAO;
import dto.ReservationDto;
import entity.Book;
import entity.BookInfo;
import entity.Reservation;
import entity.User; // 💡 追加：Userエンティティをインポート
import entity.User; // 🌟 Userエンティティをインポート
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
        
        // 💡 修正箇所1：LoginServletに合わせて "loginUser" で取得
        User loginUser = (User) session.getAttribute("loginUser"); 
        
        // 💡 修正箇所2：UserオブジェクトからIDを取り出す
        // ※もしエンティティのメソッド名が違う場合は getId() を getUserId() 等に変更してください
        int userId = loginUser.getId();
        // 🌟 ログインサーブレットに合わせて「loginUser」オブジェクトとして取得
        User loginUser = (User) session.getAttribute("loginUser");

        // オブジェクト自体が存在するか（ログインしているか）をチェック
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        // ログインが確認できたら、オブジェクトからユーザーIDを抽出（アンボクシング対策でint型へ）
        int userId = loginUser.getId();

        if (bookIdStr == null || bookIdStr.isEmpty()) {
            session.setAttribute("errorMsg", "図書IDが指定されていません。");
            response.sendRedirect(request.getContextPath() + "/LibrarySearchServlet");
            return;
        }

        int bookId = Integer.parseInt(bookIdStr);
        String resultMsg = "";
        ReservationLogic logic = new ReservationLogic();

        try (Connection conn = DBManager.getConnection()) {
            conn.setAutoCommit(false); // トランザクション開始
            
            ReservationDAO reservationDAO = new ReservationDAO(conn);
            BookDAO bookDAO = new BookDAO(conn);

            // ==========================================
            // 予約処理
            // ==========================================
            if ("reserve".equals(action)) {
                resultMsg = logic.validateReservation(conn, userId, bookId);
                
                if (resultMsg.isEmpty()) {
                    Book book = bookDAO.findById(bookId).get(); 
                    
                    ReservationDto dto = new ReservationDto();
                    dto.setUserId(userId);
                    dto.setBookId(bookId); 
                    dto.setBookInfoId(book.getBookInfoId()); 
                    dto.setStatus(1); // 1: 予約中

                    if (!reservationDAO.insert(dto)) {
                        resultMsg = "予約データの登録に失敗しました。";
                    }
                }
                
            // ==========================================
            // キャンセル処理
            // ==========================================
            } else if ("cancel".equals(action)) {
                resultMsg = logic.validateCancellation(conn, userId, bookId);
                
                if (resultMsg.isEmpty()) {
                    int bookInfoId = bookDAO.findById(bookId).get().getBookInfoId(); 
                    boolean isUpdated = false;
                    
                    for (Reservation r : reservationDAO.findAll()) {
                        if (r.getUserId() == userId && r.getBookInfoId() == bookInfoId && (r.getStatus() == 1 || r.getStatus() == 2)) {
                            ReservationDto dto = new ReservationDto();
                            dto.setId(r.getId());
                            dto.setStatus(9); // 9: キャンセル
                            
                            if (reservationDAO.update(dto)) {
                                isUpdated = true;
                                break;
                            }
                        }
                    }
                    if (!isUpdated) resultMsg = "キャンセルの更新に失敗しました。";
                }
            }

            // ==========================================
            // コミット or ロールバック
            // ==========================================

            if (resultMsg.isEmpty()) {
                conn.commit(); 
                session.setAttribute("successMessage", "reserve".equals(action) ? "予約手続きが完了しました。" : "予約をキャンセルしました。");
            } else {
                conn.rollback(); 
                session.setAttribute("errorMsg", resultMsg);
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "システムエラーが発生しました。");
        }
        
        // 💡 修正箇所3：bookIdの大文字小文字を合わせ、元の詳細画面へUターン（リダイレクト）
        response.sendRedirect("ReserveServlet?bookId=" + bookId);
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
        
        // 💡 修正箇所4：LoginServletに合わせて "loginUser" で取得
        User loginUser = (User) session.getAttribute("loginUser");
        Integer loginUserId = null;
        if (loginUser != null) {
            loginUserId = loginUser.getId(); // ※ getId() メソッド名は環境に合わせてください
        }
        
        // 🌟 GET（詳細画面表示）でも同様に「loginUser」オブジェクトからIDを取得する
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
            session.removeAttribute("removeAttribute"); 
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

                    // 💡 修正箇所5：ログイン済みの場合のみ自分の予約履歴をチェック
                    if (loginUserId != null) {
                        for (Reservation r : reservationDAO.findAll()) {
                            if (r.getUserId() == loginUserId && r.getBookId() == bookId && (r.getStatus() == 1 || r.getStatus() == 2)) {
                                isReservedByCurrentUser = true; 
                                break;
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

                    // 詳細画面JSPの記述に合わせて、Bookエンティティにフラグを仕込む
                    // エンティティにフラグを仕込む
                    book.setReservedByCurrentUser(isReservedByCurrentUser);
                    
                    // 画面のボタン切り替え等に使う単体オブジェクト
                    // JSPへデータを送る
                    request.setAttribute("isReserved", isReservedByCurrentUser);

                    request.setAttribute("bookReserverMap", bookReserverMap);
                } else {
                    request.setAttribute("errorMsg", "該当する図書データが見つかりません。");
                }

                // 🌟 JSP側の互換性のために、loginUserIdもリクエストスコープに載せておく
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