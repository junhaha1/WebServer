<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ page import="java.time.format.*" %>
<%
	String name = (String) session.getAttribute("sessionId");
	String route = request.getContextPath();
	String style = "/resources/css/bootstrap.min.css";
	String userWrite = "/BoardWriteAction.userdo";
	String home = "/AllBoardListAction.userdo";
	
	String menu = "../menu.jsp";
	if(name.equals("admin"))
		menu = "../adminPage/adminmenu.jsp";
	//System.out.println(menu);

	Board board = (Board) request.getAttribute("board");
	List comentlist = (List) request.getAttribute("comentlist");
	String date = board.getRegdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	String BID = (String) request.getAttribute("BID");
	int checkgood = ((Integer)request.getAttribute("checkgood")).intValue(); //사용자가 좋아요를 누른 상태인지 아닌지 확인
	
	
	String paddress = "none";
	String pname = "none";
	Double lat = 33.450701;
	Double lng = 126.570667;
	
	if(!board.getPname().isEmpty() && board.getLatclick() != null && board.getLngclick() != null){
		paddress = board.getPaddress();
		pname = board.getPname();
		lat = Double.parseDouble(board.getLatclick());
		lng = Double.parseDouble(board.getLngclick());
	}
	
	
	String imgroot = request.getContextPath() + "/resources/images";
	
	String title = "게시판";
	if(board.getId().equals("admin"))
		title = "공지글";
	
	String type = "";
	if(request.getParameter("type") != null){
		type = request.getParameter("type");
		System.out.println("boardview test: " + type);
		if(type.equals("common"))
			home = "/BoardListAction.userdo?type=" + type;
		else if(type.equals("notice"))
			home = "/NoticeBoardListAciton.userdo?type=" + type;
		else if(type.equals("coment"))
			home = "/comentBoardListAction.userdo?type=" + type +"&&name=" + name;
		else if(type.equals("admin"))
			home = "/BoardListAction.do";
		else if(type.equals("admin_notice"))
			home = "/NoticeListAction.do";
		else if(type.equals("admin_search")){
			List loglist = (List)session.getAttribute("researchLogList");
			SearchLog log = (SearchLog) loglist.get(0);
			home = "/SearchListAction.do?pageNum="+log.getPageNum()+"&items="+log.getItems()+"&text=" +log.getText()+"&type="+log.getType();
			System.out.println("이전 체크: " + home);
		}else if(type.equals("user_search")){
			List loglist = (List)session.getAttribute("researchLogList");
			SearchLog log = (SearchLog) loglist.get(0);
			home = "/SearchListAction.userdo?pageNum="+log.getPageNum()+"&items="+log.getItems()+"&text=" +log.getText()+"&type="+log.getType();
			System.out.println("이전 체크: " + home);
		}else if(type.equals("user_mbtisearch")){
			SearchLog log = (SearchLog)session.getAttribute("researchLog");
			home = "/userPage/main_sortMbtiBoardView.jsp";
			System.out.println("이전 체크: " + home);
			session.setAttribute("checkMove", "return");
		}
		
	}
%>
<html>
<head>
<link rel="stylesheet" href=<%=route + style %> />
<title>Board</title>
</head>
<script type="text/javascript">
	function checkForm() {
		if (!document.insertComent.coment.value) {
			alert("댓글을 입력하세요.");
			return false;
		}
	}
</script>


<body>
<div class="container py-4">
	<jsp:include page="<%=menu %>"/>
	 <div class="p-5 mb-4 bg-body-tertiary rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold"><%=title %></h1>
        <p class="col-md-8 fs-4">Board</p>      
      </div>
    </div>
	<div class="row">	 
		<form name="newUpdate" action=""  method="post">
			<table class ="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3" style="background-color: #eeeeee; text-align: center;">게시판 글 보기</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 20%;">글 번호</td>
						<td colspan="2"><%=board.getBID() %></td>
					</tr>
					<tr>
						<td style="width: 20%;">글 제목</td>
						<td colspan="2"><%=board.getTitle() %></td>
					</tr>
					<tr>
						<td>작성자</td>
						<td colspan="2"><%=board.getId() %></td>
					</tr>
					<tr>
						<td>작성일자</td>
						<td colspan="2"><%=date %></td>
					</tr>
					<tr>
						<td>내용</td>
						<td colspan="2">
							<div  style="min-height: 200px; text-align: left">
								<%if(board.getImage() != null) {%>
									<img src = "<%= imgroot%>/<%=board.getImage() %>"  width="300px" height="300px">
								<%} %>
								<%=board.getContent() %>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- 지도 표시하기 -->
					<%if(!board.getPname().isEmpty()) {%>
					<div style="min-height: 400px">
					<div id="map" style="width:60%;height:40%;"></div>
					</div>
			
							<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7f90941742cf3698b0ef909894630ad9"></script>
							<script>
									var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
									    mapOption = { 
									        center: new kakao.maps.LatLng(<%=lat%>, <%=lng%>), // 지도의 중심좌표
									        level: 3 // 지도의 확대 레벨
									    };
									
									var map = new kakao.maps.Map(mapContainer, mapOption);
									
									// 마커가 표시될 위치입니다 
									var markerPosition  = new kakao.maps.LatLng(<%=lat%>, <%=lng%>); 
									
									// 마커를 생성합니다
									var marker = new kakao.maps.Marker({
									    position: markerPosition
									});
									
									// 마커가 지도 위에 표시되도록 설정합니다
									marker.setMap(map);
									
									var iwContent = '<div style="padding:5px;"><%=pname%><br><%=paddress%></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
									    iwPosition = new kakao.maps.LatLng(<%=lat%>, <%=lng%>); //인포윈도우 표시 위치입니다
									
									// 인포윈도우를 생성합니다
									var infowindow = new kakao.maps.InfoWindow({
									    position : iwPosition, 
									    content : iwContent 
									});
									  
									// 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
									infowindow.open(map, marker); 
							</script>
					<%} %>
		</form>
		<div class="mb-3 row">
			<div class="col-sm-offset-2 col-sm-10 ">
			<%if(!type.equals("notice")) {%>
				좋아요 수 <%=board.getGoohit() %>
				<%if(checkgood == 0) {%>
				<form name = "goodHit" action="<%=route %>/requestUpdateGoodhit.userdo" method="post">
					<input name="ID" type="hidden" class="form-control" value=<%=name %>>
					<input name="BID" type="hidden" class="form-control" value=<%=board.getBID() %>>
					<input name="type" type="hidden" class="form-control" value=<%=type %>>
					<input type="submit" class="btn btn-primary " value="좋아요">	
				</form>
				<%}else if(checkgood == 1) {%>
				<form name = "goodHit" action="<%=route %>/requestDeleteGoodhit.userdo" method="post">
					<input name="ID" type="hidden" class="form-control" value=<%=name %>>
					<input name="BID" type="hidden" class="form-control" value=<%=board.getBID() %>>
					<input name="type" type="hidden" class="form-control" value=<%=type %>>
					<input type="submit" class="btn btn-primary " value="좋아요 취소">	
				</form>
				<%} %>
			<%} %>
			</div>
		</div>
		<%if(!name.equals("admin")) {%>
		<div class="mb-3 row">
				<div class="col-sm-offset-2 col-sm-10 ">
				 <form name="insertComent" action="./insertComent.userdo?type=<%=type %>&&name=<%=name %>"  method="post" onsubmit="return checkForm()">
					 <label class="col-sm-2 control-label" >댓글 작성</label>
					 		<input name="ID" type="hidden" class="form-control" value=<%=name %>>
					 		<input name="BID" type="hidden" class="form-control" value=<%=board.getBID() %>>
						<div class="col-sm-8">
							<textarea name="coment" cols="50" rows="5" class="form-control"placeholder="coment"></textarea>
						</div>
					 <input type="submit" class="btn btn-primary " value="등록 ">				
					 <input type="reset" class="btn btn-primary " value="초기화 ">
				 </form>
				</div>
			</div>
			<%} %>
			<table class="table table-hover text-center">
					<tr>
						<th>댓글 번호</th>
						<th>유저ID</th>
						<th>내용</th>
						<th>작성일</th>
					</tr>
					<%
					if(comentlist != null){
						for (int j = 0; j < comentlist.size() ; j++){
							
							Coment coment = (Coment) comentlist.get(j);
							String date_coment = coment.getRegdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					%>
					<tr>
						<td><%=coment.getCNUM()%></td>
						<td><%=coment.getId() %></td>
						<td><%=coment.getContent()%></td>
						<td><%=date_coment%></td>
						<% if (name.equals(coment.getId())){%>
							<td><a href = "<%=request.getContextPath() %>/requestComentDelete.userdo?cnum=<%=coment.getCNUM() %>&&BID=<%=board.getBID() %>&&name=<%=name %>" class = "btn btn-secondary" role="button">댓글 삭제</a></td>
						<%} %>
					</tr>
					<%
						}
					}
					%>
				</table>
			</div>
			<a href =<%=route + home %> class = "btn btn-primary" role="button">이전</a>
	</div>
	<jsp:include page="../footer.jsp" />
	</div>
</body>
</html>


