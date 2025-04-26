package gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;


import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.GridLayout;
import javax.swing.UIManager;
import java.awt.CardLayout;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel loginPane;
	private JTextField createUsertextfield;
	private JPasswordField createpwdfield;
	private JPasswordField confirmpwdfield;
	private JLabel imageLabel;

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\justf\\Downloads/icon.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Northland Bank");
		setSize(800, 657);

		loginPane = new JPanel();
		setContentPane(loginPane);
		loginPane.setLayout(new BorderLayout(0, 0));

		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(new Color(192, 192, 192));
		loginPane.add(leftPanel, BorderLayout.WEST);
		leftPanel.setPreferredSize(new Dimension(450, 0)); 
		leftPanel.setLayout(new BorderLayout(0, 0));

		JPanel headerPanelleft = new JPanel();
		headerPanelleft.setBackground(new Color(240, 240, 240));
		headerPanelleft.setPreferredSize(new Dimension(0, 100)); 
		leftPanel.add(headerPanelleft, BorderLayout.NORTH);
		headerPanelleft.setLayout(new BorderLayout(0, 0));
		
				JLabel header = new JLabel("Welcome Back");
				headerPanelleft.add(header, BorderLayout.CENTER);
				header.setFont(new Font("Arial", Font.BOLD, 24));
				header.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel westPanelleft = new JPanel();
		FlowLayout fl_westPanelleft = (FlowLayout) westPanelleft.getLayout();
		fl_westPanelleft.setHgap(30);
		westPanelleft.setBackground(new Color(255, 255, 255));
		leftPanel.add(westPanelleft, BorderLayout.WEST);

		JPanel eastPanelleft = new JPanel();
		FlowLayout fl_eastPanelleft = (FlowLayout) eastPanelleft.getLayout();
		fl_eastPanelleft.setHgap(40);
		eastPanelleft.setBackground(new Color(255, 255, 255));
		leftPanel.add(eastPanelleft, BorderLayout.EAST);

		JPanel southPanelleft = new JPanel();
		FlowLayout fl_southPanelleft = (FlowLayout) southPanelleft.getLayout();
		fl_southPanelleft.setVgap(30);
		southPanelleft.setBackground(new Color(255, 255, 255));
		leftPanel.add(southPanelleft, BorderLayout.SOUTH);

		JPanel loginFormPanelleft = new JPanel();
		loginFormPanelleft.setBackground(new Color(255, 255, 255));
		leftPanel.add(loginFormPanelleft, BorderLayout.CENTER);
		GridBagLayout gbl_loginFormPanelleft = new GridBagLayout();
		gbl_loginFormPanelleft.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_loginFormPanelleft.columnWeights = new double[]{0.0};
		loginFormPanelleft.setLayout(gbl_loginFormPanelleft); 



		// Label for username
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.gridx = 0; // Column 0
		gbc_lblUsername.gridy = 0; // Row 0
		gbc_lblUsername.insets = new Insets(0, 0, 5, 0); // Padding
		loginFormPanelleft.add(lblUsername, gbc_lblUsername);

		// TextField for username
		JTextField txtUsername_1 = new JTextField(20);
		txtUsername_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		GridBagConstraints gbc_txtUsername_1 = new GridBagConstraints();
		gbc_txtUsername_1.anchor = GridBagConstraints.WEST;
		gbc_txtUsername_1.gridx = 0; // Column 1
		gbc_txtUsername_1.gridy = 1; // Same row
		gbc_txtUsername_1.insets = new Insets(5, 0, 5, 0); // Padding
		loginFormPanelleft.add(txtUsername_1, gbc_txtUsername_1);

		// Label for password
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 0);
		gbc_lblPassword.gridx = 0; // Column 0
		gbc_lblPassword.gridy = 2; // Row 1
		loginFormPanelleft.add(lblPassword, gbc_lblPassword);

		// PasswordField
		JPasswordField pwdPassword_1 = new JPasswordField(20);
		pwdPassword_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		GridBagConstraints gbc_pwdPassword_1 = new GridBagConstraints();
		gbc_pwdPassword_1.insets = new Insets(5, 0, 5, 0);
		gbc_pwdPassword_1.gridx = 0; // Column 1
		gbc_pwdPassword_1.gridy = 3; // Same row
		loginFormPanelleft.add(pwdPassword_1, gbc_pwdPassword_1);
		
		
		
				
		
		JLabel lblForgotPwd = new JLabel("<html><u>Forgot Password</u></html>");
		GridBagConstraints gbc_lblForgotPwd = new GridBagConstraints();
		gbc_lblForgotPwd.anchor = GridBagConstraints.EAST;
		gbc_lblForgotPwd.insets = new Insets(0, 0, 5, 0);
		gbc_lblForgotPwd.gridx = 0;
		gbc_lblForgotPwd.gridy = 4;
		loginFormPanelleft.add(lblForgotPwd, gbc_lblForgotPwd);
		lblForgotPwd.setHorizontalAlignment(SwingConstants.RIGHT);
		lblForgotPwd.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblForgotPwd.setForeground(new Color(0, 102, 204));
		lblForgotPwd.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Login Button
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLogin.setFocusPainted(false);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new Color(0, 123, 255));
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLogin.insets = new Insets(5, 0, 5, 0);
		gbc_btnLogin.gridx = 0; // Column 1
		gbc_btnLogin.gridy = 5; // Row 2
		loginFormPanelleft.add(btnLogin, gbc_btnLogin);
				
				
						// "No account?" Label
						JLabel lblCreateAccount = new JLabel("<html><u>Sign Up</u></html>");
						GridBagConstraints gbc_lblCreateAccount = new GridBagConstraints();
						gbc_lblCreateAccount.insets = new Insets(0, 0, 5, 0);
						gbc_lblCreateAccount.anchor = GridBagConstraints.WEST;
						gbc_lblCreateAccount.gridx = 0;
						gbc_lblCreateAccount.gridy = 6;
						loginFormPanelleft.add(lblCreateAccount, gbc_lblCreateAccount);
						lblCreateAccount.setHorizontalAlignment(SwingConstants.LEFT);
						lblCreateAccount.setFont(new Font("SansSerif", Font.PLAIN, 14));
						lblCreateAccount.setForeground(new Color(0, 102, 204));
						lblCreateAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));
						
						
				
		
		for (Component comp : loginFormPanelleft.getComponents()) {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = GridBagConstraints.RELATIVE;  
			gbc.gridy = GridBagConstraints.RELATIVE;  
			gbc.weightx = 0.0;  
			gbc.weighty = 0.0;  
			gbc.fill = GridBagConstraints.NONE;  
			comp.setPreferredSize(comp.getPreferredSize());  
		}
		
		JPanel bgPanel = new JPanel();
		loginPane.add(bgPanel, BorderLayout.CENTER);
		bgPanel.setLayout(new CardLayout(0, 0));
		
		imageLabel = new ScaledImageLabel("C:\\Users\\justf\\Downloads/eb.jpg");
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setVerticalAlignment(SwingConstants.CENTER);

		bgPanel.add(imageLabel, "bgImage");

		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(new Color(128, 128, 128));
		bgPanel.add(rightPanel, BorderLayout.CENTER);
		rightPanel.setPreferredSize(new Dimension(350, 0)); 
		rightPanel.setLayout(new BorderLayout(0, 0));
		

		
		JPanel headerPanelright = new JPanel();
		headerPanelright.setPreferredSize(new Dimension(0, 100));
		headerPanelright.setBackground(UIManager.getColor("Button.background"));
		rightPanel.add(headerPanelright, BorderLayout.NORTH);
		headerPanelright.setLayout(new BorderLayout(0, 0));
		
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
		headerPanelright.add(lblWelcome, BorderLayout.CENTER);
		
		JPanel westPanelright = new JPanel();
		FlowLayout fl_westPanelright = (FlowLayout) westPanelright.getLayout();
		fl_westPanelright.setHgap(40);
		westPanelright.setBackground(Color.WHITE);
		rightPanel.add(westPanelright, BorderLayout.EAST);
		
		JPanel eastPanelright = new JPanel();
		FlowLayout fl_eastPanelright = (FlowLayout) eastPanelright.getLayout();
		fl_eastPanelright.setHgap(30);
		eastPanelright.setBackground(Color.WHITE);
		rightPanel.add(eastPanelright, BorderLayout.WEST);
		
		JPanel southPanelright = new JPanel();
		FlowLayout fl_southPanelright = (FlowLayout) southPanelright.getLayout();
		fl_southPanelright.setVgap(30);
		southPanelright.setBackground(Color.WHITE);
		rightPanel.add(southPanelright, BorderLayout.SOUTH);
		
		JPanel loginFormPanelright = new JPanel();
		loginFormPanelright.setBackground(new Color(255, 255, 255));
		rightPanel.add(loginFormPanelright, BorderLayout.CENTER);
		GridBagLayout gbl_loginFormPanelright = new GridBagLayout();
		gbl_loginFormPanelright.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_loginFormPanelright.columnWeights = new double[]{0.0};
		loginFormPanelright.setLayout(gbl_loginFormPanelright);
		
		JLabel lblCreateUsername = new JLabel("Username:");
		lblCreateUsername.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblCreateUsername = new GridBagConstraints();
		gbc_lblCreateUsername.anchor = GridBagConstraints.WEST;
		gbc_lblCreateUsername.insets = new Insets(0, 0, 5, 0);
		gbc_lblCreateUsername.gridx = 0;
		gbc_lblCreateUsername.gridy = 0;
		loginFormPanelright.add(lblCreateUsername, gbc_lblCreateUsername);
		
		createUsertextfield = new JTextField(20);
		createUsertextfield.setFont(new Font("SansSerif", Font.PLAIN, 14));
		GridBagConstraints gbc_createUsertextfield = new GridBagConstraints();
		gbc_createUsertextfield.insets = new Insets(0, 0, 5, 0);
		gbc_createUsertextfield.fill = GridBagConstraints.HORIZONTAL;
		gbc_createUsertextfield.gridx = 0;
		gbc_createUsertextfield.gridy = 1;
		loginFormPanelright.add(createUsertextfield, gbc_createUsertextfield);
		
		JLabel lblCreatePassword = new JLabel("Password:");
		lblCreatePassword.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblCreatePassword = new GridBagConstraints();
		gbc_lblCreatePassword.anchor = GridBagConstraints.WEST;
		gbc_lblCreatePassword.insets = new Insets(0, 0, 5, 0);
		gbc_lblCreatePassword.gridx = 0;
		gbc_lblCreatePassword.gridy = 2;
		loginFormPanelright.add(lblCreatePassword, gbc_lblCreatePassword);
		
		createpwdfield = new JPasswordField(20);
		createpwdfield.setFont(new Font("SansSerif", Font.PLAIN, 14));
		GridBagConstraints gbc_createpwdfield = new GridBagConstraints();
		gbc_createpwdfield.insets = new Insets(0, 0, 5, 0);
		gbc_createpwdfield.fill = GridBagConstraints.HORIZONTAL;
		gbc_createpwdfield.gridx = 0;
		gbc_createpwdfield.gridy = 3;
		loginFormPanelright.add(createpwdfield, gbc_createpwdfield);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 0);
		gbc_lblConfirmPassword.anchor = GridBagConstraints.WEST;
		gbc_lblConfirmPassword.gridx = 0;
		gbc_lblConfirmPassword.gridy = 4;
		loginFormPanelright.add(lblConfirmPassword, gbc_lblConfirmPassword);
		
		confirmpwdfield = new JPasswordField(20);
		confirmpwdfield.setFont(new Font("SansSerif", Font.PLAIN, 14));
		GridBagConstraints gbc_confirmpwdfield = new GridBagConstraints();
		gbc_confirmpwdfield.insets = new Insets(0, 0, 5, 0);
		gbc_confirmpwdfield.fill = GridBagConstraints.HORIZONTAL;
		gbc_confirmpwdfield.gridx = 0;
		gbc_confirmpwdfield.gridy = 5;
		loginFormPanelright.add(confirmpwdfield, gbc_confirmpwdfield);
		
		JButton btnCreateAcc = new JButton("Create Account");
		btnCreateAcc.setForeground(Color.WHITE);
		btnCreateAcc.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnCreateAcc.setFocusPainted(false);
		btnCreateAcc.setBackground(new Color(0, 123, 255));
		GridBagConstraints gbc_btnCreateAcc = new GridBagConstraints();
		gbc_btnCreateAcc.insets = new Insets(0, 0, 5, 0);
		gbc_btnCreateAcc.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCreateAcc.gridx = 0;
		gbc_btnCreateAcc.gridy = 6;
		loginFormPanelright.add(btnCreateAcc, gbc_btnCreateAcc);
		
		JLabel lbllogin = new JLabel("<html><u>Login</u></html>");
		lbllogin.setHorizontalAlignment(SwingConstants.RIGHT);
		lbllogin.setForeground(new Color(0, 102, 204));
		lbllogin.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lbllogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_lbllogin = new GridBagConstraints();
		gbc_lbllogin.anchor = GridBagConstraints.EAST;
		gbc_lbllogin.gridx = 0;
		gbc_lbllogin.gridy = 7;
		loginFormPanelright.add(lbllogin, gbc_lbllogin);
		
		
		for (Component comp : loginFormPanelright.getComponents()) {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = GridBagConstraints.RELATIVE;  
			gbc.gridy = GridBagConstraints.RELATIVE;  
			gbc.weightx = 0.0;  
			gbc.weighty = 0.0;  
			gbc.fill = GridBagConstraints.NONE;  
			comp.setPreferredSize(comp.getPreferredSize());  
		}
		
		
		lbllogin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				leftPanel.remove(imageLabel);

				for (Component comp : rightPanel.getComponents()) {
				    comp.setVisible(false);
				}
				bgPanel.setPreferredSize(new Dimension(350, 0));
				leftPanel.setPreferredSize(new Dimension(450, 0)); 
				for (Component comp : leftPanel.getComponents()) {
				    comp.setVisible(true);
				}
				bgPanel.revalidate();
				
				imageLabel = new ScaledImageLabel("C:\\Users\\justf\\Downloads/eb.jpg");
				imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
				imageLabel.setVerticalAlignment(SwingConstants.CENTER);
				rightPanel.add(imageLabel,BorderLayout.CENTER);

			}
		});
		
		lblCreateAccount.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				bgPanel.remove(imageLabel);
				for (Component comp : rightPanel.getComponents()) {
				    comp.setVisible(true);
				}
				bgPanel.setPreferredSize(new Dimension(450, 0));
				leftPanel.setPreferredSize(new Dimension(350, 0)); 
				for (Component comp : leftPanel.getComponents()) {
				    comp.setVisible(false);
				}
				leftPanel.revalidate();
				
				imageLabel = new ScaledImageLabel("C:\\Users\\justf\\Downloads/eb.jpg");
				imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
				imageLabel.setVerticalAlignment(SwingConstants.CENTER);
				leftPanel.add(imageLabel, BorderLayout.CENTER);
			}
		});

	}

	private class ScaledImageLabel extends JLabel {
		private Image image;

		public ScaledImageLabel(String imagePath) {
			ImageIcon icon = new ImageIcon(imagePath);
			this.image = icon.getImage();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (image != null) {
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		}
	}


}
