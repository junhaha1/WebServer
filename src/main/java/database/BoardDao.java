package database;

import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDateTime;

import model.Board;
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
	
	public int getListCount(String name) {
		this.con = DBconfig.makeConnection();
		String sql = "SELECT COUNT(*) from BOARD";
		
		if(name != null)
			sql += " WHERE ID = " + name;
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

	public ArrayList<Board> getBoardList(int pageNum, int limit, String name) {
		this.con = DBconfig.makeConnection();
		String sql = "SELECT * from BOARD WHERE ID != 'admin' ORDER BY BID DESC LIMIT ?,?"; // 공지글을 제외하기(default)
		
		if(name != null)	
			sql = "SELECT * from BOARD WHERE ID = " + name + " ORDER BY BID DESC LIMIT ?,?";
		
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
				board.setRegdate(null);
				board.setUpddate(null);
				board.setImage(null);
				board.setContent("content");
				board.setGoohit(rs.getInt("goodhit"));
				board.setHit(rs.getInt("hit"));
				board.setFirstadd(null);
				board.setSecondadd(null);
				board.setContent(sql);
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
}