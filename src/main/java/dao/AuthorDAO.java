package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Author;

// 著者DAO
public class AuthorDAO extends BaseDAO {

	public AuthorDAO(Connection conn) {
		super(conn);
	}

	// 著者IDから著者情報を取得
	public Optional<Author> findById(int id) {
		String sql = "SELECT id, name, created_at, updated_at, deleted_at FROM author WHERE id = ? AND deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Author author = new Author();
					author.setId(rs.getInt("id"));
					author.setName(rs.getString("name"));
					author.setCreatedAt(rs.getTimestamp("created_at"));
					author.setUpdatedAt(rs.getTimestamp("updated_at"));
					author.setDeletedAt(rs.getTimestamp("deleted_at"));
					
					return Optional.of(author);
				}
			}
		} catch (SQLException e) {
			System.err.println("AuthorDAO.findByIdの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	// 著者テーブルの全情報を取得する
	public List<Author> findAll() {
		List<Author> list = new ArrayList<>();
		String sql = "SELECT id, name, created_at, updated_at, deleted_at FROM author WHERE deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				Author author = new Author();
				author.setId(rs.getInt("id"));
				author.setName(rs.getString("name"));
				author.setCreatedAt(rs.getTimestamp("created_at"));
				author.setUpdatedAt(rs.getTimestamp("updated_at"));
				author.setDeletedAt(rs.getTimestamp("deleted_at"));
				
				list.add(author);
			}
		} catch (SQLException e) {
			System.err.println("AuthorDAO.findAllの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}
}