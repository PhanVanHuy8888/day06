package phanvanhuy.vn.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
	public static Connection getMSSQLConnection() throws ClassNotFoundException, SQLException {
		String driverName = "com.mysql.cj.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://localhost:3306/lab04JspServlerJDBC";
        String user = "root";
        String pass = "123456";
        Class.forName(driverName);
        Connection myConn = DriverManager.getConnection(jdbcUrl, user, pass);
        return myConn;
	}
	
	public static void closeQuietly(Connection conn) {
		try {
			conn.close();
		}catch(Exception e) {
		}
	}
	
	public static void rollbackQuietly(Connection conn) {
		try {
			conn.rollback();
		}catch(Exception e) {
		}
	}
}
