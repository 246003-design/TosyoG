package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 現在のセッションを取得（無ければnullが返るように false を指定）
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			// 🌟 ここが一番重要！セッション（ログインの記憶）を完全に破棄する
			session.invalidate();
		}
		
		// ログアウト後はログイン画面へリダイレクト
		// （もしログイン画面を表示するServlet名が違えば、そこに合わせてください）
		response.sendRedirect("LoginServlet"); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}