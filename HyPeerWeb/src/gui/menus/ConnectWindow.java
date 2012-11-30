package gui.menus;

import java.awt.Dimension;

import gui.newWindows.PopUpWindow;

import gui.Main.GUI;

public class ConnectWindow extends PopUpWindow {

    public ConnectWindow(GUI main, String title){
        super(main, title);
    }
    
    @Override
    protected void addPanel() {
        panel = new ConnectWindowPanel(main);
        this.setPreferredSize(new Dimension(350, 200));
        this.add(panel);
    }
}
