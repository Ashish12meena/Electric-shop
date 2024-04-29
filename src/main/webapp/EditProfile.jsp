<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
	<%@ page
	import="com.ashish.dao.*, com.ashish.service.*,com.ashish.model.*,java.util.*,jakarta.servlet.ServletOutputStream,java.io.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	/* Checking the user credentials */
	String message = request.getParameter("message");
	String userName = (String) session.getAttribute("username");
	String password = (String) session.getAttribute("password");

	if (userName == null || password == null) {

		response.sendRedirect("login.jsp?message=Session Expired, Login Again!!");
	}

	UserDao dao = new UserDao();
	UserModel user = dao.getUserDetails(userName, password);
	%>
	<jsp:include page="header.jsp" />
	<div class="container">
		<div class="row"
			style="margin-top: 5px; margin-left: 2px; margin-right: 2px;">

			<form action="./UpdateSrv" method="post" class="col-md-6 col-md-offset-3"
				style="border: 2px solid black; border-radius: 10px; background-color: #FFE5CC; padding: 10px;">
				<div style="font-weight: bold;" class="text-center">
					<h2 style="color: green;">Edit Form</h2>
					<%
					if (message != null) {
					%>
					<p style="color: blue;">
						<%=message%>
					</p>
					<%
					}
					%>
				</div>
				<div></div>
				<div class="row">
					<div class="col-md-6 form-group">
						<label for="first_name">Name</label> <input type="text"
							name="name" class="form-control" id="first_name"
							name="first_name" value="<%=user.getName()%>" required>
					</div>
				</div>
				<div class="form-group">
					<label for="last_name">Address</label>
					<input name="address" class="form-control" id="last_name"
						name="last_name" value="<%=user.getAddress()%>" required></input>
				</div>
				<div class="row">
					<div class="col-md-6 form-group">
						<label for="last_name">Mobile</label> <input type="number"
							name="mobile" class="form-control" id="last_name"
							name="last_name" value="<%=user.getMobile() %>" required>
					</div>
					<div class="col-md-6 form-group">
						<label for="last_name">Pin Code</label> <input type="number"
							name="pincode" class="form-control" id="last_name"
							name="last_name" value="<%=user.getPinCode() %>" required>
					</div>

				</div>
				<div class="row text-center">
					<div class="col-md-6" style="margin-bottom: 2px;">
						<button type="Reset" class="btn btn-danger">Reset</button>
					</div>
					<div class="col-md-6">
						<button type="submit" class="btn btn-success">Submit</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	 <%@ include file="footer.jsp"%>
</body>
</html>