/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author drenpro
 */
public class DeleteRecord extends SwingWorker<String, Object> {
    private String queryStatus = "Record Deletion Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    String query;
    String testQuery;
    
    //DeleteRecord constructor
    public DeleteRecord(String query, String testQuery,JPanel rslPanel)
    {
        resultsPanel = rslPanel;
        this.query = query;
        this.testQuery = testQuery;
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            deleteRecord(query,testQuery);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Delete Record", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,get(),"Delete Record", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Delete Record", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void deleteRecord(String query,String testQuery){ 
        //variable declaration
        boolean recordExists = false;
        
            try
            {
                ResultSet rs = database.query(testQuery);
                recordExists = rs.next();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(resultsPanel, e, "Delete Record", JOptionPane.ERROR_MESSAGE);
            }
                
                //delete record if it exists
                if(recordExists)
                {
                    try
                    {
                        database.runActionQuery(query);
                        queryStatus = "Record Successfully Deleted!";
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(resultsPanel, e, "Delete Record", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    queryStatus ="Record Does Not Exist!";
                }
            
           
    }//end of delete record method
}
