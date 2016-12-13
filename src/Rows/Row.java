/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rows;
import java.util.*;

/**
 *
 * @author Markus
 */
public class Row {
    
    private ArrayList <Object> row;
    public Row(){
        
        row = new ArrayList <Object>();
    }
    public Row(ArrayList <Object> row){
        
        this.row = row;
    }
    public Object getValue(int index){
        
        return row.get(index);   
    }
    public void addValue(Object val){
        row.add(val);
    }
    //meta daten aneinanderreihen.
     public void concatenateRow(Row val){
         
         for(int i = 0; i < val.size(); ++i){
             if(i ==0 && row.isEmpty()==false){
                 System.out.println(((Metadata)row.get(0)).getTableName());
                 System.out.println(val.getMetadata());   
                 ((Metadata)row.get(0)).addMetaRow(val.getMetadata());
                
                 ((Metadata)row.get(0)).addTypeRow(val.getMetadata());
             }
             else{
                 row.add(val.getValue(i));
             }
             
         }  
    }
    public int size(){
        return row.size();
    }
    
    public Metadata getMetadata(){
        return (Metadata)row.get(0);
    }
}
