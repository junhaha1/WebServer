<%@ page contentType="text/html; charset=utf-8"  %>
<html>
<head>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
	<title>Login</title>
</head>
<body>
	<div class="container py-4">
		<%@ include file = "/adminPage/adminmenu.jsp" %>
		
		<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="diplay-5 fw-bold">관리자용 메인 페이지</h1>
				<p class="col-md-8 fs-4">Admin Main Page</p>
			</div>
		</div>
		<a href ="./adminPage/usersearch_admin.jsp" class = "btn btn-secondary" role="button">일반 사용자 조회</a>
		<a href ="./adminPage/boardmanage_admin.jsp" class = "btn btn-secondary" role="button">게시판 목록</a>
		<a href ="./adminPage/usermanage_admin.jsp" class = "btn btn-secondary" role="button">일반 사용자 관리</a>
		<%@ include file = "footer.jsp" %>
	</div>
</body>
</html>