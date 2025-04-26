package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterForm extends JFrame {

    public RegisterForm() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\justf\\Downloads/icon.jpg"));
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
        JTextField dobField = createTextField();
        JTextField phoneField = createTextField();
        JTextField emailField = createTextField();
        JTextField addressField = createTextField();

        addFormRow(personalPanel, "Full Name:", fullNameField);
        addFormRow(personalPanel, "Date of Birth:", dobField);
        addFormRow(personalPanel, "Phone Number:", phoneField);
        addFormRow(personalPanel, "Email Address:", emailField);
        addFormRow(personalPanel, "Address:", addressField);

        mainPanel.add(personalPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Account Information Panel
        JPanel accountPanel = createFormPanel("Account Information");

        JRadioButton savingsButton = new JRadioButton("Savings");
        JRadioButton checkingButton = new JRadioButton("Checking");
        ButtonGroup accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(savingsButton);
        accountTypeGroup.add(checkingButton);

        JPanel accountTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        accountTypePanel.setBackground(Color.WHITE);
        accountTypePanel.add(savingsButton);
        accountTypePanel.add(checkingButton);

        JTextField initialDepositField = createTextField();

        addFormRow(accountPanel, "Account Type:", accountTypePanel);
        addFormRow(accountPanel, "Initial Deposit:", initialDepositField);

        mainPanel.add(accountPanel);
        
        
        JPasswordField passwordField = createPasswordField();
        JPasswordField confirmPasswordField = createPasswordField();

        addFormRow(accountPanel, "Password:", passwordField);
        addFormRow(accountPanel, "Confirm Password:", confirmPasswordField);


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


    private void addFormRow(JPanel panel, String labelText, Component field) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = panel.getComponentCount() / 2;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setBackground(new Color(240, 240, 240));
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return field;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterForm::new);
    }
}
