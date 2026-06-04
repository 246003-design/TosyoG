<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 延滞一覧表</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    <%-- 権限ごとの設定 --%>
    <%-- <c:set var="themeBg" value="${sessionScope.role == 'ADMIN' ? 'bg-gray-900' : 'bg-[#1e3a8a]'}" /> --%>
    <%-- <c:set var="homeUrl" value="${sessionScope.role == 'ADMIN' ? 'AdminHomeServlet' : 'LibrarianHomeServlet'}" /> --%>
    
    <c:set var="themeBg" value="bg-[#1e3a8a]" /> <%-- モック用 --%>
    <c:set var="homeUrl" value="LibrarianHomeServlet" />

    <header class="${themeBg} text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-10">
        <a href="${homeUrl}" class="p-1 hover:bg-white/20 rounded-full transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">延滞一覧表</h1>
    </header>
    
    <main class="flex-1 p-4 md:p-8 max-w-5xl mx-auto w-full">
        <div class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
            
            <form action="OverdueServlet" method="GET" class="p-4 border-b border-gray-200 bg-gray-50 flex flex-col md:flex-row gap-4 justify-between items-center">
                <div class="relative w-full max-w-md flex">
                    <input type="text" name="searchQuery" placeholder="ユーザーID / ISBN で検索" class="w-full pl-3 pr-4 py-2 border border-gray-300 rounded-l focus:outline-none focus:ring-2 focus:ring-red-500" />
                    <button type="submit" class="px-6 py-2 bg-gray-800 text-white font-bold rounded-r hover:bg-gray-700 transition-colors">
                        検索実行
                    </button>
                </div>
            </form>

            <div class="overflow-x-auto">
                <table class="w-full text-left border-collapse min-w-max">
                    <thead class="bg-red-50 text-red-900 border-b-2 border-red-200">
                        <tr>
                            <th class="p-4 font-bold text-sm">タイトル</th>
                            <th class="p-4 font-bold text-sm">氏名</th>
                            <th class="p-4 font-bold text-sm">ユーザーID</th>
                            <th class="p-4 font-bold text-sm">返却予定日</th>
                            
                            <%-- 司書の場合のみ「状況(超過)」列を表示 --%>
                            <%-- <c:if test="${sessionScope.role == 'LIBRARIAN'}"> --%>
                            <th class="p-4 font-bold text-sm text-center">状況</th>
                            <%-- </c:if> --%>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        <%-- <c:forEach var="overdue" items="${overdueList}"> --%>
                        <tr class="hover:bg-red-50 transition-colors bg-white">
                            <td class="p-4 font-bold text-gray-900">UIデザイン論</td>
                            <td class="p-4">
                                <%-- ★改善点: 管理者の場合は利用者詳細へのリンクにする。司書はテキストのみ。 --%>
                                <%-- <c:choose> --%>
                                    <%-- <c:when test="${sessionScope.role == 'ADMIN'}"> --%>
                                        <a href="UserDetailServlet?userId=246246" class="text-blue-600 hover:underline font-bold" title="利用者情報を確認">広島 花子</a>
                                    <%-- </c:when> --%>
                                    <%-- <c:otherwise> --%>
                                        <%-- <span class="font-semibold text-gray-700">広島 花子</span> --%>
                                    <%-- </c:otherwise> --%>
                                <%-- </c:choose> --%>
                            </td>
                            <td class="p-4 text-gray-500 font-mono text-sm">246246</td>
                            <td class="p-4 text-red-600 font-bold">2024/05/22</td>
                            
                            <%-- 司書の場合のみ「超過」バッジを表示 --%>
                            <%-- <c:if test="${sessionScope.role == 'LIBRARIAN'}"> --%>
                            <td class="p-4 text-center">
                                <span class="px-3 py-1 bg-red-100 text-red-700 rounded-full text-xs font-bold border border-red-200">超過</span>
                            </td>
                            <%-- </c:if> --%>
                        </tr>
                        <%-- </c:forEach> --%>
                    </tbody>
                </table>
            </div>
            
            <div class="p-4 bg-gray-50 text-center border-t border-gray-200">
                <a href="${homeUrl}" class="inline-block px-8 py-2 border-2 border-gray-300 text-gray-700 font-bold rounded hover:bg-white transition-colors">
                    戻る
                </a>
            </div>
        </div>
    </main>
</body>
</html>