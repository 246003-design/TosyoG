package model;

import java.sql.Connection;
import java.util.Optional;

import dao.BookDAO;
import dao.DBManager;
import dao.ReservationDAO;
import dao.UserDAO;
import dto.ReservationDto;
import entity.Book;
import entity.Reservation;
import entity.User;

public class ReservationLogic {

    private static final int MAX_LIMIT = 5;

    /**
     * 【予約実行処理】
     * @param userId 利用者ID
     * @param bookId 図書ID（個体ID）
     * @return 登録に使用した ReservationDto
     * @throws Exception エラーメッセージを保持して例外をスロー
     */
    public ReservationDto executeReservation(int userId, int bookId) throws Exception {

        try (Connection conn = DBManager.getConnection()) {
            conn.setAutoCommit(false); // トランザクション開始

            UserDAO userDao = new UserDAO(conn);
            BookDAO bookDao = new BookDAO(conn);
            ReservationDAO reservationDao = new ReservationDAO(conn);

            try {
                // ① 利用者の存在チェック
                User user = userDao.findById(userId);
                if (user == null) {
                    throw new Exception("利用者情報が見つかりません。");
                }

                // ② 図書の存在チェック
                Optional<Book> bookOpt = bookDao.findById(bookId);
                if (!bookOpt.isPresent()) {
                    throw new Exception("指定された図書情報が見つかりません。");
                }
                Book book = bookOpt.get();

                // ③ 貸出・予約上限チェック
                if (user.getBorrowCount() >= MAX_LIMIT) {
                    throw new Exception("貸出・予約の合計が上限（" + MAX_LIMIT + "冊）に達しているため、予約できません。");
                }

                // ④ 同一作品の二重予約チェック
                for (Reservation r : reservationDao.findAll()) {
                    if (r.getUserId() == userId && (r.getStatus() == 1 || r.getStatus() == 2)) {
                        if (r.getBookInfoId() == book.getBookInfoId()) {
                            throw new Exception("すでにこの作品は予約済みです。");
                        }
                    }
                }

                // ⑤ 予約DTOの生成と登録処理 (ReservationDAO の仕様に合わせる)
                ReservationDto dto = new ReservationDto();
                dto.setUserId(userId);
                dto.setBookInfoId(book.getBookInfoId());
                dto.setBookId(book.getId()); // 本の個体ID
                dto.setStatus(1);            // 1: 予約中

                boolean isInserted = reservationDao.insert(dto);
                if (!isInserted) {
                    throw new Exception("予約情報の登録に失敗しました。");
                }

                // ⑥ 貸出冊数(borrow_count)の同期
                userDao.syncBorrowCount(userId);

                // ⑦ コミット
                conn.commit();

                return dto;

            } catch (Exception e) {
                conn.rollback();
                throw e; // エラーメッセージをServlet等へ引き継ぐ
            }
        }
    }

    /**
     * 【予約キャンセル実行処理】
     * @param userId 利用者ID
     * @param bookId 図書ID（個体ID）
     * @return 更新に使用した ReservationDto
     * @throws Exception エラーメッセージを保持して例外をスロー
     */
    public ReservationDto executeCancellation(int userId, int bookId) throws Exception {

        try (Connection conn = DBManager.getConnection()) {
            conn.setAutoCommit(false); // トランザクション開始

            UserDAO userDao = new UserDAO(conn);
            BookDAO bookDao = new BookDAO(conn);
            ReservationDAO reservationDao = new ReservationDAO(conn);

            try {
                // ① 図書の存在チェック
                Optional<Book> bookOpt = bookDao.findById(bookId);
                if (!bookOpt.isPresent()) {
                    throw new Exception("図書情報が見つかりません。");
                }
                Book book = bookOpt.get();

                // ② キャンセル対象の予約データを検索
                Reservation targetReservation = null;
                for (Reservation r : reservationDao.findAll()) {
                    if (r.getUserId() == userId && r.getBookInfoId() == book.getBookInfoId() && (r.getStatus() == 1 || r.getStatus() == 2)) {
                        targetReservation = r;
                        break;
                    }
                }

                if (targetReservation == null) {
                    throw new Exception("キャンセル対象の有効な予約が見つかりませんでした。");
                }

                // ③ 予約DTOの準備とステータス更新 (0: キャンセル済)
                ReservationDto dto = new ReservationDto();
                dto.setId(targetReservation.getId());
                dto.setStatus(0);

                boolean isUpdated = reservationDao.update(dto);
                if (!isUpdated) {
                    throw new Exception("予約のキャンセル処理に失敗しました。");
                }

                // ④ 貸出冊数(borrow_count)の同期
                userDao.syncBorrowCount(userId);

                // ⑤ コミット
                conn.commit();

                return dto;

            } catch (Exception e) {
                conn.rollback();
                throw e; // エラーメッセージをServlet等へ引き継ぐ
            }
        }
    }
}