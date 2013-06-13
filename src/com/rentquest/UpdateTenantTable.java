/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author drenpro
 */
public class UpdateTenantTable extends SwingWorker<String, Object> {
    private String queryStatus = "Tenant Record Update Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    String fname;
    String lname;
    int natIdNo;
    String roomNo;
    String gender;
    int recordID;
    String plotNo;
    String telNo;
    
    //CreateNewTenant constructor
    public UpdateTenantTable(String fname, String lname, int natIdNo,String roomNo,String gender,JPanel rslPanel,int recID,String plotNo,String telNo)
    {
        resultsPanel = rslPanel;
        this.lname = lname;
        this.fname =fname;
        this.natIdNo = natIdNo;
        this.roomNo = roomNo;
        this.gender = gender;  
        recordID = recID;
        this.plotNo = plotNo;
        this.telNo = telNo;
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            updateTenant(fname, lname, natIdNo, roomNo, gender,recordID,plotNo,telNo);
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update Tenant Record", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Update Tenant Record", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update Tenant Record", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Query for creating new user
     */
    public void updateTenant(String fname, String lname, int natIdNo,String roomNo,String gender,int recID, String plotNo, String telNo) throws SQLException{
        
                String createUserQuery = "UPDATE Tenants SET First_Name= ?, Last_Name= ?, National_ID_No= ?, Room_No= ?, Gender= ?, Plot_No= ? Telephone_No= ? WHERE Tenant_ID= "+recID;

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setString(1, fname);
                    ps.setString(2, lname);
                    ps.setInt(3, natIdNo);
                    ps.setString(4, roomNo);
                    ps.setString(5, gender);
                    ps.setString(6, plotNo);
                    ps.setString(6, telNo);
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    queryStatus = "Tenant Record Successfully updated";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Update Tenant Record", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
        
    }//end of method
}
