package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import controller.memberDao;
import database.BoardDao;
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
			RequestDispatcher rd = request.getRequestDispatcher("./board/list.jsp");
			rd.forward(request, response);
		} else if (command.equals("/BoardWriteAction.do")) {//새로운 글 등록
			requestBoardWrite(request);
			RequestDispatcher rd = request.getRequestDispatcher("/BoardListAction.do");
			rd.forward(request, response);						
		}
	}
   //새로운 글 등록하기
	public void requestBoardWrite(HttpServletRequest request){
		BoardDao dao = BoardDao.getInstance();		
		
		Board board = new Board();
		board.setId(request.getParameter("name"));
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
}
