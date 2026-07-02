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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ReservationLogic;

// ★JSPの form action="ReserveServlet" に合わせています
@WebServlet("/ReserveServlet")
public class ReserveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 【図書詳細＆予約画面表示（GETリクエスト）】
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("loginUserId"); 

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/WEB-INF/JSP/common/login.jsp");
            return;
        }

        String bookIdStr = request.getParameter("bookId");
        if (bookIdStr == null || bookIdStr.isEmpty()) {
            return;
        }
        int bookId = Integer.parseInt(bookIdStr);

        try (Connection conn = DBManager.getConnection()) {
            BookDAO bookDAO = new BookDAO(conn);
            ReservationDAO reservationDAO = new ReservationDAO(conn);
            
            Optional<Book> bookOpt = bookDAO.findById(bookId);
            
            if (bookOpt.isPresent()) {
                Book book = bookOpt.get();
                
                // 1. 予約状況のチェック
                int bookInfoId = book.getBookInfoId();
                boolean isReserved = false;
                for (Reservation r : reservationDAO.findAll()) {
                    if (r.getUserId() == userId && r.getBookInfoId() == bookInfoId && (r.getStatus() == 1 || r.getStatus() == 2)) {
                        isReserved = true;
                        break;
                    }
                }
                
                // ★2. 【エンティティを変更しない代わりの裏技】
                // JSPが求める「book.isReservedByCurrentUser」という形をMapで偽装する
                java.util.Map<String, Object> bookMap = new java.util.HashMap<>();
                bookMap.put("id", book.getId());
                bookMap.put("title", book.getTitle());
                bookMap.put("author", book.getAuthor());
                bookMap.put("synopsis", book.getSynopsis());
                // ↓ここがポイント！JSPが求めている名前でMapのキーを作る
                bookMap.put("isReservedByCurrentUser", isReserved); 
                
                // 3. スコープには変わらず "book" という名前でMapをセットする
                request.setAttribute("book", bookMap);
                
            } else {
                request.setAttribute("errorMsg", "指定された図書情報が見つかりません。");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "データ取得中にエラーが発生しました。");
        }

        request.getRequestDispatcher("/WEB-INF/JSP/customer/customer_book_detail.jsp").forward(request, response);
    }

    /**
     * 【予約の登録・キャンセル（POSTリクエスト）】
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

        // ★サーブレット側でコネクションを管理し、送受信（コミット）を制御する
        try (Connection conn = DBManager.getConnection()) {
            
            // 自動コミットをオフにしてトランザクションを開始
            conn.setAutoCommit(false);
            
            ReservationDAO reservationDAO = new ReservationDAO(conn);
            BookDAO bookDAO = new BookDAO(conn);

            // ==========================================
            // 1. 予約処理
            // ==========================================
            if ("reserve".equals(action)) {
                
                // ① まずロジックで判定
                resultMsg = logic.checkRegisterReservation(conn, userId, bookId);
                
                // ② 判定OK（空文字）ならDBへInsert
                if (resultMsg.isEmpty()) {
                    Optional<Book> bookOpt = bookDAO.findById(bookId);
                    if (bookOpt.isPresent()) {
                        ReservationDto newResDto = new ReservationDto();
                        newResDto.setUserId(userId);
                        newResDto.setBookId(bookId);
                        newResDto.setBookInfoId(bookOpt.get().getBookInfoId());
                        newResDto.setStatus(1); // 1: 予約中

                        if (!reservationDAO.insert(newResDto)) {
                            resultMsg = "データベースへの予約登録に失敗しました。";
                        }
                    } else {
                        resultMsg = "図書情報が見つかりません。";
                    }
                }
                
            // ==========================================
            // 2. キャンセル処理
            // ==========================================
            } else if ("cancel".equals(action)) {
                
                // ① まずロジックで判定
                resultMsg = logic.checkCancelReservation(conn, userId, bookId);
                
                // ② 判定OK（空文字）ならDBへUpdate
                if (resultMsg.isEmpty()) {
                    Optional<Book> bookOpt = bookDAO.findById(bookId);
                    if (bookOpt.isPresent()) {
                        int bookInfoId = bookOpt.get().getBookInfoId();
                        boolean targetUpdated = false;
                        
                        // キャンセル対象の予約IDを探してUpdate
                        for (Reservation r : reservationDAO.findAll()) {
                            if (r.getUserId() == userId && r.getBookInfoId() == bookInfoId && (r.getStatus() == 1 || r.getStatus() == 2)) {
                                ReservationDto updateDto = new ReservationDto();
                                updateDto.setId(r.getId());
                                updateDto.setStatus(9); // 9: キャンセル
                                
                                if (reservationDAO.update(updateDto)) {
                                    targetUpdated = true;
                                    break;
                                }
                            }
                        }
                        if (!targetUpdated) {
                            resultMsg = "キャンセルのデータベース更新に失敗しました。";
                        }
                    } else {
                        resultMsg = "図書情報が見つかりません。";
                    }
                }
            } else {
                resultMsg = "不正なリクエストです。";
            }

            // ==========================================
            // 3. トランザクションの確定（コミット/ロールバック）
            // ==========================================
            if (resultMsg.isEmpty()) {
                conn.commit(); // エラーがなければDBに確定送信！
                String successText = "reserve".equals(action) ? "予約手続きが正常に完了しました。" : "予約のキャンセルが完了しました。";
                request.setAttribute("successMessage", successText);
            } else {
                conn.rollback(); // エラーがあれば処理を無かったことにする
                request.setAttribute("errorMsg", resultMsg);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "システムエラーが発生しました。");
        }

        // doPostの最後にdoGetを呼び出し、必要な図書データを再取得してからJSPを表示する
        doGet(request, response);
    }
}