/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rows;

import java.util.ArrayList;

/**
 *
 * @author Markus
 */
public class Metadata {
    private ArrayList <Object> meta;
    private ArrayList <Object> types;
    
    public Metadata(ArrayList <Object> meta, ArrayList <Object> types){
        
        this.meta = meta;
        this.types = types;
    }
    public Object getValue(int index){
        
        return meta.get(index);       
    }
    
    public Object getType(int index){
        
        return types.get(index);      
    }
    public String getDbName(){
        
        return meta.get(0).toString();
    }
    public String getTableName(){
        
        return meta.get(1).toString();
    }
    public void addMeta(Object val){
        meta.add(val);
    }
    public void addType(Object val){
        types.add(val);
    }
    public void addMetaRow(Metadata val){
        
        int size = val.metaSize();
        for(int i = 2; i < size; ++i){
           
            meta.add(val.getValue(i));
        }
        
    }
    public void addTypeRow(Metadata val){
         int size = val.typesSize();
        for(int i = 0; i < size; ++i){
            types.add(val.getType(i));
        }
        
    }
    public int metaSize(){
        return meta.size();
    }
    public int typesSize(){
        return types.size();
    }
    
}
