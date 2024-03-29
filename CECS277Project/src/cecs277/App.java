package cecs277;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.JComboBox;

import com.sun.source.tree.Tree;

/**
 * App is the organizer for all panels 
 * 
 * @author Darius & Arthur
 *
 */
public class App extends JFrame{
	public static boolean  detailed = true;
	private JPanel panel, topPanel;
	private JMenuBar menubar;
	private JToolBar toolbar, drivebar, statusbar;
	private MyFileList selectedFileList;
	private DefaultMutableTreeNode selectedTreeNode;
	private JTree selectedTree;
	private File copiedFile;
	
	//I'm not sure if comboBox should be declared globally.
	private JComboBox comboBox;
	private JDesktopPane desktop;

	
	private FileManagerFrame myf;
	
	private ArrayList<FileManagerFrame> fmfList = new ArrayList();
	
	// Not sure if this declaration of simple and details are needed as this was declared inside builtToolBar()
	private JButton simple, details;
	private JLabel driveInfo;
	
	private String currentDrive;
	private int currentLayer = 0;
		
	private JButton ok, cancel;
	public App() {
		// TODO: uncomment try-catch block after FileManagerFrame class is created
//		try {
			panel = new JPanel();
			topPanel = new JPanel();
			menubar = new JMenuBar();
			toolbar = new JToolBar();
			drivebar = new JToolBar();
			statusbar = new JToolBar();
			desktop = new JDesktopPane();
			myf = new FileManagerFrame(this);

//		} catch(IOException ex) {
//			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//		}
	}
	
	public void go() {
		this.setTitle("CECS 277 File Manager");
		panel.setLayout(new BorderLayout());
		topPanel.setLayout(new BorderLayout()); // toppanel is nested inside panel to create 4 total "areas"
												// toppanel has menubar + toolbar and panel has toppanel + desktoppane + statusbar
		buildMenuBar();
		
		topPanel.add(menubar, BorderLayout.NORTH);

		panel.add(topPanel, BorderLayout.NORTH);
		this.add(panel);
		
		desktop.add(myf);
		//desktop.addFocusListener(new desktopFocusListener());
		
		
		panel.add(desktop, BorderLayout.CENTER);
		
		buildToolBar();
		// TODO: build toolbar method
		
		topPanel.add(toolbar, BorderLayout.SOUTH);
		
		currentDrive = "C:\\";
		buildStatusBar();
		
		this.setSize(1200, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public String getCurrentDrive() {return currentDrive;}
	public void setCurrentDrive(String newDrive) {currentDrive = newDrive;}
	public MyFileList getSelectedFileList() {return selectedFileList;}
	public void setSelectedFileList(MyFileList selectedFileList) {
		this.selectedFileList = selectedFileList;
	}
	public DefaultMutableTreeNode getSelectedTreeNode() {
		return selectedTreeNode;
	}
	public void setSelectedTreeNode(DefaultMutableTreeNode thisNode) {
		this.selectedTreeNode = thisNode;
	}
	public JTree getSelectedTree() {
		return selectedTree;
	}
	public void setSelectedTree(JTree tree) {
		this.selectedTree = tree;
	}
	/**
	 * places all filemanagerframes from the arraylist into a cascade formation
	 * using the arraylist and brings them to front
	 * 
	 * 
	 */
	public void cascade() {
		int x = 10, y = 10;
		for(FileManagerFrame frame : fmfList) {
			if(frame != null) {
				if(!frame.isClosed()) {
					frame.setLocation(x,y);
					frame.toFront();
					x+=30;
					y+=30;
				}
			}
		}
	}
	
	/**
	 * builds the status bar for the constructor
	 */
	private void buildStatusBar() {
		File fileDrive = new File(currentDrive);
		String label = "Current Drive: " +currentDrive + "    Free Space: " + (fileDrive.getFreeSpace()/1024/1024/1024) +" GB"
				+ "    Used Space: " + ((fileDrive.getTotalSpace()-fileDrive.getFreeSpace())/1024/1024/1024)+" GB"
				+ "    Total Space: "+ (fileDrive.getTotalSpace()/1024/1024/1024)+" GB";
		driveInfo = new JLabel(label);
		statusbar.add(driveInfo);
		panel.add(statusbar, BorderLayout.SOUTH);
	}
	
	/**
	 * updates the statusbar depending on the parameter frame's root directory
	 * 
	 * @param focusedFrame the frame used as a basis for the root drive
	 */
	public void updateStatusBar(FileManagerFrame focusedFrame) {
		File fileDrive = focusedFrame.getRootDrive();
		if (fileDrive == null)
			return;
		String label = "Current Drive: " +focusedFrame.getRootDrive() + "    Free Space: " + (fileDrive.getFreeSpace()/1024/1024/1024) +" GB"
				+ "    Used Space: " + ((fileDrive.getTotalSpace()-fileDrive.getFreeSpace())/1024/1024/1024)+" GB"
				+ "    Total Space: "+ (fileDrive.getTotalSpace()/1024/1024/1024)+" GB";
		driveInfo.setText(label);
	}
	
	private void buildMenuBar() {
		// JMenu creation
		JMenu file, tree, window, helpMenu;
		file = new JMenu("File");
		tree = new JMenu("Tree");
		window = new JMenu("Window");
		helpMenu = new JMenu("Help");
		
		
		// JMenuItems for file menu
		JMenuItem rename = new JMenuItem("Rename");
		JMenuItem copy = new JMenuItem("Copy");
		JMenuItem delete = new JMenuItem("Delete");
		JMenuItem run = new JMenuItem("Run");
		JMenuItem exit = new JMenuItem("Exit");
		//create action listener and assign
		fileMenuActionListener fileMenuAL = new fileMenuActionListener();
		rename.addActionListener(fileMenuAL);
		copy.addActionListener(fileMenuAL);
		delete.addActionListener(fileMenuAL);
		run.addActionListener(fileMenuAL);
		exit.addActionListener(fileMenuAL);
		// add JMenuItems to file menu
		file.add(rename);
		file.add(copy);
		file.add(delete);
		file.add(run);
		file.add(exit);

		
		// JMenuItems for Tree
		JMenuItem expandBranch = new JMenuItem("Expand Branch");
		JMenuItem collapseBranch = new JMenuItem("Collapse Branch");
		// create action listener and assign
		treeMenuActionListener treeMenuAL = new treeMenuActionListener();
		expandBranch.addActionListener(treeMenuAL);
		collapseBranch.addActionListener(treeMenuAL);
		// add JMenuItems to tree menu
		tree.add(expandBranch);
		tree.add(collapseBranch);
		
		
		// JMenuItems for Window
		JMenuItem newWindow = new JMenuItem("New");
		JMenuItem cascadeWindow = new JMenuItem("Cascade");
		// create action listener and assign
		windowMenuActionListener windowMenuAL = new windowMenuActionListener();
		newWindow.addActionListener(windowMenuAL);
		cascadeWindow.addActionListener(windowMenuAL);
		// add JMenuItems to window menu
		window.add(newWindow);
		window.add(cascadeWindow);
		
		
		// JMenuItems for help
		JMenuItem helpItem = new JMenuItem("Help");
		JMenuItem about = new JMenuItem("About");
		// create action listener and assign
		helpMenuActionListener helpMenuAL = new helpMenuActionListener();
		helpItem.addActionListener(helpMenuAL);
		about.addActionListener(helpMenuAL);
		// add JMenuItems to help menu
		helpMenu.add(helpItem);
		helpMenu.add(about);
		
		
		// Adding all pieces to menubar
		menubar.add(file);
		menubar.add(tree);
		menubar.add(window);
		menubar.add(helpMenu);
	}
	
	/*
	 * building tool bar with combo box
	 */
	private void buildToolBar() {
		JButton detailsButton = new JButton("Details");
		JButton simpleButton = new JButton("Simple");
		
		
		File[] files = File.listRoots();
		JComboBox comboBox = new JComboBox(files);

		comboBox.setSize(50, 50);
		JPanel wrapper = new JPanel();
		wrapper.add(comboBox);
		wrapper.add(detailsButton);
		wrapper.add(simpleButton);
		toolbar.add(wrapper);
		
		
		
		comboBoxActionListener comboboxAL = new comboBoxActionListener();
		comboBox.addActionListener(comboboxAL);
		toolBarButtonActionListener toolbarbuttonAL = new toolBarButtonActionListener();
		detailsButton.addActionListener(toolbarbuttonAL);
		simpleButton.addActionListener(toolbarbuttonAL);
	}
	
	/**
	 * ActionListener for file menu items
	 * 
	 *
	 */
	private class fileMenuActionListener implements ActionListener{

		/**
		 * @param e mouse click on menu item
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Rename")) {
				buildRenameDlg();
			}
			
			if(e.getActionCommand().equals("Copy")) {
				buildCopyDlg();
			}
			
			if(e.getActionCommand().equals("Delete")) {
				buildDeleteDlg();
			}
			
			
			if(e.getActionCommand().equals("Run")) {
				run();
			}
		}
	}
	
	public void buildDeleteDlg() {
	        DeleteJDialog deletedlg = new DeleteJDialog(this);
	        deletedlg.setTitle("Deleting");
	        deletedlg.setTextPane(getSelectedFileList().getFileName());
	        deletedlg.setVisible(true);

	}
	
	public void deleteLastSelectedFile() {
		if (getSelectedFileList().getFile().exists()) {
			getSelectedFileList().getFile().delete();
		}
		// this calls DirPanel.java and then makes a new filepanel page somehow
        ((DirPanel)getSelectedTree().getParent().getParent().getParent()).getFileManagerFrame().buildNewList((MyFileNode)getSelectedTreeNode().getUserObject());
	}
	
	/**
	 * first checks if the last selected MyFileList object's file exists
	 * if it does exist, the program attempts to run it
	 */
	public void run() {
		if (!getSelectedFileList().getFile().exists())
			return;
		Desktop userDesktop = Desktop.getDesktop();
		try {
			userDesktop.open(getSelectedFileList().getFile());
		}catch(IOException ex) {
			System.out.println(ex.toString());
		}
	}
	/**
	 * copy dialog creation and handler
	 * Creates a dialog to input a new name for the selected file
	 * @throws IOException 
	 */
	public void buildCopyDlg() {
		// if the selected node is null or directory nothing happens
		if (getSelectedFileList() == null || getSelectedFileList().isDirectory())
			return;
		copiedFile = getSelectedFileList().getFile();
		// calling and naming copy DLG
	    RenameCopyJDialog renamedlg = new RenameCopyJDialog(this);
        renamedlg.setTitle("Copying");
        renamedlg.setCurrentDirectory(getSelectedFileList().getFile().getParent().toString());
        renamedlg.setFromField(getSelectedFileList().getFileName());
        renamedlg.setVisible(true);

        // getting string from user
        String toField = renamedlg.getToField();
        // copy file to new location
        if (toField.equals(""))
        	return;
        try {
        	Files.copy(getSelectedFileList().getFile().toPath(), (new File(toField)).toPath());
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
        
        // this calls DirPanel.java and then makes a new filepanel page somehow
        ((DirPanel)getSelectedTree().getParent().getParent().getParent()).getFileManagerFrame().buildNewList((MyFileNode)getSelectedTreeNode().getUserObject());
	}
	
	
	/**
	 * copy dialog creation and handler
	 * Creates a dialog to input a new name for the selected file
	 * @throws IOException 
	 */
	public void buildPasteDlg() {
		// if the copied file is empty return
		if (copiedFile == null)
			return;
		if (!copiedFile.exists())
			return;
		
		// calling and naming paste DLG
	    RenameCopyJDialog renamedlg = new RenameCopyJDialog(this);
        renamedlg.setTitle("Pasting");
        renamedlg.setCurrentDirectory(copiedFile.getParent().toString());
        String[] fileName = copiedFile.toString().split("[\\\\]");
        renamedlg.setFromField(fileName[fileName.length-1]);
        renamedlg.setToField(getSelectedFileList().getFile().getParent()+"\\"+fileName[fileName.length-1]);
        renamedlg.setVisible(true);

        // getting string from user
        String toField = renamedlg.getToField();
        // copy file to new location
        if (toField.equals(""))
        	return;
        try {
        	Files.copy(copiedFile.toPath(), (new File(toField)).toPath());
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
        
        // this calls DirPanel.java and then makes a new filepanel page somehow
        ((DirPanel)getSelectedTree().getParent().getParent().getParent()).getFileManagerFrame().buildNewList((MyFileNode)getSelectedTreeNode().getUserObject());
	}
	
	/**
	 * Rename dialog creation and handler
	 * Creates a dialog to input a new name for the selected file
	 */
	public void buildRenameDlg() {
		// if the selected node is null or directory nothing happens
		if (getSelectedFileList() == null || getSelectedFileList().isDirectory())
			return;
		
		// calling and naming rename DLG
	    RenameCopyJDialog renamedlg = new RenameCopyJDialog(this);
        renamedlg.setTitle("Renaming");
        renamedlg.setCurrentDirectory(getSelectedFileList().getFile().getParent().toString());
        renamedlg.setFromField(getSelectedFileList().getFileName());
        renamedlg.setVisible(true);

        // getting string from user
        String toField = renamedlg.getToField();
        // rename file to new location
        getSelectedFileList().getFile().renameTo(new File(toField));
        
        // repaint all panels
        for (FileManagerFrame frame : fmfList) {
        	frame.getDirPanel().repaint();
        	frame.getFilePanel().repaint();
        }
        
        // this calls DirPanel.java and then makes a new filepanel page somehow
        ((DirPanel)getSelectedTree().getParent().getParent().getParent()).getFileManagerFrame().buildNewList((MyFileNode)getSelectedTreeNode().getUserObject());
        
	}
	
	/**
	 * ActionListener for tree menu items
	 * 
	 *
	 */
	private class treeMenuActionListener implements ActionListener{

		/**
		 * @param e mouse click on menu item
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (getSelectedTreeNode() == null)
				return;
			if(e.getActionCommand().equals("Expand Branch")) {
				TreePath path = new TreePath(getSelectedTreeNode().getPath());
				getSelectedTree().expandPath(path);
			}
			
			else { // collapse branch
				TreePath path = new TreePath(getSelectedTreeNode().getPath());
				getSelectedTree().collapsePath(path);;
			}
		}
		
	}
	
	/**
	 * ActionListener for window menu items
	 * 
	 *
	 */
	private class windowMenuActionListener implements ActionListener{

		/**
		 * @param e mouse click on menu item
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("New")) {
				FileManagerFrame newFrame = buildFileManagerFrame(new File("C:\\"));
				updateStatusBar(newFrame);
				}
			else { // cascade window
				cascade();
			}
		}
		
	}
	
	/**
	 * ActionListener for help menu items
	 * 
	 *
	 */
	private class helpMenuActionListener implements ActionListener{
		/**
		 * @param e mouse click on menu item
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Help")) {
				// TODO: modify HelpJDialog to include relevant information
				try {
			        HelpJDialog helpdlg = new HelpJDialog();
			        helpdlg.setVisible(true);
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    }
			}
			
			else { // about
				// TODO: modify AboutJDialog to include relevant information
				try {
			        AboutJDialog aboutdlg = new AboutJDialog();
			        aboutdlg.setVisible(true);
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    }
			}
		}
		
	}
	/**
	 * Action listener for combobox actions
	 * 
	 * 
	 */
	private class comboBoxActionListener implements ActionListener{
		/**
		 * 
		 * @param e mouse click on combobox item
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
	
			JComboBox comboBox = (JComboBox) e.getSource();
			// check to see if the source was a combobox, if it is then create a new
			// filemanagerframe based on the selected drive and update the statusbar 
			if(e.getSource()==comboBox) {
			
				File drive = (File)comboBox.getSelectedItem();
				FileManagerFrame newFrame = buildFileManagerFrame(drive);
				updateStatusBar(newFrame);
			}
		 }
	 }
	
	/**
	 * builds a new frame based on chosen drive
	 * 
	 * @param newDrive root drive for the frame
	 * @return the created filemanagerframe object
	 */
	public FileManagerFrame buildFileManagerFrame(File newDrive) {
		FileManagerFrame newFrame = new FileManagerFrame(this, newDrive);
		desktop.add(newFrame);
		newFrame.toFront();
		newFrame.setLocation(30, 30);
		return newFrame;
	}
	
	/**
	 * adds the filemanagerframe to the arraylist of fmf in App.java
	 * 
	 * @param newFileManagerFrame newly constructed filemanagerframe
	 */
	public void addFileManagerFrame(FileManagerFrame newFileManagerFrame) {
		fmfList.add(newFileManagerFrame);
	}
	
	/**
	 * Action listener for toolbarbutton actions
	 * 
	 *
	 */
	private class toolBarButtonActionListener implements ActionListener{
		/**
		 * @param e mouse click on toolbar item
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// if details is pressed then change static variable to details
			// and reload the filepanel
			if(e.getActionCommand().equals("Details")) {
				App.detailed = true;
				myf.getFilePanel().repaint();
			}
			// if simple is pressed then change static variable to simple
			// and reload the filepanel
			else if(e.getActionCommand().equals("Simple")) {
				App.detailed = false;
				myf.getFilePanel().repaint();
			}
		 }
	 }
	
	
}
