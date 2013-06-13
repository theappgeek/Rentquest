/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import org.jdesktop.swingx.border.DropShadowBorder;

/**
 *
 * @author drenpro
 */
public class ViewTableWithDate extends SwingWorker<ResultSet, Object> {
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    String query;
    String title;
    Date fromDate;
    Date toDate;
    
    //ViewTable constructor
    public ViewTableWithDate(String query,JPanel rslPanel,String title,Date fromDate,Date toDate)
    {
        resultsPanel = rslPanel; 
        this.query = query;
        this.title = title;
        this.toDate =toDate;
        this.fromDate = fromDate;
    }

    @Override
    protected ResultSet doInBackground() throws Exception {
        ResultSet resset = null;
        try
        {
            resset = viewTableWithDate(query,fromDate, toDate);
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "View Table", JOptionPane.ERROR_MESSAGE);
        }
        return resset;
    }
    
    
    @Override
    protected void done(){
        try
        {
            resultsPanel.removeAll();
            resultsPanel.setLayout(new BorderLayout());
            resultsPanel.setBorder(new DropShadowBorder());
            
              JPanel titlePanel = new JPanel(new FlowLayout());
              titlePanel.setBackground(new java.awt.Color(144, 89, 33));
       
              JLabel myTitle = new JLabel(title);
              myTitle.setFont(new java.awt.Font("DejaVu Sans", 1, 25));
              titlePanel.add(myTitle);
              
              resultsPanel.add(titlePanel,BorderLayout.NORTH);
       
            JTable myTable = new ResultSetTable(get());  
            myTable.setFillsViewportHeight(true);  
    
            JScrollPane myJScrollPane = new JScrollPane();
            myJScrollPane.setViewportView(myTable);           
            resultsPanel.add(myJScrollPane,BorderLayout.CENTER);
            
            resultsPanel.revalidate();
            resultsPanel.repaint();
            database.closeConnection();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "View Table", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public ResultSet viewTableWithDate(String query,Date fromDate,Date toDate) throws SQLException{
        ResultSet rs = null;
        
                

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(query);
                    ps.setDate(1, fromDate);
                    ps.setDate(2, toDate);
                
                   
                    ps.execute();
                    
                    rs= ps.getResultSet();
             
                } 
                catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Create Room Results", JOptionPane.ERROR_MESSAGE);
                } 
                
                return rs;
    }
    
}
