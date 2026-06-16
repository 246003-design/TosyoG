package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Layout;

// 配置DAO
public class LayoutDAO extends BaseDAO {

	public LayoutDAO(Connection conn) {
		super(conn);
	}

	// IDから配置情報を1件取得する
	public Optional<Layout> findById(int id) {
		String sql = "SELECT id, location, created_at, updated_at, deleted_at FROM layout WHERE id = ? AND deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Layout layout = new Layout();
					layout.setId(rs.getInt("id"));
					layout.setLocation(rs.getString("location"));
					layout.setCreatedAt(rs.getTimestamp("created_at"));
					layout.setUpdatedAt(rs.getTimestamp("updated_at"));
					layout.setDeletedAt(rs.getTimestamp("deleted_at"));
					
					return Optional.of(layout);
				}
			}
		} catch (SQLException e) {
			System.err.println("LayoutDAO.findByIdの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return Optional.empty();
	}

	// 全ての配置情報を取得する
	public List<Layout> findAll() {
		List<Layout> list = new ArrayList<>();
		String sql = "SELECT id, location, created_at, updated_at, deleted_at FROM layout WHERE deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				Layout layout = new Layout();
				layout.setId(rs.getInt("id"));
				layout.setLocation(rs.getString("location"));
				layout.setCreatedAt(rs.getTimestamp("created_at"));
				layout.setUpdatedAt(rs.getTimestamp("updated_at"));
				layout.setDeletedAt(rs.getTimestamp("deleted_at"));
				
				list.add(layout);
			}
		} catch (SQLException e) {
			System.err.println("LayoutDAO.findAllの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}
}