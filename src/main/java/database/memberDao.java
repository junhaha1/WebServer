package database;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.format.*;

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
	
	public void lasttimeUpdate(String id) {
		this.con = DBconfig.makeConnection();
		String sql = "UPDATE MEMBER SET LASTDATE = ? WHERE ID=?";
		PreparedStatement stmt = null;
		
		int code = 0;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			stmt.setString(2, id);
			
			System.out.println(stmt);
			
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
	
	public int updateMemberInfo(Member member, String beforeid) { //���� ������Ʈ
		this.con = DBconfig.makeConnection();
		String sql = "UPDATE MEMBER SET ID=?, PASSWORD=?, EMAIL =?, NAME =?, MBTI=? WHERE ID=?";
		PreparedStatement stmt = null;
		
		int code = 0;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, member.getId());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getEmail());
			stmt.setString(4, member.getName());
			stmt.setString(5, member.getMbti());
			stmt.setString(6, beforeid);
			
			System.out.println(stmt);
			
			stmt.executeUpdate();
			code = 1; //����
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
	
	public void autoDeletMemberById(String id) { //�ڵ����� ��� Ż�� �����ִ� �޼ҵ� 
		this.con = DBconfig.makeConnection();
		PreparedStatement stmt = null;
		
		LocalDateTime time = LocalDateTime.now().plusDays(1);
		String date = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		String sql = "CREATE EVENT IF NOT EXISTS autoDelete"+id
				+ " ON SCHEDULE"
				+ " AT ?"
				+ " ON COMPLETION NOT PRESERVE"
				+ "  COMMENT 'match auto delete User'"
				+ " DO"
				+ "  DELETE FROM webproject.MEMBER"
				+ "  WHERE ID = ? AND DELDATE<=NOW();";
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1,  date);
			stmt.setString(2, id);
			
			stmt.execute();
			
			updateDelDate(id, time);
			
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
	
	private void updateDelDate(String id, LocalDateTime time) {
		
		this.con = DBconfig.makeConnection();
		String sql = "UPDATE MEMBER SET DELDATE = ?, DELCODE = 1 WHERE ID=?";
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setTimestamp(1, Timestamp.valueOf(time));
			stmt.setString(2, id);
			
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
	
	public int checkMemberById(String id) {
		this.con = DBconfig.makeConnection();
		PreparedStatement stmt = null;
		String sql = "SELECT COUNT(*) FROM MEMBER WHERE ID = ?";
		int count = 0;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			
			rs = stmt.executeQuery();
			if(rs.next())
				count = rs.getInt(1);
			return count;
			
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
				member.setRegisterDateTime(rs.getTimestamp("REGDATE").toLocalDateTime());
				member.setMbti(rs.getString("MBTI"));
				if(rs.getTimestamp("LASTDATE") != null)
					member.setRegisterDateTime(rs.getTimestamp("LASTDATE").toLocalDateTime());
				member.setActivity(rs.getInt("ACTIVITY"));
				if (rs.getTimestamp("DELDATE") != null)
					member.setDeleteDateTime(rs.getTimestamp("DELDATE").toLocalDateTime());
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
	public String checkMemberPw(String id) { //ȸ�� Ż�� �ÿ� ��й�ȣ �´��� Ȯ�� ����
		this.con = DBconfig.makeConnection();
		PreparedStatement stmt = null;
		String sql = "SELECT PASSWORD FROM MEMBER WHERE ID = ?";
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()) { //��й�ȣ �������ֱ�
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
				System.out.println("ȸ�� Ż�� ����");
			
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
		String sql = "SELECT COUNT(*) FROM MEMBER " + where;
		
		try {
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			System.out.println(stmt);
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
		String sql = "SELECT * FROM MEMBER ";
		String limit = " LIMIT ?,?";
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql + where + limit);
			stmt.setInt(1, (pageNumber - 1) * count );
			stmt.setInt(2, count);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getString("ID"));
				member.setEmail(rs.getString("EMAIL"));
				member.setName(rs.getString("NAME"));
				member.setRegisterDateTime(null);
				member.setRegisterDateTime(rs.getTimestamp("REGDATE").toLocalDateTime());
				member.setMbti(rs.getString("MBTI"));
				if(rs.getTimestamp("LASTDATE") != null) 
					member.setLastDateTime(rs.getTimestamp("LASTDATE").toLocalDateTime());
					
				member.setActivity(rs.getInt("ACTIVITY"));
				
				if (rs.getInt("DELCODE") == 1)
					member.setDeleteDateTime(rs.getTimestamp("DELDATE").toLocalDateTime());
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
		int code = -1; // -1�� �α׾ƿ� ����, 1�� �α׾ƿ� ����
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			
			if(stmt.executeUpdate() > 0)
				code = 1;
			else
				System.out.println("�α׾ƿ� ����");
			
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
