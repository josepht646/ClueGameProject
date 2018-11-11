package experiment;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ShowDialogGUI extends JFrame {
	private JButton login, hello;
	private MyDialog dialog;

	public ShowDialogGUI(){
		setTitle("Show Dialog");
		setSize(300, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login = new JButton("Login");
		hello = new JButton("Say hello");
		// Experiment with different locations!
		add(login, BorderLayout.CENTER);
		add(hello, BorderLayout.SOUTH);
		login.addActionListener(new ButtonListener());
		hello.addActionListener(new HelloListener());

	}
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dialog = new MyDialog();
			dialog.setVisible(true);
		}
	}

	class HelloListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (dialog == null) 
				JOptionPane.showMessageDialog(null, "Must Login First!");
			else 
				JOptionPane.showMessageDialog(null, "Hello " + dialog.getMyName());	
		}
	}
	public static void main(String[] args) {
		ShowDialogGUI gui = new ShowDialogGUI();	
		gui.setVisible(true);
	}

}
