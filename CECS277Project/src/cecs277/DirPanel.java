package cecs277;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
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
		DefaultMutableTreeNode subNode;
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
			root.add(node);
		}
		return root;
	}
}
