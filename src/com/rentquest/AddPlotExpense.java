/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import javax.swing.*;
import java.sql.*;

/**
 *
 * @author drenpro
 */
public class AddPlotExpense extends SwingWorker<String, Object> {
    
    //Variable declarations
    private String queryStatus = "Expense Addition Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    //variable declaration
    String plotNo;
    double amount;
    String expenseDes = null;
    Date myDate;
   
    
    
    //CreateNewPlot constructor
    public AddPlotExpense(String plotNo,double amount, String expenseDes,Date myDate,JPanel resultPanel)
    {
        this.amount = amount;
        this.plotNo = plotNo;
        this.expenseDes = expenseDes;
        this.myDate = myDate;
        this.resultsPanel = resultPanel;
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
          queryStatus = addPlotExpense(plotNo, amount, expenseDes, myDate);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Add Plot Expense", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Add Plot Expense", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Add Plot Expense", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Query for creating new user
     */
    public boolean addExpense(String plotNo,double amount,String expenseDes,Date date) throws SQLException{
        
        //variable declaration
        boolean isQueryExecutionSuccessful = false;
        
                String createUserQuery = "insert into Expenses(Plot_No, Amount,Date,Expense_Description) values (?, ?, ?, ?)";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setString(1, plotNo);
                    ps.setDouble(2, amount);
                    ps.setDate(3, date);
                    ps.setString(4, expenseDes);
                   
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    isQueryExecutionSuccessful = true;
                    queryStatus = "Expense Successfully Added";
                }              
                catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Add Plot Expense", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
                
                return isQueryExecutionSuccessful;
        
    }//end of method
  

    public String addPlotExpense(String plotNo, double amount, String expenseDes, Date myDate) {
        
        //varible declaration
        boolean plotExists = false;
        
        //check if the plot exists
        try
        {
            ResultSet rs = database.query("SELECT * FROM Plots WHERE Plot_No ='"+plotNo+"'");
            plotExists = rs.next();
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Add Plot Expense", JOptionPane.ERROR_MESSAGE);
        }
        
        //add plot expense if the plot exists
        //check if the plot exists
        try
        {
            if(plotExists)
            {
                addExpense(plotNo,amount,expenseDes,myDate);
            }else
            {
                queryStatus = "The Plot You Selected Does Not Exist";
            }
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Add Plot Expense", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
}
