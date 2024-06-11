package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import database.BoardDao;
import database.memberDao;
import database.messageDao;
import model.Board;
import model.Coment;
import model.Message;

import javax.servlet.*;
import javax.servlet.http.*;


public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final int LISTCOUNT = 5; 

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
	
		if (command.equals("/BoardListAction.do")) {//등록된 글 목록 페이지 출력하기
			requestBoardList(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("./adminPage/boardmanage_admin.jsp");
			rd.forward(request, response);
		} else if (command.equals("/BoardWriteAction.do")) {//새로운 글 등록
			requestBoardWrite(request);
			RequestDispatcher rd = request.getRequestDispatcher("/BoardListAction.do");
			rd.forward(request, response);						
		} else if (command.equals("/BoardWriteForm.do")) { //글 등록 페이지 출력
			RequestDispatcher rd = request.getRequestDispatcher("./board/writeForm.jsp");
			rd.forward(request, response);
		} else if(command.equals("/NoticeListAction.do")) { // 공지용 글 목록 페이지 출력
			System.out.println("공지용 글 목록 출력");
			requestNoticeList(request);
			RequestDispatcher rd = request.getRequestDispatcher("./adminPage/noticeManage_admin.jsp");
			rd.forward(request, response);
		} else if(command.equals("/NoticeWriteForm.do")) { // 공지용 글 작성 페이지 출력
			RequestDispatcher rd = request.getRequestDispatcher("./adminPage/noticeWriteForm.jsp");
			rd.forward(request, response);
		} else if(command.equals("/NoticeWriteAction.do")) { // 공지용 글 등록
			System.out.println(contextPath + command);
			requestBoardWrite(request);
			RequestDispatcher rd = request.getRequestDispatcher("/NoticeListAction.do");
			rd.forward(request, response);
		} else if(command.equals("/BoardViewAction.do")) { // 글 상세보기
			System.out.println("Common: " + request.getParameter("BID"));
			System.out.println("name: " + request.getParameter("name"));
			System.out.println("type: " + request.getParameter("type"));
			String type = request.getParameter("type");
			
			requestComentList(request); // 댓글 목록 가져오기
			if(request.getAttribute("comentlist") == null)
				System.out.println("불러오기 실패");
			requestBoardView(request);
			RequestDispatcher rd;
			
			rd = request.getRequestDispatcher("./board/boardview.jsp?type="+type);
			
			rd.forward(request, response);
		} else if(command.equals("/SearchListAction.do")) { // 게시글 겁색하기
			System.out.println("items: " + request.getParameter("items"));
			System.out.println("text: " + request.getParameter("text"));
			System.out.println("type: " + request.getParameter("type"));
			requestSearchList(request);
			
			//List searchlist = (List) request.getAttribute("searchlist");
			
			RequestDispatcher rd = request.getRequestDispatcher("./adminPage/researchBoard_admin.jsp");
			
			rd.forward(request, response);
		}  else if(command.equals("/sendDeleteMail.do")) { // 유저 강제 탈퇴 메일 보내기
			boolean check = requestSendDeleteMail(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("./adminPage/usermanage_admin.jsp");
			
			rd.forward(request, response);
			
		}
	}
	
	public boolean requestSendDeleteMail(HttpServletRequest request) {//메일 보내기
		messageDao dao = messageDao.getInstance();
		memberDao mdao = memberDao.getInstance();
		
		int result = mdao.checkMemberById(request.getParameter("rid"));
		if(result == 0) {
			request.setAttribute("error", "riderror");
			return false; //보내는 아이디가 없다. 
		}
		
		mdao.autoDeletMemberById(request.getParameter("rid"));
		
		Message msg = new Message();
		msg.setUID(request.getParameter("uid"));
		msg.setRID(request.getParameter("rid"));
		msg.setTITLE(request.getParameter("title"));
		msg.setCONTENT(request.getParameter("content"));
		
		dao.insertMessage(msg);
		
		return true;
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
		
		System.out.println("check");
	}
	
	public void requestComentList(HttpServletRequest request) { //댓글 목록 가져오기
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Coment> comentlist = new ArrayList<Coment>();
		
		comentlist = dao.getComentList(Integer.parseInt(request.getParameter("BID")));
   
   		request.setAttribute("comentlist", comentlist);
	}
	
	public void requestBoardView(HttpServletRequest request){ //상세 글 정보 가져오기
		
		BoardDao dao = BoardDao.getInstance();
		String BID = request.getParameter("BID");	
		String name = request.getParameter("name");
		//System.out.println(BID);
		
		
		Board board = new Board();
		board = dao.getBoardByNum(BID);	
		
		int checkgood = -1; //관리자 라면 -1로 좋아요 버튼 없음.
		System.out.println("이름: " + name + " 글번호: " + BID);
		
		request.setAttribute("BID", BID);		 
   		request.setAttribute("board", board); 
   		request.setAttribute("checkgood", checkgood); 
	}
	
   //새로운 글 등록하기
	public void requestBoardWrite(HttpServletRequest request){
		BoardDao dao = BoardDao.getInstance();		
		
		Board board = new Board();
		
		board.setId(request.getParameter("id"));
		System.out.println(request.getParameter("name"));
		board.setTitle(request.getParameter("subject"));
		board.setRegdate(LocalDateTime.now());
		board.setUpddate(null);
		board.setImage(null);
		board.setContent(request.getParameter("content"));	
		board.setGoohit(0);
		board.setHit(0);
		board.setPaddress(null);
		board.setPname(null);
		board.setLatclick(null);
		board.setLngclick(null);
			
		dao.insertBoard(board);								
	}
	//공지 게시글 보여주기
	public void requestNoticeList(HttpServletRequest request) {
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Board> boardlist = new ArrayList<Board>();
		
		int pageNum=1;
		int limit=LISTCOUNT;
		String name = "admin";
		
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
		request.setAttribute("type", "notice");
		
		System.out.println("check");
	}
	//일반 게시글 보여주기
	public void requestBoardList(HttpServletRequest request){
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Board> boardlist = new ArrayList<Board>();
		
	  	int pageNum=1;
		int limit=LISTCOUNT;
		
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
		request.setAttribute("type", "common");
	}
}
