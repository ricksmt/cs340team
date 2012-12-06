package gui.menus;

import gui.Main.GUI;

import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;


public class SaveWindowPanel
    extends JPanel
{
    protected GUI main;
    protected JLabel databaseNameLabel;
    protected JTextField databaseName;
    protected JButton saveButton;

    public SaveWindowPanel(GUI main) {
        //super(new GridBagLayout());
        super(new GridLayout(4, 4));
        this.main = main;
        
        databaseNameLabel = new JLabel("Database name");
        
        databaseName = new JTextField(20);    
        
        //Build the send button
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                saveButtonPressed();                
            }
        
            public void windowClosing(WindowEvent we) 
            {
                setSaveWindowToNull();  
            } 
        });

        
        JPanel databaseNamePanel = new JPanel();
        databaseNamePanel.add(databaseNameLabel);
        databaseNamePanel.add(databaseName);
        this.add(databaseNamePanel);
        
        this.add(saveButton);

    }
    
    private void setSaveWindowToNull()
    {
        //main.getHyPeerWebDebugger().getStandardCommands().setSendWindowToNull();
    }
    
    private void saveButtonPressed()
    {
       
    }
}


