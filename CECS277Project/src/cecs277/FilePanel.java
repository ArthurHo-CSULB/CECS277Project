//package cecs277;
//
//import java.awt.BorderLayout;
//
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTree;
///**
// * 
// * @author Darius & Arthur
// *
// */
//public class FilePanel extends JPanel{
//	private JScrollPane scrollpane = new JScrollPane();
//	private JTree dirTree = new JTree();
//	
//	public FilePanel() {
//		setLayout(new BorderLayout());
//		add(scrollpane, BorderLayout.CENTER);
//		scrollpane.setViewportView(dirTree);
//		add(scrollpane);
//	}
//}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cecs277;


import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;


/**
 *
 * @author Dr. Hoffman
 */

public class FilePanel extends JPanel {
    JList list = new JList();
    DefaultListModel model = new DefaultListModel();
    private JScrollPane scrollpane = new JScrollPane();
    private FileManagerFrame myFileManagerFrame;
    private JPopupMenu RCpopup;
    private JMenuItem rename, copy, paste, delete;
    private MyFileList selectedFileList;
    
    public FilePanel(FileManagerFrame myFileManagerFrame){
    	list.setFont(new Font("Courier New",Font.BOLD,14));
        this.myFileManagerFrame = myFileManagerFrame;
        this.setLayout(new BorderLayout());
        scrollpane.setViewportView(list);
		this.add(scrollpane, BorderLayout.CENTER);
        this.setDropTarget(new MyDropTarget());        
        buildList("C:\\");
        buildPopup();

    }
    
    public FilePanel(FileManagerFrame myFileManagerFrame, File file){
    	list.setFont(new Font("Courier New",Font.BOLD,14));
    	this.myFileManagerFrame = myFileManagerFrame;
        this.setLayout(new BorderLayout());
        scrollpane.setViewportView(list);
		this.add(scrollpane, BorderLayout.CENTER);
        this.setDropTarget(new MyDropTarget());        
        buildList(file.getAbsolutePath());
        buildPopup();
    }
    
    /**
     * builds the popup which appears when you right click
     */
    public void buildPopup() {
        RCpopup = new JPopupMenu();
        rename = new JMenuItem("Rename");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        delete = new JMenuItem("Delete");
        
        RCpopup.add(rename);
        RCpopup.add(copy);
        RCpopup.add(paste);
        RCpopup.addSeparator();
        RCpopup.add(delete);
        
        popupActionListener popupAL = new popupActionListener();
        
        rename.addActionListener(popupAL);
        copy.addActionListener(popupAL);
        paste.addActionListener(popupAL);
        delete.addActionListener(popupAL);
    }
    /**
     *  Builds the initial list of the root drive C:\\
     */
    public void buildList(String currentDrive){
    	File[] files = new File(currentDrive).listFiles();
    	ArrayList<MyFileList> fileNodes = new ArrayList<MyFileList>();
    	ArrayList<MyFileList> dirNodes = new ArrayList<MyFileList>();
    	for(File file: files) {
    		if (file.isDirectory())
    			dirNodes.add(new MyFileList(file.toString()));
    		else {
    			fileNodes.add(new MyFileList(file.toString()));
    		}
    	}
    	model.addAll(dirNodes);
    	model.addAll(fileNodes);
        list.setModel(model);
        list.setDragEnabled(true);
        list.setVisibleRowCount(-1);
        list.addMouseListener(new ClickMouseRun());
    }
    
    
    /**
     * This will create a new list to view for the user
     * 
     * @param focusedFileNode current node which has been selected by the user to display new list
     */
    public void buildNewList(MyFileNode focusedFileNode) {
    	if(!focusedFileNode.isDirectory()) {
    		model.removeAllElements();
    		return;
    	}
    	model.removeAllElements();
    	File[] files = focusedFileNode.getFile().listFiles();
    	if (files == null)
    		return;
    	ArrayList<MyFileList> fileNodes = new ArrayList<MyFileList>();
    	ArrayList<MyFileList> dirNodes = new ArrayList<MyFileList>();
    	for(File file: files) {
    		if (file.isDirectory())
    			dirNodes.add(new MyFileList(file.toString()));
    		else {
    			fileNodes.add(new MyFileList(file.toString()));
    		}
    	}
    	model.addAll(dirNodes);
    	model.addAll(fileNodes);
    }
    
    /**
     * return this class's filemanagerframe
     * 
     * @return local variable filemanagerframe
     */
    public FileManagerFrame getFileManagerFrame() {
    	return myFileManagerFrame;
    }
    
    /**
     * return this class's selected list item
     * 
     * @return the last selectedFileList by the user
     */
    public MyFileList getSelectedFileList() {
    	return selectedFileList;
    }
    
    /**
     * MouseAdapter which listens for double click on list to run the file 
     * 
     * @author Darius & Arthur
     *
     */
    private class ClickMouseRun extends MouseAdapter{
    	public void mouseClicked(MouseEvent e) {
    		list = (JList)e.getSource();
    		
			if (e.getClickCount() == 1) {
    			// single-click detected
    			int index = list.locationToIndex(e.getPoint());
    			// checking to make sure the doubleclick index makes sense
    			if (index >= 0) {
    				// get the node associated with the double click
    				selectedFileList = (MyFileList)list.getModel().getElementAt(index);
    				getFileManagerFrame().getMyApp().setSelectedFileList(selectedFileList);
    			}
    		}
			else if (e.getClickCount() == 2) {
    			
    			// Double-click detected
    			int index = list.locationToIndex(e.getPoint());
    			// checking to make sure the doubleclick index makes sense
    			if (index >= 0) {
    				// get the node associated with the double click
    				selectedFileList = (MyFileList)list.getModel().getElementAt(index);
    				getFileManagerFrame().getMyApp().setSelectedFileList(selectedFileList);
    				// get user desktop
    				Desktop desktop = Desktop.getDesktop();
    				try {
    					desktop.open(selectedFileList.getFile());
    				}catch(IOException ex) {
    					System.out.println(ex.toString());
    				}
    			}
    		}
    	}
    	public void mousePressed(MouseEvent e) {
    		RightmouseClicked(e);
    	}
    	public void mouseReleased(MouseEvent e) {
    		RightmouseClicked(e);
    	}
    	public void RightmouseClicked(MouseEvent e) {
    		if(e.isPopupTrigger()) {
    			int index = list.locationToIndex(e.getPoint());
    			if (index >= 0) {
    				selectedFileList = (MyFileList)list.getModel().getElementAt(index);
    				getFileManagerFrame().getMyApp().setSelectedFileList(selectedFileList);
    			}
    			RCpopup.show(e.getComponent(), e.getX(), e.getY());
    		}
    	}
    }
    
    /**
     * action listener to handle popup events
     * 
     * @author Darius & Arthur
     *
     */
    private class popupActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().equals("Rename")) {
				getFileManagerFrame().getMyApp().buildRenameDlg();
			}
			
			else if(e.getActionCommand().equals("Copy")) {
				getFileManagerFrame().getMyApp().buildCopyDlg();
			}
			
			else if(e.getActionCommand().equals("Paste")) {
				getFileManagerFrame().getMyApp().buildPasteDlg();
			}
			
			else if(e.getActionCommand().equals("Delete")) {
				getFileManagerFrame().getMyApp().buildDeleteDlg();
			}
			
		}
    	
    }
    /*************************************************************************
     * class MyDropTarget handles the dropping of files onto its owner
     * (whatever JList it is assigned to). As written, it can process two
     * types: strings and files (String, File). The String type is necessary
     * to process internal source drops from another FilePanel object. The
     * File type is necessary to process drops from external sources such 
     * as Windows Explorer or IOS.
     * 
     * Note: no code is provided that actually copies files to the target
     * directory. Also, you may need to adjust this code if your list model
     * is not the default model. JList assumes a toString operation is
     * defined for whatever class is used.
     */
    class MyDropTarget extends DropTarget {
    /**************************************************************************
     * 
     * @param evt the event that caused this drop operation to be invoked
     */    
        public void drop(DropTargetDropEvent evt){
            try {
                //types of events accepted
                evt.acceptDrop(DnDConstants.ACTION_COPY);
                //storage to hold the drop data for processing
                List result = new ArrayList();
                //what is being dropped? First, Strings are processed
                if(evt.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor)){     
                    String temp = (String)evt.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    //String events are concatenated if more than one list item 
                    //selected in the source. The strings are separated by
                    //newline characters. Use split to break the string into
                    //individual file names and store in String[]
                    String[] next = temp.split("\\n");
                    //add the strings to the listmodel
                    for(int i=0; i<next.length;i++)
                        model.addElement(next[i]); 
                }
                else{ //then if not String, Files are assumed
                    result =(List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    //process the input
                    for(Object o : result){
                        System.out.println(o.toString());
                        model.addElement(o.toString());
                    }
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
    }

}
