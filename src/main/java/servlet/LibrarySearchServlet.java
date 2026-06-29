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
    
//    // 模擬データベースのインスタンス
//    private final Database database = new Database();

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
        if (inputContent == null || inputContent.trim().isEmpty()) {
            // 【空の場合】再入力するよう表示する -> 図書検索画面を表示する
            request.setAttribute("errorMessage", "【警告】検索キーワードが空です。再入力してください。");
            request.getRequestDispatcher("WEB-INF/JSP/customer/customer_book_search.jsp").forward(request, response);
            return;
        }

     // 【空でない場合】入力内容から図書情報を取得する
        List<Book> results = null;

        // ★修正ポイント①：画面の各入力欄の「name属性」に合わせた名前で、個別に値を取得する
        String title = request.getParameter("title");       // タイトルの入力値
        String isbn = request.getParameter("isbn");         // ISBNの入力値
        String author = request.getParameter("author");     // 著者の入力値
        String category = request.getParameter("category"); // 分類の選択値（プルダウン）

        // nullが入ってきた場合の対策（空文字にしておく）
        if (title == null) title = "";
        if (isbn == null) isbn = "";
        if (author == null) author = "";

        // 1. データベース接続の開始
        // 1. データベース接続の開始
        try (Connection conn = DBManager.getConnection()) {
            // 2. BookDAOのインスタンス化
            BookDAO bookDAO = new BookDAO(conn);
            
            // ★修正ポイント：大文字の「BookDAO」を小文字の「bookDAO」に変更！
            results = bookDAO.searchBooks(title.trim(), isbn.trim(), author.trim(), category); 

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

        // 【システム】検索結果を一覧表示する（結果画面、または同画面の下部に表示）
        request.getRequestDispatcher("WEB-INF/JSP/customer/customer_book_search_result.jsp").forward(request, response);
    }
}