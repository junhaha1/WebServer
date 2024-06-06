package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import database.BoardDao;
import database.memberDao;
import model.Board;

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
		} 
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
		board.setFirstadd(request.getParameter("add1"));
		board.setSecondadd(request.getParameter("add2"));
			
		dao.insertBoard(board);								
	}
	//공지 게시글 보여주기
	public void requestNoticeList(HttpServletRequest request) {
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Board> boardlist = new ArrayList<Board>();
		
		int pageNum=1;
		int limit=LISTCOUNT;
		String name = "'admin'";
		
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
