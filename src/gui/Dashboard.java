package gui;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    public Dashboard() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 600);
        setLocationRelativeTo(null); // Center the window
        setUndecorated(false); // Set to true if you want to remove the default frame border
        getContentPane().setBackground(new Color(15, 32, 67)); // Dark blue background
        getContentPane().setLayout(new GridBagLayout());

        JPanel loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(350, 450));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Padding inside

        // Bank Icon
        JLabel bankIcon = new JLabel(new ImageIcon("C:\\Users\\justf\\Downloads/bankicon.jpg")); // replace with your image
        bankIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel title = new JLabel("BANK LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(15, 32, 67));

        // Username Field
        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));

        // Password Field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        // Remember Me Checkbox
        JCheckBox rememberMe = new JCheckBox("Remember me");
        rememberMe.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Login Button
        JButton loginButton = new JButton("Log In");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Register Label
        JLabel registerLabel = new JLabel("New user? Create an account");
        registerLabel.setForeground(new Color(0, 102, 204));
        registerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components
        loginPanel.add(bankIcon);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(title);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(usernameField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(passwordField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(rememberMe);
        Component rigidArea = Box.createRigidArea(new Dimension(10, 20));
        loginPanel.add(rigidArea);
        loginPanel.add(loginButton);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(registerLabel);

        getContentPane().add(loginPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
