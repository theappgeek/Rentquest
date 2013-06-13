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
import javax.swing.*;
import org.jdesktop.swingx.border.DropShadowBorder;

/**
 *
 * @author drenpro
 */
public class EditTenantRecord extends SwingWorker<ResultSet, Object> {
    //Variable declaration
    private String queryStatus = "Record Deletion Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    int recordID;
    boolean recordExists = false;
    
    String testQuery;
    
    //EditTenantRecord constructor
    public EditTenantRecord(String query,int recID, JPanel rslPanel)
    {
        resultsPanel = rslPanel; 
        testQuery = query;
        recordID = recID;
    }

    @Override
    protected ResultSet doInBackground() throws Exception {
        ResultSet rs = null;
        try
        {
            rs = database.query(testQuery);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Delete Record", JOptionPane.ERROR_MESSAGE);
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
                resultsPanel.removeAll();
                resultsPanel.setLayout(new BorderLayout());
                resultsPanel.setBorder(new DropShadowBorder());
       
                JPanel titlePanel = new JPanel(new FlowLayout());
                titlePanel.setBackground(new java.awt.Color(144, 89, 33));
       
                JLabel title = new JLabel("Edit Tenant Details");
                title.setFont(new java.awt.Font("DejaVu Sans", 1, 25));
       
       
                titlePanel.add(title);
       
                resultsPanel.add(titlePanel,BorderLayout.NORTH);
       
                JPanel labelPanel = new JPanel(new GridLayout(8, 1, 5, 5));
                labelPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //create field name labels
                JLabel fname = new JLabel("First Name");
                JLabel lname = new JLabel("Last Name");
                JLabel natIdNo = new JLabel("National ID No");
                JLabel roomNo = new JLabel("Room No");
                JLabel plotNo = new JLabel("Plot No");
                JLabel telNo = new JLabel("Contact(Telephone No)");
                JLabel tenantId = new JLabel("Tenant ID");
                JLabel gender = new JLabel("Gender");
       
                //add labels to labelPanel
                labelPanel.add(fname);
                labelPanel.add(lname);
                labelPanel.add(natIdNo);
                labelPanel.add(roomNo);
                labelPanel.add(plotNo);
                labelPanel.add(telNo);
                labelPanel.add(tenantId);
                labelPanel.add(gender);
       
                //add labelPanel to west side of resultsPanel
                resultsPanel.add(labelPanel,BorderLayout.WEST);
       
                //create JTextfields
                final JTextField fnameTextField = new JTextField();
                fnameTextField.setText(get().getString("First_Name"));
                
                final JTextField lnameTextField = new JTextField();
                lnameTextField.setText(get().getString("Last_Name"));
                
                final JTextField natIdNonameTextField = new JTextField();
                natIdNonameTextField.setText(get().getString("National_ID_No"));
                
                final JTextField roomNoTextField = new JTextField();
                roomNoTextField.setText(get().getString("Room_No"));
                
                final JTextField plotNoTextField = new JTextField();
                plotNoTextField.setText(get().getString("Plot_No"));
                
                final JTextField telNoTextField = new JTextField();
                telNoTextField.setText(get().getString("Telephone_No"));
                
                final JTextField tenantIdTextField = new JTextField("AUTO_INCREMENT");
                tenantIdTextField.setText(get().getString("Tenant_ID"));
                tenantIdTextField.setEnabled(false);
       
                String genderArr [] = {"Male","Female"};
                final JComboBox genderCombo = new JComboBox(genderArr);
                genderCombo.setSelectedItem(get().getString("Gender"));
       
                //create textfields panel
                JPanel fieldsPanel = new JPanel(new GridLayout(8, 1, 5, 5));
                fieldsPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //add textfields to textfield panel
                fieldsPanel.add(fnameTextField);
                fieldsPanel.add(lnameTextField);
                fieldsPanel.add(natIdNonameTextField);
                fieldsPanel.add(roomNoTextField);
                fieldsPanel.add(plotNoTextField);
                fieldsPanel.add(telNoTextField);
                fieldsPanel.add(tenantIdTextField);
                fieldsPanel.add(genderCombo);
       
                //add fieldsPanel to center region of resultsPanel
                resultsPanel.add(fieldsPanel,BorderLayout.CENTER);
       
                //create button panel 
                JPanel buttonPanel = new JPanel(new FlowLayout());
                buttonPanel.setBackground(new java.awt.Color(144, 89, 33));
                JButton saveButt = new JButton("Save");//execute a query
                
                //add annonymous inner class to handle action event
                saveButt.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        
                   String fname = null;
                   String lname = null;
                   int natIdNo = 0;
                   String roomNo = null;
                   String gender = null;
                   String plotNo = null;
                   String telNo = null;
                   
                   try
                   {
                       fname = fnameTextField.getText();
                       lname = lnameTextField.getText();
                       natIdNo= Integer.parseInt(natIdNonameTextField.getText());
                       roomNo = roomNoTextField.getText();
                       gender =  (String)genderCombo.getSelectedItem();
                       plotNo = plotNoTextField.getText();
                       telNo = telNoTextField.getText();
                   }
                   catch(Exception e)
                   {
                       JOptionPane.showMessageDialog(resultsPanel, e, "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                       return;
                   }
                   
                   //check for blank strings
                   if(lname.equals("") || fname.equals("") || roomNo.equals("") || gender.equals("") || plotNo.equals("") || telNo.equals(""))
                   {
                       JOptionPane.showMessageDialog(resultsPanel, "A Field cannot be blank!", "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                       return;
                   }
                   
                   UpdateTenantTable task = new UpdateTenantTable(fname, lname, natIdNo, roomNo, gender, resultsPanel, recordID,plotNo,telNo);
                   task.execute();
                    }//end of action perfomed method
                });//end of annonymous inner class

                //add buttons to buttonPanel
                buttonPanel.add(saveButt);                
       
                //add buttonPanel to resultsPanel
                resultsPanel.add(buttonPanel, BorderLayout.SOUTH);

                resultsPanel.revalidate();
                resultsPanel.repaint();
                
            }
            else
            {
                JOptionPane.showMessageDialog(resultsPanel, "Record Does Not Exist", "Edit Record", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Delete Record", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
