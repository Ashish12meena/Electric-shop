package com.ashish.srv;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.ashish.dao.UserDao;
import com.ashish.model.UserModel;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/UpdateSrv")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		String emailId = (String) session.getAttribute("username");
		String name = request.getParameter("name");
		Long mobileNo = Long.parseLong(request.getParameter("mobile"));
		String address = request.getParameter("address");
		int pinCode = Integer.parseInt(request.getParameter("pincode"));
		String status = "updation failed";
		
		UserModel user = new UserModel(name, mobileNo, emailId, address, pinCode);

		UserDao dao = new UserDao();
		status = dao.updateUser(user);
		
		RequestDispatcher rd = request.getRequestDispatcher("userProfile.jsp?message=" + status);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
