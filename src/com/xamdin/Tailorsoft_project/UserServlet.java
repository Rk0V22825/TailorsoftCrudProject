package com.xadmin.tailorsoftproject.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xadmin.tailorsoftproject.bean.User;
import com.xadmin.tailorsoftproject.dao.UserDao;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

  
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		userDao = new UserDao();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch(action)
		{
		case "/new":
			showNewForm(request, response);
			break;
		
	case "/insert":
			try {
				insertUser(request, response);
			} catch (SQLException | IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;
		
	case "/delete":
		deleteUser(request, response);
		break;
		
	case "/edit":
			try {
				showEditForm(request, response);
			} catch (SQLException | ServletException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		break;
		
	case "/update":
			try {
				updateUser(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		break;
		
		default:
			try {
				listUser(request, response);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		}
	}
		
		private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
			dispatcher.forward(request, response);
			}
		//insert user
		private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
		{
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String country = request.getParameter("country");
			User newUser = new User(name, email, country);
			
			userDao.insertUser(newUser);
			response.sendRedirect("list");
		}
	
	// delete user
		private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
				int id = Integer.parseInt(request.getParameter("id"));
				try {
					userDao.deleteUser(id);
				} catch(Exception e) {
					e.printStackTrace();
				}
				response.sendRedirect("list");
            }
		
		//edit user
		private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
			int id = Integer.parseInt(request.getParameter("id"));
			User existingUser = userDao.selectUser(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
			request.setAttribute("user", existingUser);
			dispatcher.forward(request, response);

		}
		
// update user
		private void updateUser(HttpServletRequest request, HttpServletResponse response) 
				throws SQLException, IOException {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String country = request.getParameter("country");

			User book = new User(id, name, email, country);
			userDao.updateUser(book);
			response.sendRedirect("list");
	
		}
		
		// default 
		private void listUser(HttpServletRequest request, HttpServletResponse response)throws SQLException, IOException, ServletException 
		{
			try
			{ 
			List<User> listUser = userDao.selectAllUsers();
			request.setAttribute("listUser", listUser);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
			dispatcher.forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	
    }
}

