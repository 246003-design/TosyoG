<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>アクセス拒否</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://unpkg.com/react@18/umd/react.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/react-dom@18/umd/react-dom.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body>
    <div id="root"></div>
    <script type="text/babel">
        const { Lock } = lucide;

        function AccessDeniedApp() {
            const handleReturn = () => {
                window.location.href = 'login.jsp';
            };

            return (
                <div className="min-h-screen bg-red-900 flex flex-col items-center justify-center p-4">
                    <div className="text-white text-center flex flex-col items-center">
                        <Lock size={80} className="mb-6 opacity-90" />
                        <h2 className="text-5xl font-bold mb-4 tracking-wider">Access Denied</h2>
                        <p className="text-red-200 text-lg mb-12">
                            指定された画面へのアクセス権限がありません
                        </p>
                        <button 
                            onClick={handleReturn}
                            className="px-10 py-3 border-2 border-white/50 text-white rounded-full font-bold hover:bg-white/10 hover:border-white transition-all shadow-lg"
                        >
                            トップへ戻る
                        </button>
                    </div>
                </div>
            );
        }

        const root = ReactDOM.createRoot(document.getElementById('root'));
        root.render(<AccessDeniedApp />);
    </script>
</body>
</html>