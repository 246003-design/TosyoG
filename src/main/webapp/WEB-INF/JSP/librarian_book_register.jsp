<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>図書管理システム - 蔵書登録</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://unpkg.com/react@18/umd/react.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/react-dom@18/umd/react-dom.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body class="bg-gray-50">
    <div id="root"></div>
    <script type="text/babel">
        const { ChevronLeft, Upload, CheckCircle2, AlertCircle } = lucide;

        function LibrarianBookRegister() {
            const [isbn, setIsbn] = React.useState('');
            const [title, setTitle] = React.useState('');
            const [author, setAuthor] = React.useState('');
            const [publisher, setPublisher] = React.useState('');
            const [category, setCategory] = React.useState('文芸');

            const [showConfirmModal, setShowConfirmModal] = React.useState(false);
            const [notification, setNotification] = React.useState('');
            const [isFileLoaded, setIsFileLoaded] = React.useState(false);

            // ISBNから他項目を補完するデモ機能
            const handleAutoFill = () => {
                if (isbn) {
                    setTitle('Webシステム設計論');
                    setAuthor('司書次郎');
                    setPublisher('情報出版');
                    setCategory('技術');
                    setNotification('ISBNより図書情報を自動補完しました。');
                    setTimeout(() => setNotification(''), 3000);
                } else {
                    alert('ISBNを入力してください。');
                }
            };

            // 仮想ファイルアップロード
            const handleFileUpload = (e) => {
                setIsFileLoaded(true);
                setNotification('一括登録ファイルを読み込みました。');
                setTimeout(() => setNotification(''), 3000);
                // サンプルデータを補完
                setIsbn('978-4-00-000000-0');
                setTitle('Webシステム設計論');
                setAuthor('司書次郎');
                setPublisher('情報出版');
                setCategory('技術');
            };

            const handleSubmitCheck = (e) => {
                e.preventDefault();
                if (!title) {
                    alert('タイトルを入力してください。');
                    return;
                }
                setShowConfirmModal(true);
            };

            const handleConfirmRegister = () => {
                setShowConfirmModal(false);
                setNotification('登録が完了しました。');
                // 入力リセット
                setIsbn('');
                setTitle('');
                setAuthor('');
                setPublisher('');
                setIsFileLoaded(false);
                setTimeout(() => {
                    setNotification('');
                    window.location.href = 'librarian_book_menu.jsp';
                }, 2000);
            };

            return (
                <div className="min-h-screen flex flex-col">
                    <header className="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-20">
                        <button onClick={() => window.location.href='librarian_book_menu.jsp'} className="p-1 hover:bg-white/20 rounded-full transition-colors">
                            <ChevronLeft size={24} />
                        </button>
                        <h1 className="text-xl font-bold tracking-wider">蔵書登録(手動入力・一括)</h1>
                    </header>

                    <main className="flex-1 p-4 md:p-8 max-w-4xl mx-auto w-full relative">
                        {notification && (
                            <div className="fixed bottom-6 left-1/2 transform -translate-x-1/2 bg-gray-800 text-white px-6 py-3 rounded-full shadow-2xl z-50 flex items-center gap-2">
                                <CheckCircle2 size={20} className="text-green-400" />
                                <span className="font-bold">{notification}</span>
                            </div>
                        )}

                        <div className="bg-white p-6 md:p-8 rounded-xl shadow-sm border border-gray-300">
                            {/* ファイル一括アップロード */}
                            <div className="mb-8 p-6 border-2 border-dashed border-gray-300 rounded-xl bg-gray-50 text-center hover:bg-gray-100 transition-colors relative">
                                <Upload size={32} className="mx-auto text-gray-400 mb-2" />
                                <p className="text-gray-600 font-bold">ファイルをアップロード (CSV/Excel)</p>
                                <p className="text-sm text-gray-400 mt-1 mb-3">一括登録データをアップロードできます</p>
                                <input 
                                    type="file" 
                                    accept=".csv,.xlsx" 
                                    onChange={handleFileUpload}
                                    className="absolute inset-0 opacity-0 cursor-pointer"
                                />
                                {isFileLoaded && (
                                    <div className="mt-2 text-green-600 font-bold text-sm inline-flex items-center gap-1">
                                        <CheckCircle2 size={16} /> 一括登録ファイルを読み込み済み
                                    </div>
                                )}
                            </div>

                            <div className="flex items-center gap-4 mb-8">
                                <hr className="flex-1 border-gray-300" />
                                <span className="text-gray-400 font-bold text-xs uppercase tracking-wider">手動入力</span>
                                <hr className="flex-1 border-gray-300" />
                            </div>

                            {/* 入力フォーム */}
                            <form onSubmit={handleSubmitCheck} className="space-y-6">
                                <div>
                                    <label className="block text-sm font-bold text-gray-700 mb-1">ISBN</label>
                                    <div className="flex gap-2">
                                        <input 
                                            type="text" 
                                            placeholder="ISBNを入力（他項目を自動補完）" 
                                            className="flex-1 p-3 border border-gray-300 rounded focus:ring-2 focus:ring-[#1e3a8a] outline-none font-mono"
                                            value={isbn}
                                            onChange={(e) => setIsbn(e.target.value)}
                                        />
                                        <button 
                                            type="button"
                                            onClick={handleAutoFill}
                                            className="bg-gray-200 text-gray-700 px-4 rounded font-bold hover:bg-gray-300 transition-colors whitespace-nowrap text-sm border border-gray-300"
                                        >
                                            補完実行
                                        </button>
                                    </div>
                                    <p className="text-xs text-gray-400 mt-1">※ISBNを入力して「補完実行」を押すとダミー情報が入ります。</p>
                                </div>

                                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                    <div>
                                        <label className="block text-sm font-bold text-gray-700 mb-1">タイトル</label>
                                        <input 
                                            type="text" 
                                            className="w-full p-3 border border-gray-300 rounded focus:ring-2 focus:ring-[#1e3a8a] outline-none"
                                            value={title}
                                            onChange={(e) => setTitle(e.target.value)}
                                            required
                                        />
                                    </div>
                                    <div>
                                        <label className="block text-sm font-bold text-gray-700 mb-1">著者</label>
                                        <input 
                                            type="text" 
                                            className="w-full p-3 border border-gray-300 rounded focus:ring-2 focus:ring-[#1e3a8a] outline-none"
                                            value={author}
                                            onChange={(e) => setAuthor(e.target.value)}
                                        />
                                    </div>
                                    <div>
                                        <label className="block text-sm font-bold text-gray-700 mb-1">出版社</label>
                                        <input 
                                            type="text" 
                                            className="w-full p-3 border border-gray-300 rounded focus:ring-2 focus:ring-[#1e3a8a] outline-none"
                                            value={publisher}
                                            onChange={(e) => setPublisher(e.target.value)}
                                        />
                                    </div>
                                    <div>
                                        <label className="block text-sm font-bold text-gray-700 mb-1">分類</label>
                                        <select 
                                            className="w-full p-3 border border-gray-300 rounded focus:ring-2 focus:ring-[#1e3a8a] outline-none bg-white"
                                            value={category}
                                            onChange={(e) => setCategory(e.target.value)}
                                        >
                                            <option>文芸</option>
                                            <option>技術</option>
                                            <option>実用</option>
                                            <option>その他</option>
                                        </select>
                                    </div>
                                </div>

                                <div className="flex gap-4 mt-8 pt-6 border-t border-gray-100">
                                    <button 
                                        type="button"
                                        onClick={() => window.location.href = 'librarian_book_menu.jsp'}
                                        className="flex-1 py-4 border-2 border-gray-300 text-gray-700 rounded font-bold hover:bg-gray-50"
                                    >
                                        戻る
                                    </button>
                                    <button 
                                        type="submit"
                                        className="flex-[2] py-4 bg-[#1e3a8a] text-white rounded font-bold hover:bg-blue-800 shadow-md transition-colors"
                                    >
                                        登録する
                                    </button>
                                </div>
                            </form>
                        </div>

                        {/* 登録確認モーダル */}
                        {showConfirmModal && (
                            <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50 p-4">
                                <div className="bg-white rounded-xl shadow-2xl p-8 max-w-sm w-full text-center border-t-8 border-[#1e3a8a]">
                                    <div className="mx-auto w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mb-6 text-[#1e3a8a]">
                                        <AlertCircle size={32} />
                                    </div>
                                    <h3 className="text-xl font-bold text-gray-900 mb-6">登録してよろしいですか？</h3>
                                    <div className="flex gap-4">
                                        <button 
                                            onClick={() => setShowConfirmModal(false)}
                                            className="flex-1 py-3 border border-gray-300 text-gray-700 rounded font-bold hover:bg-gray-50"
                                        >
                                            いいえ
                                        </button>
                                        <button 
                                            onClick={handleConfirmRegister}
                                            className="flex-1 py-3 bg-[#1e3a8a] text-white rounded font-bold hover:bg-blue-800 shadow-md"
                                        >
                                            はい
                                        </button>
                                    </div>
                                </div>
                            </div>
                        )}
                    </main>
                </div>
            );
        }

        const root = ReactDOM.createRoot(document.getElementById('root'));
        root.render(<LibrarianBookRegister />);
    </script>
</body>
</html>