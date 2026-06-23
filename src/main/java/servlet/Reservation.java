package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DBManager; // ★ご提示いただいたDBManagerをインポート
import dao.ReservationDAO;
import entity.Reservation;
import model.ReservationLogic;

@WebServlet("/reservation")
public class ReservationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 【予約一覧の取得・画面表示（GETリクエスト）】
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("loginUserId"); 

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // ★ DBManagerを使用してコネクションを取得
        try (Connection conn = DBManager.getConnection()) {
            
            ReservationDAO dao = new ReservationDAO(conn);
            List<Reservation> reservationList = dao.findAll();
            
            request.setAttribute("reservationList", reservationList);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "データ取得中にエラーが発生しました。");
        }

        request.getRequestDispatcher("/WEB-INF/JSP/bookDetail.jsp").forward(request, response);
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

        // ★ DBManagerを使用してコネクションを取得
        try (Connection conn = DBManager.getConnection()) {
            
            if ("register".equals(action)) {
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

        if (resultMsg.isEmpty()) {
            request.setAttribute("successMsg", "予約手続きが正常に完了しました。");
            request.getRequestDispatcher("/WEB-INF/JSP/bookDetail.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMsg", resultMsg);
            request.getRequestDispatcher("/WEB-INF/JSP/bookDetail.jsp").forward(request, response);
        }
    }
}