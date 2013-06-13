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
import java.security.MessageDigest;
import java.sql.ResultSet;
import javax.swing.*;
import org.jdesktop.swingx.border.DropShadowBorder;

/**
 *
 * @author drenpro
 */
public class EditUserRecord extends SwingWorker<ResultSet, Object> {
    //Variable declaration
    private String queryStatus = "Record Update Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    String recordID;
    boolean recordExists = false;
    
    String testQuery;
    
    //EditUserRecord constructor
    public EditUserRecord(String query, JPanel rslPanel, String recID)
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
       
                JLabel title = new JLabel("Edit User Record");
                title.setFont(new java.awt.Font("DejaVu Sans", 1, 25));
       
       
                titlePanel.add(title);
       
                resultsPanel.add(titlePanel,BorderLayout.NORTH);
       
                JPanel labelPanel = new JPanel(new GridLayout(6, 1, 5, 5));
                labelPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //create field name labels
                JLabel fname = new JLabel("First Name");
                JLabel lname = new JLabel("Last Name");
                JLabel natIdNo = new JLabel("National ID No");
                JLabel username = new JLabel("Username");
                JLabel password = new JLabel("Password");
                JLabel confirmPassword = new JLabel("Confirm Password");
       
                //add labels to labelPanel
                labelPanel.add(fname);
                labelPanel.add(lname);
                labelPanel.add(natIdNo);
                labelPanel.add(username);
                labelPanel.add(password);
                labelPanel.add(confirmPassword);
       
                //add labelPanel to west side of resultsPanel
                resultsPanel.add(labelPanel,BorderLayout.WEST);
       
                //create JTextfields
                final JTextField fnameTextField = new JTextField();
                fnameTextField.setText(get().getString("First_Name"));
                
                final JTextField lnameTextField = new JTextField();
                lnameTextField.setText(get().getString("Last_Name"));
                
                final JTextField natIdNonameTextField = new JTextField();
                natIdNonameTextField.setText(get().getString("National_ID_No"));
       
       
                final JTextField usernameTextField = new JTextField();
                usernameTextField.setText(get().getString("Username"));
                
                final JPasswordField passwardJPasswordField = new JPasswordField();
                final JPasswordField confirmPasswordField = new JPasswordField();
       
                //create textfields panel
                JPanel fieldsPanel = new JPanel(new GridLayout(6, 1, 5, 5));
                fieldsPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //add textfields to textfield panel
                fieldsPanel.add(fnameTextField);
                fieldsPanel.add(lnameTextField);
                fieldsPanel.add(natIdNonameTextField);
                fieldsPanel.add(usernameTextField);
                fieldsPanel.add(passwardJPasswordField);
                fieldsPanel.add(confirmPasswordField);
       
                //add fieldsPanel to center region of resultsPanel
                resultsPanel.add(fieldsPanel,BorderLayout.CENTER);
       
                //create button panel 
                JPanel buttonPanel = new JPanel(new FlowLayout());
                buttonPanel.setBackground(new java.awt.Color(144, 89, 33));
                JButton createUserButt = new JButton("Save Changes");
       
                //create annonymous inner class to handle action events       
                createUserButt.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                
                    //variable declaration
                    String username;
                    byte password[];
                    byte confirmPassword[];
                    String fname;
                    String lname;
                    int natIdNo;
                
                    //input validation and conversion
                    try
                    {
                        username= usernameTextField.getText();
                        fname = fnameTextField.getText();
                        lname = lnameTextField.getText();
                        natIdNo = Integer.parseInt(natIdNonameTextField.getText());
                        password = Utils.makePasswordDigest(usernameTextField.getText(),passwardJPasswordField.getPassword());
                        confirmPassword = Utils.makePasswordDigest(username, confirmPasswordField.getPassword());
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(resultsPanel, e, "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                
                    if(MessageDigest.isEqual(password, confirmPassword))
                    {
                    
                        //check for Blank Strings
                        if(username.equals("") || lname.equals("") || fname.equals(""))
                        {
                            JOptionPane.showMessageDialog(resultsPanel, "A Field cannot be blank!", "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        //create runnable for execution in swing worker thread
                        UpdateUserRecord newUserTask = new UpdateUserRecord(fname, lname, natIdNo, password, username, resultsPanel,recordID);
                        newUserTask.execute();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(resultsPanel, "Passwords Do not Match!", "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                
            }//end of action performed method
        });//end of annonymous inner class
  
       //add buttons to buttonPanel
       buttonPanel.add(createUserButt);
       
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
