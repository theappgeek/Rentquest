/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import javax.swing.*;
import org.jdesktop.swingx.border.DropShadowBorder;

/**
 *
 * @author drenpro
 */
public class ViewTable extends SwingWorker<ResultSet, Object> {
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    String query;
    String title;
 
    
    //ViewTable constructor
    public ViewTable(String query,JPanel rslPanel,String title)
    {
        resultsPanel = rslPanel; 
        this.query = query;
        this.title = title;
    }

    @Override
    protected ResultSet doInBackground() throws Exception {
        ResultSet resset = null;
        try
        {
            resset = database.query(query);
            
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
    
    
}
