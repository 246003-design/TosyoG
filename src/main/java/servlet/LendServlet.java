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

// 貸出処理
@WebServlet("/LoanProcessServlet")
public class LendServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 文字化け対策
        request.setCharacterEncoding("UTF-8");
        
        // 1. JSPのフォーム（inputのname属性）から値を取得
        String userIdStr = request.getParameter("userId");
        String bookIdStr = request.getParameter("bookId");
        
        Connection conn = null; 
        try {
            // ここをあなたの環境に合わせて解除し、Connectionを取得してください！
             conn = DBManager.getConnection(); 
            
            int userId = Integer.parseInt(userIdStr);
            int bookId = Integer.parseInt(bookIdStr);
            
            // 2. ロジッククラスの実行
            LendLogic lendLogic = new LendLogic();
            String resultMessage = lendLogic.registerLend(conn, userId, bookId);
            
            // 3. ロジックから返ってきたメッセージをもとに、成功かエラーかを判定
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
        } finally {
            // 念のため、ここでConnectionを使った場合はクローズする処理を入れておくと安全です
             if (conn != null) { try { conn.close(); } catch(Exception e){} }
        }
        
        // 4. 【修正】先頭に「/」を追加して自画面にフォワード
        request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_loan.jsp").forward(request, response);
    }
}