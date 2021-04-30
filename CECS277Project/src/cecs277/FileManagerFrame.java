package cecs277;

import java.io.File;

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
	DirPanel dirPanel = new DirPanel(this);
	FilePanel filePanel = new FilePanel(this);
	
	/**
	 * Initial filemanagerframe created when app is started
	 * 
	 * @param myApp the class that called this constructor
	 */
	public FileManagerFrame(App myApp){
		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dirPanel, filePanel);
		this.myApp = myApp;
		this.setTitle("C:");
		this.getContentPane().add(splitpane);
		this.setClosable(true);
		this.setMaximizable(true);
		this.setIconifiable(true);
		this.setSize(700,600);
		this.setVisible(true);
	}
	
	/**
	 * 2 parameter constructor for filemanagerframe so that you can have different drives
	 * 
	 * @param myApp the class that called this constructor
	 * @param file the new file which the dirpanel will be centered around
	 */
	public FileManagerFrame(App myApp, File file){
		dirPanel = new DirPanel(this, file);
		filePanel = new FilePanel(this, file);
		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dirPanel, filePanel);
		this.myApp = myApp;
		this.setTitle(file.toString());
		this.getContentPane().add(splitpane);
		this.setClosable(true);
		this.setMaximizable(true);
		this.setIconifiable(true);
		this.setSize(700,600);
		this.setVisible(true);
	}
	
	public void buildNewList(MyFileNode focusedFileNode) {
		filePanel.buildNewList(focusedFileNode);
	}
	
	public String getCurrentDrive() {
		return myApp.getCurrentDrive();
	}

}
