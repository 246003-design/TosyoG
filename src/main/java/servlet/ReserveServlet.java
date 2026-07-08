package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import dao.BookDAO;
import dao.DBManager;
import dao.ReservationDAO;
import dto.ReservationDto;
import entity.Book;
import entity.Reservation;
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
        Integer userId = (Integer) session.getAttribute("loginUserId"); 

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/WEB-INF/JSP/common/login.jsp");
            return;
        }

        if (bookIdStr == null || bookIdStr.isEmpty()) {
            // 💡 修正：リダイレクトするので、エラーメッセージもセッション（一時保存）に入れる
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

<<<<<<< HEAD
         // ==========================================
            // コミット or ロールバック
=======
            // ==========================================
            // コミット or ロールバック（💡 重複を排除して1つに統合）
>>>>>>> branch 'master' of https://github.com/246003-design/TosyoG.git
            // ==========================================
            if (resultMsg.isEmpty()) {
                conn.commit(); 
<<<<<<< HEAD
                // 💡 requestではなく、セッション(session)にメッセージを保存します（リダイレクトで消えないようにするため）
=======
>>>>>>> branch 'master' of https://github.com/246003-design/TosyoG.git
                session.setAttribute("successMessage", "reserve".equals(action) ? "予約手続きが完了しました。" : "予約をキャンセルしました。");
            } else {
                conn.rollback(); 
                session.setAttribute("errorMsg", resultMsg);
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "システムエラーが発生しました。");
        }
        
<<<<<<< HEAD
      
        
        // ⭕ 修正後：処理が終わったら、検索一覧サーブレットへリダイレクト（転送）する
        response.sendRedirect(request.getContextPath() + "/LibrarySearchServlet");
=======
        // 処理が終わったら、URLを綺麗にするために自分自身（GET）へリダイレクトする
        response.sendRedirect(request.getContextPath() + "/ReserveServlet?bookId=" + bookId);
>>>>>>> branch 'master' of https://github.com/246003-design/TosyoG.git
    }
    
    /**
     * 【画面表示処理（GET）】
     */
    /**
     * 【画面表示処理（GET）】
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String bookIdStr = request.getParameter("bookId");
        
<<<<<<< HEAD
        // 💡 セッションから現在のログインユーザーIDを取得する
        HttpSession session = request.getSession();
        Integer loginUserId = (Integer) session.getAttribute("loginUserId");
=======
        HttpSession session = request.getSession();
        Integer loginUserId = (Integer) session.getAttribute("loginUserId");
        
        // 💡 修正：リダイレクト先（doPost）からセッション経由で届いたメッセージを、リクエストに詰め替えてJSPに渡す（フラッシュメッセージ）
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
>>>>>>> branch 'master' of https://github.com/246003-design/TosyoG.git
        
        if (bookIdStr != null && !bookIdStr.isEmpty()) {
            try (Connection conn = DBManager.getConnection()) {
                
                // --- ① 図書情報の取得 ---
                BookDAO bookDAO = new BookDAO(conn);
                int bookId = Integer.parseInt(bookIdStr);
<<<<<<< HEAD
                
                // 図書データを取得
=======
>>>>>>> branch 'master' of https://github.com/246003-design/TosyoG.git
                Optional<Book> bookOpt = bookDAO.findById(bookId);
                
                if (bookOpt.isPresent()) {
<<<<<<< HEAD
                    request.setAttribute("book", bookOpt.get());
=======
                    Book book = bookOpt.get();
                    
                    // 💡 もしBookDAOでJOINしてない場合、作成済みのBookInfoDAOを使うならココに以下の2行を入れる
                    // dao.BookInfoDAO bookInfoDAO = new dao.BookInfoDAO(conn);
                    // book.setBookInfo(bookInfoDAO.findById(book.getBookInfoId()));
                    
                    request.setAttribute("book", book);
                    
                    // --- ② 予約情報の取得と判定 ---
                    ReservationDAO reservationDAO = new ReservationDAO(conn);
                    boolean isReservedByCurrentUser = false;

                    for (Reservation r : reservationDAO.findAll()) {
                        if (r.getUserId() == loginUserId && r.getBookId() == bookId && (r.getStatus() == 1 || r.getStatus() == 2)) {
                            isReservedByCurrentUser = true; 
                            break;
                        }
                    }

                    // 💡 修正：詳細画面JSPの記述（book.reservedByCurrentUser）に合わせて、Bookエンティティにフラグを仕込む
                    book.setReservedByCurrentUser(isReservedByCurrentUser);
                    
                    // 画面のボタン切り替え等に使う単体オブジェクト（JSPのc:choose用）
                    request.setAttribute("isReserved", isReservedByCurrentUser);
>>>>>>> branch 'master' of https://github.com/246003-design/TosyoG.git
                } else {
                    request.setAttribute("errorMsg", "該当する図書データが見つかりません。");
                }

<<<<<<< HEAD
                // 💡 【ここを追加】詳細画面でも予約状況を判定するために、マップを作成してJSPに渡す
                ReservationDAO reservationDAO = new ReservationDAO(conn);
                List<Reservation> allReservations = reservationDAO.findAll();

                java.util.Map<Integer, Integer> bookReserverMap = new java.util.HashMap<>();
                for (Reservation r : allReservations) {
                    if (r.getStatus() == 1 || r.getStatus() == 2) { // 1:予約中、2:受取待ち
                        bookReserverMap.put(r.getBookId(), r.getUserId());
                    }
                }

                // JSPへデータを送る
                request.setAttribute("bookReserverMap", bookReserverMap);
                request.setAttribute("loginUserId", loginUserId);

=======
>>>>>>> branch 'master' of https://github.com/246003-design/TosyoG.git
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMsg", "図書情報の取得中にエラーが発生しました。");
            }
        }

<<<<<<< HEAD
        // JSPへフォワード
=======
>>>>>>> branch 'master' of https://github.com/246003-design/TosyoG.git
        String viewPath = "/WEB-INF/JSP/customer/customer_book_detail.jsp";
        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}