<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ page import="java.time.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
	
	List mbtiboardlist = null; 
	String mbti = null;
	
	if(session.getAttribute("checkMove") != null){ //이전으로 넘어왔을 때
		System.out.println("checkMove");
		SearchLog log = (SearchLog) session.getAttribute("researchLog");
		mbtiboardlist = (List) log.getContent();
		System.out.println("check log mbti: " + log.getText());
		mbti = log.getText();
		session.removeAttribute("researchLog");
		session.removeAttribute("checkMove");
	}else{ //그냥 메인페이지에서 넘어오거나 검색으로 넘어왔을 때
		mbtiboardlist = (List) request.getAttribute("mbtiboardlist"); 
		mbti = (String) request.getAttribute("mbti");
	}
	
	/*log에 등록해두기*/ 
	SearchLog log = new SearchLog();
	
	log.setText(mbti);
	log.setContent(mbtiboardlist);
	log.setType("userMbti_research");
	
	System.out.println("log mbti: " + log.getText());
	
	session.setAttribute("researchLog", log);
%>
<html>
<head>
	<%	
		if(session.getAttribute("sessionId") == null) //세션 아이디가 없으면 로그인 안됐다는 뜻
			response.sendRedirect("../welcome.jsp");
	
		String name = (String)session.getAttribute("sessionId"); 
		
		/*버튼 눌렀을 시에 옮겨갈 페이지*/
		String writeForm = "/board/userWriteForm.jsp";
		String commonShow = "./BoardListAction.userdo";
		String noticeShow = "./NoticeBoardListAciton.userdo";
		
		
		String style = "/resources/css/bootstrap.min.css";
	%>
	<link rel="stylesheet" href=<%=request.getContextPath() + style %> />
	<title>userMainPage</title>
</head>
<script type="text/javascript">
	function searchCheck() {
		if (document.mbtisearch.mbti_ie.value === "" && document.mbtisearch.mbti_sn.value === "" && document.mbtisearch.mbti_ft.value === "" && document.mbtisearch.mbti_pj.value === "") {
			alert("적어도 하나는 선택해주십시오.");
			return false;
		}

	}
	function btnClick() {

	  const mydiv = document.getElementById('mbtilist');
	  if(mydiv.style.display === 'none') {

	    mydiv.style.display = 'block';

	  }else {
	    mydiv.style.display = 'none';
	  }

	}
</script>
<body>
	<div class="container py-4">
		<%@ include file = "../menu.jsp" %>
		<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="diplay-5 fw-bold"><%=name %>님 MBTI '<%=mbti.toUpperCase() %>'의 추천 게시글</h1>
				<p class="col-md-8 fs-4"><%=name %>'s MBTI '<%=mbti.toUpperCase() %>' recommand Page</p>
			</div>
		</div>
		<a href ="<%=request.getContextPath()%>/AllBoardListAction.userdo" class = "btn btn-secondary" role="button">메인으로</a>
		<button onclick='btnClick()'class = "btn btn-secondary" >다양한 mbti별 겸색</button>
		<form name="mbtisearch" action= "<%=request.getContextPath() %>/searchMbtiBoard.userdo" method="post" onsubmit="return searchCheck()">	
			<div id = "mbtilist"  style="display:none" align="left">
				<input type = "radio" name = "mbti_ie" value="" checked="checked">선택 안함			
				<input type = "radio" name = "mbti_ie" value="i">I
				<input type = "radio" name = "mbti_ie" value="e">E<br>
				<input type = "radio" name = "mbti_sn" value="" checked="checked">선택 안함	
				<input type = "radio" name = "mbti_sn" value="s">S
				<input type = "radio" name = "mbti_sn" value="n">N<br>
				<input type = "radio" name = "mbti_ft" value="" checked="checked">선택 안함	
				<input type = "radio" name = "mbti_ft" value="f">F
				<input type = "radio" name = "mbti_ft" value="t">T<br>
				<input type = "radio" name = "mbti_pj" value="" checked="checked">선택 안함	
				<input type = "radio" name = "mbti_pj" value="p">P
				<input type = "radio" name = "mbti_pj" value="j">J<br>
				<input type="submit" id="btnAdd" class="btn btn-primary " value="해당 mbti 추천 " />				
			</div>
		</form>			
		<div class="row align-items-md-stretch   text-center">	 	
		<form name="search" action= "<%=request.getContextPath() %>/SearchListAction.userdo" method="post">	
			<div style="padding-top: 20px">
				<table class="table table-hover text-center">
					<tr>
						<th>추천 순위</th>
						<th>번호</th>
						<th>제목</th>
						<th>작성일</th>
						<th>조회</th>
					</tr>
					<%
					if(mbtiboardlist != null){
						for (int j = 0; j < mbtiboardlist.size() ; j++){
							
							Board board = (Board) mbtiboardlist.get(j);
							String date = board.getRegdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					%>
					<tr>
						<td><%= j + 1 %></td>
						<td><%=board.getBID()%></td>
						<td><a href="<%=request.getContextPath()%>/userBoardView.userdo?BID=<%=board.getBID()%>&&type=user_mbtisearch"><%=board.getTitle()%></td>
						<td><%=date%></td>
						<td><%=board.getHit()%></td>
					</tr>
					<%
						}
					}
					%>
				</table>
			</div>
		</form>			
	</div>
		
		<%@ include file = "../footer.jsp" %>
	</div>
</body>
</html>