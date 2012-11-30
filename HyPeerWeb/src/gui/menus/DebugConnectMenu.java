package gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gui.Main.GUI;

public class DebugConnectMenu extends JMenuItem implements ActionListener{

    GUI main;
    
    public DebugConnectMenu(GUI main) {
        this.main = main;
        init();
    }

    /**
     * initializes the GUI components
     */
    public void init()
    {
        this.setText("Connect");
        
        this.addActionListener(this);
    }
    
    /**
     * Action when menu item is pressed
     */
    public void actionPerformed(ActionEvent e) {
        ConnectWindow connectWindow = new ConnectWindow(main,"Connect");
        
    }   
}
