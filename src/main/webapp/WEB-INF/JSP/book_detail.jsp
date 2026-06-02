<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>図書詳細</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://unpkg.com/react@18/umd/react.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/react-dom@18/umd/react-dom.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body class="bg-gray-50">
    <div id="root"></div>
    <script type="text/babel">
        const { ChevronLeft, BookOpen, CheckCircle2 } = lucide;

        function BookDetailApp() {
            // モック状態: 最初は未予約、ボタンを押すと予約済みに変わる
            const [isReserved, setIsReserved] = React.useState(false);
            const [showReserveModal, setShowReserveModal] = React.useState(false);
            const [notification, setNotification] = React.useState('');

            const handleReserve = () => {
                setIsReserved(true);
                setShowReserveModal(false);
                setNotification('予約が完了しました。');
                setTimeout(() => setNotification(''), 3000);
            };

            return (
                <div className="min-h-screen flex flex-col">
                    <header className="bg-[#1e5641] text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-10">
                        <button onClick={() => window.history.back()} className="p-1 hover:bg-white/20 rounded-full transition-colors">
                            <ChevronLeft size={24} />
                        </button>
                        <h1 className="text-xl font-bold tracking-wider">図書詳細</h1>
                    </header>
                    
                    <main className="flex-1 p-4 md:p-8 max-w-4xl mx-auto w-full relative">
                        {notification && (
                            <div className="absolute top-4 left-1/2 transform -translate-x-1/2 bg-gray-800 text-white px-6 py-3 rounded-full shadow-xl z-20 flex items-center gap-2">
                                <CheckCircle2 size={20} className="text-green-400" />
                                <span className="font-bold">{notification}</span>
                            </div>
                        )}

                        <div className="bg-white rounded-2xl shadow-sm border border-gray-200 overflow-hidden">
                            <div className="md:flex">
                                {/* 画像エリア */}
                                <div className="md:w-1/3 h-80 md:h-auto bg-gray-100 p-6 flex items-center justify-center border-r border-gray-100">
                                    <div className="w-full h-full max-w-[200px] shadow-lg rounded-md overflow-hidden bg-gradient-to-br from-teal-700 to-slate-900 flex flex-col justify-between p-4 text-white relative">
                                        <div className="absolute top-0 right-0 w-32 h-32 bg-white/10 rounded-full -mr-16 -mt-16"></div>
                                        <h3 className="font-bold text-lg leading-tight mb-2 drop-shadow-md z-10">Web UIデザイン論</h3>
                                        <div className="text-[12rem] font-black leading-none transform skew-x-12 absolute inset-0 flex items-center justify-center opacity-20">N</div>
                                    </div>
                                </div>
                                
                                {/* 詳細情報エリア */}
                                <div className="md:w-2/3 p-6 md:p-10 flex flex-col justify-between">
                                    <div>
                                        {isReserved && (
                                            <div className="inline-flex items-center gap-2 bg-yellow-100 text-yellow-800 px-3 py-1.5 rounded-full text-sm font-bold mb-4 border border-yellow-200">
                                                <CheckCircle2 size={16} /> 現在予約済みです
                                            </div>
                                        )}
                                        <h2 className="text-3xl font-bold text-gray-900 mb-2">Web UIデザイン論</h2>
                                        <p className="text-lg text-gray-600 mb-6 border-b pb-4">著者: 田中三郎</p>
                                        
                                        <div className="mb-8">
                                            <h4 className="text-sm font-bold text-[#1e5641] mb-2 uppercase tracking-wider">あらすじ</h4>
                                            <p className="text-gray-700 leading-relaxed text-sm md:text-base">
                                                現代のWeb開発において不可欠なUIデザインの基礎から応用までを網羅したベストセラー。
                                                図書管理の現場で求められる実践的なスキルを体系的に学ぶことができます。初学者からベテランまで幅広い層に向けた内容となっています。
                                            </p>
                                        </div>
                                    </div>

                                    <div className="flex flex-col sm:flex-row gap-4 mt-8 pt-6 border-t border-gray-100">
                                        <button 
                                            onClick={() => window.history.back()}
                                            className="flex-1 py-3.5 border-2 border-gray-300 text-gray-700 rounded-xl font-bold hover:bg-gray-50 transition-colors"
                                        >
                                            一覧に戻る
                                        </button>
                                        
                                        {isReserved ? (
                                            <button 
                                                onClick={() => setIsReserved(false)}
                                                className="flex-1 py-3.5 bg-white border-2 border-red-600 text-red-600 rounded-xl font-bold hover:bg-red-50 transition-colors"
                                            >
                                                予約を取り消す
                                            </button>
                                        ) : (
                                            <button 
                                                onClick={() => setShowReserveModal(true)}
                                                className="flex-1 py-3.5 bg-[#1e5641] text-white rounded-xl font-bold hover:bg-[#154231] shadow-md transition-colors"
                                            >
                                                予約する
                                            </button>
                                        )}
                                    </div>
                                </div>
                            </div>
                        </div>

                        {/* 予約確認モーダル */}
                        {showReserveModal && (
                            <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50 p-4">
                                <div className="bg-white rounded-2xl shadow-2xl p-8 max-w-sm w-full text-center border-t-8 border-[#1e5641]">
                                    <div className="mx-auto w-16 h-16 bg-teal-100 rounded-full flex items-center justify-center mb-6 text-[#1e5641]">
                                        <BookOpen size={32} />
                                    </div>
                                    <h3 className="text-xl font-bold text-gray-900 mb-6">予約してよろしいですか？</h3>
                                    <div className="flex gap-4">
                                        <button 
                                            onClick={() => setShowReserveModal(false)}
                                            className="flex-1 py-3 border border-gray-300 text-gray-700 rounded-xl font-bold hover:bg-gray-50 transition-colors"
                                        >
                                            いいえ
                                        </button>
                                        <button 
                                            onClick={handleReserve}
                                            className="flex-1 py-3 bg-[#1e5641] text-white rounded-xl font-bold hover:bg-[#154231] shadow-md transition-colors"
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
        root.render(<BookDetailApp />);
    </script>
</body>
</html>