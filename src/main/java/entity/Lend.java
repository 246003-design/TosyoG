package entity;

public class Lend {

	private int id;
	private int userId;
	private int bookId;
	private java.sql.Timestamp lendDate;
	private java.sql.Timestamp dueDate;
	private java.sql.Timestamp returnDate;
	private int status;
	private java.sql.Timestamp createdAt;
	private java.sql.Timestamp updatedAt;
	private java.sql.Timestamp deletedAt;

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
	public java.sql.Timestamp getLendDate() {
		return lendDate;
	}
	public void setLendDate(java.sql.Timestamp lendDate) {
		this.lendDate = lendDate;
	}
	public java.sql.Timestamp getDueDate() {
		return dueDate;
	}
	public void setDueDate(java.sql.Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	public java.sql.Timestamp getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(java.sql.Timestamp returnDate) {
		this.returnDate = returnDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
