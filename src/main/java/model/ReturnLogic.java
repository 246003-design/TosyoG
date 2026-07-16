package model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Optional;

import dao.BookDAO;
import dao.DBManager;
import dao.LendDAO;
import dao.UserDAO;
import dto.LendDto;
import entity.Book;
import entity.Lend;

public class ReturnLogic {

    // 返却処理を行い、成功したら画面表示用のDTOを返す
    public LendDto executeReturn(int bookId) throws Exception {
        
        try (Connection conn = DBManager.getConnection()) {
            conn.setAutoCommit(false); // トランザクション開始
            
            BookDAO bookDao = new BookDAO(conn);
            LendDAO lendDao = new LendDAO(conn);
            UserDAO userDao = new UserDAO(conn);

            try {
                // ① 本の存在チェック
                Optional<Book> bookOpt = bookDao.findById(bookId);
                if (!bookOpt.isPresent()) {
                    throw new Exception("該当する本は存在しません。IDを再確認してください。");
                }
                
                // ② 貸出中かチェック
                Lend lend = lendDao.findCurrentLendByBookId(bookId);
                if (lend == null) {
                    throw new Exception("この図書（ID: " + bookId + "）は現在貸出中ではありません。");
                }

                // ③ 貸出状況の更新処理
                // ステータスを「1 (返却済)」に更新する
                boolean isLendUpdated = lendDao.updateReturnStatus(lend.getId(), 1);
                
                // ④ ユーザーの貸出冊数を減らす処理
                userDao.syncBorrowCount(lend.getUserId());

                // ⑤ コミットとDTOの作成
conn.commit(); // 変更を確定
                
                LendDto returnDto = new LendDto();
                returnDto.setId(lend.getId());
                returnDto.setUserId(lend.getUserId());
                returnDto.setBookId(lend.getBookId());
                returnDto.setLendDate(lend.getLendDate());
                returnDto.setReturnDate(new Timestamp(System.currentTimeMillis()));
                
                return returnDto; 

            } catch (Exception e) {
                conn.rollback();
                throw e; // エラーメッセージをServletに引き継ぐ
            }
        }
    }
}