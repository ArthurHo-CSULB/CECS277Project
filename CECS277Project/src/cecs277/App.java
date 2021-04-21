package cecs277;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * App is the organizer for all panels 
 * 
 * @author Darius
 *
 */
public class App extends JFrame{
	JPanel panel, topPanel;
	JMenuBar menubar;
	JToolBar toolbar, drivebar, statusbar;
	JDesktopPane desktop;
	
	//FileManagerFrame myf, myf2;
	// TODO: Create FileManagerFrame class to put into desktop frame
	
	JButton simple, details;
	String currentDrive;
	
	JButton ok, cancel;
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
//			myf = new FileManagerFrame(this);
//			myf.setSize(750, 500);
//		} catch(IOException ex) {
//			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//		}
	}
	
	public void go() {
		this.setTitle("CECS 277 File Manager");
		panel.setLayout(new BorderLayout());
		topPanel.setLayout(new BorderLayout()); // toppanel is nested inside panel to create 4 total "areas"
												// toppanel has menubar + toolbar and panel has toppanel + desktoppane + statusbar
		buildMenu();
		
		topPanel.add(menubar, BorderLayout.NORTH);

		panel.add(topPanel, BorderLayout.NORTH);
		this.add(panel);
//		myf.setVisible(true);
		
//		desktop.add(myf);
		
		panel.add(desktop, BorderLayout.CENTER);
//		buildtoolbar();
		// TODO: build toolbar method
		
		topPanel.add(toolbar, BorderLayout.SOUTH);
		
		currentDrive = "C:";
//		buildstatusbar();
		// TODO: build statusbar method
		
		panel.add(statusbar, BorderLayout.SOUTH);
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void buildMenu() {
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
		// add JMenuItems to tre menu
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
	 * ActionListener for window menu items
	 * 
	 *
	 */
	private class helpMenuActionListener implements ActionListener{

		/**
		 * @param e mouse click on menu item
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Help"))
				// TODO: ADD help IMPLEMENTATION
				System.out.println("Help");
			
			else // about
				// TODO: ADD about IMPLEMENTATION
				System.out.println("About");
		}
		
	}
	
}
