package dto;

import java.sql.Timestamp;

public class LendDto {
	// フィールド（貸出テーブルのカラムを想定）
	private int id;                 // 貸出ID
	private int userId;             // 利用者ID
	private int bookId;             // 図書ID（実物の本）
	private Timestamp lendDate;     // 貸出日
	private Timestamp dueDate;      // 返却予定日
	private Timestamp returnDate;   // 実際の返却日（未返却ならnull）

	// ゲッター・セッター
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public Timestamp getLendDate() {
		return lendDate;
	}
	public void setLendDate(Timestamp lendDate) {
		this.lendDate = lendDate;
	}
	public Timestamp getDueDate() {
		return dueDate;
	}
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	public Timestamp getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Timestamp returnDate) {
		this.returnDate = returnDate;
	}
}