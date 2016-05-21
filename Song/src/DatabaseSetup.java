import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

 
public class DatabaseSetup  {
 
    private static final String SETUP_URL = "jdbc:mysql://localhost:3306?autoReconnect=true&useSSL=false";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/finalAssignment?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "";
 
    public static Connection getConnection() throws SQLException{
        try {
            return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch(SQLException e) {
            System.out.println("Database missing, generating new database...");
            generateDatabase();
            return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        }
    }
    
    private static void closeConnection(Connection c) throws SQLException {
        c.close();
    }
 
    private static void generateDatabase() {
        String s = new String();
        StringBuffer sb = new StringBuffer();
 
        try (BufferedReader br = new BufferedReader(new FileReader(new File("./Library/finalAssignment.sql")))) {
            while((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (FileNotFoundException e) {
            System.out.println("SQL file not found.");
            System.out.println("Database generation failed.");
            
            System.out.println("*** Error: "+e.toString());
            System.out.println("*** ");
            System.out.println("*** Stack Trace:");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            System.out.println("An I/O error occured when reading the SQL file.");
            System.out.println("Database generation failed.");
            
            System.out.println("*** Error: "+e.toString());
            System.out.println("*** ");
            System.out.println("*** Stack Trace:");
            e.printStackTrace();
            return;
        }
 
        // here is our splitter! We use ";" as a delimiter for each request,
        // so we are sure to have well-formed statements.
        String[] inst = sb.toString().split(";");
        
        try {
            Connection c = DriverManager.getConnection(SETUP_URL, USER, PASSWORD);
            Statement st = c.createStatement();
 
            for(int i = 0; i<inst.length; i++){
                /* we ensure that we do not execute
                 * either empty statements or SQL comments */
                if(!inst[i].trim().equals("") && !inst[i].contains("-- ")){
                    st.executeUpdate(inst[i]);
                    System.out.println(">>"+inst[i]);
                }
            }
            closeConnection(c);
            System.out.println("Database generated");
        } catch(Exception e) {
            System.out.println("*** Error: "+e.toString());
            System.out.println("*** ");
            System.out.println("*** Stack Trace:");
            e.printStackTrace();
            System.out.println("################################################");
            System.out.println(sb.toString());
        }
    }
}