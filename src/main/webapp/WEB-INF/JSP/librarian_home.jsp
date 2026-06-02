<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム | 司書業務ポータル</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 flex flex-col min-h-screen">
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center justify-between sticky top-0 z-20">
        <h1 class="text-xl font-bold tracking-wider">図書管理システム | 司書業務</h1>
        <div class="flex items-center gap-4">
            <span class="text-sm hidden sm:flex items-center gap-1 bg-white/10 px-3 py-1 rounded-full">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                司書: <%-- <c:out value="${sessionScope.userName}" /> --%>山田太郎 様
            </span>
            <a href="LogoutServlet" class="text-sm hover:text-blue-200 transition-colors flex items-center gap-1" onclick="return confirm('ログアウトしますか？');">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>
                ログアウト
            </a>
        </div>
    </header>

    <main class="flex-1 p-6 md:p-10 max-w-5xl mx-auto w-full flex items-center">
        <div class="grid grid-cols-2 md:grid-cols-3 gap-4 md:gap-6 w-full">
            <a href="librarian_loan.jsp" class="bg-white p-6 md:p-8 rounded-xl shadow hover:shadow-lg transition-all flex flex-col items-center justify-center gap-4 group border border-gray-200">
                <div class="bg-emerald-600 text-white p-4 rounded-full group-hover:scale-110 transition-transform shadow-md">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 2v20"/><path d="m17 7-5-5-5 5"/></svg>
                </div>
                <span class="font-bold text-gray-800 text-lg">貸出</span>
            </a>
            <a href="librarian_return.jsp" class="bg-white p-6 md:p-8 rounded-xl shadow hover:shadow-lg transition-all flex flex-col items-center justify-center gap-4 group border border-gray-200">
                <div class="bg-teal-600 text-white p-4 rounded-full group-hover:scale-110 transition-transform shadow-md">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 2v20"/><path d="m17 17-5 5-5-5"/></svg>
                </div>
                <span class="font-bold text-gray-800 text-lg">返却</span>
            </a>
            <a href="librarian_book_menu.jsp" class="bg-white p-6 md:p-8 rounded-xl shadow hover:shadow-lg transition-all flex flex-col items-center justify-center gap-4 group border border-gray-200">
                <div class="bg-blue-600 text-white p-4 rounded-full group-hover:scale-110 transition-transform shadow-md">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12.22 2h-.44a2 2 0 0 0-2 2v.18a2 2 0 0 1-1 1.73l-.43.25a2 2 0 0 1-2 0l-.15-.08a2 2 0 0 0-2.73.73l-.22.38a2 2 0 0 0 .73 2.73l.15.1a2 2 0 0 1 1 1.72v.51a2 2 0 0 1-1 1.74l-.15.09a2 2 0 0 0-.73 2.73l.22.38a2 2 0 0 0 2.73.73l.15-.08a2 2 0 0 1 2 0l.43.25a2 2 0 0 1 1 1.73V20a2 2 0 0 0 2 2h.44a2 2 0 0 0 2-2v-.18a2 2 0 0 1 1-1.73l.43-.25a2 2 0 0 1 2 0l.15.08a2 2 0 0 0 2.73-.73l.22-.39a2 2 0 0 0-.73-2.73l-.15-.08a2 2 0 0 1-1-1.74v-.5a2 2 0 0 1 1-1.74l.15-.09a2 2 0 0 0 .73-2.73l-.22-.38a2 2 0 0 0-2.73-.73l-.15.08a2 2 0 0 1-2 0l-.43-.25a2 2 0 0 1-1-1.73V4a2 2 0 0 0-2-2z"/><circle cx="12" cy="12" r="3"/></svg>
                </div>
                <span class="font-bold text-gray-800 text-lg">蔵書管理</span>
            </a>
            <!-- 共通の図書検索JSPへ -->
            <a href="book_search.jsp" class="bg-white p-6 md:p-8 rounded-xl shadow hover:shadow-lg transition-all flex flex-col items-center justify-center gap-4 group border border-gray-200">
                <div class="bg-indigo-600 text-white p-4 rounded-full group-hover:scale-110 transition-transform shadow-md">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
                </div>
                <span class="font-bold text-gray-800 text-lg">図書検索</span>
            </a>
            <a href="librarian_overdue.jsp" class="bg-white p-6 md:p-8 rounded-xl shadow hover:shadow-lg transition-all flex flex-col items-center justify-center gap-4 group border border-gray-200">
                <div class="bg-orange-600 text-white p-4 rounded-full group-hover:scale-110 transition-transform shadow-md">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3Z"/><path d="M12 9v4"/><path d="M12 17h.01"/></svg>
                </div>
                <span class="font-bold text-gray-800 text-lg">延滞一覧表</span>
            </a>
            <!-- 管理者画面へのリンク等 (今回はプレースホルダー) -->
            <a href="#" class="bg-white p-6 md:p-8 rounded-xl shadow hover:shadow-lg transition-all flex flex-col items-center justify-center gap-4 group border border-gray-200">
                <div class="bg-purple-600 text-white p-4 rounded-full group-hover:scale-110 transition-transform shadow-md">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M22 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
                </div>
                <span class="font-bold text-gray-800 text-lg">利用者管理</span>
            </a>
        </div>
    </main>
</body>
</html>