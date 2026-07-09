<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 返却処理</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        /* 簡単なフェードアウトアニメーション（追加） */
        .fade-out {
            animation: fadeOut 3s forwards;
            animation-delay: 2s; /* 2秒待ってから消え始める */
        }
        @keyframes fadeOut {
            from { opacity: 1; }
            to { opacity: 0; visibility: hidden; }
        }
    </style>
</head>
<body class="bg-slate-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center gap-4 sticky top-0 z-20">
        <a href="HomeServlet" class="p-2 hover:bg-white/20 rounded-full transition-colors group">
            <svg class="transform group-hover:-translate-x-1 transition-transform" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest">返却処理</h1>
    </header>
    
    <main class="flex-1 p-4 md:p-8 max-w-2xl w-full mx-auto flex flex-col justify-center">
        
        <div class="bg-white p-8 md:p-12 rounded-3xl shadow-xl border border-gray-100 relative overflow-hidden">
            <div class="absolute top-0 left-0 w-full h-2 bg-teal-500"></div>
            
            <div class="text-center mb-10">
                <div class="w-20 h-20 bg-teal-50 text-teal-600 rounded-2xl flex items-center justify-center mx-auto mb-6 transform rotate-3 shadow-sm border border-teal-100">
                    <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 9h18v10a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V9Z"/><path d="m3 9 2.45-4.9A2 2 0 0 1 7.24 3h9.52a2 2 0 0 1 1.8 1.1L21 9"/><path d="M12 3v6"/></svg>
                </div>
                <h2 class="text-3xl font-black text-gray-800 tracking-tight">図書の返却</h2>
                <p class="text-gray-500 mt-3 font-medium">返却する図書のバーコードをスキャン、<br class="md:hidden">またはIDを入力してください</p>
            </div>

            <%-- 💡修正: 成功メッセージ（successMessageが空でない時だけ表示） --%>
            <c:if test="${not empty successMessage}">
            <div class="mb-8 p-4 bg-teal-50 border-l-4 border-teal-500 text-teal-700 rounded-r-xl shadow-sm flex items-center gap-3 fade-out">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-teal-500"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><polyline points="22 4 12 14.01 9 11.01"/></svg>
                <span class="font-bold"><c:out value="${successMessage}" /></span>
            </div>
            </c:if>

            <%-- 💡修正: エラーメッセージ（errorMessageが空でない時だけ表示） --%>
            <c:if test="${not empty errorMessage}">
            <div class="mb-8 p-4 bg-red-50 border-l-4 border-red-500 text-red-700 rounded-r-xl shadow-sm flex items-center gap-3">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-red-500"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
                <span class="font-bold"><c:out value="${errorMessage}" /></span>
            </div>
            </c:if>

            <form action="ReturnServlet" method="POST">
                <div class="relative w-full mb-10">
                    <input 
                        type="text" 
                        name="bookId"
                        autofocus
                        placeholder="蔵書ID" 
                        class="w-full p-6 border-2 border-transparent bg-gray-50 rounded-2xl text-center text-2xl font-mono tracking-widest text-gray-800 font-bold shadow-inner focus:outline-none focus:bg-white focus:border-teal-500 focus:ring-4 focus:ring-teal-500/20 transition-all placeholder-gray-300"
                        required
                    />
                </div>

                <div class="flex flex-col sm:flex-row gap-4">
                    <a href="HomeServlet" class="w-full sm:w-1/3 py-4 border-2 border-gray-200 text-gray-600 rounded-xl font-bold text-center hover:bg-gray-50 hover:text-gray-900 transition-colors">戻る</a>
                    <button type="submit" class="w-full sm:w-2/3 py-4 bg-teal-600 text-white rounded-xl font-bold text-lg hover:bg-teal-700 shadow-lg shadow-teal-600/20 active:scale-95 transition-all flex items-center justify-center gap-2">
                        <span>返却を確定</span>
                    </button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>