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
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.GridLayout;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import system.Account;
import system.AccountFactory;
import system.Admin;
import system.AdminManager;
import system.BankLedger;
import system.Customer;

import java.awt.CardLayout;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\justf\\Downloads/bank.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Northland Bank");
		setSize(800, 657);

		getContentPane().setBackground(new Color(15, 32, 67));
        getContentPane().setLayout(new GridBagLayout());

        JPanel loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(350, 450));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Padding inside

        // Bank Icon
        JLabel bankIcon = new JLabel(new ImageIcon("C:\\Users\\justf\\Downloads/bankicon.jpg")); // Replace with your real path
        bankIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel title = new JLabel("Northland Bank Login");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(15, 32, 67));

     // Username Field
        TitledBorder userBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Username");
        userBorder.setTitleFont(new Font("Arial", Font.PLAIN, 12));
        userBorder.setTitleColor(Color.GRAY);

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setBorder(userBorder);

        // Password Field
        TitledBorder passBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Password");
        passBorder.setTitleFont(new Font("Arial", Font.PLAIN, 12));
        passBorder.setTitleColor(Color.GRAY);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(passBorder);

        
        // Login Button
        JButton loginButton = new JButton("Log In");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setPreferredSize(new Dimension(100, 40));
        
        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
            	loginButton.setBackground(new Color(30, 80, 180));
            }
            public void mouseExited(MouseEvent e) {
            	loginButton.setBackground(new Color(42, 104, 209));
            }
        });


        // Register Label
        JLabel registerLabel = new JLabel("<html><u>New user? Create an account</u></html>");
        registerLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Login.this.dispose();
        		RegisterForm registerForm = new RegisterForm();
        		registerForm.setVisible(true);
        		
        		
        	}
        });
        registerLabel.setForeground(new Color(0, 102, 204));
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components with spacing
        loginPanel.add(bankIcon);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        loginPanel.add(title);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        loginPanel.add(usernameField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        loginPanel.add(passwordField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
     // Admin Login Checkbox
        JCheckBox adminCheckBox = new JCheckBox("Login as Admin");
        adminCheckBox.setBackground(Color.WHITE);
        adminCheckBox.setFont(new Font("Arial", Font.PLAIN, 12));
        adminCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(adminCheckBox);
        
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (adminCheckBox.isSelected()) {
            	Admin admin = AdminManager.getInstance().validate(username, password);
            	if (admin != null) {
            	    JOptionPane.showMessageDialog(this, "Welcome Admin!");
            	    dispose();
            	    ControlPanel controlPanel = new ControlPanel(admin); // your own admin UI
            	    controlPanel.setVisible(true);
            	    controlPanel.setLocationRelativeTo(null);
            	    return;
            	} else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid admin credentials.");
                }
            } else {
            	BankLedger bankLedger = BankLedger.getInstance();
                Account matchedCustomer = null;

                for (Account a : bankLedger.getAllAccounts()) {
                    if (a.getOwner() != null &&
                        a.getOwner().getName().equals(username) &&
                        a.getOwner().getPassword().equals(password)) {
                        matchedCustomer = a;
                        break;
                    }
                }

                if (matchedCustomer != null) {
                	List<Account> customerAccounts = matchedCustomer.getOwner().getAccount();

                    if (customerAccounts.size() == 1) {
                        JOptionPane.showMessageDialog(Login.this, "Welcome, " + matchedCustomer.getOwner().getName() + "!");
                        dispose();
                        Dashboard dashboard = new Dashboard(customerAccounts.get(0));
                        dashboard.setVisible(true);
                        dashboard.setLocationRelativeTo(null);
                    }else if (customerAccounts.size() > 1) {
                        // Multiple accounts — ask the user which one to use
                        String[] options = customerAccounts.stream()
                            .map(acc -> acc.getAccountType() + " – " + acc.getAccountNumber())
                            .toArray(String[]::new);
                        
                        if (options.length == 0) {
                            JOptionPane.showMessageDialog(Login.this, "No account options found.");
                            return;
                        }

                        String selected = (String) JOptionPane.showInputDialog(
                            null,
                            "Select the account to use:",
                            "Choose Account",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]
                        );

                        if (selected != null) {
                            for (Account acc : customerAccounts) {
                                String label = acc.getAccountType() + " – " + acc.getAccountNumber();
                                if (label.equals(selected)) {
                                    JOptionPane.showMessageDialog(Login.this, "Welcome, " + matchedCustomer.getOwner().getName() + "!");
                                    dispose();
                                    Dashboard dashboard = new Dashboard(acc);
                                    dashboard.setVisible(true);
                                    dashboard.setLocationRelativeTo(null);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid username or password.");
                }

            }
        });
        
                    
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        loginPanel.add(registerLabel);

        getContentPane().add(loginPanel);
        setVisible(true);
        

	}
}
