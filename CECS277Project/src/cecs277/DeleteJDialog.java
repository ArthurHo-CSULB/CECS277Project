package cecs277;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;

public class DeleteJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private App myApp;
	private JTextPane textPane;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			DeleteJDialog dialog = new DeleteJDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//			dialog.setTitle("Delete");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	
	
	public void setTextPane(String s) {
		textPane.setText("Are you sure you want to delete "+ s);
	}
	/**
	 * Create the dialog.
	 */
	public DeleteJDialog(JFrame parent) {
		super(parent, true);
		myApp = (App) parent;
		setBounds(100, 100, 400, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			textPane = new JTextPane();
			contentPanel.add(textPane);
			
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("YES");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						myApp.deleteLastSelectedFile();
						setVisible(false);						
					}					
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("NO");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();						
					}					
				});
				buttonPane.add(cancelButton);
			}
		}
	}

}
