<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>図書管理システム - メニュー</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://unpkg.com/react@18/umd/react.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/react-dom@18/umd/react-dom.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body class="bg-gray-50">
    <div id="root"></div>
    <script type="text/babel">
        const { Search, BookOpen, LogOut, User } = lucide;

        function UserHomeApp() {
            const handleLogout = () => {
                if(confirm('ログアウトしますか？')) {
                    window.location.href = 'login.jsp';
                }
            };

            return (
                <div className="min-h-screen flex flex-col">
                    <header className="bg-[#1e5641] text-white p-4 shadow-md flex items-center justify-between sticky top-0 z-10">
                        <h1 className="text-xl font-bold tracking-wider">図書管理システム</h1>
                        <div className="flex items-center gap-4">
                            <span className="text-sm hidden sm:flex items-center gap-1">
                                <User size={16}/> 利用者様
                            </span>
                            <button onClick={handleLogout} className="text-sm hover:text-gray-300 transition-colors flex items-center gap-1">
                                <LogOut size={16} /> ログアウト
                            </button>
                        </div>
                    </header>
                    
                    <main className="flex-1 flex flex-col items-center justify-center p-6 gap-6">
                        <button 
                            onClick={() => window.location.href = 'book_search.jsp'}
                            className="w-full max-w-sm bg-white border border-gray-200 hover:border-[#1e5641] hover:shadow-lg transition-all p-8 rounded-2xl flex flex-col items-center justify-center gap-4 text-[#1e5641] group"
                        >
                            <div className="bg-teal-50 p-4 rounded-full group-hover:bg-[#1e5641] group-hover:text-white transition-colors">
                                <Search size={40} />
                            </div>
                            <span className="text-xl font-bold tracking-wider">本を探す</span>
                        </button>

                        <button 
                            onClick={() => window.location.href = 'loan_status.jsp'}
                            className="w-full max-w-sm bg-white border border-gray-200 hover:border-[#1e5641] hover:shadow-lg transition-all p-8 rounded-2xl flex flex-col items-center justify-center gap-4 text-[#1e5641] group"
                        >
                            <div className="bg-teal-50 p-4 rounded-full group-hover:bg-[#1e5641] group-hover:text-white transition-colors">
                                <BookOpen size={40} />
                            </div>
                            <span className="text-xl font-bold tracking-wider">貸出状況</span>
                        </button>
                    </main>
                </div>
            );
        }

        const root = ReactDOM.createRoot(document.getElementById('root'));
        root.render(<UserHomeApp />);
    </script>
</body>
</html>