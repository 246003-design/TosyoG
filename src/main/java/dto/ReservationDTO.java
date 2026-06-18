package dto;

import java.sql.Timestamp;

public class ReservationDTO {
    // --- 既存のフィールド（すでにあるもの） ---
    private int id;
    private int userId;
    private int bookInfoId;
    private int bookId;
    private Timestamp reservationDate;
    private int status;
    // ...（中略）...

    // --- ★ここを追加：画面表示（DTO化）のために必要なフィールド ---
    private String userName;   // 利用者名
    private String bookTitle;  // 図書のタイトル

    // --- 追加したフィールドのゲッター・セッター ---
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    // （既存のゲッター・セッターはそのまま残す）
}