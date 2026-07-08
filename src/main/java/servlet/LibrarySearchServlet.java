package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import dao.BookDAO;
import dao.DBManager;
import dao.ReservationDAO;
import entity.Book;
import entity.Reservation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // 💡 追加：セッション用にインポート

@WebServlet("/LibrarySearchServlet")
public class LibrarySearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 【利用者】図書検索を要求する -> 【システム】図書検索画面を表示する
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
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
        
        // 💡 修正：セッションオブジェクトをリクエストから取得する
        HttpSession session = request.getSession();

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

        // 検索結果を格納するリストの宣言
        List<Book> results = null;

        // 2. データベース接続とデータ取得
        try (Connection conn = DBManager.getConnection()) {
            
            // --- ① 図書の検索を実行 ---
            BookDAO bookDAO = new BookDAO(conn);
            // 💡 修正：results の重複定義（List<Book>）をなくし、宣言済みの変数に代入する形にする
            results = bookDAO.search(title, isbn, author, category);

            // --- ② 予約DAOを追加し、すべての予約データを取得 ---
            ReservationDAO reservationDAO = new ReservationDAO(conn);
            List<Reservation> allReservations = reservationDAO.findAll();

            // セッションから現在のログインユーザーIDを取得
            Integer loginUserId = (Integer) session.getAttribute("loginUserId");

            // --- ③ 検索結果の図書を1冊ずつループし、予約状況をチェック ---
            if (loginUserId != null && results != null) {
                for (Book book : results) {
                    boolean isReserved = false;
                    
                    for (Reservation r : allReservations) {
                        // 「ログインユーザーの予約」かつ「この本のID」かつ「ステータスが予約中(1)か受取待ち(2)」の場合
                        if (r.getUserId() == loginUserId && r.getBookId() == book.getId() && (r.getStatus() == 1 || r.getStatus() == 2)) {
                            isReserved = true;
                            break; // 予約が見つかったら、この本のチェックは終了して次の本へ
                        }
                    }
                    
                    // ➔ entity.Bookに作ったメソッドを使って、判定結果（true/false）を仕込む！
                    book.setReservedByCurrentUser(isReserved);
                }
            }

            // --- ④ JSPに荷物を送る ---
            request.setAttribute("bookList", results); // フラグが仕込まれた状態のリスト
            request.setAttribute("totalCount", results != null ? results.size() : 0);
            
            // 💡 修正：【超重要】検索結果を表示するために、JSPへフォワードする処理を追加！
            request.getRequestDispatcher("/WEB-INF/JSP/customer/customer_book_search_result.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("検索処理中にエラーが発生しました。");
            e.printStackTrace();
            request.setAttribute("errorMessage", "システムエラーが発生しました。");
            // 💡 修正：先頭に「/」を追加
            request.getRequestDispatcher("/WEB-INF/JSP/customer/customer_book_search.jsp").forward(request, response);
        }
    }
}