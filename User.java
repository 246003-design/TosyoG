package entity;

public class User {

    // ① DBのカラムと同じデータを格納する変数（フィールド）を用意する
    private int id;
    private String name;
    private String password;
    private int role;
    private int status;
    private int borrowCount; // DBでは borrow_count
	private java.sql.Timestamp createdAt; // DBでは created_at
    private java.sql.Timestamp updatedAt; // DBでは updated_at
    private java.sql.Timestamp deletedAt; // DBでは deleted_at

    // ② 引数のない空のコンストラクタ（Javaの暗黙のルールとして用意しておくのが安全です）
    public User() {
    }

    // ③ ここから下はすべて Getter と Setter
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
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