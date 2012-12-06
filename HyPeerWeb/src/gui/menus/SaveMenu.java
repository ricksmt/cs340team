package gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gui.Main.GUI;

public class SaveMenu extends JMenuItem implements ActionListener{

    GUI main;
    
    public SaveMenu(GUI main) {
        this.main = main;
        init();
    }

    /**
     * initializes the GUI components
     */
    public void init()
    {
        this.setText("Save");
        
        this.addActionListener(this);
    }
    
    /**
     * Action when menu item is pressed
     */
    public void actionPerformed(ActionEvent e) 
    {
        SaveWindow saveWindow= new SaveWindow(main,"Save");
        main.getHyPeerWeb().saveToDatabase();
        main.getHyPeerWebDebugger().getMapper().getNodeListing().updateList();
        
    }   
}
