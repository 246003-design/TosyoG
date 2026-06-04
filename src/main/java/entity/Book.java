package entity;

public class Book {

	private int id;
	private int bookInfoId;
	private int bookNumber;
	private int layoutId;
	private java.sql.Timestamp createdAt;
	private java.sql.Timestamp updatedAt;
	private java.sql.Timestamp deletedAt;

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


}
