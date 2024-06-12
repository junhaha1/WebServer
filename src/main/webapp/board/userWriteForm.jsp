<%@ page contentType="text/html; charset=utf-8"%>

<%
	String name = (String) session.getAttribute("sessionId");
	String route = request.getContextPath();
	String userWrite = "/BoardWriteAction.userdo";
	String home = "/AllBoardListAction.userdo";
%>
<html>
<head>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" />
<title>Board</title>
<style>
body{
	font-family:"맑은 고딕", "고딕", "굴림";
}

html, body {
margin: 0px; 
padding: 0px;
}

#wrapper{
	width: 1200px;
	margin: 0 auto;	
}

.map_wrap, .map_wrap * {margin:0;padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;}
.map_wrap a, .map_wrap a:hover, .map_wrap a:active{color:#000;text-decoration: none;}
.map_wrap {position:relative;width:100%;height:480px;}
#menu_wrap {position:absolute;top:0;left:0;bottom:0;width:250px;margin:10px 0 30px 10px;padding:5px;overflow-y:auto;background:rgba(255, 255, 255, 0.7);z-index: 1;font-size:12px;border-radius: 10px;}
.bg_white {background:#fff;}
#menu_wrap hr {display: block; height: 1px;border: 0; border-top: 2px solid #5F5F5F;margin:3px 0;}
#menu_wrap .option{text-align: center;}
#menu_wrap .option p {margin:10px 0;}  
#menu_wrap .option button {margin-left:5px;}
#placesList li {list-style: none;}
#placesList .item {position:relative;border-bottom:1px solid #888;overflow: hidden;cursor: pointer;min-height: 65px;}
#placesList .item span {display: block;margin-top:4px;}
#placesList .item h5, #placesList .item .info {text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
#placesList .item .info{padding:10px 0 10px 55px;}
#placesList .info .gray {color:#8a8a8a;}
#placesList .info .jibun {padding-left:26px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png) no-repeat;}
#placesList .info .tel {color:#009900;}
#placesList .info .placey {color:#009900;}
#placesList .info .placex {color:#009900;}
#placesList .item .markerbg {float:left;position:absolute;width:36px; height:37px;margin:10px 0 0 10px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;}
#placesList .item .marker_1 {background-position: 0 -10px;}
#placesList .item .marker_2 {background-position: 0 -56px;}
#placesList .item .marker_3 {background-position: 0 -102px}
#placesList .item .marker_4 {background-position: 0 -148px;}
#placesList .item .marker_5 {background-position: 0 -194px;}
#placesList .item .marker_6 {background-position: 0 -240px;}
#placesList .item .marker_7 {background-position: 0 -286px;}
#placesList .item .marker_8 {background-position: 0 -332px;}
#placesList .item .marker_9 {background-position: 0 -378px;}
#placesList .item .marker_10 {background-position: 0 -423px;}
#placesList .item .marker_11 {background-position: 0 -470px;}
#placesList .item .marker_12 {background-position: 0 -516px;}
#placesList .item .marker_13 {background-position: 0 -562px;}
#placesList .item .marker_14 {background-position: 0 -608px;}
#placesList .item .marker_15 {background-position: 0 -654px;}
#pagination {margin:10px auto;text-align: center;}
#pagination a {display:inline-block;margin-right:10px;}
#pagination .on {font-weight: bold; cursor: default;color:#777;}

</style>
</head>
<script type="text/javascript">
	function btnClick() {

	  const mydiv = document.getElementById('wrapper');
	  
	  if(mydiv.style.display === 'none') {

	    mydiv.style.display = 'block';

	  }else {
	    mydiv.style.display = 'none';
	  }
	}
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
<body>
<div class="container py-4">
	<jsp:include page="../menu.jsp" />
	 <div class="p-5 mb-4 bg-body-tertiary rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold">게시글 작성</h1>
        <p class="col-md-8 fs-4">Board</p>      
      </div>
    </div>

	<div class="row align-items-md-stretch text-center">	 	

		<form name="newWrite" action=<%=route + userWrite %> enctype="multipart/form-data" method="post" onsubmit="return checkForm()">
			<input name="id" type="hidden" class="form-control" value=<%=name %>>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >UserID</label>
				<div class="col-sm-3">
					<input name="name" type="text" class="form-control" value=<%=name %> placeholder="name" disabled>
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >제목</label>
				<div class="col-sm-5">
					<input name="subject" type="text" class="form-control"	placeholder="subject">
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >이미지 업로드</label>
				<div class="col-sm-5">
					<input type="file" name="fileName">
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >내용</label>
				<div class="col-sm-8">
					<textarea name="content" cols="50" rows="5" class="form-control"placeholder="content"></textarea>
				</div>
			</div>
			<button onclick='btnClick()'>지도 유무</button>
			<div id="wrapper">
				<div class="map_wrap">
				    <div id="map" style="width:80%;height:80%;position:relative;overflow:hidden;"></div>
				
				    <div id="menu_wrap" class="bg_white">
				        <div class="option">
				            <div>
				                    키워드 : <input type="text" value="서울역" id="keyword" size="15"> 
				            <button onclick="searchPlaces(); return false;">검색하기</button> 
				            </div>
				        </div>
				        <hr>
				        <ul id="placesList"></ul>
				        <div id="pagination"></div>
				    </div>
				</div>
			<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7f90941742cf3698b0ef909894630ad9&libraries=services"></script>
			<script>
			// 마커를 담을 배열입니다
			var markers = [];
			
			var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
			    mapOption = {
			        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
			        level: 3 // 지도의 확대 레벨
			    };  
			
			// 지도를 생성합니다    
			var map = new kakao.maps.Map(mapContainer, mapOption); 
			
			// 장소 검색 객체를 생성합니다
			var ps = new kakao.maps.services.Places();  
			
			// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
			var infowindow = new kakao.maps.InfoWindow({zIndex:1});
			
			// 키워드로 장소를 검색합니다
			searchPlaces();
			
			// 키워드 검색을 요청하는 함수입니다
			function searchPlaces() {
			
			    var keyword = document.getElementById('keyword').value;
			
			    if (!keyword.replace(/^\s+|\s+$/g, '')) {
			        alert('키워드를 입력해주세요!');
			        return false;
			    }
			
			    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
			    ps.keywordSearch( keyword, placesSearchCB); 
			}
			
			// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
			function placesSearchCB(data, status, pagination) {
			    if (status === kakao.maps.services.Status.OK) {
			
			        // 정상적으로 검색이 완료됐으면
			        // 검색 목록과 마커를 표출합니다
			        displayPlaces(data);
			
			        // 페이지 번호를 표출합니다
			        displayPagination(pagination);
			
			    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
			
			        alert('검색 결과가 존재하지 않습니다.');
			        return;
			
			    } else if (status === kakao.maps.services.Status.ERROR) {
			
			        alert('검색 결과 중 오류가 발생했습니다.');
			        return;
			
			    }
			}
			
			// 검색 결과 목록과 마커를 표출하는 함수입니다
			function displayPlaces(places) {
			
			    var listEl = document.getElementById('placesList'), 
			    menuEl = document.getElementById('menu_wrap'),
			    fragment = document.createDocumentFragment(), 
			    bounds = new kakao.maps.LatLngBounds(), 
			    listStr = '';
			    
			    // 검색 결과 목록에 추가된 항목들을 제거합니다
			    removeAllChildNods(listEl);
			
			    // 지도에 표시되고 있는 마커를 제거합니다
			    removeMarker();
			    
			    for ( var i=0; i<places.length; i++ ) {
			
			        // 마커를 생성하고 지도에 표시합니다
			        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
			            marker = addMarker(placePosition, i), 
			            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
			
			        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
			        // LatLngBounds 객체에 좌표를 추가합니다
			        bounds.extend(placePosition);
			
			        // 마커와 검색결과 항목에 mouseover 했을때
			        // 해당 장소에 인포윈도우에 장소명을 표시합니다
			        // mouseout 했을 때는 인포윈도우를 닫습니다
			        (function(marker, pname, praddress, paddress, plat, plng) {
			            kakao.maps.event.addListener(marker, 'mouseover', function() {
			                displayInfowindow(marker, pname);
			            });
			
			            kakao.maps.event.addListener(map, 'mouseout', function() {
			                infowindow.close();
			            });
			            
			            // 리스트의 아이템을 클릭하면 정보들을 text 영역으로 전송 (hidden 사용가능)
			            itemEl.onclick = function() {
			                if (praddress) {
			                document.getElementById('fulladdress').value = "[" + pname + "]" + praddress;
			                } else {
			                document.getElementById('fulladdress').value = "[" + pname + "]" + paddress;
			                }
			            	
			            	document.getElementById('pname').value = pname;            	
			                if (praddress) {
			                	document.getElementById('paddress').value = praddress;
			                	document.getElementById('paddress_v').value = praddress;
			                } else {
			                	document.getElementById('paddress').value = paddress; 
			                	document.getElementById('paddress_v').value = praddress;
			                }
			            	document.getElementById('latclick_v').value = plat;
			            	document.getElementById('lngclick_v').value = plng;
			            	document.getElementById('latclick').value = plat;
			            	document.getElementById('lngclick').value = plng;
			            };
			
			            itemEl.onmouseover =  function () {
			                displayInfowindow(marker, pname);
			            };
			
			            itemEl.onmouseout =  function () {
			                infowindow.close();
			            };
			        })(marker, places[i].place_name, places[i].road_address_name, places[i].address_name, places[i].y, places[i].x);
			
			        fragment.appendChild(itemEl);
			    }
			
			    // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
			    listEl.appendChild(fragment);
			    menuEl.scrollTop = 0;
			
			    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
			    map.setBounds(bounds);
			}
			
			// 검색결과 항목을 Element로 반환하는 함수입니다
			function getListItem(index, places) {
			
			    var el = document.createElement('li'),
			    itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
			                '<div class="info" style="cursor:pointer;">' +
			                '   <h5>' + places.place_name + '</h5>';
			
			    if (places.road_address_name) {
			        itemStr += '    <span>' + places.road_address_name + '</span>' +
			                    '   <span class="jibun gray">' +  places.address_name  + '</span>';
			    } else {
			        itemStr += '    <span>' +  places.address_name  + '</span>'; 
			    }
			      itemStr += '  <span class="tel">' + places.phone + '</span>'; +
			                '</div>';           
			
			    el.innerHTML = itemStr;
			    el.className = 'item';
			
			    return el;
			}
			
			
			// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
			function addMarker(position, idx, title) {
			    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
			        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
			        imgOptions =  {
			            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
			            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
			            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
			        },
			        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
			            marker = new kakao.maps.Marker({
			            position: position, // 마커의 위치
			            image: markerImage 
			        });
			
			    marker.setMap(map); // 지도 위에 마커를 표출합니다
			    markers.push(marker);  // 배열에 생성된 마커를 추가합니다
			
			    return marker;
			}
			
			// 지도 위에 표시되고 있는 마커를 모두 제거합니다
			function removeMarker() {
			    for ( var i = 0; i < markers.length; i++ ) {
			        markers[i].setMap(null);
			    }   
			    markers = [];
			}
			
			// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
			// 인포윈도우에 장소명을 표시합니다
			function displayInfowindow(marker, title) {
			    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';
			
			    infowindow.setContent(content);
			    infowindow.open(map, marker);
			}
			
			 // 검색결과 목록의 자식 Element를 제거하는 함수입니다
			function removeAllChildNods(el) {   
			    while (el.hasChildNodes()) {
			        el.removeChild (el.lastChild);
			    }
			}
			</script>
					<div>
				    <!-- 위도 및 경도 좌표 및 위치정보 -->
				    주소 확인<input type="text" id="fulladdress" name="fulladdress" style="width:90%;" disabled> 
				    <input type="text" id="pname" name="pname" value="">   
				    <input type="text" id="paddress_v" name="paddress_v" value="" disabled>  
				    <input type="text" id="latclick_v" name="latclick_v" value="" disabled> 
				    <input type="text" id="lngclick_v" name="lngclick_v" value="" disabled> 
				    <input type="hidden" id="paddress" name="paddress" value="" >  
				    <input type="hidden" id="latclick" name="latclick" value="" > 
				    <input type="hidden" id="lngclick" name="lngclick" value="" > 
				    </div>
			</div>
			<div class="mb-3 row">
				<div class="col-sm-offset-2 col-sm-10 ">
				 <a href =<%=route + home %> class = "btn btn-primary" role="button">이전</a>
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



