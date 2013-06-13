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
public class EditRoomRecord extends SwingWorker<ResultSet, Object> {
    //Variable declaration
    private String queryStatus = "Record Update Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    String recordID;
    boolean recordExists = false;
    
    String testQuery;
    
    //EditRoomRecord constructor
    public EditRoomRecord(String query, JPanel rslPanel, String recID)
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
       
                JLabel title = new JLabel("Edit Room Record");
                title.setFont(new java.awt.Font("DejaVu Sans", 1, 25));
       
       
                titlePanel.add(title);
       
                resultsPanel.add(titlePanel,BorderLayout.NORTH);
       
                JPanel labelPanel = new JPanel(new GridLayout(5, 1, 5, 5));
                labelPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //create field name labels
                JLabel plotNo = new JLabel("Plot No");
                JLabel roomType = new JLabel("Room Type");
                JLabel roomStatus = new JLabel("Room status");
                JLabel monthlyRent = new JLabel("Monthly Rent");
                JLabel roomNo = new JLabel("Room No");
      
       
                //add labels to labelPanel
                labelPanel.add(plotNo);
                labelPanel.add(roomType);
                labelPanel.add(roomStatus);
                labelPanel.add(monthlyRent);
                labelPanel.add(roomNo);

       
                //add labelPanel to west side of resultsPanel
               resultsPanel.add(labelPanel,BorderLayout.WEST);
       
                //create JTextfields
                final JTextField plotNoTextField = new JTextField();
                plotNoTextField.setText(get().getString("Plot_No"));
       
                //create Room type Array
                String roomTypeArr [] = {"BedSitter","Single Bedroom","Double BEdroom","3 Bedroom","4 Bedroom","more.."};
                final JComboBox roomTypeCombo = new JComboBox(roomTypeArr);
                roomTypeCombo.setSelectedItem(get().getString("Room_Type"));
       
       
                //Edit Room status Array
                String roomStatusArr[] = {"Vacant","Occupied","Booked"};
                final JComboBox roomStatusCombo = new JComboBox(roomStatusArr);
                roomStatusCombo.setSelectedItem(get().getString("Room_Status"));
       
                final JTextField monthlyRentTextField = new JTextField();
                monthlyRentTextField.setText(get().getString("Monthly_Rent"));
       
                final JTextField roomNoTextField = new JTextField();
                roomNoTextField.setText(get().getString("Room_No"));
       
       
                //create textfields panel
                JPanel fieldsPanel = new JPanel(new GridLayout(5, 1, 5, 5));
                fieldsPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //add textfields to textfield panel
                fieldsPanel.add(plotNoTextField);
                fieldsPanel.add(roomTypeCombo);
                fieldsPanel.add(roomStatusCombo);
                fieldsPanel.add(monthlyRentTextField);
                fieldsPanel.add(roomNoTextField);

       
                //add fieldsPanel to center region of resultsPanel
                resultsPanel.add(fieldsPanel,BorderLayout.CENTER);
       
                //create button panel 
                JPanel buttonPanel = new JPanel(new FlowLayout());
                buttonPanel.setBackground(new java.awt.Color(144, 89, 33));
                JButton editRoomButt = new JButton("Save Changes");
       
                //add annonymous inner class to handle action events
                editRoomButt.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                
                    //variable declaration
                    String roomType;
                    String plotNo;
                    String roomStatus;
                    double monthlyRent;
                    String roomNo;
                
                    //Input validation and conversion
                    try
                    {
                        roomType = (String)roomTypeCombo.getSelectedItem();
                        plotNo = plotNoTextField.getText();
                        roomStatus = (String)roomStatusCombo.getSelectedItem();
                        monthlyRent = Double.parseDouble(monthlyRentTextField.getText());
                        roomNo = roomNoTextField.getText();
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(resultsPanel, e, "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    //Check for Blank strings
                    if(roomType.equals("") || plotNo.equals("") || roomStatus.equals("") || roomNo.equals(""))
                    {
                       JOptionPane.showMessageDialog(resultsPanel, "A Field cannot be blank!", "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                       return;
                    }
                
                    //create Runnable to execute in SwingWorker Thread
                    UpdateRoomRecord newRoomtask = new UpdateRoomRecord(roomType, plotNo, roomStatus, monthlyRent, roomNo, resultsPanel, recordID);
                    newRoomtask.execute();
                
            }//end of action performed method
        });//end of annonymous inner class
       
       
       //add buttons to buttonPanel
       buttonPanel.add(editRoomButt);
       
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
