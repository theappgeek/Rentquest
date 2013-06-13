/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import java.sql.Date;
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
public class PayRent extends SwingWorker<String, Object> {
    private String queryStatus = "Rent Payment Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    double amount;
    Date dateOfPayment;
    int tenantID;
    String rentType;
    
    //CreateNewTenant constructor
    public PayRent(double amount, Date dateOfPayment,int tenantId,JPanel rslPanel,String rentType)
    {
        resultsPanel = rslPanel;
        this.amount = amount;
        this.dateOfPayment = dateOfPayment;
        this.tenantID = tenantId;
        this.rentType = rentType;
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            queryStatus =payRent(amount,dateOfPayment,tenantID,rentType);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Pay Rent", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,queryStatus,"Pay Rent", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "PayRent", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Paying Rent
     */
    public boolean payRentQuery(double amount, Date dateOfPayment,int tenantId,String rentType) throws SQLException{
        
        //variable
        boolean isQueryExecutedSuccessfully =  false;
        
                String createUserQuery = "insert into PayRent(Tenant_ID, Amount, Date_Of_Payment, Rent_Type ) values (?, ?, ?, ?)";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setInt(1,tenantId );
                    ps.setDouble(2, amount);
                    ps.setDate(3, dateOfPayment);
                    ps.setString(4, rentType);
                    
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    isQueryExecutedSuccessfully = true;
                    queryStatus = "Rent Payed Successfully";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Pay Rent", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
                return isQueryExecutedSuccessfully;
        
    }//end of method
    
    
    /*
     * Paying Rent
     */
    public boolean payRentQueryAll(double amount, Date dateOfPayment,int tenantId,String rentType) throws SQLException{
        
        //variable
        boolean isQueryExecutedSuccessfully =  false;
        
                String createUserQuery = "insert into PayRentAll(Tenant_ID, Amount, Date_Of_Payment, Rent_Type ) values (?, ?, ?, ?)";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setInt(1,tenantId );
                    ps.setDouble(2, amount);
                    ps.setDate(3, dateOfPayment);
                    ps.setString(4, rentType);
                    
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    isQueryExecutedSuccessfully = true;
                    queryStatus = "Rent Payed Successfully";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Pay Rent", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    //database.closeConnection();

                }
                return isQueryExecutedSuccessfully;
        
    }//end of method
    
    
    public void updateRent(double amount, Date dateOfPayment,int tenantId,String rentType) throws SQLException{
        
        //variable
        boolean isQueryExecutedSuccessfully =  false;
        
                String createUserQuery = "UPDATE PayRent SET Amount= ?, Date_Of_Payment= ?, Rent_Type= ? WHERE Tenant_ID= "+tenantId;

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    
                    ps.setDouble(1, amount);
                    ps.setDate(2, dateOfPayment);
                    ps.setString(3, rentType);
                    
                    
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    isQueryExecutedSuccessfully = true;
                    queryStatus = "Rent Successfully Updated";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Pay Rent", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
                //return isQueryExecutedSuccessfully;
        
    }//end of method
    
    /*
     * 
     */
    public String payRent(double amount, Date dateOfPayment,int tenantId,String rentType)
    {
        //variable declaration
        double fixedMonthlyRent=0.0;
        boolean tenantExists = false;
        boolean roomExists = false;
        String plotNo= null;
        String roomNo = null;
        String roomStatus = null;
        boolean hasPayedRentBefore = false;
        
        //check if tenant exists
        try
        {
            ResultSet rs = database.query("SELECT * FROM Tenants WHERE Tenant_ID = "+ tenantId);
            tenantExists = rs.next();
            
                //if tenant exists
                if(tenantExists)
                {
                    plotNo = rs.getString("Plot_No");
                    roomNo = rs.getString("Room_No");
                    
                    //fetch rooms mothly rent
                    ResultSet res = database.query("SELECT * FROM Rooms WHERE Plot_No ='"+plotNo+"' AND Room_No = '"+roomNo+"'");
                    roomExists = res.next();
                    
                        if(roomExists)
                        {
                            fixedMonthlyRent = Double.parseDouble(res.getString("Monthly_Rent"));
                            roomStatus = res.getString("Room_Status");
                        }  
                        
                        //check if tenant has payed rent before
                ResultSet resset = database.query("SELECT * FROM PayRent WHERE Tenant_ID = "+ tenantId);
                hasPayedRentBefore = resset.next();
                
                }
                
                
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Pay Rent", JOptionPane.ERROR_MESSAGE);
        }
        
        //payrent if tenant exists and amount >= fixed mothly rent
        try
        {
            if(tenantExists)
            {
                //check if the amount being payed is less than fixed monthly rent
                if(amount >= fixedMonthlyRent)
                {
                    //if roomStatus is Vacant it means tenants has aready vacated the room
                    if(roomStatus.equalsIgnoreCase("Vacant"))
                    {
                        queryStatus = "Tenant has Vacated the room";
                    }
                    else
                    {
                        if(hasPayedRentBefore)
                        {
                            updateRent(amount,dateOfPayment,tenantId,rentType);
                            payRentQueryAll(amount,dateOfPayment,tenantId,rentType);
                        }
                        else
                        {
                            //if rent is successfully payed update Rooms table and set that room status to occupied
                            payRentQuery(amount,dateOfPayment,tenantId,rentType);
                            payRentQueryAll(amount,dateOfPayment,tenantId,rentType);
                            database.runActionQuery("UPDATE Rooms SET Room_Status = "+"'Occupied'"+"WHERE Plot_No ='"+plotNo+"' AND Room_No = '"+roomNo+"'");
                        }
                    }
                }else
                {
                    queryStatus ="Amount you are paying is less than fixed monthly rent for your Room";
                }
                
            }else
            {
                queryStatus = "Sorry.Tenant does not exist";
            }
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Pay Rent", JOptionPane.ERROR_MESSAGE);
        }
        
        
        return queryStatus;
    }
}
