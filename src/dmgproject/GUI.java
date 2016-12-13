/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmgproject;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import javax.swing.JLayeredPane;
import java.awt.PointerInfo;
import java.awt.Point;
import java.awt.MouseInfo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.util.*;
import Rows.*;
import com.mongodb.DB;
import DBConnections.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

/**
 *
 * @author Markus
 */

public class GUI extends JFrame implements MouseListener{
    
    JLayeredPane laypane;
    
    private int x;
    private int y;
    
    
    Dimension d1;
    
    int border_x;
    int border_y;
    
    Connection connection;
    private StoreDB store;
    Factory factory;
    Mongo mongo;
    
    public GUI(){
        //gradient
        //
        d1 = new Dimension(700,350);
        
        this.getContentPane().setPreferredSize(d1);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.white);
                    
        this.setLocationRelativeTo(null);
        
        
       // this.addMouseListener(this);
        this.setResizable(false);
        
        //Text Panel
        JPanel textPanel = new JPanel();
        textPanel.setPreferredSize(new Dimension(500,200));
        textPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        textPanel.setBackground(Color.WHITE);
        this.add(textPanel,BorderLayout.PAGE_START);
        
        
        
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1,3));
        //buttonsPanel.setPreferredSize(new Dimension(500,200));
        buttonsPanel.setBackground(Color.WHITE);
        this.add(buttonsPanel,BorderLayout.CENTER);
        
        
        
        
        JLabel textContainer = new JLabel("");
        textPanel.add(textContainer);
        
        
        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.setLayout(new FlowLayout());
        buttonPanel1.setBackground(Color.GREEN);
        buttonPanel1.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        buttonsPanel.add(buttonPanel1);
    
        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new FlowLayout());
        buttonPanel2.setBackground(Color.BLACK);
        buttonPanel2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonsPanel.add(buttonPanel2);
         
        
        
  
        JPanel buttonPanel3 = new JPanel();
        buttonPanel3.setLayout(new FlowLayout());
        buttonPanel3.setBackground(Color.BLACK);
        buttonPanel3.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        buttonsPanel.add(buttonPanel3);
        
        
        DBConnect dbConnect = new DBConnect();
        dbConnect.setConnection("");
        connection = dbConnect.getConnection(); 
        
        
        final JComboBox comboBox = new JComboBox();
        comboBox.setSize(new Dimension(200,130));      
        comboBox.setSize(new Dimension(50,30));
        comboBox.setLocation(new Point(200,200));
        comboBox.setEditable(true);
        comboBox.setVisible(true);
        
        try{
            DatabaseMetaData md = connection.getMetaData();
            ResultSet set = md.getCatalogs();
            
            while(set.next()){
                comboBox.addItem(set.getString("TABLE_CAT"));
            }            
        }
        catch(Exception e){
            
        }

        final JComboBox docComboBox = new JComboBox();
        final JComboBox embDocComboBox = new JComboBox();
  
        JButton storeButton = new JButton();
        storeButton.setText("Store Db as objects");
        storeButton.setVisible(true);
        storeButton.setPreferredSize(new Dimension(160,30));
        storeButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
         
            textContainer.setText("Database "+comboBox.getSelectedItem().toString()+" is selected");
            //connection & DB Name mitgeben
            dbConnect.setConnection(comboBox.getSelectedItem().toString());
            store = new StoreDB(dbConnect.getConnection());
            
            store.storeTables();
            
            for(int i = 0; i < store.getTableNames().size(); ++i){
                docComboBox.addItem(store.getTableNames().get(i));
                
            }
            
            buttonPanel1.setBackground(Color.BLACK);
            buttonPanel2.setBackground(Color.GREEN);
            
            
          }
        });
        buttonPanel1.add(storeButton);
        
        buttonPanel1.add(comboBox);
       
       
       
        
        docComboBox.setSize(new Dimension(200,130));      
        docComboBox.setSize(new Dimension(50,30));
        docComboBox.setLocation(new Point(200,200));
        docComboBox.setEditable(true);
        docComboBox.setVisible(true);
        docComboBox.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
           
            //textContainer.setText("Soething has switched on gui was pressed");
           
            factory = new Factory(store.getDataBase());
            
            
           // System.out.println(docComboBox.getSelectedItem().toString());
           // System.out.println(factory.searchTableNr(docComboBox.getSelectedItem().toString()));
            ArrayList<String> joins = factory.checkForJoins(docComboBox.getSelectedItem().toString());
            embDocComboBox.removeAllItems();
            if(joins != null){
                for(int i = 0; i < joins.size(); ++i){
                embDocComboBox.addItem(joins.get(i));
                
                }
               
            }
             
            
            
          }
        });
        buttonPanel2.add(docComboBox);
        
        
        embDocComboBox.setSize(new Dimension(200,130));      
        embDocComboBox.setSize(new Dimension(50,30));
        embDocComboBox.setLocation(new Point(200,200));
        embDocComboBox.setEditable(true);
        embDocComboBox.setVisible(true);
        buttonPanel2.add(embDocComboBox);
      
        JButton embeddButton = new JButton();
        embeddButton.setText("embedd document");
        embeddButton.setVisible(true);
        embeddButton.setPreferredSize(new Dimension(160,30));
        embeddButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            
            
            
            int t = embDocComboBox.getSelectedIndex();
            textContainer.setText(new Integer(t).toString());
            factory.refactoring(t);
            
            textContainer.setText(embDocComboBox.getSelectedItem()+" is now in "+docComboBox.getSelectedItem()+" embedded");
            
          }
        });
        buttonPanel2.add(embeddButton);
        
        JButton translateButton = new JButton();
        translateButton.setText("Store db in MongoDB");
        translateButton.setVisible(true);
        translateButton.setPreferredSize(new Dimension(160,30));
        translateButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
           
            
            mongo = new Mongo();
            mongo.createMongo(store.getDataBase());
            
            buttonPanel2.setBackground(Color.BLACK);
            buttonPanel3.setBackground(Color.GREEN);
            
            textContainer.setText("Database is now in MongoDB");
            
            
          }
        });
        buttonPanel2.add(translateButton);
        
        JButton queryButton = new JButton();
        queryButton.setText("Execute Query");
        queryButton.setVisible(true);
        queryButton.setPreferredSize(new Dimension(160,30));
        queryButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            Query query = new Query(mongo);
           
            textContainer.setText(query.executeQuery());
            
          }
        });
        buttonPanel3.add(queryButton);
    
        
        
        this.setVisible(true); 
               
    }
    
   
    @Override
    public void mouseClicked(MouseEvent me){
                               
       
              
    }
    @Override
    public void mouseExited(MouseEvent me){
        
    }
    @Override
    public void mouseEntered(MouseEvent me){
        
    }
    @Override
    public void mouseReleased(MouseEvent me){
        
    }
    @Override
    public void mousePressed(MouseEvent me){
           
      
    }
    
}
