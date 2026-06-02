<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>システムエラー</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://unpkg.com/react@18/umd/react.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/react-dom@18/umd/react-dom.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body class="bg-gray-100">
    <div id="root"></div>
    <script type="text/babel">
        const { AlertTriangle } = lucide;

        function ErrorApp() {
            const handleReturn = () => {
                window.location.href = 'login.jsp';
            };

            return (
                <div className="min-h-screen flex flex-col items-center justify-center p-4">
                    <div className="bg-white p-10 rounded-2xl shadow-xl max-w-md w-full text-center border-t-8 border-slate-700">
                        <div className="mx-auto w-20 h-20 bg-gray-100 rounded-full flex items-center justify-center mb-6 text-slate-600">
                            <AlertTriangle size={40} />
                        </div>
                        <h2 className="text-2xl font-bold text-gray-900 mb-4">システムエラーが発生しました</h2>
                        <p className="text-gray-600 mb-8 leading-relaxed">
                            お手数ですが、管理者又は担当者へお問い合わせください。
                        </p>
                        <button 
                            onClick={handleReturn}
                            className="w-full py-3 bg-slate-800 text-white rounded-lg font-bold hover:bg-slate-900 transition-colors shadow-md"
                        >
                            ログイン画面へ戻る
                        </button>
                    </div>
                </div>
            );
        }

        const root = ReactDOM.createRoot(document.getElementById('root'));
        root.render(<ErrorApp />);
    </script>
</body>
</html>