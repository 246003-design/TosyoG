package entity;

// データベースの user テーブルのデータを持ち運ぶための箱　　
public class User {
    private int id;
    private String name;
    private String password;
    private int role;
    private int status;
    private int borrowCount;

    // --- ここから下は Getter と Setter（右クリック＞ソース＞自動生成で作れます） ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getRole() { return role; }
    public void setRole(int role) { this.role = role; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public int getBorrowCount() { return borrowCount; }
    public void setBorrowCount(int borrowCount) { this.borrowCount = borrowCount; }
}