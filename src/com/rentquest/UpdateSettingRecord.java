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
public class UpdateSettingRecord extends SwingWorker<String, Object> {
    private String queryStatus = "Plot Configuration Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    int overdueDays;
    int bonusDays;
    String plotNo;
    String recordID;
    
    
    //Update plot settings constructor
    public UpdateSettingRecord(int overdueDays, int bonusDays, String plotNo,JPanel rslPanel,String recID)
    {
        resultsPanel = rslPanel;
        this.overdueDays = overdueDays;
        this.bonusDays = bonusDays;
        this.plotNo = plotNo;
        recordID = recID;
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            updatePlotQuery(overdueDays, bonusDays, plotNo, recordID);
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update Plot Setting", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Update Plot Results", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update Plot Results", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Query for configuring a plot
     */
    public void updatePlotQuery(int overdueDays, int bonusDays, String plotNo,String recID) throws SQLException{
        
                String createUserQuery = "UPDATE Settings SET Overdue_Days=?, Bonus_Days=?, Plot_No=?,WHERE Plot_No= '" + recID + "'";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setInt(1, overdueDays);
                    ps.setInt(2, bonusDays);
                    ps.setString(3, plotNo);
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    queryStatus = "Plot Settings Successfully Updated";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Update Plot Results", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
        
    }//end of method
}
