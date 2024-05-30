<%@ page contentType="text/html; charset=utf-8"  %>
<html>
<head>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
	<title>userMainPage</title>
	<%	
		if(session.getAttribute("sessionId") == null) //세션 아이디가 없으면 로그인 안됐다는 뜻
			response.sendRedirect("./welcome.jsp");
		
		String name = (String)session.getAttribute("sessionId");
	%>
</head>
<body>
	<div class="container py-4">
		<%@ include file = "./menu.jsp" %>
		<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="diplay-5 fw-bold"><%=name %>님 메인 페이지</h1>
				<p class="col-md-8 fs-4"><%=name %>'s Main Page</p>
			</div>
		</div>
		
		<%@ include file = "../footer.jsp" %>
	</div>
</body>
</html>