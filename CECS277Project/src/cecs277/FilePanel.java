package cecs277;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
/**
 * 
 * @author Darius & Arthur
 *
 */
public class FilePanel extends JPanel{
	private JScrollPane scrollpane = new JScrollPane();
	private JTree dirTree = new JTree();
	
	public FilePanel() {
		setLayout(new BorderLayout());
		add(scrollpane, BorderLayout.CENTER);
		scrollpane.setViewportView(dirTree);
		add(scrollpane);
	}
}
