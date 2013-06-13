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
public class UpdateLandLordRecord extends SwingWorker<String, Object> {
    private String queryStatus = "LandLord Record Update Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    String fname;
    String lname;
    int natIdNo;
    int noOfPlots;
    String telNo;
    String gender;
    int recordId;
    
    
    //update landlord record constructor
    public UpdateLandLordRecord(String fname, String lname, int natIdNo,int noOfPlots,String telNo,String gender,JPanel rslPanel,int recId)
    {
        resultsPanel = rslPanel;
        this.lname = lname;
        this.fname =fname;
        this.natIdNo = natIdNo;
        this.noOfPlots = noOfPlots;
        this.telNo = telNo;
        this.gender = gender;   
        recordId = recId;
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            updateLandLordQuery(fname, lname, natIdNo, noOfPlots, telNo, gender,recordId);
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update LandLord Record", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Update LandLord Record", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update LandLord Record", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Query for creating new user
     */
    public void updateLandLordQuery(String fname, String lname, int natIdNo,int noOfPlots,String telNo,String gender,int recID) throws SQLException{
        
                String updateLandlordRecordQuery = "UPDATE LandLords SET First_Name=?, Last_Name=?, National_ID_No=?, No_Of_Plots=?, Telephone_No=?, Gender=? WHERE LandLord_ID= "+recID;

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(updateLandlordRecordQuery);
                    ps.setString(1, fname);
                    ps.setString(2, lname);
                    ps.setInt(3, natIdNo);
                    ps.setInt(4, noOfPlots);
                    ps.setString(5, telNo);
                    ps.setString(6, gender);
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    queryStatus = "LandLord Record Successfully updated";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "LandLord Record Update", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
        
    }//end of method
}
