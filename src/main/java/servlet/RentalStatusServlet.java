//貸出状況照会

package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import dao.BookInfoDAO;
import dao.DBManager;
import dao.LendDAO;
import dto.LendDetailDto;
import entity.BookInfo;
import entity.Lend;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.RentalStatusModel;

@WebServlet("/rentalStatus")
public class RentalStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public RentalStatusServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			throws ServletException, IOException, java.sql.SQLException {
		User user = (User) request.getSession().getAttribute("loginUser");
		int userId = user.getId();
		
		try(Connection conn = DBManager.getConnection()){
			LendDAO lendDAO = new LendDAO(conn);
			BookInfoDAO bookInfoDAO = new BookInfoDAO(conn);
			RentalStatusModel model = new RentalStatusModel();
			List<LendDetailDto> dtoList = new ArrayList<LendDetailDto>();

			
			List<Lend> lendList = lendDAO.findByUserId(userId);
			
			for(Lend lend : lendList) {
				BookInfo book = bookInfoDAO.searchTitle(lend.getBookId());
				String title;
				
				if(book == null) {
					title = "不明";
				}else {
					title = book.getTitle();
				}
				
				LendDetailDto dto = new LendDetailDto();
				dto.setTitle(title);
				dto.setDueDate(lend.getDueDate());
				dto.setLendDate(lend.getLendDate());
				dto.setOverdue(model.isOverdue(lend.getDueDate()));
				
				dtoList.add(dto);
			}
			request.setAttribute("dtoList", dtoList);
			request.getRequestDispatcher("loan_status.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
