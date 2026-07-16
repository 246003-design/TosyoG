package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import dao.BookDAO;
import dao.DBManager;
import dto.BookListDto;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/BookListServlet")
public class BookListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookListServlet() {
		super();
	}

	/**
	 * 蔵書一覧画面の表示、および検索処理（GET）
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. セッションと権限の確認
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

		int role = loginUser.getRole();
		if (role != 1 && role != 2) {
			response.sendRedirect("HomeServlet");
			return;
		}

		// 2. 検索キーワードの取得
		String keyword = request.getParameter("keyword");
		if (keyword == null) {
			keyword = ""; 
		}

		// 3. データベースから蔵書リストを取得
		try (Connection conn = DBManager.getConnection()) {
			BookDAO bookDAO = new BookDAO(conn);
			
			// 💡 BookDAOに実装した検索メソッドを呼び出す
			List<BookListDto> bookList = bookDAO.searchBooksForManagement(keyword);
			
			// 取得したリストをリクエストスコープにセット
			request.setAttribute("bookList", bookList);
			
			// 検索窓に入力した文字を残すためのセット
			request.setAttribute("keyword", keyword); 
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "蔵書データの取得に失敗しました。");
		}

		// 4. 一覧画面へフォワード
		String jspPath = (role == 1) ? "/WEB-INF/JSP/librarian/librarian_book_list.jsp" : "/WEB-INF/JSP/admin/admin_book_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}