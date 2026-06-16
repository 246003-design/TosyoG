//貸出状況照会

package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import dao.BookInfoDAO;
import dao.DBManager;
import dao.LendDAO;
import entity.Lend;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.RentalStatusModel;


public class RentalStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public RentalStatusServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("loginUser");
		int userId = user.getId();
		
		Connection conn = DBManager.getConnection();
		
		LendDAO lendDAO = new LendDAO(conn);
		BookInfoDAO bookInfoDAO = new BookInfoDAO(conn);
		RentalStatusModel model = new RentalStatusModel();
		
		List<Lend> lendList = lendDAO.findByUserId(userId);
		List<BookInfoDAO> bookInfoList = bookInfoDAO.searchTitle()
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
