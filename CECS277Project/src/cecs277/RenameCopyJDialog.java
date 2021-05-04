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
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;

public class RenameCopyJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField fromField;
	private JTextField toField;
	private JTextPane textPane;
	private String toString, fromString;
	

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			RenameCopyJDialog dialog = new RenameCopyJDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public String getToField() {
		return toField.getText();
	}
	
	public void setFromField(String s) {
		fromField.setText(s);
	}
	
	public void setToField(String s) {
		toField.setText(s);
	}
	
	public void setCurrentDirectory(String s) {
		textPane.setText("Current Directory: "+s);
	}

	
	
	/**
	 * Create the dialog.
	 */
	public RenameCopyJDialog(JFrame parent) {
		super(parent, true);
		setBounds(100, 100, 450, 188);
		getContentPane().setLayout(new MigLayout("", "[434px,grow]", "[40px][33px,grow][grow][]"));
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, "cell 0 0,alignx left,aligny top");
		{
			textPane = new JTextPane();
			contentPanel.add(textPane);
			textPane.setText("Current Directory:");
		}
		{
			JTextPane textPane = new JTextPane();
			getContentPane().add(textPane, "flowx,cell 0 1,alignx left,growy");
			textPane.setText("From:");
		}
		{
			JTextPane textPane = new JTextPane();
			getContentPane().add(textPane, "flowx,cell 0 2,alignx left,growy");
			textPane.setText("To:     ");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, "cell 0 3,growx,aligny top");
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}					
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
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
		{
			fromField = new JTextField();
			getContentPane().add(fromField, "cell 0 1");
			fromField.setColumns(40);
		}
		{
			toField = new JTextField();
			getContentPane().add(toField, "cell 0 2");
			toField.setColumns(40);
		}
	}
}
