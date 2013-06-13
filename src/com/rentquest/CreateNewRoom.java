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
public class CreateNewRoom extends SwingWorker<String, Object> {
    private String queryStatus = "Room Creation Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    String roomType;
    String plotNo;
    String roomStatus;
    double monthlyRent;
    String roomNo;
    
    
    //CreateNewTenant constructor
    public CreateNewRoom(String roomType, String  plotNo, String roomStatus,double monthlyRent,String roomNo,JPanel rslPanel)
    {
        resultsPanel = rslPanel;
        this.roomNo = roomNo;
        this.plotNo = plotNo;
        this.roomStatus = roomStatus;
        this.monthlyRent = monthlyRent;
        this.roomType = roomType;
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            queryStatus = newRoom(roomType, plotNo, roomStatus,monthlyRent,roomNo,resultsPanel);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Create Room Results", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Create Room Results", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Create Room Results", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Method for creating new Room
     */
    public void createNewRoomQuery(String roomType, String  plotNo, String roomStatus,double monthlyRent,String roomNo) throws SQLException{
        
                String createRoomQuery = "insert into Rooms(Plot_No, Room_Type, Room_Status, Monthly_Rent, Room_No) values (?, ?, ?, ?, ?)";

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
                    queryStatus = "Room Successfully created";
                } 
                catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e)
                {
                    queryStatus = "Another Room with Same Name Exists in the Same Plot!";
                }
                catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Create Room Results", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
        
    }//end of method
    
    public String newRoom(String roomType, String  plotNo, String roomStatus,double monthlyRent,String roomNo,JPanel rslPanel)
    {
        //variables
        int totalNoOfRooms= 0;
        int roomsAlredyCreated = 0;
        boolean plotExists = false;
        boolean otherRoomsExists = false;
        
        
        //check whether the plot exists
        try
        {
            ResultSet rs = database.query("SELECT * FROM Plots WHERE Plot_No = '"+plotNo+"'");
                
                plotExists = rs.next();
                
                    if(plotExists)
                    {
                        totalNoOfRooms= rs.getInt("No_Of_Rooms");
                    }
                    else
                    {
                        queryStatus = "The Plot you Selected does not Exist!";
                    }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Create Room Results", JOptionPane.ERROR_MESSAGE);
        }
        
        //get the number of rooms alredy created in that plot
        try
        {
            ResultSet rs = database.query("SELECT COUNT(*) FROM Rooms WHERE Plot_No = '"+plotNo+"'");
            
                otherRoomsExists = rs.next();
                
                roomsAlredyCreated = rs.getInt(1);
                
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Create Room Results", JOptionPane.ERROR_MESSAGE);
        }
  
        
            //create a new rooms if its plot exists and no of rooms created does not exceed 
            //max no of rooms the plot can have
            if(plotExists)
            {
                if(roomsAlredyCreated <  totalNoOfRooms)
                {
                    //create room
                    try
                    {
                        createNewRoomQuery(roomType, plotNo,roomStatus, monthlyRent, roomNo);
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(resultsPanel, e, "Create Room Results", JOptionPane.ERROR_MESSAGE);
                    }
                }else
                {
                    queryStatus ="Sorry.The Plot is full!";
                }
            }
        
        return queryStatus;
    }//end of newRoom
    
}//end of class
