<%@ page contentType="text/html; charset=utf-8"  %>
<html>
<head>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
	<title>Login</title>
</head>
<body>
	<div class="container py-4">
		<%@ include file = "menu.jsp" %>
		
		<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="diplay-5 fw-bold">로그인</h1>
				<p class="col-md-8 fs-4">Login</p>
			</div>
		</div>
		
		<div class = "row align-items-md-stretch text-center">
			<div class = "row justify-content-center align-items-center">
				<div class = "h-100 p-5 col-md-6">
					<h3>Please sign in</h3>
					<%	
						try{
							int error = Integer.parseInt(request.getParameter("error"));
							if(error == 1){
								out.println("<div class='alert alert-danger'>");
								out.println("아이디와 비밀번호를 다시 확인해 주세요");
								out.println("</div>");
							} else if(error == 2){
								out.println("<div class='alert alert-danger'>");
								out.println("로그인 서버 이상. 다시 시도해주십시오. ");
								out.println("</div>");
							}
						}catch (NumberFormatException ex){
				            ex.printStackTrace();
				        }
					%>
					<form class="form-signin" action="processUserLogin.jsp" method="post">
					<div class = "form-floating mb-3 row">
						<input type="text" class="form-control" name="id" required autofocus>
						<label for = "floatingInput">ID</label>
					</div>
					<div class="form-floating mb-3 row">
						<input type="password" class="form-control" name="passwd">
						<label for = "floatingInput">Password</label>
					</div>
					<a href ="addmember.jsp" class = "btn btn-secondary" role="button">회원가입</a>
					<button class = "btn btn-lg btn-success" type="submit">로그인</button>
					</form>
				</div>
			</div>
		</div>
		<%@ include file = "footer.jsp" %>
	</div>
</body>
</html>