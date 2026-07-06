package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.DBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CheckBookApiServlet")
public class CheckBookApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // JavaScriptが解析しやすいようにJSON形式で返す設定
        response.setContentType("application/json; charset=UTF-8");
        
        String isbn = request.getParameter("isbn");
        PrintWriter out = response.getWriter();
        
        // 💡 著者・出版社・カテゴリの各マスターテーブルをJOINして「名前(name)」を取得するSQL
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT bi.title, bi.imageUrl, ");
        sql.append("       a.name AS author_name, ");
        sql.append("       p.name AS publisher_name, ");
        sql.append("       c.name AS category_name ");
        sql.append("FROM book_info bi ");
        sql.append("LEFT JOIN author a ON bi.author_id = a.id ");
        sql.append("LEFT JOIN publisher p ON bi.publisher_id = p.id ");
        sql.append("LEFT JOIN category c ON bi.category_id = c.id ");
        sql.append("WHERE bi.isbn = ?");
        
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            pstmt.setString(1, isbn);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // データベースからそれぞれの名前（文字列）を取り出す
                    String title = rs.getString("title");
                    String author = rs.getString("author_name");
                    String publisher = rs.getString("publisher_name");
                    String category = rs.getString("category_name");
                    String imageUrl = rs.getString("imageUrl");
                    
                    // JSONの文字列が壊れないようにダブルクォーテーションをエスケープする安全処理
                    title = (title != null) ? title.replace("\"", "\\\"") : "";
                    author = (author != null) ? author.replace("\"", "\\\"") : "";
                    publisher = (publisher != null) ? publisher.replace("\"", "\\\"") : "";
                    category = (category != null) ? category.replace("\"", "\\\"") : "";
                    imageUrl = (imageUrl != null) ? imageUrl.replace("\"", "\\\"") : "";
                    
                    // すべてのデータを1つのJSONにしてJavaScriptへ送信
                    out.print(String.format(
                        "{\"exists\": true, \"title\": \"%s\", \"author\": \"%s\", \"publisher\": \"%s\", \"category\": \"%s\", \"imageUrl\": \"%s\"}",
                        title, author, publisher, category, imageUrl
                    ));
                } else {
                    // 自システムDBにこのISBNの本が1件も登録されていない場合
                    out.print("{\"exists\": false}");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 何らかのエラーが発生した場合
            out.print("{\"error\": true}");
        }
    }
}