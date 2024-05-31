<%@ page contentType="text/html; charset=utf-8"%>

<%
	String name = (String) session.getAttribute("sessionId");
	String home = request.getContextPath();
	String css = "/resources/css/bootstrap.min.css";
%>
<html>
<head>
<link href=<%=home + css%> rel="stylesheet">
<title>Notice Board</title>
</head>
<script type="text/javascript">
	function checkForm() {
		if (!document.newWrite.name.value) {
			alert("성명을 입력하세요.");
			return false;
		}
		if (!document.newWrite.subject.value) {
			alert("제목을 입력하세요.");
			return false;
		}
		if (!document.newWrite.content.value) {
			alert("내용을 입력하세요.");
			return false;
		}		
	}
</script>
<%
	String adminWrite = "/NoticeWriteAction.do";
	String route = request.getContextPath();
	String adminhome = "/adminPage/adminMain.jsp";
	//System.out.println(route + adminWrite);
%>
<body>
<div class="container py-4">
	<jsp:include page="adminmenu.jsp" />
	 <div class="p-5 mb-4 bg-body-tertiary rounded-3">
      <div class="container-fluid py-5">
      <%if(name.equals("admin")) {%>
        <h1 class="display-5 fw-bold">공지글 작성</h1>
        <p class="col-md-8 fs-4">Notice</p>      
      <% } else {%>
        <h1 class="display-5 fw-bold">게시글 작성</h1>
        <p class="col-md-8 fs-4">Board</p>      
      <% } %>
      </div>
    </div>

	<div class="row align-items-md-stretch text-center">	 	

		<form name="newWrite" action=<%=route + adminWrite%>  method="post" onsubmit="return checkForm()">
			<input name="id" type="hidden" class="form-control"
				value=<%=name %>>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >UserID</label>
				<div class="col-sm-3">
					<input name="name" type="text" class="form-control" value=<%=name %> placeholder="name" disabled>
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >지역</label>
				<div class="col-sm-8">
					<select id="add1" name="add1" size = 1>
						<option value = "서울특별시">서울특별시</option>
						<option value = "경기도">경기도</option>
						<option value = "경상도">경상도</option>
					</select>
					<select id="add2" name="add2" size = 1>
						<option value = "도봉구">도봉구</option>
						<option value = "노원구">노원구</option>
						<option value = "동대문구">동대문구</option>
					</select>
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >제목</label>
				<div class="col-sm-5">
					<input name="subject" type="text" class="form-control"	placeholder="subject">
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >내용</label>
				<div class="col-sm-8">
					<textarea name="content" cols="50" rows="5" class="form-control"placeholder="content"></textarea>
				</div>
			</div>
			<div class="mb-3 row">
				<div class="col-sm-offset-2 col-sm-10 ">
				 <a href =<%=route + adminhome %> class = "btn btn-primary" role="button">이전</a>
				 <input type="submit" class="btn btn-primary " value="등록 ">				
				 <input type="reset" class="btn btn-primary " value="초기화 ">
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="../footer.jsp" />
	</div>
</body>
</html>



