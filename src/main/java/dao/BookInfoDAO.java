package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.BookInfo;

public class BookInfoDAO extends BaseDAO {

	public BookInfoDAO(Connection conn) {
		super(conn);
	}

	/**
	 * 1. キーワード検索処理（一旦、確実に存在するタイトルだけで文字検索します）
	 */
	public List<BookInfo> search(String keyword) {
		List<BookInfo> bookList = new ArrayList<>();
		
		// 🔴 条件は一旦「title」だけに絞ります（数字のカラムに文字は検索できないため）
		String sql = "SELECT * FROM book_info WHERE title LIKE ? AND deleted_at IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			String likeKeyword = "%" + keyword + "%";
			pstmt.setString(1, likeKeyword); // タイトル（String）の ? にハメ込む

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					BookInfo book = new BookInfo();
					book.setId(rs.getInt("id"));
					book.setTitle(rs.getString("title"));
					book.setIsbn(rs.getString("isbn"));
					
					// 🔴 データベースから「数字(int)」で取ってきて、Entityの「Id(int)」にセット！
					book.setAuthorId(rs.getInt("author_id"));
					book.setPublisherId(rs.getInt("publisher_id"));
					
					book.setCreatedAt(rs.getTimestamp("created_at"));
					book.setUpdatedAt(rs.getTimestamp("updated_at"));
					book.setDeletedAt(rs.getTimestamp("deleted_at"));

					bookList.add(book);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookList;
	}

	/**
	 * 2. 新規書籍登録処理
	 */
	public boolean insert(BookInfo book) {
		boolean result = false;
		// カラム名も、あなたの設計に合わせて「isbn」「author_id」「publisher_id」に対応させました
		String sql = "INSERT INTO book_info (title, isbn, author_id, publisher_id) VALUES (?, ?, ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getIsbn());
			pstmt.setInt(3, book.getAuthorId());
			pstmt.setInt(4, book.getPublisherId());

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}