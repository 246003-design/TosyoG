<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 利用者登録完了</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">
    
    <!-- ヘッダー -->
    <header class="bg-gray-900 text-white p-4 shadow-md flex items-center gap-4 sticky top-0 z-20">
        <a href="admin_user_menu.jsp" class="p-2 hover:bg-white/20 rounded-full transition-colors group">
            <svg class="transform group-hover:-translate-x-1 transition-transform" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest">新規利用者登録</h1>
    </header>

    <main class="flex-1 p-4 md:p-8 max-w-3xl mx-auto w-full flex items-center justify-center">
        <div class="bg-white p-8 md:p-14 rounded-3xl shadow-lg border border-gray-100 text-center w-full relative overflow-hidden">
            
            <!-- 背景の装飾的アクセント -->
            <div class="absolute -top-24 -right-24 w-48 h-48 bg-green-50 rounded-full blur-3xl opacity-60"></div>
            <div class="absolute -bottom-24 -left-24 w-48 h-48 bg-blue-50 rounded-full blur-3xl opacity-60"></div>

            <div class="relative z-10">
                <!-- 成功を示す大きめのアイコン -->
                <div class="flex justify-center mb-6">
                    <div class="w-24 h-24 bg-green-100 rounded-full flex items-center justify-center text-green-600 shadow-inner">
                        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><path d="m9 11 3 3L22 4"/></svg>
                    </div>
                </div>

                <h2 class="text-2xl md:text-3xl font-bold text-gray-900 mb-4">アカウントの登録が完了しました</h2>
                <p class="text-gray-600 mb-8">
                    <%-- <c:out value="${registeredName}" /> --%>広島 情報 様のアカウントをシステムに登録しました。
                </p>

                <!-- 発行されたIDを強調表示するエリア -->
                <div class="bg-gray-50 border-2 border-gray-200 rounded-2xl p-8 mb-8 inline-block min-w-[280px] shadow-sm">
                    <p class="text-sm font-bold text-gray-500 tracking-widest uppercase mb-2">発行されたユーザーID</p>
                    <div class="text-4xl md:text-5xl font-mono font-black text-gray-900 tracking-wider">
                        ID：<%-- <c:out value="${registeredId}" /> --%>246246
                    </div>
                </div>

                <!-- 注意書き -->
                <div class="bg-blue-50/50 border border-blue-100 p-4 rounded-xl flex items-start gap-3 max-w-lg mx-auto mb-10 text-left">
                    <svg class="text-blue-500 shrink-0 mt-0.5" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><line x1="12" y1="16" x2="12" y2="12"/><line x1="12" y1="8" x2="12.01" y2="8"/></svg>
                    <p class="text-sm text-blue-800 leading-relaxed font-medium">
                        このIDはログインや貸出処理で必要になります。必ず対象者へお伝えください。
                    </p>
                </div>

                <!-- アクションボタン -->
                <div class="flex flex-col sm:flex-row justify-center gap-4">
                    <a href="admin_user_menu.jsp" class="px-8 py-4 border-2 border-gray-200 text-gray-600 rounded-xl font-bold hover:bg-gray-50 hover:text-gray-900 transition-colors w-full sm:w-auto">
                        メニューへ戻る
                    </a>
                    <a href="admin_user_register.jsp" class="px-8 py-4 bg-gray-900 text-white rounded-xl font-bold hover:bg-black shadow-lg shadow-gray-900/20 active:scale-95 transition-all flex items-center justify-center gap-2 w-full sm:w-auto">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
                        続けて登録する
                    </a>
                </div>
            </div>
        </div>
    </main>
</body>
</html>