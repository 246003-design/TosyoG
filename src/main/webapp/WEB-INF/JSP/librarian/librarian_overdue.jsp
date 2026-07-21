<%--      司書　延滞一覧画面      --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 延滞一覧表</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center gap-4 sticky top-0 z-20">
        <a href="HomeServlet" class="p-2 hover:bg-white/20 rounded-full transition-colors group">
            <svg class="transform group-hover:-translate-x-1 transition-transform" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest">延滞一覧表</h1>
    </header>
    
    <main class="flex-1 p-4 md:p-8 max-w-5xl mx-auto w-full">
        <div class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
            
            <form action="OverdueServlet" method="GET" class="p-4 border-b border-gray-200 bg-gray-50 flex flex-col md:flex-row gap-4 justify-between items-center">
                <div class="relative w-full max-w-md flex">
                    <input type="text" name="searchQuery" placeholder="ユーザーID・氏名で検索" class="w-full pl-3 pr-4 py-2 border border-gray-300 rounded-l focus:outline-none focus:ring-2 focus:ring-orange-500" />
                    <button type="submit" class="px-6 py-2 bg-gray-800 text-white font-bold rounded-r hover:bg-gray-700 transition-colors border border-gray-800">
                        検索
                    </button>
                </div>
            </form>

            <div class="overflow-x-auto">
                <table class="w-full text-left border-collapse min-w-max">
                    <thead class="bg-orange-100 text-orange-900 border-b-2 border-orange-200">
                        <tr>
                            <th class="p-4 font-bold text-sm">タイトル</th>
                            <th class="p-4 font-bold text-sm">氏名</th>
                            <th class="p-4 font-bold text-sm">ユーザーID</th>
                            <th class="p-4 font-bold text-sm">返却予定日</th>
                            <th class="p-4 font-bold text-sm text-center">状況</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        <c:forEach var="overdue" items="${overdueList}">
                            <tr class="hover:bg-orange-50 transition-colors bg-white">
                                <td class="p-4 font-bold text-gray-800"><c:out value="${overdue.title}" /></td>
                                
                                <%-- 管理者のみリンク表示。司書はテキストのみ --%>
                                <td class="p-4">
                                    <c:choose>
                                        <c:when test="${sessionScope.loginUser.role == 2}">
                                            <a href="UserDetailServlet?userId=${overdue.user_id}" class="text-blue-600 hover:underline font-semibold"><c:out value="${overdue.user_name}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="font-semibold text-gray-700"><c:out value="${overdue.user_name}" /></span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                
                                <td class="p-4 text-gray-500 font-mono text-sm"><c:out value="${overdue.user_id}" /></td>
                                
                                <td class="p-4 text-red-600 font-bold"><c:out value="${overdue.due_date}" /></td>
                                
                                <td class="p-4 text-center">
                                    <span class="px-3 py-1 bg-red-100 text-red-700 rounded-full text-xs font-bold border border-red-200">
                                        超過
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>
    </main>
</body>
</html>