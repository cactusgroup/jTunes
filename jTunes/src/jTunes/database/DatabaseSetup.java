package jTunes.database;

import jTunes.Resources;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
/*
 * The DatabaseSetup class forms the connection with the database. It generates the 
 * database from the .sql file, creates the connection with the database, 
 * and closes the connection. 
*/
public class DatabaseSetup  {
 
    private static final String SETUP_URL = "jdbc:mysql://localhost:3306?autoReconnect=true&useSSL=false";  // the URL needed to setup the database
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/finalAssignment?autoReconnect=true&useSSL=false";  // the database URL
    private static final String USER = "root";  // username to log in to the database
    private static final String PASSWORD = "";  // password to log in to the database
    
    // returns a connection object that is connected to the database
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD); // the DriverManager connects to the database
        } catch(SQLException e) {
            System.out.println("Database missing, generating new database...");
            generateDatabase(); // if database is missing, it will generate the database first
            return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD); // connects with the database after it was generated
        }
    }
    
    // closes the connection to the database
    public static void closeConnection(Connection c) throws SQLException {
        c.close();
    }
    
    // generates the database, if it hasn't been found, using a stored .sql file
    private static void generateDatabase() {
        String s = new String();
        StringBuffer sb = new StringBuffer();  // buffers 
        
        DatabaseSetup.class.getClassLoader().getResource(Resources.sqlFile);  // loads the .sql file
        try (BufferedReader br = new BufferedReader(new FileReader(new File("src/resources/sql/finalAssignment.sql")))) { // tries reading the file
            while((s = br.readLine()) != null) {  // reads line by line
                sb.append(s); // adds the read string to the buffer
            }
        } 
        catch (FileNotFoundException e) {
        	// if can't be found, tries looking for the file and read it
        	try(BufferedReader br = new BufferedReader(new InputStreamReader(DatabaseSetup.class.getResourceAsStream("/resources/sql/finalAssignment.sql")))){
        		while((s = br.readLine()) != null) {
                    sb.append(s);
                }
        	}
        	
        	catch (IOException ex) {
        		System.out.println("SQL file not found.");
        		System.out.println("Database generation failed.");
            
        		System.out.println("*** Error: "+e.toString());
        		System.out.println("*** ");
        		System.out.println("*** Stack Trace:");
        		e.printStackTrace();
        		return;
        	} 
        	
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
            Connection c = DriverManager.getConnection(SETUP_URL, USER, PASSWORD); // tries to get a connection to setup the database
            Statement st = c.createStatement();  // creates a statement object to execute the SQL statements
 
            for(int i = 0; i < inst.length; i++) {  
                /* we ensure that we do not execute
                 * either empty statements or SQL comments */
                if(!inst[i].trim().equals("") && !inst[i].contains("-- ")) { // trims the SQL statement properly
                    st.executeUpdate(inst[i]); // executes the SQL statement and updates the database
                    System.out.println(">>"+inst[i]);
                }
            }
            closeConnection(c); // closes the connection used to setup the database
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