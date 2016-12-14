/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmgproject;

import java.util.ArrayList;
import Rows.Row;
 
/**
 *
 * @author Markus
 */
public class Factory  implements Factorys {
    
    ArrayList <ArrayList> dataBase;
    ArrayList <String> tablesToEmbed;
    
    ArrayList <Object> tempTable;
    
    
    //Lists for regaining the information which table was chosen and which coloum used to embedd
    ArrayList <Integer> tableWhichJoinsNrs;
    ArrayList <Integer> coloumWhichJoinsNrs;
    ArrayList <Integer> tableToJoinNrs;
    ArrayList <Integer> coloumToJoinsNrs;
    
    
    
    
    public Factory(ArrayList <ArrayList> dataBase){
        this.dataBase = dataBase;
    }

    @Override
    public ArrayList<ArrayList> getDatabase() {
        return dataBase;
    }
    ArrayList <String> coloumWhichJoins;
    ArrayList <String> coloumToJoin;
    @Override
    public ArrayList<String> checkForJoins(String table) {
        
          tableWhichJoinsNrs = new ArrayList <Integer>();
           coloumWhichJoinsNrs = new ArrayList <Integer>();
           tableToJoinNrs = new ArrayList <Integer>();
           coloumToJoinsNrs = new ArrayList <Integer>();
            
            
            
        ArrayList <String> tablesToJoin = new ArrayList <String>();
        int tableWhichJoinsNr = searchTableNr(table);
        //über die Spalten der zu joinenden tabell iterieren.
        //Die Spalten mit Datentyp 199 müssen noch ausgeschlossen werden aus den Iterationen, da sie nur in gewissen linien vorkommen können
        for(int coloumWhichJoinsNr = 1; coloumWhichJoinsNr < ((Row)dataBase.get(tableWhichJoinsNr).get(1)).size(); ++coloumWhichJoinsNr){
            coloumWhichJoins = searchColoum(tableWhichJoinsNr, coloumWhichJoinsNr);
            // über alle spalten aller tabellen itriren
            for(int tableToJoinNr = 0; tableToJoinNr < dataBase.size(); ++ tableToJoinNr){
                
                for(int coloumToJoinNr = 1; coloumToJoinNr < ((Row)dataBase.get(tableToJoinNr).get(1)).size(); ++coloumToJoinNr){
                     
                    coloumToJoin = searchColoum(tableToJoinNr, coloumToJoinNr);
                    
                    if(coloumWhichJoins.containsAll(coloumToJoin) && tableWhichJoinsNr != tableToJoinNr){
                       
                        tablesToJoin.add(
                                ((Row)dataBase.get(tableToJoinNr).get(0)).getMetadata().getValue(1).toString()+" ("+
                                ((Row)dataBase.get(tableToJoinNr).get(0)).getMetadata().getValue(coloumToJoinNr).toString()+")");
                        
                        tableWhichJoinsNrs.add(tableWhichJoinsNr);
                        coloumWhichJoinsNrs.add(coloumWhichJoinsNr);
                        tableToJoinNrs.add(tableToJoinNr);
                        coloumToJoinsNrs.add(coloumToJoinNr);
                        
                    }
                }
            }
        }
        return tablesToJoin;
    }

    
    
    
    @Override
    public void refactoring(int chosenItemNr) {
        int tableWhichJoinsNr = tableWhichJoinsNrs.get(chosenItemNr);
        int coloumWhichJoinsNr = coloumWhichJoinsNrs.get(chosenItemNr);
        int tableToJoinNr = tableToJoinNrs.get(chosenItemNr);
        int coloumToJoinNr = coloumToJoinsNrs.get(chosenItemNr);
        
        System.out.println(chosenItemNr+"   "+tableToJoinNr);
        
        String valueToCompare1;
        String valueToCompare2;
        
        Row rowToInsert;
       // System.out.println("-----------------------------------------------");
        for(int i1 = 0; i1 < dataBase.get(tableWhichJoinsNr).size(); ++i1){
            
            rowToInsert = new Row();
            // System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
            valueToCompare1 = (((Row)dataBase.get(tableWhichJoinsNr).get(i1)).getValue(coloumWhichJoinsNr)).toString();
            boolean conc = false;
            for(int i2 = 0; i2 < dataBase.get(tableToJoinNr).size(); ++i2){
                valueToCompare2 = (((Row)dataBase.get(tableToJoinNr).get(i2)).getValue(coloumToJoinNr)).toString();
                //wenn mehrere zeilen von table to join einer Zeile in table which joins angehängt werden.
                System.out.println(valueToCompare1+"            "+valueToCompare2);
                if(valueToCompare1.equals(valueToCompare2)){
                  
                    rowToInsert.concatenateMeta((Row)dataBase.get(tableToJoinNr).get(i2));
                   
                    
                    rowToInsert.concatenateRow((Row)dataBase.get(tableToJoinNr).get(i2));
                    System.out.println("Conatenation ********************ConatenationConatenationConatenationConatenationConatenationConatenationConatenationConatenationConatenationConatenationConatenationConatenationConatenationConatenationConatenationConatenationConatenation");
                    conc = true;
                }
            }
            String TableName = ((Row)dataBase.get(tableToJoinNr).get(0)).getMetadata().getValue(1).toString();
           // System.out.println("2222222222222222222222222222222         "+rowToInsert.getValue(1)+"           555555555555555555555555555555555555");
            
            ((Row)dataBase.get(tableWhichJoinsNr).get(i1)).addValue(rowToInsert);
            //if(i1 == 0){
            ((Row)dataBase.get(tableWhichJoinsNr).get(i1)).getMetadata().addMeta(TableName);
            ((Row)dataBase.get(tableWhichJoinsNr).get(i1)).getMetadata().addType(99);
            
            
        }
       // System.out.println(((Row)dataBase.get(2).get(1)).getValue(5));
        
        
    }
    
    
    
    
    ArrayList<String> coloum;
    public ArrayList<String> searchColoum(int tableNr, int ColoumNr){
        coloum = new ArrayList<String>();
        tempTable = dataBase.get(tableNr);
        for(int i = 0; i < tempTable.size(); ++i){
            coloum.add((((Row)tempTable.get(i)).getValue(ColoumNr)).toString()); 
        }
        
        return coloum;
    }
    
   
    public int searchTableNr(String tableName){
        for(int i1 = 0; i1 < dataBase.size(); ++i1){
            tempTable = dataBase.get(i1);
            //if table names matches to arg
            if (((Row)tempTable.get(0)).getMetadata().getTableName().equals(tableName)){
                return i1;
            }
        }
        return -1;
    }
    
}
