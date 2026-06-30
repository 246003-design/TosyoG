package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.LendLogic;

/**
 * Servlet implementation class Lend
 */
//JSPのformのaction属性（LendProcessServlet）と一致させます
@WebServlet("/LendProcessServlet")
public class LendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 1. JSPのフォームから値を取得
		String userIdStr = request.getParameter("userId");
		String bookIdStr = request.getParameter("bookId");

		Connection conn = null; 

		try {
			// conn = DBConnection.getConnection(); // プロジェクトの接続処理に合わせてください
			
			int userId = Integer.parseInt(userIdStr);
			int bookId = Integer.parseInt(bookIdStr);

			// 2. 貸出ロジック（LendLogic）の実行
			LendLogic lendLogic = new LendLogic();
			String message = lendLogic.registerLend(conn, userId, bookId);

			// 3. ロジックの結果に応じてJSPへ渡すメッセージを振り分け
			if (message.isEmpty()) {
				request.setAttribute("successMessage", "貸出手続きが完了しました。");
			} else {
				request.setAttribute("errorMessage", message);
			}

		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "利用者IDおよび図書IDは正しい数値で入力してください。");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "予期せぬシステムエラーが発生しました。");
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 4. 元のJSP画面へ戻る（JSPのファイル名が lend.jsp の場合の例）
		request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_loan.jsp").forward(request, response);
	}
}
