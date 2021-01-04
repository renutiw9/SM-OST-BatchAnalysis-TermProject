package cat.mhyark.uni.tfg;
import org.mariadb.jdbc.Driver;
import org.mariadb.jdbc.MariaDbConnection;	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
	
	
	/**
	 * Created by mhyark on 01/01/21.
	 */
	public class DBManager {
	
	    private Connection connect() {
	        // SQLite connection string
	        //String url = "jdbc:sqlite:/home/mhyark/Documents/uni/tfg/sqlite/db/test.db";
	        //String url ="jdbc:mysql:/home/mhyark/Documents/uni/tfg/sqlite/db/test.db";
	        String url = "jdbc:mariadb://localhost:3306/flowinspector";
	        String username = "NOPE";               // Change
	        String password = "NOPE";               // Change
	        Properties p = new Properties();
	        p.put("user", username);
	        p.put("password", password);
	
	
	        try {
	            Class.forName("org.mariadb.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            System.out.println("\n\nWhere is the MariaDB JDBC Driver?\n\n\n");
	            e.printStackTrace();
	        }
	
	        try {
	            DriverManager.getDriver(url);
	        } catch(SQLException e) {
	            System.out.println(e.getMessage());
	        }
	
	        Driver d = new Driver();
	        Connection conn = null;
	        //MariaDbConnection conn = null;
	        try {
	            //conn = DriverManager.getConnection(url, username, password);
	            conn = d.connect(url, p);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return conn;
	    }
	
	    /**
	     * Insert a new row into the warehouses table
	     *
	     */
	    public void insertNetworkSecurity (int no, int time, int source, int destination, int protocol, double length, double info) {
	        String sql = "INSERT INTO NetworkSecurity(no,time,source,destination,protocol,length,info) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	        try (Connection conn = this.connect(); // TANCAR CONNEXIo!
	            PreparedStatement pstmt = conn.prepareStatement(sql)) {
	
	            pstmt.setInt(1, no);
	            pstmt.setInt(2, time);
	            pstmt.setInt(3, source);
	            pstmt.setInt(4, destination);
	            pstmt.setInt(5, protocol);
	            pstmt.setDouble(6, length);
	            pstmt.setDouble(7, info);
	            pstmt.setString(8, application);
	            pstmt.setString(9, prediction);
	            pstmt.setString(10, currentTime);
	            pstmt.setString(11, predictedLabel);
	            pstmt.executeUpdate();
	            conn.close();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	
	    public void insertBatchPerformance (int num_traces, long time_total, String currentTime) {
	        String sql = "INSERT INTO performance_metrics(num_traces, time_total, received) VALUES(?,?,?)";
	
	        try (Connection conn = this.connect(); // TANCAR CONNEXIo!
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            System.out.println("\n\nSAVING BATCH?\n\n\n");
	
	            pstmt.setInt(1, num_traces);
	            pstmt.setLong(2, time_total);
	            pstmt.setString(3, currentTime);
	            pstmt.executeUpdate();
	            conn.close();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	}

