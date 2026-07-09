package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dao.DBManager;
import dao.LendDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/OverdueServlet")
public class OverdueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// リクエストの文字コードをUTF-8に設定
		request.setCharacterEncoding("UTF-8");
		
		// JSPの検索窓（name="searchQuery"）からキーワードを取得
		String searchQuery = request.getParameter("searchQuery");
		
		List<Map<String, Object>> overdueList = null;
		Connection conn = null;
		
		try {
			// DB接続の取得を try ブロック内で行う（エラーハンドリングのため）
			conn = DBManager.getConnection();
			
			// Connectionを引数に渡してDAOをインスタンス化
			LendDAO lendDao = new LendDAO(conn);
			
			// 延滞一覧（Mapのリスト）を取得
			overdueList = lendDao.findOverdueMapList(searchQuery);
			
		} catch (Exception e) {
			System.err.println("OveDueServletの実行中にエラーが発生しました。");
			e.printStackTrace();
		} finally {
			// 💡サーブレット側で取得したConnectionを確実にクローズする
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		// 取得したリストをリクエストスコープに格納（JSPの ${overdueList} と紐づく）
		request.setAttribute("overdueList", overdueList);
		
		// 延滞一覧画面のJSPへ転送（ファイル名が overdue_list.jsp の場合）
		request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_overdue.jsp").forward(request, response);
	}
}