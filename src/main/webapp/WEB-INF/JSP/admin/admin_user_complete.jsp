<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 利用者登録完了</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 flex flex-col min-h-screen font-sans antialiased text-gray-800">
    
    <header class="bg-gray-900 text-white p-4 shadow-md flex items-center gap-4 sticky top-0 z-20">
        <a href="admin_user_menu.jsp" class="p-2 hover:bg-white/20 rounded-full transition-colors group">
            <svg class="transform group-hover:-translate-x-1 transition-transform" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-widest">新規利用者登録</h1>
    </header>

    <main class="flex-1 p-4 md:p-8 max-w-3xl mx-auto w-full flex items-center justify-center">
        <div class="bg-white p-8 md:p-12 rounded-3xl shadow-lg border border-gray-100 w-full relative overflow-hidden">
            
            <div class="absolute -top-24 -right-24 w-48 h-48 bg-green-50 rounded-full blur-3xl opacity-60 pointer-events-none"></div>
            <div class="absolute -bottom-24 -left-24 w-48 h-48 bg-blue-50 rounded-full blur-3xl opacity-60 pointer-events-none"></div>

            <div class="relative z-10">
                <div class="text-center mb-8">
                    <div class="inline-flex items-center justify-center w-20 h-20 bg-green-100 rounded-full text-green-600 shadow-inner mb-4">
                        <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><path d="m9 11 3 3L22 4"/></svg>
                    </div>
                    <h2 class="text-2xl md:text-3xl font-bold text-gray-900 mb-2">アカウント登録完了</h2>
                    <p class="text-gray-500 font-medium">新しいアカウントをシステムに登録しました。</p>
                </div>

                <div class="bg-gray-50 border border-gray-200 rounded-2xl p-6 md:p-8 mb-8 shadow-sm">
                    <h3 class="text-sm font-bold text-gray-400 uppercase tracking-widest mb-6 border-b border-gray-200 pb-2">登録内容詳細</h3>
                    
                    <dl class="grid grid-cols-1 md:grid-cols-2 gap-x-6 gap-y-6">
                        <div class="md:col-span-2 bg-white border-2 border-blue-100 rounded-xl p-5 flex flex-col sm:flex-row sm:items-center justify-between gap-2 shadow-sm">
                            <dt class="text-sm font-bold text-blue-800 uppercase tracking-wider">発行されたユーザーID</dt>
                            <dd class="text-4xl font-mono font-black text-gray-900 tracking-widest">
                                ${registeredUser.id}
                            </dd>
                        </div>

                        <div class="md:col-span-2">
                            <dt class="text-xs font-bold text-gray-500 mb-1 uppercase tracking-wider">氏名</dt>
                            <dd class="font-bold text-gray-900 text-lg">${registeredUser.name}</dd>
                        </div>
                        <div>
                            <dt class="text-xs font-bold text-gray-500 mb-1 uppercase tracking-wider">権限区分</dt>
                            <dd>
                                <span class="bg-gray-200 text-gray-700 px-3 py-1 rounded-md text-sm font-bold">
                                    <c:choose>
                                        <c:when test="${registeredUser.role == 2}">管理者</c:when>
                                        <c:when test="${registeredUser.role == 1}">司書</c:when>
                                        <c:otherwise>利用者</c:otherwise>
                                    </c:choose>
                                </span>
                            </dd>
                        </div>
                        <div>
                            <dt class="text-xs font-bold text-gray-500 mb-1 uppercase tracking-wider">初期状態</dt>
                            <dd>
                                <c:choose>
                                    <c:when test="${registeredUser.status == 1}">
                                        <span class="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-sm font-bold bg-red-100 text-red-700">
                                            <span class="w-2 h-2 rounded-full bg-red-600"></span>利用停止
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-sm font-bold bg-green-100 text-green-700">
                                            <span class="w-2 h-2 rounded-full bg-green-600"></span>利用可能
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </dd>
                        </div>
                    </dl>
                </div>

                <div class="bg-blue-50/50 border border-blue-100 p-4 rounded-xl flex items-start gap-3 mb-8 text-left">
                    <svg class="text-blue-500 shrink-0 mt-0.5" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><line x1="12" y1="16" x2="12" y2="12"/><line x1="12" y1="8" x2="12.01" y2="8"/></svg>
                    <p class="text-sm text-blue-800 leading-relaxed font-medium">
                        対象者へ<span class="font-bold">「ユーザーID」</span>を必ずお伝えください。初回ログイン時に必要となります。
                    </p>
                </div>

                <div class="flex flex-col sm:flex-row justify-center gap-4">
                    <a href="HomeServlet" class="px-8 py-4 border-2 border-gray-200 text-gray-600 rounded-xl font-bold hover:bg-gray-50 hover:text-gray-900 transition-colors w-full sm:w-auto text-center">
                        メニューへ戻る
                    </a>
                    <a href="UserInsertServlet" class="px-8 py-4 bg-gray-900 text-white rounded-xl font-bold hover:bg-black shadow-lg shadow-gray-900/20 active:scale-95 transition-all flex items-center justify-center gap-2 w-full sm:w-auto">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
                        続けて登録する
                    </a>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
