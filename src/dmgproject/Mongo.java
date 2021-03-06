/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmgproject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import DBConnections.*;
import java.util.ArrayList;
import Rows.*;

/**
 *
 * @author Markus
 */
public class Mongo {
    
    DB db;
    private ArrayList <Row> table;
    private Row row;
    private String query;
    private Metadata meta;
    
    private int type;
    private String key;
    private String value;
    
    private String comma;
    
    private String queryWithType;
    
    public Mongo(){
        
    }
    
    public void createMongo(ArrayList <ArrayList> dataBase){
       
        
        MongoConnection mongo = new MongoConnection(((Metadata)((Row)dataBase.get(0).get(0)).getValue(0)).getDbName());
        db = mongo.getMongoConnection();
       
       
        
        for(int i1 = 0; i1 < dataBase.size(); i1++){
            table = dataBase.get(i1);
         
            for(int i2 = 0; i2 < table.size(); i2++){
                query = "";
                row = (Row)table.get(i2);
                String collectionName = ((Metadata)row.getValue(0)).getValue(1).toString();
                //function
                setQuery((Row)row);
                //DB einträge
                try{

                    DBCollection coll = db.getCollection(collectionName);

                    DBObject dbObject = (DBObject) JSON.parse(query);

                    coll.insert(dbObject);
                }
                catch (Exception e){
                   
                }               
               
                
            }
           
        }
        
        
       
                
        
    }
    public DB getDB(){
        return db;
    }
    private void setQuery(Row row){     
        query += "{";
        comma = "";
        queryWithType = "";
        for(int i3 = 1; i3 < row.size(); i3++){            
            meta = (Metadata)row.getValue(0);
            key = meta.getValue(i3+1).toString();
            type = (int)meta.getType(i3-1);
            value = row.getValue(i3).toString();

            if(i3 > 2 && key.equals(meta.getValue(2).toString())){
                query += "}, {";
                comma = "";
            }
            if(type == 12 || type == 1){
                query += comma+" "+key+" : '"+value+"' ";
                queryWithType += comma+" "+key+" : '"+value+"' "+type; 
            }
            else if(type == -5 || type == 2|| type ==3|| type ==4|| type ==5|| type ==6|| type ==7|| type ==8){
                query += comma+" "+key+" : "+value+" ";
                queryWithType += comma+" "+key+" : '"+value+"' "+type; 
            }
            else if(type == 99&&((Row)row.getValue(i3)).size()!=0){  
               query += comma+"  "+key+" : [";
               setQuery((Row)row.getValue(i3));
               query += "]";
            }
            comma = ",";
                                 
                    
                     
        }
        query += "}";        
       
    }
    
}
