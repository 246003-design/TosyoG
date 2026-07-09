package servlet;

import java.io.IOException;

import dto.LendDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ReturnLogic;

@WebServlet("/ReturnServlet")
public class ReturnServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String RETURN_JSP_PATH = "/WEB-INF/JSP/librarian/librarian_return.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 直接アクセスされた場合は、返却画面へフォワードして戻す
        request.getRequestDispatcher(RETURN_JSP_PATH).forward(request, response);
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String bookIdStr = request.getParameter("bookId");

        if (bookIdStr == null || bookIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "IDが入力されていません。");
            request.getRequestDispatcher(RETURN_JSP_PATH).forward(request, response);
            return;
        }

        try {
            int bookId = Integer.parseInt(bookIdStr.trim());
            
            // 💡 ロジッククラスを呼び出すだけ！
            ReturnLogic logic = new ReturnLogic();
            LendDto returnDto = logic.executeReturn(bookId);

            // 成功した場合の処理
            request.setAttribute("returnInfo", returnDto);
            request.setAttribute("successMessage", "図書ID:" + bookId + " の返却処理が完了しました。");
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "無効な数字（ID）が入力されました。");
        } catch (Exception e) {
            // Logic側で発生したエラーメッセージ（「貸出中ではありません」等）をそのまま表示
            request.setAttribute("errorMessage", e.getMessage());
        }

        request.getRequestDispatcher(RETURN_JSP_PATH).forward(request, response);
    }
}