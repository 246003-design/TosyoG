package servlet;

import java.io.IOException;
import java.sql.Connection;

import dao.BookDAO;
import dao.BookInfoDAO;
import dao.DBManager;
import dao.MasterDAO;
import entity.Book;
import entity.BookInfo;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/BookRegisterServlet")
public class BookRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookRegisterServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			response.sendRedirect("LoginServlet");
			return; 
		}

		int role = loginUser.getRole();
		String jspPath = ""; 
		
		if (role == 1) {
			jspPath = "/WEB-INF/JSP/librarian/librarian_book_register.jsp";
		} else if (role == 2) {
			jspPath = "/WEB-INF/JSP/admin/admin_book_register.jsp";
		} else {
			response.sendRedirect("HomeServlet");
			return; 
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

		int role = loginUser.getRole();
		
		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String authorName = request.getParameter("author");
		String publisherName = request.getParameter("publisher");
		String categoryName = request.getParameter("categoryName");
		String imageUrl = request.getParameter("imageUrl");
		
		try (Connection conn = DBManager.getConnection()) {
			
			// 💡 変更：オートコミットをOFFにしてトランザクションを開始
			conn.setAutoCommit(false);
			
			try {
				MasterDAO masterDAO = new MasterDAO(conn);
				int authorId = masterDAO.getOrCreateAuthor(authorName);
				int publisherId = masterDAO.getOrCreatePublisher(publisherName);
				int categoryId = masterDAO.getOrCreateCategory(categoryName);
				
				BookInfo bookInfo = new BookInfo();
				bookInfo.setIsbn(isbn);
				bookInfo.setTitle(title);
				bookInfo.setAuthorId(authorId);
				bookInfo.setPublisherId(publisherId);
				bookInfo.setCategoryId(categoryId);
				bookInfo.setImageUrl(imageUrl);
				
				BookInfoDAO bookInfoDAO = new BookInfoDAO(conn);
				
				BookInfo existingInfo = bookInfoDAO.findByIsbn(isbn);
				int targetBookInfoId = 0;
				
				if (existingInfo != null) {
					targetBookInfoId = existingInfo.getId();
					System.out.println("🔍 デバッグ: 登録済みの本を発見しました！ 取得したID=" + targetBookInfoId);
				} else {
					targetBookInfoId = bookInfoDAO.insert(bookInfo);
					System.out.println("🔍 デバッグ: 未登録の本と判断し、新規登録しました。 結果ID=" + targetBookInfoId);
				}
				
				if (targetBookInfoId > 0) {
					BookDAO bookDAO = new BookDAO(conn);
					Book book = new Book();
					
					book.setBookInfoId(targetBookInfoId);
					
					// 💡 変更：1固定ではなく、排他制御で現在の最大値+1を取得してセットする
					int nextNumber = bookDAO.getNextBookNumberWithLock(targetBookInfoId);
					book.setBookNumber(nextNumber); 
					
					book.setLayoutId(1);   
					
					// 💡 変更：インサート処理の成功判定を行う
					boolean isBookInserted = bookDAO.insert(book);
					
					if (isBookInserted) {
						// 💡 成功：すべての登録が終わったのでDBの変更を確定（コミット）
						conn.commit();
						response.sendRedirect("BookRegisterServlet");
					} else {
						// 失敗：図書テーブル(book)への登録に失敗したら、BookInfoなどの変更も取り消す
						conn.rollback();
						System.err.println("Bookテーブルへの登録に失敗しました。");
						request.setAttribute("errorMessage", "蔵書の登録に失敗しました。");
						String jspPath = (role == 1) ? "/WEB-INF/JSP/librarian/librarian_book_register.jsp" : "/WEB-INF/JSP/admin/admin_book_register.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
						dispatcher.forward(request, response);
					}
					
				} else {
					// 失敗：BookInfoテーブルへの登録に失敗した場合も取り消す
					conn.rollback();
					System.err.println("BookInfoの登録に失敗しました。");
					request.setAttribute("errorMessage", "書籍情報の登録に失敗しました。");
					String jspPath = (role == 1) ? "/WEB-INF/JSP/librarian/librarian_book_register.jsp" : "/WEB-INF/JSP/admin/admin_book_register.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
					dispatcher.forward(request, response);
				}
				
			} catch (Exception e) {
				// 💡 変更：SQLエラーなどが途中で起きた場合は、トランザクションをロールバック（巻き戻し）する
				conn.rollback();
				throw e; // エラーを外側のcatchブロックへ投げる
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "システムエラーが発生しました: " + e.getMessage());
			String jspPath = (role == 1) ? "/WEB-INF/JSP/librarian/librarian_book_register.jsp" : "/WEB-INF/JSP/admin/admin_book_register.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
			dispatcher.forward(request, response);
		}
	}
}