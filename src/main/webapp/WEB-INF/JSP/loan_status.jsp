
　　　　　　　　　　　　　　　　　　　<%--      貸出状況照会画面　　　　 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>貸出状況照会</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-stone-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">

    <header class="bg-[#1e5641] text-white p-4 shadow-md flex items-center gap-4 sticky top-0 z-20">
        <a href="user_home.jsp" class="p-2 hover:bg-white/20 rounded-full transition-colors group">
            <svg class="transform group-hover:-translate-x-1 transition-transform" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest">貸出状況照会</h1>
    </header>
    
    <main class="flex-1 p-4 md:p-8 max-w-5xl mx-auto w-full">
        
        <div class="mb-6">
            <h2 class="text-2xl font-bold text-gray-800 mb-2">現在の貸出リスト</h2>
            <p class="text-gray-500 text-sm">返却期限にご注意ください。期限を過ぎると新たな貸出ができなくなります。</p>
        </div>

        <div class="bg-white rounded-3xl shadow-sm border border-gray-100 overflow-hidden">
            <div class="overflow-x-auto">
                <table class="w-full text-left border-collapse whitespace-nowrap">
                    <thead>
                        <tr class="bg-green-50/50 border-b border-gray-100 text-gray-500 text-sm tracking-wider uppercase">
                            <th class="p-5 font-bold">タイトル</th>
                            <th class="p-5 font-bold">貸出開始日</th>
                            <th class="p-5 font-bold">返却期限日</th>
                            <th class="p-5 font-bold text-center">状態</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-100">
                        
<<<<<<< HEAD
                        <%-- <c:forEach var="loan" items="${loanList}"> --%>
=======
                        <%-- JSTLでのループ処理（サーブレットから loanList 等を受け取る） --%>
                        <c:forEach var="loan" items="${loanList}">
>>>>>>> branch 'master' of https://github.com/246003-design/TosyoG.git
                        
<<<<<<< HEAD
                        <!-- サンプル 1: 超過の場合 (赤の背景で強調) -->
                        <tr class="hover:bg-red-50/30 transition-colors group">
                            <td class="p-5">
                                <span class="font-bold text-gray-900 text-lg">Web UIデザイン論</span>
                            </td>
                            <td class="p-5 text-gray-500">2024/05/10</td>
                            <td class="p-5">
                                <div class="flex items-center gap-2 text-red-600 font-bold">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>
                                    2024/05/24
                                </div>
                            </td>
                            <td class="p-5 text-center">
                                <span class="inline-flex items-center gap-1.5 px-4 py-1.5 rounded-lg text-xs font-black bg-red-100 text-red-700 border border-red-200">
                                    <span class="w-1.5 h-1.5 rounded-full bg-red-600 animate-pulse"></span>
                                    期限超過
=======
                        <!-- サンプルデータ 1行目 (超過の場合のスタイル例) -->
                        <tr class="hover:bg-gray-50 transition-colors">
                            <td class="p-4 font-semibold text-gray-900">${loan.title}</td>
                            <td class="p-4 text-gray-600">${loan.lend_date}</td>
                            <td class="p-4 font-medium text-red-600">${loan.due_date}</td>
                            <td class="p-4 text-center">
                                <span class="px-3 py-1 rounded-full text-xs font-bold bg-red-100 text-red-700 border border-red-200">
                                    超過
>>>>>>> branch 'master' of https://github.com/246003-design/TosyoG.git
                                </span>
                            </td>
                        </tr>

                        <!-- サンプル 2: 正常な貸出 -->
                        <tr class="hover:bg-gray-50 transition-colors">
<<<<<<< HEAD
                            <td class="p-5">
                                <span class="font-bold text-gray-800 text-lg">技術情報の活用</span>
                            </td>
                            <td class="p-5 text-gray-500">2024/06/01</td>
                            <td class="p-5 font-medium text-gray-700">2024/06/15</td>
                            <td class="p-5 text-center">
                                <span class="inline-flex items-center gap-1.5 px-4 py-1.5 rounded-lg text-xs font-bold bg-green-100 text-green-800 border border-green-200">
                                    <span class="w-1.5 h-1.5 rounded-full bg-green-600"></span>
=======
                            <td class="p-4 font-semibold text-gray-900">due_date</td>
                            <td class="p-4 text-gray-600">${loan.lend_date}</td>
                            <td class="p-4 font-medium text-gray-600">${loan.due_date}</td>
                            <td class="p-4 text-center">
                                <span class="px-3 py-1 rounded-full text-xs font-bold bg-green-100 text-green-700 border border-green-200">
>>>>>>> branch 'master' of https://github.com/246003-design/TosyoG.git
                                    貸出中
                                </span>
                            </td>
                        </tr>

                        </c:forEach>
                        
                    </tbody>
                </table>
            </div>
            
            <div class="p-6 bg-gray-50/50 border-t border-gray-100 flex justify-center">
                <a href="user_home.jsp" class="px-8 py-3.5 bg-white border-2 border-gray-200 text-gray-600 rounded-xl font-bold hover:bg-gray-50 hover:text-gray-900 transition-colors shadow-sm">
                    メニューへ戻る
                </a>
            </div>
        </div>
    </main>
</body>
</html>