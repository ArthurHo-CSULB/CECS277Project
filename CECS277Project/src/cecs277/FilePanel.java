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
    
    public FilePanel(FileManagerFrame myFileManagerFrame){
    	list.setFont(new Font("Courier New",Font.BOLD,14));
        this.myFileManagerFrame = myFileManagerFrame;
        this.setLayout(new BorderLayout());
        scrollpane.setViewportView(list);
		this.add(scrollpane, BorderLayout.CENTER);
        this.setDropTarget(new MyDropTarget());        
        buildList("C:\\");
        RCpopup = new JPopupMenu();
        RCpopup.add(new JMenuItem("Rename"));
        RCpopup.add(new JMenuItem("Copy"));
        RCpopup.add(new JMenuItem("Paste"));
        RCpopup.addSeparator();
        RCpopup.add(new JMenuItem("Delete"));
    }
    
    public FilePanel(FileManagerFrame myFileManagerFrame, File file){
    	list.setFont(new Font("Courier New",Font.BOLD,14));
    	this.myFileManagerFrame = myFileManagerFrame;
        this.setLayout(new BorderLayout());
        scrollpane.setViewportView(list);
		this.add(scrollpane, BorderLayout.CENTER);
        this.setDropTarget(new MyDropTarget());        
        buildList(file.getAbsolutePath());
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
        list.addMouseListener(new doubleClickMouseRun());
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
     * MouseAdapter which listens for double click on list to run the file 
     * 
     * @author Darius & Arthur
     *
     */
    public class doubleClickMouseRun extends MouseAdapter{
    	public void mouseClicked(MouseEvent e) {
    		list = (JList)e.getSource();
    		if (e.getClickCount() == 2) {
    			
    			// Double-click detected
    			int index = list.locationToIndex(e.getPoint());
    			// checking to make sure the doubleclick index makes sense
    			if (index > 0) {
    				// get the node associated with the double click
    				MyFileList fileNode = (MyFileList)list.getModel().getElementAt(index);
    				// get user desktop
    				Desktop desktop = Desktop.getDesktop();
    				try {
    					desktop.open(fileNode.getFile());
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
    			RCpopup.show(e.getComponent(), e.getX(), e.getY());
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
