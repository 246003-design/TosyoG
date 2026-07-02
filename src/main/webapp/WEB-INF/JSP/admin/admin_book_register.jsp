<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 蔵書登録</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    
    <!-- ヘッダー -->
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-20">
        <!-- 戻るボタン（Servlet経由での画面遷移に合わせています） -->
        <a href="BookManagementMenuServlet" class="p-1 hover:bg-white/20 rounded-full transition-colors" title="ホームに戻る">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest">蔵書新規登録</h1>
    </header>

    <main class="flex-1 flex flex-col items-center p-6">
        
        <!-- エラーメッセージ表示エリア -->
        <% String errorMsg = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMsg != null && !errorMsg.isEmpty()) { %>
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4 w-full max-w-2xl text-sm">
                <%= errorMsg %>
            </div>
        <% } %>

        <div class="bg-white rounded-2xl shadow-md p-8 w-full max-w-2xl border border-gray-100">
            
            <form action="BookRegisterServlet" method="post" class="flex flex-col gap-5">
                
                <!-- 1. ISBN入力と自動検索 -->
                <div class="bg-blue-50/50 p-4 rounded-xl border border-blue-100">
                    <label for="isbn" class="block text-sm font-bold text-gray-700 mb-1">ISBN</label>
                    <div class="flex gap-2">
                        <input type="text" id="isbn" name="isbn" required placeholder="例: 9784..." class="flex-1 border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-[#1e3a8a]/50">
                        <button type="button" id="search-btn" class="bg-[#1e3a8a] hover:bg-blue-800 text-white font-bold py-2 px-4 rounded-lg transition-colors flex items-center gap-2 whitespace-nowrap shadow-sm">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
                            情報取得
                        </button>
                    </div>
                    <!-- ここに検索ステータスが出ます -->
                    <p id="search-status" class="text-xs text-gray-500 mt-2 h-4"></p>
                </div>

                <!-- 2. 自動入力される情報エリア -->
                <div class="grid grid-cols-1 md:grid-cols-3 gap-5">
                    
                    <!-- 画像プレビュー -->
                    <div class="col-span-1 flex flex-col items-center justify-start pt-2">
                        <div id="cover-preview-area" class="w-32 h-48 bg-gray-100 rounded-lg border-2 border-dashed border-gray-300 flex items-center justify-center overflow-hidden hidden shadow-inner">
                            <img id="cover-img" src="" alt="表紙プレビュー" class="w-full h-full object-cover">
                        </div>
                        <p class="text-xs text-gray-400 mt-2 text-center">表紙画像</p>
                    </div>

                    <!-- 本のテキスト情報 -->
                    <div class="col-span-1 md:col-span-2 flex flex-col gap-4">
                        <div>
                            <label for="title" class="block text-sm font-bold text-gray-700 mb-1">タイトル <span class="text-red-500">*</span></label>
                            <input type="text" id="title" name="title" required class="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-[#1e3a8a]/50 bg-gray-50">
                        </div>
                        <div>
                            <label for="author" class="block text-sm font-bold text-gray-700 mb-1">著者</label>
                            <input type="text" id="author" name="author" class="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-[#1e3a8a]/50 bg-gray-50">
                        </div>
                        <div>
                            <label for="publisher" class="block text-sm font-bold text-gray-700 mb-1">出版社</label>
                            <input type="text" id="publisher" name="publisher" class="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-[#1e3a8a]/50 bg-gray-50">
                        </div>
                        <div>
                            <label for="categoryName" class="block text-sm font-bold text-gray-700 mb-1">カテゴリ</label>
                            <select id="categoryName" name="categoryName" class="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-[#1e3a8a]/50 bg-white">
                                <option value="文学">文学</option>
                                <option value="技術">技術</option>
                                <option value="実用">実用</option>
                            </select>
                        </div>
                    </div>
                </div>

                <!-- 隠し項目：画像URLをJavaに送る用 -->
                <input type="hidden" id="imageUrl" name="imageUrl" value="">

                <hr class="border-gray-200 my-2">

                <!-- 送信ボタン -->
                <div class="flex justify-end gap-3 mt-2">
                    <button type="reset" class="px-6 py-2 border border-gray-300 rounded-lg text-gray-600 hover:bg-gray-50 font-bold transition-colors">
                        クリア
                    </button>
                    <button type="submit" class="px-8 py-2 bg-green-600 hover:bg-green-700 text-white rounded-lg font-bold shadow-md hover:shadow-lg transition-all">
                        この内容で登録する
                    </button>
                </div>
            </form>
        </div>
    </main>

    <!-- 💡 ここが修正された安全なJavaScriptです！ -->
    <script>
    document.getElementById('search-btn').addEventListener('click', function() {
        const isbn = document.getElementById('isbn').value.trim();
        const statusText = document.getElementById('search-status');

        if (!isbn) {
            statusText.innerText = "⚠️ ISBNコードを入力してください。";
            statusText.className = "text-xs font-bold mt-1 text-red-500";
            return;
        }

        statusText.innerText = "⏳ Google Books から情報を検索中...";
        statusText.className = "text-xs font-bold mt-1 text-blue-500";

        const url = 'https://www.googleapis.com/books/v1/volumes?q=isbn:' + isbn;

        fetch(url)
            .then(response => {
                // 429エラーなどの通信拒否があった場合はここでキャッチ
                if (!response.ok) {
                    throw new Error("通信エラー: " + response.status);
                }
                return response.json();
            })
            .then(data => {
                // 🌟 超重要：データがちゃんと存在するか（undefinedじゃないか）をチェックする安全装置
                if (data.items && data.items.length > 0) {
                    
                    // 無事にデータがあった場合のみ取り出し処理をする
                    const bookInfo = data.items[0].volumeInfo;
                    
                    document.getElementById('title').value = bookInfo.title || '';
                    document.getElementById('author').value = bookInfo.authors ? bookInfo.authors.join(', ') : '';
                    document.getElementById('publisher').value = bookInfo.publisher || '';

                    // 画像URLの取得とプレビュー表示
                    const previewArea = document.getElementById('cover-preview-area');
                    const coverImg = document.getElementById('cover-img');
                    const imageUrlHidden = document.getElementById('imageUrl');

                    if (bookInfo.imageLinks && bookInfo.imageLinks.thumbnail) {
                        const secureUrl = bookInfo.imageLinks.thumbnail.replace('http://', 'https://');
                        coverImg.src = secureUrl;
                        imageUrlHidden.value = secureUrl; 
                        previewArea.classList.remove('hidden');
                    } else {
                        previewArea.classList.add('hidden');
                        imageUrlHidden.value = '';
                    }

                    statusText.innerText = "✨ 情報を自動入力しました！";
                    statusText.className = "text-xs font-bold mt-1 text-green-600";
                    
                } else {
                    // 🌟 安全装置その2：通信は成功したけど「本が見つからなかった」場合
                    statusText.innerText = "⚠️ 該当する本が見つかりませんでした。手入力してください。";
                    statusText.className = "text-xs font-bold mt-1 text-yellow-600";
                }
            })
            .catch(error => {
                // 💥 429エラー(Too Many Requests)などが起きた場合はここに来る
                console.error('API Error:', error);
                statusText.innerText = "💥 通信制限中、またはエラーが発生しました。手入力してください。";
                statusText.className = "text-xs font-bold mt-1 text-red-500";
            });
    });
    </script>
</body>
</html>