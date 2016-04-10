package utilities.dbconnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConnector {
    
    private static String url;
    private static String dbName;
    private static String driver;
    private static String userName;
    private static String pass;
    private static long exeTime; 
	
    public MySQLConnector(String url, String dbName, String driver, String userName, String pass){
        this.url = url;             //"jdbc:mysql://localhost:3306/"; 
        this.dbName = dbName;       //"CkeckUp";
        this.driver = driver;       //"com.mysql.jdbc.Driver"; 
        this.userName = userName;   //"root"; 
        this.pass = pass;           //"root";
    }
    
    public static Connection getConnection(){	 
        Connection conn = null;
	try {
            Class.forName(driver).newInstance();
            return DriverManager.getConnection(url + dbName, userName, pass);
	} catch (Exception e) {
            e.printStackTrace();
	}
	return conn; 
    }
	
    public static void executeStatement(String statement){
        Connection conn = getConnection();
	ResultSet res = null;
	try {
            Statement st = conn.createStatement();
            st.execute(statement);
            conn.close();
	} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DB error");
	}
    }
    
    public static ResultSet executeQuery(String query)
    {
            ResultSet result = null;
            Connection conn = MySQLConnector.getConnection();
        try {
        	long start = System.currentTimeMillis();
            Statement st = conn.createStatement();
            result = st.executeQuery(query);
            exeTime = System.currentTimeMillis() - start;
            System.out.println(exeTime);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public void updateIsolationLevel(String isoLevel){
        String isolationQuery = "SET SESSION TRANSACTION ISOLATION LEVEL " + isoLevel;
        try{
            PreparedStatement pstmt;
            try(Connection conn = MySQLConnector.getConnection()){
                pstmt = conn.prepareStatement(isolationQuery);
                pstmt.executeUpdate();

                pstmt = conn.prepareStatement("SELECT @@tx_isolation");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()){
                    System.out.println(rs.getString("@@tx_isolation"));
                }
            }
            pstmt.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public long getExeTime()
    {
    	return this.exeTime;
    }
}
