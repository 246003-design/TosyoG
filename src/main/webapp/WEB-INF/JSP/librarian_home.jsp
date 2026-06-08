<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム | 司書業務ポータル</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">
    
    <!-- 信頼感を演出する深いネイビーのヘッダー -->
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center justify-between sticky top-0 z-20">
        <div class="flex items-center gap-2">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"/><path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"/></svg>
            <h1 class="text-xl font-bold tracking-widest">図書管理システム | 司書業務</h1>
        </div>
        <div class="flex items-center gap-5">
            <span class="text-sm hidden sm:flex items-center gap-1.5 bg-white/10 px-3 py-1.5 rounded-full backdrop-blur-sm border border-white/20">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                司書: <%-- <c:out value="${sessionScope.userName}" /> --%>山田太郎 様
            </span>
            <a href="LogoutServlet" class="text-sm font-medium hover:text-blue-200 transition-colors flex items-center gap-1.5" onclick="return confirm('ログアウトしますか？');">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>
                ログアウト
            </a>
        </div>
    </header>

    <main class="flex-1 p-6 md:p-10 max-w-5xl mx-auto w-full flex flex-col justify-center gap-8">
        
        <div class="text-center mb-2">
            <h2 class="text-2xl md:text-3xl font-bold text-[#1e3a8a] mb-2">司書業務ポータル</h2>
            <p class="text-gray-500">担当する業務メニューを選択してください。</p>
        </div>

        <div class="grid grid-cols-2 md:grid-cols-3 gap-6 w-full">
            <!-- 貸出 -->
            <a href="librarian_loan.jsp" class="group bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl border border-gray-100 transition-all duration-300 hover:-translate-y-1 flex flex-col items-center justify-center gap-4">
                <div class="bg-emerald-50 text-emerald-600 p-5 rounded-full group-hover:bg-emerald-600 group-hover:text-white transition-colors duration-300 shadow-inner">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 2v20"/><path d="m17 7-5-5-5 5"/></svg>
                </div>
                <span class="font-bold text-gray-800 text-lg group-hover:text-emerald-700 transition-colors">貸出</span>
            </a>
            
            <!-- 返却 -->
            <a href="librarian_return.jsp" class="group bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl border border-gray-100 transition-all duration-300 hover:-translate-y-1 flex flex-col items-center justify-center gap-4">
                <div class="bg-teal-50 text-teal-600 p-5 rounded-full group-hover:bg-teal-600 group-hover:text-white transition-colors duration-300 shadow-inner">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 2v20"/><path d="m17 17-5 5-5-5"/></svg>
                </div>
                <span class="font-bold text-gray-800 text-lg group-hover:text-teal-700 transition-colors">返却</span>
            </a>
            
            <!-- 蔵書管理 (共通画面へ) -->
            <a href="book_management_menu.jsp" class="group bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl border border-gray-100 transition-all duration-300 hover:-translate-y-1 flex flex-col items-center justify-center gap-4">
                <div class="bg-blue-50 text-[#1e3a8a] p-5 rounded-full group-hover:bg-[#1e3a8a] group-hover:text-white transition-colors duration-300 shadow-inner">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12.22 2h-.44a2 2 0 0 0-2 2v.18a2 2 0 0 1-1 1.73l-.43.25a2 2 0 0 1-2 0l-.15-.08a2 2 0 0 0-2.73.73l-.22.38a2 2 0 0 0 .73 2.73l.15.1a2 2 0 0 1 1 1.72v.51a2 2 0 0 1-1 1.74l-.15.09a2 2 0 0 0-.73 2.73l.22.38a2 2 0 0 0 2.73.73l.15-.08a2 2 0 0 1 2 0l.43.25a2 2 0 0 1 1 1.73V20a2 2 0 0 0 2 2h.44a2 2 0 0 0 2-2v-.18a2 2 0 0 1 1-1.73l.43-.25a2 2 0 0 1 2 0l.15.08a2 2 0 0 0 2.73-.73l.22-.39a2 2 0 0 0-.73-2.73l-.15-.08a2 2 0 0 1-1-1.74v-.5a2 2 0 0 1 1-1.74l.15-.09a2 2 0 0 0 .73-2.73l-.22-.38a2 2 0 0 0-2.73-.73l-.15.08a2 2 0 0 1-2 0l-.43-.25a2 2 0 0 1-1-1.73V4a2 2 0 0 0-2-2z"/><circle cx="12" cy="12" r="3"/></svg>
                </div>
                <span class="font-bold text-gray-800 text-lg group-hover:text-[#1e3a8a] transition-colors">蔵書管理</span>
            </a>
            
            <!-- 図書検索 (共通画面へ) -->
            <a href="book_search.jsp" class="group bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl border border-gray-100 transition-all duration-300 hover:-translate-y-1 flex flex-col items-center justify-center gap-4">
                <div class="bg-indigo-50 text-indigo-600 p-5 rounded-full group-hover:bg-indigo-600 group-hover:text-white transition-colors duration-300 shadow-inner">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
                </div>
                <span class="font-bold text-gray-800 text-lg group-hover:text-indigo-700 transition-colors">図書検索</span>
            </a>
            
            <!-- 延滞一覧表 (共通画面へ) -->
            <a href="overdue_list.jsp" class="group bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl border border-gray-100 transition-all duration-300 hover:-translate-y-1 flex flex-col items-center justify-center gap-4">
                <div class="bg-orange-50 text-orange-600 p-5 rounded-full group-hover:bg-orange-500 group-hover:text-white transition-colors duration-300 shadow-inner">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3Z"/><path d="M12 9v4"/><path d="M12 17h.01"/></svg>
                </div>
                <span class="font-bold text-gray-800 text-lg group-hover:text-orange-600 transition-colors">延滞一覧表</span>
            </a>
            
            <!-- 利用者管理 (通常は管理者機能だが、PDFに存在するため配置) -->
            <a href="#" class="group bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl border border-gray-100 transition-all duration-300 hover:-translate-y-1 flex flex-col items-center justify-center gap-4 opacity-75 hover:opacity-100">
                <div class="bg-purple-50 text-purple-600 p-5 rounded-full group-hover:bg-purple-600 group-hover:text-white transition-colors duration-300 shadow-inner">
                    <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M22 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
                </div>
                <span class="font-bold text-gray-800 text-lg group-hover:text-purple-700 transition-colors">利用者管理</span>
            </a>
        </div>
    </main>
</body>
</html>