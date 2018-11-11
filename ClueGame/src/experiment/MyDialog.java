package experiment;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MyDialog extends JDialog {
	private JTextField myName;
	private JPasswordField password;

	public MyDialog() {
		setTitle("Login Dialog");
		setSize(300, 200);
		setLayout(new GridLayout(0, 2));
		JLabel nameLabel = new JLabel("Name");
		myName = new JTextField(20);
		JLabel pwdLabel = new JLabel("Password");
		password = new JPasswordField();
		add(nameLabel);
		add(myName);
		add(pwdLabel);
		add(password);

		JButton button = new JButton("OK");
		// e is the parameter to actionPerformed, -> separates parameter from 
		// body, the body is the setVisible statement
		button.addActionListener(e -> setVisible(false));

		/* Syntax prior to Java 8		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		*/
		add(button);

	}

	public String getMyName() {
		return myName.getText();
	}

}
