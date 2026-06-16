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
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-20">
        <a href="librarian_book_menu.jsp" class="p-1 hover:bg-white/20 rounded-full transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">蔵書登録(手動入力・一括)</h1>
    </header>

    <main class="flex-1 p-4 md:p-8 max-w-4xl mx-auto w-full relative">
        <div class="bg-white p-6 md:p-8 rounded-xl shadow-sm border border-gray-300">
            
            <form action="BookBulkRegisterServlet" method="POST" enctype="multipart/form-data" class="mb-8 p-6 border-2 border-dashed border-gray-300 rounded-xl bg-gray-50 text-center relative">
                <svg class="mx-auto text-gray-400 mb-2" xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="17 8 12 3 7 8"/><line x1="12" y1="3" x2="12" y2="15"/></svg>
                <p class="text-gray-600 font-bold mb-2">ファイルをアップロード (CSV)</p>
                <input type="file" name="csvFile" accept=".csv" required class="mb-3 block w-full text-sm text-slate-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100 cursor-pointer"/>
                <button type="submit" class="px-4 py-2 bg-[#1e3a8a] text-white rounded font-bold shadow-md">一括登録を実行</button>
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
                        <input type="text" id="isbn" name="isbn" placeholder="ISBNを入力 (ハイフンなし13桁推奨)" class="flex-1 p-3 border border-gray-300 rounded focus:ring-2 focus:ring-[#1e3a8a] outline-none font-mono" required />
                        <button type="button" id="btn-lookup" class="px-5 bg-teal-600 text-white rounded font-bold shadow-md hover:bg-teal-700 transition-colors whitespace-nowrap">
                            情報取得
                        </button>
                    </div>
                    <p id="api-status" class="text-xs font-bold mt-1 text-gray-500"></p>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <label class="block text-sm font-bold text-gray-700 mb-1">タイトル</label>
                        <input type="text" id="title" name="title" class="w-full p-3 border border-gray-300 rounded focus:ring-2 focus:ring-[#1e3a8a] outline-none" required />
                    </div>
                    <div>
                        <label class="block text-sm font-bold text-gray-700 mb-1">著者</label>
                        <input type="text" id="author" name="author" class="w-full p-3 border border-gray-300 rounded focus:ring-2 focus:ring-[#1e3a8a] outline-none" required />
                    </div>
                    <div>
                        <label class="block text-sm font-bold text-gray-700 mb-1">出版社</label>
                        <input type="text" id="publisher" name="publisher" class="w-full p-3 border border-gray-300 rounded focus:ring-2 focus:ring-[#1e3a8a] outline-none" required />
                    </div>
                    <div>
                        <label class="block text-sm font-bold text-gray-700 mb-1">分類</label>
                        <select name="category" class="w-full p-3 border border-gray-300 rounded focus:ring-2 focus:ring-[#1e3a8a] outline-none bg-white">
                            <option value="文芸">文芸</option>
                            <option value="技術">技術</option>
                            <option value="実用">実用</option>
                            <option value="その他">その他</option>
                        </select>
                    </div>
                </div>

                <div id="cover-preview-area" class="hidden border-t border-gray-200 pt-6 flex items-center gap-4">
                    <div class="w-24 h-32 bg-gray-100 rounded border border-gray-300 flex items-center justify-center overflow-hidden shadow-sm">
                        <img id="cover-img" src="" alt="表紙プレビュー" class="w-full h-full object-cover">
                    </div>
                    <div>
                        <p class="text-sm font-bold text-gray-700">表紙画像プレビュー</p>
                        <p class="text-xs text-gray-500">Google Books APIから取得した画像URLを保存します。</p>
                        <input type="hidden" id="imageUrl" name="imageUrl" value="" />
                    </div>
                </div>

                <div class="flex gap-4 mt-8 pt-6 border-t border-gray-100">
                    <a href="librarian_book_menu.jsp" class="flex-1 py-4 border-2 border-gray-300 text-gray-700 rounded font-bold text-center hover:bg-gray-50">戻る</a>
                    <button type="submit" class="flex-[2] py-4 bg-[#1e3a8a] text-white rounded font-bold hover:bg-blue-800 shadow-md transition-colors" onclick="return confirm('登録してよろしいですか？');">
                        登録する
                    </button>
                </div>
            </form>
        </div>
    </main>

    <script>
    document.getElementById('btn-lookup').addEventListener('click', function() {
        const isbnInput = document.getElementById('isbn').value.trim().replace(/-/g, ''); // ハイフンを除去
        const statusText = document.getElementById('api-status');
        
        if (!isbnInput) {
            alert('ISBNを入力してください。');
            return;
        }

        statusText.innerText = "🔍 Googleからデータを検索中...";
        statusText.className = "text-xs font-bold mt-1 text-blue-600";

        // Google Books APIを直接呼び出す
        fetch(`https://www.googleapis.com/books/v1/volumes?q=isbn:${isbnInput}`)
            .then(response => response.json())
            .then(data => {
                if (data.totalItems === 0) {
                    statusText.innerText = "❌ 本が見つかりませんでした。手動で入力してください。";
                    statusText.className = "text-xs font-bold mt-1 text-red-500";
                    return;
                }

                // APIのデータ構造から必要な情報を引き出す
                const bookInfo = data.items[0].volumeInfo;
                
                // 各入力欄に自動でセット！
                document.getElementById('title').value = bookInfo.title || '';
                document.getElementById('author').value = bookInfo.authors ? bookInfo.authors.join(', ') : '';
                document.getElementById('publisher').value = bookInfo.publisher || '';

                // 画像URLの取得とプレビュー表示
                const previewArea = document.getElementById('cover-preview-area');
                const coverImg = document.getElementById('cover-img');
                const imageUrlHidden = document.getElementById('imageUrl');

                if (bookInfo.imageLinks && bookInfo.imageLinks.thumbnail) {
                    // httpをhttpsにセキュア化してセット
                    const secureUrl = bookInfo.imageLinks.thumbnail.replace('http://', 'https://');
                    coverImg.src = secureUrl;
                    imageUrlHidden.value = secureUrl; // 隠しインプットにURLを保存（Javaへ送る用）
                    previewArea.classList.remove('hidden');
                } else {
                    previewArea.classList.add('hidden');
                    imageUrlHidden.value = '';
                }

                statusText.innerText = "✨ 情報を自動入力しました！内容を確認してください。";
                statusText.className = "text-xs font-bold mt-1 text-green-600";
            })
            .catch(error => {
                console.error('Error:', error);
                statusText.innerText = "💥 通信エラーが発生しました。";
                statusText.className = "text-xs font-bold mt-1 text-red-500";
            });
    });
    </script>
</body>
</html>