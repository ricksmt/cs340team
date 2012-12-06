package gui.menus;

import java.awt.Dimension;

import gui.newWindows.PopUpWindow;

import gui.Main.GUI;

public class SaveWindow extends PopUpWindow {

    public SaveWindow(GUI main, String title){
        super(main, title);
    }
    
    @Override
    protected void addPanel() {
        this.setPreferredSize(new Dimension(350, 200));
        this.add(panel);
    }
}
