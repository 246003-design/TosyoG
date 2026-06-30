<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 蔵書管理メニュー</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    
    <!-- ヘッダー -->
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center justify-between sticky top-0 z-20">
        <div class="flex items-center gap-3">
            <a href="HomeServlet" class="p-1 hover:bg-white/20 rounded-full transition-colors" title="ホームに戻る">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
            </a>
            <h1 class="text-xl font-bold tracking-wider">蔵書管理</h1>
        </div>
        <div>
            <!-- ログアウトボタン -->
            <a href="LogoutServlet" class="text-sm hover:underline hover:text-blue-200 transition-colors">ログアウト</a>
        </div>
    </header>

    <!-- メインメニュー -->
    <main class="flex-1 p-6 max-w-3xl mx-auto w-full flex flex-col justify-center gap-6">
        
        <!-- 蔵書登録ボタン：BookRegisterServletへ遷移 -->
        <a href="BookRegisterServlet" class="group w-full bg-white p-8 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md hover:border-blue-200 transition-all flex items-center gap-6">
            <div class="p-4 bg-blue-50 rounded-xl text-blue-700 group-hover:bg-[#1e3a8a] group-hover:text-white transition-colors">
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M4 19.5v-15A2.5 2.5 0 0 1 6.5 2H20v20H6.5a2.5 2.5 0 0 1 0-5H20"/><path d="M12 8v6"/><path d="M9 11h6"/></svg>
            </div>
            <div class="flex-1">
                <h2 class="text-2xl font-bold text-gray-800 mb-2">蔵書登録</h2>
                <p class="text-gray-500">新しい本をシステムに登録します（ISBNからの自動入力対応）</p>
            </div>
            <div class="text-gray-300 group-hover:text-[#1e3a8a] transition-colors">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m9 18 6-6-6-6"/></svg>
            </div>
        </a>

        <!-- 蔵書更新・削除ボタン：BookListServletへ遷移 -->
        <a href="BookListServlet" class="group w-full bg-white p-8 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md hover:border-blue-200 transition-all flex items-center gap-6">
            <div class="p-4 bg-blue-50 rounded-xl text-blue-700 group-hover:bg-[#1e3a8a] group-hover:text-white transition-colors">
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12.22 2h-.44a2 2 0 0 0-2 2v.18a2 2 0 0 1-1 1.73l-.43.25a2 2 0 0 1-2 0l-.15-.08a2 2 0 0 0-2.73.73l-.22.38a2 2 0 0 0 .73 2.73l.15.1a2 2 0 0 1 1 1.72v.51a2 2 0 0 1-1 1.74l-.15.09a2 2 0 0 0-.73 2.73l.22.38a2 2 0 0 0 2.73.73l.15-.08a2 2 0 0 1 2 0l.43.25a2 2 0 0 1 1 1.73V20a2 2 0 0 0 2 2h.44a2 2 0 0 0 2-2v-.18a2 2 0 0 1 1-1.73l.43-.25a2 2 0 0 1 2 0l.15.08a2 2 0 0 0 2.73-.73l.22-.39a2 2 0 0 0-.73-2.73l-.15-.08a2 2 0 0 1-1-1.74v-.5a2 2 0 0 1 1-1.74l.15-.09a2 2 0 0 0 .73-2.73l-.22-.38a2 2 0 0 0-2.73-.73l-.15.08a2 2 0 0 1-2 0l-.43-.25a2 2 0 0 1-1-1.73V4a2 2 0 0 0-2-2z"/><circle cx="12" cy="12" r="3"/></svg>
            </div>
            <div class="flex-1">
                <h2 class="text-2xl font-bold text-gray-800 mb-2">蔵書一覧・更新・削除</h2>
                <p class="text-gray-500">登録されている本の内容を修正したり、システムから削除します</p>
            </div>
            <div class="text-gray-300 group-hover:text-[#1e3a8a] transition-colors">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m9 18 6-6-6-6"/></svg>
            </div>
        </a>

    </main>
</body>
</html>