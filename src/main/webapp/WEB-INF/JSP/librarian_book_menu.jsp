<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>図書管理システム - 蔵書管理メニュー</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://unpkg.com/react@18/umd/react.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/react-dom@18/umd/react-dom.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body class="bg-gray-50">
    <div id="root"></div>
    <script type="text/babel">
        const { ChevronLeft, PlusCircle, Settings, User } = lucide;

        function LibrarianBookMenu() {
            const handleBack = () => {
                // 司書メインポータル画面へ戻る（本来はポータルのJSPを指定）
                window.history.back();
            };

            return (
                <div className="min-h-screen flex flex-col">
                    {/* ヘッダー */}
                    <header className="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center justify-between sticky top-0 z-20">
                        <div className="flex items-center gap-3">
                            <button onClick={handleBack} className="p-1 hover:bg-white/20 rounded-full transition-colors">
                                <ChevronLeft size={24} />
                            </button>
                            <h1 className="text-xl font-bold tracking-wider">蔵書管理</h1>
                        </div>
                        <div className="flex items-center gap-2 bg-white/10 px-3 py-1 rounded-full text-sm">
                            <User size={16}/> 司書:山田太郎様
                        </div>
                    </header>

                    {/* メインメニュー */}
                    <main className="flex-1 p-6 max-w-3xl mx-auto w-full flex flex-col justify-center gap-6">
                        <button 
                            onClick={() => window.location.href = 'librarian_book_register.jsp'}
                            className="w-full bg-white p-8 rounded-xl shadow-sm hover:shadow-md border border-gray-200 flex items-center justify-between group hover:border-[#1e3a8a] transition-all"
                        >
                            <div className="flex items-center gap-4">
                                <div className="bg-blue-100 p-4 rounded-full text-blue-700 group-hover:bg-[#1e3a8a] group-hover:text-white transition-colors">
                                    <PlusCircle size={32} />
                                </div>
                                <div className="text-left">
                                    <h2 className="text-xl font-bold text-gray-800">蔵書登録</h2>
                                    <p className="text-gray-500 text-sm mt-1">手動入力または一括CSV読み込みによる新規登録</p>
                                </div>
                            </div>
                            <ChevronLeft size={24} className="text-gray-400 rotate-180 group-hover:text-[#1e3a8a] transition-colors" />
                        </button>

                        <button 
                            onClick={() => window.location.href = 'librarian_book_list.jsp'}
                            className="w-full bg-white p-8 rounded-xl shadow-sm hover:shadow-md border border-gray-200 flex items-center justify-between group hover:border-[#1e3a8a] transition-all"
                        >
                            <div className="flex items-center gap-4">
                                <div className="bg-blue-100 p-4 rounded-full text-blue-700 group-hover:bg-[#1e3a8a] group-hover:text-white transition-colors">
                                    <Settings size={32} />
                                </div>
                                <div className="text-left">
                                    <h2 className="text-xl font-bold text-gray-800">更新・検索・状態変更</h2>
                                    <p className="text-gray-500 text-sm mt-1">登録済み蔵書情報の検索、編集、および削除</p>
                                </div>
                            </div>
                            <ChevronLeft size={24} className="text-gray-400 rotate-180 group-hover:text-[#1e3a8a] transition-colors" />
                        </button>

                        <div className="text-center mt-4">
                            <button onClick={handleBack} className="text-gray-500 font-bold hover:text-gray-800 underline">
                                戻る
                            </button>
                        </div>
                    </main>
                </div>
            );
        }

        const root = ReactDOM.createRoot(document.getElementById('root'));
        root.render(<LibrarianBookMenu />);
    </script>
</body>
</html>