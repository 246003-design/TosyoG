package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import dao.DBManager;
import dao.LendDAO;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OverdueServlet")
public class OverdueServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // ログインチェック（ReserveServlet と同様）
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        // 権限チェック：司書(1) or 管理者(2) のみアクセス可能
        if (loginUser.getRole() != 1 && loginUser.getRole() != 2) {
            session.setAttribute("errorMsg", "この画面へのアクセス権限がありません。");
            response.sendRedirect(request.getContextPath() + "/HomeServlet");
            return;
        }

        // リダイレクト経由のメッセージを request スコープに詰め替え
        String successMessage = (String) session.getAttribute("successMessage");
        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        String errorMsg = (String) session.getAttribute("errorMsg");
        if (errorMsg != null) {
            request.setAttribute("errorMsg", errorMsg);
            session.removeAttribute("errorMsg");
        }

        // 検索キーワード取得
        String searchQuery = request.getParameter("searchQuery");

        // 延滞一覧の取得（try-with-resources で自動クローズ）
        try (Connection conn = DBManager.getConnection()) {

            LendDAO lendDAO = new LendDAO(conn);
            List<Map<String, Object>> overdueList = lendDAO.findOverdueMapList(searchQuery);

            request.setAttribute("overdueList", overdueList);
            request.setAttribute("searchQuery", searchQuery); // 検索窓に入力値を残す

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "延滞一覧の取得中にエラーが発生しました。");
        }

        // JSP へフォワード
        request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_overdue.jsp")
               .forward(request, response);
    }
}