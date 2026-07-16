package model;

import java.sql.Connection;
import java.util.Optional;

import dao.BookDAO;
import dao.ReservationDAO;
import dao.UserDAO;
import entity.Book;
import entity.Reservation;
import entity.User;

public class ReservationLogic {
    
    private static final int MAX_LIMIT = 5;

    /**
     * 【予約可能かどうかの判定】
     */
    public String validateReservation(Connection conn, int userId, int bookId) {
        UserDAO userDAO = new UserDAO(conn);
        BookDAO bookDAO = new BookDAO(conn);
        ReservationDAO reservationDAO = new ReservationDAO(conn);

        try {
            User user = userDAO.findById(userId);
            if (user == null) return "利用者情報が見つかりません。";

            Optional<Book> bookOpt = bookDAO.findById(bookId);
            if (!bookOpt.isPresent()) return "指定された図書情報が見つかりません。";
            Book book = bookOpt.get();

            // 🌟 1. 貸出・予約上限の判定
            // 予約時にborrowCountが増減するため、単純に現在のborrowCountと比較するだけでOK
            if (user.getBorrowCount() >= MAX_LIMIT) {
                return "貸出・予約の合計が上限（" + MAX_LIMIT + "冊）に達しているため、予約できません。";
            }

            // 🌟 2. 同一作品の二重予約チェック
            boolean isAlreadyReserved = false;
            for (Reservation r : reservationDAO.findAll()) {
                if (r.getUserId() == userId && (r.getStatus() == 1 || r.getStatus() == 2)) {
                    if (r.getBookInfoId() == book.getBookInfoId()) {
                        isAlreadyReserved = true;
                        break; // 見つかった時点でループを抜ける
                    }
                }
            }

            if (isAlreadyReserved) {
                return "すでにこの作品は予約済みです。";
            }

            return ""; // 問題なければ空文字
        } catch (Exception e) {
            e.printStackTrace();
            return "予期せぬエラーが発生しました。";
        }
    }

    /**
     * 【キャンセル可能かどうかの判定】
     */
    public String validateCancellation(Connection conn, int userId, int bookId) {
        ReservationDAO reservationDAO = new ReservationDAO(conn);
        BookDAO bookDAO = new BookDAO(conn);

        try {
            Optional<Book> bookOpt = bookDAO.findById(bookId);
            if (!bookOpt.isPresent()) return "図書情報が見つかりません。";
            int bookInfoId = bookOpt.get().getBookInfoId();

            for (Reservation r : reservationDAO.findAll()) {
                if (r.getUserId() == userId && r.getBookInfoId() == bookInfoId && (r.getStatus() == 1 || r.getStatus() == 2)) {
                    return ""; // 対象が見つかればOK
                }
            }
            return "キャンセル対象の予約が見つかりませんでした。";
        } catch (Exception e) {
            e.printStackTrace();
            return "キャンセル判定中にエラーが発生しました。";
        }
    }
}