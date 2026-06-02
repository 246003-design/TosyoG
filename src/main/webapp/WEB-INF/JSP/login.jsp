<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>図書管理システム - ログイン</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://unpkg.com/react@18/umd/react.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/react-dom@18/umd/react-dom.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body>
    <div id="root"></div>
    <script type="text/babel">
        const { Lock, AlertCircle } = lucide;

        function LoginApp() {
            const [userId, setUserId] = React.useState('');
            const [password, setPassword] = React.useState('');
            const [loginError, setLoginError] = React.useState(false);

            const handleLogin = (e) => {
                e.preventDefault();
                // モック用ロジック: 入力値によって遷移先JSPを変更
                if (userId === 'error') {
                    window.location.href = 'error.jsp';
                } else if (userId === 'deny') {
                    window.location.href = 'access_denied.jsp';
                } else if (userId && password) {
                    // 本来はサーバーでロールを判定して各ホームへ遷移
                    alert('ログイン成功！各ホーム画面(JSP)へ遷移します。');
                    // window.location.href = 'home.jsp'; 
                } else {
                    setLoginError(true);
                }
            };

            return (
                <div className="min-h-screen relative flex items-center justify-center bg-slate-900">
                    <div className="absolute inset-0 bg-[url('https://images.unsplash.com/photo-1481627834876-b7833e8f5570?auto=format&fit=crop&q=80')] bg-cover bg-center opacity-30 mix-blend-overlay"></div>
                    <div className="absolute inset-0 bg-gradient-to-t from-slate-900 to-transparent"></div>
                    
                    <div className="bg-white/95 backdrop-blur-sm p-8 md:p-12 rounded-2xl shadow-2xl w-11/12 max-w-md relative z-10 border-t-8 border-slate-700">
                        <div className="flex justify-center mb-6">
                            <div className="bg-slate-700 p-4 rounded-full text-white shadow-lg">
                                <Lock size={32} />
                            </div>
                        </div>
                        <h2 className="text-2xl font-bold text-slate-800 text-center mb-8 tracking-widest">図書管理システム</h2>
                        
                        {loginError && (
                            <div className="mb-4 text-red-600 text-sm flex items-center gap-2 bg-red-50 p-3 rounded-lg border border-red-200">
                                <AlertCircle size={16} />
                                <span>IDまたはパスワードが誤っています</span>
                            </div>
                        )}

                        <form onSubmit={handleLogin} className="space-y-6">
                            <div>
                                <label className="block text-sm font-semibold text-slate-700 mb-2">ユーザーID</label>
                                <input 
                                    type="text" 
                                    placeholder="IDを入力" 
                                    className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-slate-700"
                                    value={userId}
                                    onChange={(e) => setUserId(e.target.value)}
                                />
                            </div>
                            <div>
                                <label className="block text-sm font-semibold text-slate-700 mb-2">パスワード</label>
                                <input 
                                    type="password" 
                                    placeholder="パスワードを入力" 
                                    className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-slate-700"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                            </div>
                            <button type="submit" className="w-full bg-slate-800 text-white py-3.5 rounded-lg font-bold hover:bg-slate-900 shadow-md transition-colors mt-4">
                                {loginError ? '再ログイン' : 'ログイン'}
                            </button>
                        </form>
                        
                        <div className="mt-6 text-xs text-gray-500 text-center space-y-1">
                            <p>※テスト: ID <code>error</code> でエラー画面へ遷移。</p>
                            <p>※テスト: ID <code>deny</code> で権限無し画面へ遷移。</p>
                        </div>
                    </div>
                </div>
            );
        }

        const root = ReactDOM.createRoot(document.getElementById('root'));
        root.render(<LoginApp />);
    </script>
</body>
</html>