                            <%--      新規利用者登録画面　　　　 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 利用者登録</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">
    <header class="bg-gray-900 text-white p-4 shadow-md flex items-center gap-4 sticky top-0 z-20">
        <a href="UserManagementMenuServlet" class="p-2 hover:bg-white/20 rounded-full transition-colors group">
            <svg class="transform group-hover:-translate-x-1 transition-transform" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest">新規利用者登録</h1>
    </header>

    <main class="flex-1 p-4 md:p-8 max-w-3xl mx-auto w-full">
        <div class="bg-white p-8 md:p-12 rounded-3xl shadow-sm border border-gray-100">
            
            <div class="flex items-center gap-3 mb-8 pb-4 border-b border-gray-100">
                <div class="bg-blue-50 p-2 rounded-lg text-blue-600">
                    <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><line x1="19" y1="8" x2="19" y2="14"/><line x1="22" y1="11" x2="16" y2="11"/></svg>
                </div>
                <h2 class="text-xl font-bold text-gray-800">アカウント情報の入力</h2>
            </div>

            <form action="UserRegisterServlet" method="POST" class="space-y-8">
                
                <div class="grid grid-cols-1 md:grid-cols-2 gap-x-8 gap-y-6">
                    <div>
                        <label class="block text-sm font-bold text-gray-600 mb-2 uppercase tracking-wider">氏名</label>
                        <input type="text" name="name" placeholder="例: 広島 情報" class="w-full p-4 bg-gray-50 border border-gray-200 rounded-xl focus:bg-white focus:outline-none focus:ring-2 focus:ring-gray-900 transition-all" required />
                    </div>
                    <div>
                        <label class="block text-sm font-bold text-gray-600 mb-2 uppercase tracking-wider">初期パスワード</label>
                        <input type="text" name="password" placeholder="例: password123" class="w-full p-4 bg-gray-50 border border-gray-200 rounded-xl focus:bg-white focus:outline-none focus:ring-2 focus:ring-gray-900 transition-all font-mono" required />
                    </div>

                    <div>
                        <label class="block text-sm font-bold text-gray-600 mb-2 uppercase tracking-wider">初期状態</label>
                        <select name="status" class="w-full p-4 bg-gray-50 border border-gray-200 rounded-xl focus:bg-white focus:outline-none focus:ring-2 focus:ring-gray-900 transition-all appearance-none cursor-pointer">
                            <option value="0">利用可能</option>
                            <option value="1">利用停止</option>
                        </select>
                    </div>
                    <div>
                        <label class="block text-sm font-bold text-gray-600 mb-2 uppercase tracking-wider">権限区分</label>
                        <select name="role" class="w-full p-4 bg-gray-50 border border-gray-200 rounded-xl focus:bg-white focus:outline-none focus:ring-2 focus:ring-gray-900 transition-all appearance-none cursor-pointer">
                            <option value="0">利用者</option>
                            <option value="1">司書</option>
                            <option value="2">管理者</option>
                        </select>
                    </div>
                </div>

                <div class="bg-blue-50/50 border border-blue-100 p-5 rounded-2xl flex items-start gap-4 mt-6">
                    <svg class="text-blue-500 shrink-0 mt-0.5" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><path d="M12 16v-4"/><path d="M12 8h.01"/></svg>
                    <div>
                        <p class="text-sm font-bold text-blue-900 mb-1">ユーザーIDについて</p>
                        <p class="text-xs text-blue-700 leading-relaxed">ユーザーIDは登録完了時にシステムによって自動採番されます。登録後の画面で必ず対象者へIDをお伝えください。</p>
                    </div>
                </div>

                <div class="flex flex-col sm:flex-row gap-4 pt-8 border-t border-gray-100">
                    <a href="UserManagementMenuServlet" class="w-full sm:w-1/3 py-4 border-2 border-gray-200 text-gray-600 rounded-xl font-bold text-center hover:bg-gray-50 hover:text-gray-900 transition-colors">
                        戻る
                    </a>
                    <button type="submit" class="w-full sm:w-2/3 py-4 bg-gray-900 text-white rounded-xl font-bold text-lg hover:bg-black shadow-lg shadow-gray-900/20 active:scale-95 transition-all flex items-center justify-center gap-2" onclick="return confirm('新しいアカウントを発行します。よろしいですか？');">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"/></svg>
                        登録内容を確認して登録
                    </button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>
