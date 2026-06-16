package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.BookInfo;

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
	 * 2. 新規書籍登録（APIから取得した情報＋画像URL＋カテゴリIDを保存する）
	 */
	public int insert(BookInfo book) {
		int generatedId = -1; // 失敗時は -1 を返す
		
		// 🔴 インサート先のカラム名も「アンダーバー型」にし、category_id も追加しました！
		String sql = "INSERT INTO book_info (title, isbn, author_id, publisher_id, category_id, imageUrl) VALUES (?, ?, ?, ?, ?, ?)";

		// 🔴 「PreparedStatement.RETURN_GENERATED_KEYS」をつけることで、自動生成された主キー（id）を取れるようにします
		try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getIsbn());
			pstmt.setInt(3, book.getAuthorId());
			pstmt.setInt(4, book.getPublisherId());
			pstmt.setInt(5, book.getCategoryId());
			pstmt.setString(6, book.getImageUrl());

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						generatedId = generatedKeys.getInt(1); // 生成されたIDをゲット！
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("BookInfoDAO.insertでエラーが発生しました。");
			e.printStackTrace();
		}
		return generatedId; // 🔴 成功すれば、新しく採番されたID（例: 15 とか）が戻る
	}
	
	/**
	 * 3. idが届いたら情報を詰めて返す（存在しなかったらnull）
	 */
	public BookInfo searchTitle(int id) {
		BookInfo bookInfo = null;
		
		String sql = "SELECT * FROM book_info WHERE id = ? AND deleted_at IS NULL";
		try (PreparedStatement pstms = conn.prepareStatement(sql)) {
			pstms.setInt(1, id);
			
			try (ResultSet rs = pstms.executeQuery()) {
				if (rs.next()) {
					bookInfo = new BookInfo();
					
					bookInfo.setId(rs.getInt("id"));
					bookInfo.setTitle(rs.getString("title"));
					bookInfo.setIsbn(rs.getString("isbn"));
					bookInfo.setAuthorId(rs.getInt("author_id"));
					bookInfo.setPublisherId(rs.getInt("publisher_id"));
					bookInfo.setCategoryId(rs.getInt("category_id"));
					bookInfo.setImageUrl(rs.getString("imageUrl"));
				}
			}
		} catch (SQLException e) {
			System.err.println("BookInfoDAO.searchTitleでエラーが発生しました。");
			e.printStackTrace();
		}
		
		return bookInfo;
	}
}