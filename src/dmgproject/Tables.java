/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmgproject;

/**
 *
 * @author Markus
 */


import DBConnections.DBConnect;
import java.sql.Connection;

public class Tables {
    DBConnect dbConnect = new DBConnect();
    private Connection connection = null;
    
    public Tables(){
        
    }
    public DBConnect getDbConnect(){
        return dbConnect;
    }
}
