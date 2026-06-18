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
import model.LoginLogic;

@WebServlet("/Login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	//id,パスワード,区分を参照する
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");	
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		User user = new User();
		
		LoginLogic loginLogic = new LoginLogic();
		boolean isLogin = loginLogic.execute(user, role);
		
		if(isLogin) {
			HttpSession session = request.getSession();
			session.setAttribute("loginUser",user);
			}
		RequestDispatcher dispacher = request.getRequestDispatcher("WEB-INF/jsp/common/login.jsp");
		dispacher.forward(request,response);
	}
}
