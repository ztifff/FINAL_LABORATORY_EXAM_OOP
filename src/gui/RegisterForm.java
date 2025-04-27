package gui;

import javax.swing.*;
import javax.swing.border.*;

import system.Account;
import system.AccountFactory;
import system.Bank;
import system.BankLedger;
import system.CheckingAccount;
import system.Customer;
import system.LoanAccount;
import system.SavingsAccount;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterForm extends JFrame {

    public RegisterForm() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\justf\\Downloads/bank.png"));
        setTitle("Northland Bank Account Registration Form");
        setSize(550, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(249, 249, 249)); // Slightly off-white
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(230, 250, 230)); // soft green
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JLabel headerTitle = new JLabel("Northland Bank Account Registration Form");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 22));
        headerPanel.add(headerTitle);
        mainPanel.add(headerPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Personal Information Panel
        JPanel personalPanel = createFormPanel("Personal Information");

        JTextField fullNameField = createTextField();
        JLabel lblfullName = new JLabel("Full Name:");
        JSpinner dobSpinner = new JSpinner(new SpinnerDateModel());
        JLabel lbldob = new JLabel("Date of Birth:");
        dobSpinner.setEditor(new JSpinner.DateEditor(dobSpinner, "yyyy-MM-dd")); 
        JTextField phoneField = createTextField();
        JLabel lblphone= new JLabel("Phone Number:");
        JTextField emailField = createTextField();
        JLabel lblemail= new JLabel("Email Address:");
        JTextField addressField = createTextField();
        JLabel lbladdress= new JLabel("Address:");

        addFormRow(personalPanel, lblfullName, fullNameField);
        addFormRow(personalPanel, lbldob, dobSpinner);
        addFormRow(personalPanel, lblphone, phoneField);
        addFormRow(personalPanel, lblemail, emailField);
        addFormRow(personalPanel, lbladdress, addressField);

        mainPanel.add(personalPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Account Information Panel
        JPanel accountPanel = createFormPanel("Account Information");

        JRadioButton savingsButton = new JRadioButton("Savings");
        JRadioButton checkingButton = new JRadioButton("Checking");
        JRadioButton loanButton = new JRadioButton("Loan");
        ButtonGroup accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(savingsButton);
        accountTypeGroup.add(checkingButton);
        accountTypeGroup.add(loanButton);

        JPanel accountTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        accountTypePanel.setBackground(Color.WHITE);
        accountTypePanel.add(savingsButton);
        accountTypePanel.add(checkingButton);
        accountTypePanel.add(loanButton);
        JLabel lblAccountype = new JLabel("Account Type:");

        JTextField initialDepositField = createTextField();
        JLabel lblinitialDeposit = new JLabel("Initial Deposit:");

        addFormRow(accountPanel, lblAccountype, accountTypePanel);
        addFormRow(accountPanel, lblinitialDeposit, initialDepositField);

        mainPanel.add(accountPanel);
        
        
        JPasswordField passwordField = createPasswordField();
        JPasswordField confirmPasswordField = createPasswordField();
        JLabel lblPasswordField = new JLabel("Password:");
        JLabel lblConfirmPasswordField = new JLabel("Confirm Password:");

        addFormRow(accountPanel, lblPasswordField, passwordField);
        addFormRow(accountPanel, lblConfirmPasswordField, confirmPasswordField);


        // Create Account Button
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccountButton.setBackground(new Color(42, 104, 209));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFocusPainted(false);
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 16));
        createAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        
        // Hover effect
        createAccountButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                createAccountButton.setBackground(new Color(30, 80, 180));
            }
            public void mouseExited(MouseEvent e) {
                createAccountButton.setBackground(new Color(42, 104, 209));
            }
        });
        
     // Back to Login Button
        JButton backButton = new JButton("Back to Login");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(42, 104, 209));
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add action listener
        backButton.addActionListener(e -> {
            dispose(); // close this frame
            Login login = new Login();
            login.setVisible(true);
            login.setLocationRelativeTo(null);
        });

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // small space
        mainPanel.add(backButton);


        mainPanel.add(createAccountButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        setVisible(true);
        
        
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

            // Create Customer
            Customer customer = new Customer(fullName, dob, phone, email, address, password);
            
            String accountType = "";
            if (savingsButton.isSelected()) {
                accountType = "Savings";
            } else if (checkingButton.isSelected()) {
                accountType = "Checking";
            } else if (loanButton.isSelected()) {
                accountType = "Loan";
            } else {
                JOptionPane.showMessageDialog(this, "Please select an account type.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create Account
         // Use AccountFactory to create the account
            Account account = AccountFactory.createAccount(accountType, customer);

            if (loanButton.isSelected()) {
                ((LoanAccount) account).borrow(deposit);
            } else {
                account.deposit(deposit);
            }
            
            BankLedger bankLedger = BankLedger.getInstance();
            bankLedger.addAccount(account);

            JOptionPane.showMessageDialog(this, "Account created successfully!\nYour Account Number: " + account.getAccountNumber());
            dispose(); 
            Login login = new Login();
            login.setVisible(true);
            login.setLocationRelativeTo(null);
        });
        
        savingsButton.addActionListener(e -> lblinitialDeposit.setText("Initial Deposit:"));
        checkingButton.addActionListener(e -> lblinitialDeposit.setText("Initial Deposit:"));
        loanButton.addActionListener(e -> lblinitialDeposit.setText("Borrow Amount:"));


    }

    private JPanel createFormPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                new Color(85, 170, 85)
        ));
        panel.setLayout(new GridBagLayout());
        return panel;
    }
    
    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setBackground(new Color(240, 240, 240));
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return field;
    }


    private void addFormRow(JPanel panel, Component labelText, Component field) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = panel.getComponentCount() / 2;
        panel.add(labelText, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setBackground(new Color(240, 240, 240));
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return field;
    }
}
