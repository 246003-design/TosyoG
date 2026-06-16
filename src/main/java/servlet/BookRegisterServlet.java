package servlet;

import java.io.IOException;
import java.sql.Connection;

import dao.BookDAO;
import dao.BookInfoDAO;
import dao.DBManager;
import entity.Book;
import entity.BookInfo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/BookRegisterServlet")
public class BookRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookRegisterServlet() {
		super();
	}

	/**
	 * ★新しく追加：URLを直接叩いたとき、またはメニューから遷移したときに
	 * WEB-INFの中にある「book_register.jsp」を安全に表示させる処理
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// WEB-INF/JSP/book_register.jsp を安全に呼び出して画面を表示する
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/book_register.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * 登録ボタンが押されたときの処理（POST送信）
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 文字化け対策
		request.setCharacterEncoding("UTF-8");
		
		// 2. 画面（JSP）から送信されたデータをすべて受け取る
		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String authorName = request.getParameter("author");       // 文字列として受け取る
		String publisherName = request.getParameter("publisher"); // 文字列として受け取る
		String categoryName = request.getParameter("category");   // "文芸" などの文字列
		String imageUrl = request.getParameter("imageUrl");       // APIが取得した画像URL
		
		// 3. データベースに入れるために「文字」を「ID（数字）」に変換・仮設定する
		int categoryId = 4; // デフォルトは「その他」
		if ("文芸".equals(categoryName)) categoryId = 1;
		else if ("技術".equals(categoryName)) categoryId = 2;
		else if ("実用".equals(categoryName)) categoryId = 3;
		
		// ⚠️ 本来は著者名や出版社名からDB検索してIDを取得しますが、今回はテスト用に「1」を仮セットします
		int authorId = 1;
		int publisherId = 1;
		
		// 4. 登録用の箱（Entity）にデータを詰める
		BookInfo bookInfo = new BookInfo();
		bookInfo.setIsbn(isbn);
		bookInfo.setTitle(title);
		bookInfo.setAuthorId(authorId);
		bookInfo.setPublisherId(publisherId);
		bookInfo.setCategoryId(categoryId);
		bookInfo.setImageUrl(imageUrl);
		
		// 5. データベース接続 ＆ 登録処理（バケツリレー！）
		try (Connection conn = DBManager.getConnection()) {
			
			// ① まず「本の中身（マスタ）」を登録して、自動発番されたIDを受け取る
			BookInfoDAO bookInfoDAO = new BookInfoDAO(conn);
			int newBookInfoId = bookInfoDAO.insert(bookInfo);
			
			if (newBookInfoId > 0) {
				// ② 続けて「本の実物（在庫）」を登録する
				BookDAO bookDAO = new BookDAO(conn);
				Book book = new Book();
				
				// さっき取得した親のIDと紐付ける！
				book.setBookInfoId(newBookInfoId);
				book.setBookNumber(1); // 新規なのでとりあえずシリアル番号は1
				book.setLayoutId(1);   // 配置画面はないので「配置ID=1」で固定（以前相談したパターンAです！）
				
				bookDAO.insert(book); // Bookテーブルへ登録！
				
				// ⭕ 登録成功！メニュー画面などに戻る（※成功メッセージを出したい場合はsetAttributeを使います）
				response.sendRedirect("librarian_book_menu.jsp"); // 👈 リダイレクトでスッキリ画面遷移
				
			} else {
				// ❌ BookInfoの登録に失敗した場合
				System.err.println("BookInfoの登録に失敗しました。");
				request.setAttribute("errorMessage", "登録に失敗しました。");
				// エラー時は安全に同じ登録画面（サーブレット経由）にフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/book_register.jsp");
				dispatcher.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "システムエラーが発生しました。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/book_register.jsp");
			dispatcher.forward(request, response);
		}
	}
}