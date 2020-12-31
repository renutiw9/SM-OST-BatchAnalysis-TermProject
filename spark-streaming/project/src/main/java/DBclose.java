
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
/*
 * Close the connection
 */


public class DBclose {
    public static void getclose(ResultSet rs ,Statement st,Connection c){
    	try{
    		if(rs!=null)rs.close();
    		if(st!=null)st.close();
    		if(c!=null)c.close();
    		
    	}catch(Exception e){
    		
    	}
    }
}
