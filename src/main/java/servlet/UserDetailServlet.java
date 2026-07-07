package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import dao.DBManager;
import dao.UserDAO;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserDetailServlet
 */
@WebServlet("/UserUpdateServlet")
public class UserDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("userId"));
		
		
		try(Connection conn = DBManager.getConnection()){
			UserDAO dao = new UserDAO(conn);
			
			request.setAttribute("user", dao.findById(id));
			
			request.getRequestDispatcher("/WEB-INF/JSP/admin/admin_user_detail.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("userId"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		int role = Integer.parseInt(request.getParameter("role"));
		int status = Integer.parseInt(request.getParameter("status"));
		
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setRole(role);
		user.setStatus(status);
		
		try(Connection conn = DBManager.getConnection()){
			UserDAO dao = new UserDAO(conn);
			boolean success;
			
			if(password == null) {
				success = dao.update(user);
			}else {
			    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
				user.setPassword(hashedPassword);
				success = dao.updateWithPassword(user);
			}
			
			if(success) {
				request.getRequestDispatcher("/WEB-INF/JSP/admin/admin_user_list.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("/WEB-INF/JSP/common/error.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
