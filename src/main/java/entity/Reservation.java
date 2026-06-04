package entity;

public class Reservation {

	private int id;
	private int userId;
	private int bookInfoId;
	private int bookId;
	private java.sql.Timestamp reservationDate;
	private java.sql.Timestamp expireDate;
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
	public int getBookInfoId() {
		return bookInfoId;
	}
	public void setBookInfoId(int bookInfoId) {
		this.bookInfoId = bookInfoId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public java.sql.Timestamp getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(java.sql.Timestamp reservationDate) {
		this.reservationDate = reservationDate;
	}
	public java.sql.Timestamp getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(java.sql.Timestamp expireDate) {
		this.expireDate = expireDate;
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
