package database;

import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.ZoneId;

import model.*;


public class messageDao {
	private static messageDao instance;
	private Connection con;
	private ResultSet rs;
	
	private messageDao() {
	}

	public static messageDao getInstance() {
		if (instance == null)
			instance = new messageDao();
		return instance;
	}	
	
	public int getReciListCount(String name) { //모든 수신 메일 갯수
		this.con = DBconfig.makeConnection();
		String sql = "SELECT COUNT(*) from MESSAGE WHERE RID = ?";
		
		PreparedStatement stmt = null;
		int count = 0;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, name);
			
			rs = stmt.executeQuery();
			if(rs.next())
				count = rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
		return count;
	}
	
	public int getSendListCount(String name) { //모든 발신 메일 갯수
		this.con = DBconfig.makeConnection();
		String sql = "SELECT COUNT(*) from MESSAGE WHERE ID = ?";
		
		PreparedStatement stmt = null;
		int count = 0;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, name);
			
			rs = stmt.executeQuery();
			if(rs.next())
				count = rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
		return count;
	}
	
	public int getRDListCount(String name, int check) { //읽음 안 읽은 메세지
		this.con = DBconfig.makeConnection();
		
		String sql = null;
		
		if(check == 0) //안 읽은 메세지
			sql = "SELECT COUNT(*) from MESSAGE WHERE ID = ? AND RDCHECK = 0";
		else // 읽은 메세지
			sql = "SELECT COUNT(*) from MESSAGE WHERE ID = ? AND RDCHECK = 1";
		
		PreparedStatement stmt = null;
		int count = 0;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, name);
			
			rs = stmt.executeQuery();
			if(rs.next())
				count = rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
		return count;
	}
	
	public ArrayList<Message> selectMessage(int pageNum, int limit, String name, int option) { //메세지 목록 읽기
		this.con = DBconfig.makeConnection();
		String sql = "SELECT * from MESSAGE"; 
		String where = "";
		String check = " ORDER BY SENDDATE DESC LIMIT ?,?";
		
		if(option == 0) //받은 메일 읽어오기
			where = " WHERE RID = ?";
		else if (option == 1) //보낸 메일 읽어오기
			where = " WHERE UID = ?";
		else if (option == 2) //안 읽은 메일 읽어오기
			where = " WHERE RID = ? AND RDCHECK = 0";
		
		PreparedStatement stmt = null;
		ArrayList<Message> list = new ArrayList<Message>();
		sql = sql + where + check;
		
		int start = (pageNum - 1) * limit;
		int end = limit;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setInt(2, start);
			stmt.setInt(3, end);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Message msg = new Message();
				msg.setMID(rs.getInt("MID"));
				msg.setUID(rs.getString("UID"));
				msg.setRID(rs.getString("RID"));
				msg.setTITLE(rs.getString("TITLE"));
				msg.setCONTENT(rs.getString("CONTENT"));
				msg.setSENDDATE(rs.getTimestamp("SENDDATE").toLocalDateTime());
				msg.setRDCHECK(rs.getInt("RDCHECK"));
				list.add(msg);
			}
			
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
		return null;
	}
	
	public Message getMessage(int MID) { //메세지 상세 읽기
		this.con = DBconfig.makeConnection();
		String sql = "SELECT * from MESSAGE WHERE MID = ?"; 
		
		PreparedStatement stmt = null;
		Message msg = null;
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, MID);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				msg = new Message();
				msg.setMID(rs.getInt("MID"));
				msg.setUID(rs.getString("UID"));
				msg.setRID(rs.getString("RID"));
				msg.setTITLE(rs.getString("TITLE"));
				msg.setCONTENT(rs.getString("CONTENT"));
				msg.setSENDDATE(rs.getTimestamp("SENDDATE").toLocalDateTime());
				msg.setRDCHECK(rs.getInt("RDCHECK"));
				
				if(msg.getRDCHECK() == 0) // 안읽음 상태라면
					this.updateRDcheck(MID); //읽음으로 변환
			}
			
			return msg;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
		return msg;
	}
	
	public boolean insertMessage(Message msg) { //메세지 보내기
		this.con = DBconfig.makeConnection();
		String sql = "INSERT INTO MESSAGE(UID, RID, TITLE, CONTENT, SENDDATE) VALUE(?, ?, ?, ?, ?)";
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, msg.getUID());
			stmt.setString(2, msg.getRID());
			stmt.setString(3, msg.getTITLE());
			stmt.setString(4, msg.getCONTENT());
			stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
			
			stmt.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
		return false;
	}
	private boolean updateRDcheck(int MID) { //읽음으로 변환
		this.con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean check = false;

		String sql = "UPDATE MESSAGE SET RDCHECK = 1 WHERE MID = ?";

		try {
			con =  DBconfig.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, MID);
			
			if(pstmt.executeUpdate() > 0)
				check = true;
			else
				System.out.println("읽음 실패");
			
			return check;
		} catch (Exception ex) {
			System.out.println("updateRDcheck() error : " + ex);
		} finally {
			try {
				if (rs != null) 
					rs.close();							
				if (pstmt != null) 
					pstmt.close();				
				if (con != null) 
					con.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}		
		}
		return check;
	}
	
	//////////////////////////////////////////////////////////////////////////////
	public void deleteUserGoodhit(int BID, String ID) {
		this.con = DBconfig.makeConnection();
		PreparedStatement stmt = null;
		
		String sql = "DELETE FROM USERGOOD WHERE BID = ? AND ID = ?";
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, BID);
			stmt.setString(2, ID);
			
			if(stmt.executeUpdate() > 0)
				System.out.println("좋아요 삭제 성공");
			else
				System.out.println("좋아요 삭제 실패");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
	}
	
	public void reduceBoardState(int BID, String mbti) {
		this.con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String[] count = new String[mbti.length()];
		
		for(int i = 0; i < count.length; i++)
			count[i] = mbti.charAt(i) + "count";
		
		//boolean check = false;
		
		//updateHit(num); 조회수 늘리기
		String sql = "UPDATE BOARDSTATE SET " + 
					count[0] + "=" + count[0] + " - 1," +
					count[1] + "=" + count[1] + " - 1," +
					count[2] + "=" + count[2] + " - 1," +
					count[3] + "=" + count[3] + " - 1 " +
					"WHERE BID = ?";
		try {
			con =  DBconfig.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, BID);
			if(pstmt.executeUpdate() > 0) {
				this.reduceGoodhit(BID); //Board에 좋아요 수 줄이기
				//check = true;
			}
				
			else
				System.out.println("좋아요 줄이기 실패");
			
		} catch (Exception ex) {
			System.out.println("Board State error : " + ex);
		} finally {
			try {
				if (rs != null) 
					rs.close();							
				if (pstmt != null) 
					pstmt.close();				
				if (con != null) 
					con.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}		
		}
	}
	
	public int deleteBoardByBid(String bid) {
		this.con = DBconfig.makeConnection();
		PreparedStatement stmt = null;
		
		String sql = "DELETE FROM BOARD WHERE BID = ?";
		int code = 0;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, bid);
			
			if(stmt.executeUpdate() > 0)
				code = 1;
			else
				System.out.println("글 삭제 실패");
			
			return code;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
		return 0;
	}
	
	public int deleteComentByCnum(String cnum) {
		this.con = DBconfig.makeConnection();
		PreparedStatement stmt = null;
		
		String sql = "DELETE FROM COMENT WHERE CNUM = ?";
		int code = 0;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cnum);
			
			if(stmt.executeUpdate() > 0)
				code = 1;
			else
				System.out.println("댓글 삭제 실패");
			
			return code;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
		
		return 0;
	}
	
	public ArrayList<Coment> getComentList(int BID){
		this.con = DBconfig.makeConnection();
		String sql = "SELECT * from COMENT WHERE BID = ?"; 
		PreparedStatement stmt = null;
		
		ArrayList<Coment> list = new ArrayList<Coment>();
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, BID);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Coment coment = new Coment();
				coment.setCNUM(rs.getInt("CNUM"));
				coment.setBID(rs.getInt("BID"));
				coment.setId(rs.getString("ID"));
				coment.setContent(rs.getString("content"));
				coment.setRegdate(rs.getTimestamp("REGDATE").toLocalDateTime());
				coment.setPid(rs.getString("parentID"));
				list.add(coment);
			}
			
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
		return null;
	}
	
	public ArrayList<Coment> getUserComentList(int pageNum, int limit, String name){
		this.con = DBconfig.makeConnection();
		
		String sql = "SELECT COMENT.CNUM, COMENT.BID, COMENT.ID, BOARD.TITLE, COMENT.CONTENT, COMENT.REGDATE "
				+ "from COMENT left join BOARD on COMENT.BID = BOARD.BID where COMENT.ID = ? order by COMENT.CNUM DESC limit ?, ?"; 
		
		PreparedStatement stmt = null;
		
		System.out.println("name = " + name);
		int start = (pageNum - 1) * limit;
		int end = limit;
		
		ArrayList<Coment> list = new ArrayList<Coment>();
		
		try {
			System.out.println("댓글 SQL 설정");
			stmt = con.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setInt(2, start);
			stmt.setInt(3, end);

			rs = stmt.executeQuery();
			
			while(rs.next()) {
				System.out.println("댓글 불러오기");
				Coment coment = new Coment();
				coment.setCNUM(rs.getInt("CNUM"));
				coment.setBID(rs.getInt("BID"));
				coment.setId(rs.getString("ID"));
				coment.setBtitle(rs.getString("TITLE"));
				coment.setContent(rs.getString("CONTENT"));
				coment.setRegdate(rs.getTimestamp("REGDATE").toLocalDateTime());
				
				list.add(coment);
			}
			System.out.println("리스트 ");
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
		return null;
	}
	
	public int getSearchCount(String table, int code, String name, String type) { //해당 글 조건의 글 갯수 가져오기
		this.con = DBconfig.makeConnection();
		String sql = "SELECT COUNT(*) from " + table;
		String where = "";
		String str = name.trim();
		if(code == 0) //글쓴이
			where = " WHERE ID = '" + str + "'";
		else if(code == 1) {
			if(type.equals("user")) //공지용 제외
				where = " WHERE ID != 'admin' AND TITLE like '%" + str + "%'";
			else //공지용에서만
				where = " WHERE ID = 'admin' AND TITLE like '%" + str + "%'";
		}
		else if(code == 2) { //내용에서
			if(type.equals("user")) //공지용 제외
				where = " WHERE ID != 'admin' AND CONTENT like '%" + str + "%'";
			else //공지용에서만
				where = " WHERE ID = 'admin' AND CONTENT like '%" + str + "%'";
		}
		
		sql = sql + where;
		
		PreparedStatement stmt = null;
		int count = 0;
		
		try {
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next())
				count = rs.getInt(1);
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
		return count;
	}
	
	public ArrayList<Board> getSearchList(int pageNum, int limit, int code, String name, String type) { //검색한 글 목록 불러오기
		this.con = DBconfig.makeConnection();
		String sql = "SELECT * from BOARD";
		String where = "";
		String str = name.trim();
		
		if(code == 0) //글쓴이
			where = " WHERE ID = '" + str + "'";
		else if(code == 1) {
			if(type.equals("user")) //공지용 제외
				where = " WHERE ID != 'admin' AND TITLE like '%" + str + "%'";
			else //공지용에서만
				where = " WHERE ID = 'admin' AND TITLE like '%" + str + "%'";
		}
		else if(code == 2) { //내용에서
			if(type.equals("user")) //공지용 제외
				where = " WHERE ID != 'admin' AND CONTENT like '%" + str + "%'";
			else //공지용에서만
				where = " WHERE ID = 'admin' AND CONTENT like '%" + str + "%'";
		}
		else if(code == 3) { //mbti
			str = str.toUpperCase();
			if(type.equals("user")) //공지용 제외
				where = " WHERE ID != 'admin' AND MBTI = '" + str + "'";
			else //공지용에서만
				where = " WHERE ID = 'admin' AND MBTI = '" + str + "'";
		}
			
		String check = " ORDER BY BID DESC LIMIT ?,?"; 
		
		sql = sql + where + check;
		
		PreparedStatement stmt = null;
	
		int start = (pageNum - 1) * limit;
		int end = limit;
		
		
		ArrayList<Board> list = new ArrayList<Board>();
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, start);
			stmt.setInt(2, end);
			
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Board board = new Board();
				board.setBID(rs.getInt("BID"));
				board.setId(rs.getString("id"));
				board.setMbti(rs.getString("mbti"));
				board.setTitle(rs.getString("title"));
				board.setRegdate(rs.getTimestamp("regdate").toLocalDateTime());
				board.setUpddate(null);
				board.setImage(null);
				board.setContent("content");
				board.setGoohit(rs.getInt("goodhit"));
				board.setHit(rs.getInt("hit"));
				board.setPname(rs.getString("pname"));
				board.setPaddress(rs.getString("paddress"));
				board.setLatclick(rs.getString("latclick"));
				board.setLngclick(rs.getString("lngclick"));
				
				list.add(board);
			}
			
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
		return null;
	}

	public ArrayList<Board> getBoardList(int pageNum, int limit, String name) {
		this.con = DBconfig.makeConnection();
		String sql = "SELECT * from BOARD WHERE ID != 'admin' ORDER BY BID DESC LIMIT ?,?"; // 공지글을 제외하기(default)
		
		if(name != null)	
			sql = "SELECT * from BOARD WHERE ID = '" + name + "' ORDER BY BID DESC LIMIT ?,?";
		
		PreparedStatement stmt = null;
	
		int start = (pageNum - 1) * limit;
		int end = limit;
		
		
		ArrayList<Board> list = new ArrayList<Board>();
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, start);
			stmt.setInt(2, end);
			
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Board board = new Board();
				board.setBID(rs.getInt("BID"));
				board.setId(rs.getString("id"));
				board.setMbti(rs.getString("mbti"));
				board.setTitle(rs.getString("title"));
				board.setRegdate(rs.getTimestamp("regdate").toLocalDateTime());
				board.setUpddate(null);
				board.setImage(null);
				board.setContent("content");
				board.setGoohit(rs.getInt("goodhit"));
				board.setHit(rs.getInt("hit"));
				board.setPname(rs.getString("pname"));
				board.setPaddress(rs.getString("paddress"));
				board.setLatclick(rs.getString("latclick"));
				board.setLngclick(rs.getString("lngclick"));
				
				list.add(board);
			}
			
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if (rs != null) 
					rs.close();							
				if (stmt != null) 
					stmt.close();				
				if (con != null) 
					con.close();												
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}	
		}
		return null;
	}
	
	private boolean updateGoodhit(int BID) { //좋아요 갯수 업데이트
		this.con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean check = false;

		//updateHit(num); 조회수 늘리기
		String sql = "UPDATE BOARD SET GOODHIT = GOODHIT + 1 WHERE BID=?";

		try {
			con =  DBconfig.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, BID);
			if(pstmt.executeUpdate() > 0)
				check = true;
			else
				System.out.println("좋아요 갱신 실패");
			
			return check;
		} catch (Exception ex) {
			System.out.println("좋아요 갱신 실패 error : " + ex);
		} finally {
			try {
				if (rs != null) 
					rs.close();							
				if (pstmt != null) 
					pstmt.close();				
				if (con != null) 
					con.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}		
		}
		return check;
	}
	private boolean reduceGoodhit(int BID) { //좋아요 갯수 업데이트
		this.con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean check = false;

		//updateHit(num); 조회수 늘리기
		String sql = "UPDATE BOARD SET GOODHIT = GOODHIT - 1 WHERE BID=?";

		try {
			con =  DBconfig.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, BID);
			if(pstmt.executeUpdate() > 0)
				check = true;
			else
				System.out.println("좋아요 줄이기 갱신 실패");
			
			return check;
		} catch (Exception ex) {
			System.out.println("좋아요 줄이기 갱신 실패 error : " + ex);
		} finally {
			try {
				if (rs != null) 
					rs.close();							
				if (pstmt != null) 
					pstmt.close();				
				if (con != null) 
					con.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}		
		}
		return check;
	}
	
	private boolean updateHit(String BID) { //조회수 업데이트
		this.con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean check = false;

		//updateHit(num); 조회수 늘리기
		String sql = "UPDATE BOARD SET HIT = HIT + 1 WHERE BID=?";

		try {
			con =  DBconfig.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, BID);
			if(pstmt.executeUpdate() > 0)
				check = true;
			else
				System.out.println("조회수 갱신 실패");
			
			return check;
		} catch (Exception ex) {
			System.out.println("getBoardByNum() error : " + ex);
		} finally {
			try {
				if (rs != null) 
					rs.close();							
				if (pstmt != null) 
					pstmt.close();				
				if (con != null) 
					con.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}		
		}
		return check;
	}
	
	public Board getBoardByNum(String BID) {
		this.con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board board = null;

		
		String sql = "select * from board where BID = ? ";

		try {
			con =  DBconfig.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, BID);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				System.out.println("게시글 상세 보기 sucess");
				board = new Board();
				board.setBID(rs.getInt("BID"));
				board.setId(rs.getString("id"));
				board.setTitle(rs.getString("title"));
				board.setRegdate(rs.getTimestamp("regdate").toLocalDateTime());
				board.setUpddate(null);
				board.setImage(rs.getString("image"));
				board.setContent(rs.getString("content"));
				board.setGoohit(rs.getInt("goodhit"));
				board.setHit(rs.getInt("hit"));
				board.setPname(rs.getString("pname"));
				board.setPaddress(rs.getString("paddress"));
				board.setLatclick(rs.getString("latclick"));
				board.setLngclick(rs.getString("lngclick"));
				
				this.updateHit(BID); //조회수 갱신하는 코드
			}
			
			return board;
		} catch (Exception ex) {
			System.out.println("getBoardByNum() error : " + ex);
		} finally {
			try {
				if (rs != null) 
					rs.close();							
				if (pstmt != null) 
					pstmt.close();				
				if (con != null) 
					con.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}		
		}
		return null;
	}
}