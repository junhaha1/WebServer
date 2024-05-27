package database;

import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDateTime;

import controller.DBconfig;
import model.Board;
import model.Member;


public class BoardDao {
	private static BoardDao instance;
	private Connection con;
	private ResultSet rs;
	
	private BoardDao() {
		this.con = DBconfig.makeConnection();
	}

	public static BoardDao getInstance() {
		if (instance == null)
			instance = new BoardDao();
		return instance;
	}	
	
	public void insertBoard(Board board) {
		String sql = "INSERT INTO BOARD(ID, TITLE, REGDATE, UPDATETIME, IMAGE, CONTENT, GOODHIT, HIT, FIRSTADD, SECONDADD) VALUE(?, ? ,? ,? ,?, ?, ?, ? ,? ,?)";
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			
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
		}
	}
}