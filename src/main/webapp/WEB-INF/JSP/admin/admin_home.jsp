
　　　　　　　　　　　　　　　　　　　<%--      管理者メニュー画面　　　　 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム | 管理者ポータル</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">
    
    <!-- 権威と洗練を感じさせるチャコールグレーのヘッダー -->
    <header class="bg-gray-900 text-white p-4 shadow-md flex items-center justify-between sticky top-0 z-20">
        <div class="flex items-center gap-2">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="3" width="18" height="18" rx="2" ry="2"/><path d="M9 3v18"/><path d="M15 3v18"/></svg>
            <h1 class="text-xl font-bold tracking-widest">図書管理システム | 管理者ポータル</h1>
        </div>
        <div class="flex items-center gap-5">
            <span class="text-sm hidden sm:flex items-center gap-1.5 bg-white/10 px-3 py-1.5 rounded-full backdrop-blur-sm border border-white/20">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                管理者: <%-- <c:out value="${sessionScope.adminName}" /> --%>System Admin 様
            </span>
            <a href="LogoutServlet" class="text-sm font-medium hover:text-gray-300 transition-colors flex items-center gap-1.5" onclick="return confirm('ログアウトしますか？');">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>
                ログアウト
            </a>
        </div>
    </header>

    <main class="flex-1 p-6 md:p-10 max-w-5xl mx-auto w-full flex flex-col justify-center gap-8">
        
        <div class="text-center mb-2">
            <h2 class="text-2xl md:text-3xl font-bold text-gray-900 mb-2">管理者ダッシュボード</h2>
            <p class="text-gray-500">システム全体の管理メニューを選択してください。</p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6 w-full">
            <!-- 蔵書管理 -->
            <a href="BookManagementMenuServlet" class="group bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl border border-gray-100 hover:border-gray-900/30 transition-all duration-300 hover:-translate-y-1 flex items-center gap-6">
                <div class="bg-gray-50 text-gray-600 p-5 rounded-full group-hover:bg-gray-900 group-hover:text-white transition-colors duration-300 shadow-inner shrink-0">
                    <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12.22 2h-.44a2 2 0 0 0-2 2v.18a2 2 0 0 1-1 1.73l-.43.25a2 2 0 0 1-2 0l-.15-.08a2 2 0 0 0-2.73.73l-.22.38a2 2 0 0 0 .73 2.73l.15.1a2 2 0 0 1 1 1.72v.51a2 2 0 0 1-1 1.74l-.15.09a2 2 0 0 0-.73 2.73l.22.38a2 2 0 0 0 2.73.73l.15-.08a2 2 0 0 1 2 0l.43.25a2 2 0 0 1 1 1.73V20a2 2 0 0 0 2 2h.44a2 2 0 0 0 2-2v-.18a2 2 0 0 1 1-1.73l.43-.25a2 2 0 0 1 2 0l.15.08a2 2 0 0 0 2.73-.73l.22-.39a2 2 0 0 0-.73-2.73l-.15-.08a2 2 0 0 1-1-1.74v-.5a2 2 0 0 1 1-1.74l.15-.09a2 2 0 0 0 .73-2.73l-.22-.38a2 2 0 0 0-2.73-.73l-.15.08a2 2 0 0 1-2 0l-.43-.25a2 2 0 0 1-1-1.73V4a2 2 0 0 0-2-2z"/><circle cx="12" cy="12" r="3"/></svg>
                </div>
                <div>
                    <span class="block font-bold text-gray-900 text-2xl mb-1 group-hover:text-gray-900 transition-colors">蔵書管理</span>
                    <span class="block text-gray-500 text-sm">書籍データの登録・更新・削除</span>
                </div>
            </a>

            <!-- 利用者管理 -->
            <a href="UserManagementMenuServlet" class="group bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl border border-gray-100 hover:border-gray-900/30 transition-all duration-300 hover:-translate-y-1 flex items-center gap-6">
                <div class="bg-gray-50 text-gray-600 p-5 rounded-full group-hover:bg-gray-900 group-hover:text-white transition-colors duration-300 shadow-inner shrink-0">
                    <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M22 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
                </div>
                <div>
                    <span class="block font-bold text-gray-900 text-2xl mb-1 group-hover:text-gray-900 transition-colors">利用者管理</span>
                    <span class="block text-gray-500 text-sm">アカウントの発行と権限制御</span>
                </div>
            </a>

            <!-- 図書検索 -->
            <a href="LibrarySearchServlet" class="group bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl border border-gray-100 hover:border-gray-900/30 transition-all duration-300 hover:-translate-y-1 flex items-center gap-6">
                <div class="bg-gray-50 text-gray-600 p-5 rounded-full group-hover:bg-gray-900 group-hover:text-white transition-colors duration-300 shadow-inner shrink-0">
                    <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
                </div>
                <div>
                    <span class="block font-bold text-gray-900 text-2xl mb-1 group-hover:text-gray-900 transition-colors">図書検索</span>
                    <span class="block text-gray-500 text-sm">システム全体の蔵書状況を検索</span>
                </div>
            </a>

            <!-- 延滞一覧表 -->
            <a href="OverdueServlet" class="group bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl border border-gray-100 hover:border-gray-900/30 transition-all duration-300 hover:-translate-y-1 flex items-center gap-6">
                <div class="bg-gray-50 text-gray-600 p-5 rounded-full group-hover:bg-gray-900 group-hover:text-white transition-colors duration-300 shadow-inner shrink-0">
                    <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3Z"/><path d="M12 9v4"/><path d="M12 17h.01"/></svg>
                </div>
                <div>
                    <span class="block font-bold text-gray-900 text-2xl mb-1 group-hover:text-gray-900 transition-colors">延滞一覧表</span>
                    <span class="block text-gray-500 text-sm">期限超過者のリストと詳細確認</span>
                </div>
            </a>
        </div>
    </main>
</body>
</html>