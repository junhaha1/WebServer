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
		String sql = "INSERT INTO BOARD(ID, TITLE, REGDATE, UPDATETIME, IMAGE, CONTENT, GOODHIT, HIT, FIRSTADD, SECONDADD) VALUE(?, ? ,? ,? ,?, ?, ?, ? ,? ,?)";
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, board.getId());
			stmt.setString(2, board.getTitle());
			stmt.setTimestamp(3, Timestamp.valueOf(board.getRegdate()));
			stmt.setTimestamp(4, null);
			stmt.setString(5, board.getImage());
			stmt.setString(6, board.getContent());
			stmt.setInt(7, board.getGoohit());
			stmt.setInt(8, board.getHit());
			stmt.setString(9, board.getFirstadd());
			stmt.setString(10, board.getSecondadd());
			
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
				board.setTitle(rs.getString("title"));
				board.setRegdate(rs.getTimestamp("regdate").toLocalDateTime());
				board.setUpddate(null);
				board.setImage(null);
				board.setContent("content");
				board.setGoohit(rs.getInt("goodhit"));
				board.setHit(rs.getInt("hit"));
				board.setFirstadd(null);
				board.setSecondadd(null);
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
	private boolean updateHit(String BID) {
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
				board.setFirstadd(null);
				board.setSecondadd(null);
				
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