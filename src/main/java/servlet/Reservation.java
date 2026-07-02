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
            return;
        }
        int bookId = Integer.parseInt(bookIdStr);

        try (Connection conn = DBManager.getConnection()) {
            BookDAO bookDAO = new BookDAO(conn);
            ReservationDAO reservationDAO = new ReservationDAO(conn);
            
            Optional<Book> bookOpt = bookDAO.findById(bookId);
            
            if (bookOpt.isPresent()) {
                Book book = bookOpt.get();
                
                // 1. 予約状況のチェック
                int bookInfoId = book.getBookInfoId();
                boolean isReserved = false;
                for (Reservation r : reservationDAO.findAll()) {
                    if (r.getUserId() == userId && r.getBookInfoId() == bookInfoId && (r.getStatus() == 1 || r.getStatus() == 2)) {
                        isReserved = true;
                        break;
                    }
                }
                
                // ★2. 【エンティティを変更しない代わりの裏技】
                // JSPが求める「book.isReservedByCurrentUser」という形をMapで偽装する
                java.util.Map<String, Object> bookMap = new java.util.HashMap<>();
                bookMap.put("id", book.getId());
                bookMap.put("title", book.getTitle());
                bookMap.put("author", book.getAuthor());
                bookMap.put("synopsis", book.getSynopsis());
                // ↓ここがポイント！JSPが求めている名前でMapのキーを作る
                bookMap.put("isReservedByCurrentUser", isReserved); 
                
                // 3. スコープには変わらず "book" という名前でMapをセットする
                request.setAttribute("book", bookMap);
                
            } else {
                request.setAttribute("errorMsg", "指定された図書情報が見つかりません。");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "データ取得中にエラーが発生しました。");
        }

        request.getRequestDispatcher("/WEB-INF/JSP/customer/customer_book_detail.jsp").forward(request, response);
    }
