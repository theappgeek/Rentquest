/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author drenpro
 */
public class ConfigurePlotSettings extends SwingWorker<String, Object> {
    private String queryStatus = "Plot Configuration Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    int overdueDays;
    int bonusDays;
    String plotNo;
   
    
    
    //Configure plot settings constructor
    public ConfigurePlotSettings(int overdueDays, int bonusDays, String plotNo,JPanel rslPanel)
    {
        resultsPanel = rslPanel;
        this.overdueDays = overdueDays;
        this.bonusDays = bonusDays;
        this.plotNo = plotNo;
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            queryStatus = configurePlot(overdueDays, bonusDays, plotNo);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Configure Plot Results", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Configure Plot Results", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Configure Plot Results", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Query for configuring a plot
     */
    public void configurePlotQuery(int overdueDays, int bonusDays, String plotNo) throws SQLException{
        
                String createUserQuery = "insert into Settings(Overdue_Days, Bonus_Days, Plot_No) values (?, ?, ?)";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setInt(1, overdueDays);
                    ps.setInt(2, bonusDays);
                    ps.setString(3, plotNo);
                                        
                    ps.executeUpdate();
                    
                    //query executed successfully
                    queryStatus = "Plot Successfully Configured";
                }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e)
                {
                    queryStatus = "The Plot is Already Configured!";
                } 
                catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Configure Plot Results", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
        
    }//end of method
    
    public String configurePlot(int overdueDays, int bonusDays, String plotNo)
    {
        //variable declaration
        boolean plotExists = false;
        
        //check if the selected plot exits
        try
        {
            ResultSet rs = database.query("SELECT * FROM Plots WHERE Plot_No = '"+plotNo+"'");
            plotExists = rs.next();
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Configure Plot Results", JOptionPane.ERROR_MESSAGE);
        }
        
        //configure settings if the plot exists
        try
        {
            if(plotExists)
            {
                configurePlotQuery(overdueDays, bonusDays, plotNo);
            }
            else
            {
                queryStatus = "Selected Plot does not Exist!";
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Configure Plot Results", JOptionPane.ERROR_MESSAGE);
        }
        return queryStatus;
    }
}
