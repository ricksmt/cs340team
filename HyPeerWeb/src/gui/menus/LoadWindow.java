package gui.menus;

import java.awt.Dimension;

import gui.newWindows.PopUpWindow;

import gui.Main.GUI;

public class LoadWindow extends PopUpWindow {

    public LoadWindow(GUI main, String title){
        super(main, title);
    }
    
    @Override
    protected void addPanel() {
        panel = new LoadWindowPanel(main);
        this.setPreferredSize(new Dimension(350, 200));
        this.add(panel);
    }
}
