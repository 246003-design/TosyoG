package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Publisher;

public class PublisherDAO extends BaseDAO{
//出版社DAO
	public PublisherDAO(Connection conn) {
		super(conn);
	}
//IDから出版社情報を1件取得する
	public Optional<Publisher>findById(int id){
		
		String sql ="SELECT id, name, createdAt, updatedAt, deletedAt"
				+ "FROM publisher WHERE id = ? deletedAt IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, id);
			
			try(ResultSet rs = pstmt.executeQuery()){
				
				if(rs.next()) {
					Publisher publisher = new Publisher();
					publisher.setId(rs.getInt("id"));
					publisher.setName(rs.getString("name"));
					publisher.setCreatedAt(rs.getTimestamp("createdAt"));
					publisher.setUpdatedAt(rs.getTimestamp("updatedAt"));
					publisher.setDeletedAt(rs.getTimestamp("deletedAt"));
					
					return Optional.of(publisher);
				}
			}
			}catch (SQLException e) {
				System.err.println("findByIdの実行中にエラーが発生しました。");
				e.printStackTrace();
		}
		return Optional.empty();
	}
//出版社情報と一致する情報の取得を行う		
	public List<Publisher>findAll(){
		List<Publisher> list = new ArrayList<>();
		String sql ="SELECT id, name, createdAt, updatedAt, deletedAt"
				+ "FROM id WEHER deletedAt IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()){
			Publisher publisher = new Publisher();
			publisher.setId(rs.getInt("id"));
			publisher.setName(rs.getString("name"));
			publisher.setCreatedAt(rs.getTimestamp("createdAt"));
			publisher.setUpdatedAt(rs.getTimestamp("updatedAt"));
			publisher.setDeletedAt(rs.getTimestamp("deletedAt"));

			list.add(publisher);
		}catch (SQLException e) {
            System.err.println("findAllの実行中にエラーが発生しました。");
            e.printStackTrace();
		}
		return list;
	}
}
