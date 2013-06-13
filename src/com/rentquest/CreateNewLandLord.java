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
public class CreateNewLandLord extends SwingWorker<String, Object> {
    private String queryStatus = "LandLord Creation Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    String fname;
    String lname;
    long natIdNo;
    int noOfPlots;
    String telNo;
    String gender;
    
    
    //CreateNewTenant constructor
    public CreateNewLandLord(String fname, String lname, long natIdNo,int noOfPlots,String telNo,String gender,JPanel rslPanel)
    {
        resultsPanel = rslPanel;
        this.lname = lname;
        this.fname =fname;
        this.natIdNo = natIdNo;
        this.noOfPlots = noOfPlots;
        this.telNo = telNo;
        this.gender = gender;    
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            createNewLandLordQuery(fname, lname, natIdNo, noOfPlots, telNo, gender);
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Create LandLord Results", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Create LandLord Results", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Create LandLord Results", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Query for creating new user
     */
    public void createNewLandLordQuery(String fname, String lname, long natIdNo,int noOfPlots,String telNo,String gender) throws SQLException{
        
                String createUserQuery = "insert into LandLords(First_Name, Last_Name, National_ID_No, No_Of_Plots, Telephone_No, Gender) values (?, ?, ?, ?, ? ,?)";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setString(1, fname);
                    ps.setString(2, lname);
                    ps.setLong(3, natIdNo);
                    ps.setInt(4, noOfPlots);
                    ps.setString(5, telNo);
                    ps.setString(6, gender);
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    queryStatus = "LandLord Successfully created";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "LandLord Results", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
        
    }//end of method
}
