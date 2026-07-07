package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LibrarySearchServlet")
public class LibrarySearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
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
        
        request.setCharacterEncoding("UTF-8");
        
        // 1. 検索条件の取得
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String category = request.getParameter("category");

        // 全ての入力が空かどうかのチェック（中身は維持）
        if ((title == null || title.trim().isEmpty()) &&
            (author == null || author.trim().isEmpty()) &&
            (isbn == null || isbn.trim().isEmpty()) &&
            (category == null || category.trim().isEmpty() || category.equals("すべて"))) {
            
            request.setAttribute("errorMessage", "【警告】検索条件が何も入力されていません。");
            request.getRequestDispatcher("WEB-INF/JSP/customer/customer_book_search.jsp").forward(request, response);
            return;
        }

        // 💡 セッションからログイン中のユーザーIDを取得（ReserveServletと同じ仕組み）
        HttpSession session = request.getSession();
        Integer loginUserId = (Integer) session.getAttribute("loginUserId");

        // 2. データベース接続とデータ取得
        try (java.sql.Connection conn = dao.DBManager.getConnection()) {
            
            dao.BookDAO bookDAO = new dao.BookDAO(conn);
            
            // 図書の検索結果を取得
            List<entity.Book> results = bookDAO.search(title, isbn, author, category);
            request.setAttribute("bookList", results);
            // 該当件数もJSPに渡す
            request.setAttribute("totalCount", results.size());

            // 💡 【ここが核心】全ての予約データを一回取り出して、JSPが判定しやすい形に整理する
            dao.ReservationDAO reservationDAO = new dao.ReservationDAO(conn);
            List<entity.Reservation> allReservations = reservationDAO.findAll();

            // 「どの本(bookId)」が「誰によって」予約されているかを記録するマップを作る
            java.util.Map<Integer, Integer> bookReserverMap = new java.util.HashMap<>();
            
            for (entity.Reservation r : allReservations) {
                // ステータスが 1(予約中) または 2(受取待ち) のアクティブな予約だけを対象にする
                if (r.getStatus() == 1 || r.getStatus() == 2) {
                    // キー: bookId, 値: userId (誰が予約しているか)
                    bookReserverMap.put(r.getBookId(), r.getUserId());
                }
            }

            // JSPに2つの荷物を送る！
            request.setAttribute("bookReserverMap", bookReserverMap); // 予約状況のマップ
            request.setAttribute("loginUserId", loginUserId);          // 現在のログインユーザーID
            
        } catch (Exception e) {
            System.err.println("検索処理中にエラーが発生しました。");
            e.printStackTrace();
            request.setAttribute("errorMessage", "システムエラーが発生しました。");
            request.getRequestDispatcher("WEB-INF/JSP/customer/customer_book_search.jsp").forward(request, response);
            return;
        }

        // 【システム】検索結果画面へ移動
        request.getRequestDispatcher("WEB-INF/JSP/customer/customer_book_search_result.jsp").forward(request, response);
    }
}