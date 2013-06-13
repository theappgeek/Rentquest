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
public class UpdateRoomRecord extends SwingWorker<String, Object> {
    private String queryStatus = "Room Record Update Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    String roomType;
    String plotNo;
    String roomStatus;
    double monthlyRent;
    String roomNo;
    String recordID;
    
    
    //Update Room Record constructor
    public UpdateRoomRecord(String roomType, String  plotNo, String roomStatus,double monthlyRent,String roomNo,JPanel rslPanel, String recID)
    {
        resultsPanel = rslPanel;
        this.roomNo = roomNo;
        this.plotNo = plotNo;
        this.roomStatus = roomStatus;
        this.monthlyRent = monthlyRent;
        this.roomType = roomType;
        recordID = recID;
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            updateRoomRecord(roomType, plotNo,roomStatus, monthlyRent, roomNo, recordID);
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update Room Record", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Update Room Record", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update Room Record", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Method for creating new Room
     */
    public void updateRoomRecord(String roomType, String  plotNo, String roomStatus,double monthlyRent,String roomNo, String recID) throws SQLException{
        
                String createRoomQuery = "UPDATE Rooms SET Plot_No=?, Room_Type=?, Room_Status=?, Monthly_Rent=?, Room_No=? WHERE Room_No= '" +recID + "'";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createRoomQuery);
                    ps.setString(1, plotNo);
                    ps.setString(2, roomType);
                    ps.setString(3, roomStatus);
                    ps.setDouble(4, monthlyRent);
                    ps.setString(5, roomNo);
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    queryStatus = "Room  Record Successfully Updated";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Edit Room Record", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
        
    }//end of method
}
