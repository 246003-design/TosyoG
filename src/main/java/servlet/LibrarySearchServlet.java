package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.BookDAO;
import dao.BookInfoDAO;
import dao.DBManager;
import dao.ReservationDAO;
import entity.Book;
import entity.BookInfo;
import entity.Reservation;
import entity.User; // 🌟 Userエンティティをインポート
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
        
        // 1. セッションから loginUser オブジェクトを取得
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        

        // 2. ログイン情報を維持できているか（nullでないか）チェック
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return; 
        }
        
        request.getRequestDispatcher("/WEB-INF/JSP/customer/customer_book_search.jsp").forward(request, response);
    }

    /**
     * 【利用者】検索ボタンを押す（検索条件を入力して送信）
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        // 1. まず最初にログインチェック！（doGetと同じ処理）
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return; 
        }
        
        // 2. loginUserオブジェクトから確実にユーザーIDを取得
        int loginUserId = loginUser.getId();

        // 3. 検索条件の取得
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String category = request.getParameter("category");

        // 全ての入力が空かどうかのチェック
        if ((title == null || title.trim().isEmpty()) &&
            (author == null || author.trim().isEmpty()) &&
            (isbn == null || isbn.trim().isEmpty()) &&
            (category == null || category.trim().isEmpty() || category.equals("すべて"))) {
            
            // JSP側で作った赤いエラー枠の変数名「errorMsg」に合わせてセット
            request.setAttribute("errorMsg", "検索条件を少なくとも1つ入力してください。");
            request.getRequestDispatcher("/WEB-INF/JSP/customer/customer_book_search.jsp").forward(request, response);
            return;
        }

        // nullが入ってきた場合の対策（空文字やデフォルト値にしておく）
        if (title == null) title = "";
        if (isbn == null) isbn = "";
        if (author == null) author = "";
        if (category == null) category = "すべて";

        // 4. データベース接続とデータ取得
        try (Connection conn = DBManager.getConnection()) {
            
            // --- ① 図書の検索を実行 ---
            BookDAO bookDAO = new BookDAO(conn);
            List<Book> results = bookDAO.search(title, isbn, author, category);

            // --- ② BookInfoDAOを使って詳細を補填 ---
            if (results != null) {
                BookInfoDAO bookInfoDAO = new BookInfoDAO(conn);
                for (Book book : results) {
                    BookInfo info = bookInfoDAO.findDetailById(book.getBookInfoId());
                    book.setBookInfo(info);
                }
            }

            // --- ③ 予約DAOを使い、すべての予約データを取得してマップを生成 ---
            ReservationDAO reservationDAO = new ReservationDAO(conn);
            List<Reservation> allReservations = reservationDAO.findAll();

            Map<Integer, Integer> bookReserverMap = new HashMap<>();
            
            if (allReservations != null) {
                for (Reservation r : allReservations) {
                    if (r.getStatus() == 1 || r.getStatus() == 2) {
                        bookReserverMap.put(r.getBookId(), r.getUserId());
                    }
                }
            }

            // --- ④ 検索結果の図書に現在のユーザーの予約フラグを仕込む ---
            if (results != null) {
                for (Book book : results) {
                    boolean isReserved = false;
                    
                    Integer reserverId = bookReserverMap.get(book.getId());
                    // loginUserId (int) とマップのIDを比較して判定
                    if (reserverId != null && reserverId == loginUserId) {
                        isReserved = true;
                    }
                    book.setReservedByCurrentUser(isReserved);
                }
            }

            // --- ⑤ JSPに荷物を送る ---
            request.setAttribute("bookList", results);         
            request.setAttribute("totalCount", results != null ? results.size() : 0);
            
          
            request.setAttribute("reservedMap", bookReserverMap); 
            
            request.setAttribute("loginUserId", loginUserId);          
            
            // 検索結果画面へ移動
            request.getRequestDispatcher("/WEB-INF/JSP/customer/customer_book_search_result.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("検索処理中にエラーが発生しました。");
            e.printStackTrace();
            // ここも "errorMsg" に変更してJSPに送る
            request.setAttribute("errorMsg", "システムエラーが発生しました。");
            request.getRequestDispatcher("/WEB-INF/JSP/customer/customer_book_search.jsp").forward(request, response);
        }
    }
}