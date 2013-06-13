/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author drenpro
 */
public class UpdateBill extends SwingWorker<String, Object> {
    //Variable declaration
    private String queryStatus = "Bill Payment Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    int billID;
    
    
    
    //Bill a tenant constructor
    public UpdateBill(int billID,JPanel rslPanel)
    {
        resultsPanel = rslPanel;
        this.billID = billID;
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            database.runActionQuery("UPDATE Bills SET Bill_Payment_Status ="+"'Payed'"+"WHERE Bill_ID = "+ billID);
            queryStatus = "Bill Successfully Payed";
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Pay Bills", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,get(),"Pay Bill", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Pay Bill", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}//end of class
