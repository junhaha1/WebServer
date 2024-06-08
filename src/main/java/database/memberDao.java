package database;
import java.sql.*;
import java.util.ArrayList;

import model.Member;

public class memberDao {
	private static memberDao instance;
	private Connection con;
	private ResultSet rs;
	
	private memberDao() {
	}

	public static memberDao getInstance() {
		if (instance == null)
			instance = new memberDao();
		return instance;
	}
	
	public Member getMemberInfo(String id) {
		this.con = DBconfig.makeConnection();
		PreparedStatement stmt = null;
		String sql = "SELECT * FROM MEMBER WHERE ID = ?";
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				Member member = new Member();
				member.setId(rs.getString("ID"));
				member.setEmail(rs.getString("EMAIL"));
				member.setName(rs.getString("NAME"));
				member.setRegisterDateTime(null);//다시 수정할 코드
				member.setMbti(rs.getString("MBTI"));
				member.setLastDateTime(null);//다시 수정할 코드
				member.setActivity(rs.getInt("ACTIVITY"));
				
				return member;
			}
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
		
		return null;
	}
	public String checkMemberPw(String id) { //회원 탈퇴 시에 비밀번호 맞는지 확인 쿼리
		this.con = DBconfig.makeConnection();
		PreparedStatement stmt = null;
		String sql = "SELECT PASSWORD FROM MEMBER WHERE ID = ?";
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()) { //비밀번호 리턴해주기
				System.out.println("db pw check: " + rs.getString("PASSWORD"));
				return rs.getString("PASSWORD");
			}
			
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
		
		return null;
	}
	
	public int deleteMemberById(String id) {
		this.con = DBconfig.makeConnection();
		PreparedStatement stmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE ID = ?";
		int code = 0;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			
			if(stmt.executeUpdate() > 0)
				code = 1;
			else
				System.out.println("회원 탈퇴 실패");
			
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
	
	public int allcount(String where) {
		this.con = DBconfig.makeConnection();
		int count = 0;
		PreparedStatement stmt = null;
		String sql = "SELECT COUNT(*) FROM MEMBER" + where;
		
		try {
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next())
				count = rs.getInt(1);
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
		
		return count;
	}
	public ArrayList<Member> getList(int pageNumber, int count, String where) {
		this.con = DBconfig.makeConnection();
		ArrayList<Member> list = new ArrayList<Member>();
		String sql = "SELECT * FROM MEMBER";
		String limit = " LIMIT ?,?";
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql + where + limit);
			stmt.setInt(1, (pageNumber - 1) * count );
			stmt.setInt(2, count);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getString("ID"));
				member.setEmail(rs.getString("EMAIL"));
				member.setName(rs.getString("NAME"));
				member.setRegisterDateTime(null);//다시 수정할 코드
				member.setMbti(rs.getString("MBTI"));
				member.setLastDateTime(null);//다시 수정할 코드
				member.setActivity(rs.getInt("ACTIVITY"));
				list.add(member);
			}
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
		return list;
	}
	
	public int logoutUser(String id) {
		this.con = DBconfig.makeConnection();
		String sql = "UPDATE MEMBER SET ACTIVITY = 0 WHERE ID=?";
		PreparedStatement stmt = null;
		int code = -1; // -1은 로그아웃 실패, 1은 로그아웃 성공
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			
			if(stmt.executeUpdate() > 0)
				code = 1;
			else
				System.out.println("로그아웃 실패");
			
			return code;
			
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
		return code;
		
	}
	
	public String loginUser(String id, String pw) {
		this.con = DBconfig.makeConnection();
		String sql = "SELECT ID from MEMBER WHERE ID = ? AND PASSWORD = ?";
		PreparedStatement stmt = null;
		String Id = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, pw);
			rs = stmt.executeQuery();
			if(rs.next()){
				Id = rs.getString(1);
				sql = "UPDATE MEMBER SET ACTIVITY = 1 WHERE ID=?";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, id);
				stmt.executeUpdate();
			}
			return Id;
			
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
		
		return Id;
	}
}
