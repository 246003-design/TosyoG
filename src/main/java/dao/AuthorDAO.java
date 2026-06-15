package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Author;

public class AuthorDAO extends BaseDAO{

	public AuthorDAO(Connection conn) {
		super(conn);
		
	}
//著者IDから著者情報を取得
	public Optional<Author>findIById(int id){
		
		String sql ="SELECT id,name,createdAt,updatedAt,deletedAt"
					+"FROM author WEHER id =? deledAt IS NULL";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, id);
			
			try(ResultSet rs = pstmt.executeQuery()){
				
				if(rs.next()) {
					Author author = new Author();
					author.setId(rs.getInt("id"));
					author.setName(rs.getString("name"));
					author.setCreatedAt(rs.getTimestamp("createAt"));
					author.setUpdatedAt(rs.getTimestamp("updatedAt"));
					author.setDeletedAt(rs.getTimestamp("deletedAt"));
					
					return Optional.of(author);
				}
			}
		}catch (SQLException e) {
			System.err.println("findByIdの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
//著者テーブルの全情報を取得する
	public List<Author>findAll(){
		List<Author>list = new ArrayList<>();
		
		String sql = "SELECT id,name,createdAt,updatedAt,deletedAt"
				+"FROM author WEHER deledAt IS NULL";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()) {
				Author author = new Author();
				author.setId(rs.getInt("id"));
				author.setName(rs.getString("name"));
				author.setCreatedAt(rs.getTimestamp("createAt"));
				author.setUpdatedAt(rs.getTimestamp("updatedAt"));
				author.setDeletedAt(rs.getTimestamp("deletedAt"));
				
				list.add(author);
			}
	}catch(SQLException e) {
		System.err.println("findAllの実行中にエラーが発生しました。");
		e.printStackTrace();
	}
		return list;
	}
}
