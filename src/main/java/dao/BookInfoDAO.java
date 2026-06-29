package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.BookInfo;

// 書籍マスタDAO
public class BookInfoDAO extends BaseDAO {

	// BaseDAOのコンストラクタを呼び出す
	public BookInfoDAO(Connection conn) {
		super(conn);
	}

	/**
	 * 1. キーワード検索（タイトルでのあいまい検索）
	 */
	public List<BookInfo> search(String keyword) {
		List<BookInfo> bookList = new ArrayList<>();
		// 🔴 SQL側のカラム名は、DBの定義に合わせてください（通常は大文字小文字が区別されます）
		String sql = "SELECT id, title, isbn, author_id, publisher_id, category_id, imageUrl, created_at, updated_at, deleted_at "
				+ "FROM book_info WHERE title LIKE ? AND deleted_at IS NULL";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			String likeKeyword = "%" + keyword + "%";
			pstmt.setString(1, likeKeyword);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					BookInfo book = new BookInfo();
					book.setId(rs.getInt("id"));
					book.setTitle(rs.getString("title"));
					book.setIsbn(rs.getString("isbn"));
					book.setAuthorId(rs.getInt("author_id"));
					book.setPublisherId(rs.getInt("publisher_id"));
					book.setCategoryId(rs.getInt("category_id"));
					
					// 🔴 BookInfo.java のメソッド名「setImageUrl」に完全に合わせました
					book.setImageUrl(rs.getString("imageUrl")); 
					
					book.setCreatedAt(rs.getTimestamp("created_at"));
					book.setUpdatedAt(rs.getTimestamp("updated_at"));
					book.setDeletedAt(rs.getTimestamp("deleted_at"));
					
					bookList.add(book);
				}
			}
		} catch (SQLException e) {
			System.err.println("BookInfoDAO.searchでエラーが発生しました。");
			e.printStackTrace();
		}
		return bookList;
	}

	/**
	 * 3. idから書籍マスタの情報を取得する
	 */
	public BookInfo searchTitle(int id) {
		BookInfo bookInfo = null;
		String sql = "SELECT id, title, isbn, author_id, publisher_id, category_id, imageUrl, created_at, updated_at, deleted_at "
				+ "FROM book_info WHERE id = ? AND deleted_at IS NULL";
				
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					bookInfo = new BookInfo();
					bookInfo.setId(rs.getInt("id"));
					bookInfo.setTitle(rs.getString("title"));
					bookInfo.setIsbn(rs.getString("isbn"));
					bookInfo.setAuthorId(rs.getInt("author_id"));
					bookInfo.setPublisherId(rs.getInt("publisher_id"));
					bookInfo.setCategoryId(rs.getInt("category_id"));
					
					// 🔴 BookInfo.java のメソッド名「setImageUrl」に完全に合わせました
					bookInfo.setImageUrl(rs.getString("imageUrl")); 
					
					bookInfo.setCreatedAt(rs.getTimestamp("created_at"));
					bookInfo.setUpdatedAt(rs.getTimestamp("updated_at"));
					bookInfo.setDeletedAt(rs.getTimestamp("deleted_at"));
				}
			}
		} catch (SQLException e) {
			System.err.println("BookInfoDAO.searchTitleでエラーが発生しました。");
			e.printStackTrace();
		}
		return bookInfo;
	}

	// 💡 ISBNを使ってbook_infoテーブルを検索するメソッドを追加します
		public BookInfo findByIsbn(String isbn) {
			BookInfo bookInfo = null;
			// 🔴 DBのカラム名に合わせて imageUrl に戻しました！
			String sql = "SELECT id, isbn, title, author_id, publisher_id, category_id, imageUrl FROM book_info WHERE isbn = ?";
			
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, isbn);
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						bookInfo = new BookInfo();
						bookInfo.setId(rs.getInt("id"));
						bookInfo.setIsbn(rs.getString("isbn"));
						bookInfo.setTitle(rs.getString("title"));
						bookInfo.setAuthorId(rs.getInt("author_id"));
						bookInfo.setPublisherId(rs.getInt("publisher_id"));
						bookInfo.setCategoryId(rs.getInt("category_id"));
						
						// 🔴 ここも imageUrl から取得します
						bookInfo.setImageUrl(rs.getString("imageUrl"));
					}
				}
			} catch (SQLException e) {
				System.err.println("findByIsbnでエラー: " + e.getMessage());
				e.printStackTrace();
			}
			return bookInfo;
		}

		// 💡 新規登録してIDを返すinsertメソッド
		public int insert(BookInfo bookInfo) {
			int newId = 0;
			// 🔴 カラム名を imageUrl に修正しました
			String sql = "INSERT INTO book_info (isbn, title, author_id, publisher_id, category_id, imageUrl) VALUES (?, ?, ?, ?, ?, ?)";
			
			try (PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, bookInfo.getIsbn());
				pstmt.setString(2, bookInfo.getTitle());
				pstmt.setInt(3, bookInfo.getAuthorId());
				pstmt.setInt(4, bookInfo.getPublisherId());
				pstmt.setInt(5, bookInfo.getCategoryId());
				pstmt.setString(6, bookInfo.getImageUrl());
				
				pstmt.executeUpdate();
				
				// 🌟 登録された直後の新しいIDを取得する
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						newId = generatedKeys.getInt(1);
					}
				}
			} catch (SQLException e) {
				System.err.println("insertでエラー: " + e.getMessage());
				e.printStackTrace();
			}
			return newId; 
		}
	}