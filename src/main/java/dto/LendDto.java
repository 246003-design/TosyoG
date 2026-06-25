package dto;

import java.sql.Timestamp;

/**
 * 貸出情報のデータを保持するためのDTO（LendDto）
 */
public class LendDto {
	// フィールド（lendテーブルのカラムに対応）
	private int id;                 // 貸出ID
	private int userId;             // 利用者ID
	private int bookId;             // 図書ID
	private Timestamp lendDate;     // 貸出日
	private Timestamp dueDate;      // 返却予定日
	private Timestamp returnDate;   // 返却日（未返却の場合はnull）
	private int status;             // ステータス（貸出中・返却済など）

	// コンストラクタ（デフォルト）
	public LendDto() {}

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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}