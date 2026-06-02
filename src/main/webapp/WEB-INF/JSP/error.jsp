<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>システムエラー</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen flex flex-col items-center justify-center p-4">
    <div class="bg-white p-10 rounded-2xl shadow-xl max-w-md w-full text-center border-t-8 border-slate-700">
        <div class="mx-auto w-20 h-20 bg-gray-100 rounded-full flex items-center justify-center mb-6 text-slate-600">
            <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3Z"/><path d="M12 9v4"/><path d="M12 17h.01"/></svg>
        </div>
        <h2 class="text-2xl font-bold text-gray-900 mb-4">システムエラーが発生しました</h2>
        <p class="text-gray-600 mb-8 leading-relaxed">
            お手数ですが、管理者又は担当者へお問い合わせください。
        </p>
        
        <!-- Javaの例外メッセージを出力したい場合などは以下を使えます -->
        <%-- <p class="text-xs text-red-500 mb-4 break-all"><c:out value="${exception.message}" /></p> --%>
        
        <a href="login.jsp" class="block w-full py-3 bg-slate-800 text-white rounded-lg font-bold hover:bg-slate-900 transition-colors shadow-md">
            ログイン画面へ戻る
        </a>
    </div>
</body>
</html>