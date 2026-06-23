package model;

import java.sql.Connection;

// ログイン時のパスワードをハッシュ化し判定を行う
import org.mindrot.jbcrypt.BCrypt;

import dao.DBManager;
import dao.UserDAO;
import entity.User;

public class LoginLogic {
	
	/**
	 * ログイン認証を実行し、成功すればUserオブジェクトを返す
	 * 失敗すれば null を返す
	 */
	public User execute(int userId, String inputPassword) {
		
		User user = null;
		
		// 1. データベースに接続
		try (Connection conn = DBManager.getConnection()) {
			UserDAO userDAO = new UserDAO(conn);
			
			// 2. ID「だけ」を使って、データベースからユーザー情報を取ってくる
			// （UserDAOにすでに作られている findById メソッドが大活躍します！）
			user = userDAO.findById(userId);
			
			// 3. ユーザーが見つかった場合のみ、パスワードの照合を行う
			if (user != null) {
				// データベースに保存されている暗号化（ハッシュ化）されたパスワード
				String dbHashedPassword = user.getPassword();
				
				// 4. BCryptを使って、入力されたパスワードと暗号を照らし合わせる
				if (BCrypt.checkpw(inputPassword, dbHashedPassword)) {
					// ⭕ 一致した！ ＝ ログイン成功（そのままuserを返す）
					return user;
				} else {
					// ❌ パスワードが違う ＝ ログイン失敗
					return null;
				}
			}
			
		} catch (Exception e) {
			System.err.println("ログイン処理中にエラーが発生しました。");
			e.printStackTrace();
		}
		
		// ユーザーが見つからなかった、またはエラーが起きた場合は失敗
		return null;
	}
}