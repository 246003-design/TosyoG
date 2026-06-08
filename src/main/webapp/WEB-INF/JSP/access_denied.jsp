<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>アクセス拒否</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<!-- 禁止を表すが、フラストレーションを与えすぎない深いローズレッド -->
<body class="bg-rose-900 min-h-screen flex flex-col items-center justify-center p-4 font-sans antialiased relative overflow-hidden">
    
    <!-- 背景の装飾的な斜線（UXの視覚的アクセント） -->
    <div class="absolute inset-0 opacity-10" style="background-image: repeating-linear-gradient(45deg, #000 0, #000 2px, transparent 2px, transparent 10px);"></div>

    <div class="text-white text-center flex flex-col items-center relative z-10 max-w-lg w-full bg-black/20 p-10 rounded-3xl backdrop-blur-sm border border-white/10 shadow-2xl">
        <!-- 拒否を示すアイコン -->
        <div class="w-24 h-24 bg-rose-950/50 rounded-full flex items-center justify-center mb-8 border-2 border-rose-400/30">
            <svg class="text-rose-200" xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="11" width="18" height="11" rx="2" ry="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>
        </div>
        
        <h2 class="text-4xl md:text-5xl font-black mb-4 tracking-widest text-transparent bg-clip-text bg-gradient-to-br from-white to-rose-200">
            ACCESS DENIED
        </h2>
        
        <p class="text-rose-100 text-lg mb-10 font-medium tracking-wide">
            指定された画面へのアクセス権限がありません
        </p>
        
        <!-- コントラストを高めた白基調のボタンで、戻るアクションを明確に提示 -->
        <a href="login.jsp" class="group relative inline-flex items-center justify-center gap-3 px-10 py-4 bg-white text-rose-900 rounded-full font-bold text-lg hover:bg-rose-50 hover:shadow-[0_0_20px_rgba(255,255,255,0.3)] active:scale-95 transition-all w-full md:w-auto">
            <svg class="transition-transform group-hover:-translate-x-1" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
            トップ(ログイン)へ戻る
        </a>
    </div>
</body>
</html>