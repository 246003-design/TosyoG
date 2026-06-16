
　　　　　　　　　　　　　　　　　　　<%--      利用者詳細編集画面　　　　 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 利用者詳細編集</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">
    <header class="bg-gray-900 text-white p-4 shadow-md flex items-center gap-4 sticky top-0 z-20">
        <a href="UserSearchServlet" class="p-2 hover:bg-white/20 rounded-full transition-colors group">
            <svg class="transform group-hover:-translate-x-1 transition-transform" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest flex items-center gap-2">
            <span class="text-gray-400 font-normal text-lg">利用者詳細 :</span> 
            <%-- <c:out value="${user.name}"/> --%>情報 太郎
        </h1>
    </header>

    <main class="flex-1 p-4 md:p-8 max-w-6xl mx-auto w-full">
        
        <%-- 更新完了通知 --%>
        <%-- <c:if test="${not empty successMessage}"> --%>
        <div class="mb-6 bg-teal-50 border border-teal-200 text-teal-800 px-5 py-4 rounded-xl flex items-center gap-3 font-bold shadow-sm">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22c5.523 0 10-4.477 10-10S17.523 2 12 2 2 6.477 2 12s4.477 10 10 10z"/><path d="m9 12 2 2 4-4"/></svg>
            利用者情報の更新が完了しました。
        </div>
        <%-- </c:if> --%>

        <div class="grid grid-cols-1 lg:grid-cols-12 gap-8">
            
            <!-- ①左側ペイン: ユーザー情報編集フォーム (1/3幅) -->
            <div class="lg:col-span-4 bg-white p-8 rounded-3xl shadow-sm border border-gray-100 self-start">
                <div class="flex flex-col items-center text-center mb-8 pb-8 border-b border-gray-100">
                    <div class="w-20 h-20 bg-gray-50 rounded-full flex items-center justify-center text-gray-400 mb-4 border-4 border-white shadow-md">
                        <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                    </div>
                    <h3 class="font-bold text-2xl text-gray-900 mb-1">
                        <%-- <c:out value="${user.name}"/> --%>情報 太郎
                    </h3>
                    <p class="text-sm font-mono text-gray-400 mb-3">ID: <%-- <c:out value="${user.id}"/> --%>123456</p>
                    <span class="text-xs bg-gray-100 text-gray-600 px-3 py-1.5 rounded-md font-bold border border-gray-200">
                        <%-- <c:out value="${user.type}"/> --%> 司書
                    </span>
                </div>
                
                <form action="UserUpdateServlet" method="POST" class="space-y-6">
                    <input type="hidden" name="userId" value="${user.id}" />

                    <div>
                        <label class="block text-xs font-bold text-gray-500 mb-2 uppercase tracking-wider">所属</label>
                        <input type="text" name="department" value="広島情報本部" class="w-full p-4 border border-gray-200 rounded-xl focus:ring-2 focus:ring-gray-900 outline-none bg-gray-50 focus:bg-white transition-all text-sm" required />
                    </div>
                    <div>
                        <label class="block text-xs font-bold text-gray-500 mb-2 uppercase tracking-wider">パスワードの再設定</label>
                        <input type="text" name="password" value="password" class="w-full p-4 border border-gray-200 rounded-xl focus:ring-2 focus:ring-gray-900 outline-none font-mono text-sm bg-gray-50 focus:bg-white transition-all" required />
                        <p class="text-xs text-gray-400 mt-2">※表示されているのは現在のパスワードです</p>
                    </div>
                    <div>
                        <label class="block text-xs font-bold text-gray-500 mb-2 uppercase tracking-wider">アカウント状態</label>
                        <select name="status" class="w-full p-4 border border-gray-200 rounded-xl outline-none bg-gray-50 focus:bg-white focus:ring-2 focus:ring-gray-900 transition-all text-sm appearance-none cursor-pointer">
                            <option value="利用可能">🟢 利用可能</option>
                            <option value="利用停止">🔴 利用停止</option>
                            <option value="貸出停止">🟡 貸出停止</option>
                        </select>
                    </div>
                    
                    <button type="submit" class="w-full mt-8 py-4 bg-gray-900 text-white rounded-xl font-bold hover:bg-black flex items-center justify-center gap-2 shadow-lg shadow-gray-900/20 active:scale-95 transition-all" onclick="return confirm('ユーザーの変更内容を保存しますか？');">
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/><polyline points="17 21 17 13 7 13 7 21"/><polyline points="7 3 7 8 15 8"/></svg>
                        変更を保存
                    </button>
                </form>
            </div>

            <!-- ②右側ペイン: 現在貸出中の図書リスト (2/3幅) -->
            <div class="lg:col-span-8 bg-white rounded-3xl shadow-sm border border-gray-100 flex flex-col h-full overflow-hidden">
                <div class="p-6 border-b border-gray-100 bg-gray-50/50 flex items-center justify-between">
                    <div class="flex items-center gap-3">
                        <div class="bg-emerald-50 text-emerald-600 p-2 rounded-lg">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"/><path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"/></svg>
                        </div>
                        <h3 class="font-bold text-gray-800 text-lg">現在貸出中の図書</h3>
                    </div>
                    <span class="text-sm font-bold text-emerald-600 bg-emerald-50 px-3 py-1 rounded-full">2 冊</span>
                </div>
                
                <div class="overflow-x-auto flex-1">
                    <table class="w-full text-left border-collapse whitespace-nowrap">
                        <thead class="bg-white border-b border-gray-100 text-gray-400 text-xs tracking-wider uppercase">
                            <tr>
                                <th class="p-5 font-bold">タイトル</th>
                                <th class="p-5 font-bold">ISBN</th>
                                <th class="p-5 font-bold">著者</th>
                                <th class="p-5 font-bold">出版社</th>
                            </tr>
                        </thead>
                        <tbody class="divide-y divide-gray-50">
                            <%-- <c:forEach var="loan" items="${currentLoanList}"> --%>
                            
                            <tr class="hover:bg-gray-50/50 transition-colors">
                                <td class="p-5 font-bold text-gray-900">技術情報の基礎</td>
                                <td class="p-5 text-gray-400 font-mono text-sm">978-4-00123</td>
                                <td class="p-5 text-gray-600 text-sm">情報 太郎</td>
                                <td class="p-5 text-gray-600 text-sm">技術出版</td>
                            </tr>
                            <tr class="hover:bg-gray-50/50 transition-colors">
                                <td class="p-5 font-bold text-gray-900">Web UIの実践</td>
                                <td class="p-5 text-gray-400 font-mono text-sm">978-4-98765</td>
                                <td class="p-5 text-gray-600 text-sm">佐藤次郎</td>
                                <td class="p-5 text-gray-600 text-sm">技術書院</td>
                            </tr>
                            
                            <%-- </c:forEach> --%>
                            
                            <%-- <c:if test="${empty currentLoanList}"> --%>
                            <!-- <tr><td colSpan="4" class="p-10 text-center text-gray-400 font-medium">現在、貸出中の図書はありません。</td></tr> -->
                            <%-- </c:if> --%>
                        </tbody>
                    </table>
                </div>
                
                <div class="p-6 bg-gray-50/50 border-t border-gray-100 text-right">
                    <a href="UserSearchServlet" class="inline-flex items-center gap-2 px-8 py-3 border-2 border-gray-200 text-gray-600 rounded-xl font-bold hover:bg-gray-100 hover:text-gray-900 transition-colors bg-white shadow-sm">
                        一覧に戻る
                    </a>
                </div>
            </div>

        </div>
    </main>
</body>
</html>