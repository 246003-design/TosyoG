<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 利用者登録</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    <!-- ヘッダー -->
    <header class="bg-gray-900 text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-20 border-b border-gray-700">
        <a href="admin_user_menu.jsp" class="p-1 hover:bg-gray-700 rounded-full transition-colors text-white">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">新規利用者登録</h1>
    </header>

    <main class="flex-1 p-6 max-w-2xl mx-auto w-full flex items-center">
        <div class="bg-white p-8 rounded-xl shadow-sm border border-gray-300 w-full">
            <div class="flex justify-center mb-6">
                <div class="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center text-gray-500">
                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/></svg>
                </div>
            </div>

            <!-- サーブレットへのPOSTフォーム -->
            <form action="UserRegisterServlet" method="POST" class="space-y-6">
                <div>
                    <label class="block text-sm font-bold text-gray-700 mb-2">氏名</label>
                    <input type="text" name="name" placeholder="例: 広島 情報" class="w-full p-3 border border-gray-300 rounded focus:ring-2 focus:ring-gray-900 outline-none" required />
                </div>
                <div>
                    <label class="block text-sm font-bold text-gray-700 mb-2">所属部署・団体</label>
                    <input type="text" name="department" placeholder="例: 広島情報本部" class="w-full p-3 border border-gray-300 rounded focus:ring-2 focus:ring-gray-900 outline-none" required />
                </div>
                <div>
                    <label class="block text-sm font-bold text-gray-700 mb-2">初期パスワード</label>
                    <input type="text" name="password" placeholder="例: password123" class="w-full p-3 border border-gray-300 rounded focus:ring-2 focus:ring-gray-900 outline-none font-mono" required />
                </div>
                <div class="grid grid-cols-2 gap-6">
                    <div>
                        <label class="block text-sm font-bold text-gray-700 mb-2">状態</label>
                        <select name="status" class="w-full p-3 border border-gray-300 rounded bg-white outline-none">
                            <option value="利用可能">利用可能</option>
                            <option value="利用停止">利用停止</option>
                        </select>
                    </div>
                    <div>
                        <label class="block text-sm font-bold text-gray-700 mb-2">区分</label>
                        <select name="type" class="w-full p-3 border border-gray-300 rounded bg-white outline-none">
                            <option value="司書">司書</option>
                            <option value="利用者">利用者</option>
                        </select>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-bold text-gray-700 mb-2">ユーザーID (自動発行)</label>
                    <div class="w-full p-3 border border-gray-200 rounded bg-gray-100 text-gray-500 font-mono font-bold text-center">登録完了時にサーバーにて採番されます</div>
                </div>

                <div class="flex gap-4 mt-10">
                    <a href="admin_user_menu.jsp" class="flex-1 py-4 border border-gray-300 text-gray-700 rounded font-bold text-center hover:bg-gray-50 transition-colors">戻る</a>
                    <button type="submit" class="flex-[2] py-4 bg-gray-900 text-white rounded font-bold hover:bg-black shadow-md transition-colors" onclick="return confirm('登録してよろしいですか？');">
                        登録内容を確認して登録
                    </button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>