package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.User;

// 利用者DAO
public class UserDAO extends BaseDAO {

	public UserDAO(Connection conn) {
		super(conn);
	}

	// 1. ログイン認証処理
	public User login(int id, String password) {
		User user = null;
		String sql = "SELECT id, name, password, role, status, borrow_count, created_at, updated_at, deleted_at "
				+ "FROM user WHERE id = ? AND password = ? AND deleted_at IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.setString(2, password);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setRole(rs.getInt("role"));
					user.setStatus(rs.getInt("status"));
					user.setBorrowCount(rs.getInt("borrow_count"));
					user.setCreatedAt(rs.getTimestamp("created_at"));
					user.setUpdatedAt(rs.getTimestamp("updated_at"));
					user.setDeletedAt(rs.getTimestamp("deleted_at"));
				}
			}
		} catch (SQLException e) {
			System.err.println("UserDAO.loginの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return user;
	}

	// 2. 利用者の新規登録処理 エラーは-1
	public int insert(User user) {
		int result = -1;
		String sql = "INSERT INTO user (name, password, role, status) VALUES (?, ?, ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getRole());
			pstmt.setInt(4, user.getStatus());
			
			//更新された情報の数
			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				try(ResultSet rs = pstmt.getGeneratedKeys()){
					if(rs.next()) {
						result = rs.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("UserDAO.insertの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return result;
	}


	// 3. 利用者の情報更新処理
	public boolean update(User user) {
		boolean result = false;
		String sql = "UPDATE user SET name = ?, role = ?, status = ? WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, user.getName());
	        pstmt.setInt(2, user.getRole());
	        pstmt.setInt(3, user.getStatus());
	        pstmt.setInt(4, user.getId());  

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				result = true;
			}
		} catch (SQLException e) {
			System.err.println("UserDAO.updateの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return result;
	}
	
	//パスワード更新があったらこっちつかう
	public boolean updateWithPassword (User user) {
		boolean result = false;
		String sql = "UPDATE user SET name = ?, password = ?, role = ?, status = ? WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, user.getName());
	        pstmt.setString(2, user.getPassword());
	        pstmt.setInt(3, user.getRole());
	        pstmt.setInt(4, user.getStatus());
	        pstmt.setInt(5, user.getId());  

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				result = true;
			}
		} catch (SQLException e) {
			System.err.println("UserDAO.updateWithPasswordの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return result;
	}
	
	
	// 4. 利用者の論理削除処理
	public boolean delete(int id) {
		boolean result = false;
		String sql = "UPDATE user SET deleted_at = NOW() WHERE id = ? AND deleted_at IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				result = true;
			}
		} catch (SQLException e) {
			System.err.println("UserDAO.deleteの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return result;
	}

	// 5. 利用者の1件検索処理（主キー指定）
	public User findById(int id) {
		User user = null;
		String sql = "SELECT id, name, password, role, status, borrow_count, created_at, updated_at, deleted_at "
				+ "FROM user WHERE id = ? AND deleted_at IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setRole(rs.getInt("role"));
					user.setStatus(rs.getInt("status"));
					user.setBorrowCount(rs.getInt("borrow_count"));
					user.setCreatedAt(rs.getTimestamp("created_at"));
					user.setUpdatedAt(rs.getTimestamp("updated_at"));
					user.setDeletedAt(rs.getTimestamp("deleted_at"));
				}
			}
		} catch (SQLException e) {
			System.err.println("UserDAO.findByIdの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return user;
	}

	// 6. 利用者の全件検索処理
	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		String sql = "SELECT id, name, password, role, status, borrow_count, created_at, updated_at, deleted_at "
				+ "FROM user WHERE deleted_at IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getInt("role"));
				user.setStatus(rs.getInt("status"));
				user.setBorrowCount(rs.getInt("borrow_count"));
				user.setCreatedAt(rs.getTimestamp("created_at"));
				user.setUpdatedAt(rs.getTimestamp("updated_at"));
				user.setDeletedAt(rs.getTimestamp("deleted_at"));

				list.add(user);
			}
		} catch (SQLException e) {
			System.err.println("UserDAO.findAllの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}

	// id,nameで検索	
	public List<User> searchUsers(String keyword) {
		List<User> list = new ArrayList<>();
		String sql;

		if (keyword == null || keyword.isEmpty()) {
			sql = "SELECT id, name, role, status FROM user WHERE id = ? OR name LIKE ? ORDER BY id;";
			return list;
		}

		boolean isNumeric = keyword.matches("\\d+");

		if (isNumeric) {
			sql = "SELECT id, name, role, status FROM user WHERE id = ? OR name LIKE ? ORDER BY id;";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, Integer.parseInt(keyword));
				pstmt.setString(2, "%" + keyword + "%");

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						User user = new User();
						user.setId(rs.getInt("id"));
						user.setName(rs.getString("name"));
						user.setRole(rs.getInt("role"));
						user.setStatus(rs.getInt("status"));

						list.add(user);
					}
				}
			} catch (SQLException e) {
				System.err.println("LendDAO.findByUserIdの実行中にエラーが発生しました。");
				e.printStackTrace();
			}
		} else {
			sql = "SELECT id, name, role, status FROM user WHERE name LIKE ? ORDER BY id;";

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, "%" + keyword + "%");

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						User user = new User();
						user.setId(rs.getInt("id"));
						user.setName(rs.getString("name"));
						user.setRole(rs.getInt("role"));
						user.setStatus(rs.getInt("status"));

						list.add(user);
					}
				}
			} catch (SQLException e) {
				System.err.println("LendDAO.findByUserIdの実行中にエラーが発生しました。");
				e.printStackTrace();
			}
		}
		return list;
	}
	
//	// 利用者の貸出冊数(borrow_count)を+1するメソッド
//    public void incrementBorrowCount(int userId) throws SQLException {
//        String sql = "UPDATE user SET borrow_count = borrow_count + 1, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
//        
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, userId);
//            pstmt.executeUpdate();
//        }
//    }
	
	// 利用者の現在の実際の「貸出数 ＋ 予約数」を計算し、borrow_countを正確な値に上書き（同期）するメソッド
	public void syncBorrowCount(int userId) throws SQLException {
	    // lendテーブル（未返却）と reservationテーブル（予約中・取置中: status 1, 2）の数を合計し、
	    // その結果をuserテーブルのborrow_countにセットするSQL
	    String sql = "UPDATE user SET borrow_count = "
	               + "((SELECT COUNT(*) FROM lend WHERE user_id = ? AND return_date IS NULL AND deleted_at IS NULL) "
	               + "+ (SELECT COUNT(*) FROM reservation WHERE user_id = ? AND status IN (1, 2) AND deleted_at IS NULL)), "
	               + "updated_at = CURRENT_TIMESTAMP "
	               + "WHERE id = ?";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, userId); // lend用（SELECT）のuserId
	        pstmt.setInt(2, userId); // reservation用（SELECT）のuserId
	        pstmt.setInt(3, userId); // UPDATE（WHERE）用のuserId
	        pstmt.executeUpdate();
	    }
	}
}