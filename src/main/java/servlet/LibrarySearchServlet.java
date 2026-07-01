package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BookDAO;
import dao.DBManager;
import entity.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LibrarySearchServlet")
public class LibrarySearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * 【利用者】図書検索を要求する -> 【システム】図書検索画面を表示する
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 検索画面（JSP）へ遷移
        request.getRequestDispatcher("WEB-INF/JSP/customer/customer_book_search.jsp").forward(request, response);
    }

    /**
     * 【利用者】検索ボタンを押す（検索条件を入力して送信）
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // リクエストの文字コード設定
        request.setCharacterEncoding("UTF-8");
        
        // 入力内容（図書名・著者名等の条件）を取得
        String inputContent = request.getParameter("inputContent");

        // 分岐：検索内容は空か？
        if (inputContent == null || inputContent.isEmpty()) {
            // キーワードが空の場合は再度検索画面を表示
            request.setAttribute("errorMessage", "検索キーワードを入力してください。");
            request.getRequestDispatcher("WEB-INF/JSP/customer/customer_book_search.jsp").forward(request, response);
            return;
        }

        // 検索結果を格納するリスト
        List<Book> results = null;

        // ★修正ポイント①：画面の各入力欄の「name属性」に合わせた名前で、個別に値を取得する
        String title = request.getParameter("title");       // タイトルの入力値
        String isbn = request.getParameter("isbn");         // ISBN 入力値
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
            
            // ★修正ポイント②：引数4つの詳細検索用メソッド「search」を正しく呼び出す
            results = bookDAO.search(title.trim(), isbn.trim(), author.trim(), category); 

        } catch (SQLException e) {
        	System.err.println("サーブレットでのDB処理中にエラーが発生しました。");
            e.printStackTrace();
            request.setAttribute("errorMessage", "システムエラーが発生しました。");
            request.getRequestDispatcher("WEB-INF/JSP/customer/customer_book_search.jsp").forward(request, response);
            return;
        }

        // 検索結果と入力されたキーワードをリクエストに設定
        request.setAttribute("searchResults", results);
        request.setAttribute("keyword", inputContent);

        // 【システム】検索結果を一覧画面へ表示
        request.getRequestDispatcher("WEB-INF/JSP/customer/customer_book_search_result.jsp").forward(request, response);
    }
}