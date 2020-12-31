
import java.sql.*;
//Create a database connection class DBConnect
public class  DBConnect {
   // Define the parameters needed to connect to the database
   static String driverName = "com.mysql.jdbc.Driver";
   static String dbURL = "jdbc:mysql://127.0.0.1:3306/db_schoolweb?useSSL=false&serverTimezone=UTC";
   static String userName = "root";
   static String userPwd = "123456";
   private static Connection Conn=null;
   public static Connection getConn(){
      try {
         // Load the driver
         Class.forName(driverName);
         System.out.println("Load the driver");
         // Connect to the database
         Conn = DriverManager.getConnection(dbURL, userName, userPwd);
         System.out.println("Connect to the database");
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      return Conn;
   }
}
