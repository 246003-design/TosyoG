package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Lend;

// 貸出DAO
public class LendDAO extends BaseDAO {

	public LendDAO(Connection conn) {
		super(conn);
	}
	
	// IDから貸出情報を1件取得する
	public Lend findById(int id) {
		Lend lend = null;
		String sql = "SELECT id, user_id, book_id, lend_date, due_date, return_date, status, created_at, updated_at, deleted_at "
				+ "FROM lend WHERE id = ? AND deleted_at IS NULL";
				
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					lend = new Lend();
					lend.setId(rs.getInt("id"));
					lend.setUserId(rs.getInt("user_id"));
					lend.setBookId(rs.getInt("book_id"));
					lend.setLendDate(rs.getTimestamp("lend_date"));
					lend.setDueDate(rs.getTimestamp("due_date"));
					lend.setReturnDate(rs.getTimestamp("return_date"));
					lend.setStatus(rs.getInt("status"));
					lend.setCreatedAt(rs.getTimestamp("created_at"));
					lend.setUpdatedAt(rs.getTimestamp("updated_at"));
					lend.setDeletedAt(rs.getTimestamp("deleted_at"));
				}
			}
		} catch (SQLException e) {
			System.err.println("LendDAO.findByIdの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return lend;
	}
	
	// すべての貸出情報を取得する
	public List<Lend> findAll() {
		List<Lend> list = new ArrayList<>();
		String sql = "SELECT id, user_id, book_id, lend_date, due_date, return_date, status, created_at, updated_at, deleted_at "
				+ "FROM lend WHERE deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				Lend lend = new Lend();
				lend.setId(rs.getInt("id"));
				lend.setUserId(rs.getInt("user_id"));
				lend.setBookId(rs.getInt("book_id"));
				lend.setLendDate(rs.getTimestamp("lend_date"));
				lend.setDueDate(rs.getTimestamp("due_date"));
				lend.setReturnDate(rs.getTimestamp("return_date"));
				lend.setStatus(rs.getInt("status"));
				lend.setCreatedAt(rs.getTimestamp("created_at"));
				lend.setUpdatedAt(rs.getTimestamp("updated_at"));
				lend.setDeletedAt(rs.getTimestamp("deleted_at"));
                
				list.add(lend);
			}
		} catch (SQLException e) {
			System.err.println("LendDAO.findAllの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Lend> findByUserId(int userId) {
		List<Lend> list = new ArrayList<>();
		String sql = "SELECT book_id, lend_date, due_date FROM lend WHERE user_id = ? AND return_date IS NULL AND deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, userId);

			try(ResultSet rs = pstmt.executeQuery()) {
			
				while (rs.next()) {
					Lend lend = new Lend();
					lend.setBookId(rs.getInt("book_id"));
					lend.setLendDate(rs.getTimestamp("lend_date"));
					lend.setDueDate(rs.getTimestamp("due_date"));
	                
					list.add(lend);
				}
			}
		} catch (SQLException e) {
			System.err.println("LendDAO.findByUserIdの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}
}