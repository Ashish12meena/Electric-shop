<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="com.ashish.dao.*, com.ashish.service.*,com.ashish.model.*,java.util.*,jakarta.servlet.ServletOutputStream,java.io.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Profile Details</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/changes.css">
<style>
.ff {
	display: none;
}
</style>
</head>
<body style="background-color: #E6F9E6;">

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
	if (user == null)
		user = new UserModel("Test User", 9876543210L, "test@gmail.com", "ABC colony, rajasthan", 87659, "lksdjf");
	%>



	<jsp:include page="header.jsp" />


	<div class="container bg-secondary">
		<div class="row">
			<div class="col">
				<nav aria-label="breadcrumb" class="bg-light rounded-3 p-3 mb-4">
					<ol class="breadcrumb mb-0">
						<li class="breadcrumb-item"><a href="index.jsp">Home</a></li>
						<li class="breadcrumb-item"><a href="profile.jsp">User
								Profile</a></li>
						<li>
							<%
							if (message != null) {
							%>
							<p style="color: red;">
								<%=message%>
							</p> <%
							}
							%>
						</li>
					</ol>
				</nav>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-4">
				<div class="card mb-4">
					<div class="card-body text-center">
						<div class="">
							<div>
								<img src="./UserImageServlet?uid=<%=user.getEmail()%>"
									onclick="func()" alt="Image"
									style="height: 150px; max-width: 150px" class="class-img">
							</div>
							<div class="row">

								<%-- <div class="btn btn-success">
									<a href="<%dao.removeImage(user.getEmail());%>">Remove</a>
								</div> --%>
								<div class="btn btn-success">
									<a href="./RemoveImageSrv">Remove</a>
								</div>
								<div class="btn btn-success" onclick="func()">
									<a href="#">Edit</a>
								</div>
							</div>
						</div>


						<h5 class="my-3">
							Hello
							<%=user.getName()%>
							here!!
						</h5>
						<!-- <p class="text-muted mb-1">Full Stack Developer</p>
						<p class="text-muted mb-4">Bay Area, San Francisco, CA</p> -->
					</div>
					<form action="./SetImageServlet" method="post"
						enctype="multipart/form-data" style="display: none;" id="setImage">
						<div class="">
							<div class="row-form1">
								<input type="file" placeholder="Select Image" name="image"
									id="last_name" required>
							</div>
							<div>
								<button type="submit" class="btn btn-success">Set Image</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="col-lg-8">
				<div class="card mb-4">
					<div class="card-body">
						<div class="row">
							<div class="col-sm-3">
								<p class="mb-0">Full Name</p>
							</div>
							<div class="col-sm-9">
								<p class="text-muted mb-0"><%=user.getName()%></p>
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="col-sm-3">
								<p class="mb-0">Email</p>
							</div>
							<div class="col-sm-9">
								<p class="text-muted mb-0"><%=user.getEmail()%>
								</p>
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="col-sm-3">
								<p class="mb-0">Phone</p>
							</div>
							<div class="col-sm-9">
								<p class="text-muted mb-0"><%=user.getMobile()%>
								</p>
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="col-sm-3">
								<p class="mb-0">Address</p>
							</div>
							<div class="col-sm-9">
								<p class="text-muted mb-0"><%=user.getAddress()%>
								</p>
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="col-sm-3">
								<p class="mb-0">PinCode</p>
							</div>
							<div class="col-sm-9">
								<p class="text-muted mb-0"><%=user.getPinCode()%>
								</p>
							</div>
						</div>
						<div class="row">
							<form action="EditProfile.jsp">
								<div>
									<button type="submit" class="btn btn-success">Edit</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<br>
	<br>
	<br>

	<%@ include file="footer.jsp"%>
	<script type="text/javascript">
		function func() {
			var element = document.getElementById("setImage");
			element.style.display = 'block';
			console.log(element);
		}
	</script>

</body>
</html>