<%@ page contentType="text/html; charset=utf-8" %>
<header class = "pb-3 mb-4 border-bottom">
	<%
		String route = request.getContextPath();
		String sessionId;
		
		String home = "/welcome.jsp";
		String logout = "/logout.jsp";
		String userinfo = null;
		
		if(session.getAttribute("sessionId") == null){
			sessionId = null;
		} else{ //로그인 상태일 경우
			sessionId = (String) session.getAttribute("sessionId");
			home = "/AllBoardListAction.userdo";
			userinfo = "/requestUserInfo.userdo?name="+sessionId;
		}	
		//System.out.println((String)sessionId);
	%>
	<div class="container ">  
	<div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">  
	<a href=<%=route + home %> class="d-flex align-itmes-center text-dark text-decoration-none">
	<svg width="32" height="32" fill="currentColor" class="bi bi-house-fill" viewBox="0 0 16 16">
  		<path d="M8.707 1.5a1 1 0 0 0-1.414 0L.646 8.146a.5.5 0 0 0 .708.708L8 2.207l6.646 6.647a.5.5 0 0 0 .708-.708L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293z"/>
  		<path d="m8 3.293 6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293z"/>
	</svg>
		<span class="fs-4">Home</span>
		</a>
		<div class = "bg-body-tertiary rounded-3">
		<% 
			if(sessionId != null){
		%>
        	<a href =<%=route + logout %> class = "btn btn-secondary" role="button">로그아웃</a></li>
        	<a href =<%=route + userinfo %> class = "btn btn-secondary" role="button">내 정보</a></li>
        <% 
			}
        %>
    	</div>
    	</div>
    	</div>
</header>