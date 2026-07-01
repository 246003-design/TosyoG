package servlet;

import java.io.IOException;
import java.sql.Connection;

import dao.DBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.LendLogic;

@WebServlet("/LoanProcessServlet")
public class LendServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * ⭕ 追加：最初に画面を表示するとき（GETアクセス）の処理
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 何も処理せず、そのまま貸出画面（JSP）を表示する
        request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_loan.jsp").forward(request, response);
    }

    /**
     * 確定ボタンが押されたとき（POSTアクセス）の処理
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String userIdStr = request.getParameter("userId");
        String bookIdStr = request.getParameter("bookId");
        
        Connection conn = DBManager.getConnection();
        try {
           
            
            int userId = Integer.parseInt(userIdStr);
            int bookId = Integer.parseInt(bookIdStr);
            
            LendLogic lendLogic = new LendLogic();
            String resultMessage = lendLogic.registerLend(conn, userId, bookId);
            
            if (resultMessage.contains("完了")) {
                request.setAttribute("successMessage", resultMessage);
            } else {
                request.setAttribute("errorMessage", resultMessage);
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "エラー：IDは半角数字で入力してください。");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "システムエラーが発生しました。");
        }
        
        // 処理が終わったら、また同じJSPを表示する
        request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_loan.jsp").forward(request, response);
    }
}