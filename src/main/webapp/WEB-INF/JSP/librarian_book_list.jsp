<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>図書管理システム - 蔵書管理一覧・更新</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://unpkg.com/react@18/umd/react.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/react-dom@18/umd/react-dom.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body class="bg-gray-50">
    <div id="root"></div>
    <script type="text/babel">
        const { ChevronLeft, Search, AlertTriangle, CheckCircle2 } = lucide;

        // モックデータ (貸出可、貸出中の書籍が含まれます)
        const INITIAL_BOOKS = [
            { id: 1, title: 'Webシステム設計論', author: '司書次郎', isbn: '978-4-00-000001-0', publisher: '情報出版', category: '技術', status: '貸出可' },
            { id: 2, title: 'システム設計の基礎', author: '情報 太郎', isbn: '978-4-001234567', publisher: '技術出版', category: '技術', status: '貸出中' },
            { id: 3, title: 'Web UIデザインの実践', author: '佐藤次郎', isbn: '978-4-00-000003-4', publisher: '技術出版', category: 'デザイン', status: '貸出中' },
            { id: 4, title: '図書館情報学概論', author: '山田花子', isbn: '978-4-00-000004-1', publisher: '文系出版', category: '文芸', status: '貸出可' },
        ];

        function LibrarianBookList() {
            const [books, setBooks] = React.useState(INITIAL_BOOKS);
            const [searchQuery, setSearchQuery] = React.useState('');
            const [selectedBook, setSelectedBook] = React.useState(null); // 編集中の本
            const [isEditMode, setIsEditMode] = React.useState(false);

            // ダイアログ・通知状態
            const [showDeleteModal, setShowDeleteModal] = React.useState(false);
            const [notification, setNotification] = React.useState('');

            // 検索フィルタリング
            const filteredBooks = books.filter(book => 
                book.title.includes(searchQuery) || 
                book.isbn.includes(searchQuery) ||
                book.author.includes(searchQuery)
            );

            // 編集画面を開く
            const handleEdit = (book) => {
                setSelectedBook(book);
                setIsEditMode(true);
            };

            // 更新処理
            const handleUpdateSubmit = (e) => {
                e.preventDefault();
                // 本来はサーバー側のDB更新処理を呼び出す
                setBooks(books.map(b => b.id === selectedBook.id ? selectedBook : b));
                setIsEditMode(false);
                setNotification('更新が完了しました。');
                setTimeout(() => setNotification(''), 3000);
            };

            // 削除処理の確認
            const handleDeleteCheck = () => {
                setShowDeleteModal(true);
            };

            // 削除確定
            const handleDeleteConfirm = () => {
                setBooks(books.filter(b => b.id !== selectedBook.id));
                setShowDeleteModal(false);
                setIsEditMode(false);
                setNotification('図書の削除が完了しました。');
                setTimeout(() => setNotification(''), 3000);
            };

            return (
                <div className="min-h-screen flex flex-col">
                    <header className="bg-[#1e3a8a] text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-20">
                        <button 
                            onClick={() => isEditMode ? setIsEditMode(false) : window.location.href='librarian_book_menu.jsp'} 
                            className="p-1 hover:bg-white/20 rounded-full transition-colors"
                        >
                            <ChevronLeft size={24} />
                        </button>
                        <h1 className="text-xl font-bold tracking-wider">
                            {isEditMode ? '蔵書情報更新' : '蔵書管理:検索結果一覧'}
                        </h1>
                    </header>

                    <main className="flex-1 p-4 md:p-8 max-w-6xl mx-auto w-full relative">
                        {notification && (
                            <div className="fixed bottom-6 left-1/2 transform -translate-x-1/2 bg-gray-800 text-white px-6 py-3 rounded-full shadow-2xl z-50 flex items-center gap-2">
                                <CheckCircle2 size={20} className="text-green-400" />
                                <span className="font-bold">{notification}</span>
                            </div>
                        )}

                        {!isEditMode ? (
                            /* 一覧・検索画面 */
                            <div className="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden flex flex-col">
                                <div className="p-4 border-b border-gray-200 bg-gray-50 flex justify-between items-center">
                                    <div className="relative w-full max-w-md">
                                        <Search className="absolute left-3 top-3 text-gray-400" size={20} />
                                        <input 
                                            type="text" 
                                            placeholder="キーワード検索 (タイトル, ISBN...)" 
                                            className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#1e3a8a]" 
                                            value={searchQuery}
                                            onChange={(e) => setSearchQuery(e.target.value)}
                                        />
                                    </div>
                                    <span className="text-gray-500 font-bold ml-4 whitespace-nowrap">全 {filteredBooks.length} 件</span>
                                </div>

                                <div className="overflow-x-auto">
                                    <table className="w-full text-left border-collapse min-w-max">
                                        <thead className="bg-[#1e3a8a] text-white sticky top-0">
                                            <tr>
                                                <th className="p-3 font-semibold text-sm w-16 text-center">画像</th>
                                                <th className="p-3 font-semibold text-sm">タイトル</th>
                                                <th className="p-3 font-semibold text-sm">著者</th>
                                                <th className="p-3 font-semibold text-sm">ISBN</th>
                                                <th className="p-3 font-semibold text-sm text-center">状態</th>
                                                <th className="p-3 font-semibold text-sm text-center">操作</th>
                                            </tr>
                                        </thead>
                                        <tbody className="divide-y divide-gray-200">
                                            {filteredBooks.map(book => (
                                                <tr key={book.id} className="hover:bg-blue-50 transition-colors bg-white">
                                                    <td className="p-3 text-center">
                                                        <div className="w-10 h-14 bg-gray-200 mx-auto rounded border border-gray-300 flex items-center justify-center text-[10px] text-gray-400">表紙</div>
                                                    </td>
                                                    <td className="p-3 font-bold text-gray-800">{book.title}</td>
                                                    <td className="p-3 text-gray-600 text-sm">{book.author}</td>
                                                    <td className="p-3 text-gray-500 font-mono text-sm">{book.isbn}</td>
                                                    <td className="p-3 text-center">
                                                        <span className={`px-2 py-1 rounded text-xs font-bold ${
                                                            book.status === '貸出可' ? 'bg-green-100 text-green-700' : 'bg-yellow-100 text-yellow-800'
                                                        }`}>
                                                            {book.status}
                                                        </span>
                                                    </td>
                                                    <td className="p-3 text-center">
                                                        <button 
                                                            onClick={() => handleEdit(book)}
                                                            className="px-4 py-1.5 border border-[#1e3a8a] text-[#1e3a8a] rounded hover:bg-[#1e3a8a] hover:text-white transition-colors text-sm font-bold"
                                                        >
                                                            編集
                                                        </button>
                                                    </td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        ) : (
                            /* 情報更新・削除画面 (個別書籍の詳細) */
                            <div className="bg-white p-8 rounded-xl shadow-sm border border-gray-300">
                                {selectedBook.status === '貸出中' && (
                                    <div className="mb-6 bg-yellow-50 border-l-4 border-yellow-500 p-4 rounded text-yellow-800 font-bold flex items-center gap-2">
                                        <AlertTriangle size={20} className="shrink-0" />
                                        <span>貸出中のため実行できません（情報の更新・登録取消は「貸出可」の書籍のみ行えます）</span>
                                    </div>
                                )}

                                <div className="md:flex gap-8">
                                    {/* カバー画像プレースホルダー */}
                                    <div className="md:w-1/3 mb-6 md:mb-0 flex flex-col items-center justify-center bg-gray-100 border border-gray-200 rounded p-4 h-64 text-gray-400 font-mono">
                                        <span>600 x 400</span>
                                        <span className="text-sm mt-2">Cover Image</span>
                                    </div>

                                    {/* 編集フォーム */}
                                    <form onSubmit={handleUpdateSubmit} className="md:w-2/3 space-y-4">
                                        <div>
                                            <label className="block text-xs font-bold text-gray-500 mb-1 uppercase">タイトル</label>
                                            <input 
                                                type="text" 
                                                value={selectedBook.title} 
                                                onChange={(e) => setSelectedBook({...selectedBook, title: e.target.value})}
                                                disabled={selectedBook.status === '貸出中'} 
                                                className="w-full p-2.5 border border-gray-300 rounded font-bold text-lg disabled:bg-gray-100 disabled:text-gray-500 outline-none focus:ring-2 focus:ring-[#1e3a8a]" 
                                            />
                                        </div>
                                        <div className="grid grid-cols-2 gap-4">
                                            <div>
                                                <label className="block text-xs font-bold text-gray-500 mb-1 uppercase">ISBN</label>
                                                <input 
                                                    type="text" 
                                                    value={selectedBook.isbn} 
                                                    disabled 
                                                    className="w-full p-2.5 border border-gray-200 rounded bg-gray-100 text-gray-500 font-mono outline-none" 
                                                />
                                            </div>
                                            <div>
                                                <label className="block text-xs font-bold text-gray-500 mb-1 uppercase">分類</label>
                                                <select 
                                                    value={selectedBook.category} 
                                                    onChange={(e) => setSelectedBook({...selectedBook, category: e.target.value})}
                                                    disabled={selectedBook.status === '貸出中'} 
                                                    className="w-full p-2.5 border border-gray-300 rounded disabled:bg-gray-100 disabled:text-gray-500 outline-none focus:ring-2"
                                                >
                                                    <option>技術</option>
                                                    <option>デザイン</option>
                                                    <option>文芸</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div className="grid grid-cols-2 gap-4">
                                            <div>
                                                <label className="block text-xs font-bold text-gray-500 mb-1 uppercase">著者</label>
                                                <input 
                                                    type="text" 
                                                    value={selectedBook.author} 
                                                    onChange={(e) => setSelectedBook({...selectedBook, author: e.target.value})}
                                                    disabled={selectedBook.status === '貸出中'} 
                                                    className="w-full p-2.5 border border-gray-300 rounded disabled:bg-gray-100 disabled:text-gray-500 outline-none focus:ring-2" 
                                                />
                                            </div>
                                            <div>
                                                <label className="block text-xs font-bold text-gray-500 mb-1 uppercase">出版社</label>
                                                <input 
                                                    type="text" 
                                                    value={selectedBook.publisher} 
                                                    onChange={(e) => setSelectedBook({...selectedBook, publisher: e.target.value})}
                                                    disabled={selectedBook.status === '貸出中'} 
                                                    className="w-full p-2.5 border border-gray-300 rounded disabled:bg-gray-100 disabled:text-gray-500 outline-none" 
                                                />
                                            </div>
                                        </div>
                                        
                                        <div className="pt-6 mt-6 border-t border-gray-200 flex flex-col md:flex-row justify-between items-center gap-4">
                                            <button 
                                                type="button"
                                                onClick={handleDeleteCheck}
                                                disabled={selectedBook.status === '貸出中'}
                                                className="w-full md:w-auto text-red-600 font-bold hover:underline disabled:text-gray-400 disabled:no-underline px-4 py-2"
                                            >
                                                登録取消 (削除)
                                            </button>
                                            <div className="flex w-full md:w-auto gap-3">
                                                <button 
                                                    type="button" 
                                                    onClick={() => setIsEditMode(false)} 
                                                    className="flex-1 md:flex-none px-6 py-3 border border-gray-300 rounded font-bold text-gray-700 hover:bg-gray-50"
                                                >
                                                    戻る
                                                </button>
                                                <button 
                                                    type="submit"
                                                    disabled={selectedBook.status === '貸出中'} 
                                                    className="flex-1 md:flex-none px-8 py-3 bg-[#1e3a8a] text-white rounded font-bold hover:bg-blue-800 disabled:bg-gray-400"
                                                >
                                                    {selectedBook.status === '貸出中' ? '更新不可' : '更新内容を確認'}
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        )}

                        {/* 削除確認モーダル */}
                        {showDeleteModal && (
                            <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50 p-4">
                                <div className="bg-white rounded-xl shadow-2xl p-8 max-w-md w-full text-center border-t-8 border-red-600">
                                    <div className="mx-auto w-16 h-16 bg-red-100 rounded-full flex items-center justify-center mb-6 text-red-600">
                                        <AlertTriangle size={32} />
                                    </div>
                                    <h3 className="text-xl font-bold text-gray-900 mb-2">本当に削除してよろしいですか？</h3>
                                    <p className="text-sm text-gray-500 mb-6">この操作は取り消せません。<br/>タイトル: <span className="font-bold text-gray-800">{selectedBook.title}</span></p>
                                    <div className="flex gap-4">
                                        <button 
                                            onClick={() => setShowDeleteModal(false)}
                                            className="flex-1 py-3 border border-gray-300 text-gray-700 rounded font-bold hover:bg-gray-50"
                                        >
                                            いいえ
                                        </button>
                                        <button 
                                            onClick={handleDeleteConfirm}
                                            className="flex-1 py-3 bg-red-600 text-white rounded font-bold hover:bg-red-700 shadow-md"
                                        >
                                            はい(削除)
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
        root.render(<LibrarianBookList />);
    </script>
</body>
</html>