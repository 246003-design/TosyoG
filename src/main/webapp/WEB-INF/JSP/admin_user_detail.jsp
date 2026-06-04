<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>図書管理システム - 利用者詳細編集</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 flex flex-col min-h-screen">
    <!-- ヘッダー -->
    <header class="bg-gray-900 text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-20 border-b border-gray-700">
        <a href="UserSearchServlet" class="p-1 hover:bg-gray-700 rounded-full transition-colors text-white">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
        </a>
        <h1 class="text-xl font-bold tracking-wider">
            利用者: <%-- <c:out value="${user.name}"/> --%>情報 太郎 (<%-- <c:out value="${user.id}"/> --%>123456)
        </h1>
    </header>

    <main class="flex-1 p-4 md:p-8 max-w-5xl mx-auto w-full">
        
        <%-- サーブレットからの更新完了通知 --%>
        <%-- <c:if test="${not empty successMessage}"> --%>
        <div class="mb-6 bg-green-50 border border-green-200 text-green-800 px-4 py-3 rounded-lg flex items-center gap-2 font-bold shadow-sm">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22c5.523 0 10-4.477 10-10S17.523 2 12 2 2 6.477 2 12s4.477 10 10 10z"/><path d="m9 12 2 2 4-4"/></svg>
            利用者情報を更新しました。
        </div>
        <%-- </c:if> --%>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
            
            <!-- ①左側: ユーザー情報編集フォーム -->
            <div class="md:col-span-1 bg-white p-6 rounded-xl shadow-sm border border-gray-300 self-start">
                <div class="flex items-center gap-3 mb-6 pb-4 border-b border-gray-200">
                    <div class="w-12 h-12 bg-gray-200 rounded-full flex items-center justify-center text-gray-500">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                    </div>
                    <div>
                        <h3 class="font-bold text-lg text-gray-900">
                            <%-- <c:out value="${user.name}"/> --%> 情報 太郎 様
                        </h3>
                        <span class="text-xs bg-gray-200 px-2 py-0.5 rounded font-bold text-gray-700">
                            <%-- <c:out value="${user.type}"/> --%> 司書
                        </span>
                    </div>
                </div>
                
                <!-- 情報更新用のPOST送信フォーム -->
                <form action="UserUpdateServlet" method="POST" class="space-y-4">
                    <!-- IDの隠しデータ -->
                    <input type="hidden" name="userId" value="${user.id}" />

                    <div>
                        <label class="block text-xs font-bold text-gray-500 mb-1">所属</label>
                        <input type="text" name="department" value="広島情報本部" class="w-full p-2.5 border border-gray-300 rounded focus:ring-1 focus:ring-gray-900 outline-none text-sm bg-gray-50 focus:bg-white" required />
                    </div>
                    <div>
                        <label class="block text-xs font-bold text-gray-500 mb-1">パスワード</label>
                        <input type="text" name="password" value="password" class="w-full p-2.5 border border-gray-300 rounded focus:ring-1 focus:ring-gray-900 outline-none font-mono text-sm bg-gray-50 focus:bg-white" required />
                    </div>
                    <div>
                        <label class="block text-xs font-bold text-gray-500 mb-1">状態</label>
                        <select name="status" class="w-full p-2.5 border border-gray-300 rounded outline-none text-sm bg-gray-50 focus:bg-white">
                            <!-- 状態に応じてセレクト状態を制御 -->
                            <option value="利用可能">利用可能</option>
                            <option value="利用停止">利用停止</option>
                            <option value="貸出停止">貸出停止</option>
                        </select>
                    </div>
                    
                    <button type="submit" class="w-full mt-4 py-3 bg-gray-900 text-white rounded font-bold hover:bg-black flex items-center justify-center gap-2 shadow-sm transition-colors" onclick="return confirm('ユーザーの変更内容を保存しますか？');">
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/><polyline points="17 21 17 13 7 13 7 21"/><polyline points="7 3 7 8 15 8"/></svg>
                        保存
                    </button>
                </form>
            </div>

            <!-- ②右側: 現在貸出中の図書リスト -->
            <div class="md:col-span-2 bg-white rounded-xl shadow-sm border border-gray-300 flex flex-col h-full overflow-hidden">
                <div class="p-4 border-b border-gray-200 bg-gray-100 flex items-center gap-2">
                    <svg class="text-gray-600" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"/><path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"/></svg>
                    <h3 class="font-bold text-gray-800">現在貸出中の図書</h3>
                </div>
                <div class="overflow-x-auto flex-1">
                    <table class="w-full text-left border-collapse">
                        <thead class="bg-gray-50 text-gray-700 border-b border-gray-200">
                            <tr>
                                <th class="p-3 font-semibold text-sm">タイトル</th>
                                <th class="p-3 font-semibold text-sm">ISBN</th>
                                <th class="p-3 font-semibold text-sm">著者名</th>
                                <th class="p-3 font-semibold text-sm">出版社名</th>
                            </tr>
                        </thead>
                        <tbody class="divide-y divide-gray-100">
                            <%-- <c:forEach var="loan" items="${currentLoanList}"> --%>
                            
                            <!-- 貸出図書のサンプル -->
                            <tr class="hover:bg-gray-50">
                                <td class="p-3 font-bold text-gray-900">技術情報の基礎</td>
                                <td class="p-3 text-gray-500 font-mono text-sm">978-4-00123</td>
                                <td class="p-3 text-gray-700 text-sm">情報 太郎</td>
                                <td class="p-3 text-gray-700 text-sm">技術出版</td>
                            </tr>
                            <tr class="hover:bg-gray-50">
                                <td class="p-3 font-bold text-gray-900">Web UIの実践</td>
                                <td class="p-3 text-gray-500 font-mono text-sm">978-4-98765</td>
                                <td class="p-3 text-gray-700 text-sm">佐藤次郎</td>
                                <td class="p-3 text-gray-700 text-sm">技術書院</td>
                            </tr>
                            
                            <%-- </c:forEach> --%>

                            <%-- もし貸出中の書籍がゼロ件の場合（JSTL制御用） --%>
                            <%-- <c:if test="${empty currentLoanList}"> --%>
                            <!-- <tr><td colSpan="4" class="p-8 text-center text-gray-500">現在、貸出中の図書はありません。</td></tr> -->
                            <%-- </c:if> --%>
                        </tbody>
                    </table>
                </div>
                <div class="p-4 bg-gray-50 border-t border-gray-200 text-right">
                    <a href="UserSearchServlet" class="px-6 py-2 border border-gray-300 rounded font-bold text-gray-700 bg-white hover:bg-gray-100 transition-colors inline-block text-sm">
                        一覧に戻る
                    </a>
                </div>
            </div>

        </div>
    </main>
</body>
</html>