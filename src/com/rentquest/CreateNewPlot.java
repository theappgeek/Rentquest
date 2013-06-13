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
public class CreateNewPlot extends SwingWorker<String, Object> {
    private String queryStatus = "Plot Creation Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    String plotName;
    String plotNo;
    int noOfRooms;
    String location;
    int landLordID;
   
    
    
    //CreateNewPlot constructor
    public CreateNewPlot(String plotName, String plotNo, int noOfRooms,String location,int landLordID,JPanel rslPanel)
    {
        resultsPanel = rslPanel;
        this.plotName = plotName;
        this.plotNo = plotNo;
        this.noOfRooms = noOfRooms;
        this.location = location;
        this.landLordID = landLordID;
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
          queryStatus = newPlot(plotName, plotNo, noOfRooms,location,landLordID,resultsPanel);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Create Plot Results", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Create Plot Results", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Create Plot Results", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Query for creating new user
     */
    public boolean createNewPlotQuery(String plotName, String plotNo, int noOfRooms,String location,int landLordID) throws SQLException{
        
        //variable declaration
        boolean isQueryExecutionSuccessful = false;
        
                String createUserQuery = "insert into Plots(Plot_Name, Plot_No, No_Of_Rooms, Location, LandLord_ID) values (?, ?, ?, ?, ?)";

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
                    isQueryExecutionSuccessful = true;
                    queryStatus = "Plot Successfully created";
                }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e)
                {
                    queryStatus = "Plot Already Exists";
                }                
                catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Create Plot Results$", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
                
                return isQueryExecutionSuccessful;
        
    }//end of method
    
    public String newPlot(String plotName, String plotNo, int noOfRooms,String location,int landLordID,JPanel rslPanel)
    {
        //fetch no of plots for the given landlord_ID form landlords table
        //variable declaration
        boolean recordExists = false;
        int noOfPlots = 0;
                
            try
            {
                ResultSet rs = database.query("SELECT * FROM LandLords WHERE LandLord_ID = "+landLordID);
                
                recordExists = rs.next();
                
                    if(recordExists)
                    {
                        noOfPlots= rs.getInt("No_Of_Plots");
                    }
                
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(resultsPanel, e, "Create Plot", JOptionPane.ERROR_MESSAGE);
            }
                
                
                //Create new Plot if Landlord exists
                if(recordExists)
                {
                                        
                    try
                    {
                        //create new plot
                        if(createNewPlotQuery(plotName, plotNo, noOfRooms, location, landLordID))
                        {
                            //update  number of plots field in landLords table
                            int newNoOfPlts=  noOfPlots+1;
                            database.runActionQuery("UPDATE LandLords SET No_Of_Plots = "+newNoOfPlts+" WHERE LandLord_ID = "+landLordID);                            
                        }
                         
                        
                        
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(resultsPanel, e, "Create Plot", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    queryStatus ="LandLord Does Not Exist!";
                }
            
        return queryStatus;
    }
}
