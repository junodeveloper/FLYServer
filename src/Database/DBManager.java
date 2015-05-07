package Database;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
	
	Connection con;
	PreparedStatement pstmt;
	Statement stmt;
	
	public void readyDB() {
		try {
			//Class.forName("org.gjt.mm.mysql.Driver");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBConfig.scName, "", "");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dbTest() {
		readyDB();
		try {
			String sql = "insert ";
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeDB();
	}
	
	public ArrayList<ImageInfo> getImageList() {
		ArrayList<ImageInfo> ret = new ArrayList<ImageInfo>();
		try {
			readyDB();
			String sql = "select * from " + DBConfig.tableName;
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				double latitude = rs.getInt(1) / 1000000.0;
				double longitude = rs.getInt(2) / 1000000.0;
				int radius = rs.getInt(3);
				ImageInfo newInfo = new ImageInfo(latitude, longitude, radius);
				ret.add(newInfo);
			}
		} catch (Exception ex) { ex.printStackTrace(); }
		finally { closeDB(); }
		return ret;
	}
	
	public void closeDB() {
		try {
			if(con!=null) con.close();
			if(pstmt!=null) pstmt.close();
			if(stmt!=null) stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
