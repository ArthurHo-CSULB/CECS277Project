package cecs277;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import javax.swing.JComboBox;

import com.sun.source.tree.Tree;

/**
 * App is the organizer for all panels 
 * 
 * @author Darius & Arthur
 *
 */
public class App extends JFrame{
	private JPanel panel, topPanel;
	private JMenuBar menubar;
	private JToolBar toolbar, drivebar, statusbar;
	
	//I'm not sure if comboBox should be declared globally.
	private JComboBox comboBox;
	
	private JDesktopPane desktop;

	
	private FileManagerFrame myf;
	
	
	private JButton simple, details;
	private String currentDrive;
		
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
		
		panel.add(desktop, BorderLayout.CENTER);
		
		buildToolBar();
		// TODO: build toolbar method
		
		topPanel.add(toolbar, BorderLayout.SOUTH);
		
		currentDrive = "C:\\";
		buildStatusBar();
		
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public String getCurrentDrive() {return currentDrive;}
	public void setCurrentDrive(String newDrive) {currentDrive = newDrive;}
	
	private void buildStatusBar() {
		File fileDrive = new File(currentDrive);
		String label = "Current Drive: " +currentDrive + "    Free Space: " + (fileDrive.getFreeSpace()/1024/1024/1024) +" GB"
				+ "    Used Space: " + ((fileDrive.getTotalSpace()-fileDrive.getFreeSpace())/1024/1024/1024)+" GB"
				+ "    Total Space: "+ (fileDrive.getTotalSpace()/1024/1024/1024)+" GB";
		JLabel size = new JLabel(label);
		statusbar.add(size);
		panel.add(statusbar, BorderLayout.SOUTH);
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
		
		JComboBox comboBox = new JComboBox(new String[] {"test1", "test2", "test3"});
		// I declared this both globally and locally. Locally is just for a test,
		//but glob
		
		comboBox.setSize(50, 50);
		toolbar.add(comboBox);
		toolbar.add(detailsButton);
		toolbar.add(simpleButton);
		
		//toolBarActionListener toolBarAL = new toolBarActionListener();
		//toolbar.addActionListener(toolBarAL);
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
			if(e.getActionCommand().equals("Rename"))
				// TODO: ADD RENAME FILE IMPLEMENTATION
				System.out.println("Rename");
			
			else if(e.getActionCommand().equals("Copy"))
				// TODO: ADD COPY FILE IMPLEMENTATION
				System.out.println("Copy");
			
			else if(e.getActionCommand().equals("Delete"))
				// TODO: ADD DELETE FILE IMPLEMENTATION
				System.out.println("Delete");
			
			else if(e.getActionCommand().equals("Run"))
				// TODO: ADD RUN FILE IMPLEMENTATION
				System.out.println("Run");
			
			else // exit item
				System.exit(0);
		}
		
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
			if(e.getActionCommand().equals("Expand Branch"))
				// TODO: ADD expand branch IMPLEMENTATION
				System.out.println("Expand Branch");
			
			else // collapse branch
				// TODO: ADD COLLAPSE BRANCH IMPLEMENTATION
				System.out.println("Collapse Branch");
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
			if(e.getActionCommand().equals("New"))
				// TODO: ADD expand branch IMPLEMENTATION
				System.out.println("New Window");
			
			else // cascade window
				// TODO: ADD cascade window IMPLEMENTATION
				System.out.println("Cascade Window");
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
				System.out.println("Help");
			}
			
			else { // about
				// TODO: modify AboutJDialog to include relevant information
				try {
			        AboutJDialog aboutdlg = new AboutJDialog();
			        aboutdlg.setVisible(true);
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    }
				System.out.println("About");
			}
		}
		
	}
	
	private class toolBarActionListener implements ActionListener{
		/**
		 * @param e mouse click on menu item
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO: tool bar action listener IMPLEMENTATION
			if(comboBox.getSelectedItem().toString().equals("test1")) {
				//This is just place holder code. I don't think it'll work
				//for our final application.
				System.out.println("test1");
			}
			
			if(e.getActionCommand().equals("Details"))
				System.out.println("Details");
			
			else if(e.getActionCommand().equals("Simple"))
				System.out.println("Simple");
			
		 }
	 }
	
	
}
