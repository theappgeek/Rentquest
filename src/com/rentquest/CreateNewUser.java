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
public class CreateNewUser extends SwingWorker<String, Object> {
    private String queryStatus = "User Creation Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
     //variable declaration
     String username;
     byte password[];
     String fname;
     String lname;
     long natIdNo;
    
    
    //CreateNewTenant constructor
    public CreateNewUser(String fname, String lname, long natIdNo,byte password[], String username,JPanel rslPanel)
    {
        resultsPanel = rslPanel;
        this.lname = lname;
        this.fname =fname;
        this.natIdNo = natIdNo;
        this.password = password;
        this.username = username;
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            createUser(fname, lname, natIdNo, password, username);
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Create User Results", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Create User Results", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Create User Results", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Query for creating new user
     */
    public void createUser(String fname, String lname, long natIdNo,byte password[], String username) throws SQLException{
        
                String createUserQuery = "insert into Users(Username, Password, First_Name, Last_Name, National_ID_No) values (?, ?, ?, ?, ?)";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setString(1, username);
                    ps.setBytes(2, password);
                    ps.setString(3, fname);
                    ps.setString(4, lname);
                    ps.setLong(5, natIdNo);
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    queryStatus = "User Successfully created";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Create User Results", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
        
    }//end of method
}
