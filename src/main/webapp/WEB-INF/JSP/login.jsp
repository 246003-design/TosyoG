<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - ログイン</title>
    <!-- Tailwind CSS (CDN) -->
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<!-- 信頼と安全を象徴する深いブルーの背景 -->
<body class="bg-blue-900 min-h-screen relative flex items-center justify-center font-sans antialiased">
    
    <!-- 背景装飾（控えめなオーバーレイで奥行きを演出） -->
    <div class="absolute inset-0 bg-[url('https://images.unsplash.com/photo-1481627834876-b7833e8f5570?auto=format&fit=crop&q=80')] bg-cover bg-center opacity-20 mix-blend-overlay"></div>
    <div class="absolute inset-0 bg-gradient-to-t from-blue-950 to-transparent opacity-80"></div>
    
    <div class="bg-white p-8 md:p-12 rounded-2xl shadow-2xl w-11/12 max-w-md relative z-10">
        <div class="flex justify-center mb-6">
            <!-- ブランドカラーのアイコン -->
            <div class="bg-blue-100 p-4 rounded-full text-blue-800 shadow-inner">
                <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="11" width="18" height="11" rx="2" ry="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>
            </div>
        </div>
        <h2 class="text-2xl font-bold text-gray-800 text-center mb-8 tracking-wider">図書管理システム</h2>
        
        <%-- エラー時のアラート（視認性の高い赤） --%>
        <%-- <c:if test="${not empty errorMessage}"> --%>
        <div class="mb-6 bg-red-50 text-red-600 text-sm flex items-center gap-3 p-4 rounded-xl border border-red-100 font-bold shadow-sm">
            <svg class="shrink-0" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
            <span>
                <%-- <c:out value="${errorMessage}" /> --%>
                IDまたはパスワードが誤っています
            </span>
        </div>
        <%-- </c:if> --%>

        <form action="LoginServlet" method="POST" class="space-y-6">
            <div>
                <label class="block text-sm font-bold text-gray-700 mb-2">ユーザーID</label>
                <input 
                    type="text" 
                    name="userId"
                    placeholder="IDを入力" 
                    class="w-full p-3.5 bg-gray-50 border border-gray-200 rounded-xl focus:bg-white focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                    required
                    autofocus
                />
            </div>
            <div>
                <label class="block text-sm font-bold text-gray-700 mb-2">パスワード</label>
                <input 
                    type="password" 
                    name="password"
                    placeholder="パスワードを入力" 
                    class="w-full p-3.5 bg-gray-50 border border-gray-200 rounded-xl focus:bg-white focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                    required
                />
            </div>
            
            <!-- 補色（ブルーの反対色）であるオレンジ系を用いて、クリック率（アフォーダンス）を高める -->
            <button type="submit" class="w-full bg-orange-500 text-white py-4 rounded-xl font-bold text-lg hover:bg-orange-600 active:transform active:scale-95 shadow-lg shadow-orange-500/30 transition-all mt-6">
                <%-- <c:choose> --%>
                    <%-- <c:when test="${not empty errorMessage}"> --%>
                        <%-- 再ログイン --%>
                    <%-- </c:when> --%>
                    <%-- <c:otherwise> --%>
                        ログイン
                    <%-- </c:otherwise> --%>
                <%-- </c:choose> --%>
                ログイン
            </button>
        </form>
    </div>
</body>
</html>