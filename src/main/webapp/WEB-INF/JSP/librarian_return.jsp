<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 返却処理</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-10">
        <a href="librarian_home.jsp" class="p-1 hover:bg-white/20 rounded-full transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">返却処理</h1>
    </header>
    
    <main class="flex-1 p-6 max-w-2xl mx-auto w-full mt-10 relative">
        
        <%-- 返却完了メッセージ --%>
        <%-- <c:if test="${not empty successMessage}"> --%>
        <div class="mb-6 bg-green-50 border border-green-200 text-green-800 px-4 py-3 rounded-lg flex items-center gap-2 font-bold shadow-sm">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22c5.523 0 10-4.477 10-10S17.523 2 12 2 2 6.477 2 12s4.477 10 10 10z"/><path d="m9 12 2 2 4-4"/></svg>
            <%-- <c:out value="${successMessage}" /> --%>
            返却処理が完了しました。
        </div>
        <%-- </c:if> --%>

        <div class="bg-white p-8 rounded-xl shadow-sm border border-gray-300 text-center">
            <div class="mb-8">
                <!-- 下向き矢印付きの本アイコン -->
                <svg class="mx-auto text-teal-600 opacity-70 mb-4" xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 2v20"/><path d="m17 17-5 5-5-5"/></svg>
                <h2 class="text-xl font-bold text-gray-800">図書ISBN / 蔵書ID スキャン</h2>
                <p class="text-gray-500 mt-2">バーコードリーダーで図書をスキャンしてください</p>
            </div>

            <%-- エラー（該当本なし等） --%>
            <%-- <c:if test="${not empty errorMessage}"> --%>
            <div class="mb-6 bg-red-50 text-red-700 p-4 rounded border border-red-200 font-bold">
                <%-- <c:out value="${errorMessage}" /> --%>
                該当する本は存在しません。IDを再確認してください。
            </div>
            <%-- </c:if> --%>

            <%-- サーブレットへのPOSTフォーム --%>
            <form action="ReturnProcessServlet" method="POST">
                <input 
                    type="text" 
                    name="bookId"
                    autofocus
                    placeholder="IDを入力..." 
                    class="w-full p-4 border-2 border-teal-500 bg-teal-50 rounded text-center text-xl font-mono focus:outline-none mb-8"
                    required
                />

                <div class="flex gap-4">
                    <a href="librarian_home.jsp" class="flex-1 py-4 border-2 border-gray-300 text-gray-700 rounded font-bold text-center hover:bg-gray-50 transition-colors">戻る</a>
                    <button type="submit" class="flex-1 py-4 bg-teal-600 text-white rounded font-bold hover:bg-teal-700 shadow-md transition-colors">
                        返却確認
                    </button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>