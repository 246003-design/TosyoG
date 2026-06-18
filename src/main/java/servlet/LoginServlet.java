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

@WebServlet("/LoginServlet") 
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	// 画面を最初に開いたとき（GET）
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログイン画面を表示する
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/common/login.jsp");
		dispatcher.forward(request, response);
	}

	// ログインボタンが押されたとき（POST）
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	
		
		// 1. JSPから入力値を受け取る（name属性と一致させる）
		// ※StringのままだとDB検索しづらいので、int型に変換します
		String userIdStr = request.getParameter("userId");
		String password = request.getParameter("password");
		
		int userId = 0;
		try {
			userId = Integer.parseInt(userIdStr);
		} catch (NumberFormatException e) {
			// IDに数字以外が入力された場合の処理
			request.setAttribute("errorMessage", "IDは数字で入力してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/common/login.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// 2. Logicに渡して認証する
		// 💡ここは「戻り値として、DBから取得した完全なUser情報を受け取る」設計にするのが一般的です
		LoginLogic loginLogic = new LoginLogic();
		User loginUser = loginLogic.execute(userId, password);
		
		// 3. 認証結果による分岐
		if (loginUser != null) {
			// ⭕ ログイン成功！
			// セッションにユーザー情報を保存
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			
			// 権限（role）をチェックして、それぞれのホーム画面へリダイレクト
			int role = loginUser.getRole();
			if (role == 1) {
				response.sendRedirect("librarian_home.jsp"); // 司書のホームへ
			} else if (role == 2) {
				response.sendRedirect("admin_home.jsp");     // 管理者のホームへ
			} else {
				response.sendRedirect("customer_home.jsp");  // 利用者のホームへ
			}
			
		} else {
			// ❌ ログイン失敗...
			// エラーメッセージをセットしてログイン画面に戻す
			request.setAttribute("errorMessage", "IDまたはパスワードが誤っています");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/common/login.jsp");
			dispatcher.forward(request, response);
		}
	}
}