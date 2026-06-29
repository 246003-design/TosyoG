package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Book;

// 図書DAO
public class BookDAO extends BaseDAO {
	
	public BookDAO(Connection conn) {
		super(conn);
	}
	
	// 実物の本（Book）をデータベースに登録する
	public boolean insert(Book book) {
		boolean result = false;
		String sql = "INSERT INTO book (book_info_id, book_number, layout_id) VALUES (?, ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, book.getBookInfoId());
			pstmt.setInt(2, book.getBookNumber());
			pstmt.setInt(3, book.getLayoutId());

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				result = true;
			}
		} catch (SQLException e) {
			System.err.println("BookDAO.insertでエラーが発生しました。");
			e.printStackTrace();
		}
		return result;
	}

	// IDから図書情報を1件取得する（論理削除を除く）
	public Optional<Book> findById(int id) {
		String sql = "SELECT id, book_info_id, book_number, layout_id, created_at, updated_at, deleted_at "
				+ "FROM book WHERE id = ? AND deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Book book = new Book();
					book.setId(rs.getInt("id"));
					book.setBookInfoId(rs.getInt("book_info_id"));
					book.setBookNumber(rs.getInt("book_number"));
					book.setLayoutId(rs.getInt("layout_id"));
					book.setCreatedAt(rs.getTimestamp("created_at"));
					return Optional.of(book);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	// 💡 蔵書の一覧表示＆検索メソッド（完成版）
	public List<Book> searchBooks(String keyword) {
		List<Book> bookList = new ArrayList<>();
		
		// 🌟 書籍マスタ(book_info)と著者(author)を繋いで、タイトル・ISBN・著者名の「どれか」に一致するものを探すSQL
		String sql = "SELECT b.id, b.book_info_id, b.book_number, b.layout_id, b.created_at "
				   + "FROM book b "
				   + "INNER JOIN book_info bi ON b.book_info_id = bi.id "
				   + "LEFT JOIN author a ON bi.author_id = a.id "
				   + "WHERE (bi.title LIKE ? OR bi.isbn LIKE ? OR a.name LIKE ?) "
				   + "  AND b.deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 前後の空白を取り除いて検索キーワードを作成
			String trim = (keyword == null) ? "" : keyword.trim();
			String likeKeyword = "%" + trim + "%";
			
			// 3つの「?」に同じキーワードをセット（どれかに引っかかればOK）
			pstmt.setString(1, likeKeyword);
			pstmt.setString(2, likeKeyword);
			pstmt.setString(3, likeKeyword);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Book book = new Book();
					book.setId(rs.getInt("id"));
					book.setBookInfoId(rs.getInt("book_info_id"));
					book.setBookNumber(rs.getInt("book_number"));
					book.setLayoutId(rs.getInt("layout_id"));
					book.setCreatedAt(rs.getTimestamp("created_at"));
					
					bookList.add(book);
				}
			}
		} catch (SQLException e) {
			System.err.println("BookDAO.searchBooksでエラーが発生しました。");
			e.printStackTrace();
		}
		
		return bookList;
	}
}