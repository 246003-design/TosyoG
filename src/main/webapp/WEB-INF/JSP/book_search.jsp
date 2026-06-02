<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書検索</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    
    <header class="bg-[#1e5641] text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-10">
        <a href="user_home.jsp" class="p-1 hover:bg-white/20 rounded-full transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">図書検索</h1>
    </header>
    
    <main class="flex-1 p-4 md:p-8 max-w-4xl mx-auto w-full">
        <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-200">
            
            <!-- サーブレットへ検索条件を送信するフォーム -->
            <form action="BookSearchServlet" method="GET">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
                    <div>
                        <label class="block text-sm font-semibold text-[#1e5641] mb-2">タイトル</label>
                        <!-- name属性でJava側で request.getParameter("title") として受け取れます -->
                        <input type="text" name="title" class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-[#1e5641] outline-none" />
                    </div>
                    <div>
                        <label class="block text-sm font-semibold text-[#1e5641] mb-2">著者検索</label>
                        <input type="text" name="author" class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-[#1e5641] outline-none" />
                    </div>
                    <div>
                        <label class="block text-sm font-semibold text-[#1e5641] mb-2">ISBN</label>
                        <input type="text" name="isbn" class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-[#1e5641] outline-none font-mono" />
                    </div>
                    <div>
                        <label class="block text-sm font-semibold text-[#1e5641] mb-2">分類</label>
                        <select name="category" class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-[#1e5641] outline-none bg-white">
                            <option value="">すべて</option>
                            <option value="IT">IT</option>
                            <option value="デザイン">デザイン</option>
                            <option value="業務">業務</option>
                        </select>
                    </div>
                </div>
                
                <div class="flex justify-end gap-4 border-t pt-6">
                    <a href="user_home.jsp" class="px-6 py-3 border border-gray-300 text-gray-700 rounded-lg font-bold hover:bg-gray-50 transition-colors">
                        戻る
                    </a>
                    <button type="submit" class="px-8 py-3 bg-[#1e5641] text-white rounded-lg font-bold hover:bg-[#154231] shadow-md transition-colors">
                        検索実行
                    </button>
                </div>
            </form>

        </div>
    </main>
</body>
</html>