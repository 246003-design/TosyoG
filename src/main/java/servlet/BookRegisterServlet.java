package servlet;

import java.io.IOException;
import java.sql.Connection;

import dao.BookDAO;
import dao.BookInfoDAO;
import dao.DBManager;
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
		
		// 🚨 未ログインの場合はログイン画面へ弾く（ファイル名は環境に合わせてください）
		if (loginUser == null) {
			response.sendRedirect("login.jsp");
			return; // これ以上下の処理をさせない
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
			// 🚨 利用者（または予期せぬ権限）の場合は、利用者のメニューへ強制送還！
			response.sendRedirect("customer_book_menu.jsp");
			return; // JSPの表示処理はせずに終了
		}

		// 権限がある人だけ、目的のJSPへフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
		dispatcher.forward(request, response);
	}

	/**
	 * 登録ボタンが押されたときの処理（POST）
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		// 🚨 POST送信（ボタン押下）の場合も、念のため権限をチェックして弾く（直URLアタック対策）
		if (loginUser == null || (loginUser.getRole() != 1 && loginUser.getRole() != 2)) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		int role = loginUser.getRole();
		String errorJspPath = ""; 
		if (role == 1) {
			errorJspPath = "/WEB-INF/JSP/librarian/librarian_book_register.jsp";
		} else if (role == 2) {
			errorJspPath = "/WEB-INF/JSP/admin/admin_book_register.jsp";
		}
		
		// 画面から送信されたデータをすべて受け取る
		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String authorName = request.getParameter("author");
		String publisherName = request.getParameter("publisher");
		String categoryName = request.getParameter("category");
		String imageUrl = request.getParameter("imageUrl");
		
		int categoryId = 4;
		if ("文芸".equals(categoryName)) categoryId = 1;
		else if ("技術".equals(categoryName)) categoryId = 2;
		else if ("実用".equals(categoryName)) categoryId = 3;
		
		int authorId = 1;
		int publisherId = 1;
		
		BookInfo bookInfo = new BookInfo();
		bookInfo.setIsbn(isbn);
		bookInfo.setTitle(title);
		bookInfo.setAuthorId(authorId);
		bookInfo.setPublisherId(publisherId);
		bookInfo.setCategoryId(categoryId);
		bookInfo.setImageUrl(imageUrl);
		
		try (Connection conn = DBManager.getConnection()) {
			BookInfoDAO bookInfoDAO = new BookInfoDAO(conn);
			
			// 💡 1. まずISBNで既に登録されているか検索する
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
				
				// 💡 2. 確定したIDを使って物理本(book)を登録する
				book.setBookInfoId(targetBookInfoId);
				book.setBookNumber(1); 
				book.setLayoutId(1);   
				
				bookDAO.insert(book);
				
				// ⭕ 登録成功後のメニュー画面へのリダイレクト
					response.sendRedirect("BookRegisterServlet");
				
			} else {
				System.err.println("DBへの登録に失敗しました。");
				request.setAttribute("errorMessage", "登録に失敗しました。");
				RequestDispatcher dispatcher = request.getRequestDispatcher(errorJspPath);
				dispatcher.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "システムエラーが発生しました。");
			RequestDispatcher dispatcher = request.getRequestDispatcher(errorJspPath);
			dispatcher.forward(request, response);
		}
	}
}