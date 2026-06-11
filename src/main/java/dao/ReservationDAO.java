package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Reservation;


public class ReservationDAO extends BaseDAO{

	public ReservationDAO(Connection conn) {
		super(conn);
		
	}
	
	public Reservation findById(int id){
		Reservation reservation = null;
		
		String sql = "SELECT * FROM reservation WHERE id = ? AND deleted_at IS NULL";
		try (PreparedStatement pstms = conn.prepareStatement(sql)){
			pstms.setInt(1, id);
			
			try(ResultSet rs = pstms.executeQuery()){
				if(rs.next()) {
					reservation = new Reservation();
					
					reservation.setId(rs.getInt("id"));
					reservation.setUserId(rs.getInt("user_id"));
					reservation.setBookInfoId(rs.getInt("book_info_id"));
					reservation.setBookId(rs.getInt("book_id"));
					reservation.setReservationDate(rs.getTimestamp("reservation_date"));
					reservation.setExpireDate(rs.getTimestamp("expire_date"));
					reservation.setStatus(rs.getInt("status"));
					reservation.setCreatedAt(rs.getTimestamp("created_at"));
					reservation.setUpdatedAt(rs.getTimestamp("updated_at"));
					reservation.setDeletedAt(rs.getTimestamp("deleted_at"));
					
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reservation;
	}
	
	public List<Reservation>findAll(){
		List<Reservation> list = new ArrayList<>();
		
		String sql = "SELECT * FROM reservation WHERE deleted_at IS NULL ORDER BY reservation_date ASC";//修正予定
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()) {
				Reservation reservation = new Reservation();
				
				reservation.setId(rs.getInt("id"));
				reservation.setUserId(rs.getInt("user_id"));
				reservation.setBookInfoId(rs.getInt("book_info_id"));
				reservation.setBookId(rs.getInt("book_id"));
				reservation.setReservationDate(rs.getTimestamp("reservation_date"));
				reservation.setExpireDate(rs.getTimestamp("expire_date"));
				reservation.setStatus(rs.getInt("status"));
				reservation.setCreatedAt(rs.getTimestamp("created_at"));
				reservation.setUpdatedAt(rs.getTimestamp("updated_at"));
				reservation.setDeletedAt(rs.getTimestamp("deleted_at"));
                
                list.add(reservation);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
