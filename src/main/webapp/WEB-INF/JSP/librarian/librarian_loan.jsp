<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // 追加：サーブレットから渡されたメッセージを取得
    String successMessage = (String) request.getAttribute("successMessage");
    String errorMessage = (String) request.getAttribute("errorMessage");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 貸出処理</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center gap-4 sticky top-0 z-20">
        <a href="HomeServlet" class="p-2 hover:bg-white/20 rounded-full transition-colors group">
            <svg class="transform group-hover:-translate-x-1 transition-transform" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest">貸出処理</h1>
    </header>
    
    <main class="flex-1 p-4 md:p-8 max-w-3xl mx-auto w-full relative">
        
        <%-- 修正：完了メッセージがある場合のみ表示する --%>
        <% if (successMessage != null && !successMessage.isEmpty()) { %>
        <div class="mb-6 bg-emerald-50 border border-emerald-200 text-emerald-800 px-5 py-4 rounded-xl flex items-center gap-3 font-bold shadow-sm">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22c5.523 0 10-4.477 10-10S17.523 2 12 2 2 6.477 2 12s4.477 10 10 10z"/><path d="m9 12 2 2 4-4"/></svg>
            <%= successMessage %>
        </div>
        <% } %>

        <div class="bg-white p-8 md:p-10 rounded-3xl shadow-sm border border-gray-100">
            
            <div class="flex items-center gap-3 mb-8 pb-4 border-b border-gray-100">
                <div class="bg-emerald-50 p-2 rounded-lg text-emerald-600">
                    <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 2v20"/><path d="m17 7-5-5-5 5"/></svg>
                </div>
                <h2 class="text-xl font-bold text-gray-800">貸出スキャン</h2>
            </div>

            <%-- 修正：エラーメッセージがある場合のみ表示する --%>
            <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
            <div class="mb-8 bg-red-50 border border-red-100 p-5 rounded-2xl shadow-sm">
                <div class="flex items-start gap-4">
                    <div class="bg-red-100 p-2 rounded-full text-red-600 shrink-0">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
                    </div>
                    <div>
                        <h4 class="text-red-800 font-bold text-lg mb-1">貸出エラー</h4>
                        <p class="text-sm text-red-700 leading-relaxed">
                            <%= errorMessage %>
                        </p>
                    </div>
                </div>
            </div>
            <% } %>

            <%-- actionを "LendServlet" に変更 --%>
            <form action="LendServlet" method="POST" class="space-y-8">
                <div>
                    <label class="block text-sm font-bold text-gray-600 mb-3 uppercase tracking-wider">1. 利用者ID</label>
                    <input 
                        type="text" 
                        name="userId"
                        placeholder="利用者IDを入力" 
                        class="w-full p-5 bg-gray-50 border border-gray-200 rounded-xl focus:bg-white focus:outline-none focus:ring-4 focus:ring-[#1e3a8a]/20 focus:border-[#1e3a8a] transition-all text-xl font-mono tracking-widest placeholder-gray-400 text-gray-800 font-bold shadow-inner"
                        required
                        autofocus
                    />
                </div>
                <div>
                    <label class="block text-sm font-bold text-gray-600 mb-3 uppercase tracking-wider">2.蔵書ID</label>
                    <input 
                        type="text" 
                        name="bookId"
                        placeholder="図書IDを入力" 
                        class="w-full p-5 bg-gray-50 border border-gray-200 rounded-xl focus:bg-white focus:outline-none focus:ring-4 focus:ring-[#1e3a8a]/20 focus:border-[#1e3a8a] transition-all text-xl font-mono tracking-widest placeholder-gray-400 text-gray-800 font-bold shadow-inner"
                        required
                    />
                </div>

                <div class="flex flex-col sm:flex-row gap-4 pt-8 border-t border-gray-100">
                    <a href="HomeServlet" class="w-full sm:w-1/3 py-4 border-2 border-gray-200 text-gray-600 rounded-xl font-bold text-center hover:bg-gray-50 hover:text-gray-900 transition-colors">戻る</a>
                    <button type="submit" class="w-full sm:w-2/3 py-4 bg-[#1e3a8a] text-white rounded-xl font-bold text-lg hover:bg-blue-900 shadow-lg shadow-blue-900/20 active:scale-95 transition-all flex items-center justify-center gap-2" onclick="return confirm('図書の貸出を確定します。よろしいですか？');">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"/></svg>
                        貸出内容を確定
                    </button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>