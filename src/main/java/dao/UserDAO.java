package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	// 2. 利用者の新規登録処理
	public boolean insert(User user) {
		boolean result = false;
		String sql = "INSERT INTO user (name, password, role, status, borrow_count) VALUES (?, ?, ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getRole());
			pstmt.setInt(4, user.getStatus());
			
			//更新された情報の数
			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				result = true;
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
		String sql = "UPDATE user SET name = ?, password = ?, role = ?, status = ?, borrow_count = ? WHERE id = ? AND deleted_at IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getRole());
			pstmt.setInt(4, user.getStatus());
			pstmt.setInt(5, user.getBorrowCount());
			pstmt.setInt(6, user.getId());

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
}