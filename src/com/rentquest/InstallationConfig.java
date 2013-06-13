/*
 * WARNING:This class is currently Deprecated.
 * 
 * This class enables a user to configure MySQL password and username to be
 * used by the Application to connect to MySQL Server.
 */
package com.rentquest;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**d
 *
 * @author drenpro
 */
public class InstallationConfig extends JFrame{
    
    
    //Parameterless constructor
    public InstallationConfig()
    {
        super("RentQuest Installation...");
        
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new java.awt.Color(144, 89, 33));
        
        //Create title
        JPanel titlePanel  = new JPanel();
        titlePanel.setBackground(new java.awt.Color(144, 89, 33));
        
        JLabel title  = new JLabel("RentQuest MySQL Configuration.");
        
        titlePanel.add(title);
        mainPanel.add(titlePanel,BorderLayout.NORTH);
        
        //create contentPanel
        JPanel labelPanel = new JPanel(new GridLayout(3, 1,5,5));
        labelPanel.setBackground(new java.awt.Color(144, 89, 33));
        
        //create labels for fields
        JLabel MySQLusername =  new JLabel("Enter MySQL Username");
        JLabel password = new JLabel("Enter MySQL Password");
        JLabel confirmPassword = new JLabel("Confirm MySQL Password");
        
        //add the JLabels
        labelPanel.add(MySQLusername);
        labelPanel.add(password);
        labelPanel.add(confirmPassword);
        
        //add labelPanel to the west
        mainPanel.add(labelPanel,BorderLayout.WEST);
        
        JPanel contentPanel = new JPanel(new GridLayout(3, 1,5,5));
        contentPanel.setBackground(new java.awt.Color(144, 89, 33));
        
        //create the fields
        final JTextField usernameTextField = new JTextField();
        final JPasswordField passwordField = new JPasswordField();
        final JPasswordField confirmPasswordField = new JPasswordField();
        
        //add the fields to content panel
        contentPanel.add(usernameTextField);
        contentPanel.add(passwordField);
        contentPanel.add(confirmPasswordField);
        
        //add contentPanel to center of mainPanel
        mainPanel.add(contentPanel,BorderLayout.CENTER);
        
        //create  buttonPanel to add buttons
        JPanel buttPanel = new JPanel();
        buttPanel.setBackground(new java.awt.Color(144, 89, 33));
        
        JButton saveButt = new JButton("Save");
        
        //create annonymous inner class to handle saveButt action events
        saveButt.addActionListener(new ActionListener() {

                String username;
                char password [];
                char confirmpassword [];
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Variable declaration
                
                
                //input validation and conversion
                try
                {
                    username = usernameTextField.getText();
                    password = passwordField.getPassword();
                    confirmpassword = confirmPasswordField.getPassword();
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(mainPanel, e, "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                    //check if the two passwords have the equal length
                    if(password.length==confirmpassword.length)
                    {
                        //passwords have equal length.Do something
                    }else
                    {
                        JOptionPane.showMessageDialog(mainPanel, "Passwords do not have equal length!", "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                
                    //check if the two passwords match
                    for(int i = 0;i < password.length;i++)
                    {
                        if(password[i]== confirmpassword[i])
                        {
                            //password charAt[i] match. Do something
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(mainPanel, "Passwords do not Match", "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                            
                     }//end of for loop
                    
                     String pass = "";
                    for(int i = 0;i< password.length;i++)
                    {
                        pass+=password[i];
                    }
                   
                    
                   
                    
                    ConnectionDetailsDataType configData = new ConnectionDetailsDataType(username, pass);
                    ConnectionDetailsFileManip manip = new ConnectionDetailsFileManip();
                     
                    manip.openFileForWriting();
                    manip.addRecord(configData);
                    manip.closeFile();
                    JOptionPane.showMessageDialog(null, "MySQL Connection Details successfully created", "Connection Details", JOptionPane.INFORMATION_MESSAGE);
                
            }//end of action performed Method
        });//end of annonymous inner class
        
        JButton cancelButt = new JButton("Cancel");
        
        //create annonymous inner class to handle cancelButt action events
        cancelButt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //reset all fields
                usernameTextField.setText(null);
                passwordField.setText(null);
                confirmPasswordField.setText(null);
                
            }//end of actionPerformed Method
        });//end of annonymous inner class
        
        //add buttons to buttPanel
        buttPanel.add(saveButt);
        buttPanel.add(cancelButt);
        
        mainPanel.add(buttPanel,BorderLayout.SOUTH);
        
        //Add mainPanel to JFrame
        add(mainPanel);
        pack();
    }//end of constructor
    
    /*
    //Main method
    public static void main(String args [])
    {
        //set Nimbus look and feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            //do something
            JOptionPane.showMessageDialog(null, e, "Selected Look And Feel not Found", JOptionPane.ERROR_MESSAGE);
            return;
        } 
        //end look and feel code 
        
        InstallationConfig install = new InstallationConfig();
        install.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        install.setSize(400,200);
        install.setVisible(true);
    }
    
     * 
     */
}
