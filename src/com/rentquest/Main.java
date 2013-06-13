/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;
/**
 *
 * @author drenpro
 */
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;







//Mine
/**
 *
 * @author drenpro
 */
public class Main {
    
    public static void main(String[] args) {
        
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
        
       final JFrame f = new JFrame();
       
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                SplashWindow s = new SplashWindow(f);
            }
        });
    }
    
}
class SplashWindow extends JWindow
{
    public SplashWindow(Frame f)
    {
        super(f);
        JLabel l = new JLabel();
        l.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/rentquest/Resources/img.jpg")));
        
        
        getContentPane().add(l, BorderLayout.CENTER);
        pack();
        Dimension screenSize =
          Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();
        setLocation(screenSize.width/2 - (labelSize.width/2),
                    screenSize.height/2 - (labelSize.height/2));
        
        
        //mycode
        JPanel southpanel  = new JPanel(new GridLayout(2, 1));
        JLabel status = new JLabel("Starting RentQuest...");
        
        southpanel.add(status);
        
        final JProgressBar bar = new JProgressBar(0, 100);
        bar.setIndeterminate(true);
        southpanel.add(bar);
        getContentPane().add(southpanel,BorderLayout.SOUTH);
       
        final Task task = new Task("My attempt");
        task.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equalsIgnoreCase("progress")) {
                    int progress = task.getProgress();
                    if (progress == 0) {
                        bar.setIndeterminate(true);
                    } else {
                        bar.setIndeterminate(false);
                        bar.setValue(progress);
                        dispose();
                    }
                }
            }
        });
        task.execute();
        setVisible(true);
        screenSize = null;
        labelSize = null;
        
        //progressDialog.setVisible(true);
    }
        
    }
    
    
//}
//end of mine



class Task extends SwingWorker<Void, Void> {

    private static final long SLEEP_TIME = 400;
    private String text;

    public Task(String text) {
        this.text = text;
    }

    @Override
    public Void doInBackground() {
        setProgress(0);
        try 
        {
            //Thread.sleep(SLEEP_TIME);// imitate a long-running task
            MainApp.getMainAppInstance();
            LoginForm.getLoginFormInstance().setVisible(true);
        } 
        catch (Exception e) 
        {
            //Do something
            JOptionPane.showMessageDialog(null, e, "Fatal Error Ocurred While Starting Application!", JOptionPane.ERROR_MESSAGE);
        }
        setProgress(100);
        return null;
    }

    @Override
    public void done() {
        //System.out.println(text + " is done");
        Toolkit.getDefaultToolkit().beep();
    }
}