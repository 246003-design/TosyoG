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
            request.setAttribute("errorMsg", "図書IDが指定されていません。");
            request.getRequestDispatcher("/WEB-INF/JSP/common/error.jsp").forward(request, response);
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
                        // ★ここでDTOを活用してデータを受け渡す
                        ReservationDto dto = new ReservationDto();
                        dto.setUserId(userId);
                        dto.setBookId(bookId); // 物理本ID
                        dto.setBookInfoId(bookOpt.get().getBookInfoId()); // 作品マスタID
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
                                // ★ここでもDTOを活用して更新データを渡す
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
                // 💡 requestではなく、セッション(session)にメッセージを保存します（リダイレクトで消えないようにするため）
                session.setAttribute("successMessage", "reserve".equals(action) ? "予約手続きが完了しました。" : "予約をキャンセルしました。");
            } else {
                conn.rollback(); 
                session.setAttribute("errorMsg", resultMsg);
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "システムエラーが発生しました。");
        }
        
      
        
        // ⭕ 修正後：処理が終わったら、検索一覧サーブレットへリダイレクト（転送）する
        response.sendRedirect(request.getContextPath() + "/LibrarySearchServlet");
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
        
        // 💡 セッションから現在のログインユーザーIDを取得する
        HttpSession session = request.getSession();
        Integer loginUserId = (Integer) session.getAttribute("loginUserId");
        
        if (bookIdStr != null && !bookIdStr.isEmpty()) {
            try (Connection conn = DBManager.getConnection()) {
                BookDAO bookDAO = new BookDAO(conn);
                int bookId = Integer.parseInt(bookIdStr);
                
                // 図書データを取得
                Optional<Book> bookOpt = bookDAO.findById(bookId);
                if (bookOpt.isPresent()) {
                    request.setAttribute("book", bookOpt.get());
                } else {
                    request.setAttribute("errorMsg", "該当する図書データが見つかりません。");
                }

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