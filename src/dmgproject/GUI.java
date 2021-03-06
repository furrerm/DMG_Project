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
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

/**
 *
 * @author Markus
 */

public class GUI extends JFrame{
    
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
    
    DBConnect dbConnect;
    
    public GUI(){
        //gradient
        
        String filePath = new File("").getAbsolutePath();
        
        filePath = filePath.concat("\\Pictures\\FrameIcon_tree.png");
       
        ImageIcon img = new ImageIcon(filePath);
        this.setIconImage(img.getImage());
        d1 = new Dimension(700,350);
        
        this.getContentPane().setPreferredSize(d1);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.white);
                    
        this.setLocationRelativeTo(null);
        
        
       // this.addMouseListener(this);
        this.setResizable(false);
        this.setTitle("MyMongo");
        
        //Text Panel
        JPanel textPanel = new JPanel();
        textPanel.setPreferredSize(new Dimension(500,200));
        textPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        Color col = new Color(50,40,130);
        textPanel.setBackground(col);
        this.add(textPanel,BorderLayout.PAGE_START);
        
        
        
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1,3));
        //buttonsPanel.setPreferredSize(new Dimension(500,200));
        
        this.add(buttonsPanel,BorderLayout.CENTER);
        
        
        
        
        JLabel textContainer1 = new JLabel("");
        textPanel.add(textContainer1);
        textContainer1.setText("Welcome to MyMongo");
        textContainer1.setPreferredSize(new Dimension(650,30));
        textContainer1.setForeground(Color.WHITE);
        
        
        JLabel textContainer2 = new JLabel("");
        textPanel.add(textContainer2);
        textContainer2.setText("connect to your mysql Database on localhost");
        textContainer2.setPreferredSize(new Dimension(650,30));
        textContainer2.setForeground(Color.WHITE);
        
       
        
        JPanel buttonPanel0 = new JPanel();
        buttonPanel0.setLayout(new FlowLayout());
        buttonPanel0.setBackground(Color.WHITE);
        buttonPanel0.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        buttonsPanel.add(buttonPanel0);
        
        
        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.setLayout(new FlowLayout());
        buttonPanel1.setBackground(Color.BLACK);
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
       // buttonsPanel.add(buttonPanel3);
        
        
         
        
        
        final JComboBox comboBox = new JComboBox();
        comboBox.setSize(new Dimension(200,130));      
        comboBox.setSize(new Dimension(50,30));
        comboBox.setLocation(new Point(200,200));
        comboBox.setEditable(true);
        comboBox.setVisible(true);
        
        
        
        //*************************************************************************************************//
        //erster Panle Funktionalität
        //*************************************************************************************************//
        JLabel userNameLabel = new JLabel("User name:");
        JTextField userNameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        userNameField.setPreferredSize(new Dimension(150,23));
        
        JTextField passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(150,23));
        
        JButton connectButton = new JButton();
        connectButton.setText("Connect to Database");
        connectButton.setVisible(true);
        connectButton.setPreferredSize(new Dimension(160,30));
        connectButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
         
            
            dbConnect = new DBConnect(userNameField.getText(), passwordField.getText());
            dbConnect.setConnection("");
            connection = dbConnect.getConnection();
            
            try{
                DatabaseMetaData md = connection.getMetaData();
                ResultSet set = md.getCatalogs();

                while(set.next()){
                    comboBox.addItem(set.getString("TABLE_CAT"));
                }            
            }
            catch(Exception exception){

            }
            buttonPanel1.setBackground(Color.WHITE);
            buttonPanel0.setBackground(Color.BLACK);
            
            textContainer1.setText("Choose the databse you want to migrate");
            textContainer2.setText("");
            
          }
        });
        buttonPanel0.add(userNameLabel);
        buttonPanel0.add(userNameField);
        buttonPanel0.add(passwordLabel);
        buttonPanel0.add(passwordField);
        buttonPanel0.add(connectButton);
        //*************************************************************************************************//
        //zweiter Panle Funktionalität
        //*************************************************************************************************//

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
         
            textContainer1.setText("choose first the document where you want to embedd in");
            textContainer2.setText("choose then the table  you want to embedd (The brackets show over which fields you can embedd)");
            //connection & DB Name mitgeben
            dbConnect.setConnection(comboBox.getSelectedItem().toString());
            store = new StoreDB(dbConnect.getConnection());
            
            store.storeTables();
            
            for(int i = 0; i < store.getTableNames().size(); ++i){
                docComboBox.addItem(store.getTableNames().get(i));
                
            }
            
            buttonPanel1.setBackground(Color.BLACK);
            buttonPanel2.setBackground(Color.WHITE);
           
            
            
          }
        });
        buttonPanel1.add(comboBox);
        buttonPanel1.add(storeButton);
        
        
       
       
       
        
        docComboBox.setSize(new Dimension(200,130));      
        docComboBox.setSize(new Dimension(50,30));
        docComboBox.setLocation(new Point(200,200));
        docComboBox.setEditable(true);
        docComboBox.setVisible(true);
        docComboBox.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
           
          
           
            factory = new Factory(store.getDataBase());
            
            
           
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
            
            
            
            
            
            
            factory.refactoring(embDocComboBox.getSelectedIndex());
            
            textContainer1.setText(embDocComboBox.getSelectedItem()+" is now in "+docComboBox.getSelectedItem()+" embedded");
            textContainer2.setText("");
           
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
            buttonPanel3.setBackground(Color.WHITE);
            
            textContainer1.setText("Database is now in MongoDB");
            textContainer2.setText("");
            
          }
        });
        buttonPanel2.add(translateButton);
        
        
        //******************************************************************************************//
        //For query executer
        //********************************************************************************************//
        
        /*
        JButton queryButton = new JButton();
        queryButton.setText("Execute Query");
        queryButton.setVisible(true);
        queryButton.setPreferredSize(new Dimension(160,30));
        queryButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            Query query = new Query(mongo);
           
            textContainer1.setText(query.executeQuery());
            
          }
        });
        buttonPanel3.add(queryButton);
    */
        
        
        this.setVisible(true); 
               
    }

   
    //Only for debugging purposes
    public void showDb(){
        
        System.out.println(store.getDataBase().get(2));
        ArrayList <ArrayList> l1 = store.getDataBase();
        ArrayList <Object> l2 = l1.get(6);
        Row r = (Row)l2.get(3);
        Metadata m = (Metadata)r.getMetadata();
       
      
        for(int i = 2; i < m.metaSize();++i){
            System.out.println("*******************"+m.getValue(i));
        }
        for(int i = 1; i < r.size();++i){
            System.out.println("+++++++++++++++++++++++"+r.getValue(i));
        }
        for(int i = 0; i < m.typesSize();++i){
            System.out.println("-------------------------"+m.getType(i));
        }
        
        l2 = l1.get(2);
        r = (Row)l2.get(3);
        m = (Metadata)r.getMetadata();
       System.out.println("pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
        
        for(int i = 2; i < m.metaSize();++i){
            System.out.println("*******************"+m.getValue(i));
        }
        for(int i = 1; i < r.size();++i){
            System.out.println("+++++++++++++++++++++++"+r.getValue(i));
        }
        for(int i = 0; i < m.typesSize();++i){
            System.out.println("-------------------------"+m.getType(i));
        }

    }
    
}
