package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Lend;

// 貸出DAO
public class LendDAO extends BaseDAO {

	public LendDAO(Connection conn) {
		super(conn);
	}
	
	// IDから貸出情報を1件取得する
	public Lend findById(int id) {
		Lend lend = null;
		String sql = "SELECT id, user_id, book_id, lend_date, due_date, return_date, status, created_at, updated_at, deleted_at "
				+ "FROM lend WHERE id = ? AND deleted_at IS NULL";
				
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
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
		} catch (SQLException e) {
			System.err.println("LendDAO.findByIdの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return lend;
	}
	
	// すべての貸出情報を取得する
	public List<Lend> findAll() {
		List<Lend> list = new ArrayList<>();
		String sql = "SELECT id, user_id, book_id, lend_date, due_date, return_date, status, created_at, updated_at, deleted_at "
				+ "FROM lend WHERE deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
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
		} catch (SQLException e) {
			System.err.println("LendDAO.findAllの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Lend> findByUserId(int userId) {
		List<Lend> list = new ArrayList<>();
		String sql = "SELECT book_id, lend_date, due_date FROM lend WHERE user_id = ? AND return_date IS NULL AND deleted_at IS NULL";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, userId);

			try(ResultSet rs = pstmt.executeQuery()) {
			
				while (rs.next()) {
					Lend lend = new Lend();
					lend.setBookId(rs.getInt("book_id"));
					lend.setLendDate(rs.getTimestamp("lend_date"));
					lend.setDueDate(rs.getTimestamp("due_date"));
	                
					list.add(lend);
				}
			}
		} catch (SQLException e) {
			System.err.println("LendDAO.findByUserIdの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}
	
//延滞一覧機能
// 延滞一覧を取得する（DTOなし・Map使用版）
	public List<java.util.Map<String, Object>> findOverdueMapList(String searchQuery) {
		List<java.util.Map<String, Object>> list = new ArrayList<>();
		
		// 【SQL構築】返却予定日が過去 且つ 未返却 且つ 論理削除されていないデータ
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT b.title, u.name AS user_name, l.user_id, l.due_date ");
		sql.append("FROM lend l ");
		sql.append("JOIN user u ON l.user_id = u.id ");   // 💡環境に合わせてテーブル名・カラム名を調整してください
		sql.append("JOIN book b ON l.book_id = b.id ");   // 💡環境に合わせてテーブル名・カラム名を調整してください
		sql.append("WHERE l.return_date IS NULL AND l.due_date < CURRENT_TIMESTAMP AND l.deleted_at IS NULL ");
		
		// 検索キーワードがある場合、条件を追加（部分一致）
		boolean hasQuery = (searchQuery != null && !searchQuery.trim().isEmpty());
		if (hasQuery) {
			sql.append("AND (CAST(l.user_id AS CHAR) LIKE ? OR u.name LIKE ?) ");
		}
		
		// 期限の古い順に並び替え
		sql.append("ORDER BY l.due_date ASC");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
			if (hasQuery) {
				String keyword = "%" + searchQuery.trim() + "%";
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
			}
			
			try (ResultSet rs = pstmt.executeQuery()) {
				// 日付フォーマットの準備 (JSPで「2024/05/10」の形式で見せるため)
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd");
				
				while (rs.next()) {
					// 1行分のデータを格納するMapを作成
					java.util.Map<String, Object> map = new java.util.HashMap<>();
					
					// 💡JSPの ${overdue.xxxx} の「xxxx」の部分と完全に一致するキー名でputします
					map.put("title", rs.getString("title"));
					map.put("userName", rs.getString("user_name"));
					map.put("userId", rs.getInt("user_id"));
					
					java.sql.Timestamp dueDate = rs.getTimestamp("due_date");
					if (dueDate != null) {
						map.put("dueDate", sdf.format(dueDate));
					} else {
						map.put("dueDate", "");
					}
					
					list.add(map);
				}
			}
		} catch (SQLException e) {
			System.err.println("LendDAO.findOverdueMapListの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return list;
	}
	
	//返却機能	
	// 💡 追加1: 本のIDから、現在貸出中（未返却）の貸出情報を1件取得する
	public Lend findCurrentLendByBookId(int bookId) {
		Lend lend = null;
		String sql = "SELECT id, user_id, book_id, lend_date, due_date, return_date, status, created_at, updated_at, deleted_at "
				+ "FROM lend WHERE book_id = ? AND return_date IS NULL AND deleted_at IS NULL";
				
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, bookId);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
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
		} catch (SQLException e) {
			System.err.println("LendDAO.findCurrentLendByBookIdの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return lend;
	}

	// 💡 追加2: 返却日時を現在時刻にし、ステータスを更新する（返却完了処理）
	public boolean updateReturnStatus(int id, int status) {
		boolean result = false;
		// return_date と updated_at に CURRENT_TIMESTAMP (現在時刻) をセットする
		String sql = "UPDATE lend SET return_date = CURRENT_TIMESTAMP, status = ?, updated_at = CURRENT_TIMESTAMP "
				+ "WHERE id = ? AND deleted_at IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, status);
			pstmt.setInt(2, id);

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				result = true;
			}
		} catch (SQLException e) {
			System.err.println("LendDAO.updateReturnStatusの実行中にエラーが発生しました。");
			e.printStackTrace();
		}
		return result;
	}
	
	public void insert(int userId, int bookId) throws SQLException {
        // ※SQLの構文（返却期限を14日後にする等）はご自身のデータベースの設定に合わせてください
        String sql = "INSERT INTO lend (user_id, book_id, lend_date, due_date, status, created_at, updated_at) "
                   + "VALUES (?, ?, CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 14 DAY), 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
        }
    }
}