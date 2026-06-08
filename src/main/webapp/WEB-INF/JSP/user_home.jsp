<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - メニュー</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-stone-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">

    <!-- ヘッダー：深みのある緑で安心感と知性を演出 -->
    <header class="bg-[#1e5641] text-white p-4 shadow-md flex items-center justify-between sticky top-0 z-20">
        <div class="flex items-center gap-2">
            <!-- 本をモチーフにしたロゴアイコン -->
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"/><path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"/></svg>
            <h1 class="text-xl font-bold tracking-widest">図書管理システム</h1>
        </div>
        <div class="flex items-center gap-5">
            <span class="text-sm hidden sm:flex items-center gap-1.5 bg-white/10 px-3 py-1.5 rounded-full backdrop-blur-sm border border-white/20">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                <%-- <c:out value="${sessionScope.userName}" /> --%>利用者 様
            </span>
            <a href="LogoutServlet" class="text-sm font-medium hover:text-green-200 transition-colors flex items-center gap-1.5" onclick="return confirm('ログアウトしますか？');">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>
                ログアウト
            </a>
        </div>
    </header>
    
    <!-- メインコンテンツ：余裕のある余白と、浮き上がるカードUI -->
    <main class="flex-1 flex flex-col items-center justify-center p-6 gap-8">
        
        <div class="text-center mb-4">
            <h2 class="text-2xl md:text-3xl font-bold text-[#1e5641] mb-2">ようこそ、図書管理システムへ</h2>
            <p class="text-gray-500">ご希望のメニューを選択してください。</p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6 w-full max-w-3xl">
            <!-- 検索メニューカード -->
            <a href="book_search.jsp" class="group bg-white rounded-3xl p-8 shadow-sm hover:shadow-xl border border-gray-100 hover:border-[#1e5641]/30 transition-all duration-300 hover:-translate-y-1 flex flex-col items-center text-center">
                <div class="w-24 h-24 bg-green-50 rounded-full flex items-center justify-center mb-6 group-hover:bg-[#1e5641] group-hover:text-white text-[#1e5641] transition-colors duration-300 shadow-inner">
                    <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
                </div>
                <h3 class="text-2xl font-bold text-gray-800 mb-2 group-hover:text-[#1e5641] transition-colors">本を探す</h3>
                <p class="text-gray-500 text-sm">蔵書の中から希望の図書を検索し、詳細や予約を行います。</p>
            </a>

            <!-- 貸出状況メニューカード -->
            <a href="LoanStatusServlet" class="group bg-white rounded-3xl p-8 shadow-sm hover:shadow-xl border border-gray-100 hover:border-[#1e5641]/30 transition-all duration-300 hover:-translate-y-1 flex flex-col items-center text-center">
                <div class="w-24 h-24 bg-green-50 rounded-full flex items-center justify-center mb-6 group-hover:bg-[#1e5641] group-hover:text-white text-[#1e5641] transition-colors duration-300 shadow-inner">
                    <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/><path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/></svg>
                </div>
                <h3 class="text-2xl font-bold text-gray-800 mb-2 group-hover:text-[#1e5641] transition-colors">貸出状況</h3>
                <p class="text-gray-500 text-sm">現在借りている図書の一覧と、返却期限を確認できます。</p>
            </a>
        </div>
    </main>
</body>
</html>