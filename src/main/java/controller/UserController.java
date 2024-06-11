package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import database.BoardDao;
import database.memberDao;
import database.messageDao;
import model.Board;
import model.Coment;
import model.Member;
import model.Message;

import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;


public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final int LISTCOUNT = 8; 
	static final int NOTICECOUNT = 3; 

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
	
		if (command.equals("/BoardWriteAction.userdo")) { //일반 사용자 게시글 작성
			requestBoardWrite(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/AllBoardListAction.userdo");
			rd.forward(request, response);
		} else if (command.equals("/BoardListAction.userdo")) {//일반 사용자 게시글만 보여주기
			requestBoardList(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/main_common.jsp");
			rd.forward(request, response);
		} else if (command.equals("/NoticeBoardListAciton.userdo")) { //공지용 게시판만 보여주기
			requestNoticeList(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/main_notice.jsp");
			rd.forward(request, response); 
		} else if(command.equals("/AllBoardListAction.userdo")) { //모든 게시판 보여주기
			requestBoardList(request);
			requestNoticeList(request);
			
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/mainpage.jsp");
			rd.forward(request, response); 
		} else if (command.equals("/userBoardView.userdo")) {  //글 상세 페이지 출
			System.out.println("Common: " + request.getParameter("BID"));
			System.out.println("name: " + request.getParameter("name"));
			String type = request.getParameter("type");
			requestComentList(request); // 댓글 목록 가져오기
			if(request.getAttribute("comentlist") == null)
				System.out.println("불러오기 실패");
			requestBoardView(request);
			RequestDispatcher rd;
			
			if(type != null && type.equals("common"))
				rd = request.getRequestDispatcher("./board/boardview.jsp?type="+type);
			else
				rd = request.getRequestDispatcher("./board/boardview.jsp");
			
			rd.forward(request, response);
		} else if (command.equals("/userNoticeView.userdo")) {  //공지 상세 페이지 줄
			System.out.println("Notice: " + request.getParameter("BID"));
			String type = request.getParameter("type");
			
			requestBoardView(request);
			RequestDispatcher rd;
			if(type != null && type.equals("notice"))
				rd = request.getRequestDispatcher("./board/boardview.jsp?type="+type);
			else
				rd = request.getRequestDispatcher("./board/boardview.jsp");
			rd.forward(request, response);
		} else if(command.equals("/insertComent.userdo")) { //댓글 작성하기
			System.out.println("ID: " + request.getParameter("ID"));
			System.out.println("BID: " + request.getParameter("BID"));
			System.out.println("coment: " + request.getParameter("coment"));
			System.out.println("type: " + request.getParameter("type"));
			
			String type = request.getParameter("type");
			String name = request.getParameter("name");
			
			requestInsertComent(request);
			
			RequestDispatcher rd = null;
			if(type.equals("notice")) {
				rd = request.getRequestDispatcher("/userNoticeView.userdo?type="+type);
			}else if(type.equals("common")){
				rd = request.getRequestDispatcher("/userBoardView.userdo?type="+type);
			}else if(type.equals("coment")) {
				rd = request.getRequestDispatcher("/userBoardView.userdo?type="+type);
			}else {
				rd = request.getRequestDispatcher("/AllBoardListAction.userdo");
			}
			rd.forward(request, response); 
		} else if(command.equals("/comentBoardListAction.userdo")) { //내 댓글 목록 보여주기
			requestUserComentList(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/main_coment.jsp");
			rd.forward(request, response);
		} else if(command.equals("/MyBoardListAction.userdo")) { // 로그인한 유저가 쓴 게시글만 불러오기
			requestUserBoardList(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/main_userBoard.jsp");
			rd.forward(request, response);
		} else if(command.equals("/requestUserInfo.userdo")) { // 로그인한 유저 정보 불러오기
			requestUserInfo(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/memberInfo.jsp");
			rd.forward(request, response);
		} else if(command.equals("/requestUserDelete.userdo")) { // 유저 정보 삭제하기 
			int code = requestUserDelete(request);
			request.setAttribute("code", code);
			RequestDispatcher rd = null;
			
			if(code == 0) { //탈퇴 쿼리 중 멤버 조회 실패
				request.setAttribute("passwd", request.getParameter("passwd"));
				rd = request.getRequestDispatcher("/userPage/deletemember.jsp");
			}
			else if(code == 1) //탈퇴 성공
				rd = request.getRequestDispatcher("/userPage/delete_confirm.jsp");
			else if(code == 2) {// 비밀번호가 회원 정보와 일치하지 않음. 
				request.setAttribute("passwd", request.getParameter("passwd"));
				rd = request.getRequestDispatcher("/userPage/deletemember.jsp");
			}
			rd.forward(request, response);
		} else if(command.equals("/requestBoardDelete.userdo")) { //게시글 삭제 액션
			int code = requestBoardDelete(request);
			String bid = request.getParameter("bid");
			String title = request.getParameter("title");
			
			System.out.println("name: " + request.getParameter("name") + " bid: " + bid + " title: " + title);
			request.setAttribute("code", code);
			RequestDispatcher rd = null;
			
			if(code == 0 || code == 2) { //탈퇴 쿼리 중 멤버 조회 실패
				rd = request.getRequestDispatcher("/board/deleteBoardForm.jsp?bid="+bid+"&&title="+title);
			}
			else if(code == 1) //글 삭제 성공
				rd = request.getRequestDispatcher("/MyBoardListAction.userdo?name="+ request.getParameter("name"));
			
			rd.forward(request, response);
		} else if(command.equals("/requestComentDelete.userdo")) { //댓글 삭제 액션
			int code = requestComentDelete(request);
			
			String bid = request.getParameter("BID");
			String cnum = request.getParameter("cnum");
			String name = request.getParameter("name");
			
			System.out.println("cnum: " + cnum + " bid: " + bid + " name: " + name);
			
			RequestDispatcher rd = null;

			if(bid == null) //내 댓글 목록에서 삭제
				rd = request.getRequestDispatcher("/comentBoardListAction.userdo?type=coment&&name="+name);
			else
				rd = request.getRequestDispatcher("/userBoardView.userdo?BID=" + bid + "&&type=common");
			
			rd.forward(request, response);
		} else if(command.equals("/requestUpdateGoodhit.userdo")) { //좋아요 눌렀을 때 처리
			String BID = request.getParameter("BID");
			String ID = request.getParameter("ID");
			String type = request.getParameter("type");
			requestUpdateGoodhit(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("/userBoardView.userdo?BID="+BID+"&&name="+ID + "&&type="+type);
			rd.forward(request, response);
		} else if(command.equals("/requestDeleteGoodhit.userdo")) { //좋아요 눌렀을 때 처리
			String BID = request.getParameter("BID");
			String ID = request.getParameter("ID");
			String type = request.getParameter("type");
			requestDeleteGoodhit(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("/userBoardView.userdo?BID="+BID+"&&name="+ID + "&&type="+type);
			rd.forward(request, response);
		} else if(command.equals("/SearchListAction.userdo")) { // 게시글 겁색하기
			System.out.println("items: " + request.getParameter("items"));
			System.out.println("text: " + request.getParameter("text"));
			System.out.println("type: " + request.getParameter("type"));
			requestSearchList(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("./userPage/researchBoard_user.jsp");
			
			rd.forward(request, response);
		} else if(command.equals("/requestUserMail.userdo")) { // 내 메일 불러오기
			System.out.println("name: " + request.getParameter("name"));
			System.out.println("option: " + request.getParameter("option"));
			requestMailList(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("./userPage/main_message.jsp");
			
			rd.forward(request, response);
		} else if(command.equals("/userMailView.userdo")) { // 메일 상세보기 불러오기
			System.out.println("MID: " + request.getParameter("MID"));
			System.out.println("type: " + request.getParameter("type"));
			System.out.println("name: " + request.getParameter("name"));
			
			requestMailView(request);
			RequestDispatcher rd = request.getRequestDispatcher("./board/messageView.jsp");
			rd.forward(request, response);
		} else if(command.equals("/sendMail.userdo")) { // 메일 상세보기 불러오기
			System.out.println("uid: " + request.getParameter("uid"));
			System.out.println("rid: " + request.getParameter("rid"));
			System.out.println("title: " + request.getParameter("title"));
			
			boolean check = requestSendMail(request);
			RequestDispatcher rd = null;
			
			if(((String) request.getAttribute("error"))!= null && ((String) request.getAttribute("error")).equals("riderror")) { //아이디가 없을 경우
				Message msg = new Message();
				msg.setUID(request.getParameter("uid"));
				msg.setRID(request.getParameter("rid"));
				msg.setTITLE(request.getParameter("title"));
				msg.setCONTENT(request.getParameter("content"));
				
				request.setAttribute("msg", msg);
				
				rd = request.getRequestDispatcher("./board/userMessageWriteForm.jsp");
				rd.forward(request, response);
			}else {
				if(check)
					rd = request.getRequestDispatcher("./userPage/msg_success.jsp");
				else
					rd = request.getRequestDispatcher("./userPage/msg_false.jsp");
				rd.forward(request, response);
			}
		}
	}
	
	public boolean requestSendMail(HttpServletRequest request) {//메일 보내기
		messageDao dao = messageDao.getInstance();
		memberDao mdao = memberDao.getInstance();
		int result = mdao.checkMemberById(request.getParameter("rid"));
		
		if(result == 0) {
			request.setAttribute("error", "riderror");
			return false; //보내는 아이디가 없다. 
		}
		
		Message msg = new Message();
		msg.setUID(request.getParameter("uid"));
		msg.setRID(request.getParameter("rid"));
		msg.setTITLE(request.getParameter("title"));
		msg.setCONTENT(request.getParameter("content"));
		
		boolean check = dao.insertMessage(msg);
		
		return check;
	}
	
	public void requestMailView(HttpServletRequest request) { //메일 상세내용 불러오기
		messageDao dao = messageDao.getInstance();
		Message msg = null;
		
		int MID = Integer.parseInt(request.getParameter("MID"));
		msg = dao.getMessage(MID);
		request.setAttribute("msg", msg);
	}
	
	public void requestMailList(HttpServletRequest request) { //메일 목록 불러오기
		messageDao dao = messageDao.getInstance();
		ArrayList<Message> maillist = new ArrayList<Message>();
		
		int pageNum=1;
		int limit=LISTCOUNT;
		int option = Integer.parseInt(request.getParameter("option"));
		String name = request.getParameter("name");
		
		if(request.getParameter("pageNum")!=null)
			pageNum=Integer.parseInt(request.getParameter("pageNum"));
		
		int total_record = 0;
		
		if(option == 0) //받은 메일
			total_record = dao.getReciListCount(name); // 총 게시글 갯수
		else if(option == 1)
			total_record = dao.getSendListCount(name); // 총 게시글 갯수
		
		maillist = dao.selectMessage(pageNum, limit, name, option); // 해당 게시글들 정보
		
		int total_page; // 갯수
		
		if (total_record % limit == 0){     
	     	total_page =total_record/limit;
	     	Math.floor(total_page);  
		}
		else{
		   total_page =total_record/limit;
		   Math.floor(total_page); 
		   total_page =  total_page + 1; 
		}		
   
   		request.setAttribute("pageNum", pageNum);		  
   		request.setAttribute("total_page", total_page);   
		request.setAttribute("total_record",total_record); 
		request.setAttribute("maillist", maillist);
		
		//request.setAttribute("type", type);
		//request.setAttribute("items", code);
		//request.setAttribute("text", name);
		
		System.out.println("mail check");
	}
	
	public void requestSearchList(HttpServletRequest request) { //검색 목록 가져오기
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Board> searchlist = new ArrayList<Board>();
		
		int pageNum=1;
		int limit=LISTCOUNT;
		
		String name = request.getParameter("text");
		int code = Integer.parseInt(request.getParameter("items"));
		String type = request.getParameter("type");
		
		if(request.getParameter("pageNum")!=null)
			pageNum=Integer.parseInt(request.getParameter("pageNum"));
		
		int total_record = dao.getSearchCount("BOARD", code, name, type); // 총 게시글 갯수
		searchlist = dao.getSearchList(pageNum, limit, code, name, type); // 해당 게시글들 정보
		
		int total_page; // 갯수
		
		if (total_record % limit == 0){     
	     	total_page =total_record/limit;
	     	Math.floor(total_page);  
		}
		else{
		   total_page =total_record/limit;
		   Math.floor(total_page); 
		   total_page =  total_page + 1; 
		}		
   
   		request.setAttribute("pageNum", pageNum);		  
   		request.setAttribute("total_page", total_page);   
		request.setAttribute("total_record",total_record); 
		request.setAttribute("searchlist", searchlist);		
		request.setAttribute("type", type);
		request.setAttribute("items", code);
		request.setAttribute("text", name);
		
		//System.out.println("check");
	}
	
   //새로운 글 등록하기
	public void requestBoardWrite(HttpServletRequest request){
		/*파일 업로드*/
		String upload= request.getServletContext().getRealPath("/resources/images");		
		String encType = "utf-8";				
		int maxSize=5*1024*1024;	
		
		System.out.println(upload + " => " + request.getParameter("fileName"));
				
		MultipartRequest multi = null;
		
		//파일업로드를 직접적으로 담당		
		try {
			multi = new MultipartRequest(request, upload, maxSize, encType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BoardDao dao = BoardDao.getInstance();		
		memberDao mdao = memberDao.getInstance();
		
		Board board = new Board();
		Member member = mdao.getMemberInfo(multi.getParameter("id"));
		
		board.setId(multi.getParameter("id"));
		board.setMbti(member.getMbti());
		System.out.println(multi.getParameter("name"));
		board.setTitle(multi.getParameter("subject"));
		board.setRegdate(LocalDateTime.now());
		board.setUpddate(null);
		board.setImage(multi.getFilesystemName("fileName"));
		board.setContent(multi.getParameter("content"));	
		board.setGoohit(0);
		board.setHit(0);
		board.setPname(multi.getParameter("pname"));
		board.setPaddress(multi.getParameter("paddress"));
		board.setLatclick(multi.getParameter("latclick"));
		board.setLngclick(multi.getParameter("lngclick"));
			
		dao.insertBoard(board);	
	}
	//댓글 등록하기
	public void requestInsertComent(HttpServletRequest request) { 
		BoardDao dao = BoardDao.getInstance();
		int BID = Integer.parseInt(request.getParameter("BID"));
		String ID = request.getParameter("ID");
		String coment = request.getParameter("coment");
		String PID = null; //후에 추가할 대댓글을 위한 부모 아이디
		
		dao.insertComent(BID, ID, coment, PID);
		
	}
	
	public void requestUpdateGoodhit(HttpServletRequest request) { //좋아요 눌렀을 때 
		BoardDao dao = BoardDao.getInstance();
		memberDao mdao = memberDao.getInstance();
		
		int BID = Integer.parseInt(request.getParameter("BID"));
		String ID = request.getParameter("ID");
		
		if(dao.getBoardStateCount(BID) != 1) // 테이블에 없을 경우
			dao.insertBoardState(BID);
		
		dao.insertUserGoodhit(BID, ID);
		
		Member member = mdao.getMemberInfo(ID);
		
		dao.updateBoardState(BID, member.getMbti());
	}
	
	public void requestDeleteGoodhit(HttpServletRequest request) { //좋아요 취소 눌렀을 때 
		BoardDao dao = BoardDao.getInstance();
		memberDao mdao = memberDao.getInstance();
		
		int BID = Integer.parseInt(request.getParameter("BID"));
		String ID = request.getParameter("ID");
	
		dao.deleteUserGoodhit(BID, ID);
		
		Member member = mdao.getMemberInfo(ID);
		
		dao.reduceBoardState(BID, member.getMbti());
	}
	
	//댓글 목록 가져오기
	public void requestComentList(HttpServletRequest request) {
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Coment> comentlist = new ArrayList<Coment>();
		
		comentlist = dao.getComentList(Integer.parseInt(request.getParameter("BID")));
   
   		request.setAttribute("comentlist", comentlist);
	}
	
	//유저 정보 불러오기
	public void requestUserInfo(HttpServletRequest request) {
		memberDao dao = memberDao.getInstance();
		Member member = dao.getMemberInfo(request.getParameter("name"));
		
		request.setAttribute("member", member);
	}
	public int requestBoardDelete(HttpServletRequest request) { //게시글 삭제하기
		BoardDao dao = BoardDao.getInstance();
		int code = 0;
		String id = request.getParameter("name");
		String pw = request.getParameter("passwd");
		
		String pw_check = memberDao.getInstance().checkMemberPw(id);
		
		System.out.println("user id pw: "+ id + "  " + pw + " db: " + pw_check + " bid: " + request.getParameter("bid"));
		
		if(pw_check != null && pw.equals(pw_check))
			code = dao.deleteBoardByBid(request.getParameter("bid"));
		else
			return 2; //비밀번호가 틀렸다는 code값
		
		return code; //0은 탈퇴 실패(멤버 조회가 안됨) 1은 탈퇴 성공
	}
	public int requestComentDelete(HttpServletRequest request) { //댓글 삭제하기
		BoardDao dao = BoardDao.getInstance();
		int code = 0;
		
		code = dao.deleteComentByCnum(request.getParameter("cnum"));
		
		return code; //0은 댓글 삭제 실패(댓글 조회가 안됨) 1은 댓글 삭제 성공
	}
	public int requestUserDelete(HttpServletRequest request) { //유저 정보 삭제하기
		memberDao dao = memberDao.getInstance();
		int code = 0;
		String id = request.getParameter("name");
		String pw = request.getParameter("passwd");
		
		String pw_check = dao.checkMemberPw(id);
		
		System.out.println("user id pw: "+ id + "  " + pw + " db: " + pw_check);
		
		if(pw_check != null && pw.equals(pw_check))
			code = dao.deleteMemberById(request.getParameter("name"));
		else
			return 2; //비밀번호가 틀렸다는 code값
		
		return code; //0은 탈퇴 실패(멤버 조회가 안됨) 1은 탈퇴 성공
	}
	
	public void requestUserComentList(HttpServletRequest request) { //댓글만 보기
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Coment> comentlist = new ArrayList<Coment>();
		
	  	int pageNum=1;
		int limit = LISTCOUNT;
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		
		if(type != null && type.equals("coment")) {
			request.setAttribute("type", "coment");
			System.out.println("type = " + type + "   limit: " + limit);
		}
			
		
		if(request.getParameter("pageNum_coment")!=null)
			pageNum=Integer.parseInt(request.getParameter("pageNum_coment"));
		
		int total_record = dao.getListCount("COMENT", name);
		comentlist = dao.getUserComentList(pageNum, limit, name); 
		
		int total_page; // 갯수
		
		if (total_record % limit == 0){     
	     	total_page =total_record/limit;
	     	Math.floor(total_page);  
		}
		else{
		   total_page =total_record/limit;
		   Math.floor(total_page); 
		   total_page =  total_page + 1; 
		}		
		
   		request.setAttribute("pageNum_coment", pageNum);		  
   		request.setAttribute("total_page_coment", total_page);   
		request.setAttribute("total_record_coment",total_record); 
		request.setAttribute("comentlist", comentlist);
		
	}
	//로그인한 사용자의 게시글만 보여주기
	public void requestUserBoardList(HttpServletRequest request) {
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Board> boardlist = new ArrayList<Board>();
		
	  	int pageNum=1;
		int limit=LISTCOUNT;
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		
		if(type != null && type.equals("common")) {
			limit += 10;
			request.setAttribute("type", "common");
			System.out.println("type = " + type + "   limit: " + limit);
		}
			
		
		if(request.getParameter("pageNum")!=null)
			pageNum=Integer.parseInt(request.getParameter("pageNum"));
		
		int total_record = dao.getListCount("BOARD", name); // 총 게시글 갯수
		boardlist = dao.getBoardList(pageNum, limit, name); 
		
		int total_page; // 갯수
		
		if (total_record % limit == 0){     
	     	total_page =total_record/limit;
	     	Math.floor(total_page);  
		}
		else{
		   total_page =total_record/limit;
		   Math.floor(total_page); 
		   total_page =  total_page + 1; 
		}		
   
   		request.setAttribute("pageNum", pageNum);		  
   		request.setAttribute("total_page", total_page);   
		request.setAttribute("total_record",total_record); 
		request.setAttribute("boardlist", boardlist);
	}
	
	//일반 게시글 보여주기
	public void requestBoardList(HttpServletRequest request){
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Board> boardlist = new ArrayList<Board>();
		
	  	int pageNum=1;
		int limit=LISTCOUNT;
		String type = request.getParameter("type");
		
		if(type != null && type.equals("common")) {
			limit += 10;
			request.setAttribute("type", "common");
			System.out.println("type = " + type + "   limit: " + limit);
		}
			
		
		if(request.getParameter("pageNum")!=null)
			pageNum=Integer.parseInt(request.getParameter("pageNum"));
		
		int total_record = dao.getListCount("BOARD", null); // 총 게시글 갯수
		boardlist = dao.getBoardList(pageNum, limit, null); 
		
		int total_page; // 갯수
		
		if (total_record % limit == 0){     
	     	total_page =total_record/limit;
	     	Math.floor(total_page);  
		}
		else{
		   total_page =total_record/limit;
		   Math.floor(total_page); 
		   total_page =  total_page + 1; 
		}		
   
   		request.setAttribute("pageNum", pageNum);		  
   		request.setAttribute("total_page", total_page);   
		request.setAttribute("total_record",total_record); 
		request.setAttribute("boardlist", boardlist);
	}
	
	
	
	public void requestNoticeList(HttpServletRequest request) {
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Board> boardlist = new ArrayList<Board>();
		
		int pageNum=1;
		int limit = NOTICECOUNT;
		String type = request.getParameter("type");

		if(type != null && type.equals("notice")){
			limit += 10;
			request.setAttribute("type", "notice");
			System.out.println("type = " + type + "   limit: " + limit);
		}
		String name = "admin";
		
		if(request.getParameter("pageNum_notice")!=null)
			pageNum=Integer.parseInt(request.getParameter("pageNum_notice"));
		
		int total_record = dao.getListCount("BOARD", "admin"); // 총 게시글 갯수
		boardlist = dao.getBoardList(pageNum, limit, name); 
		
		int total_page; // 갯수
		
		if (total_record % limit == 0){     
	     	total_page =total_record/limit;
	     	Math.floor(total_page);  
		}
		else{
		   total_page =total_record/limit;
		   Math.floor(total_page); 
		   total_page =  total_page + 1; 
		}		
   
   		request.setAttribute("pageNum_notice", pageNum);		  
   		request.setAttribute("total_page_notice", total_page);   
		request.setAttribute("total_record_notice",total_record); 
		request.setAttribute("noiceboardList", boardlist);		
	}
	
	public void requestBoardView(HttpServletRequest request){
		
		BoardDao dao = BoardDao.getInstance();
		String BID = request.getParameter("BID");	
		String name = request.getParameter("name");
		//System.out.println(BID);
		
		
		Board board = new Board();
		board = dao.getBoardByNum(BID);	
		
		
		int checkgood = dao.checkUserGood(name, BID); //좋아요 여부 가져오기 -> 0이라면 좋아요 누르지 않은 것 1이라면 좋아요 누른 것
		System.out.println("이름: " + name + " 글번호: " + BID);
		System.out.println("좋아요 check: " + checkgood);
		
		request.setAttribute("BID", BID);		 
   		request.setAttribute("board", board); 
   		request.setAttribute("checkgood", checkgood); 
	}
}
