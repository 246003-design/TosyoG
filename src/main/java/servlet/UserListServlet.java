package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DBManager;
import dao.UserDAO;
import dto.UserListDto;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserListServlet
 */
@WebServlet("/userList")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("searchQuery");
		
		try(Connection conn = DBManager.getConnection()){
			UserDAO dao = new UserDAO(conn);
			List<UserListDto> dtoList = new ArrayList<UserListDto>();
			List<User> userList = dao.searchUsers(keyword);
			
			for(User user : userList) {
				UserListDto dto = new UserListDto();
				dto.setId(user.getId());
				dto.setName(user.getName());
				dto.setRole(user.getRole());
				dto.setStatus(user.getStatus());
				
				dtoList.add(dto);
			}
			request.setAttribute("userList", dtoList);
			request.getRequestDispatcher("/WEB-INF/JSP/admin/admin_user_list.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
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
