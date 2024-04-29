package com.ashish.srv;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import com.ashish.dao.CartDao;
import com.ashish.dao.DemandDao;
import com.ashish.dao.ProductDao;
import com.ashish.model.DemandModel;
import com.ashish.model.ProductModel;

/**
 * Servlet implementation class AddCart
 */
@WebServlet("/AddtoCart")
public class AddCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		String usertype = (String) session.getAttribute("usertype");
		if (userName == null || password == null || usertype == null || !usertype.equalsIgnoreCase("customer")) {
			response.sendRedirect("login.jsp?message=Session Expired, Login Again to Continue!");
			return;
		}

		// login Check Successfull

		String userId = userName;
		String prodId = request.getParameter("pid");
		int pQty = Integer.parseInt(request.getParameter("pqty")); // 1

		CartDao cart = new CartDao();

		ProductDao productDao = new ProductDao();

		ProductModel product = productDao.getProductDetails(prodId);

		int availableQty = product.getProdQuantity();

		int cartQty = cart.getProductCount(userId, prodId);

		pQty += cartQty;

		PrintWriter pw = response.getWriter();

		response.setContentType("text/html");
		if (pQty == cartQty) {
			String status = cart.removeProductFromCart(userId, prodId);

			RequestDispatcher rd = request.getRequestDispatcher("userHome.jsp");

			rd.include(request, response);

			pw.println("<script>document.getElementById('message').innerHTML='" + status + "'</script>");
		} else if (availableQty < pQty) {

			String status = null;

			if (availableQty == 0) {
				status = "Product is Out of Stock!";
			} else {

				cart.updateProductToCart(userId, prodId, availableQty);

				status = "Only " + availableQty + " no of " + product.getProdName()
						+ " are available in the shop! So we are adding only " + availableQty
						+ " products into Your Cart" + "";
			}
			DemandModel demandModel = new DemandModel(userName, product.getProdId(), pQty - availableQty);

			DemandDao demand = new DemandDao();

			boolean flag = demand.addProduct(demandModel);

			if (flag)
				status += "<br/>Later, We Will Mail You when " + product.getProdName()
						+ " will be available into the Store!";

			RequestDispatcher rd = request.getRequestDispatcher("cartDetails.jsp");

			rd.include(request, response);

			pw.println("<script>document.getElementById('message').innerHTML='" + status + "'</script>");

		} else {
			String status = cart.updateProductToCart(userId, prodId, pQty);

			RequestDispatcher rd = request.getRequestDispatcher("userHome.jsp");

			rd.include(request, response);

			pw.println("<script>document.getElementById('message').innerHTML='" + status + "'</script>");
		}

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
