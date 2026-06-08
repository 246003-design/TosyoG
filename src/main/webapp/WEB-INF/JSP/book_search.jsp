<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書検索</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-stone-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">
    
    <header class="bg-[#1e5641] text-white p-4 shadow-md flex items-center gap-4 sticky top-0 z-20">
        <a href="user_home.jsp" class="p-2 hover:bg-white/20 rounded-full transition-colors group">
            <svg class="transform group-hover:-translate-x-1 transition-transform" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest">図書検索</h1>
    </header>
    
    <main class="flex-1 p-4 md:p-8 max-w-4xl mx-auto w-full">
        <div class="bg-white p-8 md:p-10 rounded-3xl shadow-sm border border-gray-100">
            
            <div class="flex items-center gap-3 mb-8 pb-4 border-b border-gray-100">
                <div class="bg-green-50 p-2 rounded-lg text-[#1e5641]">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
                </div>
                <h2 class="text-xl font-bold text-gray-800">検索条件を指定してください</h2>
            </div>

            <form action="BookSearchServlet" method="GET">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-x-8 gap-y-6 mb-10">
                    <div>
                        <label class="block text-sm font-bold text-gray-600 mb-2 uppercase tracking-wider">タイトル</label>
                        <input type="text" name="title" placeholder="例: システム設計" class="w-full p-4 bg-gray-50 border border-gray-200 rounded-xl focus:bg-white focus:outline-none focus:ring-2 focus:ring-[#1e5641]/50 focus:border-[#1e5641] transition-all" />
                    </div>
                    <div>
                        <label class="block text-sm font-bold text-gray-600 mb-2 uppercase tracking-wider">著者検索</label>
                        <input type="text" name="author" placeholder="例: 山田太郎" class="w-full p-4 bg-gray-50 border border-gray-200 rounded-xl focus:bg-white focus:outline-none focus:ring-2 focus:ring-[#1e5641]/50 focus:border-[#1e5641] transition-all" />
                    </div>
                    <div>
                        <label class="block text-sm font-bold text-gray-600 mb-2 uppercase tracking-wider">ISBN</label>
                        <input type="text" name="isbn" placeholder="例: 978-4-..." class="w-full p-4 bg-gray-50 border border-gray-200 rounded-xl focus:bg-white focus:outline-none focus:ring-2 focus:ring-[#1e5641]/50 focus:border-[#1e5641] transition-all font-mono text-sm" />
                    </div>
                    <div>
                        <label class="block text-sm font-bold text-gray-600 mb-2 uppercase tracking-wider">分類</label>
                        <select name="category" class="w-full p-4 bg-gray-50 border border-gray-200 rounded-xl focus:bg-white focus:outline-none focus:ring-2 focus:ring-[#1e5641]/50 focus:border-[#1e5641] transition-all appearance-none cursor-pointer">
                            <option value="">すべての分類から探す</option>
                            <option value="IT">IT・技術</option>
                            <option value="デザイン">デザイン</option>
                            <option value="業務">業務・ビジネス</option>
                        </select>
                    </div>
                </div>
                
                <div class="flex flex-col sm:flex-row justify-end gap-4 border-t border-gray-100 pt-8">
                    <a href="user_home.jsp" class="px-8 py-4 border-2 border-gray-200 text-gray-600 rounded-xl font-bold text-center hover:bg-gray-50 hover:text-gray-800 transition-colors">
                        メニューへ戻る
                    </a>
                    <button type="submit" class="px-10 py-4 bg-[#1e5641] text-white rounded-xl font-bold text-lg hover:bg-[#164030] shadow-lg shadow-green-900/20 active:scale-95 transition-all flex items-center justify-center gap-2">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
                        検索を実行
                    </button>
                </div>
            </form>

        </div>
    </main>
</body>
</html>