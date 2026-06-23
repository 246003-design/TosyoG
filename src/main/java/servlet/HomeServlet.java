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

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. セッションからログインユーザー情報を取得する
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		// 🔒 セキュリティ：ログインしていない（URL直叩きなど）場合はログイン画面へ強制送還
		if (loginUser == null) {
			response.sendRedirect("LoginServlet");
			return;
		}
		
		// 2. ユーザーの権限（role）に応じて、WEB-INF内のJSPへフォワード（内部転送）する
		int role = loginUser.getRole();
		String jspPath = "";
		
		if (role == 1) {
			// 司書の場合のJSPパス
			jspPath = "/WEB-INF/JSP/librarian/librarian_home.jsp"; 
		} else if (role == 2) {
			// 管理者の場合のJSPパス
			jspPath = "/WEB-INF/JSP/admin/admin_home.jsp"; 
		} else {
			// 利用者の場合のJSPパス（※フォルダ名が違う場合は適宜合わせてください）
			jspPath = "/WEB-INF/JSP/customer/customer_home.jsp"; 
		}
		
		// 3. 決定したJSPを表示する
		RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}