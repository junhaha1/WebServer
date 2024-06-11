package database;

import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.ZoneId;

import model.Board;
import model.Coment;
import model.Member;


public class BoardDao {
	private static BoardDao instance;
	private Connection con;
	private ResultSet rs;
	
	private BoardDao() {
	}

	public static BoardDao getInstance() {
		if (instance == null)
			instance = new BoardDao();
		return instance;
	}	
	
	public int getBoardStateCount(int BID) {
		this.con = DBconfig.makeConnection();
		String sql = "SELECT COUNT(*) from BOARDSTATE where BID = ?";
		
		PreparedStatement stmt = null;
		int count = 0;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, BID);
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
	
	public ArrayList<Board> getMbtiResearchBoard(String[] mbti){
		this.con = DBconfig.makeConnection();
		
		String sql = "SELECT BID, "; 
		String where = " as mbtiset FROM BOARDSTATE ORDER BY mbtiset desc LIMIT 5";
		
		String mbti_s = "";
		
		ArrayList<Integer> BIDs = new ArrayList<Integer>();
		
		for(int i = 0; i < mbti.length; i++) {
			if(!mbti[i].equals("")) {
				mbti_s += mbti[i]+"count";
			}
			if(i+1 < mbti.length && !mbti_s.equals("") && !mbti[i+1].equals("") ) {
				mbti_s += "+";
			}
		}
		
		sql = sql + mbti_s + where;
		
		System.out.println(sql);
		
		PreparedStatement stmt = null;
		
		ArrayList<Board> list = new ArrayList<Board>();
		
		try {
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				BIDs.add(rs.getInt("BID"));
			}
			
			list = getSortBoardList(BIDs);
			
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
	public ArrayList<Board> getSortBoard(String mbti){ //해당 사용자 mbti 상위 5개
		this.con = DBconfig.makeConnection();
		
		String[] count = new String[mbti.length()];
		ArrayList<Integer> BIDs = new ArrayList<Integer>();
		
		for(int i = 0; i < count.length; i++)
			count[i] = mbti.charAt(i) + "count";
		
		String sql = "SELECT BID, " + count[0] + "+" +  count[1] +  "+" + count[2] + "+" + count[3] + " as mbtiset FROM BOARDSTATE ORDER BY mbtiset desc LIMIT 5";
		
		System.out.println(sql);
		
		PreparedStatement stmt = null;
		
		ArrayList<Board> list = new ArrayList<Board>();
		
		try {
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				BIDs.add(rs.getInt("BID"));
			}
			
			list = getSortBoardList(BIDs);
			
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
	
	private ArrayList<Board> getSortBoardList(ArrayList<Integer> BIDs){
		this.con = DBconfig.makeConnection();
		String sql = "SELECT * FROM BOARD WHERE BID";
		String instart = " IN (";
		String inend = ")";
		
		for(int i = 0; i < BIDs.size(); i++) {
			instart += BIDs.get(i);
			if(i + 1 < BIDs.size())
				instart += ",";
			System.out.println( BIDs.get(i));
		}
		
		sql = sql + instart + inend;
		
		PreparedStatement stmt = null;
		
		ArrayList<Board> list = new ArrayList<Board>();
	
		try {
			stmt = con.prepareStatement(sql);
			
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
	public int checkUserGood(String name, String BID) {
		this.con = DBconfig.makeConnection();
		String sql = "SELECT COUNT(*) from USERGOOD WHERE BID = ? AND ID = ?";
		PreparedStatement stmt = null;
		
		int count = 0;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, BID);
			stmt.setString(2, name);
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
	
	public int getListCount(String table, String name) {
		this.con = DBconfig.makeConnection();
		String sql = "SELECT COUNT(*) from " + table;
		
		if(name != null)
			sql += " WHERE ID = '" + name + "'";
		else
			sql += " WHERE ID != 'admin'";
		
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
	
	public void insertBoardState(int BID) { //BoardState 테이블에 게시글 등록
		this.con = DBconfig.makeConnection();
		String sql = "INSERT INTO BOARDSTATE(BID) VALUE(?)";
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, BID);
			stmt.executeUpdate();
			
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
	}
	
	public void insertUserGoodhit(int BID, String ID) { //userGood 테이블에 해당 ID와 글ID 등록
		this.con = DBconfig.makeConnection();
		String sql = "INSERT INTO USERGOOD VALUE(?, ?)";
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, BID);
			stmt.setString(2, ID);
			
			stmt.executeUpdate();
			
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
	}
	public void updateBoardState(int BID, String mbti) { //boardstate 테이블에 해당 글에 좋아요 누른 mbti 값 설정
		this.con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String[] count = new String[mbti.length()];
		
		for(int i = 0; i < count.length; i++)
			count[i] = mbti.charAt(i) + "count";
		
		boolean check = false;
		
		//updateHit(num); 조회수 늘리기
		String sql = "UPDATE BOARDSTATE SET " + 
					count[0] + "=" + count[0] + " + 1," +
					count[1] + "=" + count[1] + " + 1," +
					count[2] + "=" + count[2] + " + 1," +
					count[3] + "=" + count[3] + " + 1 " +
					"WHERE BID = ?";

		try {
			con =  DBconfig.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, BID);
			if(pstmt.executeUpdate() > 0) {
				this.updateGoodhit(BID); //Board에 좋아요 수 늘리기
				check = true;
			}
				
			else
				System.out.println("좋아요 갱신 실패");
			
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
	
	public void insertComent(int BID, String ID, String coment, String PID) {
		this.con = DBconfig.makeConnection();
		String sql = "INSERT INTO COMENT(BID, ID, content, REGDATE, parentID) VALUE(?, ?, ?, ?, ?)";
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, BID);
			stmt.setString(2, ID);
			stmt.setString(3, coment);
			stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
			stmt.setString(5, PID);
			
			stmt.executeUpdate();
			
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
	}
	
	public void insertBoard(Board board) {
		this.con = DBconfig.makeConnection();
		String sql = "INSERT INTO BOARD(ID, MBTI, TITLE, REGDATE, UPDATETIME, IMAGE, CONTENT, GOODHIT, HIT, PNAME, PADDRESS, LATCLICK, LNGCLICK) VALUE(?, ?, ? ,? ,? ,?, ?, ?, ? , ?, ?, ? ,?)";
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, board.getId());
			stmt.setString(2, board.getMbti());
			stmt.setString(3, board.getTitle());
			stmt.setTimestamp(4, Timestamp.valueOf(board.getRegdate()));
			stmt.setTimestamp(5, null);
			stmt.setString(6, board.getImage());
			stmt.setString(7, board.getContent());
			stmt.setInt(8, board.getGoohit());
			stmt.setInt(9, board.getHit());
			stmt.setString(10, board.getPname());
			stmt.setString(11, board.getPaddress());
			stmt.setString(12, board.getLatclick());
			stmt.setString(13, board.getLngclick());
			
			stmt.executeUpdate();
			
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
	}
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