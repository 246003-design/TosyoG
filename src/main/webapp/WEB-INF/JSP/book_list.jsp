<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>蔵書管理一覧・更新</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    <%-- 権限テーマ --%>
    <%-- <c:set var="themeBg" value="${sessionScope.role == 'ADMIN' ? 'bg-gray-900' : 'bg-[#1e3a8a]'}" /> --%>
    <c:set var="themeBg" value="bg-[#1e3a8a]" />

    <header class="${themeBg} text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-20">
        <a href="BookManagementMenuServlet" class="p-1 hover:bg-white/20 rounded-full transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">蔵書検索・一覧・更新</h1>
    </header>

    <main class="flex-1 p-4 md:p-8 max-w-6xl mx-auto w-full relative">
        <%-- メッセージ領域 --%>
        <%-- <c:if test="${not empty message}"> --%>
        <div class="mb-4 bg-green-50 text-green-800 p-3 rounded border border-green-200 font-bold text-center">
            <%-- <c:out value="${message}" /> --%> 操作が完了しました。
        </div>
        <%-- </c:if> --%>
        
        <%-- ▼▼▼ 【一覧表示モード】 ▼▼▼ --%>
        <%-- <c:if test="${not isEditMode}"> --%>
        <div class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden flex flex-col">
            
            <form action="BookListServlet" method="GET" class="p-4 border-b border-gray-200 bg-gray-50 flex flex-wrap gap-4 justify-between items-center">
                <div class="relative w-full md:w-96 flex items-center">
                    <input type="text" name="keyword" placeholder="キーワードを入力" class="w-full pl-3 pr-4 py-2 border border-gray-300 rounded-l-lg focus:outline-none focus:ring-2 focus:ring-gray-400" />
                    <button type="submit" class="bg-gray-700 text-white px-4 py-2 rounded-r-lg font-bold">検索</button>
                </div>
                
                <%-- 管理者の場合のみ「新規登録」ボタンを表示 --%>
                <%-- <c:if test="${sessionScope.role == 'ADMIN'}"> --%>
                <div class="w-full md:w-auto">
                    <a href="BookRegisterServlet" class="block text-center px-4 py-2 border border-gray-800 text-gray-800 font-bold rounded hover:bg-gray-100 whitespace-nowrap">新規登録</a>
                </div>
                <%-- </c:if> --%>
            </form>

            <div class="overflow-x-auto">
                <table class="w-full text-left border-collapse min-w-max">
                    <thead class="bg-gray-200 text-gray-800">
                        <tr>
                            <th class="p-3 font-semibold text-sm">タイトル</th>
                            <th class="p-3 font-semibold text-sm">著者</th>
                            <th class="p-3 font-semibold text-sm">ISBN</th>
                            <th class="p-3 font-semibold text-sm text-center">状態</th>
                            <th class="p-3 font-semibold text-sm text-center">操作</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        <%-- <c:forEach var="book" items="${bookList}"> --%>
                        <tr class="hover:bg-gray-50 transition-colors bg-white">
                            <td class="p-3 font-bold text-gray-800">モダンエンジニアリング</td>
                            <td class="p-3 text-gray-600 text-sm">佐藤次郎</td>
                            <td class="p-3 text-gray-500 font-mono text-sm">978-4-002</td>
                            <td class="p-3 text-center">
                                <span class="px-2 py-1 rounded text-xs font-bold bg-yellow-100 text-yellow-800">貸出中</span>
                            </td>
                            <td class="p-3 text-center">
                                <!-- 編集モードへ遷移 (GET) -->
                                <a href="BookEditServlet?id=123" class="inline-block px-4 py-1.5 border border-gray-600 text-gray-700 rounded hover:bg-gray-100 transition-colors text-sm font-bold">更新</a>
                            </td>
                        </tr>
                        <%-- </c:forEach> --%>
                    </tbody>
                </table>
            </div>
        </div>
        <%-- </c:if> --%>
        
        <%-- ▼▼▼ 【編集・削除モード】 ▼▼▼ --%>
        <%-- <c:if test="${isEditMode}"> --%>
        <div class="bg-white p-8 rounded-xl shadow-sm border border-gray-300 hidden">
            <!-- ※確認用 hidden -->
            
            <%-- 貸出中の警告 --%>
            <%-- <c:if test="${book.status == '貸出中'}"> --%>
            <div class="mb-6 bg-yellow-50 border-l-4 border-yellow-500 p-4 rounded text-yellow-800 font-bold">
                貸出中のため実行できません（情報の更新・削除は「貸出可」の書籍のみ行えます）
            </div>
            <%-- </c:if> --%>

            <%-- ★改善点: 画像をアップロードできるように enctype を追加 --%>
            <form action="BookUpdateServlet" method="POST" enctype="multipart/form-data" class="md:flex gap-8 max-w-4xl mx-auto">
                <input type="hidden" name="id" value="${book.id}" />
                
                <!-- 左側：画像アップロード領域 -->
                <div class="md:w-1/3 mb-6 md:mb-0">
                    <div class="w-full h-64 bg-gray-100 border border-gray-300 rounded flex flex-col items-center justify-center p-4">
                        <span class="text-gray-400 font-mono text-sm mb-4">COVER IMAGE</span>
                        <!-- 貸出中なら画像も変更不可に -->
                        <input type="file" name="coverImage" accept="image/*" class="w-full text-xs" <%-- <c:if test="${book.status == '貸出中'}">disabled</c:if> --%> />
                    </div>
                </div>

                <!-- 右側：テキストフォーム -->
                <div class="md:w-2/3 space-y-4">
                    <%-- ★改善点: disabled の値はPOSTされないため、貸出中の場合は readonly を使うか hidden で値を補完します --%>
                    <%-- <c:set var="isLocked" value="${book.status == '貸出中' ? 'readonly' : ''}" /> --%>
                    
                    <div>
                        <label class="block text-xs font-bold text-gray-500 mb-1 uppercase">タイトル</label>
                        <input type="text" name="title" value="モダンエンジニアリング" class="w-full p-2.5 border border-gray-300 rounded font-bold text-lg outline-none" <%-- ${isLocked} --%> />
                    </div>
                    <div class="grid grid-cols-2 gap-4">
                        <div>
                            <label class="block text-xs font-bold text-gray-500 mb-1 uppercase">ISBN(変更不可)</label>
                            <input type="text" value="978-4-002" readonly class="w-full p-2.5 border border-gray-200 rounded bg-gray-100 text-gray-500 font-mono outline-none" />
                        </div>
                        <div>
                            <label class="block text-xs font-bold text-gray-500 mb-1 uppercase">分類</label>
                            <!-- select は readonly が効かないため、disabled にする場合は別途 hidden が必要です -->
                            <select name="category" class="w-full p-2.5 border border-gray-300 rounded outline-none" <%-- <c:if test="${book.status == '貸出中'}">disabled</c:if> --%>>
                                <option value="技術">技術</option>
                            </select>
                            <%-- <c:if test="${book.status == '貸出中'}"> --%>
                                <%-- <input type="hidden" name="category" value="${book.category}" /> --%>
                            <%-- </c:if> --%>
                        </div>
                    </div>
                    <div class="grid grid-cols-2 gap-4">
                        <div>
                            <label class="block text-xs font-bold text-gray-500 mb-1 uppercase">著者</label>
                            <input type="text" name="author" value="佐藤次郎" class="w-full p-2.5 border border-gray-300 rounded outline-none" <%-- ${isLocked} --%> />
                        </div>
                        <div>
                            <label class="block text-xs font-bold text-gray-500 mb-1 uppercase">出版社</label>
                            <input type="text" name="publisher" value="技術出版" class="w-full p-2.5 border border-gray-300 rounded outline-none" <%-- ${isLocked} --%> />
                        </div>
                    </div>
                    
                    <div class="pt-6 mt-6 border-t border-gray-200 flex flex-col md:flex-row justify-between items-center gap-4">
                        <!-- 削除サーブレットへ送信 -->
                        <button type="submit" formaction="BookDeleteServlet" class="w-full md:w-auto text-red-600 font-bold hover:underline px-4 py-2" onclick="return confirm('本当に削除してよろしいですか？');" <%-- <c:if test="${book.status == '貸出中'}">disabled</c:if> --%>>
                            登録取消 (削除)
                        </button>

                        <div class="flex w-full md:w-auto gap-3">
                            <a href="BookListServlet" class="flex-1 md:flex-none px-6 py-3 border border-gray-300 rounded font-bold text-gray-700 text-center hover:bg-gray-50">戻る</a>
                            <button type="submit" class="${themeBg} text-white px-8 py-3 rounded font-bold" onclick="return confirm('更新内容を保存しますか？');" <%-- <c:if test="${book.status == '貸出中'}">disabled</c:if> --%>>
                                更新内容を確認
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <%-- </c:if> --%>
    </main>
</body>
</html>