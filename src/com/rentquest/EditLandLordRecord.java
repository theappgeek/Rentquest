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
public class EditLandLordRecord extends SwingWorker<ResultSet, Object> {
    //Variable declaration
    private String queryStatus = "LandLord Record Update Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    int recordID;
    boolean recordExists = false;
    
    String testQuery;
    
    //EditTenantRecord constructor
    public EditLandLordRecord(String query,int recID, JPanel rslPanel)
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
       
                JLabel title = new JLabel("Edit LandLord Details");
                title.setFont(new java.awt.Font("DejaVu Sans", 1, 25));
       
       
                titlePanel.add(title);
       
                resultsPanel.add(titlePanel,BorderLayout.NORTH);
       
                JPanel labelPanel = new JPanel(new GridLayout(7, 1, 5, 5));
                labelPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //create field name labels
                JLabel fname = new JLabel("First Name");
                JLabel lname = new JLabel("Last Name");
                JLabel natIdNo = new JLabel("National ID No");
                JLabel noOfPlots = new JLabel("No of Plots");
                JLabel telNo = new JLabel("Telephone No");
                JLabel landLordId = new JLabel("LandLord ID");
                JLabel gender = new JLabel("Gender");
       
                //add labels to labelPanel
                labelPanel.add(fname);
                labelPanel.add(lname);
                labelPanel.add(natIdNo);
                labelPanel.add(noOfPlots);
                labelPanel.add(telNo);
                labelPanel.add(landLordId);
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
                
                final JTextField noOfPlotsTextField = new JTextField();
                noOfPlotsTextField.setText(get().getString("No_Of_Plots"));
       
                final JTextField telNoTextField = new JTextField();
                telNoTextField.setText(get().getString("Telephone_No"));
       
                final JTextField landLordIdTextField = new JTextField("AUTO_INCREMENT");
                landLordIdTextField.setText(get().getString("LandLord_ID"));
                landLordIdTextField.setEnabled(false);
       
                String genderArr [] = {"Male","Female"};
                final JComboBox genderCombo = new JComboBox(genderArr);
                genderCombo.setSelectedItem(get().getString("Gender"));
       
                //create textfields panel
                JPanel fieldsPanel = new JPanel(new GridLayout(7, 1, 5, 5));
                fieldsPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //add textfields to textfield panel
                fieldsPanel.add(fnameTextField);
                fieldsPanel.add(lnameTextField);
                fieldsPanel.add(natIdNonameTextField);
                fieldsPanel.add(noOfPlotsTextField);
                fieldsPanel.add(telNoTextField);
                fieldsPanel.add(landLordIdTextField);
                fieldsPanel.add(genderCombo);
       
               //add fieldsPanel to center region of resultsPanel
                resultsPanel.add(fieldsPanel,BorderLayout.CENTER);
       
                //create button panel 
                 JPanel buttonPanel = new JPanel(new FlowLayout());
                buttonPanel.setBackground(new java.awt.Color(144, 89, 33));
                JButton saveButt = new JButton("Save");//execute query
                
                saveButt.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        
                        //variable declaration
                        String fname;
                        String lname;
                        int natIdNo;
                        int noOfPlots;
                        String telNo;
                        String gender;
                
                        //Input validation and conversion
                        try
                        {
                            fname = fnameTextField.getText();
                            lname = lnameTextField.getText();
                            natIdNo = Integer.parseInt(natIdNonameTextField.getText());
                            noOfPlots = Integer.parseInt(noOfPlotsTextField.getText());
                            telNo = telNoTextField.getText();
                            gender = (String)genderCombo.getSelectedItem();
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(resultsPanel, e, "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                            return;                    
                        }
                        
                        //check for Blank Strings
                        if(lname.equals("") || fname.equals("") || telNo.equals("") || gender.equals(""))
                        {
                            JOptionPane.showMessageDialog(resultsPanel, "A Field cannot be blank!", "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                
                        //Create CreateNewLandlord Runnable 
                        UpdateLandLordRecord landLordTask = new UpdateLandLordRecord(fname, lname, natIdNo, noOfPlots, telNo, gender, resultsPanel,recordID);
                        landLordTask.execute();
                    }
                });
                              
       
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
            JOptionPane.showMessageDialog(resultsPanel, e, "Edit Record", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
