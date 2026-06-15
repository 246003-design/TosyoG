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
		// 🔴 画像URL（imageUrl）も取得するようにSQLに追加
		String sql = "SELECT id, title, isbn, authorId, publisherId, imageUrl, createdAt, updatedAt, deletedAt "
				+ "FROM book_info WHERE title LIKE ? AND deletedAt IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			String likeKeyword = "%" + keyword + "%";
			pstmt.setString(1, likeKeyword);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					BookInfo book = new BookInfo();
					book.setId(rs.getInt("id"));
					book.setTitle(rs.getString("title"));
					book.setIsbn(rs.getString("isbn"));
					book.setAuthorId(rs.getInt("authorId"));
					book.setPublisherId(rs.getInt("publisherId"));
					book.setImageUrl(rs.getString("imageUrl")); // 🔴 画像URLをセット
					book.setCreatedAt(rs.getTimestamp("createdAt"));
					book.setUpdatedAt(rs.getTimestamp("updatedAt"));
					book.setDeletedAt(rs.getTimestamp("deletedAt"));

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
	 * 2. 新規書籍登録（APIから取得した情報＋画像URLを保存する）
	 * 🔴 登録完了時に、データベースが自動生成した「id」をひっくり返して戻す特別なインサート文です！
	 */
	public int insert(BookInfo book) {
		int generatedId = -1; // 失敗時は -1 を返す
		
		// 🔴 imageUrl も一緒に保存するSQL
		String sql = "INSERT INTO book_info (title, isbn, authorId, publisherId, imageUrl) VALUES (?, ?, ?, ?, ?)";

		// 🔴 「PreparedStatement.RETURN_GENERATED_KEYS」をつけることで、自動生成された主キー（id）を取れるようにします
		try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getIsbn());
			pstmt.setInt(3, book.getAuthorId());
			pstmt.setInt(4, book.getPublisherId());
			pstmt.setString(5, book.getImageUrl()); // 🔴 画像URLをインサート！

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				// 🔴 データベースが自動で発番した最新の id を抜き出す処理
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
}