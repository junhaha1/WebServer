<%@ page contentType="text/html; charset=utf-8"  %>
<html>
<%
	String style = "/resources/css/bootstrap.min.css";
	session.removeAttribute("sessionId");
	session.setAttribute("sessionId", (String)request.getAttribute("id"));
%>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath() + style%>" />
	<title>Success</title>
</head>
<body>
	<div class="container py-4">
		<%@ include file = "../menu.jsp" %>
		<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 style="color:blue" class="diplay-5 fw-bold"><strong>유저 정보 업데이트 성공</strong></h1>
				<p style="color:blue" class="col-md-8 fs-4"><strong>Success!</strong></p>
			</div>
		</div>
		<a href =<%=route + home %> class = "btn btn-primary" role="button">메인으로</a>
		<%@ include file = "../footer.jsp" %>
	</div>
</body>
</html>