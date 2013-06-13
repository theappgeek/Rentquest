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
public class UpdateUserRecord extends SwingWorker<String, Object> {
    private String queryStatus = "User Record Update Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
     //variable declaration
     String username;
     byte password[];
     String fname;
     String lname;
     int natIdNo;
     
     String recordID;
    
    
    //update constructor
    public UpdateUserRecord(String fname, String lname, int natIdNo,byte password[],String username,JPanel rslPanel,String recID)
    {
        resultsPanel = rslPanel;
        this.lname = lname;
        this.fname =fname;
        this.natIdNo = natIdNo;
        this.password = password;
        this.username = username;
        recordID = recID;
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            updateUser(fname, lname, natIdNo, password,username,recordID);
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update User Record", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Update User Record", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update User Record", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Query for updateing user record
     */
    public void updateUser(String fname, String lname, int natIdNo,byte password[],String username, String recID) throws SQLException{
        
                String createUserQuery = "UPDATE Users SET Username=?, Password=?, First_Name=?, Last_Name=?, National_ID_No=? WHERE Username= '" + recID + "'";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setString(1, username);
                    ps.setBytes(2, password);
                    ps.setString(3, fname);
                    ps.setString(4, lname);
                    ps.setInt(5, natIdNo);
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    queryStatus = "User Record Successfully Updated";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Update User Record", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
        
    }//end of method
}
