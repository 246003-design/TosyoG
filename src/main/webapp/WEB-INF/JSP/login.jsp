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
<body class="bg-slate-900 min-h-screen relative flex items-center justify-center">
    
    <!-- 背景装飾 -->
    <div class="absolute inset-0 bg-[url('https://images.unsplash.com/photo-1481627834876-b7833e8f5570?auto=format&fit=crop&q=80')] bg-cover bg-center opacity-30 mix-blend-overlay"></div>
    <div class="absolute inset-0 bg-gradient-to-t from-slate-900 to-transparent"></div>
    
    <div class="bg-white/95 backdrop-blur-sm p-8 md:p-12 rounded-2xl shadow-2xl w-11/12 max-w-md relative z-10 border-t-8 border-slate-700">
        <div class="flex justify-center mb-6">
            <div class="bg-slate-700 p-4 rounded-full text-white shadow-lg">
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="11" width="18" height="11" rx="2" ry="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>
            </div>
        </div>
        <h2 class="text-2xl font-bold text-slate-800 text-center mb-8 tracking-widest">図書管理システム</h2>
        
        <%-- サーブレットからエラーメッセージが渡された場合の表示 --%>
        <%-- <c:if test="${not empty errorMessage}"> --%>
        <div class="mb-4 text-red-600 text-sm flex items-center gap-2 bg-red-50 p-3 rounded-lg border border-red-200 font-bold">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
            <span>
                <%-- <c:out value="${errorMessage}" /> --%>
                IDまたはパスワードが誤っています
            </span>
        </div>
        <%-- </c:if> --%>

        <!-- 認証用サーブレットへのPOSTフォーム -->
        <form action="LoginServlet" method="POST" class="space-y-6">
            <div>
                <label class="block text-sm font-semibold text-slate-700 mb-2">ユーザーID</label>
                <input 
                    type="text" 
                    name="userId"
                    placeholder="IDを入力" 
                    class="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-slate-700"
                    required
                    autofocus
                />
            </div>
            <div>
                <label class="block text-sm font-semibold text-slate-700 mb-2">パスワード</label>
                <input 
                    type="password" 
                    name="password"
                    placeholder="パスワードを入力" 
                    class="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-slate-700"
                    required
                />
            </div>
            
            <button type="submit" class="w-full bg-slate-800 text-white py-3.5 rounded-lg font-bold hover:bg-slate-900 shadow-md transition-colors mt-4">
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