package database;
import java.sql.*;

public class DBconfig {
	public static Connection makeConnection() {
		String url = "jdbc:mysql://localhost:3306/webproject";
		
		String id = "webroot";
		String password = "webroot";
		Connection con = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("����̹� ���� ����");
			con = DriverManager.getConnection(url, id, password);
			//System.out.println("�����ͺ��̽� ���� ����");
		} catch(ClassNotFoundException e) {
			System.out.println("����̹��� ã�� �� �����ϴ�.");
		} catch(SQLException e) {
			System.out.println("���ῡ �����Ͽ����ϴ�.");
		}
		return con;
	}
}
