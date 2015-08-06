package vn.ptit.project.token;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class PinInputter extends JDialog {
	private static final long serialVersionUID = 5926423178158402692L;
	/* 45 */private final JPanel contentPanel = new JPanel();
	
	private JPasswordField passwordField;
	
	public PinInputter() {
		setBounds(100, 100, 260, 119);
		getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(this.contentPanel, "Center");
		this.contentPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Please enter your PIN");
		this.contentPanel.add(lblNewLabel, "North");
		
		this.passwordField = new JPasswordField();
		this.contentPanel.add(this.passwordField, "Center");
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(2));
		getContentPane().add(buttonPane, "South");
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PinInputter.this.dispose();
			}
			
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PinInputter.this.passwordField.setText("");
				PinInputter.this.dispose();
			}
			
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		setModal(true);
	}
	
	public static CallbackHandler getCallbackHandler() {
		CallbackHandler handler = new CallbackHandler() {
			public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
				Callback[] arrayOfCallback;
				int j = (arrayOfCallback = callbacks).length;
				for (int i = 0; i < j; i++) {
					Callback callback = arrayOfCallback[i];
					if ((callback instanceof PasswordCallback)) {
						PinInputter pinInput = new PinInputter();
						pinInput.setDefaultCloseOperation(2);
						pinInput.setVisible(true);
						
						((PasswordCallback) callback).setPassword(pinInput.passwordField.getPassword());
					} else {
						throw new UnsupportedCallbackException(callback);
					}
					
				}
			}
			
		};
		return handler;
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: vn.ptit.project.token.PinInputter Java Class Version: 7
 * (51.0) JD-Core Version: 0.7.1
 */