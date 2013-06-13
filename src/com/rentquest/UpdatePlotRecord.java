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
public class UpdatePlotRecord extends SwingWorker<String, Object> {
    private String queryStatus = "Plot Record Update Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    String plotName;
    String plotNo;
    int noOfRooms;
    String location;
    int landLordID;
    
    String recordID;
    
    
    //UpdatePlotRecord constructor
    public UpdatePlotRecord(String plotName, String plotNo, int noOfRooms,String location,int landLordID,JPanel rslPanel,String recID)
    {
        resultsPanel = rslPanel;
        this.plotName = plotName;
        this.plotNo = plotNo;
        this.noOfRooms = noOfRooms;
        this.location = location;
        this.landLordID = landLordID;
        recordID = recID;
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            updatePlotRecordQuery(plotName, plotNo, noOfRooms, location, landLordID,recordID);
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update Plot Record", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Update Plot Record", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update Plot Record", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Updating Plot Record
     */
    public void updatePlotRecordQuery(String plotName, String plotNo, int noOfRooms,String location,int landLordID, String recID) throws SQLException{
        
                String createUserQuery = "UPDATE Plots SET Plot_Name=?, Plot_No=?, No_Of_Rooms=?, Location=?, LandLord_ID=? WHERE Plot_No= '" + recID + "'";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setString(1, plotName);
                    ps.setString(2, plotNo);
                    ps.setInt(3, noOfRooms);
                    ps.setString(4, location);
                    ps.setInt(5, landLordID);
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    queryStatus = "Plot Record Successfully updated";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Update Record", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
        
    }//end of method
}
