package model;

import dto.LendDetailDto;

public class ReturnBookLogic {
    
    /**
     * ISBN番号を元に、貸出テーブル(DB)から図書情報および利用者情報を検索する
     * （フローの「ISNB番号を入力し、検索を要求」〜「図書と利用者ID、利用者名を表示」に対応）
     */
    public LendDetailDto searchLoanInfo(String isbn) {
        // 本来はここにDB接続（DAOの呼び出し）が入ります。以下はモック（疑似）処理です。
        if (isbn == null || isbn.isEmpty()) {
            return null;
        }

        // 1. 正しくインスタンス化した「dto」変数を使用します
        LendDetailDto dto = new LendDetailDto();
        
        // 2. LendDetailDtoクラスに用意されているセッターを呼び出します
        dto.setBookId("BK-" + isbn);
        dto.setTitle("テスト用図書タイトル"); // LendDetailDtoに元からあるフィールド

        // 3. 「予約がある」の条件分岐をシミュレート
        if (isbn.endsWith("7")) {
            dto.setHasReservation(true); 
        } else {
            dto.setHasReservation(false);
        }

        // 4. 正しく「dto」を返却します
        return dto;
    }

    /**
     * 返却を確定させる処理
     * （フローの最後の「返却完了を表示」に進む前のDB更新処理に対応）
     */
    public boolean executeReturn(String bookId) {
        // 本来はここでDBの更新処理等を行います。
        System.out.println("図書ID: " + bookId + " の返却処理（DB更新）が成功しました。");
        return true; 
    }
}