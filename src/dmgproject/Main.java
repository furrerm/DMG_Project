/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmgproject;

import DBConnections.DBConnect;

/**
 *
 * @author Markus
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        DBConnect conn = new DBConnect();
        GUI gui = new GUI();
    }
    
}
