package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dao.DBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.LendLogic;

@WebServlet("/LendServlet")
public class LendServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_loan.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String userIdStr = request.getParameter("userId");
        String bookIdStr = request.getParameter("bookId");
        
        // --- 前後の見えない空白を自動で削除する ---
        if (userIdStr != null) {
            userIdStr = userIdStr.trim();
        }
        if (bookIdStr != null) {
            bookIdStr = bookIdStr.trim();
        }
        
        // --- 何も入力されていない場合はエラーにする ---
        if (userIdStr == null || userIdStr.isEmpty() || bookIdStr == null || bookIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "エラー：利用者IDと図書IDを入力してください。");
            request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_loan.jsp").forward(request, response);
            return;
        }
        
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            
            // ⭕ 追加：自動保存をオフにし、手動でコミット（確定）できるようにする
            conn.setAutoCommit(false);
            
            int userId = Integer.parseInt(userIdStr);
            int bookId = Integer.parseInt(bookIdStr);
            
            LendLogic lendLogic = new LendLogic();
            String resultMessage = lendLogic.registerLend(conn, userId, bookId);
            
            if (resultMessage.contains("完了")) {
                // ⭕ 追加：成功した場合はDBへの変更を「確定（保存）」する
                conn.commit();
                request.setAttribute("successMessage", resultMessage);
            } else {
                // ⭕ 追加：エラーメッセージが返ってきた場合は変更を「取り消し」する
                conn.rollback();
                request.setAttribute("errorMessage", resultMessage);
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "エラー：IDは半角数字で入力してください。<br>※受信した文字 ＝ 利用者ID:「" + userIdStr + "」, 図書ID:「" + bookIdStr + "」");
        } catch (Exception e) {
            e.printStackTrace();
            
            // ⭕ 追加：システムエラーが起きた場合もDBへの変更を取り消す
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            request.setAttribute("errorMessage", "システムエラーが発生しました。");
            
        } finally {
            // ⭕ 追加：最後に必ずDBとの接続を閉じる（リソースリーク防止）
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_loan.jsp").forward(request, response);
    }
}