package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import database.BoardDao;
import database.memberDao;
import model.Board;
import model.Coment;

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
		} else if(command.equals("/insertComent.userdo")) {
			System.out.println("ID: " + request.getParameter("ID"));
			System.out.println("BID: " + request.getParameter("BID"));
			System.out.println("coment: " + request.getParameter("coment"));
			System.out.println("type: " + request.getParameter("type"));
			
			String type = request.getParameter("type");
			
			requestInsertComent(request);
			
			RequestDispatcher rd = null;
			if(type.equals("notice")) {
				rd = request.getRequestDispatcher("/userNoticeView.userdo?type="+type);
			}else if(type.equals("common")){
				rd = request.getRequestDispatcher("/userBoardView.userdo?type="+type);
			}else {
				rd = request.getRequestDispatcher("/AllBoardListAction.userdo");
			}
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
	//댓글 등록하기
	public void requestInsertComent(HttpServletRequest request) { 
		BoardDao dao = BoardDao.getInstance();
		int BID = Integer.parseInt(request.getParameter("BID"));
		String ID = request.getParameter("ID");
		String coment = request.getParameter("coment");
		String PID = null; //후에 추가할 대댓글을 위한 부모 아이디
		
		dao.insertComent(BID, ID, coment, PID);
		
	}
	
	//댓글 목록 가져오기
	public void requestComentList(HttpServletRequest request) {
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Coment> comentlist = new ArrayList<Coment>();
		
		comentlist = dao.getComentList(Integer.parseInt(request.getParameter("BID")));
   
   		request.setAttribute("comentlist", comentlist);
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
		int limit = NOTICECOUNT;
		String type = request.getParameter("type");

		if(type != null && type.equals("notice")){
			limit += 10;
			request.setAttribute("type", "notice");
			System.out.println("type = " + type + "   limit: " + limit);
		}
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
		//System.out.println(BID);
		
		
		Board board = new Board();
		board = dao.getBoardByNum(BID);		
		
		request.setAttribute("BID", BID);		 
   		request.setAttribute("board", board);   									
	}
}
