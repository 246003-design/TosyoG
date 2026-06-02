<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>検索結果</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">

    <header class="bg-[#1e5641] text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-10">
        <a href="book_search.jsp" class="p-1 hover:bg-white/20 rounded-full transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">検索結果</h1>
    </header>
    
    <main class="flex-1 p-4 md:p-8 max-w-6xl mx-auto w-full">
        <div class="flex justify-between items-center mb-6">
            <%-- 件数はサーブレットから渡す --%>
            <p class="text-gray-600 font-medium">全 <span class="font-bold text-[#1e5641]">4</span> 件</p>
            <a href="book_search.jsp" class="text-sm bg-white border border-[#1e5641] text-[#1e5641] px-4 py-2 rounded-lg hover:bg-teal-50 transition-colors font-semibold">
                条件変更
            </a>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            
            <%-- JSTLでのループ処理（サーブレット実装時にコメントアウトを外して使用） --%>
            <%-- <c:forEach var="book" items="${bookList}"> --%>
            
            <!-- 書籍カードのひな形（ループで繰り返し出力する部分） -->
            <div class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden flex flex-col">
                <div class="h-48 w-full bg-gradient-to-br from-teal-700 to-slate-900 p-4 text-white relative">
                    <h3 class="font-bold text-lg leading-tight relative z-10">
                        <%-- <c:out value="${book.title}" /> --%>
                        Web UIデザイン論
                    </h3>
                    
                    <%-- 予約済みの条件分岐 --%>
                    <%-- <c:if test="${book.isReserved}"> --%>
                    <div class="absolute top-2 right-2 bg-yellow-500 text-white text-xs font-bold px-2 py-1 rounded shadow z-20">
                        予約済
                    </div>
                    <%-- </c:if> --%>
                </div>
                <div class="p-5 flex-1 flex flex-col justify-between">
                    <div>
                        <h3 class="font-bold text-lg text-gray-900 mb-1">
                            <%-- <c:out value="${book.title}" /> --%>
                            Web UIデザイン論
                        </h3>
                        <p class="text-sm text-gray-600 mb-4">
                            著者: <%-- <c:out value="${book.author}" /> --%>田中三郎
                        </p>
                    </div>
                    <!-- 詳細画面へIDを渡す -->
                    <a href="BookDetailServlet?id=123" class="w-full py-2.5 border-2 border-[#1e5641] text-[#1e5641] rounded-lg font-bold hover:bg-[#1e5641] hover:text-white transition-colors text-center block">
                        詳細
                    </a>
                </div>
            </div>
            
            <%-- </c:forEach> --%>

        </div>
    </main>
</body>
</html>