package servlet;

import java.io.IOException;
import java.sql.Connection;

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
 * Servlet implementation class UserInsertServlet
 */
@WebServlet("/UserRegisterServlet")
public class UserInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/JSP/admin/admin_user_register.jsp").forward(request, response);	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String name = request.getParameter("name");
	    String password = request.getParameter("password");
	    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
	    
	    int role = Integer.parseInt(request.getParameter("role"));
	    int status = Integer.parseInt(request.getParameter("status"));
	    
	    User user = new User();
	    user.setName(name);
	    user.setPassword(hashedPassword);
	    user.setRole(role);
	    user.setStatus(status);
	    
	    Connection conn = DBManager.getConnection();
	    UserDAO dao = new UserDAO(conn);
	    
	    if(dao.insert(user)) {
	    	request.getRequestDispatcher("/WEB-INF/JSP/admin/admin_user_list.jsp").forward(request, response); //いったんテストで別画面に飛ぶようにしてる
	    }else { 
	    	request.getRequestDispatcher("/WEB-INF/JSP/common/error.jsp").forward(request, response);
	    }

	}

}
