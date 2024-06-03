package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import database.BoardDao;
import database.memberDao;
import model.Board;

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
		} else if (command.equals("/BoardListAction.userdo")) {//일반 사용자 게시글만 반영하여 메인 페이지로
			requestBoardList(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/mainpage.jsp");
			rd.forward(request, response);
		} else if (command.equals("/NoticeBoardListAciton.userdo")) { //공지용 게시판만 반영하여 메인 페이지로
			requestNoticeList(request);
			
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/mainpage.jsp");
			rd.forward(request, response); 
			
		} else if(command.equals("/AllBoardListAction.userdo")) { //모든 게시판 보여주기
			requestBoardList(request);
			requestNoticeList(request);
			
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/mainpage.jsp");
			rd.forward(request, response); 
		} else if (command.equals("/userBoardView.userdo")) {  //글 상세 페이지 출
			System.out.println(request.getParameter("BID"));
			requestBoardView(request);
			RequestDispatcher rd = request.getRequestDispatcher("./board/boardview.jsp");
			rd.forward(request, response);
		}
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
		
		Board board = new Board();
		
		board.setId(multi.getParameter("id"));
		System.out.println(multi.getParameter("name"));
		board.setTitle(multi.getParameter("subject"));
		board.setRegdate(LocalDateTime.now());
		board.setUpddate(null);
		board.setImage(multi.getFilesystemName("fileName"));
		board.setContent(multi.getParameter("content"));	
		board.setGoohit(0);
		board.setHit(0);
		board.setFirstadd(multi.getParameter("add1"));
		board.setSecondadd(multi.getParameter("add2"));
			
		dao.insertBoard(board);			
		
		
	}
	//일반 게시글 보여주기
	public void requestBoardList(HttpServletRequest request){
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Board> boardlist = new ArrayList<Board>();
		
	  	int pageNum=1;
		int limit=LISTCOUNT;
		
		if(request.getParameter("pageNum")!=null)
			pageNum=Integer.parseInt(request.getParameter("pageNum"));
		
		int total_record = dao.getListCount(null); // 총 게시글 갯수
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
		int limit=NOTICECOUNT;
		String name = "'admin'";
		
		if(request.getParameter("pageNum_notice")!=null)
			pageNum=Integer.parseInt(request.getParameter("pageNum_notice"));
		
		int total_record = dao.getListCount(name); // 총 게시글 갯수
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
		System.out.println(BID);
		
		
		Board board = new Board();
		board = dao.getBoardByNum(BID);		
		
		request.setAttribute("BID", BID);		 
   		request.setAttribute("board", board);   									
	}
}
