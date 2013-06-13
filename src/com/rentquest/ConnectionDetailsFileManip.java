/*
 * This class is currently Deprecated.
 */
package com.rentquest;

import java.io.*;
import javax.swing.JOptionPane;
/**
 *
 * @author drenpro
 */
public class ConnectionDetailsFileManip {
    
    //Variable declaration
    private ObjectOutputStream output; // outputs data to file
    private ObjectInputStream input;
    String winPathName = "C:\\Rentquest\\config.ser";
    String unixPath ="/home/drenpro/Rentquest/config.ser";
    
    //allow user to specify file name
    public void openFileForWriting()
    {
        try // open file
        {
            output = new ObjectOutputStream(
            new FileOutputStream(new File(winPathName)));
        } // end try
        catch (IOException e )
        {
        JOptionPane.showMessageDialog(null, e, "File Error", JOptionPane.ERROR_MESSAGE);            
        } // end catch
    } // end method openFile
    
    
    public void addRecord(ConnectionDetailsDataType config)
    {
        try
        {
            output.writeObject(config);
            
        }catch(Exception e)
        {
            //do something
            JOptionPane.showMessageDialog(null, e, "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void closeFile()
    {
        try
        {
            if(output != null)
            {
                output.close();
            }
            
            if(input != null)
            {
                input.close();
            }
            
        }catch(Exception e)
        {
            //do something
            JOptionPane.showMessageDialog(null, e, "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
   
    // enable user to select file to open
    public void openFileForReading()
    {
        try // open file
        {
            input = new ObjectInputStream(
            new FileInputStream( winPathName) );
        } // end try
        catch ( IOException e)
        {
            //do something
            JOptionPane.showMessageDialog(null, e, "File Error", JOptionPane.ERROR_MESSAGE);
        } //end catch
    }//end method openFile
    
    
    public ConnectionDetailsDataType readRecord()
    {
        ConnectionDetailsDataType  config = null;
        try
        {
            config = (ConnectionDetailsDataType)input.readObject();
        }catch(Exception e)
        {
            //do something
            JOptionPane.showMessageDialog(null, e, "File Error", JOptionPane.ERROR_MESSAGE);
            
        }
        
        return config;
    }

}//end of class
