package cecs277;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
/**
 * 
 * @author Darius & Arthur
 *
 */
public class DirPanel extends JPanel{
	private JScrollPane scrollpane = new JScrollPane();
	private JTree dirTree = new JTree();
	private DefaultTreeModel treeModel;
	
	public JTree getTree() {
		return dirTree;
	}
	public DirPanel() {
		setLayout(new BorderLayout());
		add(scrollpane, BorderLayout.CENTER);
		scrollpane.setViewportView(dirTree);
		add(scrollpane);
		buildDir();
	}
	
	// builds the initial directory
	public void buildDir() {
		File[] rootDirectories;
		rootDirectories = File.listRoots();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new MyFileNode("C:\\"));
		treeModel = new DefaultTreeModel(root);
				
		DefaultMutableTreeNode node;
		File cFile = new File("C:\\");
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
	
	class directoryTreeSelectionListener implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			
			DefaultMutableTreeNode thisNode = (DefaultMutableTreeNode) dirTree.getLastSelectedPathComponent();
			
			/*
			 * //checking if the node doesnt have children and should have children
			 * if(thisNode != null && thisNode.getChildCount()<1 &&
			 * ((MyFileNode)thisNode.getUserObject()).isDirectory()) {
			 * DefaultMutableTreeNode node =
			 * buildNode(((MyFileNode)thisNode.getUserObject()).getFile());
			 * thisNode.add(node); }
			 */
			
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
				else {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.open(((MyFileNode)thisNode.getUserObject()).getFile());
					}catch(IOException ex) {
						System.out.println(ex.toString());
					}
				}
			}
			System.out.println(e.getPath());	
		}
	}
}
