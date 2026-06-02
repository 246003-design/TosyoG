<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 貸出処理</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-10">
        <a href="librarian_home.jsp" class="p-1 hover:bg-white/20 rounded-full transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">貸出処理</h1>
    </header>
    
    <main class="flex-1 p-6 max-w-3xl mx-auto w-full relative">
        
        <%-- サーブレットからの完了メッセージ表示 --%>
        <%-- <c:if test="${not empty successMessage}"> --%>
        <div class="mb-6 bg-green-50 border border-green-200 text-green-800 px-4 py-3 rounded-lg flex items-center gap-2 font-bold shadow-sm">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22c5.523 0 10-4.477 10-10S17.523 2 12 2 2 6.477 2 12s4.477 10 10 10z"/><path d="m9 12 2 2 4-4"/></svg>
            <%-- <c:out value="${successMessage}" /> --%>
            貸出手続きが完了しました。
        </div>
        <%-- </c:if> --%>

        <div class="bg-white p-8 rounded-xl shadow-sm border border-gray-300">
            <%-- サーブレットからのエラー表示（貸出上限等） --%>
            <%-- <c:if test="${not empty errorMessage}"> --%>
            <div class="mb-6 bg-red-50 border-l-4 border-red-600 p-4 rounded">
                <div class="flex items-start gap-3">
                    <svg class="text-red-600 mt-0.5 shrink-0" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
                    <div>
                        <h4 class="text-red-800 font-bold">貸出エラー</h4>
                        <p class="text-sm text-red-700 mt-1">
                            <%-- <c:out value="${errorMessage}" /> --%>
                            貸出上限数を超過、または返却遅延があります。これ以上の貸出は出来ません。
                        </p>
                    </div>
                </div>
            </div>
            <%-- </c:if> --%>

            <%-- サーブレットへのPOSTフォーム --%>
            <form action="LoanProcessServlet" method="POST" class="space-y-6">
                <div>
                    <label class="block text-sm font-bold text-gray-700 mb-2">利用者ID</label>
                    <input 
                        type="text" 
                        name="userId"
                        placeholder="利用者カードをスキャンまたは入力" 
                        class="w-full p-4 border border-gray-300 rounded bg-gray-50 focus:bg-white focus:ring-2 focus:ring-[#1e3a8a] outline-none text-lg font-mono"
                        required
                        autofocus
                    />
                </div>
                <div>
                    <label class="block text-sm font-bold text-gray-700 mb-2">図書ISBN / 蔵書ID</label>
                    <input 
                        type="text" 
                        name="bookId"
                        placeholder="ISBNをスキャンまたは入力" 
                        class="w-full p-4 border border-gray-300 rounded bg-gray-50 focus:bg-white focus:ring-2 focus:ring-[#1e3a8a] outline-none text-lg font-mono"
                        required
                    />
                </div>

                <div class="flex gap-4 mt-8">
                    <a href="librarian_home.jsp" class="flex-1 py-4 border-2 border-gray-300 text-gray-700 rounded font-bold text-center hover:bg-gray-50 transition-colors">戻る</a>
                    <!-- JSを使わず、直接サーブレットへ送信（確認画面JSPを挟むか、サーブレット側で制御します） -->
                    <button type="submit" class="flex-[2] py-4 bg-[#1e3a8a] text-white rounded font-bold hover:bg-blue-800 shadow-md transition-colors" onclick="return confirm('図書の貸出を確定します。よろしいですか？');">
                        貸出内容を確認して確定
                    </button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>