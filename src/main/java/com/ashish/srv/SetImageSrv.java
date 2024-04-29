package com.ashish.srv;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

import com.ashish.dao.UserDao;

/**
 * Servlet implementation class SetImageSrv
 */
@WebServlet("/SetImageServlet")
@MultipartConfig(maxFileSize = 16177215)
public class SetImageSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetImageSrv() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String status = "Setting image failed";

		HttpSession session = request.getSession();
		String emailId = (String) session.getAttribute("username");
		Part part = request.getPart("image");
		InputStream inputStream = part.getInputStream();

		InputStream userImage = inputStream;

		UserDao dao = new UserDao();
		status = dao.SetImage(userImage, emailId);

		RequestDispatcher rd = request.getRequestDispatcher("userProfile.jsp?message=" + status);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
