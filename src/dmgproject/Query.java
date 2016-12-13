/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmgproject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
/**
 *
 * @author Markus
 */

public class Query {
    String queryResult;
    DB db;
    String query;
    String collectionName;
    public Query(Mongo mongo){
        db = mongo.getDB();
        
    }
    public String executeQuery(){
        collectionName = "professoren";
     
        queryResult = "";
        DBCollection coll = db.getCollection(collectionName);

       
        BasicDBObject fields = new BasicDBObject();
        fields.put("PersNr", 2125);


        DBCursor cursor = coll.find(fields);
        
        while(cursor.hasNext()){
            queryResult+= cursor.next();
        }
        
            System.out.println(queryResult);
        
        return queryResult;
    }
}

