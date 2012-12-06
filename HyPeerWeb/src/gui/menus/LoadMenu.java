package gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gui.Main.GUI;

public class LoadMenu extends JMenuItem implements ActionListener{

    GUI main;
    
    public LoadMenu(GUI main) {
        this.main = main;
        init();
    }

    /**
     * initializes the GUI components
     */
    public void init()
    {
        this.setText("Load");
        
        this.addActionListener(this);
    }
    
    /**
     * Action when menu item is pressed
     */
    public void actionPerformed(ActionEvent e) 
    {
        main.getHyPeerWeb().reload();
        main.getHyPeerWebDebugger().getMapper().getNodeListing().updateList();
        
    }   
}
