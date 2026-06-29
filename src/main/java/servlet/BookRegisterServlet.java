package servlet;

import java.io.IOException;
import java.sql.Connection;

import dao.BookDAO;
import dao.BookInfoDAO;
import dao.DBManager;
import dao.MasterDAO; // 💡 追加：マスタ自動登録用のDAO
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

	/**
	 * 画面表示時の処理（GET）
	 * 司書と管理者のみ画面を表示し、それ以外は強制送還する
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		// 🚨 未ログインの場合はログイン画面へ弾く
		if (loginUser == null) {
			response.sendRedirect("LoginServlet");
			return; 
		}

		int role = loginUser.getRole();
		String jspPath = ""; 
		
		if (role == 1) {
			// 司書の場合
			jspPath = "/WEB-INF/JSP/librarian/librarian_book_register.jsp";
		} else if (role == 2) {
			// 管理者の場合
			jspPath = "/WEB-INF/JSP/admin/admin_book_register.jsp";
		} else {
			// それ以外（利用者など）はホームへ強制送還
			response.sendRedirect("HomeServlet");
			return; 
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
		dispatcher.forward(request, response);
	}

	/**
	 * 登録ボタンが押された時の処理（POST）
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

		int role = loginUser.getRole();
		
		// 画面から入力された値（文字列）を受け取る
		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String authorName = request.getParameter("author");
		String publisherName = request.getParameter("publisher");
		String categoryName = request.getParameter("categoryName");
		String imageUrl = request.getParameter("imageUrl");
		
		try (Connection conn = DBManager.getConnection()) {
			
			// 💡 1. MasterDAOを使って、著者名・出版社名・カテゴリ名からIDを取得（無ければ自動登録される）
			MasterDAO masterDAO = new MasterDAO(conn);
			int authorId = masterDAO.getOrCreateAuthor(authorName);
			int publisherId = masterDAO.getOrCreatePublisher(publisherName);
			int categoryId = masterDAO.getOrCreateCategory(categoryName);
			
			// 💡 2. 確定した各IDをBookInfoにセットする
			BookInfo bookInfo = new BookInfo();
			bookInfo.setIsbn(isbn);
			bookInfo.setTitle(title);
			bookInfo.setAuthorId(authorId);
			bookInfo.setPublisherId(publisherId);
			bookInfo.setCategoryId(categoryId);
			bookInfo.setImageUrl(imageUrl);
			
			BookInfoDAO bookInfoDAO = new BookInfoDAO(conn);
			
			// 💡 3. まずISBNで既に登録されているか検索する
			BookInfo existingInfo = bookInfoDAO.findByIsbn(isbn);
			int targetBookInfoId = 0;
			
			if (existingInfo != null) {
				// 既に登録済みの本なら、そのIDを利用する（book_infoには追加しない）
				targetBookInfoId = existingInfo.getId();
				System.out.println("🔍 デバッグ: 登録済みの本を発見しました！ 取得したID=" + targetBookInfoId);
			} else {
				// まだ登録されていない本なら、新規登録して新しいIDを取得する
				targetBookInfoId = bookInfoDAO.insert(bookInfo);
				System.out.println("🔍 デバッグ: 未登録の本と判断し、新規登録しました。 結果ID=" + targetBookInfoId);
			}
			
			if (targetBookInfoId > 0) {
				BookDAO bookDAO = new BookDAO(conn);
				Book book = new Book();
				
				// 💡 4. 確定したIDを使って物理本(book)を登録する
				book.setBookInfoId(targetBookInfoId);
				book.setBookNumber(1); 
				book.setLayoutId(1);   
				
				bookDAO.insert(book);
				
				// ⭕ 登録成功後、再び自分自身（BookRegisterServlet）をGETで呼び出して画面をリセットする
				response.sendRedirect("BookRegisterServlet");
				
			} else {
				System.err.println("BookInfoの登録に失敗しました。");
				request.setAttribute("errorMessage", "登録に失敗しました。");
				String jspPath = (role == 1) ? "/WEB-INF/JSP/librarian/librarian_book_register.jsp" : "/WEB-INF/JSP/admin/admin_book_register.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
				dispatcher.forward(request, response);
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