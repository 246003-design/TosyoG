<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 利用者管理メニュー</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">
    <header class="bg-gray-900 text-white p-4 shadow-md flex items-center gap-4 sticky top-0 z-20">
        <a href="admin_home.jsp" class="p-2 hover:bg-white/20 rounded-full transition-colors group">
            <svg class="transform group-hover:-translate-x-1 transition-transform" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest">利用者管理</h1>
    </header>

    <main class="flex-1 p-6 md:p-10 max-w-3xl mx-auto w-full flex flex-col justify-center gap-6">
        
        <a href="admin_user_register.jsp" class="group bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl border border-gray-100 transition-all duration-300 hover:-translate-y-1 flex items-center justify-between">
            <div class="flex items-center gap-6">
                <div class="bg-blue-50 text-blue-600 p-5 rounded-full group-hover:bg-blue-600 group-hover:text-white transition-colors duration-300 shadow-inner">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><line x1="19" y1="8" x2="19" y2="14"/><line x1="22" y1="11" x2="16" y2="11"/></svg>
                </div>
                <div>
                    <h2 class="text-2xl font-bold text-gray-900 mb-1 group-hover:text-blue-700 transition-colors">新規アカウント登録</h2>
                    <p class="text-gray-500 text-sm">利用者および司書のアカウントを発行します。</p>
                </div>
            </div>
            <svg class="text-gray-300 group-hover:text-blue-600 transition-colors transform group-hover:translate-x-1" xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m9 18 6-6-6-6"/></svg>
        </a>

        <!-- サーブレット経由で一覧画面へ -->
        <a href="UserSearchServlet" class="group bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl border border-gray-100 transition-all duration-300 hover:-translate-y-1 flex items-center justify-between">
            <div class="flex items-center gap-6">
                <div class="bg-indigo-50 text-indigo-600 p-5 rounded-full group-hover:bg-indigo-600 group-hover:text-white transition-colors duration-300 shadow-inner">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/><path d="M16 21v-2a4 4 0 0 0-4-4H6"/></svg>
                </div>
                <div>
                    <h2 class="text-2xl font-bold text-gray-900 mb-1 group-hover:text-indigo-700 transition-colors">検索・情報更新</h2>
                    <p class="text-gray-500 text-sm">登録済みユーザーのステータス変更やパスワード再設定。</p>
                </div>
            </div>
            <svg class="text-gray-300 group-hover:text-indigo-600 transition-colors transform group-hover:translate-x-1" xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m9 18 6-6-6-6"/></svg>
        </a>

    </main>
</body>
</html>