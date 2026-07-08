package model;

import java.sql.Connection;

import dao.UserDAO;
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
            return "貸出エラー：貸出上限（5冊）に達しているか、ユーザーが存在しません。";
        }
        
        // 2. 本の在庫チェックや、実際の貸出登録処理をここに行う（DAOの呼び出しなど）
        try {
            // ⭕ 修正：ダミーのコメントを外し、実際にLendDAOを呼び出してDBに保存する
            dao.LendDAO lendDAO = new dao.LendDAO(conn);
            lendDAO.insert(userId, bookId);
            
            // すべて成功したら完了メッセージを返す
            return "貸出が完了しました。";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "システムエラー：貸出登録に失敗しました。";
        }
    }

    /**
     * 【さっきのロジック】貸出上限未満（5冊未満）かどうかを判定する
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