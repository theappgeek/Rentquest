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
public class EditPlotRecord extends SwingWorker<ResultSet, Object> {
    //Variable declaration
    private String queryStatus = "Record Update Failed!";
    Database database = Database.getDatabaseInstance();
    JPanel resultsPanel;
    String recordID;
    boolean recordExists = false;
    
    String testQuery;
    
    //EditPlotRecord constructor
    public EditPlotRecord(String query, JPanel rslPanel, String recID)
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
       
                JLabel title = new JLabel("Edit Plot Record");
                title.setFont(new java.awt.Font("DejaVu Sans", 1, 25));
       
       
                titlePanel.add(title);
       
                resultsPanel.add(titlePanel,BorderLayout.NORTH);
       
                JPanel labelPanel = new JPanel(new GridLayout(5, 1, 5, 5));
                labelPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //create field name labels
                JLabel plotname = new JLabel("Plot Name");
                JLabel plotNo = new JLabel("Plot No");
                JLabel noOfRooms = new JLabel("No Of Rooms");
                JLabel location = new JLabel("Location");
                JLabel landLordID = new JLabel("LandLordID");
       
       
                //add labels to labelPanel
                labelPanel.add(plotname);
                labelPanel.add(plotNo);
                labelPanel.add(noOfRooms);
                labelPanel.add(location);
                labelPanel.add(landLordID);
       
                //add labelPanel to west side of resultsPanel
                resultsPanel.add(labelPanel,BorderLayout.WEST);
       
                //create JTextfields
                final JTextField plotnameTextField = new JTextField();
                plotnameTextField.setText(get().getString("Plot_Name"));
                
                final JTextField plotNoTextField = new JTextField();
                plotNoTextField.setText(get().getString("Plot_No"));
                
                final JTextField noOfRoomsTextField = new JTextField();
                noOfRoomsTextField.setText(get().getString("No_Of_Rooms"));
                
                final JTextField locationTextField = new JTextField();
                locationTextField.setText(get().getString("Location"));
                
                final JTextField landLordIdTextField = new JTextField();
                landLordIdTextField.setText(get().getString("LandLord_ID"));
                       
                //create textfields panel
                JPanel fieldsPanel = new JPanel(new GridLayout(5, 1, 5, 5));
                fieldsPanel.setBackground(new java.awt.Color(144, 89, 33));
       
                //add textfields to textfield panel
                fieldsPanel.add(plotnameTextField);
                fieldsPanel.add(plotNoTextField);
                fieldsPanel.add(noOfRoomsTextField);
                fieldsPanel.add(locationTextField);
                fieldsPanel.add(landLordIdTextField);
       
                //add fieldsPanel to center region of resultsPanel
                resultsPanel.add(fieldsPanel,BorderLayout.CENTER);
       
                //create button panel 
                JPanel buttonPanel = new JPanel(new FlowLayout());
                buttonPanel.setBackground(new java.awt.Color(144, 89, 33));
                JButton editPlotButt = new JButton("Save");
       
                //create annonymous inner class to handle action events
                editPlotButt.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                
                    //variable declaration
                    String plotName;
                    String plotNo;
                    int noOfRooms;
                    String location;
                    int landLordID;
                
                    //Input validation code and conversion
                    try
                     {
                        plotName = plotnameTextField.getText();
                        plotNo = plotNoTextField.getText();
                        noOfRooms = Integer.parseInt(noOfRoomsTextField.getText());
                        location = locationTextField.getText();
                        landLordID = Integer.parseInt(landLordIdTextField.getText());
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(resultsPanel, e, "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                        return;                    
                    }
                    
                    //Check for Blank Strings
                    if(plotName.equals("") || plotNo.equals("") || location.equals(""))
                    {
                        JOptionPane.showMessageDialog(resultsPanel, "A Field cannot be Blank!", "Invalid Data Entered!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                
                    //create CreateNewPlot Runnable
                    UpdatePlotRecord newPlotTask = new UpdatePlotRecord(plotName, plotNo, noOfRooms, location, landLordID, resultsPanel,recordID);
                    newPlotTask.execute();
                   
                   
            }//end of Action Event method
        });//end of annonymous inner class
               
       //add buttons to buttonPanel
       buttonPanel.add(editPlotButt);
              
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
            JOptionPane.showMessageDialog(resultsPanel, e, "Update Record", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
