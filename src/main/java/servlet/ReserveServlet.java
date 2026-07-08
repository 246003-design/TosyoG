package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

import dao.BookDAO;
import dao.DBManager;
import dao.ReservationDAO;
import dto.ReservationDto;
import entity.Book;
import entity.Reservation;
import entity.User; // 💡 追加：Userエンティティをインポート
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
                    Optional<Book> bookOpt = bookDAO.findById(bookId);
                    if (bookOpt.isPresent()) {
                        ReservationDto dto = new ReservationDto();
                        dto.setUserId(userId);
                        dto.setBookId(bookId); 
                        dto.setBookInfoId(bookOpt.get().getBookInfoId()); 
                        dto.setStatus(1); // 1: 予約中

                        if (!reservationDAO.insert(dto)) {
                            resultMsg = "予約データの登録に失敗しました。";
                        }
                    }
                }
                
            // ==========================================
            // キャンセル処理
            // ==========================================
            } else if ("cancel".equals(action)) {
                resultMsg = logic.validateCancellation(conn, userId, bookId);
                
                if (resultMsg.isEmpty()) {
                    Optional<Book> bookOpt = bookDAO.findById(bookId);
                    if (bookOpt.isPresent()) {
                        int bookInfoId = bookOpt.get().getBookInfoId();
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
        
        String successMessage = (String) session.getAttribute("successMessage");
        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage"); // 使い終わったら消す
        }
        String errorMsg = (String) session.getAttribute("errorMsg");
        if (errorMsg != null) {
            request.setAttribute("errorMsg", errorMsg);
            session.removeAttribute("errorMsg"); // 使い終わったら消す
        }
        
        if (bookIdStr != null && !bookIdStr.isEmpty()) {
            try (Connection conn = DBManager.getConnection()) {
                
                // --- ① 図書情報の取得 ---
                BookDAO bookDAO = new BookDAO(conn);
                int bookId = Integer.parseInt(bookIdStr);
                Optional<Book> bookOpt = bookDAO.findById(bookId);
                
                if (bookOpt.isPresent()) {
                    Book book = bookOpt.get();
                    
                    request.setAttribute("book", book);
                    
                    // --- ② 予約情報の取得と判定 ---
                    ReservationDAO reservationDAO = new ReservationDAO(conn);
                    boolean isReservedByCurrentUser = false;

                    // 💡 修正箇所5：ログイン済みの場合のみ自分の予約履歴をチェック
                    if (loginUserId != null) {
                        for (Reservation r : reservationDAO.findAll()) {
                            if (r.getUserId() == loginUserId && r.getBookId() == bookId && (r.getStatus() == 1 || r.getStatus() == 2)) {
                                isReservedByCurrentUser = true; 
                                break;
                            }
                        }
                    }

                    // 詳細画面JSPの記述に合わせて、Bookエンティティにフラグを仕込む
                    book.setReservedByCurrentUser(isReservedByCurrentUser);
                    
                    // 画面のボタン切り替え等に使う単体オブジェクト
                    request.setAttribute("isReserved", isReservedByCurrentUser);
                } else {
                    request.setAttribute("errorMsg", "該当する図書データが見つかりません。");
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMsg", "図書情報の取得中にエラーが発生しました。");
            }
        }

        String viewPath = "/WEB-INF/JSP/customer/customer_book_detail.jsp";
        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}