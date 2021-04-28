package cecs277;

/**
 * FileFrame splitpane for Directory and File panels
 * 
 * @author Arthur Ho & Darius
 */

import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;

public class FileManagerFrame extends JInternalFrame {
	JSplitPane splitpane;
	App myApp;
	DirPanel dirPanel = new DirPanel();
	FilePanel filePanel = new FilePanel();
	
	
	public FileManagerFrame(App myApp){
		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dirPanel, filePanel);
		this.myApp = myApp;
		this.setTitle("C:");
		this.getContentPane().add(splitpane);
		this.setClosable(true);
		this.setMaximizable(true);
		this.setIconifiable(true);
		this.setSize(500,400);
		this.setVisible(true);
	}

}
