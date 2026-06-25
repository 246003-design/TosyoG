<%-- 利用者検索一覧画面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 利用者一覧・検索</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">
    <header class="bg-gray-900 text-white p-4 shadow-md flex items-center gap-4 sticky top-0 z-20">
        <a href="admin_user_menu.jsp" class="p-2 hover:bg-white/20 rounded-full transition-colors group">
            <svg class="transform group-hover:-translate-x-1 transition-transform" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest">利用者検索・情報更新</h1>
    </header>

    <main class="flex-1 p-4 md:p-8 max-w-6xl mx-auto w-full">
        <div class="bg-white rounded-3xl shadow-sm border border-gray-100 overflow-hidden flex flex-col">
            
            <form action="UserListServlet" method="GET" class="p-6 border-b border-gray-100 bg-gray-50/50 flex flex-col sm:flex-row gap-4 justify-between items-center">
                <div class="relative w-full sm:w-96 flex">
                    <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
                        <svg class="text-gray-400" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
                    </div>
                    <input type="text" name="searchQuery" placeholder="氏名 または ユーザーID" class="w-full pl-12 pr-4 py-3 border border-gray-200 rounded-l-xl focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent transition-all" />
                    <button type="submit" class="px-8 py-3 bg-gray-900 text-white font-bold rounded-r-xl hover:bg-black transition-colors border border-gray-900 shadow-sm">
                        検索
                    </button>
                </div>
            </form>

            <div class="overflow-x-auto">
                <table class="w-full text-left border-collapse whitespace-nowrap">
                    <thead class="bg-gray-50 text-gray-500 border-b border-gray-100 text-sm tracking-wider uppercase">
                        <tr>
                            <th class="p-5 font-bold">ユーザーID</th>
                            <th class="p-5 font-bold">氏名</th>
                            <th class="p-5 font-bold">権限</th>
                            <th class="p-5 font-bold text-center">アカウント状態</th>
                            <th class="p-5 font-bold text-center">操作</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-100">
                        <c:forEach var="user" items="${userList}">
                        
                            <tr class="hover:bg-indigo-50/30 transition-colors group">
                                <td class="p-5 font-mono text-gray-500 text-sm">${user.id}</td>
                                <td class="p-5 font-bold text-gray-900 text-lg">${user.name}</td>
                                <td class="p-5">
                                    <span class="bg-gray-100 text-gray-600 px-3 py-1 rounded-md text-xs font-bold border border-gray-200">
                                        <c:choose>
                                            <c:when test="${user.role == 2}">管理者</c:when>
                                            <c:when test="${user.role == 1}">司書</c:when>
                                            <c:otherwise>利用者</c:otherwise>
                                        </c:choose>
                                    </span>
                                </td>
                                <td class="p-5 text-center">
                                    <c:choose>
                                        <c:when test="${user.status == 1}">
                                            <span class="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-bold bg-red-100 text-red-700 border border-red-200">
                                                <span class="w-1.5 h-1.5 rounded-full bg-red-600"></span>貸出停止
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-bold bg-green-100 text-green-700 border border-green-200">
                                                <span class="w-1.5 h-1.5 rounded-full bg-green-600"></span>利用可能
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="p-5 text-center">
                                    <a href="UserDetailServlet?userId=${user.id}" class="inline-flex items-center gap-1.5 px-4 py-2 border-2 border-gray-200 text-gray-700 rounded-lg hover:bg-gray-900 hover:text-white hover:border-gray-900 transition-colors text-sm font-bold shadow-sm">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z"/><path d="m15 5 4 4"/></svg>
                                        詳細 / 編集
                                    </a>
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
