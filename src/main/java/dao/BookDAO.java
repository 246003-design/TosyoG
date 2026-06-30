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
					book.setUpdatedAt(rs.getTimestamp("updated_at"));
					book.setDeletedAt(rs.getTimestamp("deleted_at"));
					
					return Optional.of(book);
				}
			}
		} catch (SQLException e) {
			System.err.println("BookDAO.findByIdの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	// 図書情報の全取得を行う
	public List<Book> findAll() {
		List<Book> list = new ArrayList<>();
		String sql = "SELECT id, book_info_id, book_number, layout_id, created_at, updated_at, deleted_at "
				+ "FROM book WHERE deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
			 ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setBookInfoId(rs.getInt("book_info_id"));
				book.setBookNumber(rs.getInt("book_number"));
				book.setLayoutId(rs.getInt("layout_id"));
				book.setCreatedAt(rs.getTimestamp("created_at"));
				book.setUpdatedAt(rs.getTimestamp("updated_at"));
				book.setDeletedAt(rs.getTimestamp("deleted_at"));
				
				list.add(book);
			}
		} catch (SQLException e) {
			System.err.println("BookDAO.findAllの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 画面の4つの条件（タイトル、ISBN、著者、分類）で図書を検索する
	 */
	public List<Book> searchBooks(String title, String isbn, String author, String category) {
		List<Book> list = new ArrayList<>();
		
		// 未入力の項目（空文字）や分類の「すべて」を無視できるようにOR条件を組み込んだSQL
		String sql = "SELECT b.id, b.book_info_id, b.book_number, b.layout_id, b.created_at "
				   + "FROM book b "
				   + "INNER JOIN book_info bi ON b.book_info_id = bi.id "
				   + "WHERE (? = '' OR bi.title LIKE ?) "
				   + "  AND (? = '' OR bi.isbn LIKE ?) "
				   + "  AND (? = '' OR bi.author LIKE ?) "
				   + "  AND (? = 'すべて' OR ? = '' OR bi.category = ?) "
				   + "  AND b.deleted_at IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 各項目に対して、空文字判定用と部分一致検索用の値を正しく順番にセット
			pstmt.setString(1, title);
			pstmt.setString(2, "%" + title + "%");
			
			pstmt.setString(3, isbn);
			pstmt.setString(4, "%" + isbn + "%");
			
			pstmt.setString(5, author);
			pstmt.setString(6, "%" + author + "%");
			
			// 分類用（すべて選択時の判定、空文字判定、一致判定）
			pstmt.setString(7, category);
			pstmt.setString(8, category);
			pstmt.setString(9, category);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Book book = new Book();
					book.setId(rs.getInt("id"));
					book.setBookInfoId(rs.getInt("book_info_id"));
					book.setBookNumber(rs.getInt("book_number"));
					book.setLayoutId(rs.getInt("layout_id"));
					book.setCreatedAt(rs.getTimestamp("created_at"));
					
					list.add(book);
				}
			}
		} catch (SQLException e) {
			System.err.println("図書の複合検索中にエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}
}