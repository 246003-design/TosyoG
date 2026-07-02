/**
     * 【図書詳細＆予約画面表示（GETリクエスト）】
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("loginUserId"); 

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/WEB-INF/JSP/common/login.jsp");
            return;
        }

        String bookIdStr = request.getParameter("bookId");
        if (bookIdStr == null || bookIdStr.isEmpty()) {
            request.setAttribute("errorMsg", "図書IDが指定されていません。");
            request.getRequestDispatcher("/WEB-INF/JSP/common/error.jsp").forward(request, response);
            return;
        }
        int bookId = Integer.parseInt(bookIdStr);

        try (Connection conn = DBManager.getConnection()) {
            BookDAO bookDAO = new BookDAO(conn);
            ReservationDAO reservationDAO = new ReservationDAO(conn);
            
            // ① 図書情報をDBから取得
            Optional<Book> bookOpt = bookDAO.findById(bookId);
            
            if (bookOpt.isPresent()) {
                Book book = bookOpt.get();
                // JSPの ${book.title} などで表示するためにセット
                request.setAttribute("book", book);
                
                // ② ログイン中のユーザーがこの図書をすでに予約しているかチェック
                int bookInfoId = book.getBookInfoId();
                boolean isReserved = false;
                
                // 全予約データから「自分」かつ「この本（bookInfoId）」かつ「有効（1:予約中, 2:準備中）」を探す
                for (Reservation r : reservationDAO.findAll()) {
                    if (r.getUserId() == userId && r.getBookInfoId() == bookInfoId && (r.getStatus() == 1 || r.getStatus() == 2)) {
                        isReserved = true;
                        break;
                    }
                }
                
                // ③ 判定結果（true/false）をリクエストスコープに格納
                request.setAttribute("isReserved", isReserved);
                
            } else {
                request.setAttribute("errorMsg", "指定された図書情報が見つかりません。");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "データ取得中にエラーが発生しました。");
        }

        // 確定したJSPへフォワード
        request.getRequestDispatcher("/WEB-INF/JSP/customer/customer_book_detail.jsp").forward(request, response);
    }
