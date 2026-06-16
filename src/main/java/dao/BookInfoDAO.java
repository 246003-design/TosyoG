package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.BookInfo;

// 書籍マスタDAO
public class BookInfoDAO extends BaseDAO {

	public BookInfoDAO(Connection conn) {
		super(conn);
	}

	// キーワード検索（タイトルでのあいまい検索）
	public List<BookInfo> search(String keyword) {
		List<BookInfo> bookList = new ArrayList<>();
		String sql = "SELECT id, isbn, title, author_id, publisher_id, category_id, created_at, updated_at, deleted_at "
				+ "FROM book_info WHERE title LIKE ? AND deleted_at IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			String likeKeyword = "%" + keyword + "%";
			pstmt.setString(1, likeKeyword);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					BookInfo book = new BookInfo();
					book.setId(rs.getInt("id"));
					book.setIsbn(rs.getString("isbn"));
					book.setTitle(rs.getString("title"));
					book.setAuthorId(rs.getInt("author_id"));
					book.setPublisherId(rs.getInt("publisher_id"));
					book.setCategoryId(rs.getInt("category_id"));
					book.setCreatedAt(rs.getTimestamp("created_at"));
					book.setUpdatedAt(rs.getTimestamp("updated_at"));
					book.setDeletedAt(rs.getTimestamp("deleted_at"));
					
					bookList.add(book);
				}
			}
		} catch (SQLException e) {
			System.err.println("BookInfoDAO.searchでエラーが発生しました。");
			e.printStackTrace();
		}
		return bookList;
	}

	// 書籍マスタへの新規登録処理（自動採番されたIDを返す）
	public int insert(BookInfo book) {
		int generatedId = 0;
		String sql = "INSERT INTO book_info (isbn, title, author_id, publisher_id, category_id) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, book.getIsbn());
			pstmt.setString(2, book.getTitle());
			pstmt.setInt(3, book.getAuthorId());
			pstmt.setInt(4, book.getPublisherId());
			pstmt.setInt(5, book.getCategoryId());

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						generatedId = generatedKeys.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("BookInfoDAO.insertでエラーが発生しました。");
			e.printStackTrace();
		}
		return generatedId;
	}
	
	// idから書籍マスタの情報を取得する
	public BookInfo searchTitle(int id) {
		BookInfo bookInfo = null;
		String sql = "SELECT id, isbn, title, author_id, publisher_id, category_id, created_at, updated_at, deleted_at "
				+ "FROM book_info WHERE id = ? AND deleted_at IS NULL";
				
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					bookInfo = new BookInfo();
					bookInfo.setId(rs.getInt("id"));
					bookInfo.setIsbn(rs.getString("isbn"));
					bookInfo.setTitle(rs.getString("title"));
					bookInfo.setAuthorId(rs.getInt("author_id"));
					bookInfo.setPublisherId(rs.getInt("publisher_id"));
					bookInfo.setCategoryId(rs.getInt("category_id"));
					bookInfo.setCreatedAt(rs.getTimestamp("created_at"));
					bookInfo.setUpdatedAt(rs.getTimestamp("updated_at"));
					bookInfo.setDeletedAt(rs.getTimestamp("deleted_at"));
				}
			}
		} catch (SQLException e) {
			System.err.println("BookInfoDAO.searchTitleでエラーが発生しました。");
			e.printStackTrace();
		}
		return bookInfo;
	}
}