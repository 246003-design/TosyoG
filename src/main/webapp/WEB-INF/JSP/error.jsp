
　　　　　　　　　　　　　　　　　　　<%--      システムエラー画面　　　　 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>システムエラー</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 min-h-screen flex flex-col items-center justify-center p-4 font-sans antialiased">
    <div class="bg-white p-10 md:p-14 rounded-3xl shadow-xl max-w-lg w-full text-center border-t-8 border-orange-500">
        <div class="flex justify-center mb-8">
            <!-- 警告を示すアンバー（オレンジ寄りの黄色）のアイコン -->
            <div class="w-24 h-24 bg-orange-50 rounded-full flex items-center justify-center text-orange-500 shadow-inner">
                <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3Z"/><path d="M12 9v4"/><path d="M12 17h.01"/></svg>
            </div>
        </div>
        
        <h2 class="text-2xl md:text-3xl font-bold text-gray-800 mb-4 tracking-tight">システムエラー</h2>
        <p class="text-gray-600 mb-10 leading-relaxed text-sm md:text-base">
            申し訳ありません。予期せぬエラーが発生しました。<br>
            お手数ですが、管理者又は担当者へお問い合わせください。
        </p>
        
        <%-- <p class="text-xs text-red-500 mb-6 bg-red-50 p-2 rounded break-all text-left"><c:out value="${exception.message}" /></p> --%>
        
        <!-- 冷静な対処を促す青色のボタン -->
        <a href="login.jsp" class="inline-flex items-center justify-center gap-2 w-full py-4 bg-blue-600 text-white rounded-xl font-bold hover:bg-blue-700 active:scale-95 transition-all shadow-md">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
            ログイン画面へ戻る
        </a>
    </div>
</body>
</html>