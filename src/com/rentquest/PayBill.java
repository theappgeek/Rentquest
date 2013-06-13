/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Locale;
import javax.swing.*;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.border.DropShadowBorder;

/**
 *
 * @author drenpro
 */
public class PayBill extends SwingWorker<ResultSet, Object> {
    //Variable declaration
    private String queryStatus = "Bill Payment Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    String recordID;
    boolean recordExists = false;
    
    String testQuery;
    int billID;
    
    //EditRoomRecord constructor
    public PayBill(int billID, JPanel rslPanel)
    {
        resultsPanel = rslPanel; 
        this.billID = billID;
        
    }


    @Override
    protected ResultSet doInBackground() throws Exception {
        ResultSet rs = null;
        try
        {
            rs = database.query("SELECT * FROM Bills WHERE Bill_ID = "+billID);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update Record", JOptionPane.ERROR_MESSAGE);
        }
        
        return rs;
    }
    
    
    @Override
    protected void done(){
        try
        {
            recordExists = get().next();
            
            //If record exists display form with data aready filled in
            if(recordExists)
            {
                //GUI code
                resultsPanel.removeAll(); 
                resultsPanel.setLayout(new BorderLayout());
                resultsPanel.setBorder(new DropShadowBorder());
       
                JPanel titlePanel = new JPanel(new FlowLayout());
                titlePanel.setBackground(new java.awt.Color(144, 89, 33));
       
                JLabel title = new JLabel("Your Bill");
                title.setFont(new java.awt.Font("DejaVu Sans", 1, 25));
       
       
                titlePanel.add(title);
       
                resultsPanel.add(titlePanel,BorderLayout.NORTH);
       
                JPanel labelPanel = new JPanel(new GridLayout(9, 1, 5, 5));
                labelPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //create field name labels
                JLabel tenantID = new JLabel("TenantID");
                JLabel fixedCharge = new JLabel("Fixed Charge(KES)");
                JLabel units = new JLabel("No Of Units");
                JLabel unitCharge = new JLabel("Unit Charge(KES)");
                JLabel utility = new JLabel("Utility");
                JLabel date = new JLabel("Date");
                JLabel billPaymentStatus = new JLabel("Bill Payment Status");
                JLabel billId = new JLabel("BillID");
                JLabel total = new JLabel("Total Bill(KES)");
       

       
                //add labels to labelPanel
                labelPanel.add(tenantID);
                labelPanel.add(fixedCharge);
                labelPanel.add(units);
                labelPanel.add(unitCharge);
                labelPanel.add(utility);
                labelPanel.add(date);
                labelPanel.add(billPaymentStatus);
                labelPanel.add(billId);
                labelPanel.add(total);
       
       
                //add labelPanel to west side of resultsPanel
                resultsPanel.add(labelPanel,BorderLayout.WEST);
       
                //create JTextfields
                final JTextField tenantIDTextField = new JTextField();
                tenantIDTextField.setText(get().getString("Tenant_ID"));
                tenantIDTextField.setEnabled(false);
                
                final JTextField fixedTextField = new JTextField();
                fixedTextField.setText(get().getString("Fixed_Charge"));
                fixedTextField.setEnabled(false);
                
                final JTextField unitsTextField = new JTextField();
                unitsTextField.setText(get().getString("Units"));
                unitsTextField.setEnabled(false);
                
                final JTextField unitChargeTextField = new JTextField();
                unitChargeTextField.setText(get().getString("Unit_Charge"));
                unitChargeTextField.setEnabled(false);
                
                final JTextField billPaymentStatusTextField = new JTextField("Not_Payed");
                billPaymentStatusTextField.setText(get().getString("Bill_Payment_Status"));
                billPaymentStatusTextField.setEnabled(false);
       

                final JTextField utilityJCombo = new JTextField();
                utilityJCombo.setText(get().getString("Utility"));
                utilityJCombo.setEnabled(false);
       
                final JXDatePicker billDate = new JXDatePicker(Locale.UK);
                billDate.setDate(get().getDate("Date"));
                billDate.setEnabled(false);
       
                final JTextField billIDTextField = new JTextField();
                billIDTextField.setText(get().getString("Bill_ID"));
                billIDTextField.setEnabled(false);//
       
                final JTextField totalTextField = new JTextField();
                totalTextField.setText(get().getString("Total_Bill"));
                totalTextField.setEnabled(false);
       
                //create textfields panel
                JPanel fieldsPanel = new JPanel(new GridLayout(9, 1, 5, 5));
                fieldsPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //add textfields to textfield panel
                fieldsPanel.add(tenantIDTextField);
                fieldsPanel.add(fixedTextField);
                fieldsPanel.add(unitsTextField);
                fieldsPanel.add(unitChargeTextField);
                fieldsPanel.add(utilityJCombo);
                fieldsPanel.add(billDate);
                fieldsPanel.add(billPaymentStatusTextField);
                fieldsPanel.add(billIDTextField);
                fieldsPanel.add(totalTextField);
    
       
                //add fieldsPanel to center region of resultsPanel
                resultsPanel.add(fieldsPanel,BorderLayout.CENTER);
       
                //create button panel 
                JPanel buttonPanel = new JPanel(new FlowLayout());
                buttonPanel.setBackground(new java.awt.Color(144, 89, 33));
                JButton payBillButt = new JButton("Pay Bill");
       
                //Annonymous inner class for handling Action Events
                payBillButt.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                
                    //variables
                    int billID;
                    
                
                    //Input data conversion and validation
                    try
                    {
                        billID = Integer.parseInt(billIDTextField.getText());
                        
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(resultsPanel, e, "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                
                   //create runnable to execute in different thread
                    UpdateBill payBillTask = new UpdateBill(billID, resultsPanel);
                    payBillTask.execute();
                
                }//end of actionPerformed
            });//end of annonymous inner class
       
            JButton printButt = new JButton("Print Bill");
       
            //add buttons to buttonPanel
            buttonPanel.add(payBillButt);
            buttonPanel.add(printButt);
       
            //add buttonPanel to resultsPanel
            resultsPanel.add(buttonPanel, BorderLayout.SOUTH);

            resultsPanel.revalidate();
            resultsPanel.repaint();
                
                
            }
            else
            {
                JOptionPane.showMessageDialog(resultsPanel, "Bill Does Not Exist", "Pay Bill", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Pay Bill", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
