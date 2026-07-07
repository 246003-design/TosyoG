package servlet;

import java.io.IOException;

import dto.LendDetailDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ReturnBookLogic;

// JSP側のアクション先（ReturnProcessServlet）に合わせるか、
// もしくはJSP側をReturnBookServletに書き換えてください。ここではJSPに合わせます。
@WebServlet("/ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // GETリクエスト：初期表示（「返却処理要求」→「返却処理画面を表示」）
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // パスは実際のプロジェクト構成に合わせて調整してください
        request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_return.jsp").forward(request, response);
    }

    // POSTリクエスト：検索および返却確定処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        // JSPから送信される図書ID（ISBN/ID）を取得
        String bookIdParam = request.getParameter("bookId"); // 引数名をJSPに合わせる
        String action = request.getParameter("action");
        
        ReturnBookLogic logic = new ReturnBookLogic();
        HttpSession session = request.getSession();

        // フォームから action パラメータが送られてこない場合のデフォルト挙動を「検索」とする
        if (action == null || "search".equals(action) || action.isEmpty()) {
            
            // 1. 「ISBN/図書IDを入力し、検索を要求」の処理
            if (bookIdParam == null || bookIdParam.trim().isEmpty()) {
                request.setAttribute("errorMessage", "図書IDを入力してください。");
                request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_return.jsp").forward(request, response);
                return;
            }

            // ロジッククラスで検索（メソッド名や引数型がStringで対応している前提）
            LendDetailDto loanInfo = logic.searchLoanInfo(bookIdParam);

            if (loanInfo != null) {
                // セッションに情報を保存
                session.setAttribute("loanInfo", loanInfo);
                
                // フロー上の「予約がある(YES/NO)」のメッセージ用判定
                if (loanInfo.isHasReservation()) {
                    // 確認画面で出す警告メッセージ
                    request.setAttribute("warningMessage", "【注意】この図書には予約が入っています。");
                }
                
                // 「確認画面を表示」へフォワード
                request.getRequestDispatcher("/WEB-INF/JSP/librarian/returnConfirm.jsp").forward(request, response);
            } else {
                // 該当図書がない場合、初期画面（librarian_return.jsp）に戻してエラーを表示
                // JSPの変数名 ${errorMessage} に合わせる
                request.setAttribute("errorMessage", "該当する本は存在しません。IDを再確認してください。");
                request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_return.jsp").forward(request, response);
            }

        } else if ("confirm_yes".equals(action)) {
            // 2. 「確認画面」で YES（返却確定ボタンを押す）が選択された場合
            LendDetailDto loanInfo = (LendDetailDto) session.getAttribute("loanInfo");
            
            if (loanInfo != null) {
                // ロジックでDB更新処理を実行
                boolean success = logic.executeReturn(loanInfo.getBookId());
                
                if (success) {
                    // セッション情報をクリア
                    session.removeAttribute("loanInfo");
                    
                    // 次の画面（または元の画面）で表示する成功メッセージをセット
                    request.setAttribute("successMessage", "図書の返却処理が完了しました。");
                    
                    // 設計に応じて、専用の完了画面に行くか、元の画面に戻すか選択
                    // 元の画面（librarian_return.jsp）に戻してトーストを出す場合はこちら：
                    request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_return.jsp").forward(request, response);
                    
                    // 専用の成功画面（returnSuccess.jsp）がある場合は元のコード通りこちら：
                    // request.getRequestDispatcher("/WEB-INF/JSP/librarian/returnSuccess.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "返却処理に失敗しました。システム管理者に連絡してください。");
                    request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_return.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("ReturnProcessServlet");
            }

        } else if ("confirm_no".equals(action)) {
            // 3. 「確認画面」で NO（キャンセル）が選択された場合
            session.removeAttribute("loanInfo");
            request.setAttribute("errorMessage", "返却処理がキャンセルされました。");
            request.getRequestDispatcher("/WEB-INF/JSP/librarian/librarian_return.jsp").forward(request, response);
        }
    }
}