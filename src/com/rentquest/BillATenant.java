/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import javax.swing.*;
import java.sql.*;

/**
 *
 * @author drenpro
 */
public class BillATenant extends SwingWorker<String, Object> {
    private String queryStatus = "Tenant Billing Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    
    int tenantID;
    double fixedCharge;
    double units;
    double unitCharge;
    String utility;
    Date date;
    double totalbill;
    String billPaymentStatus;
    
    
    //Bill a tenant constructor
    public BillATenant(int tenantId,double fixedCharge, double units ,double unitCharge ,String utility, Date date, double totalbill, JPanel rslPanel,String billPaymentStatus)
    {
        resultsPanel = rslPanel;
        this.tenantID = tenantId;
        this.fixedCharge = fixedCharge;
        this.units = units;
        this.unitCharge = unitCharge;
        this.utility = utility;
        this.date = date;
        this.totalbill =  totalbill;
        this.billPaymentStatus = billPaymentStatus;
        
    }

    @Override
    protected String doInBackground() throws Exception {
        try
        {
            queryStatus = billATenant(tenantID,fixedCharge,units ,unitCharge ,utility,date,totalbill,billPaymentStatus);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Bill A Tenant", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
    
    
    @Override
    protected void done(){
        try
        {
            JOptionPane.showMessageDialog(resultsPanel,get(),"Bill A Tenant", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Bill A Tenant", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * Query for creating new user
     */
    public void billATenantQuery(int tenantId,double fixedCharge, double units ,double unitCharge ,String utility, Date date, double totalbill,String billPaymentStatus) throws SQLException{
        
                String createUserQuery = "insert into Bills(Tenant_ID, Fixed_Charge ,Units, Unit_Charge, Utility, date, Total_Bill,Bill_Payment_Status) values (?, ?, ?, ?, ?, ? ,?, ?)";

                PreparedStatement ps = null;
                try {
                    ps = database.getConnection().prepareStatement(createUserQuery);
                    ps.setInt(1, tenantId);
                    ps.setDouble(2, fixedCharge);
                    ps.setDouble(3, units);
                    ps.setDouble(4, unitCharge);
                    ps.setString(5, utility);
                    ps.setDate(6, date);
                    ps.setDouble(7, totalbill);
                    ps.setString(8, billPaymentStatus);
                    
                    ps.executeUpdate();
                    
                    //query executed successfully
                    queryStatus = "Tenant Successfully Billed";
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(resultsPanel, e, "Bill A Tenant", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ps.close();
                    database.closeConnection();

                }
        
    }//end of method
    
    public String  billATenant(int tenantId,double fixedCharge, double units ,double unitCharge ,String utility, Date date, double totalbill,String billPaymentStatus)
    {
        //variable declaratio
        boolean tenantExists = false;
        
        //check if tenant exists
        try
        {
            ResultSet  rs = database.query("SELECT * FROM Tenants WHERE Tenant_ID= "+tenantId);
            tenantExists =rs.next();
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Bill A Tenant", JOptionPane.ERROR_MESSAGE);
        }
        
        //Bill a tenant if she/he exists
        try
        {
            if(tenantExists)
            {
                billATenantQuery(tenantID,fixedCharge,units ,unitCharge ,utility,date,totalbill,billPaymentStatus);
            }else
            {
                queryStatus = "Tenant Selected Does Not Exist!";
            }
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Bill A Tenant", JOptionPane.ERROR_MESSAGE);
        }
        
        return queryStatus;
    }
}
