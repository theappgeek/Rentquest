/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.border.DropShadowBorder;

/**
 *
 * @author drenpro
 */
public class ProfitLossReport extends SwingWorker<MyDataType, Object> {
    
    //Variable declaration
    private String queryStatus = "Report Retrival Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    String recordID;
    boolean recordExists = false;
    
    String plotNo;
    Date toDate;
    Date fromDate;
    
    boolean plotExistsInPayRent = false;
    boolean plotExistsInExpenses = false;
  
    //constructor
    public ProfitLossReport(String plotNo, Date toDate, Date fromDate,JPanel rslPanel)
    {
        resultsPanel = rslPanel; 
        this.toDate = toDate;
        this.fromDate = fromDate;
        this.plotNo = plotNo;
    }


    @Override
    protected MyDataType doInBackground() throws Exception {
        MyDataType mydata = null;
        try
        {
          mydata = getProfitLossReport(plotNo, toDate, fromDate, resultsPanel);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update Record", JOptionPane.ERROR_MESSAGE);
        }
        
        return mydata;
    }
    
    
    @Override
    protected void done(){
        //draw GUI
        try
        {
           if(plotExistsInPayRent)
            {
                    //Draw GUI
                    resultsPanel.removeAll(); 
                    resultsPanel.setLayout(new BorderLayout());
                    resultsPanel.setBorder(new DropShadowBorder());
       
                    JPanel titlePanel = new JPanel(new FlowLayout());
                    titlePanel.setBackground(new java.awt.Color(144, 89, 33));
       
                    JLabel title = new JLabel("Plot's Profit Loss Report");
                    title.setFont(new java.awt.Font("DejaVu Sans", 1, 36));
       
       
                    titlePanel.add(title);
       
                    resultsPanel.add(titlePanel,BorderLayout.NORTH);
       
                    JPanel labelPanel = new JPanel(new GridLayout(7, 1, 5, 5));
                    labelPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                    //create field name labels
                    JLabel plotID = new JLabel("Plot No");
                    JLabel fronDateLabel = new JLabel("From Date");
                    JLabel toDateLabel = new JLabel("To Date");
                    JLabel totalIncomeLabel = new JLabel("Total Income(KES)");
                    JLabel totalExpensesLabel = new JLabel("Total Expense(KES)");
                    JLabel plStatus = new JLabel("Profit/Loss Status");
                    JLabel amountLabel = new JLabel("Amount");
       
                    //add labels to labelPanel
                    labelPanel.add(plotID);
                    labelPanel.add(fronDateLabel);
                    labelPanel.add(toDateLabel);
                    labelPanel.add(totalIncomeLabel);
                    labelPanel.add(totalExpensesLabel);
                    labelPanel.add(plStatus);
                    labelPanel.add(amountLabel);
     
                     //add labelPanel to west side of resultsPanel
                    resultsPanel.add(labelPanel,BorderLayout.WEST);
       
                    //create Jfields
                    final JTextField plotIDTextField = new JTextField();
                    plotIDTextField.setText(plotNo);
                    plotIDTextField.setEnabled(false);
                    
                    final JXDatePicker fromDatePicker = new JXDatePicker();
                    fromDatePicker.setDate(get().getFromDate());    
                    fromDatePicker.setEnabled(false);
                    
                    final JXDatePicker toDatePicker = new JXDatePicker();
                    toDatePicker.setDate(get().getToDate());
                    toDatePicker.setEnabled(false);
                    
                    final JTextField totalIncome = new JTextField();
                    totalIncome.setText(""+get().getTotalIncome());
                    totalIncome.setEnabled(false);
                    
                    final JTextField totalExpenses = new JTextField();
                    totalExpenses.setText(""+get().getTotalExpenses());
                    totalExpenses.setEnabled(false);
                    
                    final JTextField plstatusField = new JTextField();
                    plstatusField.setText(get().getProfitLossStatus());
                    plstatusField.setEnabled(false);
       
                    //Exact profit/loss amount
                    final JTextField plAmountField = new JTextField();
                    double loss = get().getTotalExpenses();
                    double profit = get().getTotalIncome();
                    double amount;
                    
                            if(loss < profit)
                            {
                                amount = profit -loss;
                            }else
                                if(loss > profit)
                                {
                                    amount = loss - profit;                                    
                                }
                            else
                                {
                                    amount = 0;
                                }
                      plAmountField.setText(""+ amount);
                      plAmountField.setEnabled(false);
                    
                    //create textfields panel
                    JPanel fieldsPanel = new JPanel(new GridLayout(7, 1, 5, 5));
                    fieldsPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                    //add textfields to textfield panel
                    fieldsPanel.add(plotIDTextField); 
                    fieldsPanel.add(fromDatePicker); 
                    fieldsPanel.add(toDatePicker); 
                    fieldsPanel.add(totalIncome);
                    fieldsPanel.add(totalExpenses);
                    fieldsPanel.add(plstatusField);
                    fieldsPanel.add(plAmountField);
       
                    //add fieldsPanel to center region of resultsPanel
                    resultsPanel.add(fieldsPanel,BorderLayout.CENTER);
       
       
                    resultsPanel.revalidate();
                    resultsPanel.repaint();
                    
                    
                
                //}
                //else
                //{
                    //JOptionPane.showMessageDialog(resultsPanel, "The plot You Selected Does  Not Exist In Expenses Table", "Get Plots Profit/Loss Report", JOptionPane.ERROR_MESSAGE);
                //}
                
            }
            else
            {
                JOptionPane.showMessageDialog(resultsPanel, "The plot You Selected Does  Not Exist In PayRent Table", "Get Plots Profit/Loss Report", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update Record", JOptionPane.ERROR_MESSAGE);
        }
    }//end of method done
    
    
    /*
     * Returns Plots Total Income in a specified time period
     */
    public double getPlotsTotalIncome(String plotNo, Date toDate, Date fromDate) throws SQLException 
    {
        //variable declaration
        double income = 0.0;
        String createUserQuery = "SELECT SUM(PayRentAll.Amount) FROM PayRentAll INNER JOIN Tenants ON Tenants.Tenant_ID = PayRentAll.Tenant_ID AND Tenants.Plot_No=? AND PayRentAll.Date_Of_Payment >=? AND PayRentAll.Date_Of_Payment <=?";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setString(1, plotNo);
                    ps.setDate(2, fromDate);
                    ps.setDate(3, toDate);
                   
                    
                    ps.execute();
                    
                    ResultSet rs = ps.getResultSet();
                    
                    if(rs.next())
                    {
                        income = rs.getDouble(1);
                    }
                    
                    
                }              
                catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Get Plots Income", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();
                }
        
        return income;
    }
    
    
    /*
     * Returns Plots Total Expenditure in a specified time period
     */
    public double getPlotsTotalExpenses(String plotNo, Date toDate, Date fromDate) throws SQLException
    {
        //Variable declaration
        double expenses = 0.0;
        String createUserQuery = "SELECT SUM(Expenses.Amount) FROM Expenses WHERE Expenses.Plot_No= ? AND Expenses.Date >=? AND Expenses.Date <=?";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setString(1, plotNo);
                    ps.setDate(2, fromDate);
                    ps.setDate(3, toDate);
                   
                    
                    ps.execute();
                    ResultSet rs = ps.getResultSet();
                    
                    if(rs.next())
                    {
                        expenses = rs.getDouble(1);
                    }
                    
                    
                }              
                catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Get Plots Expenses", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();
                }
        
        return expenses;
    }
    
    
    /*
     * Returns a Jpanel with a Plots Profit/Loss report
     */
    public MyDataType getProfitLossReport(String plotNo, Date toDate, Date fromDate,JPanel rslPanel)
    {
        //Variable declaration;
        MyDataType myData = null;
        double totalExpenses = 0.0;
        double totalIncome = 0.0;
        String plotProfitLossStatus = null;
        
        
        //Check if plots exists
        try 
        {
            //check if plot exists in PayRent
            ResultSet rs = database.query("SELECT * FROM PayRent INNER JOIN Tenants ON Tenants.Tenant_ID = PayRent.Tenant_ID AND Tenants.Plot_No = '"+plotNo+"'");
            plotExistsInPayRent = rs.next();
            
                if(plotExistsInPayRent)
                {
                    totalIncome =  getPlotsTotalIncome(plotNo, toDate, fromDate);
                }
                
           //Check if plot exists in Expenses
            ResultSet res = database.query("SELECT * FROM Expenses WHERE Plot_No = '"+plotNo+"'");
            plotExistsInExpenses = res.next();
            
                if(plotExistsInExpenses)
                {
                    totalExpenses = getPlotsTotalExpenses(plotNo, toDate, fromDate);
                }
                
           //determine if plot made a profit or loss
                if(totalIncome > totalExpenses)
                {
                    plotProfitLossStatus ="The Plot Made Profit";
                }
                else
                if(totalIncome < totalExpenses)
                {
                    plotProfitLossStatus ="The Plot Made Loss";    
                }else
                {
                    plotProfitLossStatus ="Plots Expenditure And Income was Equal";
                }
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(rslPanel, e, "Get Plots Profit/Loss Report", JOptionPane.ERROR_MESSAGE);
        }
        
        //draw GUI
        try
        {
            myData = new MyDataType(totalExpenses, totalIncome, fromDate, toDate, plotProfitLossStatus);
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(rslPanel, e, "Get Plots Profit/Loss Report", JOptionPane.ERROR_MESSAGE);
        }
        
        return myData;
    }
    
    
}
