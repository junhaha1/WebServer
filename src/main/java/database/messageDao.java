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
	
	public int getReciListCount(String name) { //��� ���� ���� ����
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
	
	public int getSendListCount(String name) { //��� �߽� ���� ����
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
	
	public int getRDListCount(String name, int check) { //���� �� ���� �޼���
		this.con = DBconfig.makeConnection();
		
		String sql = null;
		
		if(check == 0) //�� ���� �޼���
			sql = "SELECT COUNT(*) from MESSAGE WHERE ID = ? AND RDCHECK = 0";
		else // ���� �޼���
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
	
	public ArrayList<Message> selectMessage(int pageNum, int limit, String name, int option) { //�޼��� ��� �б�
		this.con = DBconfig.makeConnection();
		String sql = "SELECT * from MESSAGE"; 
		String where = "";
		String check = " ORDER BY SENDDATE DESC LIMIT ?,?";
		
		if(option == 0) //���� ���� �о����
			where = " WHERE RID = ?";
		else if (option == 1) //���� ���� �о����
			where = " WHERE UID = ?";
		else if (option == 2) //�� ���� ���� �о����
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
	
	public Message getMessage(int MID) { //�޼��� �� �б�
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
				
				if(msg.getRDCHECK() == 0) // ������ ���¶��
					this.updateRDcheck(MID); //�������� ��ȯ
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
	
	public boolean insertMessage(Message msg) { //�޼��� ������
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
	private boolean updateRDcheck(int MID) { //�������� ��ȯ
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
				System.out.println("���� ����");
			
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
}