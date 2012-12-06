package gui.menus;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import gui.Main.GUI;

/**
 * Main menu bar for the GUI debugger
 * 
 * @author Matthew Smith
 *
 */
public class DebugMenu extends JMenuBar {
	
	/** Root of the GUI */
	GUI main;
	
	/** File Menu */
	JMenu file;
	
	/** Help Menu */
	JMenu help;
	
	/**Connect */
	JMenuItem connect;
	
	/**Shut down*/
	JMenuItem shutdown;
	
	/**
	 * Create a Debug Menu
	 * @param main
	 */
	public DebugMenu(GUI main)
	{
		this.main = main;
		
		init();
	}
	
	/**
	 * Initializes the GUI components
	 */
	public void init()
	{
		file = new DebugFileMenu(main);
		this.add(file);
		
		help = new DebugHelpMenu(main);
		this.add(help);
		
		connect = new DebugConnectMenu(main);
		this.add(connect);
		
	    shutdown = new DebugShutdownMenu(main);
	    this.add(shutdown);
	}
}
