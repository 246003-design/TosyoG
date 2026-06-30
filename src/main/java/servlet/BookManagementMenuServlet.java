package servlet;

import java.io.IOException;

import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 蔵書管理メニュー画面を表示するためのサーブレット
 */
@WebServlet("/BookManagementMenuServlet")
public class BookManagementMenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BookManagementMenuServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        
        // 未ログインの場合はログイン画面へ
        if (loginUser == null) {
            response.sendRedirect("LoginServlet");
            return; 
        }

        int role = loginUser.getRole();
        String jspPath = ""; 
        
        if (role == 1) {
            // 司書の場合：司書用の蔵書管理メニューへ
            jspPath = "/WEB-INF/JSP/librarian/librarian_book_menu.jsp";
        } else if (role == 2) {
            // 管理者の場合：管理者用の蔵書管理メニューへ
            jspPath = "/WEB-INF/JSP/admin/admin_book_menu.jsp";
        } else {
            // 利用者など、権限がない場合はホームへ強制送還
            response.sendRedirect("HomeServlet");
            return; 
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
        dispatcher.forward(request, response);
    }
}