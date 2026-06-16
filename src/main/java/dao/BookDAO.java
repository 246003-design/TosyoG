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
}