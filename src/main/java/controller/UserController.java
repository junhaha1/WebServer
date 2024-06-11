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
	
		if (command.equals("/BoardWriteAction.userdo")) { //�Ϲ� ����� �Խñ� �ۼ�
			requestBoardWrite(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/AllBoardListAction.userdo");
			rd.forward(request, response);
		} else if (command.equals("/BoardListAction.userdo")) {//�Ϲ� ����� �Խñ۸� �����ֱ�
			requestBoardList(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/main_common.jsp");
			rd.forward(request, response);
		} else if (command.equals("/NoticeBoardListAciton.userdo")) { //������ �Խ��Ǹ� �����ֱ�
			requestNoticeList(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/main_notice.jsp");
			rd.forward(request, response); 
		} else if(command.equals("/AllBoardListAction.userdo")) { //��� �Խ��� �����ֱ�
			requestBoardList(request);
			requestNoticeList(request);
			
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/mainpage.jsp");
			rd.forward(request, response); 
		} else if (command.equals("/userBoardView.userdo")) {  //�� �� ������ ��
			System.out.println("Common: " + request.getParameter("BID"));
			System.out.println("name: " + request.getParameter("name"));
			String type = request.getParameter("type");
			requestComentList(request); // ��� ��� ��������
			if(request.getAttribute("comentlist") == null)
				System.out.println("�ҷ����� ����");
			requestBoardView(request);
			RequestDispatcher rd;
			
			if(type != null && type.equals("common"))
				rd = request.getRequestDispatcher("./board/boardview.jsp?type="+type);
			else
				rd = request.getRequestDispatcher("./board/boardview.jsp");
			
			rd.forward(request, response);
		} else if (command.equals("/userNoticeView.userdo")) {  //���� �� ������ ��
			System.out.println("Notice: " + request.getParameter("BID"));
			String type = request.getParameter("type");
			
			requestBoardView(request);
			RequestDispatcher rd;
			if(type != null && type.equals("notice"))
				rd = request.getRequestDispatcher("./board/boardview.jsp?type="+type);
			else
				rd = request.getRequestDispatcher("./board/boardview.jsp");
			rd.forward(request, response);
		} else if(command.equals("/insertComent.userdo")) { //��� �ۼ��ϱ�
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
		} else if(command.equals("/comentBoardListAction.userdo")) { //�� ��� ��� �����ֱ�
			requestUserComentList(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/main_coment.jsp");
			rd.forward(request, response);
		} else if(command.equals("/MyBoardListAction.userdo")) { // �α����� ������ �� �Խñ۸� �ҷ�����
			requestUserBoardList(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/main_userBoard.jsp");
			rd.forward(request, response);
		} else if(command.equals("/requestUserInfo.userdo")) { // �α����� ���� ���� �ҷ�����
			requestUserInfo(request);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/userPage/memberInfo.jsp");
			rd.forward(request, response);
		} else if(command.equals("/requestUserDelete.userdo")) { // ���� ���� �����ϱ� 
			int code = requestUserDelete(request);
			request.setAttribute("code", code);
			RequestDispatcher rd = null;
			
			if(code == 0) { //Ż�� ���� �� ��� ��ȸ ����
				request.setAttribute("passwd", request.getParameter("passwd"));
				rd = request.getRequestDispatcher("/userPage/deletemember.jsp");
			}
			else if(code == 1) //Ż�� ����
				rd = request.getRequestDispatcher("/userPage/delete_confirm.jsp");
			else if(code == 2) {// ��й�ȣ�� ȸ�� ������ ��ġ���� ����. 
				request.setAttribute("passwd", request.getParameter("passwd"));
				rd = request.getRequestDispatcher("/userPage/deletemember.jsp");
			}
			rd.forward(request, response);
		} else if(command.equals("/requestBoardDelete.userdo")) { //�Խñ� ���� �׼�
			int code = requestBoardDelete(request);
			String bid = request.getParameter("bid");
			String title = request.getParameter("title");
			
			System.out.println("name: " + request.getParameter("name") + " bid: " + bid + " title: " + title);
			request.setAttribute("code", code);
			RequestDispatcher rd = null;
			
			if(code == 0 || code == 2) { //Ż�� ���� �� ��� ��ȸ ����
				rd = request.getRequestDispatcher("/board/deleteBoardForm.jsp?bid="+bid+"&&title="+title);
			}
			else if(code == 1) //�� ���� ����
				rd = request.getRequestDispatcher("/MyBoardListAction.userdo?name="+ request.getParameter("name"));
			
			rd.forward(request, response);
		} else if(command.equals("/requestComentDelete.userdo")) { //��� ���� �׼�
			int code = requestComentDelete(request);
			
			String bid = request.getParameter("BID");
			String cnum = request.getParameter("cnum");
			String name = request.getParameter("name");
			
			System.out.println("cnum: " + cnum + " bid: " + bid + " name: " + name);
			
			RequestDispatcher rd = null;

			if(bid == null) //�� ��� ��Ͽ��� ����
				rd = request.getRequestDispatcher("/comentBoardListAction.userdo?type=coment&&name="+name);
			else
				rd = request.getRequestDispatcher("/userBoardView.userdo?BID=" + bid + "&&type=common");
			
			rd.forward(request, response);
		} else if(command.equals("/requestUpdateGoodhit.userdo")) { //���ƿ� ������ �� ó��
			String BID = request.getParameter("BID");
			String ID = request.getParameter("ID");
			String type = request.getParameter("type");
			requestUpdateGoodhit(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("/userBoardView.userdo?BID="+BID+"&&name="+ID + "&&type="+type);
			rd.forward(request, response);
		} else if(command.equals("/requestDeleteGoodhit.userdo")) { //���ƿ� ������ �� ó��
			String BID = request.getParameter("BID");
			String ID = request.getParameter("ID");
			String type = request.getParameter("type");
			requestDeleteGoodhit(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("/userBoardView.userdo?BID="+BID+"&&name="+ID + "&&type="+type);
			rd.forward(request, response);
		} else if(command.equals("/SearchListAction.userdo")) { // �Խñ� �̻��ϱ�
			System.out.println("items: " + request.getParameter("items"));
			System.out.println("text: " + request.getParameter("text"));
			System.out.println("type: " + request.getParameter("type"));
			requestSearchList(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("./userPage/researchBoard_user.jsp");
			
			rd.forward(request, response);
		} else if(command.equals("/requestUserMail.userdo")) { // �� ���� �ҷ�����
			System.out.println("name: " + request.getParameter("name"));
			System.out.println("option: " + request.getParameter("option"));
			requestMailList(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("./userPage/main_message.jsp");
			
			rd.forward(request, response);
		} else if(command.equals("/userMailView.userdo")) { // ���� �󼼺��� �ҷ�����
			System.out.println("MID: " + request.getParameter("MID"));
			System.out.println("type: " + request.getParameter("type"));
			System.out.println("name: " + request.getParameter("name"));
			
			requestMailView(request);
			RequestDispatcher rd = request.getRequestDispatcher("./board/messageView.jsp");
			rd.forward(request, response);
		} else if(command.equals("/sendMail.userdo")) { // ���� �󼼺��� �ҷ�����
			System.out.println("uid: " + request.getParameter("uid"));
			System.out.println("rid: " + request.getParameter("rid"));
			System.out.println("title: " + request.getParameter("title"));
			
			boolean check = requestSendMail(request);
			RequestDispatcher rd = null;
			
			if(((String) request.getAttribute("error"))!= null && ((String) request.getAttribute("error")).equals("riderror")) { //���̵� ���� ���
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
	
	public boolean requestSendMail(HttpServletRequest request) {//���� ������
		messageDao dao = messageDao.getInstance();
		memberDao mdao = memberDao.getInstance();
		int result = mdao.checkMemberById(request.getParameter("rid"));
		
		if(result == 0) {
			request.setAttribute("error", "riderror");
			return false; //������ ���̵� ����. 
		}
		
		Message msg = new Message();
		msg.setUID(request.getParameter("uid"));
		msg.setRID(request.getParameter("rid"));
		msg.setTITLE(request.getParameter("title"));
		msg.setCONTENT(request.getParameter("content"));
		
		boolean check = dao.insertMessage(msg);
		
		return check;
	}
	
	public void requestMailView(HttpServletRequest request) { //���� �󼼳��� �ҷ�����
		messageDao dao = messageDao.getInstance();
		Message msg = null;
		
		int MID = Integer.parseInt(request.getParameter("MID"));
		msg = dao.getMessage(MID);
		request.setAttribute("msg", msg);
	}
	
	public void requestMailList(HttpServletRequest request) { //���� ��� �ҷ�����
		messageDao dao = messageDao.getInstance();
		ArrayList<Message> maillist = new ArrayList<Message>();
		
		int pageNum=1;
		int limit=LISTCOUNT;
		int option = Integer.parseInt(request.getParameter("option"));
		String name = request.getParameter("name");
		
		if(request.getParameter("pageNum")!=null)
			pageNum=Integer.parseInt(request.getParameter("pageNum"));
		
		int total_record = 0;
		
		if(option == 0) //���� ����
			total_record = dao.getReciListCount(name); // �� �Խñ� ����
		else if(option == 1)
			total_record = dao.getSendListCount(name); // �� �Խñ� ����
		
		maillist = dao.selectMessage(pageNum, limit, name, option); // �ش� �Խñ۵� ����
		
		int total_page; // ����
		
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
	
	public void requestSearchList(HttpServletRequest request) { //�˻� ��� ��������
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Board> searchlist = new ArrayList<Board>();
		
		int pageNum=1;
		int limit=LISTCOUNT;
		
		String name = request.getParameter("text");
		int code = Integer.parseInt(request.getParameter("items"));
		String type = request.getParameter("type");
		
		if(request.getParameter("pageNum")!=null)
			pageNum=Integer.parseInt(request.getParameter("pageNum"));
		
		int total_record = dao.getSearchCount("BOARD", code, name, type); // �� �Խñ� ����
		searchlist = dao.getSearchList(pageNum, limit, code, name, type); // �ش� �Խñ۵� ����
		
		int total_page; // ����
		
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
	
   //���ο� �� ����ϱ�
	public void requestBoardWrite(HttpServletRequest request){
		/*���� ���ε�*/
		String upload= request.getServletContext().getRealPath("/resources/images");		
		String encType = "utf-8";				
		int maxSize=5*1024*1024;	
		
		System.out.println(upload + " => " + request.getParameter("fileName"));
				
		MultipartRequest multi = null;
		
		//���Ͼ��ε带 ���������� ���		
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
	//��� ����ϱ�
	public void requestInsertComent(HttpServletRequest request) { 
		BoardDao dao = BoardDao.getInstance();
		int BID = Integer.parseInt(request.getParameter("BID"));
		String ID = request.getParameter("ID");
		String coment = request.getParameter("coment");
		String PID = null; //�Ŀ� �߰��� ������ ���� �θ� ���̵�
		
		dao.insertComent(BID, ID, coment, PID);
		
	}
	
	public void requestUpdateGoodhit(HttpServletRequest request) { //���ƿ� ������ �� 
		BoardDao dao = BoardDao.getInstance();
		memberDao mdao = memberDao.getInstance();
		
		int BID = Integer.parseInt(request.getParameter("BID"));
		String ID = request.getParameter("ID");
		
		if(dao.getBoardStateCount(BID) != 1) // ���̺� ���� ���
			dao.insertBoardState(BID);
		
		dao.insertUserGoodhit(BID, ID);
		
		Member member = mdao.getMemberInfo(ID);
		
		dao.updateBoardState(BID, member.getMbti());
	}
	
	public void requestDeleteGoodhit(HttpServletRequest request) { //���ƿ� ��� ������ �� 
		BoardDao dao = BoardDao.getInstance();
		memberDao mdao = memberDao.getInstance();
		
		int BID = Integer.parseInt(request.getParameter("BID"));
		String ID = request.getParameter("ID");
	
		dao.deleteUserGoodhit(BID, ID);
		
		Member member = mdao.getMemberInfo(ID);
		
		dao.reduceBoardState(BID, member.getMbti());
	}
	
	//��� ��� ��������
	public void requestComentList(HttpServletRequest request) {
		BoardDao dao = BoardDao.getInstance();
		ArrayList<Coment> comentlist = new ArrayList<Coment>();
		
		comentlist = dao.getComentList(Integer.parseInt(request.getParameter("BID")));
   
   		request.setAttribute("comentlist", comentlist);
	}
	
	//���� ���� �ҷ�����
	public void requestUserInfo(HttpServletRequest request) {
		memberDao dao = memberDao.getInstance();
		Member member = dao.getMemberInfo(request.getParameter("name"));
		
		request.setAttribute("member", member);
	}
	public int requestBoardDelete(HttpServletRequest request) { //�Խñ� �����ϱ�
		BoardDao dao = BoardDao.getInstance();
		int code = 0;
		String id = request.getParameter("name");
		String pw = request.getParameter("passwd");
		
		String pw_check = memberDao.getInstance().checkMemberPw(id);
		
		System.out.println("user id pw: "+ id + "  " + pw + " db: " + pw_check + " bid: " + request.getParameter("bid"));
		
		if(pw_check != null && pw.equals(pw_check))
			code = dao.deleteBoardByBid(request.getParameter("bid"));
		else
			return 2; //��й�ȣ�� Ʋ�ȴٴ� code��
		
		return code; //0�� Ż�� ����(��� ��ȸ�� �ȵ�) 1�� Ż�� ����
	}
	public int requestComentDelete(HttpServletRequest request) { //��� �����ϱ�
		BoardDao dao = BoardDao.getInstance();
		int code = 0;
		
		code = dao.deleteComentByCnum(request.getParameter("cnum"));
		
		return code; //0�� ��� ���� ����(��� ��ȸ�� �ȵ�) 1�� ��� ���� ����
	}
	public int requestUserDelete(HttpServletRequest request) { //���� ���� �����ϱ�
		memberDao dao = memberDao.getInstance();
		int code = 0;
		String id = request.getParameter("name");
		String pw = request.getParameter("passwd");
		
		String pw_check = dao.checkMemberPw(id);
		
		System.out.println("user id pw: "+ id + "  " + pw + " db: " + pw_check);
		
		if(pw_check != null && pw.equals(pw_check))
			code = dao.deleteMemberById(request.getParameter("name"));
		else
			return 2; //��й�ȣ�� Ʋ�ȴٴ� code��
		
		return code; //0�� Ż�� ����(��� ��ȸ�� �ȵ�) 1�� Ż�� ����
	}
	
	public void requestUserComentList(HttpServletRequest request) { //��۸� ����
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
		
		int total_page; // ����
		
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
	//�α����� ������� �Խñ۸� �����ֱ�
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
		
		int total_record = dao.getListCount("BOARD", name); // �� �Խñ� ����
		boardlist = dao.getBoardList(pageNum, limit, name); 
		
		int total_page; // ����
		
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
	
	//�Ϲ� �Խñ� �����ֱ�
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
		
		int total_record = dao.getListCount("BOARD", null); // �� �Խñ� ����
		boardlist = dao.getBoardList(pageNum, limit, null); 
		
		int total_page; // ����
		
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
		
		int total_record = dao.getListCount("BOARD", "admin"); // �� �Խñ� ����
		boardlist = dao.getBoardList(pageNum, limit, name); 
		
		int total_page; // ����
		
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
		
		
		int checkgood = dao.checkUserGood(name, BID); //���ƿ� ���� �������� -> 0�̶�� ���ƿ� ������ ���� �� 1�̶�� ���ƿ� ���� ��
		System.out.println("�̸�: " + name + " �۹�ȣ: " + BID);
		System.out.println("���ƿ� check: " + checkgood);
		
		request.setAttribute("BID", BID);		 
   		request.setAttribute("board", board); 
   		request.setAttribute("checkgood", checkgood); 
	}
}
