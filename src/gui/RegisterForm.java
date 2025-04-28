package gui;

import javax.swing.*;
import javax.swing.border.*;
import system.Account;
import system.AccountFactory;
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
        setSize(550, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(249, 249, 249));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);

        // Header
        JLabel headerTitle = new JLabel("Northland Bank Account Registration Form");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 22));
        headerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(headerTitle);

        // Personal Information Panel
        JPanel personalPanel = new JPanel();
        personalPanel.setBackground(Color.WHITE);
        personalPanel.setBorder(createTitledBorder("Personal Information"));
        personalPanel.setLayout(new BoxLayout(personalPanel, BoxLayout.Y_AXIS));
        personalPanel.setMaximumSize(new Dimension(500, 300));

        JTextField fullNameField = createTextField();
        JSpinner dobSpinner = new JSpinner(new SpinnerDateModel());
        dobSpinner.setEditor(new JSpinner.DateEditor(dobSpinner, "yyyy-MM-dd"));
        JTextField phoneField = createTextField();
        JTextField emailField = createTextField();
        JTextField addressField = createTextField();
        
        personalPanel.add(createFormRowPersonalDetails("Full Name:", fullNameField));
        personalPanel.add(createFormRowPersonalDetails("Date of Birth:", dobSpinner));
        personalPanel.add(createFormRowPersonalDetails("Phone Number:", phoneField));
        personalPanel.add(createFormRowPersonalDetails("Email Address:", emailField));
        personalPanel.add(createFormRowPersonalDetails("Address:", addressField));

        mainPanel.add(personalPanel);

        mainPanel.add(Box.createVerticalStrut(20)); 

        // Account Information Panel
        JPanel accountPanel = new JPanel();
        accountPanel.setBackground(Color.WHITE);
        accountPanel.setBorder(createTitledBorder("Account Information"));
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));
        accountPanel.setMaximumSize(new Dimension(500, 250));

        JRadioButton savingsButton = new JRadioButton("Savings");
        JRadioButton checkingButton = new JRadioButton("Checking");
        JRadioButton loanButton = new JRadioButton("Loan");
        ButtonGroup accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(savingsButton);
        accountTypeGroup.add(checkingButton);
        accountTypeGroup.add(loanButton);

        JPanel accountTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        accountTypePanel.setBackground(Color.WHITE);
        accountTypePanel.add(savingsButton);
        accountTypePanel.add(checkingButton);
        accountTypePanel.add(loanButton);

        JLabel lblInitialDeposit = new JLabel("Initial Deposit:");
        JLabel lblAccontType = new JLabel("Account Type:");
        JLabel lblPassword = new JLabel("Password:");
        JLabel lblConfirmPassword = new JLabel("Confirm Password:");
        JTextField initialDepositField = createTextField();
        JPasswordField passwordField = createPasswordField();
        JPasswordField confirmPasswordField = createPasswordField();

        accountPanel.add(createFormRowAccountTypeLabel(lblAccontType, accountTypePanel));
        accountPanel.add(createFormRowAccountTypeLabel(lblInitialDeposit, initialDepositField));
        accountPanel.add(createFormRowAccountTypeLabel(lblPassword, passwordField));
        accountPanel.add(createFormRowAccountTypeLabel(lblConfirmPassword, confirmPasswordField));

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

            // Create Customer
            Customer customer = new Customer(fullName, dob, phone, email, address, password);

            String accountType = "";
            if (savingsButton.isSelected()) {
                accountType = "Savings";
            } else if (checkingButton.isSelected()) {
                accountType = "Checking";
            } else if (loanButton.isSelected()) {
                accountType = "Loan";
            }

            Account account = AccountFactory.createAccount(accountType, customer);

            if (loanButton.isSelected()) {
                ((LoanAccount) account).borrow(deposit);
            } else {
                account.deposit(deposit);
            }

            BankLedger.getInstance().addAccount(account);

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

    private JPanel createFormRowPersonalDetails(String labelText, JComponent field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(130, 25));
        panel.add(label);
        panel.add(field);
        return panel;
    }
    
    private JPanel createFormRowAccountTypeLabel(JLabel label, JComponent field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        label.setPreferredSize(new Dimension(130, 25));
        panel.add(label);
        panel.add(field);
        return panel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setBackground(new Color(240, 240, 240));
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setBackground(new Color(240, 240, 240));
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return field;
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
