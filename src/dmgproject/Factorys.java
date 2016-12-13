/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmgproject;
import java.util.ArrayList;

/**
 *
 * @author Markus
 */
public interface Factorys{
    
    //returns the refactored Array List 
    //This List should be used in createMongo
    ArrayList <ArrayList> getDatabase();
    
    //Always used when user selects a table. This function provides the List of tables which can get joined (embedded)
    //in the second combo Box
    ArrayList <String> checkForJoins(String table);
    
    //refactors the Database by embedding the second arg in the first one. 
    void refactoring (int chosenItemNr);
    
    
    
}
