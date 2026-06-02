<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>貸出状況照会</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">

    <header class="bg-[#1e5641] text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-10">
        <a href="user_home.jsp" class="p-1 hover:bg-white/20 rounded-full transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">貸出状況照会</h1>
    </header>
    
    <main class="flex-1 p-4 md:p-8 max-w-5xl mx-auto w-full">
        <div class="bg-white rounded-2xl shadow-sm border border-gray-200 overflow-hidden">
            <div class="overflow-x-auto">
                <table class="w-full text-left border-collapse">
                    <thead>
                        <tr class="bg-teal-50 border-b border-teal-100 text-[#1e5641]">
                            <th class="p-4 font-bold">タイトル</th>
                            <th class="p-4 font-bold">貸出開始日</th>
                            <th class="p-4 font-bold">返却期限日</th>
                            <th class="p-4 font-bold text-center">状態</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-100">
                        
                        <%-- JSTLでのループ処理（サーブレットから loanList 等を受け取る） --%>
                        <%-- <c:forEach var="loan" items="${loanList}"> --%>
                        
                        <!-- サンプルデータ 1行目 (超過の場合のスタイル例) -->
                        <tr class="hover:bg-gray-50 transition-colors">
                            <td class="p-4 font-semibold text-gray-900">Web UIデザイン論</td>
                            <td class="p-4 text-gray-600">2024/05/10</td>
                            <td class="p-4 font-medium text-red-600">2024/05/24</td>
                            <td class="p-4 text-center">
                                <span class="px-3 py-1 rounded-full text-xs font-bold bg-red-100 text-red-700 border border-red-200">
                                    超過
                                </span>
                            </td>
                        </tr>

                        <!-- サンプルデータ 2行目 (正常な場合) -->
                        <tr class="hover:bg-gray-50 transition-colors">
                            <td class="p-4 font-semibold text-gray-900">技術情報の活用</td>
                            <td class="p-4 text-gray-600">2024/06/01</td>
                            <td class="p-4 font-medium text-gray-600">2024/06/15</td>
                            <td class="p-4 text-center">
                                <span class="px-3 py-1 rounded-full text-xs font-bold bg-green-100 text-green-700 border border-green-200">
                                    貸出中
                                </span>
                            </td>
                        </tr>

                        <%-- </c:forEach> --%>
                        
                    </tbody>
                </table>
            </div>
            
            <div class="p-6 bg-gray-50 border-t flex justify-center">
                <a href="user_home.jsp" class="px-8 py-3 bg-white border-2 border-[#1e5641] text-[#1e5641] rounded-lg font-bold hover:bg-[#1e5641] hover:text-white transition-colors">
                    メニューへ
                </a>
            </div>
        </div>
    </main>
</body>
</html>