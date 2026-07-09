<%--      検索結果画面     --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>検索結果</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-stone-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">

    <header class="bg-[#1e5641] text-white p-4 shadow-md flex items-center gap-4 sticky top-0 z-20">
        <a href="LibrarySearchServlet" class="p-2 hover:bg-white/20 rounded-full transition-colors group">
            <svg class="transform group-hover:-translate-x-1 transition-transform" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest">検索結果</h1>
    </header>
    
    <main class="flex-1 p-4 md:p-8 max-w-6xl mx-auto w-full">
        <div class="flex flex-col sm:flex-row sm:justify-between sm:items-center gap-4 mb-8">
            <p class="text-gray-600 font-medium bg-white px-4 py-2 rounded-lg border border-gray-200 inline-block shadow-sm">
                該当件数: <span class="font-bold text-[#1e5641] text-lg mx-1"><c:out value="${totalCount}" /></span> 件
            </p>
            <a href="LibrarySearchServlet" class="inline-flex items-center justify-center gap-2 text-sm bg-white border border-[#1e5641] text-[#1e5641] px-5 py-2.5 rounded-lg hover:bg-green-50 transition-colors font-bold shadow-sm">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 12a9 9 0 1 0 9-9 9.75 9.75 0 0 0-6.74 2.74L3 8"/><path d="M3 3v5h5"/></svg>
                条件を変えて再検索
            </a>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            
            <c:forEach var="book" items="${bookList}">
            
            <div class="bg-white rounded-2xl shadow-sm hover:shadow-xl border border-gray-100 overflow-hidden flex flex-col group transition-all duration-300 hover:-translate-y-1">
                
                <div class="h-48 w-full bg-gradient-to-br from-[#1e5641] to-slate-800 p-5 text-white relative overflow-hidden flex items-end">
                    <div class="absolute -top-10 -right-10 w-32 h-32 bg-white/10 rounded-full blur-xl group-hover:scale-150 transition-transform duration-700"></div>
                    
                    <h3 class="font-bold text-xl leading-tight relative z-10 text-shadow drop-shadow-md">
                        <%-- 💡 修正：図書マスタ(BookInfo)からタイトルを取得するように統一 --%>
                        <c:out value="${not empty book.bookInfo.title ? book.bookInfo.title : '図書タイトル未設定'}" />
                    </h3>
                    
                    <%-- 💡 修正：コンフリクトマーカーを削除し、予約Mapによる判定を採用 --%>
                    <c:if test="${not empty reservedMap[book.id]}">                    <div class="absolute top-3 right-3 bg-amber-500/90 backdrop-blur text-white text-xs font-black px-3 py-1.5 rounded-md shadow-sm border border-amber-400 z-20 flex items-center gap-1">
                        <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>
                        予約済
                    </div>
                    </c:if>
                </div>

                <div class="p-6 flex-1 flex flex-col justify-between bg-white">
                    <div class="mb-6">
                        <p class="text-sm font-bold text-gray-400 mb-1 tracking-wider uppercase">Author</p>
                        <p class="text-base text-gray-700 font-medium line-clamp-2">
                            <%-- 💡 修正：図書マスタ(BookInfo)から著者名を取得 --%>
                            <c:out value="${not empty book.bookInfo.authorName ? book.bookInfo.authorName : '未設定'}" />
                        </p>
                    </div>
                    
                    <%-- 💡 修正：コンフリクトマーカーを削除し、大文字の bookId に統一 --%>
                    <a href="ReserveServlet?bookId=${book.id}" class="w-full py-3 bg-gray-50 border border-gray-200 text-[#1e5641] rounded-xl font-bold hover:bg-[#1e5641] hover:text-white hover:border-[#1e5641] transition-all text-center flex items-center justify-center gap-2">
                        詳細を見る
                        <svg class="opacity-50" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M5 12h14"/><path d="m12 5 7 7-7 7"/></svg>
                    </a>
                </div>
            </div>
            
            </c:forEach>

        </div>
    </main>
</body>
</html>