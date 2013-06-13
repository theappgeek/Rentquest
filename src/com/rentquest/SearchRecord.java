/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import org.jdesktop.swingx.border.DropShadowBorder;

/**
 *
 * @author drenpro
 */
public class SearchRecord extends SwingWorker<ResultSet, Object> {
    
    //Variable Declaration
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    String searchString;
    String table;
    String title = "Search Results";
 
    
    //ViewTable constructor
    public SearchRecord(String searchString,JPanel rslPanel,String table)
    {
        resultsPanel = rslPanel; 
        this.searchString = searchString;
        this.table = table;
    }
//"Users","Tenants","LandLords","Plots","Bills","Settings","PayRent","PayRentAll","Rooms","Account","Expenses","Vacation Notice"};
     
    @Override
    protected ResultSet doInBackground() throws Exception {
        ResultSet resset = null;
        try
        {
            if(table.equalsIgnoreCase("Users"))
            {
                resset = searchUsersTable(searchString);
            }else
                
            if(table.equalsIgnoreCase("Tenants"))
            {
                resset =searchTenantsTable(searchString);
            }else
            if(table.equalsIgnoreCase("LandLords"))
            {
                resset =searchLandLordsTable(searchString);
            }else
            if(table.equalsIgnoreCase("Plots"))
            {
                resset =searchPlotsTable(searchString);
            }else
                
            if(table.equalsIgnoreCase("Settings"))
            {
                resset = searchSettingsTable(searchString);
            }else
            if(table.equalsIgnoreCase("PayRent"))
            {
                resset = searchPayRentTable(searchString);
            }else
            if(table.equalsIgnoreCase("PayRentAll"))
            {
                resset = searchPayRentAllTable(searchString);
            }else
                
            if(table.equalsIgnoreCase("Rooms"))
            { 
                resset = searchRoomsTable(searchString);
            }else
            if(table.equalsIgnoreCase("Accounts"))
            {
                resset = searchAccountsTable(searchString);
            }else
            if(table.equalsIgnoreCase("Expenses"))
            {
                resset = searchExpensesTable(searchString);
            }else
                
            if(table.equalsIgnoreCase("Vacation_Notice"))
            {
                resset = searchVacationNoticeTable(searchString);
            }else
            if(table.equalsIgnoreCase("Bills"))
            {
                resset = searchBillsTable(searchString);
            }
                
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
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
       
              JLabel myTitle = new JLabel(table+" "+title);
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
            JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
        }
    }//end of done
    
    
    
    public ResultSet searchUsersTable(String searchString)
    {
        //Variable declaration
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM Users WHERE Username LIKE '%"+searchString+"%' OR First_Name LIKE '%"+searchString+"%' OR Last_Name LIKE '%"+searchString+"%' OR National_ID_No LIKE '%"+searchString+"%'";

                PreparedStatement ps = null;
                try 
                {
                    ps = database.getConnection().prepareStatement(searchQuery);
                   
                    ps.execute();
                    
                    //Retrieve ResultSet
                    rs = ps.getResultSet();

                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
                }                
        
        return rs;
    }//end of method
    
    public ResultSet searchTenantsTable(String searchString)
    {
        //Variable declaration
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM Tenants WHERE First_Name LIKE '%"+searchString+"%' OR Last_Name LIKE '%"+searchString+"%' OR National_ID_No LIKE '%"+searchString+"%' OR Room_No LIKE '%"+searchString+"%' OR Plot_No LIKE '%"+searchString+"%' OR Telephone_No LIKE '%"+searchString+"%' OR Gender LIKE '%"+searchString+"%' OR Tenant_ID LIKE '%"+searchString+"%'";

                PreparedStatement ps = null;
                try 
                {
                    ps = database.getConnection().prepareStatement(searchQuery);
                    ps.execute();
                    
                    //Retrieve ResultSet
                    rs = ps.getResultSet();

                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
                }                
        
        return rs;
    }//end of method
    
    public ResultSet searchPlotsTable(String searchString)
    {
        //Variable declaration
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM Plots WHERE Plot_Name LIKE '%"+searchString+"%' OR Plot_No LIKE '%"+searchString+"%'OR No_Of_Rooms LIKE '%"+searchString+"%' OR Location LIKE '%"+searchString+"%' OR LandLord_ID LIKE '%"+searchString+"%'";

                PreparedStatement ps = null;
                try 
                {
                    ps = database.getConnection().prepareStatement(searchQuery);
                    ps.execute();
                    
                    //Retrieve ResultSet
                    rs = ps.getResultSet();

                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
                }                
        
        return rs;
    }//end of method
    
    public ResultSet searchBillsTable(String searchString)
    {
        //Variable declaration
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM Bills WHERE Tenant_ID LIKE '%"+searchString+"%' OR Fixed_Charge LIKE '%"+searchString+"%' OR Units LIKE '%"+searchString+"%' OR Unit_Charge LIKE '%"+searchString+"%' OR Utility LIKE '%"+searchString+"%' OR Bill_Payment_Status LIKE '%"+searchString+"%' OR Date LIKE '%"+searchString+"%' OR Total_Bill LIKE '%"+searchString+"%' OR Bill_ID LIKE '%"+searchString+"%'";

                PreparedStatement ps = null;
                try 
                {
                    ps = database.getConnection().prepareStatement(searchQuery);
                    ps.execute();
                    
                    //Retrieve ResultSet
                    rs = ps.getResultSet();

                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
                }                
        
        return rs;
    }//end of method
    
    public ResultSet searchSettingsTable(String searchString)
    {
        //Variable declaration
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM Settings WHERE Overdue_Days LIKE '%"+searchString+"%' OR Bonus_Days LIKE '%"+searchString+"%' OR Plot_No LIKE '%"+searchString+"%'";

                PreparedStatement ps = null;
                try 
                {
                    ps = database.getConnection().prepareStatement(searchQuery);
                    ps.execute();
                    
                    //Retrieve ResultSet
                    rs = ps.getResultSet();

                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
                }                
        
        return rs;
    }//end of method
    
    public ResultSet searchPayRentTable(String searchString)
    {
        //Variable declaration
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM PayRent WHERE Tenant_ID LIKE '%"+searchString+"%' OR Amount LIKE '%"+searchString+"%' OR Date_Of_Payment LIKE '%"+searchString+"%' OR Rent_Type LIKE '%"+searchString+"%' OR PayRent_ID LIKE '%"+searchString+"%'";

                PreparedStatement ps = null;
                try 
                {
                    ps = database.getConnection().prepareStatement(searchQuery);
                    ps.execute();
                    
                    //Retrieve ResultSet
                    rs = ps.getResultSet();

                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
                }                
        
        return rs;
    }//end of method
    
    public ResultSet searchPayRentAllTable(String searchString)
    {
        //Variable declaration
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM PayRentAll WHERE Tenant_ID LIKE '%"+searchString+"%' OR Amount LIKE '%"+searchString+"%' OR Date_Of_Payment LIKE '%"+searchString+"%' OR Rent_Type LIKE '%"+searchString+"%' OR PayRent_ID LIKE '%"+searchString+"%'";

                PreparedStatement ps = null;
                try 
                {
                    ps = database.getConnection().prepareStatement(searchQuery);
                    ps.execute();
                    
                    //Retrieve ResultSet
                    rs = ps.getResultSet();

                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
                }                
        
        return rs;
    }//end of method
    
    public ResultSet searchRoomsTable(String searchString)
    {
        //Variable declaration
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM Rooms WHERE Plot_No LIKE '%"+searchString+"%' OR Room_Type LIKE '%"+searchString+"%' OR Room_Status LIKE '%"+searchString+"%' OR Monthly_Rent LIKE '%"+searchString+"%' OR Room_No LIKE '%"+searchString+"%' OR Room_ID LIKE '%"+searchString+"%'";

                PreparedStatement ps = null;
                try 
                {
                    ps = database.getConnection().prepareStatement(searchQuery);
                    ps.execute();
                    
                    //Retrieve ResultSet
                    rs = ps.getResultSet();

                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
                }                
        
        return rs;
    }//end of method
    
    public ResultSet searchAccountsTable(String searchString)
    {
        //Variable declaration
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM Accounts WHERE Plot_No LIKE '%"+searchString+"%' OR Total_Income LIKE '%"+searchString+"%' OR Total_Expenses LIKE '%"+searchString+"%' OR Date LIKE '%"+searchString+"%' OR Account_ID LIKE '%"+searchString+"%'";

                PreparedStatement ps = null;
                try 
                {
                    ps = database.getConnection().prepareStatement(searchQuery);
                    ps.execute();
                    
                    //Retrieve ResultSet
                    rs = ps.getResultSet();

                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
                }                
        
        return rs;
    }//end of method
    
    public ResultSet searchExpensesTable(String searchString)
    {
        //Variable declaration
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM Expenses WHERE Plot_No LIKE '%"+searchString+"%' OR Amount LIKE '%"+searchString+"%' OR Expense_Description LIKE '%"+searchString+"%' OR Date LIKE '%"+searchString+"%' OR Expense_ID LIKE '%"+searchString+"%'";

                PreparedStatement ps = null;
                try 
                {
                    ps = database.getConnection().prepareStatement(searchQuery);
                    ps.execute();
                    
                    //Retrieve ResultSet
                    rs = ps.getResultSet();

                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
                }                
        
        return rs;
    }//end of method
    
    public ResultSet searchVacationNoticeTable(String searchString)
    {
        //Variable declaration
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM Vacation_Notice WHERE Tenant_ID LIKE '%"+searchString+"%' OR Plot_No LIKE '%"+searchString+"%' OR Expected_Leaving_Date LIKE '%"+searchString+"%'";

                PreparedStatement ps = null;
                try 
                {
                    ps = database.getConnection().prepareStatement(searchQuery);
                    ps.execute();
                    
                    //Retrieve ResultSet
                    rs = ps.getResultSet();

                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
                }                
        
        return rs;
    }//end of method
    
    
    public ResultSet searchLandLordsTable(String searchString)
    {
        //Variable declaration
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM LandLords WHERE First_Name LIKE '%"+searchString+"%' OR Last_Name LIKE '%"+searchString+"%' OR National_ID_No LIKE '%"+searchString+"%' OR No_Of_Plots LIKE '%"+searchString+"%' OR Telephone_No LIKE '%"+searchString+"%' OR Gender LIKE '%"+searchString+"%' OR LandLord_ID LIKE '%"+searchString+"%'";

                PreparedStatement ps = null;
                try 
                {
                    ps = database.getConnection().prepareStatement(searchQuery);
                    ps.execute();
                    
                    //Retrieve ResultSet
                    rs = ps.getResultSet();

                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(resultsPanel, e, "Search Record", JOptionPane.ERROR_MESSAGE);
                }                
        
        return rs;
    }//end of method
}//end of class
