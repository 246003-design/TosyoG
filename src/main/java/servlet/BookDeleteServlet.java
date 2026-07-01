package servlet;

import java.io.IOException;
import java.sql.Connection;

import dao.BookDAO;
import dao.DBManager;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 蔵書の削除（論理削除）を行うサーブレット
 */
@WebServlet("/BookDeleteServlet")
public class BookDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookDeleteServlet() {
		super();
	}

	/**
	 * 削除ボタンが押された時の処理（POST）
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. セッションと権限の確認（管理者または司書のみ許可）
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			response.sendRedirect("LoginServlet");
			return; 
		}

		int role = loginUser.getRole();
		if (role != 1 && role != 2) {
			// 権限がない場合はホームへ
			response.sendRedirect("HomeServlet");
			return;
		}

		// 2. 画面から送信された削除対象の本のIDを取得
		String bookIdStr = request.getParameter("bookId");
		
		if (bookIdStr != null && !bookIdStr.isEmpty()) {
			try {
				int bookId = Integer.parseInt(bookIdStr);
				
				// 3. データベースに接続し、削除（論理削除）を実行
				try (Connection conn = DBManager.getConnection()) {
					BookDAO bookDAO = new BookDAO(conn);
					boolean success = bookDAO.delete(bookId);
					
					if (!success) {
						System.err.println("図書の削除に失敗しました。ID: " + bookId);
					}
				}
			} catch (NumberFormatException e) {
				System.err.println("不正な図書IDが送信されました: " + bookIdStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 4. 削除処理が終わったら、もう一度「蔵書一覧画面」を表示するためにリダイレクト
		response.sendRedirect("BookListServlet");
	}
}