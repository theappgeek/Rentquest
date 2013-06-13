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
public class CreateNewTenant extends SwingWorker<String, Object> {
    private String queryStatus = "Tenant Creation Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    String fname;
    String lname;
    long natIdNo;
    String roomNo;
    String gender;
    String plotNo;
    String telNo;
    
    
    //CreateNewTenant constructor
    public CreateNewTenant(String fname, String lname, long natIdNo,String roomNo,String gender,JPanel rslPanel, String plotNo,String telNo)
    {
        resultsPanel = rslPanel;
        this.lname = lname;
        this.fname =fname;
        this.natIdNo = natIdNo;
        this.roomNo = roomNo;
        this.gender = gender;  
        this.plotNo = plotNo;
        this.telNo = telNo;        
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            queryStatus = newTenant(fname, lname, natIdNo,roomNo,gender,plotNo,telNo);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Create Tenant Results", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Create Tenant Results", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Create Tenant Results", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Query for creating new user
     */
    public boolean createTenant(String fname, String lname, long natIdNo,String roomNo,String gender, String plotNo, String telNo) throws SQLException{
        
        //variable
        boolean isQueryExecutionSuccessful = false;
        
                String createUserQuery = "insert into Tenants(First_Name, Last_Name, National_ID_No, Room_No, Gender, Plot_No,Telephone_No) values (?, ?, ?, ?, ?, ? ,?)";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setString(1, fname);
                    ps.setString(2, lname);
                    ps.setLong(3, natIdNo);
                    ps.setString(4, roomNo);
                    ps.setString(5, gender);
                    ps.setString(6, plotNo);
                    ps.setString(7, telNo);
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    isQueryExecutionSuccessful = true;
                    queryStatus = "Tenant Successfully created";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Create Tenant Results", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();
                }
                
                return isQueryExecutionSuccessful;
        
    }//end of method
    
    
    public String newTenant(String fname, String lname, long  natIdNo,String roomNo,String gender,String plotNo, String telNo)
    {
        //variable declaration
        String targetRoomStatus = null;
        boolean roomExists =  false;
        
        //check if the selected room exists
        try
        {
            ResultSet rs = database.query("SELECT * FROM Rooms WHERE Plot_No ='"+plotNo+"' AND Room_No = '"+roomNo+"'");
            roomExists = rs.next();
            
            //if room exists get its status
            if(roomExists)
            {
                targetRoomStatus = rs.getString("Room_Status");
            }
            else
            {
                queryStatus = "Selected Room Does Not Exist";
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Create Tenant Results", JOptionPane.ERROR_MESSAGE);
        }
        
            
            //if tenant creation was successfull update room status of target room to booked
            try
            {
                String newRoomStatus= "Booked";
                //boolean isTenant = createTenant(fname, lname, natIdNo, roomNo, gender,plotNo);
                        
                if(roomExists)// && targetRoomStatus.equals("Vaccant"))
                {
                  
                    if(targetRoomStatus.equals("Vacant"))
                    {
                        if(createTenant(fname, lname, natIdNo, roomNo, gender,plotNo,telNo))
                        {
                          database.runActionQuery("UPDATE Rooms SET Room_Status= '"+newRoomStatus+"' WHERE Plot_NO = '"+plotNo+"' AND Room_No='"+roomNo+"'"); 
                        }
                     }
                    else if(targetRoomStatus.equals("Booked"))
                    {
                        queryStatus = "Sorry.The selected room is Already Booked.";
                    }
                    else
                    {
                        queryStatus = "Sorry.The selected room is Already Occupied.";
                    }
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(resultsPanel, e, "Create Tenant Results", JOptionPane.ERROR_MESSAGE);
            }
        
        return queryStatus;
    }//end of newTenant Method
}
