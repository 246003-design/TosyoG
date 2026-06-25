package model;

import java.sql.Connection;
import java.util.Optional;

import dao.BookDAO;
import dao.ReservationDAO;
import dao.UserDAO;
import dto.ReservationDto; // ★追加：DTOを扱うためにインポート
import entity.Book;
import entity.Reservation;
import entity.User;

public class ReservationLogic {
	
	// 最大貸出・予約上限数
	private static final int MAX_LIMIT = 5;

	/**
	 * 【1. 予約登録ロジック】
	 */
	public String registerReservation(Connection conn, int userId, int bookId) {
		UserDAO userDAO = new UserDAO(conn);
		BookDAO bookDAO = new BookDAO(conn);
		ReservationDAO reservationDAO = new ReservationDAO(conn);

		try {
			// ① 利用者情報の取得と貸出冊数チェック
			User user = userDAO.findById(userId);
			if (user == null) {
				return "利用者情報が見つかりません。";
			}
			int borrowCount = user.getBorrowCount();

			// ② 図書情報の取得
			Optional<Book> bookOpt = bookDAO.findById(bookId);
			if (!bookOpt.isPresent()) {
				return "指定された図書情報が見つかりません。";
			}
			Book book = bookOpt.get();

			// ③ 現在の有効な予約数をカウント
			int reservationCount = 0;
			for (Reservation r : reservationDAO.findAll()) {
				if (r.getUserId() == userId && (r.getStatus() == 1 || r.getStatus() == 2)) {
					reservationCount++;
				}
			}

			// ④ 【判定: 上限チェック】
			if ((borrowCount + reservationCount) >= MAX_LIMIT) {
				return "貸出・予約の合計が上限（" + MAX_LIMIT + "冊）に達しているため、予約できません。";
			}

			// ⑤ 【判定: 二重予約チェック】
			for (Reservation r : reservationDAO.findAll()) {
				if (r.getUserId() == userId && r.getBookInfoId() == book.getBookInfoId() && (r.getStatus() == 1 || r.getStatus() == 2)) {
					return "すでにこの図書は予約済みです。";
				}
			}

			// ⑥ ★修正：DAOの引数（ReservationDTO）に合わせて、DTOの箱を作って値を送る！
			ReservationDto newResDto = new ReservationDto();
			newResDto.setUserId(userId);
			newResDto.setBookInfoId(book.getBookInfoId());
			newResDto.setBookId(bookId);
			newResDto.setStatus(1); // 1: 予約中

			// 修正したDAOの insert(ReservationDTO) を呼び出す
			boolean isSuccess = reservationDAO.insert(newResDto); 
			if (isSuccess) {
				return ""; // 成功
			} else {
				return "予約の登録に失敗しました。";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "予期せぬエラーが発生しました。";
		}
	}

	/**
	 * 【2. キャンセルロジック】
	 */
	public String cancelReservation(Connection conn, int userId, int bookId) {
		ReservationDAO reservationDAO = new ReservationDAO(conn);
		BookDAO bookDAO = new BookDAO(conn);

		try {
			Optional<Book> bookOpt = bookDAO.findById(bookId);
			if (!bookOpt.isPresent()) {
				return "図書情報が見つかりません。";
			}
			int bookInfoId = bookOpt.get().getBookInfoId();

			boolean targetFound = false;
			for (Reservation r : reservationDAO.findAll()) {
				if (r.getUserId() == userId && r.getBookInfoId() == bookInfoId && (r.getStatus() == 1 || r.getStatus() == 2)) {
					
					// ★修正：DAOの update(ReservationDTO) に合わせて、DTOの箱に移し替えて送る！
					ReservationDto updateDto = new ReservationDto();
					updateDto.setId(r.getId());
					updateDto.setStatus(9); // 9: キャンセル
					
					if (reservationDAO.update(updateDto)) {
						targetFound = true;
						break;
					}
				}
			}
			return targetFound ? "" : "キャンセル対象の予約が見つかりませんでした。";

		} catch (Exception e) {
			e.printStackTrace();
			return "キャンセル処理中にエラーが発生しました。";
		}
	}
}