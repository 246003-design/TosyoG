package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Category;

public class CategoryDAO extends BaseDAO{

	public CategoryDAO(Connection conn) {
		super(conn);
	}
	

	
	public Category findById(int id){
		Category category = null;
		
		String sql = "SELECT * FROM category WHERE id = ? AND deleted_at IS NULL";
		try (PreparedStatement pstms = conn.prepareStatement(sql)){
			pstms.setInt(1, id);
			
			try(ResultSet rs = pstms.executeQuery()){
				if(rs.next()) {
					category = new Category();
					
					category.setId(rs.getInt("id"));
					category.setName(rs.getString("name"));
					category.setSortOrder(rs.getInt("sort_order"));
					category.setCreatedAt(rs.getTimestamp("created_at"));
					category.setUpdatedAt(rs.getTimestamp("updated_at"));
					category.setDeletedAt(rs.getTimestamp("deleted_at"));
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return category;
	}
	
	public List<Category>findAll(){
		List<Category> list = new ArrayList<>();
		
		String sql = "SELECT * FROM category WHERE deleted_at IS NULL ORDER BY sort_order ASC";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()) {
				Category category = new Category();
				
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
				category.setSortOrder(rs.getInt("sort_order"));
				category.setCreatedAt(rs.getTimestamp("created_at"));
				category.setUpdatedAt(rs.getTimestamp("updated_at"));
				category.setDeletedAt(rs.getTimestamp("deleted_at"));
                
                list.add(category);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
