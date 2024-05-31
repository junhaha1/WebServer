<%@ page contentType="text/html; charset=utf-8"  %>
<html>
<head>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
	<script type="text/javascript" src="./resources/js/validation_reg.js"></script>
	<title>회원가입</title>
</head>
<body>
<div class="container py-4">
		<%@ include file = "menu.jsp" %>
		
		<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="diplay-5 fw-bold">회원가입</h1>
				<p class="col-md-8 fs-4">Member Register</p>
			</div>
		</div>
		<%
			String error = request.getParameter("error");
			if(error!=null){
				out.println("<div class='alert alert-danger'>");
				out.println("회원 정보를 다시 확인해주십시오.");
				out.println("</div>");
			}
		%>
		<form class="form-signin" action="processRegister.jsp" name= "Member" method="post">
			<div>
				ID<input type="text" class="form-control" id="id" name="id" required autofocus>
			</div>
			<div>
				Password<input type="password" class="form-control" id="passwd" name="passwd">
			</div>
			<div>
				name<input type="text" class="form-control" id="name" name="name">
			</div>
			<div>
				Email<input type="text" class="form-control" id="email" name="email">
			</div>
			<div>
				MBTI<input type="text" class="form-control" id="mbti" name="mbti">
			</div>
			<div class = "mb-3 row">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="button" class = "btn btn-primary" value="회원가입 등록" onclick="CheckMember()">
				</div>
			</div>
		</form>
		<%@ include file = "footer.jsp" %>
	</div>
</body>
</html>