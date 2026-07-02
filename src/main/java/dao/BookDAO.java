package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Book;
import entity.BookInfo;

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

	// 💡 追加: 排他制御（ロック）をかけながら、指定した本の次の番号(book_number)を取得する
	public int getNextBookNumberWithLock(int bookInfoId) throws SQLException {
		// FOR UPDATE をつけることで、トランザクションが終了（コミット/ロールバック）するまで他の処理を待たせます
		String sql = "SELECT COALESCE(MAX(book_number), 0) AS max_num FROM book WHERE book_info_id = ? FOR UPDATE";
		
		try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
			pStmt.setInt(1, bookInfoId);
			try (ResultSet rs = pStmt.executeQuery()) {
				if (rs.next()) {
					int currentMax = rs.getInt("max_num");
					return currentMax + 1; // 現在の最大値 + 1 を返す
				}
			}
		}
		return 1; // データが1件もない場合は 1 を返す
	}

	// 図書を論理削除するメソッド（deleted_at に現在時刻をセット）
	public boolean delete(int id) {
		boolean result = false;
		String sql = "UPDATE book SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				result = true;
			}
		} catch (SQLException e) {
			System.err.println("BookDAO.deleteでエラーが発生しました。");
			e.printStackTrace();
		}
		return result;
	}

	// IDから図書情報を1件取得する（論理削除を除く）
	public Optional<Book> findById(int id) {
		String sql = "SELECT b.id, b.book_info_id, b.book_number, b.layout_id, b.created_at, b.updated_at, b.deleted_at, "
				+ "bi.isbn, bi.title, bi.author_id, bi.publisher_id, bi.category_id, bi.imageUrl "
				+ "FROM book b "
				+ "LEFT JOIN book_info bi ON b.book_info_id = bi.id "
				+ "WHERE b.id = ? AND b.deleted_at IS NULL";
		
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
					book.setUpdatedAt(rs.getTimestamp("updated_at"));
					book.setDeletedAt(rs.getTimestamp("deleted_at"));

					// BookInfoをセットしてネストした呼び出しに対応
					BookInfo info = new BookInfo();
					info.setId(rs.getInt("book_info_id"));
					info.setIsbn(rs.getString("isbn"));
					info.setTitle(rs.getString("title"));
					info.setAuthorId(rs.getInt("author_id"));
					info.setPublisherId(rs.getInt("publisher_id"));
					info.setCategoryId(rs.getInt("category_id"));
					info.setImageUrl(rs.getString("imageUrl"));
					
					book.setBookInfo(info);

					return Optional.of(book);
				}
			}
		} catch (SQLException e) {
			System.err.println("BookDAO.findByIdでエラーが発生しました。");
			e.printStackTrace();
		}
		return Optional.empty();
	}

	/**
	 * キーワード（書名、著者名、ISBN）から蔵書を検索して一覧を取得する（論理削除を除く）
	 */
	public List<Book> searchBooks(String keyword) {
		List<Book> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT b.id, b.book_info_id, b.book_number, b.layout_id, b.created_at, b.updated_at, b.deleted_at, ");
		sql.append("bi.isbn, bi.title, bi.author_id, bi.publisher_id, bi.category_id, bi.imageUrl ");
		sql.append("FROM book b ");
		sql.append("INNER JOIN book_info bi ON b.book_info_id = bi.id ");
		sql.append("LEFT JOIN author a ON bi.author_id = a.id ");
		sql.append("WHERE b.deleted_at IS NULL ");
		
		if (keyword != null && !keyword.trim().isEmpty()) {
			sql.append("AND (bi.title LIKE ? OR bi.isbn LIKE ? OR a.name LIKE ?) ");
		}
		
		sql.append("ORDER BY b.id DESC");

		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
			if (keyword != null && !keyword.trim().isEmpty()) {
				String likeKeyword = "%" + keyword.trim() + "%";
				pstmt.setString(1, likeKeyword);
				pstmt.setString(2, likeKeyword);
				pstmt.setString(3, likeKeyword);
			}

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Book book = new Book();
					book.setId(rs.getInt("id"));
					book.setBookInfoId(rs.getInt("book_info_id"));
					book.setBookNumber(rs.getInt("book_number"));
					book.setLayoutId(rs.getInt("layout_id"));
					book.setCreatedAt(rs.getTimestamp("created_at"));
					book.setUpdatedAt(rs.getTimestamp("updated_at"));
					book.setDeletedAt(rs.getTimestamp("deleted_at"));

					BookInfo info = new BookInfo();
					info.setId(rs.getInt("book_info_id"));
					info.setIsbn(rs.getString("isbn"));
					info.setTitle(rs.getString("title"));
					info.setAuthorId(rs.getInt("author_id"));
					info.setPublisherId(rs.getInt("publisher_id"));
					info.setCategoryId(rs.getInt("category_id"));
					info.setImageUrl(rs.getString("imageUrl"));
					
					book.setBookInfo(info);
					list.add(book);
				}
			}
		} catch (SQLException e) {
			System.err.println("BookDAO.searchBooksでエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 条件に合致する図書情報を複数条件から検索する
	 */
	public List<Book> search(String title, String isbn, String author, String category) {
		List<Book> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT b.id, b.book_info_id, b.book_number, b.layout_id, b.created_at, b.updated_at, b.deleted_at, ");
		sql.append("bi.isbn, bi.title, bi.author_id, bi.publisher_id, bi.category_id, bi.imageUrl ");
		sql.append("FROM book b ");
		sql.append("INNER JOIN book_info bi ON b.book_info_id = bi.id ");
		sql.append("LEFT JOIN author a ON bi.author_id = a.id ");
		sql.append("LEFT JOIN category c ON bi.category_id = c.id ");
		sql.append("WHERE b.deleted_at IS NULL ");
		
		if (title != null && !title.isEmpty()) {
			sql.append("AND bi.title LIKE ? ");
		}
		if (isbn != null && !isbn.isEmpty()) {
			sql.append("AND bi.isbn LIKE ? ");
		}
		if (author != null && !author.isEmpty()) {
			sql.append("AND a.name LIKE ? ");
		}
		if (category != null && !category.isEmpty() && !category.equals("すべて")) {
			sql.append("AND c.name = ? ");
		}
		
		sql.append("ORDER BY b.id DESC");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
			int paramIndex = 1;
			
			if (title != null && !title.isEmpty()) {
				pstmt.setString(paramIndex++, "%" + title + "%");
			}
			if (isbn != null && !isbn.isEmpty()) {
				pstmt.setString(paramIndex++, "%" + isbn + "%");
			}
			if (author != null && !author.isEmpty()) {
				pstmt.setString(paramIndex++, "%" + author + "%");
			}
			if (category != null && !category.isEmpty() && !category.equals("すべて")) {
				pstmt.setString(paramIndex++, category);
			}
			
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Book book = new Book();
					book.setId(rs.getInt("id"));
					book.setBookInfoId(rs.getInt("book_info_id"));
					book.setBookNumber(rs.getInt("book_number"));
					book.setLayoutId(rs.getInt("layout_id"));
					book.setCreatedAt(rs.getTimestamp("created_at"));
					book.setUpdatedAt(rs.getTimestamp("updated_at"));
					book.setDeletedAt(rs.getTimestamp("deleted_at"));

					BookInfo info = new BookInfo();
					info.setId(rs.getInt("book_info_id"));
					info.setIsbn(rs.getString("isbn"));
					info.setTitle(rs.getString("title"));
					info.setAuthorId(rs.getInt("author_id"));
					info.setPublisherId(rs.getInt("publisher_id"));
					info.setCategoryId(rs.getInt("category_id"));
					info.setImageUrl(rs.getString("imageUrl"));
					
					book.setBookInfo(info);
					list.add(book);
				}
			}
		} catch (SQLException e) {
			System.err.println("BookDAO.searchでエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}
}