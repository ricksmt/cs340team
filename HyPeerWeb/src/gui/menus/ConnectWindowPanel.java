package gui.menus;

import gui.Main.GUI;

import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;


public class ConnectWindowPanel
    extends JPanel
{
    protected GUI main;
    protected JLabel globalObjectIDLabel;
    protected JLabel portLabel;
    protected JLabel localObjectIDLabel;
    protected JTextField globalObjectID;
    protected JTextField port;
    protected JTextField localObjectID;
    protected JButton connectButton;

    public ConnectWindowPanel(GUI main) {
        //super(new GridBagLayout());
        super(new GridLayout(4, 4));
        this.main = main;
        
        globalObjectIDLabel = new JLabel("IP Addess");
        portLabel = new JLabel("Port");
        localObjectIDLabel = new JLabel("Local ObjectID");
        
        globalObjectID = new JTextField(20);    
        port = new JTextField(3);
        localObjectID = new JTextField(20);
        
        //Build the send button
        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                connectButtonPressed();                
            }
        
            public void windowClosing(WindowEvent we) 
            {
                setConnectWindowToNull();  
            } 
        });

        
        JPanel globalObjectIDPanel = new JPanel();
        globalObjectIDPanel.add(globalObjectIDLabel);
        globalObjectIDPanel.add(globalObjectID);
        this.add(globalObjectIDPanel);
        
        JPanel portPanel = new JPanel();
        portPanel.add(portLabel);
        portPanel.add(port);
        this.add(portPanel);
        
        JPanel localObjectIDPanel = new JPanel();
        localObjectIDPanel.add(localObjectIDLabel);
        localObjectIDPanel.add(localObjectID);
        this.add(localObjectIDPanel);
        
        this.add(connectButton);

    }
    
    private void setConnectWindowToNull()
    {
        //main.getHyPeerWebDebugger().getStandardCommands().setSendWindowToNull();
    }
    
    private void connectButtonPressed()
    {
        /*main.getHyPeerWeb().connectToSegment(globalObjectID.getText(),
                Integer.parseInt(port.getText()), Integer.parseInt(localObjectID.getText())); */
        main.getHyPeerWeb().connectToSegment("192.168.2.153",
                55555,-2147483648); 
    }
}


