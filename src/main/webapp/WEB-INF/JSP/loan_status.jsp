<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>貸出状況照会</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://unpkg.com/react@18/umd/react.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/react-dom@18/umd/react-dom.production.min.js" crossorigin></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body class="bg-gray-50">
    <div id="root"></div>
    <script type="text/babel">
        const { ChevronLeft } = lucide;

        const MOCK_LOANS = [
            { id: 1, title: 'Web UIデザイン論', startDate: '2024/05/10', dueDate: '2024/05/24', status: '超過' },
            { id: 2, title: '技術情報の活用', startDate: '2024/06/01', dueDate: '2024/06/15', status: '貸出中' },
        ];

        function LoanStatusApp() {
            return (
                <div className="min-h-screen flex flex-col">
                    <header className="bg-[#1e5641] text-white p-4 shadow-md flex items-center gap-3 sticky top-0 z-10">
                        <button onClick={() => window.location.href = 'user_home.jsp'} className="p-1 hover:bg-white/20 rounded-full transition-colors">
                            <ChevronLeft size={24} />
                        </button>
                        <h1 className="text-xl font-bold tracking-wider">貸出状況照会</h1>
                    </header>
                    
                    <main className="flex-1 p-4 md:p-8 max-w-5xl mx-auto w-full">
                        <div className="bg-white rounded-2xl shadow-sm border border-gray-200 overflow-hidden">
                            <div className="overflow-x-auto">
                                <table className="w-full text-left border-collapse">
                                    <thead>
                                        <tr className="bg-teal-50 border-b border-teal-100 text-[#1e5641]">
                                            <th className="p-4 font-bold">タイトル</th>
                                            <th className="p-4 font-bold">貸出開始日</th>
                                            <th className="p-4 font-bold">返却期限日</th>
                                            <th className="p-4 font-bold text-center">状態</th>
                                        </tr>
                                    </thead>
                                    <tbody className="divide-y divide-gray-100">
                                        {MOCK_LOANS.map(loan => {
                                            const isOverdue = loan.status === '超過';
                                            return (
                                                <tr key={loan.id} className="hover:bg-gray-50 transition-colors">
                                                    <td className="p-4 font-semibold text-gray-900">{loan.title}</td>
                                                    <td className="p-4 text-gray-600">{loan.startDate}</td>
                                                    <td className={`p-4 font-medium ${isOverdue ? 'text-red-600' : 'text-gray-600'}`}>
                                                        {loan.dueDate}
                                                    </td>
                                                    <td className="p-4 text-center">
                                                        <span className={`px-3 py-1 rounded-full text-xs font-bold ${
                                                            isOverdue ? 'bg-red-100 text-red-700 border border-red-200' : 'bg-green-100 text-green-700 border border-green-200'
                                                        }`}>
                                                            {loan.status}
                                                        </span>
                                                    </td>
                                                </tr>
                                            );
                                        })}
                                    </tbody>
                                </table>
                            </div>
                            
                            <div className="p-6 bg-gray-50 border-t flex justify-center">
                                <button 
                                    onClick={() => window.location.href = 'user_home.jsp'}
                                    className="px-8 py-3 bg-white border-2 border-[#1e5641] text-[#1e5641] rounded-lg font-bold hover:bg-[#1e5641] hover:text-white transition-colors"
                                >
                                    メニューへ
                                </button>
                            </div>
                        </div>
                    </main>
                </div>
            );
        }

        const root = ReactDOM.createRoot(document.getElementById('root'));
        root.render(<LoanStatusApp />);
    </script>
</body>
</html>