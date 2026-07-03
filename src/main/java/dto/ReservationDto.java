package dto;

import java.sql.Timestamp;

public class ReservationDto {

    // フィールド
    private int id;
    private int userId;
    private int bookInfoId;  // 作品マスタID
    private int bookId;      // 物理本（蔵書）ID
    private Timestamp reservationDate;
    private Timestamp expireDate;
    private int status;      // 1:予約中, 2:準備中, 9:キャンセルなど
    
    // --- 画面表示用（Select時にJOINして取得する想定） ---
    private String userName;   // 利用者名
    private String bookTitle;  // 図書のタイトル
    private String imageUrl;   // ★追加: 表紙画像URL

    // --- ゲッター・セッター ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookInfoId() { return bookInfoId; }
    public void setBookInfoId(int bookInfoId) { this.bookInfoId = bookInfoId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public Timestamp getReservationDate() { return reservationDate; }
    public void setReservationDate(Timestamp reservationDate) { this.reservationDate = reservationDate; }

    public Timestamp getExpireDate() { return expireDate; }
    public void setExpireDate(Timestamp expireDate) { this.expireDate = expireDate; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}