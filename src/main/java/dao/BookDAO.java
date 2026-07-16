package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dto.BookListDto;
import entity.Book;
import entity.BookInfo;

// 図書DAO、図書詳細情報も取得できる
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

	// 蔵書情報更新（bookテーブル と book_infoテーブル）をするメソッド
		public boolean update(Book book) {
			boolean result = false;
			
			// 1. book_info テーブルの更新用SQL
			String sqlBookInfo = "UPDATE book_info SET isbn = ?, title = ?, author_id = ?, publisher_id = ?, imageUrl = ? WHERE id = ?";
			// 2. book テーブルの更新用SQL
			String sqlBook = "UPDATE book SET book_number = ?, layout_id = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

			try {
				// トランザクション開始（2つのテーブルを更新するため、途中で失敗したら元に戻せるようにする）
				conn.setAutoCommit(false);

				// ① book_info テーブルの更新
				try (PreparedStatement pstmtInfo = conn.prepareStatement(sqlBookInfo)) {
					pstmtInfo.setString(1, book.getBookInfo().getIsbn());
					pstmtInfo.setString(2, book.getBookInfo().getTitle());
					pstmtInfo.setInt(3, book.getBookInfo().getAuthorId());
					pstmtInfo.setInt(4, book.getBookInfo().getPublisherId());
					pstmtInfo.setString(5, book.getBookInfo().getImageUrl());
					pstmtInfo.setInt(6, book.getBookInfoId()); // WHERE id = ?
					pstmtInfo.executeUpdate();
				}

				// ② book テーブルの更新
				try (PreparedStatement pstmtBook = conn.prepareStatement(sqlBook)) {
					pstmtBook.setInt(1, book.getBookNumber());
					pstmtBook.setInt(2, book.getLayoutId());
					pstmtBook.setInt(3, book.getId()); // WHERE id = ?
					pstmtBook.executeUpdate();
				}

				// 両方成功したらコミット（確定）
				conn.commit();
				result = true;

			} catch (SQLException e) {
				System.err.println("BookDAO.updateでエラーが発生しました。ロールバックします。");
				e.printStackTrace();
				try {
					// エラーが起きた場合はロールバック（変更を取り消し）
					conn.rollback();
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			} finally {
				try {
					// オートコミットを元に戻す
					conn.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return result;
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

	public List<Book> search(String title, String isbn, String author, String category) {
		List<Book> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		// 🔴 SELECT句から book_info や author などの詳細情報のカラムを削除しました
		sql.append("SELECT b.id, b.book_info_id, b.book_number, b.layout_id, b.created_at, b.updated_at, b.deleted_at ");
		sql.append("FROM book b ");
		sql.append("INNER JOIN book_info bi ON b.book_info_id = bi.id ");
		sql.append("LEFT JOIN author a ON bi.author_id = a.id ");
		sql.append("LEFT JOIN category c ON bi.category_id = c.id ");
		sql.append("WHERE b.deleted_at IS NULL ");
		
		// 💡 検索の絞り込みを行うために、WHERE句の結合条件は残しています
		if (title != null && !title.isEmpty()) {
			sql.append("AND bi.title LIKE ? ");
		}
		if (isbn != null && !isbn.isEmpty()) {
			sql.append("AND bi.isbn LIKE ? ");
		}
		if (author != null && !author.isEmpty()) {
			sql.append("AND a.name LIKE ? ");
		}
		if (category != null && !category.trim().isEmpty() && !category.equals("すべて")) {
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
			if (category != null && !category.trim().isEmpty() && !category.equals("すべて")) {
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

					// 🔴 BookInfoオブジェクトの生成、および book.setBookInfo(info) の処理を削除しました
					
					list.add(book);
				}
			}
		} catch (SQLException e) {
			System.err.println("BookDAO.searchでエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 💡 現在予約中（または受取待ち）の図書IDの一覧を取得する
	 */
	public List<Integer> getReservedBookIds() {
		List<Integer> list = new ArrayList<>();
		String sql = "SELECT book_id FROM reservation WHERE status IN (0, 1)";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
			 ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(rs.getInt("book_id"));
			}
		} catch (SQLException e) {
			System.err.println("BookDAO.getReservedBookIdsでエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}
	
	public List<BookListDto> searchBooksForManagement(String keyword) {
	    List<BookListDto> list = new ArrayList<>();

	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT ");
	    sql.append("b.id, b.book_number, b.layout_id, ");
	    sql.append("bi.isbn, bi.title, bi.author_id, bi.publisher_id, bi.category_id, bi.imageUrl, ");
	    sql.append("a.name AS author_name, ");
	    sql.append("p.name AS publisher_name, ");
	    sql.append("c.name AS category_name, ");
	    sql.append("l.location AS layout_location ");
	    sql.append("FROM book b ");
	    sql.append("INNER JOIN book_info bi ON b.book_info_id = bi.id ");
	    sql.append("LEFT JOIN author a ON bi.author_id = a.id ");
	    sql.append("LEFT JOIN publisher p ON bi.publisher_id = p.id ");
	    sql.append("LEFT JOIN category c ON bi.category_id = c.id ");
	    sql.append("LEFT JOIN layout l ON b.layout_id = l.id ");
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
	                BookListDto dto = new BookListDto();

	                // ID系
	                dto.setId(rs.getInt("id"));
	                dto.setBookNumber(rs.getInt("book_number"));
	                dto.setLayoutId(rs.getInt("layout_id"));
	                dto.setAuthorId(rs.getInt("author_id"));
	                dto.setPublisherId(rs.getInt("publisher_id"));
	                dto.setCategoryId(rs.getInt("category_id"));

	                // 文字列(book_info由来)
	                dto.setIsbn(rs.getString("isbn"));
	                dto.setTitle(rs.getString("title"));
	                dto.setImageUrl(rs.getString("imageUrl"));

	                // 表示用の名前(JOIN由来、ASで付けたエイリアス名で取得)
	                dto.setAuthorName(rs.getString("author_name"));
	                dto.setPublisherName(rs.getString("publisher_name"));
	                dto.setCategoryName(rs.getString("category_name"));
	                dto.setLayoutLocation(rs.getString("layout_location"));

	                list.add(dto);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("BookDAO.searchBooksForManagementでエラーが発生しました。");
	        e.printStackTrace();
	    }
	    return list;
	}
}