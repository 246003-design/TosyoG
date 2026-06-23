package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ReservationDTO;
import entity.Reservation;

// 予約DAO(ID検索、一覧取得、予約情報の登録更新が可能）
public class ReservationDAO extends BaseDAO {

	public ReservationDAO(Connection conn) {
		super(conn);
	}
	
	// IDから予約情報を1件取得する
	public Reservation findById(int id) {
		Reservation reservation = null;
		String sql = "SELECT id, user_id, book_info_id, book_id, reservation_date, expire_date, status, created_at, updated_at, deleted_at "
				+ "FROM reservation WHERE id = ? AND deleted_at IS NULL";
				
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
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
		} catch (SQLException e) {
			System.err.println("ReservationDAO.findByIdの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return reservation;
	}
	
	// すべての予約情報を取得する
	public List<Reservation> findAll() {
		List<Reservation> list = new ArrayList<>();
		String sql = "SELECT id, user_id, book_info_id, book_id, reservation_date, expire_date, status, created_at, updated_at, deleted_at "
				+ "FROM reservation WHERE deleted_at IS NULL ORDER BY reservation_date ASC";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
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
		} catch (SQLException e) {
			System.err.println("ReservationDAO.findAllの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}

	// ★DTOから直接情報を取得して新規登録する形に修正
		public boolean insert(ReservationDTO newRes) { // 型を ReservationDTO に変更
			boolean result = false;
			String sql = "INSERT INTO reservation (user_id, book_info_id, book_id, reservation_date, status, created_at) "
					+ "VALUES (?, ?, ?, NOW(), ?, NOW())";

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				// newRes（DTOの箱）からゲッターで値を取得
				pstmt.setInt(1, newRes.getUserId());
				pstmt.setInt(2, newRes.getBookInfoId());
				pstmt.setInt(3, newRes.getBookId());
				pstmt.setInt(4, newRes.getStatus());

				int rows = pstmt.executeUpdate();
				if (rows > 0) {
					result = true;
				}
			} catch (SQLException e) {
				System.err.println("ReservationDAO.insertの実行中にエラーが発生しました。");
				e.printStackTrace();
			}
			return result;
		}

		// ★DTOから直接情報を取得して更新（キャンセル等）する形に修正
		public boolean update(ReservationDTO r) { // 型を ReservationDTO に変更
			boolean result = false;
			String sql = "UPDATE reservation SET status = ?, updated_at = NOW() WHERE id = ? AND deleted_at IS NULL";

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				// r（DTOの箱）からゲッターで値を取得
				pstmt.setInt(1, r.getStatus());
				pstmt.setInt(2, r.getId());

				int rows = pstmt.executeUpdate();
				if (rows > 0) {
					result = true;
				}
			} catch (SQLException e) {
				System.err.println("ReservationDAO.updateの実行中にエラーが発生しました。");
				e.printStackTrace();
			}
			return result;
		}
}