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
public class VacateARoom extends SwingWorker<String, Object> {
    private String queryStatus = "Room Record Update Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;    
    int tenantID;
    
    
    
    //Update Room Record constructor
    public VacateARoom(int tenantID,JPanel resultsPanel)
    {
       this.tenantID = tenantID;    
       this.resultsPanel = resultsPanel;
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            vacateARoom(tenantID);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Vacate a Room", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Vacate a Room", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Vacte a Room", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Method for creating new Room
     */
    public void updateRoomRecord(String  plotNo,String roomNo) throws SQLException{
        
                String createRoomQuery = "UPDATE Rooms SET Room_Status="+"'Vacant'"+" WHERE Room_No= '" +roomNo + "' AND Plot_No = '"+plotNo+"'";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createRoomQuery);
                   
                    ps.executeUpdate();
                    
                    //query executed successfully
                    queryStatus = "Room Successfully Vacated";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Vacate Room", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
        
    }//end of method
    public String vacateARoom(int tenantID)
    {
        //variable declaration
        boolean tenantExists = false;
        String plotNo = null;
        String roomNo = null;
        
        //check if the tenant exists
        try
        {
            ResultSet rs = database.query("SELECT * FROM Tenants WHERE Tenant_ID ="+tenantID);
            tenantExists = rs.next();
            
            //if tenants exists
            if(tenantExists)
            {
                plotNo = rs.getString("Plot_No");
                roomNo = rs.getString("Room_No");
            }
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Vacate Room", JOptionPane.ERROR_MESSAGE);
        }
        
        //vacate a room if tenant exists
         try
        {
              //if tenants exists
            if(tenantExists)
            {
                updateRoomRecord(plotNo, roomNo);
            }else
            {
                queryStatus = "Tenant you Selected Does Not Exist";
            }
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Vacate Room", JOptionPane.ERROR_MESSAGE);
        }
        return queryStatus;
    }
}
