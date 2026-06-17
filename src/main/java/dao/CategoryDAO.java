package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Category;

// 分類DAO
public class CategoryDAO extends BaseDAO {

	public CategoryDAO(Connection conn) {
		super(conn);
	}
	
	// IDから分類情報を1件取得する
	public Category findById(int id) {
		Category category = null;
		String sql = "SELECT id, name, sort_order, created_at, updated_at, deleted_at FROM category WHERE id = ? AND deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					category = new Category();
					category.setId(rs.getInt("id"));
					category.setName(rs.getString("name"));
					category.setSortOrder(rs.getInt("sort_order"));
					category.setCreatedAt(rs.getTimestamp("created_at"));
					category.setUpdatedAt(rs.getTimestamp("updated_at"));
					category.setDeletedAt(rs.getTimestamp("deleted_at"));
				}
			}
		} catch (SQLException e) {
			System.err.println("CategoryDAO.findByIdの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return category;
	}
	
	// 全ての分類情報を並び順通りに取得する
	public List<Category> findAll() {
		List<Category> list = new ArrayList<>();
		String sql = "SELECT id, name, sort_order, created_at, updated_at, deleted_at FROM category WHERE deleted_at IS NULL ORDER BY sort_order ASC";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				Category category = new Category();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
				category.setSortOrder(rs.getInt("sort_order"));
				category.setCreatedAt(rs.getTimestamp("created_at"));
				category.setUpdatedAt(rs.getTimestamp("updated_at"));
				category.setDeletedAt(rs.getTimestamp("deleted_at"));
				
				list.add(category);
			}
		} catch (SQLException e) {
			System.err.println("CategoryDAO.findAllの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}
}