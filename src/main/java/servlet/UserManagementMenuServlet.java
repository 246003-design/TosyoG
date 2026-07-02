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

@WebServlet("/UserManagementMenuServlet")
public class UserManagementMenuServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public UserManagementMenuServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		if(loginUser == null) {
			response.sendRedirect("LoginServlet");
			return;
		}
		
		int role = loginUser.getRole();
		String jspPath="";
		
		if (role == 2) {
			jspPath = "/WEB-INF/JSP/admin/admin_user_menu.jsp";
		} else {
			response.sendRedirect("HomeServlet");
			return;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
		dispatcher.forward(request, response);
	}
}
