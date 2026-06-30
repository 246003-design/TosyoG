package model;

import java.sql.Connection;
import java.util.Optional;

import dao.BookDAO;
import dao.UserDAO;
import dto.LendDto; // ★LendDtoを使用
import entity.Book;
import entity.User;

public class LendLogic {
	
	// 最大貸出上限数
	private static final int MAX_LIMIT = 5;

	/**
	 * 【貸出可否・登録ロジック】
	 * 貸出冊数が上限以内かチェックし、DTOに値をセットして登録を行います。
	 */
	public String registerLend(Connection conn, int userId, int bookId) {
		UserDAO userDAO = new UserDAO(conn);
		BookDAO bookDAO = new BookDAO(conn);
		// ※LendDAOはプロジェクトに合わせて使用してください（今回は上限チェックメイン）

		try {
			// ① 利用者情報の取得と貸出冊数チェック
			User user = userDAO.findById(userId);
			if (user == null) {
				return "利用者情報が見つかりません。";
			}
			
			// ★【判定: 貸出冊数上限チェック】
			int borrowCount = user.getBorrowCount();
			if (borrowCount >= MAX_LIMIT) {
				return "貸出冊数が上限（" + MAX_LIMIT + "冊）に達しているため、これ以上貸出できません。";
			}

			// ② 図書情報の取得
			Optional<Book> bookOpt = bookDAO.findById(bookId);
			if (!bookOpt.isPresent()) {
				return "指定された図書情報が見つかりません。";
			}
			Book book = bookOpt.get();

			// ③ ★ご要望の通り、DTOの箱を作って値を移し替える処理
			LendDto newLendDto = new LendDto();
			newLendDto.setUserId(userId);
			newLendDto.setBookId(bookId);
			newLendDto.setStatus(0); // 0: 貸出中

			// ⑤ 登録処理の実行（LendDAO側の引数がDTOを想定）
			// ※もしLendDAOへのインサートも行う場合は、以下のようにDTOを渡します
			/*
			LendDAO lendDAO = new LendDAO(conn);
			boolean isSuccess = lendDAO.insert(newLendDto); 
			if (!isSuccess) {
				return "貸出の登録に失敗しました。";
			}
			*/

			return ""; // 貸出上限以内で、DTOへの移し替えも成功

		} catch (Exception e) {
			e.printStackTrace();
			return "予期せぬエラーが発生しました。";
		}
	}
}