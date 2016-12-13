/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBConnections;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

/**
 *
 * @author Markus
 */
public class MongoConnection {
    private DB db;
    public MongoConnection(String dbName){
        
        try{
	     
         // To connect to mongodb server
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        
        
         // Now connect to your databases
       
         db = mongoClient.getDB( dbName);
         System.out.println("Connect to database successfully");
         //boolean auth = db.authenticate(myUserName, myPassword);
         //System.out.println("Authentication: "+auth);
         
			
      }catch(Exception e){
         
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
      }
    }
    public DB getMongoConnection(){
        return db;
    }
}
