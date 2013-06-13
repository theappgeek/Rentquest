/*
 * This class allows one to edit a Plot's Setting. 
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
public class EditSettingRecord extends SwingWorker<ResultSet, Object> {
    //Variable declaration
    private String queryStatus = "Setting Update Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    String recordID;
    boolean recordExists = false;
    
    String testQuery;
    
    //EditTenantRecord constructor
    public EditSettingRecord(String query, JPanel rslPanel, String recID)
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
       
                JLabel title = new JLabel("Configure A Plot's Settings");
                title.setFont(new java.awt.Font("DejaVu Sans", 1, 25));
       
       
                titlePanel.add(title);
       
                resultsPanel.add(titlePanel,BorderLayout.NORTH);
       
                JPanel labelPanel = new JPanel(new GridLayout(3, 1, 5, 5));
                labelPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //create field name labels
                JLabel overdueDays = new JLabel("Set Max Rent Overdue Days");
                JLabel percentageFine = new JLabel("Set Percentage Fine");
                JLabel plotNo = new JLabel("Plot No");
                JLabel settingsID = new JLabel("SettingsID");

       
                //add labels to labelPanel
                labelPanel.add(overdueDays);
                labelPanel.add(percentageFine);
                labelPanel.add(plotNo);
 
       
                //add labelPanel to west side of resultsPanel
                resultsPanel.add(labelPanel,BorderLayout.WEST);
       
                //create JTextfields
                final JTextField overdueDaysTextField = new JTextField();
                overdueDaysTextField.setText(get().getString("Overdue_Days"));
                
                final JTextField bonusDaysTextField = new JTextField();
                bonusDaysTextField.setText(get().getString("Bonus_Days"));
                
                final JTextField plotNoTextField = new JTextField();
                plotNoTextField.setText(get().getString("Plot_No"));
                
                
                //create textfields panel
                JPanel fieldsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
                fieldsPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //add textfields to textfield panel
                fieldsPanel.add(overdueDaysTextField);
                fieldsPanel.add(bonusDaysTextField);
                fieldsPanel.add(plotNoTextField);
                
      
    
       
                //add fieldsPanel to center region of resultsPanel
                resultsPanel.add(fieldsPanel,BorderLayout.CENTER);
       
                //create button panel 
                JPanel buttonPanel = new JPanel(new FlowLayout());
                buttonPanel.setBackground(new java.awt.Color(144, 89, 33));
                JButton configurePloButt = new JButton("Save Settings");
       
                //add action listener to handle action events
                configurePloButt.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                
                    //variable declaration
                    int overdueDays;
                    int bonusDays;
                    String plotNo;
                
                    //Input validation and conversion
                    try
                    {
                        overdueDays = Integer.parseInt(overdueDaysTextField.getText());
                        bonusDays = Integer.parseInt(bonusDaysTextField.getText());
                        plotNo = plotNoTextField.getText();
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(resultsPanel, e, "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    //Check for Blank Strings
                    if(plotNo.equals(""))
                    {
                        JOptionPane.showMessageDialog(resultsPanel,"A field cannot be Blank!","Invalid Data Entered!",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                
                    //Create configure settings runnable to run in swing worker thread
                    UpdateSettingRecord configtask = new UpdateSettingRecord(overdueDays, bonusDays, plotNo, resultsPanel,recordID);
                    configtask.execute();
                
                
            }//end of action performed method
        });//end of annonymous inner class
       
       //add buttons to buttonPanel
       buttonPanel.add(configurePloButt);
       
       
       //add buttonPanel to resultsPanel
       resultsPanel.add(buttonPanel, BorderLayout.SOUTH);
       
       resultsPanel.revalidate();
       resultsPanel.repaint();
                
            }
            else
            {
                JOptionPane.showMessageDialog(resultsPanel, "Record Does Not Exist", "Update Record", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(resultsPanel, e, "Update Record", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
