package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entity.User;

	public class UserDAO extends BaseDAO{
		//DBManagerを継承
			public UserDAO(Connection conn) {
				super(conn);
			}
	/**
	 * 1. ログイン認証処理
	 * @param id ユーザーID
	 * @param password パスワード
	 * @return 認証成功時はユーザー情報を入れたEntity、失敗時はnull
	 */
	public User login(int id, String password) {
		User user = null;
		// 削除されていない(deleted_at IS NULL)ユーザーを対象にするのがプロの技！
		String sql = "SELECT * FROM user WHERE id = ? AND password = ? AND deleted_at IS NULL";

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * 2. 新規利用者登録処理
	 * @param user 登録したい情報が入ったEntityの箱
	 * @return 登録成功ならtrue、失敗ならfalse
	 */
	public boolean insert(User user) {
		boolean result = false;
		// idはAUTO_INCREMENT、日付やカウントはデフォルト値があるので、必要な4つだけインサート！
		String sql = "INSERT INTO user (name, password, role, status) VALUES (?, ?, ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getRole());
			pstmt.setInt(4, user.getStatus());

			// 実行された行数が1行以上あれば成功
			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 3. 利用者情報の更新処理（主キー指定）
	 * @param user 更新したい新しい情報と、対象者のidが入ったEntityの箱
	 * @return 更新成功ならtrue、失敗ならfalse
	 */
	public boolean update(User user) {
		boolean result = false;
		// 🔴 主キー（id）を「WHERE句」に指定して、その人だけを狙い撃ちで更新するSQL
		String sql = "UPDATE user SET name = ?, password = ?, role = ?, status = ? WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// SQLの左側にある「?」から順番に番号を割り振ってハメ込む！
			pstmt.setString(1, user.getName());      // 1番目の ? (name)
			pstmt.setString(2, user.getPassword());  // 2番目の ? (password)
			pstmt.setInt(3, user.getRole());         // 3番目の ? (role)
			pstmt.setInt(4, user.getStatus());       // 4番目の ? (status)
			pstmt.setInt(5, user.getId());           // 5番目の ? (id)

			// 実行された行数（アップデートされた件数）を受け取る
			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				result = true; // 1件以上更新されたら成功！
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 4. 利用者の論理削除処理（主キー指定）
	 * @param id 削除したい（退会させたい）利用者のユーザーID
	 * @return 削除（更新）成功ならtrue、失敗ならfalse
	 */
	public boolean delete(int id) {
		boolean result = false;
		// 🔴 データを消すのではなく、deleted_at に現在のシステム日時（CURRENT_TIMESTAMP）をセットするSQL
		String sql = "UPDATE user SET deleted_at = CURRENT_TIMESTAMP WHERE id = ? AND deleted_at IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// SQLにある唯一の「?」に、引数で届いた id をハメ込む！
			pstmt.setInt(1, id);

			// 実行された行数（アップデートされた件数）を受け取る
			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				result = true; // 1件無事に論理削除（更新）されたら成功！
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 5. 利用者の1件検索処理（主キー指定）
	 * @param id 検索したい利用者のユーザーID
	 * @return 見つかった場合はユーザー情報を入れたEntity、存在しない（または削除済み）場合はnull
	 */
	public User findById(int id) {
		User user = null;
		// 🔴 主キー（id）で狙い撃ちし、かつ削除されていない(deleted_at IS NULL)人を検索するSQL
		String sql = "SELECT * FROM user WHERE id = ? AND deleted_at IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// SQLにある唯一の「?」に、引数で届いた id をハメ込む！
			pstmt.setInt(1, id);

			// 検索系（SELECT）なので executeQuery() を使う
			try (ResultSet rs = pstmt.executeQuery()) {
				// もしデータが1件見つかったら（rs.nextがtrueなら）
				if (rs.next()) {
					user = new User(); // 持ち帰り用の箱（Entity）を作る！
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 見つかればデータ入りの箱、見つからなければ空っぽ（null）が戻る
		return user;
	}

}