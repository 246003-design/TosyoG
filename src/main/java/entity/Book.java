package entity;

public class Book {

	private int id;
	private int bookInfoId;
	private int bookNumber;
	private int layoutId;
	private java.sql.Timestamp createdAt;
	private java.sql.Timestamp updatedAt;
	private java.sql.Timestamp deletedAt;

	// 💡 JSPで「book.bookInfo.title」や「book.bookInfo.imageUrl」などを呼び出すために、
	// Bookエンティティの中に書籍マスタ情報（BookInfo）を格納するフィールドを追加します
	private BookInfo bookInfo;

	// 💡 追加した bookInfo のゲッターメソッド
	public BookInfo getBookInfo() {
		return bookInfo;
	}

	// 💡 追加した bookInfo のセッターメソッド（これでBookDAOのエラーが消えます！）
	public void setBookInfo(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBookInfoId() {
		return bookInfoId;
	}
	public void setBookInfoId(int bookInfoId) {
		this.bookInfoId = bookInfoId;
	}
	public int getBookNumber() {
		return bookNumber;
	}
	public void setBookNumber(int bookNumber) {
		this.bookNumber = bookNumber;
	}

	public int getLayoutId() {
		return layoutId;
	}
	public void setLayoutId(int layoutId) {
		this.layoutId = layoutId;
	}
	public java.sql.Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(java.sql.Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public java.sql.Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(java.sql.Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public java.sql.Timestamp getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(java.sql.Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public void setReservedByCurrentUser(boolean isReserved) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	// entity.Book.java にもし無ければ追加
	private boolean reservedByCurrentUser; // フィールド

	public boolean isReservedByCurrentUser() {
	    return reservedByCurrentUser;
	}
}