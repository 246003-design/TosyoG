<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 利用者情報更新</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">
    
    <header class="bg-gray-900 text-white p-4 shadow-md flex items-center justify-between sticky top-0 z-20">
        <h1 class="text-xl font-bold tracking-widest">利用者情報更新</h1>
        <a href="UserSearchServlet" class="px-5 py-2 border border-white text-white rounded font-bold text-sm hover:bg-white hover:text-gray-900 transition-colors">
            戻る
        </a>
    </header>

    <main class="flex-1 p-4 md:p-8 max-w-5xl mx-auto w-full flex items-center justify-center">
        
        <div class="bg-white p-6 md:p-10 rounded-2xl shadow-sm border border-gray-100 flex flex-col md:flex-row gap-8 w-full">
            
            <div class="md:w-1/3 bg-emerald-50/60 rounded-xl p-8 flex flex-col items-center justify-center text-center">
                <div class="w-24 h-24 bg-emerald-700 rounded-full flex items-center justify-center text-white mb-6 shadow-sm">
                    <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                </div>
                <h3 class="font-bold text-xl text-gray-900 mb-2">
                    <%-- <c:out value="${user.name}"/> --%>情報 太郎 様
                </h3>
                <p class="text-gray-500 font-mono text-sm mb-6">
                    ID: <%-- <c:out value="${user.id}"/> --%>246246
                </p>
                <span class="text-sm font-bold text-emerald-700">
                    <%-- 貸出状況等をJSTLで判定して表示 --%>
                    貸出中
                </span>
            </div>
            
            <div class="md:w-2/3 flex flex-col justify-center">
                <form action="UserUpdateServlet" method="POST" class="space-y-6">
                    <input type="hidden" name="userId" value="${user.id}" />

                    <div class="grid grid-cols-1 sm:grid-cols-2 gap-x-6 gap-y-6">
                        <div>
                            <label class="block text-sm font-bold text-gray-700 mb-2">氏名</label>
                            <input type="text" name="name" value="広島 情報" class="w-full p-3 border border-gray-200 rounded focus:ring-2 focus:ring-gray-900 outline-none transition-all" required />
                        </div>

                        <div>
                            <label class="block text-sm font-bold text-gray-700 mb-2">パスワード</label>
                            <input type="text" name="password" value="password" class="w-full p-3 border border-gray-200 rounded focus:ring-2 focus:ring-gray-900 outline-none transition-all" required />
                        </div>

                        <div>
                            <label class="block text-sm font-bold text-gray-700 mb-2">区分</label>
                            <select name="type" class="w-full p-3 border border-gray-200 rounded outline-none focus:ring-2 focus:ring-gray-900 transition-all bg-gray-100 cursor-pointer text-gray-800">
                                <option value="管理者">管理者</option>
                                <option value="司書">司書</option>
                                <option value="利用者">利用者</option>
                            </select>
                        </div>

                        <div>
                            <label class="block text-sm font-bold text-gray-700 mb-2">状態</label>
                            <div class="flex items-center gap-6 h-[46px]">
                                <label class="flex items-center gap-2 cursor-pointer">
                                    <input type="radio" name="status" value="利用可能" class="w-4 h-4 text-blue-600 focus:ring-gray-900" checked />
                                    <span class="text-sm font-medium text-gray-800">利用可能</span>
                                </label>
                                <label class="flex items-center gap-2 cursor-pointer">
                                    <input type="radio" name="status" value="利用停止" class="w-4 h-4 text-blue-600 focus:ring-gray-900" />
                                    <span class="text-sm font-medium text-gray-800">利用停止</span>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="grid grid-cols-1 sm:grid-cols-2 gap-4 mt-8 pt-4">
                        <button type="submit" class="w-full py-3 bg-gray-900 text-white rounded font-bold hover:bg-black transition-colors shadow-md" onclick="return confirm('ユーザー情報を更新します。よろしいですか？');">
                            保存
                        </button>
                        <a href="UserSearchServlet" class="flex items-center justify-center w-full py-3 bg-white border border-red-600 text-red-600 rounded font-bold hover:bg-red-50 transition-colors shadow-sm">
                            取消
                        </a>
                    </div>
                </form>
            </div>

        </div>
    </main>
</body>
</html>