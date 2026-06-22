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

	// 画面を最初に開いたとき（GET）
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 先頭の「/」と大文字の「JSP」が必須です！
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/common/login.jsp");
		dispatcher.forward(request, response);
	}

	// ログインボタンが押されたとき（POST）
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");	
		
		// 1. JSPの <input name="..."> と名前を合わせて受け取る
		// JSP側が name="userId" なら request.getParameter("userId") にする必要があります
		String idStr = request.getParameter("userId"); 
		if (idStr == null) {
			idStr = request.getParameter("id"); // 念のための予備
		}
		String password = request.getParameter("password");
		
		// 2. 文字列のIDを、データベースで使える「数字(int)」に変換する
		int id = 0;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			// IDに文字が入力された場合の弾き処理
			request.setAttribute("errorMessage", "IDは数字で入力してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/common/login.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// 3. LoginLogicに「数字のID」と「入力されたパスワード」を渡して照合する
		LoginLogic loginLogic = new LoginLogic();
		User loginUser = loginLogic.execute(id, password); 
		
		// 4. 認証結果による分岐
		if (loginUser != null) {
			// ⭕ ログイン成功！
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser); // 取得した本物のUser情報をセッションに入れる
			
			// 権限（role）をチェックして、それぞれのホーム画面へリダイレクト
			int role = loginUser.getRole();
			if (role == 1) {
				response.sendRedirect("librarian_home.jsp"); // 司書のホームへ
			} else if (role == 2) {
				response.sendRedirect("admin_home.jsp");     // 管理者のホームへ
			} else {
				response.sendRedirect("user_home.jsp");      // 利用者のホームへ
			}
			
		} else {
			// ❌ ログイン失敗...
			request.setAttribute("errorMessage", "IDまたはパスワードが誤っています");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/common/login.jsp");
			dispatcher.forward(request, response);
		}
	}
}