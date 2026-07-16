package model;

import java.sql.Connection;

import dao.LendDAO;
import dao.UserDAO;
import entity.Lend;
import entity.User;

//貸出判定処理
public class LendLogic {

    /**
     * 貸出処理を登録し、結果のメッセージを返すメインメソッド
     */
    public String registerLend(Connection conn, int userId, int bookId) {
        
        // 1. まずは貸出可能か（上限未満か）をチェック
        if (!isUnderBorrowLimit(conn, userId)) {
            // 上限に達している、またはユーザーが存在しない場合
            return "エラー：貸出上限（5冊）に達しているか、ユーザーが存在しません。";
        }
        
        try {
            LendDAO lendDAO = new LendDAO(conn);
            UserDAO userDAO = new UserDAO(conn);
            
            // 2. 同じ本が既に貸出中かチェック
            Lend currentLend = lendDAO.findCurrentLendByBookId(bookId); 
            if (currentLend != null) {
                // 貸出中ならエラーメッセージを返す
                return "エラー：指定された図書（ID: " + bookId + "）はすでに貸出中です。";
            }
            
            // 3. 貸出登録処理（lendテーブルへのINSERT）
            lendDAO.insert(userId, bookId);
            
            // 4. 利用者の borrow_count を +1 する（userテーブルのUPDATE）
            userDAO.syncBorrowCount(userId);
            
            // すべて成功したら完了メッセージを返す
            return "貸出が完了しました。";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "エラー：システムエラーにより貸出登録に失敗しました。";
        }
    }

    /**
     * 貸出上限未満（5冊未満）かどうかを判定する
     * クラス内部でのみ使うため private に変更
     */
    private boolean isUnderBorrowLimit(Connection conn, int userId) {
        UserDAO userDAO = new UserDAO(conn);
        User user = userDAO.findById(userId);
        
        // 利用者が存在しない場合は一律で不可（false）とする
        if (user == null) {
            return false; 
        }
        
        // 現在の貸出冊数が 5冊未満 であれば、まだ借りられる
        return user.getBorrowCount() < 5;
    }
}