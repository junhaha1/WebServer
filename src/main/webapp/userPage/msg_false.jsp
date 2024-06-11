<%@ page contentType="text/html; charset=utf-8"  %>
<html>
<%
	String style = "/resources/css/bootstrap.min.css";
%>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath() + style%>" />
	<title>False</title>
</head>
<body>
	<div class="container py-4">
		<%@ include file = "../menu.jsp" %>
		<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 style="color:red" class="diplay-5 fw-bold"><strong>메일 전송 실패</strong></h1>
				<p style="color:red" class="col-md-8 fs-4"><strong>False</strong></p>
			</div>
		</div>
		<a href =<%=route + home %> class = "btn btn-primary" role="button">home</a>
		<%@ include file = "../footer.jsp" %>
	</div>
</body>
</html>