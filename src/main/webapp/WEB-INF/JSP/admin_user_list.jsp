<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 利用者一覧・検索</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    <!-- ヘッダー -->
    <header class="bg-gray-900 text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-20 border-b border-gray-700">
        <a href="admin_user_menu.jsp" class="p-1 hover:bg-gray-700 rounded-full transition-colors text-white">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">利用者検索・情報更新</h1>
    </header>

    <main class="flex-1 p-4 md:p-8 max-w-5xl mx-auto w-full">
        <div class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
            <!-- 検索フォーム -->
            <form action="UserSearchServlet" method="GET" class="p-4 border-b border-gray-200 bg-gray-100 flex flex-col md:flex-row gap-4 justify-between items-center">
                <div class="relative w-full max-w-md flex">
                    <input type="text" name="searchQuery" placeholder="氏名またはユーザーIDを入力" class="w-full pl-3 pr-4 py-2 border border-gray-300 rounded-l focus:outline-none focus:ring-2 focus:ring-gray-900" />
                    <button type="submit" class="px-8 py-2 bg-gray-900 text-white font-bold rounded-r hover:bg-black transition-colors border border-gray-900">
                        検索
                    </button>
                </div>
            </form>

            <div class="overflow-x-auto">
                <table class="w-full text-left border-collapse">
                    <thead class="bg-gray-200 text-gray-800">
                        <tr>
                            <th class="p-3 font-semibold text-sm">ユーザーID</th>
                            <th class="p-3 font-semibold text-sm">氏名</th>
                            <th class="p-3 font-semibold text-sm">区分</th>
                            <th class="p-3 font-semibold text-sm">所属</th>
                            <th class="p-3 font-semibold text-sm text-center">状態</th>
                            <th class="p-3 font-semibold text-sm text-center">操作</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        <%-- <c:forEach var="user" items="${userList}"> --%>
                        <!-- サンプル行 1 (司書) -->
                        <tr class="hover:bg-blue-50 transition-colors bg-white">
                            <td class="p-3 font-mono text-gray-600">123456</td>
                            <td class="p-3 font-bold text-gray-900">情報 太郎</td>
                            <td class="p-3 text-gray-700">司書</td>
                            <td class="p-3 text-gray-600 text-sm">広島情報本部</td>
                            <td class="p-3 text-center">
                                <span class="px-2 py-1 rounded text-xs font-bold bg-green-100 text-green-700 border border-green-200">
                                    利用可能
                                </span>
                            </td>
                            <td class="p-3 text-center">
                                <a href="UserDetailServlet?userId=123456" class="inline-block px-4 py-1.5 border border-blue-600 text-blue-600 rounded hover:bg-blue-50 font-bold text-sm">
                                    詳細・編集
                                </a>
                            </td>
                        </tr>

                        <!-- サンプル行 2 (利用者) -->
                        <tr class="hover:bg-blue-50 transition-colors bg-white">
                            <td class="p-3 font-mono text-gray-600">246246</td>
                            <td class="p-3 font-bold text-gray-900">広島 花子</td>
                            <td class="p-3 text-gray-700">利用者</td>
                            <td class="p-3 text-gray-600 text-sm">総務部</td>
                            <td class="p-3 text-center">
                                <span class="px-2 py-1 rounded text-xs font-bold bg-red-100 text-red-700 border border-red-200">
                                    貸出停止
                                </span>
                            </td>
                            <td class="p-3 text-center">
                                <a href="UserDetailServlet?userId=246246" class="inline-block px-4 py-1.5 border border-blue-600 text-blue-600 rounded hover:bg-blue-50 font-bold text-sm">
                                    詳細・編集
                                </a>
                            </td>
                        </tr>
                        <%-- </c:forEach> --%>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</body>
</html>