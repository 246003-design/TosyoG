package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.BookInfo;

// 書籍マスタDAO
public class BookInfoDAO extends BaseDAO {

	// BaseDAOのコンストラクタを呼び出す
	public BookInfoDAO(Connection conn) {
		super(conn);
	}

	/**
	 * 1. キーワード検索（タイトルでのあいまい検索）
	 */
	public List<BookInfo> search(String keyword) {
		List<BookInfo> bookList = new ArrayList<>();
		// 🔴 SQL側のカラム名は、DBの定義に合わせてください（通常は大文字小文字が区別されます）
		String sql = "SELECT id, title, isbn, author_id, publisher_id, category_id, imageUrl, created_at, updated_at, deleted_at "
				+ "FROM book_info WHERE title LIKE ? AND deleted_at IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			String likeKeyword = "%" + keyword + "%";
			pstmt.setString(1, likeKeyword);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					BookInfo book = new BookInfo();
					book.setId(rs.getInt("id"));
					book.setTitle(rs.getString("title"));
					book.setIsbn(rs.getString("isbn"));
					book.setAuthorId(rs.getInt("author_id"));
					book.setPublisherId(rs.getInt("publisher_id"));
					book.setCategoryId(rs.getInt("category_id"));
					
					// 🔴 BookInfo.java のメソッド名「setImageUrl」に完全に合わせました
					book.setImageUrl(rs.getString("imageUrl")); 
					
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

	/**
	 * 2. 書籍マスタへの新規登録処理（環境依存エラー回避版）
	 */
	public int insert(BookInfo book) {
		int generatedId = 0;
		// SQL文
		String insertSql = "INSERT INTO book_info (title, isbn, author_id, publisher_id, category_id, imageUrl) VALUES (?, ?, ?, ?, ?, ?)";
		// 直前に自動採番されたIDを安全に取得するSQL（これで最初のNotSupportedExceptionを回避します）
		String selectIdSql = "SELECT LAST_INSERT_ID()";

		try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getIsbn());
			pstmt.setInt(3, book.getAuthorId());
			pstmt.setInt(4, book.getPublisherId());
			pstmt.setInt(5, book.getCategoryId());
			
			// 🔴 BookInfo.java のメソッド名「getImageUrl」に完全に合わせました
			pstmt.setString(6, book.getImageUrl()); 

			int rows = pstmt.executeUpdate();
			
			// 安全に自動採番されたIDを取得する
			if (rows > 0) {
				try (PreparedStatement pstmtId = conn.prepareStatement(selectIdSql);
					 ResultSet rs = pstmtId.executeQuery()) {
					if (rs.next()) {
						generatedId = rs.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("BookInfoDAO.insertでエラーが発生しました。");
			e.printStackTrace();
		}
		return generatedId;
	}
	
	/**
	 * 3. idから書籍マスタの情報を取得する
	 */
	public BookInfo searchTitle(int id) {
		BookInfo bookInfo = null;
		String sql = "SELECT id, title, isbn, author_id, publisher_id, category_id, imageUrl, created_at, updated_at, deleted_at "
				+ "FROM book_info WHERE id = ? AND deleted_at IS NULL";
				
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					bookInfo = new BookInfo();
					bookInfo.setId(rs.getInt("id"));
					bookInfo.setTitle(rs.getString("title"));
					bookInfo.setIsbn(rs.getString("isbn"));
					bookInfo.setAuthorId(rs.getInt("author_id"));
					bookInfo.setPublisherId(rs.getInt("publisher_id"));
					bookInfo.setCategoryId(rs.getInt("category_id"));
					
					// 🔴 BookInfo.java のメソッド名「setImageUrl」に完全に合わせました
					bookInfo.setImageUrl(rs.getString("imageUrl")); 
					
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