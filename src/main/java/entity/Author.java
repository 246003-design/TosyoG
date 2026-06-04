package entity;

public class Author {

	private int id;
	private String name;
	private java.sql.Timestamp createdAt;
	private java.sql.Timestamp updatedAt;
	private java.sql.Timestamp deletedAt;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public java.sql.Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(java.sql.Timestamp created_at) {
		this.created_at = created_at;
	}
	public java.sql.Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(java.sql.Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	public java.sql.Timestamp getDeleted_at() {
		return deleted_at;
	}
	public void setDeleted_at(java.sql.Timestamp deleted_at) {
		this.deleted_at = deleted_at;
	}

}
