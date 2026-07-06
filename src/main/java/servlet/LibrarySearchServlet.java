package servlet;

import java.io.IOException;
import java.util.List;

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

    /**
     * 【利用者】図書検索を要求する -> 【システム】図書検索画面を表示する
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 検索画面（JSP）へ遷移
        request.getRequestDispatcher("/search.jsp").forward(request, response);
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
            request.getRequestDispatcher("/search.jsp").forward(request, response);
            return;
        }

        // 【空でない場合】入力内容から図書情報を取得する
        List<BookInfo> results = LibrarySearch.searchBooks(inputContent.trim());

        // 検索結果と入力されたキーワードをリクエストに設定
        request.setAttribute("searchResults", results);
        request.setAttribute("keyword", inputContent);

        // 【システム】検索結果を一覧表示する（結果画面、または同画面の下部に表示）
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
}
