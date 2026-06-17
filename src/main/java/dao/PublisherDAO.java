package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Publisher;

// 出版社DAO
public class PublisherDAO extends BaseDAO {

	public PublisherDAO(Connection conn) {
		super(conn);
	}

	// IDから出版社情報を1件取得する
	public Optional<Publisher> findById(int id) {
		String sql = "SELECT id, name, created_at, updated_at, deleted_at FROM publisher WHERE id = ? AND deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Publisher publisher = new Publisher();
					publisher.setId(rs.getInt("id"));
					publisher.setName(rs.getString("name"));
					publisher.setCreatedAt(rs.getTimestamp("created_at"));
					publisher.setUpdatedAt(rs.getTimestamp("updated_at"));
					publisher.setDeletedAt(rs.getTimestamp("deleted_at"));
					
					return Optional.of(publisher);
				}
			}
		} catch (SQLException e) {
			System.err.println("PublisherDAO.findByIdの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return Optional.empty();
	}

	// 出版社情報と一致する情報の取得を行う        
	public List<Publisher> findAll() {
		List<Publisher> list = new ArrayList<>();
		String sql = "SELECT id, name, created_at, updated_at, deleted_at FROM publisher WHERE deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				Publisher publisher = new Publisher();
				publisher.setId(rs.getInt("id"));
				publisher.setName(rs.getString("name"));
				publisher.setCreatedAt(rs.getTimestamp("created_at"));
				publisher.setUpdatedAt(rs.getTimestamp("updated_at"));
				publisher.setDeletedAt(rs.getTimestamp("deleted_at"));

				list.add(publisher);
			}
		} catch (SQLException e) {
			System.err.println("PublisherDAO.findAllの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}
}