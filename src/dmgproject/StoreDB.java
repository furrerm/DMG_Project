/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmgproject;
import java.sql.Connection;
import DBConnections.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Rows.*;
import java.sql.DatabaseMetaData;
import java.sql.ResultSetMetaData;
import javax.sql.rowset.*;

/**
 *
 * @author Markus
 */
public class StoreDB {
    private Connection connection = null;
    private ArrayList <Object> row;
    private ArrayList <Object> types;
    private ArrayList <Object> meta;
    private ArrayList <Row> table;
    private ArrayList <ArrayList> dataBase = new ArrayList <ArrayList>();
   
    private String tableName;
   
    
    public StoreDB(Connection connection){
        
       
        this.connection = connection;
        
    }
    public ArrayList getDataBase(){
        return dataBase;
    }
    
    public void storeTables(){
                    
        ResultSet resultSet;                   
                    
        try{
            DatabaseMetaData md = connection.getMetaData();
            ResultSet set = md.getTables(null, null, "%", null);
            
            //Iteration über alle Tables
            while(set.next()){
                tableName = set.getString(3);
              
                table = new ArrayList <Row>();
                    
                    try{
                        
                    Statement st = (Statement) connection.createStatement(); 
                    resultSet = st.executeQuery("SELECT * FROM "+tableName);
                   
                     ResultSetMetaData rsmd = resultSet.getMetaData();
                    
                    
                    
                    
                    
                    
                                 
                    while(resultSet.next()){
                        boolean inColoumNr = true;
                    
                   
                        meta = new ArrayList <Object>();
                    types = new ArrayList <Object>();
                    meta.add(rsmd.getCatalogName(1));
                    meta.add(rsmd.getTableName(1));
                    
                    int i = 1;
                    
                    while(inColoumNr){
                        
                        try{
                            meta.add(rsmd.getColumnName(i));
                            types.add(rsmd.getColumnType(i));
                            
                        }
                        catch (Exception e){
                           
                            inColoumNr = false;
                        }
                                
                        ++i;
                    }

                        i = 1;
                        inColoumNr = true;
                        Object value;
                        row = new ArrayList <Object>();
                        
                        row.add(new Metadata(meta,types));
                        for (int i6 =0; i6 < ((Metadata)row.get(0)).metaSize();++i6){
                      
                                }
                        
                        
                        while(inColoumNr){
                           

                            try{
                              
                             
                               value = resultSet.getObject(i);
                                row.add(value);
                                for (int i5 =0; i5 < ((Metadata)row.get(0)).metaSize();++i5){
                                  
                                   
                                    
                                }
                                
                                
                            }
                            catch (Exception e){
                                //System.out.println(e.getMessage());
                                
                                inColoumNr = false;
                                
                                
                                
                                
                                
                                
                                
                                
                                table.add(new Row(row));
                                
                            }
                            
                            ++i;
                        }
                       
                    }
                    
                    
                    
                    
                   
                   
                    
                    dataBase.add(table);
                    
                    }
                    catch (SQLException ex) {
                            ex.printStackTrace();	   
                    } 
                }//End of Iteration over Tables            
            }
            catch(Exception e){
                //System.out.println(e.getMessage());
            }    
                    
                    
                    
                    
    }
    public ArrayList getdataBase(){
        return dataBase;
    }
    private ArrayList <String> tableNames;
    
    public ArrayList getTableNames(){
        tableNames = new ArrayList <String>();
        for(int i = 0; i < dataBase.size(); ++i){
            tableNames.add( ((Row)dataBase.get(i).get(0)).getMetadata().getTableName());
            
        }
        
        return tableNames;
    }
}
