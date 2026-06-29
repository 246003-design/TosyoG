package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MasterDAO extends BaseDAO {

	public MasterDAO(Connection conn) {
		super(conn);
	}

	// 💡 著者名からIDを取得（無ければ新規登録してIDを返す）
	public int getOrCreateAuthor(String name) {
		if (name == null || name.trim().isEmpty()) {
			name = "著者不明";
		}
		
		String selectSql = "SELECT id FROM author WHERE name = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
			pstmt.setString(1, name);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id"); 
				}
			}
		} catch (SQLException e) {
			System.err.println("著者検索でエラー: " + e.getMessage());
		}
		
		String insertSql = "INSERT INTO author (name) VALUES (?)";
		try (PreparedStatement pstmt = conn.prepareStatement(insertSql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, name);
			pstmt.executeUpdate();
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			}
		} catch (SQLException e) {
			System.err.println("著者登録でエラー: " + e.getMessage());
		}
		return 1; 
	}

	// 💡 出版社名からIDを取得（無ければ新規登録）
	public int getOrCreatePublisher(String name) {
		if (name == null || name.trim().isEmpty()) {
			name = "出版社不明";
		}
		
		String selectSql = "SELECT id FROM publisher WHERE name = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
			pstmt.setString(1, name);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			System.err.println("出版社検索でエラー: " + e.getMessage());
		}
		
		String insertSql = "INSERT INTO publisher (name) VALUES (?)";
		try (PreparedStatement pstmt = conn.prepareStatement(insertSql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, name);
			pstmt.executeUpdate();
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			}
		} catch (SQLException e) {
			System.err.println("出版社登録でエラー: " + e.getMessage());
		}
		return 1;
	}

	// 💡 カテゴリ名からIDを取得（無ければ新規登録）
	public int getOrCreateCategory(String name) {
		if (name == null || name.trim().isEmpty()) {
			name = "未分類";
		}
		
		String selectSql = "SELECT id FROM category WHERE name = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
			pstmt.setString(1, name);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			System.err.println("カテゴリ検索でエラー: " + e.getMessage());
		}
		
		// 🌟 新規登録の前に、現在の最大のsort_orderを取得して +1 する
		int nextSortOrder = 1;
		String maxSortSql = "SELECT COALESCE(MAX(sort_order), 0) + 1 AS next_sort FROM category";
		try (PreparedStatement maxStmt = conn.prepareStatement(maxSortSql);
			 ResultSet maxRs = maxStmt.executeQuery()) {
			if (maxRs.next()) {
				nextSortOrder = maxRs.getInt("next_sort");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 🌟 sort_order も一緒に登録するように修正
		String insertSql = "INSERT INTO category (name, sort_order) VALUES (?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(insertSql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, name);
			pstmt.setInt(2, nextSortOrder); // 計算した表示順をセット
			pstmt.executeUpdate();
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			}
		} catch (SQLException e) {
			System.err.println("カテゴリ登録でエラー: " + e.getMessage());
		}
		return 1;
	}
}