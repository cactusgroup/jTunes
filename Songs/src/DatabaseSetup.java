import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
 
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

 
public class DatabaseSetup  {
 
    private static final String SETUP_URL = "jdbc:mysql://localhost:3306?autoReconnect=true&useSSL=false";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/finalassignment?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "";
 
    public static Connection getConnection() throws SQLException{
        try {
        	return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        	}
       
        catch(SQLException e) {
        	System.out.println("Database missing, generating new database...");
        	DatabaseSetup.setupDatabase();
        	System.out.println("Database generated");
        	return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        	}
    }
    
    public static void closeConnection(Connection c) throws SQLException{
    	c.close();
    }
 
    public static void setupDatabase() throws SQLException{
        String s  = new String();
        StringBuffer sb = new StringBuffer();
 
        try
        {
            FileReader fr = new FileReader(new File("./bin/finalAssignment.sql"));
 
            BufferedReader br = new BufferedReader(fr);
 
            while((s = br.readLine()) != null)
            {
                sb.append(s);
            }
            br.close();
 
            // here is our splitter ! We use ";" as a delimiter for each request
            // then we are sure to have well formed statements
            String[] inst = sb.toString().split(";");
 
            Connection c = DriverManager.getConnection(SETUP_URL, USER, PASSWORD);
            Statement st = c.createStatement();
 
            for(int i = 0; i<inst.length; i++){
                // we ensure that there is no spaces before or after the request string
                // in order to not execute empty statements
                if(!inst[i].trim().equals("") && !inst[i].contains("-- ")){
                    st.executeUpdate(inst[i]);
                    System.out.println(">>"+inst[i]);
                }
            }
           DatabaseSetup.closeConnection(c);
   
        }
        catch(Exception e)
        {
            System.out.println("*** Error : "+e.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
            e.printStackTrace();
            System.out.println("################################################");
            System.out.println(sb.toString());
        }
 
    }
}