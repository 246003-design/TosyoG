
　　　　　　　　　　　　　　　　　　　<%--      蔵書管理画面　　　　 --%>

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
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-20">
        <a href="librarian_book_menu.jsp" class="p-1 hover:bg-white/20 rounded-full transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">
            <%-- <c:choose><c:when test="${isEditMode}"> --%>
            <!-- 編集モードの場合のタイトル（切り替え用） -->
            <%-- 蔵書情報更新 --%>
            <%-- </c:when><c:otherwise> --%>
            蔵書管理:検索結果一覧
            <%-- </c:otherwise></c:choose> --%>
        </h1>
    </header>

    <main class="flex-1 p-4 md:p-8 max-w-6xl mx-auto w-full relative">
        
        <%-- ▼▼▼ ここから【一覧表示モード】のHTML ▼▼▼ --%>
        <%-- <c:if test="${not isEditMode}"> --%>
        <div class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden flex flex-col">
            <!-- 検索フォーム -->
            <form action="BookListServlet" method="GET" class="p-4 border-b border-gray-200 bg-gray-50 flex justify-between items-center">
                <div class="relative w-full max-w-md flex items-center">
                    <input type="text" name="keyword" placeholder="キーワード検索 (タイトル, ISBN...)" class="w-full pl-3 pr-4 py-2 border border-gray-300 rounded-l-lg focus:outline-none focus:ring-2 focus:ring-[#1e3a8a]" />
                    <button type="submit" class="bg-[#1e3a8a] text-white px-4 py-2 rounded-r-lg font-bold border border-[#1e3a8a]">検索</button>
                </div>
            </form>

            <div class="overflow-x-auto">
                <table class="w-full text-left border-collapse min-w-max">
                    <thead class="bg-[#1e3a8a] text-white">
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
                        <tr class="hover:bg-blue-50 transition-colors bg-white">
                            <td class="p-3 font-bold text-gray-800">Webシステム設計論</td>
                            <td class="p-3 text-gray-600 text-sm">司書次郎</td>
                            <td class="p-3 text-gray-500 font-mono text-sm">978-4-00-000001-0</td>
                            <td class="p-3 text-center">
                                <span class="px-2 py-1 rounded text-xs font-bold bg-green-100 text-green-700">貸出可</span>
                            </td>
                            <td class="p-3 text-center">
                                <!-- 編集モード(isEditMode=true)へ遷移するためのGETリクエスト -->
                                <a href="BookEditServlet?id=123" class="inline-block px-4 py-1.5 border border-[#1e3a8a] text-[#1e3a8a] rounded hover:bg-[#1e3a8a] hover:text-white transition-colors text-sm font-bold">編集</a>
                            </td>
                        </tr>
                        <%-- </c:forEach> --%>
                    </tbody>
                </table>
            </div>
        </div>
        <%-- </c:if> --%>
        <%-- ▲▲▲ ここまで【一覧表示モード】のHTML ▲▲▲ --%>



        <%-- ▼▼▼ ここから【編集・削除モード】のHTML ▼▼▼ --%>
        <%-- <c:if test="${isEditMode}"> --%>
        <div class="bg-white p-8 rounded-xl shadow-sm border border-gray-300 hidden">
            <!-- ※確認用に hidden クラスをつけています。本番で実装する際は hidden を外してください -->
            
            <%-- 貸出中の警告 --%>
            <%-- <c:if test="${book.status == '貸出中'}"> --%>
            <div class="mb-6 bg-yellow-50 border-l-4 border-yellow-500 p-4 rounded text-yellow-800 font-bold flex items-center gap-2">
                <span>貸出中のため実行できません（情報の更新・登録取消は「貸出可」の書籍のみ行えます）</span>
            </div>
            <%-- </c:if> --%>

            <div class="md:flex gap-8">
                <!-- 更新フォーム -->
                <form action="BookUpdateServlet" method="POST" class="md:w-2/3 space-y-4">
                    <input type="hidden" name="id" value="${book.id}" />
                    <div>
                        <label class="block text-xs font-bold text-gray-500 mb-1 uppercase">タイトル</label>
                        <!-- 貸出中なら readonly を付与するなどの制御をJSTLで行います -->
                        <input type="text" name="title" value="Webシステム設計論" class="w-full p-2.5 border border-gray-300 rounded font-bold text-lg outline-none focus:ring-2 focus:ring-[#1e3a8a]" />
                    </div>
                    <div class="grid grid-cols-2 gap-4">
                        <div>
                            <label class="block text-xs font-bold text-gray-500 mb-1 uppercase">ISBN(変更不可)</label>
                            <input type="text" value="978-4-00-000001-0" readonly class="w-full p-2.5 border border-gray-200 rounded bg-gray-100 text-gray-500 font-mono outline-none" />
                        </div>
                        <div>
                            <label class="block text-xs font-bold text-gray-500 mb-1 uppercase">分類</label>
                            <select name="category" class="w-full p-2.5 border border-gray-300 rounded outline-none focus:ring-2">
                                <option value="技術">技術</option>
                                <option value="デザイン">デザイン</option>
                            </select>
                        </div>
                    </div>
                    
                    <div class="pt-6 mt-6 border-t border-gray-200 flex flex-col md:flex-row justify-between items-center gap-4">
                        <!-- 削除は別のアクション（ボタン）でPOST送信 -->
                        <button type="submit" formaction="BookDeleteServlet" class="w-full md:w-auto text-red-600 font-bold hover:underline px-4 py-2" onclick="return confirm('本当に削除してよろしいですか？この操作は取り消せません。');">
                            登録取消 (削除)
                        </button>
                        
                        <div class="flex w-full md:w-auto gap-3">
                            <a href="BookListServlet" class="flex-1 md:flex-none px-6 py-3 border border-gray-300 rounded font-bold text-gray-700 text-center hover:bg-gray-50">戻る</a>
                            <button type="submit" class="flex-1 md:flex-none px-8 py-3 bg-[#1e3a8a] text-white rounded font-bold hover:bg-blue-800" onclick="return confirm('更新内容を保存しますか？');">
                                更新内容を確認
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <%-- </c:if> --%>
        <%-- ▲▲▲ ここまで【編集・削除モード】のHTML ▲▲▲ --%>

    </main>
</body>
</html>