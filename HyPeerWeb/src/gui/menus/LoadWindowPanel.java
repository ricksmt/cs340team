package gui.menus;

import gui.Main.GUI;

import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;


public class LoadWindowPanel
    extends JPanel
{
    protected GUI main;
    protected JLabel databaseNameLabel;
    protected JTextField databaseName;
    protected JButton loadButton;

    public LoadWindowPanel(GUI main) {
        //super(new GridBagLayout());
        super(new GridLayout(4, 4));
        this.main = main;
        
        databaseNameLabel = new JLabel("Database name");
        
        databaseName = new JTextField(20);    
        
        //Build the send button
        loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                loadButtonPressed();                
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
        
        this.add(loadButton);

    }
    
    private void setSaveWindowToNull()
    {
        //main.getHyPeerWebDebugger().getStandardCommands().setSendWindowToNull();
    }
    
    private void loadButtonPressed()
    {
        main.getHyPeerWeb().reload(databaseName.getText());
    }
}


