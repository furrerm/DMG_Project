package DBConnections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Collection;

import java.util.Arrays;
import java.util.List;
/**
 *
 * @author Markus
 */
public class DBConnect {
    
	private Connection connection = null;
        
        final private String serverName = "localhost";
        
        final private String userName;
	final private String password;
        
        String dbName;
        
        
	public DBConnect(String userName, String password) {
                this.userName = userName;
                this.password = password;
                
		 String driverName = "org.gjt.mm.mysql.Driver";
		 
		   
		 	try {
				Class.forName(driverName);
				System.out.println("treiber geladen");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
                                System.out.println("in catch");
				e.printStackTrace();
			}

		    
		    

                    
		    
	}
        public Connection getConnection(){
		return connection;
	}
        public void setConnection(String dbName){
            
            this.dbName = dbName;
            String url = "jdbc:mysql://" + serverName + "/" + dbName; 


            try {
		connection = DriverManager.getConnection(url, userName, password);
		System.out.println("connection to db "+dbName+" established");
            } catch (SQLException e) {
				
		e.printStackTrace();
            }            
        }
}
