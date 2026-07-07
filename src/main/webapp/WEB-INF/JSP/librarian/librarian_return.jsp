<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 返却処理</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">
    <header class="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center gap-4 sticky top-0 z-20">
        <a href="HomeServlet" class="p-2 hover:bg-white/20 rounded-full transition-colors group">
            <svg class="transform group-hover:-translate-x-1 transition-transform" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest">返却処理</h1>
    </header>
    
    <main class="flex-1 p-4 md:p-8 max-w-2xl mx-auto w-full flex flex-col justify-center relative">
        
        <%-- 返却完了メッセージ --%>
        <%-- <c:if test="${not empty successMessage}"> --%>
        <div class="mb-6 bg-teal-50 border border-teal-200 text-teal-800 px-5 py-4 rounded-xl flex items-center gap-3 font-bold shadow-sm">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22c5.523 0 10-4.477 10-10S17.523 2 12 2 2 6.477 2 12s4.477 10 10 10z"/><path d="m9 12 2 2 4-4"/></svg>
            <%-- <c:out value="${successMessage}" /> --%>
            返却処理が完了しました。
        </div>
        <%-- </c:if> --%>

        <div class="bg-white p-8 md:p-12 rounded-3xl shadow-sm border border-gray-100 text-center">
            <div class="mb-10 flex flex-col items-center">
                <!-- 洗練されたアイコン配置 -->
                <div class="w-24 h-24 bg-teal-50 rounded-full flex items-center justify-center text-teal-600 mb-6 shadow-inner">
                    <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 2v20"/><path d="m17 17-5 5-5-5"/></svg>
                </div>
                <h2 class="text-2xl font-bold text-gray-800 mb-2">図書の返却</h2>
                <p class="text-gray-500">図書IDを入力してください</p>
            </div>

            <%-- エラー（該当本なし等） --%>
            <%-- <c:if test="${not empty errorMessage}"> --%>
            <div class="mb-8 bg-red-50 text-red-700 p-4 rounded-xl border border-red-100 font-bold flex items-center justify-center gap-2 shadow-sm">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
                <%-- <c:out value="${errorMessage}" /> --%>
                該当する本は存在しません。IDを再確認してください。
            </div>
            <%-- </c:if> --%>

            <form action="ReturnProcessServlet" method="POST">
                <div class="relative w-full mb-10">
                    <input 
                        type="text" 
                        name="bookId"
                        autofocus
                        placeholder="ISBN / ID" 
                        class="w-full p-6 border-2 border-transparent bg-gray-50 rounded-2xl text-center text-2xl font-mono tracking-widest text-gray-800 font-bold shadow-inner focus:outline-none focus:bg-white focus:border-teal-500 focus:ring-4 focus:ring-teal-500/20 transition-all placeholder-gray-300"
                        required
                    />
                </div>

                <div class="flex flex-col sm:flex-row gap-4">
                    <a href="librarian_home.jsp" class="w-full sm:w-1/3 py-4 border-2 border-gray-200 text-gray-600 rounded-xl font-bold text-center hover:bg-gray-50 hover:text-gray-900 transition-colors">戻る</a>
                    <button type="submit" class="w-full sm:w-2/3 py-4 bg-teal-600 text-white rounded-xl font-bold text-lg hover:bg-teal-700 shadow-lg shadow-teal-700/20 active:scale-95 transition-all">
                        返却確認
                    </button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>