package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BookDAO;
import dao.DBManager;
import entity.Book;
import entity.BookInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.LibrarySearch;

@WebServlet("/LibrarySearchServlet")
public class LibrarySearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    

    // 模擬データベースのインスタンス
    private final LibrarySearch database = new LibrarySearch();

//github.com/246003-design/TosyoG.git
    /**
     * 【利用者】図書検索を要求する -> 【システム】図書検索画面を表示する
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // ★修正：先頭に「/」を追加
        request.getRequestDispatcher("/WEB-INF/JSP/customer/customer_book_search.jsp").forward(request, response);
    }

    /**
     * 【利用者】検索ボタンを押す（検索条件を入力して送信）
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // リクエストの文字コード設定
        request.setCharacterEncoding("UTF-8");

        // 検索結果を格納するリスト
        List<Book> results = null;

        // 画面の各入力欄の「name属性」に合わせた名前で、個別に値を取得する
        String title = request.getParameter("title");       // タイトルの入力値
        String isbn = request.getParameter("isbn");         // ISBNの入力値
        String author = request.getParameter("author");     // 著者の入力値
        String category = request.getParameter("category"); // 分類の選択値（プルダウン）

        // nullが入ってきた場合の対策（空文字にしておく）
        if (title == null) title = "";
        if (isbn == null) isbn = "";
        if (author == null) author = "";
        if (category == null) category = "すべて";

        // 1. データベース接続の開始
        try (Connection conn = DBManager.getConnection()) {
            // 2. BookDAOのインスタンス化
            BookDAO bookDAO = new BookDAO(conn);
            
            // 引数4つの詳細検索用メソッド「search」を呼び出す
            results = bookDAO.search(title.trim(), isbn.trim(), author.trim(), category); 

        } catch (SQLException e) {
            System.err.println("サーブレットでのDB処理中にエラーが発生しました。");
            e.printStackTrace();
            request.setAttribute("errorMessage", "システムエラーが発生しました。");
            // ★修正：先頭に「/」を追加
            request.getRequestDispatcher("/WEB-INF/JSP/customer/customer_book_search.jsp").forward(request, response);
            return;
        }


        // 【空でない場合】入力内容から図書情報を取得する
        String inputContent = null;
		List<BookInfo> results1 = LibrarySearch.searchBooks(inputContent.trim());

        // ★修正：JSPの items="${bookList}" と名前を一致させる
        request.setAttribute("bookList", results);
        
        // ★修正：JSPの <c:out value="${totalCount}" /> に件数を渡す
        int count = (results != null) ? results.size() : 0;
        request.setAttribute("totalCount", count);
        https://github.com/246003-design/TosyoG.git

        // 【システム】検索結果を一覧画面へ表示
        request.getRequestDispatcher("/WEB-INF/JSP/customer/customer_book_search_result.jsp").forward(request, response);
    }
}