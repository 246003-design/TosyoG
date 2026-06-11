package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Lend;


public class LendDAO extends BaseDAO{

	public LendDAO(Connection conn) {
		super(conn);
	}
	

	
	public Lend findById(int id){
		Lend lend = null;
		
		String sql = "SELECT * FROM lend WHERE id = ? AND deleted_at IS NULL";
		try (PreparedStatement pstms = conn.prepareStatement(sql)){
			pstms.setInt(1, id);
			
			try(ResultSet rs = pstms.executeQuery()){
				if(rs.next()) {
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
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lend;
	}
	
	public List<Lend>findAll(){
		List<Lend> list = new ArrayList<>();
		
		String sql = "SELECT * FROM lend WHERE deleted_at IS NULL ORDER BY due_date ASC"; //修正予定orderbyどうするか
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()) {
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
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
//rs.getInt
//rs.getString
//getTimestamp
