package cecs277;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;

public class RenameCopyJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RenameCopyJDialog dialog = new RenameCopyJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//public void setTitle(String s) {
		//dialog.setTitle(s);
	//}

	/**
	 * Create the dialog.
	 */
	public RenameCopyJDialog() {
		setBounds(100, 100, 450, 188);
		getContentPane().setLayout(new MigLayout("", "[434px,grow]", "[40px][33px,grow][grow][]"));
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, "cell 0 0,alignx left,aligny top");
		{
			JTextPane textPane = new JTextPane();
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
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			textField = new JTextField();
			getContentPane().add(textField, "cell 0 1");
			textField.setColumns(40);
			textField.setText("Test");
		}
		{
			textField_1 = new JTextField();
			getContentPane().add(textField_1, "cell 0 2");
			textField_1.setColumns(40);
		}
	}
}
