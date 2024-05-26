package controller;
import java.sql.*;
import java.util.ArrayList;
import model.Member;

public class memberDao {
	private Connection con;
	private ResultSet rs;
	
	public memberDao() {
		this.con = DBconfig.makeConnection();
	}
	public int allcount() {
		int count = 0;
		String sql = "SELECT COUNT(*) FROM MEMBER";
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next())
				count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	public ArrayList<Member> getList(int pageNumber, int count) {
		ArrayList<Member> list = new ArrayList<Member>();
		String sql = "SELECT * FROM MEMBER LIMIT ?,?";
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, (pageNumber - 1) * count );
			stmt.setInt(2, count);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getString("ID"));
				member.setEmail(rs.getString("EMAIL"));
				member.setName(rs.getString("NAME"));
				member.setRegisterDateTime(null);
				member.setMbti(rs.getString("MBTI"));
				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
