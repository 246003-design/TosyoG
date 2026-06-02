<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>図書検索</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://unpkg.com/react@18/umd/react.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/react-dom@18/umd/react-dom.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body class="bg-gray-50">
    <div id="root"></div>
    <script type="text/babel">
        const { ChevronLeft, Search } = lucide;

        function BookSearchApp() {
            const handleSearch = () => {
                // 検索結果画面のJSPへ遷移する想定
                alert('検索条件を送信し、検索結果画面(book_search_result.jsp)へ遷移します。');
                // window.location.href = 'book_search_result.jsp';
            };

            const handleBack = () => {
                // 前の画面（各ホーム画面）へ戻る
                window.history.back();
            };

            return (
                <div className="min-h-screen flex flex-col">
                    {/* 共通ヘッダー */}
                    <header className="bg-slate-700 text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-10">
                        <button onClick={handleBack} className="p-1 hover:bg-white/20 rounded-full transition-colors">
                            <ChevronLeft size={24} />
                        </button>
                        <h1 className="text-xl font-bold tracking-wider">図書検索</h1>
                    </header>
                    
                    <main className="flex-1 p-4 md:p-8 max-w-4xl mx-auto w-full">
                        <div className="bg-white p-6 md:p-8 rounded-2xl shadow-sm border border-gray-200">
                            <div className="flex items-center gap-2 mb-6 border-b pb-4">
                                <Search className="text-slate-500" size={24} />
                                <h2 className="text-lg font-bold text-slate-800">検索条件を指定してください</h2>
                            </div>

                            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
                                <div>
                                    <label className="block text-sm font-semibold text-slate-700 mb-2">タイトル</label>
                                    <input type="text" placeholder="例: システム設計" className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-slate-500 outline-none" />
                                </div>
                                <div>
                                    <label className="block text-sm font-semibold text-slate-700 mb-2">著者</label>
                                    <input type="text" placeholder="例: 山田太郎" className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-slate-500 outline-none" />
                                </div>
                                <div>
                                    <label className="block text-sm font-semibold text-slate-700 mb-2">ISBN</label>
                                    <input type="text" placeholder="例: 978-4-..." className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-slate-500 outline-none font-mono" />
                                </div>
                                <div>
                                    <label className="block text-sm font-semibold text-slate-700 mb-2">分類</label>
                                    <select className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-slate-500 outline-none bg-white">
                                        <option>すべて</option>
                                        <option>文学</option>
                                        <option>IT・技術</option>
                                        <option>デザイン</option>
                                        <option>業務</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div className="flex justify-end gap-4 border-t pt-6">
                                <button onClick={handleBack} className="px-6 py-3 border border-gray-300 text-gray-700 rounded-lg font-bold hover:bg-gray-50 transition-colors">
                                    戻る
                                </button>
                                <button onClick={handleSearch} className="px-10 py-3 bg-slate-800 text-white rounded-lg font-bold hover:bg-slate-900 shadow-md transition-colors">
                                    検索実行
                                </button>
                            </div>
                        </div>
                    </main>
                </div>
            );
        }

        const root = ReactDOM.createRoot(document.getElementById('root'));
        root.render(<BookSearchApp />);
    </script>
</body>
</html>