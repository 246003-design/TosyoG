package dto;


	import java.sql.Timestamp;

	public class ReservationDto {

	    // フィールド
	    private int id;
	    private int userId;
	    private int bookInfoId;
	    private int bookId;
	    private Timestamp reservationDate;
	    private Timestamp expireDate;
	    private int status;
	    private String userName;   // 利用者名
	    private String bookTitle;  // 図書のタイトル

	    // --- DAOが値を「入れる」「出す」ための正しいゲッター・セッター ---
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
	}
}
