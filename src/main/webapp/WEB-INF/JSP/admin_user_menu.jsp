<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 利用者管理メニュー</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    <!-- ヘッダー -->
    <header class="bg-gray-900 text-white p-4 shadow-md flex items-center justify-between sticky top-0 z-20 border-b border-gray-700">
        <div class="flex items-center gap-3">
            <a href="admin_home.jsp" class="p-1 hover:bg-gray-700 rounded-full transition-colors text-white">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
            </a>
            <h1 class="text-xl font-bold tracking-wider">利用者管理</h1>
        </div>
    </header>

    <main class="flex-1 p-6 max-w-3xl mx-auto w-full flex flex-col justify-center gap-6">
        <!-- 登録へのリンク -->
        <a href="admin_user_register.jsp" class="w-full bg-white p-8 rounded-xl shadow border border-gray-200 flex items-center justify-between group hover:border-gray-900 transition-colors">
            <div class="flex items-center gap-5">
                <div class="bg-gray-100 p-4 rounded-full text-gray-800 group-hover:bg-gray-900 group-hover:text-white transition-colors">
                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><line x1="19" y1="8" x2="19" y2="14"/><line x1="22" y1="11" x2="16" y2="11"/></svg>
                </div>
                <div class="text-left">
                    <h2 class="text-xl font-bold text-gray-900">利用者登録</h2>
                    <p class="text-gray-500 text-sm mt-1">新規アカウントの作成、IDの発行</p>
                </div>
            </div>
            <svg class="text-gray-400 group-hover:text-gray-900 transition-colors" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m9 18 6-6-6-6"/></svg>
        </a>

        <!-- 検索・情報更新へのリンク(サーブレット経由を想定) -->
        <a href="UserSearchServlet" class="w-full bg-white p-8 rounded-xl shadow border border-gray-200 flex items-center justify-between group hover:border-gray-900 transition-colors">
            <div class="flex items-center gap-5">
                <div class="bg-gray-100 p-4 rounded-full text-gray-800 group-hover:bg-gray-900 group-hover:text-white transition-colors">
                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/><path d="M16 21v-2a4 4 0 0 0-4-4H6"/></svg>
                </div>
                <div class="text-left">
                    <h2 class="text-xl font-bold text-gray-900">検索・情報更新</h2>
                    <p class="text-gray-500 text-sm mt-1">登録されている利用者情報の編集と削除</p>
                </div>
            </div>
            <svg class="text-gray-400 group-hover:text-gray-900 transition-colors" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m9 18 6-6-6-6"/></svg>
        </a>
    </main>
</body>
</html>