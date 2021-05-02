package cecs277;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
/**
 * 
 * @author Darius & Arthur
 *
 */
public class DirPanel extends JPanel{
	private JScrollPane scrollpane = new JScrollPane();
	private JTree dirTree = new JTree();
	private DefaultTreeModel treeModel;
	private FileManagerFrame myFileManagerFrame;
	
	public JTree getTree() {
		return dirTree;
	}
	public DirPanel(FileManagerFrame myFileManagerFrame) {
		this.myFileManagerFrame = myFileManagerFrame;
		setLayout(new BorderLayout());
		add(scrollpane, BorderLayout.CENTER);
		scrollpane.setViewportView(dirTree);
		buildDir("C:\\");
	}
	public DirPanel(FileManagerFrame myFileManagerFrame, File file) {
		this.myFileManagerFrame = myFileManagerFrame;
		setLayout(new BorderLayout());
		add(scrollpane, BorderLayout.CENTER);
		scrollpane.setViewportView(dirTree);
		buildDir(file.toString());
	}
	
	// builds the initial directory
	public void buildDir(String rootStr) {
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new MyFileNode(rootStr));
		treeModel = new DefaultTreeModel(root);
				
		DefaultMutableTreeNode node;
		File cFile = new File(rootStr);
		File[] cFiles = cFile.listFiles();
		for(File item : cFiles) {
			if (item.listFiles() != null)
				node = buildNode(item);
			else
				node = new DefaultMutableTreeNode(new MyFileNode(item.toString()));
			root.add(node);
		}
		dirTree.setModel(treeModel);
		dirTree.addTreeSelectionListener(new directoryTreeSelectionListener());
		dirTree.addTreeExpansionListener(new directoryTreeExpansionListener());
	}
	
	/**
	 *  builds and returns a connected node's subtree ONLY 1 LEVEL DEEP
	 * 
	 * @param rootFile the file you want to build a node for
	 * @return a node with children node underneath
	 */
	public DefaultMutableTreeNode buildNode(File rootFile) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new MyFileNode(rootFile.getAbsolutePath()));
				
		DefaultMutableTreeNode node;
		File[] files = rootFile.listFiles();
		for(File item : files) {		
			node = new DefaultMutableTreeNode(new MyFileNode(item.toString()));
			if(((MyFileNode)node.getUserObject()).isDirectory())
				root.add(buildNodeOneDeep(item));
			else
				root.add(node);
		}
		return root;
	}
	
	/**
	 * ONLY USED TO MAKE A NODE 1 LEVEL DEEP FOR INITIALIZATION OF THE DRIVE
	 * 
	 * @param rootFile file that a node is being built for
	 * @return treenode of all children files
	 */
	public DefaultMutableTreeNode buildNodeOneDeep(File rootFile) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new MyFileNode(rootFile.getAbsolutePath()));
				
		DefaultMutableTreeNode node;
		File[] files = rootFile.listFiles();
		for(File item : files) {		
			node = new DefaultMutableTreeNode(new MyFileNode(item.toString()));
			root.add(node);
		}
		return root;
	}

	/**
	 * adds children nodes to the root node by searching for a list of all files and creating a node for each one
	 * 
	 * 
	 * @param root the root node that children will be added to
	 */
	public void addNodeChildren(DefaultMutableTreeNode root) {
					
		DefaultMutableTreeNode node;
		File[] files = ((MyFileNode)root.getUserObject()).getFile().listFiles();
		for(File item : files) {		
			node = new DefaultMutableTreeNode(new MyFileNode(item.toString()));
			root.add(node);
		}
		
	}
	
	/**
     * return this class's filemanagerframe
     * 
     * @return local variable filemanagerframe
     */
    public FileManagerFrame getFileManagerFrame() {
    	return myFileManagerFrame;
    }
	
    
    class directoryTreeExpansionListener implements TreeExpansionListener{

		@Override
		public void treeExpanded(TreeExpansionEvent event) {
			TreePath path = event.getPath();
			DefaultMutableTreeNode thisNode = (DefaultMutableTreeNode) path.getLastPathComponent();
			System.out.println(thisNode);
			if(thisNode!=null)
				myFileManagerFrame.setTitle(((MyFileNode)thisNode.getUserObject()).getFilePath());
			expandDirectoryNode(thisNode);
			if(thisNode!=null)
				myFileManagerFrame.buildNewList((MyFileNode)thisNode.getUserObject());
			
			
		}

		@Override
		public void treeCollapsed(TreeExpansionEvent event) {
			// TODO Auto-generated method stub
			
		}
		
		public void expandDirectoryNode(DefaultMutableTreeNode thisNode) {
			if(thisNode != null) {
				int childCount = thisNode.getChildCount();
				if(childCount > 0) {
					DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) thisNode.getFirstChild();
					if(((MyFileNode)childNode.getUserObject()).isDirectory()) {
						if(childNode.getChildCount()==0) {
							addNodeChildren(childNode);
						}
					}
					childCount--;
					while(childCount > 0) {
						childNode = (DefaultMutableTreeNode) thisNode.getChildAfter(childNode);
						if(((MyFileNode)childNode.getUserObject()).isDirectory()) {
							if(childNode.getChildCount()==0) {
								addNodeChildren(childNode);
							}
						}
						childCount--;
					}
				}
			}
			
		}
    	
    }
    
	class directoryTreeSelectionListener implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			
			DefaultMutableTreeNode thisNode = (DefaultMutableTreeNode) dirTree.getLastSelectedPathComponent();
			if(thisNode!=null)
				myFileManagerFrame.setTitle(((MyFileNode)thisNode.getUserObject()).getFilePath());
			expandDirectoryNode(thisNode);
			if(thisNode!=null) {
				myFileManagerFrame.buildNewList((MyFileNode)thisNode.getUserObject());
			}
			
		}
		
		/**
		 * Checks if a node needs children nodes to be created within its tree based on subdirectories 
		 * 
		 * @param thisNode node to be expanded on the tree
		 */
		public void expandDirectoryNode(DefaultMutableTreeNode thisNode) {
			if(thisNode != null) {
				int childCount = thisNode.getChildCount();
				if(childCount > 0) {
					DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) thisNode.getFirstChild();
					if(((MyFileNode)childNode.getUserObject()).isDirectory()) {
						if(childNode.getChildCount()==0) {
							addNodeChildren(childNode);
						}
					}
					childCount--;
					while(childCount > 0) {
						childNode = (DefaultMutableTreeNode) thisNode.getChildAfter(childNode);
						if(((MyFileNode)childNode.getUserObject()).isDirectory()) {
							if(childNode.getChildCount()==0) {
								addNodeChildren(childNode);
							}
						}
						childCount--;
					}
				}
//				else {
//					Desktop desktop = Desktop.getDesktop();
//					try {
//						desktop.open(((MyFileNode)thisNode.getUserObject()).getFile());
//					}catch(IOException ex) {
//						System.out.println(ex.toString());
//					}
//				}
			}
			
		}
	}
	
}
