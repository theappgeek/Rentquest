/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import java.sql.Date;
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
public class IssueVacationNotice extends SwingWorker<String, Object> {
    private String queryStatus = "Vacation Notice Issue Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    String plotNo;
    Date expectedLeavingDate;
    int tenantID;
    
    
    //CreateNewTenant constructor
    public IssueVacationNotice(String plotNo, Date expectedLeavingDate ,int tenantID,JPanel rslPanel)
    {
        resultsPanel = rslPanel;
        this.plotNo = plotNo;
        this.expectedLeavingDate = expectedLeavingDate;
        this.tenantID = tenantID;
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            queryStatus = issueVacationNotice(plotNo,expectedLeavingDate , tenantID);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Issue Vacation Notice", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Issue Vacation Notice", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Issue Vacation Notice", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Issuing a vacation Notice
     */
    public void issuevacationNoticQeuery(int tenantID, String plotNo, Date expectedLeavingDate) throws SQLException{
        
                String createUserQuery = "insert into Vacation_Notice(Tenant_ID, Plot_No,Expected_Leaving_Date) values (?, ?, ?)";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setInt(1,tenantID );
                    ps.setString(2, plotNo);
                    ps.setDate(3, expectedLeavingDate);
                    
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    queryStatus = "Vacation Notice Issued Successfully";
                }
                catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e)
                {
                    queryStatus = "Vacation Notice has Aready Been Issued!";
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(resultsPanel, e, "Issue Vacation Notice", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();
                }
        
    }//end of method
    
    
    public String issueVacationNotice(String plotNo, Date expectedLeavingDate ,int tenantID)
    {
        //Variable declaration
        boolean tenantExists = false;
        
        //check if selected tenant exists
        try
        {
            ResultSet rs = database.query("SELECT * FROM Tenants WHERE Tenant_ID = "+tenantID);
            
            tenantExists = rs.next();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Issue Vacation Notice", JOptionPane.ERROR_MESSAGE);
        }
        
        //issue vacation notice if the selected exists
        try
        {
            if(tenantExists)
            {
                issuevacationNoticQeuery(tenantID, plotNo, expectedLeavingDate);
            }
            else
            {
                queryStatus = "The Selected Tenant does Not Exists!";
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Issue Vacation Notice", JOptionPane.ERROR_MESSAGE);
        }
        return queryStatus;
    }
}
