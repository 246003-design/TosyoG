
　　　　　　　　　　　　　　　　　　　<%--      共通　蔵書登録画面　　　　 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 蔵書登録</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    <%-- <c:set var="themeBg" value="${sessionScope.role == 'ADMIN' ? 'bg-gray-900' : 'bg-[#1e3a8a]'}" /> --%>
    <c:set var="themeBg" value="bg-[#1e3a8a]" />

    <header class="${themeBg} text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-20">
        <a href="book_management_menu.jsp" class="p-1 hover:bg-white/20 rounded-full transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">蔵書登録</h1>
    </header>

    <main class="flex-1 p-4 md:p-8 max-w-4xl mx-auto w-full relative">
        <div class="bg-white p-6 md:p-8 rounded-xl shadow-sm border border-gray-300">
            
            <form action="BookBulkRegisterServlet" method="POST" enctype="multipart/form-data" class="mb-8 p-6 border-2 border-dashed border-gray-300 rounded-xl bg-gray-50 text-center relative">
                <svg class="mx-auto text-gray-400 mb-2" xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="17 8 12 3 7 8"/><line x1="12" y1="3" x2="12" y2="15"/></svg>
                <p class="text-gray-600 font-bold mb-2">ファイルをアップロード (CSV/Excel)</p>
                <input type="file" name="file" accept=".csv,.xlsx" required class="mb-3 block w-full text-sm text-slate-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-gray-200 file:text-gray-700 hover:file:bg-gray-300 cursor-pointer"/>
                <button type="submit" class="px-6 py-2 ${themeBg} text-white rounded font-bold shadow-md">一括登録を実行</button>
            </form>

            <div class="flex items-center gap-4 mb-8">
                <hr class="flex-1 border-gray-300" />
                <span class="text-gray-400 font-bold text-xs uppercase tracking-wider">手動入力</span>
                <hr class="flex-1 border-gray-300" />
            </div>

            <form action="BookRegisterServlet" method="POST" class="space-y-6">
                <div>
                    <label class="block text-sm font-bold text-gray-700 mb-1">ISBN</label>
                    <div class="flex gap-2">
                        <input type="text" name="isbn" placeholder="ISBNを入力" class="flex-1 p-3 border border-gray-300 rounded focus:ring-2 outline-none font-mono" required />
                    </div>
                </div>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <label class="block text-sm font-bold text-gray-700 mb-1">タイトル</label>
                        <input type="text" name="title" class="w-full p-3 border border-gray-300 rounded focus:ring-2 outline-none" required />
                    </div>
                    <div>
                        <label class="block text-sm font-bold text-gray-700 mb-1">著者</label>
                        <input type="text" name="author" class="w-full p-3 border border-gray-300 rounded focus:ring-2 outline-none" required />
                    </div>
                    <div>
                        <label class="block text-sm font-bold text-gray-700 mb-1">出版社</label>
                        <input type="text" name="publisher" class="w-full p-3 border border-gray-300 rounded focus:ring-2 outline-none" required />
                    </div>
                    <div>
                        <label class="block text-sm font-bold text-gray-700 mb-1">分類</label>
                        <select name="category" class="w-full p-3 border border-gray-300 rounded focus:ring-2 outline-none bg-white">
                            <option value="文芸">文芸</option>
                            <option value="技術">技術</option>
                            <option value="実用">実用</option>
                        </select>
                    </div>
                </div>
                <div class="flex gap-4 mt-8 pt-6 border-t border-gray-100">
                    <a href="book_management_menu.jsp" class="flex-1 py-4 border-2 border-gray-300 text-gray-700 rounded font-bold text-center hover:bg-gray-50">戻る</a>
                    <button type="submit" class="flex-[2] py-4 ${themeBg} text-white rounded font-bold shadow-md transition-all" onclick="return confirm('登録してよろしいですか？');">
                        登録する
                    </button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>