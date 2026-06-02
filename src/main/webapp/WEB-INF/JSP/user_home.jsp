<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - メニュー</title>
    <!-- スタイリング用のTailwind CSSのみ読み込みます（JSフレームワークではありません） -->
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">

    <!-- ヘッダー -->
    <header class="bg-[#1e5641] text-white p-4 shadow-md flex items-center justify-between sticky top-0 z-10">
        <h1 class="text-xl font-bold tracking-wider">図書管理システム</h1>
        <div class="flex items-center gap-4">
            <span class="text-sm hidden sm:flex items-center gap-1">
                <!-- ユーザーアイコン(SVG) -->
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                <%-- サーブレットから渡されたユーザー名を表示する例 --%>
                <%-- <c:out value="${sessionScope.userName}" /> 様 --%>
                利用者 様
            </span>
            <!-- ログアウトは専用のサーブレットへPOSTまたはGETで遷移 -->
            <a href="LogoutServlet" class="text-sm hover:text-gray-300 transition-colors flex items-center gap-1">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>
                ログアウト
            </a>
        </div>
    </header>
    
    <!-- メインコンテンツ -->
    <main class="flex-1 flex flex-col items-center justify-center p-6 gap-6">
        <!-- 本を探す画面へのリンク -->
        <a href="book_search.jsp" class="w-full max-w-sm bg-white border border-gray-200 hover:border-[#1e5641] hover:shadow-lg transition-all p-8 rounded-2xl flex flex-col items-center justify-center gap-4 text-[#1e5641] group">
            <div class="bg-teal-50 p-4 rounded-full group-hover:bg-[#1e5641] group-hover:text-white transition-colors">
                <!-- 検索アイコン(SVG) -->
                <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
            </div>
            <span class="text-xl font-bold tracking-wider">本を探す</span>
        </a>

        <!-- 貸出状況画面へのリンク -->
        <a href="LoanStatusServlet" class="w-full max-w-sm bg-white border border-gray-200 hover:border-[#1e5641] hover:shadow-lg transition-all p-8 rounded-2xl flex flex-col items-center justify-center gap-4 text-[#1e5641] group">
            <div class="bg-teal-50 p-4 rounded-full group-hover:bg-[#1e5641] group-hover:text-white transition-colors">
                <!-- 本アイコン(SVG) -->
                <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/><path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/></svg>
            </div>
            <span class="text-xl font-bold tracking-wider">貸出状況</span>
        </a>
    </main>
</body>
</html>