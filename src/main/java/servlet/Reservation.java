package servlet;

import java.io.IOException;
import java.sql.Connection;

import dao.DBManager;
import dao.ReservationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ReservationLogic;

// ★修正1: JSPの form action="ReserveServlet" に合わせる
@WebServlet("/ReserveServlet")
public class Reservation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 【図書詳細＆予約画面表示（GETリクエスト）】
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("loginUserId"); 

        // ※本来はログイン画面へのURLを指定します（JSP側を変更しないため元の記述を維持）
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/WEB-INF/JSP/common/login.jsp");
            return;
        }

        String bookIdStr = request.getParameter("bookId");
        if (bookIdStr == null || bookIdStr.isEmpty()) {
            // bookIdがない場合の防衛策（任意のエラーハンドリング）
            return;
        }
        int bookId = Integer.parseInt(bookIdStr);

        try (Connection conn = DBManager.getConnection()) {
            
            ReservationDAO dao = new ReservationDAO(conn);
            entity.Reservation reservationDto = dao.findById(bookId);
            
            // ★修正3: JSPの EL式 ${book.title} などに合わせてスコープにセット
            request.setAttribute("book", reservationDto);
            
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

        try (Connection conn = DBManager.getConnection()) {
            
            // ★修正2: JSPのボタンの value="reserve" に合わせる
            if ("reserve".equals(action)) {
                resultMsg = logic.registerReservation(conn, userId, bookId);
            } else if ("cancel".equals(action)) {
                resultMsg = logic.cancelReservation(conn, userId, bookId);
            } else {
                resultMsg = "不正なリクエストです。";
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultMsg = "システムエラーが発生しました。しばらく経ってから再度お試しください。";
        }

        // ★修正4: JSPを表示するために、必要な図書データを再取得してからフォワードする
        if (resultMsg == null || resultMsg.isEmpty()) {
            // JSPの ${successMessage} に連動させる
            request.setAttribute("successMessage", "予約手続きが正常に完了しました。");
        } else {
            request.setAttribute("errorMsg", resultMsg);
        }

        // doGetの「データ取得ロジック」を通すために、内部でdoGetを呼び出して画面を表示する
        doGet(request, response);
    }
}