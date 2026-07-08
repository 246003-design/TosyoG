<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 蔵書管理メニュー</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center justify-between sticky top-0 z-20">
        <div class="flex items-center gap-3">
            <a href="HomeServlet" class="p-1 hover:bg-white/20 rounded-full transition-colors">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
            </a>
            <h1 class="text-xl font-bold tracking-wider">蔵書管理</h1>
        </div>
    </header>

    <main class="flex-1 p-6 max-w-3xl mx-auto w-full flex flex-col justify-center gap-6">
        <a href="librarian_book_register.jsp" class="w-full bg-white p-8 rounded-xl shadow-sm hover:shadow-md border border-gray-200 flex items-center justify-between group hover:border-[#1e3a8a] transition-all">
            <div class="flex items-center gap-4">
                <div class="bg-blue-100 p-4 rounded-full text-blue-700 group-hover:bg-[#1e3a8a] group-hover:text-white transition-colors">
                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><path d="M8 12h8"/><path d="M12 8v8"/></svg>
                </div>
                <div class="text-left">
                    <h2 class="text-xl font-bold text-gray-800">蔵書登録</h2>
                    <p class="text-gray-500 text-sm mt-1">手動入力または一括CSV読み込みによる新規登録</p>
                </div>
            </div>
            <svg class="text-gray-400 group-hover:text-[#1e3a8a] transition-colors" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m9 18 6-6-6-6"/></svg>
        </a>

        <!-- サーブレットを経由して一覧(librarian_book_list.jsp)を表示させるためのリンク -->
        <a href="BookListServlet" class="w-full bg-white p-8 rounded-xl shadow-sm hover:shadow-md border border-gray-200 flex items-center justify-between group hover:border-[#1e3a8a] transition-all">
            <div class="flex items-center gap-4">
                <div class="bg-blue-100 p-4 rounded-full text-blue-700 group-hover:bg-[#1e3a8a] group-hover:text-white transition-colors">
                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12.22 2h-.44a2 2 0 0 0-2 2v.18a2 2 0 0 1-1 1.73l-.43.25a2 2 0 0 1-2 0l-.15-.08a2 2 0 0 0-2.73.73l-.22.38a2 2 0 0 0 .73 2.73l.15.1a2 2 0 0 1 1 1.72v.51a2 2 0 0 1-1 1.74l-.15.09a2 2 0 0 0-.73 2.73l.22.38a2 2 0 0 0 2.73.73l.15-.08a2 2 0 0 1 2 0l.43.25a2 2 0 0 1 1 1.73V20a2 2 0 0 0 2 2h.44a2 2 0 0 0 2-2v-.18a2 2 0 0 1 1-1.73l.43-.25a2 2 0 0 1 2 0l.15.08a2 2 0 0 0 2.73-.73l.22-.39a2 2 0 0 0-.73-2.73l-.15-.08a2 2 0 0 1-1-1.74v-.5a2 2 0 0 1 1-1.74l.15-.09a2 2 0 0 0 .73-2.73l-.22-.38a2 2 0 0 0-2.73-.73l-.15.08a2 2 0 0 1-2 0l-.43-.25a2 2 0 0 1-1-1.73V4a2 2 0 0 0-2-2z"/><circle cx="12" cy="12" r="3"/></svg>
                </div>
                <div class="text-left">
                    <h2 class="text-xl font-bold text-gray-800">更新・検索・状態変更</h2>
                    <p class="text-gray-500 text-sm mt-1">登録済み蔵書情報の検索、編集、および削除</p>
                </div>
            </div>
            <svg class="text-gray-400 group-hover:text-[#1e3a8a] transition-colors" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m9 18 6-6-6-6"/></svg>
        </a>
    </main>
</body>
</html>