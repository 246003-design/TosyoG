package model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Optional;

import dao.BookDAO;
import dao.DBManager; // 独自のDB接続クラス
import dao.LendDAO;
import dao.UserDAO;
import dto.LendDto;
import entity.Book;
import entity.Lend;
import entity.User;

public class ReturnLogic {

    // 返却処理を行い、成功したら画面表示用のDTOを返す
    public LendDto executeReturn(int bookId) throws Exception {
        
        try (Connection conn = DBManager.getConnection()) {
            conn.setAutoCommit(false);
            
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
                    throw new Exception("この図書は現在貸出中ではありません。");
                }

                // ③・④ 更新処理
                boolean isLendUpdated = lendDao.updateReturnStatus(lend.getId(), 2);
                User user = userDao.findById(lend.getUserId());
                boolean isUserUpdated = false;
                if (user != null) {
                    user.setBorrowCount(Math.max(0, user.getBorrowCount() - 1));
                    isUserUpdated = userDao.update(user);
                }

                // ⑤ コミットとDTOの作成
                if (isLendUpdated && isUserUpdated) {
                    conn.commit();
                    
                    LendDto returnDto = new LendDto();
                    returnDto.setId(lend.getId());
                    returnDto.setUserId(lend.getUserId());
                    returnDto.setBookId(lend.getBookId());
                    returnDto.setLendDate(lend.getLendDate());
                    returnDto.setReturnDate(new Timestamp(System.currentTimeMillis()));
                    
                    // タイトルをDTOの空きフィールドや別の方法で渡す工夫をしてもOKです
                    return returnDto; 
                } else {
                    conn.rollback();
                    throw new Exception("返却処理中にエラーが発生しました。");
                }
            } catch (Exception e) {
                conn.rollback();
                throw e; // エラーメッセージをServletに引き継ぐ
            }
        }
    }
}