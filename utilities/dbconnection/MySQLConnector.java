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

    public static void executeUpdate( String query ) {
        Statement stmt = null;
        try {
            stmt = MySQLConnector.getConnection().createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public static void lockTable() {

        String prequery = "LOCK TABLE hpq_hh WRITE;";
        PreparedStatement lockStatement;
        try {
            lockStatement = MySQLConnector.getConnection().prepareStatement(prequery);
            lockStatement.setQueryTimeout(20);
            lockStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unlockTable() {

        String prequery = "UNLOCK TABLES;";
        PreparedStatement unlockStatement;
        try {
            unlockStatement = MySQLConnector.getConnection().prepareStatement(prequery);
            unlockStatement.setQueryTimeout(20);
            unlockStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public boolean isTableLocked() {

        String query = "SHOW OPEN TABLES WHERE `Table` LIKE 'hpq_hh' AND In_use > 0;";

        try {
            PreparedStatement statement;
            statement = MySQLConnector.getConnection().prepareStatement(query);
            statement.setQueryTimeout(20);

            ResultSet tablecheck = statement.executeQuery();

            tablecheck.next();
            System.out.println("abot dito sirs");
            if(tablecheck.getString("In_use").equalsIgnoreCase("1"))
                return true;
            else return false;

        } catch (SQLException ex) {
            System.out.println("check table error");
        }
        return false;
    }
    
    public long getExeTime()
    {
    	return this.exeTime;
    }
}
