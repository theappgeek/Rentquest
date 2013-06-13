/*
 * Poor design.Code repetition.Remedy:I might change the way i represent this later
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
public class ViewRentOverdueTenants extends SwingWorker<ResultSet, Object> {
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    String plotNo;
 
    
    //ViewTable constructor
    public ViewRentOverdueTenants(String plotNo,JPanel rslPanel)
    {
        resultsPanel = rslPanel; 
        this.plotNo = plotNo;
    }

    @Override
    protected ResultSet doInBackground() throws Exception {
        ResultSet resset = null;
        try
        {
            resset = RentDueTenants(plotNo);
            
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
       
              JLabel myTitle = new JLabel("Tenants Whose Rent Is Overdue");
              myTitle.setFont(new java.awt.Font("DejaVu Sans", 1, 25));
              titlePanel.add(myTitle);
              
              resultsPanel.add(titlePanel,BorderLayout.NORTH);
       
              
              //Handle null Resultsets from Non existent plot numbers
              if(get()==null)
              {
                  return;
              }
              else
              {
                   JTable myTable = new ResultSetTable(get());  
                   myTable.setFillsViewportHeight(true);  
    
                   JScrollPane myJScrollPane = new JScrollPane();
                   myJScrollPane.setViewportView(myTable);
                   resultsPanel.add(myJScrollPane,BorderLayout.CENTER);
              }
                       
            
            
            resultsPanel.revalidate();
            resultsPanel.repaint();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "View Table", JOptionPane.ERROR_MESSAGE);
        }
    }//end of done
    
    
    public ResultSet RentDueTenants(String plotNo)
    {
        //Variable declaration
        ResultSet rs = null;
        int overdueDays = 0;
        int bonusDays = 0;
        int maxDays;
        boolean plotExists = false;
                        
        //Check if plot exits
        try
        {
            ResultSet res = database.query("SELECT * FROM Settings WHERE Plot_No = '"+plotNo+"'");
            plotExists = res.next();
            
            if(plotExists)
            {
                overdueDays = res.getInt("Overdue_Days");
                bonusDays = res.getInt("Bonus_Days");
                
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "View Table", JOptionPane.ERROR_MESSAGE);
        }
        
        maxDays = overdueDays+bonusDays;
        //Query String
        String myQuery ="SELECT Tenants.First_Name, Tenants.Last_Name, Tenants.Tenant_ID, Tenants.Plot_no,"+
                "Tenants.Room_No,PayRent.Date_Of_Payment FROM Tenants JOIN PayRent ON "+
                "Tenants.Tenant_ID = PayRent.Tenant_ID "+
                "AND DATE_SUB(CURDATE(),INTERVAL "+maxDays+" DAY) > PayRent.Date_Of_Payment"+
                " WHERE Tenants.Plot_No = '"+plotNo+"'";
        //if plot exists run query
        try
        {
            if(plotExists)
            {
                   rs = database.query(myQuery);   
            }else
            {
                JOptionPane.showMessageDialog(resultsPanel, "Plot is not Configured!", "View Table", JOptionPane.ERROR_MESSAGE);
            }
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "View Table", JOptionPane.ERROR_MESSAGE);
        }
        
        return rs;
    }
    
    
}
