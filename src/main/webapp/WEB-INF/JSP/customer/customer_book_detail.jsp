
　　　　　　　　　　　　　　　　　　　<%--      図書詳細＆予約画面　　　　 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書詳細</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    
    <%-- 権限に応じたテーマカラー設定 --%>
    <%-- <c:choose> --%>
        <%-- <c:when test="${sessionScope.role == 'ADMIN'}"><c:set var="themeBg" value="bg-gray-900" /></c:when> --%>
        <%-- <c:when test="${sessionScope.role == 'LIBRARIAN'}"><c:set var="themeBg" value="bg-[#1e3a8a]" /></c:when> --%>
        <%-- <c:otherwise><c:set var="themeBg" value="bg-[#1e5641]" /></c:otherwise> --%>
    <%-- </c:choose> --%>
    <c:set var="themeBg" value="bg-[#1e5641]" /> <%-- モック用 --%>

    <header class="${themeBg} text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-10 transition-colors">
        <a href="javascript:history.back()" class="p-1 hover:bg-white/20 rounded-full transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">図書詳細</h1>
    </header>
    
    <main class="flex-1 p-4 md:p-8 max-w-4xl mx-auto w-full relative">
        <%-- 処理成功メッセージ --%>
        <%-- <c:if test="${not empty successMessage}"> --%>
        <div class="mb-6 bg-green-50 border border-green-200 text-green-800 px-4 py-3 rounded-lg flex items-center gap-2 font-bold shadow-sm">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22c5.523 0 10-4.477 10-10S17.523 2 12 2 2 6.477 2 12s4.477 10 10 10z"/><path d="m9 12 2 2 4-4"/></svg>
            <%-- <c:out value="${successMessage}" /> --%>
            予約が完了しました。
        </div>
        <%-- </c:if> --%>

        <div class="bg-white rounded-2xl shadow-sm border border-gray-200 overflow-hidden">
            <div class="md:flex">
                <div class="md:w-1/3 h-80 md:h-auto bg-gray-100 p-6 flex items-center justify-center border-r border-gray-100">
                    <div class="w-full h-full max-w-[200px] shadow-lg rounded-md overflow-hidden bg-gradient-to-br from-teal-700 to-slate-900 p-4 text-white text-center flex items-center justify-center">
                        <h3 class="font-bold text-lg">カバー画像</h3>
                    </div>
                </div>
                
                <div class="md:w-2/3 p-6 md:p-10 flex flex-col justify-between">
                    <div>
                        <%-- 予約済みバッジの表示 (利用者本人が予約している場合のみ表示など、条件を設定) --%>
                        <%-- <c:if test="${book.isReservedByCurrentUser}"> --%>
                        <div class="inline-flex items-center gap-2 bg-yellow-100 text-yellow-800 px-3 py-1.5 rounded-full text-sm font-bold mb-4 border border-yellow-200">
                            現在予約済みです
                        </div>
                        <%-- </c:if> --%>

                        <h2 class="text-3xl font-bold text-gray-900 mb-2"><%-- <c:out value="${book.title}" /> --%>Web UIデザイン論</h2>
                        <p class="text-lg text-gray-600 mb-6 border-b pb-4">著者: <%-- <c:out value="${book.author}" /> --%>田中三郎</p>
                        
                        <div class="mb-8">
                            <h4 class="text-sm font-bold text-gray-500 mb-2 uppercase tracking-wider">あらすじ</h4>
                            <p class="text-gray-700 leading-relaxed text-sm md:text-base">
                                <%-- <c:out value="${book.synopsis}" /> --%>
                                現代のWeb開発において不可欠なUIデザインの基礎から応用までを網羅したベストセラー。
                            </p>
                        </div>
                    </div>

                    <div class="flex flex-col sm:flex-row gap-4 mt-8 pt-6 border-t border-gray-100">
                        <a href="javascript:history.back()" class="flex-1 py-3.5 border-2 border-gray-300 text-gray-700 rounded-xl font-bold text-center hover:bg-gray-50 transition-colors">
                            戻る
                        </a>
                        
                        <%-- ★改善点: 利用者のみ「予約処理フォーム」を表示し、司書・管理者の場合はボタンを出さない --%>
                        <%-- <c:if test="${sessionScope.role == 'USER'}"> --%>
                        <form action="ReserveServlet" method="POST" class="flex-1 flex">
                            <input type="hidden" name="bookId" value="${book.id}" />

                            <%-- <c:choose> --%>
                                <%-- <c:when test="${book.isReservedByCurrentUser}"> --%>
                                <button type="submit" name="action" value="cancel" class="w-full py-3.5 bg-white border-2 border-red-600 text-red-600 rounded-xl font-bold hover:bg-red-50 transition-colors" onclick="return confirm('予約を取り消しますか？');">
                                    予約を取り消す
                                </button>
                                <%-- </c:when> --%>
                                <%-- <c:otherwise> --%>
                                <button type="submit" name="action" value="reserve" class="w-full py-3.5 ${themeBg} text-white rounded-xl font-bold shadow-md opacity-90 hover:opacity-100 transition-all" onclick="return confirm('この図書を予約してよろしいですか？');">
                                    予約する
                                </button>
                                <%-- </c:otherwise> --%>
                            <%-- </c:choose> --%>
                        </form>
                        <%-- </c:if> --%>
                    </div>
                </div>
            </div>
        </div>
    </main>
</body>
</html>