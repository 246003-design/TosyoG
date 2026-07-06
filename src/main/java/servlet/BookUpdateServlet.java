package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

import dao.BookDAO;
import dao.DBManager;
import entity.Book;
import entity.BookInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/BookUpdateServlet")
public class BookUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストの文字エンコーディングをUTF-8に設定
		request.setCharacterEncoding("UTF-8");

		// 1. 画面（JSP）のフォームから送られてきた値を取得する
		String bookIdStr = request.getParameter("bookId");
		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String authorIdStr = request.getParameter("authorId");
		String publisherIdStr = request.getParameter("publisherId");
		String layoutIdStr = request.getParameter("layoutId");
		String bookNumberStr = request.getParameter("bookNumber");
		String imageUrl = request.getParameter("imageUrl");

		try (Connection conn = DBManager.getConnection()) {
			BookDAO bookDAO = new BookDAO(conn);
			int bookId = Integer.parseInt(bookIdStr);

			// 2. book_info_id を特定するために、一度現在のデータをDBから1件取得する
			Optional<Book> optBook = bookDAO.findById(bookId);
			
			if (optBook.isPresent()) {
				Book book = optBook.get();
				
				// 3. 画面から入力された新しい値で上書きする
				book.setBookNumber(Integer.parseInt(bookNumberStr));
				book.setLayoutId(Integer.parseInt(layoutIdStr));

				BookInfo info = book.getBookInfo();
				info.setIsbn(isbn);
				info.setTitle(title);
				info.setAuthorId(Integer.parseInt(authorIdStr));
				info.setPublisherId(Integer.parseInt(publisherIdStr));
				info.setImageUrl(imageUrl);

				// 4. 先ほどBookDAOに追加したupdateメソッドを呼んで、DBを更新する
				boolean success = bookDAO.update(book);

				if (success) {
					// 5. 更新が成功したら、一覧画面（BookListServlet）にリダイレクトして再表示
					response.sendRedirect("BookListServlet");
					return;
				}
			}
			
			// 更新失敗、またはデータが見つからなかった場合
			request.setAttribute("errorMessage", "蔵書情報の更新に失敗しました。");
			request.getRequestDispatcher("BookListServlet").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "システムエラーが発生しました。");
			request.getRequestDispatcher("BookListServlet").forward(request, response);
		}
	}
}