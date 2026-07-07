package entity;

public class Layout {

	private int id;
	private String location;
	private java.sql.Timestamp createdAt;
	private java.sql.Timestamp updatedAt;
	private java.sql.Timestamp deletedAt;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
