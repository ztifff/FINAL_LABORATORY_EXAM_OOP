package gui;

import javax.swing.*;
import javax.swing.border.*;
import system.Account;
import system.AccountFactory;
import system.Bank;
import system.BankLedger;
import system.Customer;
import system.LoanAccount;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterForm extends JFrame {

	public RegisterForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\justf\\Downloads\\bank.png"));
		setTitle("Northland Bank Account Registration Form");
		setSize(550, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(249, 249, 249));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		getContentPane().add(mainPanel);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(255, 255, 255));
		FlowLayout flowLayout = (FlowLayout) headerPanel.getLayout();
		mainPanel.add(headerPanel);

		// Header
		JLabel headerTitle = new JLabel("Northland Bank Account Registration Form");
		headerPanel.add(headerTitle);
		headerTitle.setHorizontalAlignment(SwingConstants.CENTER);
		headerTitle.setFont(new Font("Arial", Font.BOLD, 22));
		headerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		headerTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		
		ImageIcon originalIcon = new ImageIcon("C:\\Users\\justf\\Downloads\\bankicon-removebg-preview.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(60, 50, Image.SCALE_SMOOTH); // width, height
		ImageIcon resizedIcon = new ImageIcon(scaledImage);

		JLabel bankIcon = new JLabel(resizedIcon);
		bankIcon.setHorizontalAlignment(SwingConstants.RIGHT);
		headerPanel.add(bankIcon);

		// Personal Information Panel
		JPanel personalPanel = new JPanel();
		personalPanel.setBackground(Color.WHITE);
		personalPanel.setBorder(createTitledBorder("Personal Information"));
		personalPanel.setLayout(new BoxLayout(personalPanel, BoxLayout.Y_AXIS));
		personalPanel.setMaximumSize(new Dimension(500, 300));

		// FULL NAME
		JPanel rowFullName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rowFullName.setBackground(Color.WHITE);
		JLabel lblFullName = new JLabel("Full Name:");
		lblFullName.setPreferredSize(new Dimension(130, 25));
		JTextField fullNameField = new JTextField(20);
		fullNameField.setBackground(new Color(240, 240, 240));
		fullNameField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		rowFullName.add(lblFullName);
		rowFullName.add(fullNameField);

		// DATE OF BIRTH
		JPanel rowDOB = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rowDOB.setBackground(Color.WHITE);
		JLabel lblDOB = new JLabel("Date of Birth:");
		lblDOB.setPreferredSize(new Dimension(130, 25));
		JSpinner dobSpinner = new JSpinner(new SpinnerDateModel());
		dobSpinner.setEditor(new JSpinner.DateEditor(dobSpinner, "yyyy-MM-dd"));
		rowDOB.add(lblDOB);
		rowDOB.add(dobSpinner);

		// PHONE NUMBER
		JPanel rowPhone = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rowPhone.setBackground(Color.WHITE);
		JLabel lblPhone = new JLabel("Phone Number:");
		lblPhone.setPreferredSize(new Dimension(130, 25));
		JTextField phoneField = new JTextField(20);
		phoneField.setBackground(new Color(240, 240, 240));
		phoneField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		rowPhone.add(lblPhone);
		rowPhone.add(phoneField);

		// EMAIL
		JPanel rowEmail = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rowEmail.setBackground(Color.WHITE);
		JLabel lblEmail = new JLabel("Email Address:");
		lblEmail.setPreferredSize(new Dimension(130, 25));
		JTextField emailField = new JTextField(20);
		emailField.setBackground(new Color(240, 240, 240));
		emailField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		rowEmail.add(lblEmail);
		rowEmail.add(emailField);

		// ADDRESS
		JPanel rowAddress = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rowAddress.setBackground(Color.WHITE);
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setPreferredSize(new Dimension(130, 25));
		JTextField addressField = new JTextField(20);
		addressField.setBackground(new Color(240, 240, 240));
		addressField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		rowAddress.add(lblAddress);
		rowAddress.add(addressField);

		// ADD ALL ROWS TO PERSONAL PANEL
		personalPanel.add(rowFullName);
		personalPanel.add(rowDOB);
		personalPanel.add(rowPhone);
		personalPanel.add(rowEmail);
		personalPanel.add(rowAddress);

		// Add to main panel
		mainPanel.add(personalPanel);

		mainPanel.add(Box.createVerticalStrut(20)); 

		// ACCOUNT INFORMATION PANEL
		JPanel accountPanel = new JPanel();
		accountPanel.setBackground(Color.WHITE);
		accountPanel.setBorder(createTitledBorder("Account Information"));
		accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));
		accountPanel.setMaximumSize(new Dimension(500, 250));

		// ACCOUNT TYPE
		JPanel rowAccountType = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rowAccountType.setBackground(Color.WHITE);
		JLabel lblAccountType = new JLabel("Account Type:");
		lblAccountType.setPreferredSize(new Dimension(130, 25));

		// Grouping buttons
		JRadioButton savingsButton = new JRadioButton("Savings");
		JRadioButton checkingButton = new JRadioButton("Checking");
		JRadioButton loanButton = new JRadioButton("Loan");
		ButtonGroup accountTypeGroup = new ButtonGroup();
		accountTypeGroup.add(savingsButton);
		accountTypeGroup.add(checkingButton);
		accountTypeGroup.add(loanButton);

		// Wrap radio buttons in panel
		JPanel accountTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		accountTypePanel.setBackground(Color.WHITE);
		accountTypePanel.add(savingsButton);
		accountTypePanel.add(checkingButton);
		accountTypePanel.add(loanButton);

		rowAccountType.add(lblAccountType);
		rowAccountType.add(accountTypePanel);

		// INITIAL DEPOSIT
		JPanel rowDeposit = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rowDeposit.setBackground(Color.WHITE);
		JLabel lblInitialDeposit = new JLabel("Initial Deposit:");
		lblInitialDeposit.setPreferredSize(new Dimension(130, 25));
		JTextField initialDepositField = new JTextField(20);
		initialDepositField.setBackground(new Color(240, 240, 240));
		initialDepositField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		rowDeposit.add(lblInitialDeposit);
		rowDeposit.add(initialDepositField);

		// PASSWORD
		JPanel rowPassword = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rowPassword.setBackground(Color.WHITE);
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setPreferredSize(new Dimension(130, 25));
		JPasswordField passwordField = new JPasswordField(20);
		passwordField.setBackground(new Color(240, 240, 240));
		passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		rowPassword.add(lblPassword);
		rowPassword.add(passwordField);

		// CONFIRM PASSWORD
		JPanel rowConfirmPassword = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rowConfirmPassword.setBackground(Color.WHITE);
		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setPreferredSize(new Dimension(130, 25));
		JPasswordField confirmPasswordField = new JPasswordField(20);
		confirmPasswordField.setBackground(new Color(240, 240, 240));
		confirmPasswordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		rowConfirmPassword.add(lblConfirmPassword);
		rowConfirmPassword.add(confirmPasswordField);

		// Add all rows to account panel
		accountPanel.add(rowAccountType);
		accountPanel.add(rowDeposit);
		accountPanel.add(rowPassword);
		accountPanel.add(rowConfirmPassword);

		// Add to main panel
		mainPanel.add(accountPanel);


		mainPanel.add(Box.createVerticalStrut(20)); // space

		// Buttons
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		buttonPanel.setBackground(new Color(249, 249, 249));

		JButton createAccountButton = new JButton("Create Account");
		createAccountButton.setBackground(new Color(42, 104, 209));
		createAccountButton.setForeground(Color.WHITE);
		createAccountButton.setFocusPainted(false);
		createAccountButton.setFont(new Font("Arial", Font.BOLD, 16));
		createAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		createAccountButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				createAccountButton.setBackground(new Color(30, 80, 180));
			}
			public void mouseExited(MouseEvent e) {
				createAccountButton.setBackground(new Color(42, 104, 209));
			}
		});

		JButton backButton = new JButton("Back to Login");
		backButton.setBackground(Color.WHITE);
		backButton.setForeground(new Color(42, 104, 209));
		backButton.setFocusPainted(false);
		backButton.setFont(new Font("Arial", Font.PLAIN, 14));
		backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		buttonPanel.add(backButton);
		buttonPanel.add(createAccountButton);

		mainPanel.add(buttonPanel);

		setVisible(true);

		// --- Button Actions ---
		backButton.addActionListener(e -> {
			dispose();
			Login login = new Login();
			login.setVisible(true);
			login.setLocationRelativeTo(null);
		});

		createAccountButton.addActionListener(e -> {
			String fullName = fullNameField.getText().trim();
			Date dobDate = (Date) dobSpinner.getValue();
			String dob = new SimpleDateFormat("yyyy-MM-dd").format(dobDate);
			String phone = phoneField.getText().trim();
			String email = emailField.getText().trim();
			String address = addressField.getText().trim();
			String depositStr = initialDepositField.getText().trim();
			String password = new String(passwordField.getPassword());
			String confirmPassword = new String(confirmPasswordField.getPassword());

			// Validation
			if (fullName.isEmpty() || dob.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() ||
					depositStr.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
					(!savingsButton.isSelected() && !checkingButton.isSelected() && !loanButton.isSelected())) {

				JOptionPane.showMessageDialog(this, "Please complete all fields.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!phone.matches("\\d{10,11}")) {
				JOptionPane.showMessageDialog(this, "Please enter a valid phone number (10-11 digits).", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!email.contains("@") || !email.contains(".com")) {
				JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			
			if (!password.equals(confirmPassword)) {
				JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			double deposit;
			try {
				deposit = Double.parseDouble(depositStr);
				if (deposit < 0) throw new NumberFormatException();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Please enter a valid initial deposit.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String accountType = "";
			if (savingsButton.isSelected()) {
				accountType = "Savings";
			} else if (checkingButton.isSelected()) {
				accountType = "Checking";
			} else if (loanButton.isSelected()) {
				accountType = "Loan";
			}
			
			Bank bank = BankLedger.getInstance().getBank();
		    Customer existing = bank.findCustomerByEmail(email);

		    if (existing != null) {
		        int choice = JOptionPane.showConfirmDialog(
		            null,
		            "An account already exists with this email. Do you want to open a new account under the same user?",
		            "Customer Found",
		            JOptionPane.YES_NO_OPTION
		        );

		        if (choice == JOptionPane.YES_OPTION) {
		        	boolean hasSameType = false;

		            for (Account acc : existing.getAccount()) {
		                if (acc.getAccountType().equalsIgnoreCase(accountType)) {
		                    hasSameType = true;
		                    break;
		                }
		            }

		            if (hasSameType) {
		                JOptionPane.showMessageDialog(null,
		                    "You already have a " + accountType + " account.",
		                    "Duplicate Account Type",
		                    JOptionPane.WARNING_MESSAGE);
		                return;
		            } else {
		                // Safe to create a new account
		                Account newAccount = AccountFactory.createAccount(accountType, existing);
		                bank.addAccount(newAccount);
		                existing.addAccount(newAccount); 

		                if (loanButton.isSelected()) {
		                    ((LoanAccount) newAccount).borrow(deposit);
		                } else {
		                    newAccount.deposit(deposit);
		                }

		                JOptionPane.showMessageDialog(null, "New account created successfully!\nYour Account Number: " + newAccount.getAccountNumber());
		                dispose();
		                Login login = new Login();
		                login.setVisible(true);
		                login.setLocationRelativeTo(null);
		                return;
		            }  
		        } else {
					return;
				}
		    }
			
			// Create Customer
			Customer customer = new Customer(fullName, dob, phone, email, address, password);

			Account account = AccountFactory.createAccount(accountType, customer);
			
			BankLedger.getInstance().addAccount(account);
			customer.addAccount(account);
			
			if (loanButton.isSelected()) {
				((LoanAccount) account).borrow(deposit);
			} else {
				account.deposit(deposit);
			}

			JOptionPane.showMessageDialog(this, "Account created successfully!\nYour Account Number: " + account.getAccountNumber());
			dispose();
			Login login = new Login();
			login.setVisible(true);
			login.setLocationRelativeTo(null);
		});

		savingsButton.addActionListener(e -> lblInitialDeposit.setText("Initial Deposit:"));
		checkingButton.addActionListener(e -> lblInitialDeposit.setText("Initial Deposit:"));
		loanButton.addActionListener(e -> lblInitialDeposit.setText("Borrow Amount:"));
	}

	private TitledBorder createTitledBorder(String title) {
		return BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY),
				title,
				TitledBorder.LEFT,
				TitledBorder.TOP,
				new Font("Arial", Font.BOLD, 16),
				new Color(85, 170, 85)
				);
	}
}
