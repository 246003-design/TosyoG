<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>アクセス拒否</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-red-900 min-h-screen flex flex-col items-center justify-center p-4">
    <div class="text-white text-center flex flex-col items-center">
        <svg class="mb-6 opacity-90" xmlns="http://www.w3.org/2000/svg" width="80" height="80" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="11" width="18" height="11" rx="2" ry="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>
        <h2 class="text-5xl font-bold mb-4 tracking-wider">Access Denied</h2>
        <p class="text-red-200 text-lg mb-12">
            指定された画面へのアクセス権限がありません
        </p>
        <a href="login.jsp" class="px-10 py-3 border-2 border-white/50 text-white rounded-full font-bold hover:bg-white/10 hover:border-white transition-all shadow-lg inline-block">
            トップ(ログイン)へ戻る
        </a>
    </div>
</body>
</html>