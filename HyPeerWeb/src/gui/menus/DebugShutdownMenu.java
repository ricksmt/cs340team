package gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gui.Main.GUI;

public class DebugShutdownMenu extends JMenuItem implements ActionListener{

    GUI main;
    
    public DebugShutdownMenu(GUI main) {
        this.main = main;
        init();
    }

    /**
     * initializes the GUI components
     */
    public void init()
    {
        this.setText("Migrate");
        
        this.addActionListener(this);
    }
    
    /**
     * Action when menu item is pressed
     */
    public void actionPerformed(ActionEvent e) 
    {
        main.getHyPeerWeb().migrateNodes();
        main.getHyPeerWeb().disconnectFromSegment();
        main.getHyPeerWebDebugger().getMapper().getNodeListing().updateList();
        
    }   
}
