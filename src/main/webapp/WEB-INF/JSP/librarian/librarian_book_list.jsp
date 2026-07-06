<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>蔵書管理一覧・更新 (司書用)</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    <!-- ヘッダー -->
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-20">
        <a href="BookManagementMenuServlet" class="p-1 hover:bg-white/20 rounded-full transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">蔵書管理一覧・更新 (司書)</h1>
    </header>

    <main class="flex-1 p-4 md:p-6 max-w-7xl mx-auto w-full grid grid-cols-1 lg:grid-cols-3 gap-6">
        
        <!-- 左側：蔵書一覧リスト (2カラム幅) -->
        <div class="lg:col-span-2 flex flex-col gap-4">
            
            <!-- 検索窓 -->
            <div class="bg-white p-4 rounded-xl shadow-sm border border-gray-100 flex gap-2">
                <form action="BookListServlet" method="GET" class="flex w-full gap-2">
                    <input type="text" name="keyword" value="${fn:escapeXml(keyword)}" placeholder="書名、著者、ISBNで検索..." class="flex-1 border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <button type="submit" class="bg-[#1e3a8a] text-white px-6 py-2 rounded-lg font-bold hover:bg-blue-800 transition-colors">検索</button>
                </form>
            </div>

            <!-- エラーメッセージの表示 -->
            <c:if test="${not empty errorMessage}">
                <div class="bg-red-50 text-red-600 p-4 rounded-lg border border-red-200">
                    ${errorMessage}
                </div>
            </c:if>

            <!-- 蔵書カードリスト（スクロール可能エリア） -->
            <div class="space-y-3 overflow-y-auto max-h-[calc(100vh-220px)] pr-2">
                
                <c:choose>
                    <c:when test="${empty bookList}">
                        <div class="bg-white p-8 text-center rounded-2xl border border-gray-100 text-gray-500">
                            該当する蔵書が見つかりませんでした。
                        </div>
                    </c:when>
                    <c:otherwise>
                        <!-- 💡 DBから取得した本をループ処理 -->
                        <c:forEach var="book" items="${bookList}">
                            <!-- カードをクリックすると、右側の詳細フォームにデータをセットする設計 -->
                            <!-- 💡 book.bookInfo から各情報を安全に取得 -->
                            <div onclick="selectBook('${book.id}', '${book.bookInfo.isbn}', '${book.bookInfo.title}', '${book.bookInfo.authorId}', '${book.bookInfo.publisherId}', '${book.bookInfo.categoryId}', '${book.bookInfo.imageUrl}', '${book.bookNumber}', '${book.layoutId}')"
                                 class="bg-white p-4 rounded-xl shadow-sm border border-gray-100 hover:border-blue-300 hover:shadow transition-all cursor-pointer flex gap-4">
                                
                                <!-- 本の画像 -->
                                <div class="w-16 h-24 bg-gray-100 rounded overflow-hidden flex-shrink-0 flex items-center justify-center">
                                    <c:choose>
                                        <c:when test="${not empty book.bookInfo.imageUrl}">
                                            <img src="${book.bookInfo.imageUrl}" alt="${book.bookInfo.title}" class="w-full h-full object-cover" onerror="this.src='https://placehold.co/150x200?text=No+Image'">
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-xs text-gray-400">No Image</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                
                                <!-- 本の情報 -->
                                <div class="flex-1 min-w-0">
                                    <div class="flex items-center gap-2 mb-1">
                                        <span class="text-xs bg-blue-50 text-blue-700 px-2 py-0.5 rounded-full font-bold">図書ID: ${book.id}</span>
                                        <span class="text-xs text-gray-400">ISBN: ${book.bookInfo.isbn}</span>
                                    </div>
                                    <h3 class="font-bold text-gray-900 truncate">${book.bookInfo.title}</h3>
                                    <p class="text-sm text-gray-500 truncate">著者ID: ${book.bookInfo.authorId} / 出版社ID: ${book.bookInfo.publisherId}</p>
                                    <div class="mt-2 flex gap-4 text-xs text-gray-400">
                                        <span>配置場所: ${book.layoutId}</span>
                                        <span>本番号: ${book.bookNumber}</span>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>

        <!-- 右側：選択された蔵書の詳細・編集エリア (1カラム幅) -->
        <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 h-fit sticky top-24">
            <h2 class="text-xl font-bold text-gray-900 mb-6 border-b pb-3">詳細・情報更新 (司書権限)</h2>
            
            <form action="BookUpdateServlet" method="POST" id="editForm" class="space-y-4">
                <input type="hidden" name="bookId" id="editBookId">
                
                <div>
                    <label class="block text-xs font-bold text-gray-500 uppercase mb-1">ISBN</label>
                    <input type="text" name="isbn" id="editIsbn"required class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none">
                </div>

                <div>
                    <label class="block text-xs font-bold text-gray-500 uppercase mb-1">書名</label>
                    <input type="text" name="title" id="editTitle" required class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none">
                </div>

                <div class="grid grid-cols-2 gap-3">
                    <div>
                        <label class="block text-xs font-bold text-gray-500 uppercase mb-1">著者 (ID)</label>
                        <input type="text" name="authorId" id="editAuthorId" class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none">
                    </div>
                    <div>
                        <label class="block text-xs font-bold text-gray-500 uppercase mb-1">出版社 (ID)</label>
                        <input type="text" name="publisherId" id="editPublisherId" class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none">
                    </div>
                </div>

                <div class="grid grid-cols-2 gap-3">
                    <div>
                        <label class="block text-xs font-bold text-gray-500 uppercase mb-1">配置場所 (ID)</label>
                        <input type="number" name="layoutId" id="editLayoutId" required class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none">
                    </div>
                    <div>
                        <label class="block text-xs font-bold text-gray-500 uppercase mb-1">本番号</label>
                        <input type="number" name="bookNumber" id="editBookNumber" required class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none">
                    </div>
                </div>

                <div>
                    <label class="block text-xs font-bold text-gray-500 uppercase mb-1">画像用URL</label>
                    <input type="text" name="imageUrl" id="editImageUrl" class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none">
                </div>

                <div class="pt-4 border-t flex justify-end gap-2">
                    <button type="submit" class="bg-[#1e3a8a] text-white px-6 py-2 rounded-lg font-bold hover:bg-blue-800 transition-colors">
                        更新を保存
                    </button>
                </div>
            </form>
        </div>

    </main>

    <!-- JavaScriptでサイドバーに情報をバインド -->
    <script>
        function selectBook(id, isbn, title, authorId, publisherId, categoryId, imageUrl, bookNumber, layoutId) {
            document.getElementById('editBookId').value = id;
            document.getElementById('editIsbn').value = isbn;
            document.getElementById('editTitle').value = title;
            document.getElementById('editAuthorId').value = authorId;
            document.getElementById('editPublisherId').value = publisherId;
            document.getElementById('editLayoutId').value = layoutId;
            document.getElementById('editBookNumber').value = bookNumber;
            document.getElementById('editImageUrl').value = imageUrl;
        }
    </script>
</body>
</html>