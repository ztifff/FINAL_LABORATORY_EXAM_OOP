package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import system.Admin;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ControlPanel extends JFrame {
	private static final long serialVersionUID = 1L;
	private Color navBgColor = new Color(255, 255, 255);
	private Color activeColor = new Color(192, 192, 192);
	private Color hoverColor = new Color(220, 230, 240);
	private Color infoBgColor = new Color(240, 240, 240);
	private JButton btnLogout;
	private JButton activeButton;
	private JButton[] buttons;
	
	public ControlPanel(Admin admin) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\justf\\Downloads/bank.png"));
		setTitle("Northland Bank");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 920);
		setLocationRelativeTo(null);


		// NAVBAR MAIN PANEL
		JPanel navPanel = new JPanel();
		navPanel.setBackground(navBgColor);
		navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
		navPanel.setPreferredSize(new Dimension(250, 0));
		getContentPane().add(navPanel, BorderLayout.WEST);

		// HEADER
		JLabel lblHeader = new JLabel("🏦 Northland Bank", SwingConstants.CENTER);
		lblHeader.setFont(new Font("Montserrat", Font.BOLD, 20));
		lblHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblHeader.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // padding
		navPanel.add(lblHeader);



		// NAVIGATION BUTTONS
		JButton btnManageAccounts = new JButton("📊 ManageAccounts");
		btnManageAccounts.setHorizontalAlignment(SwingConstants.LEFT);
		btnManageAccounts.setBackground(navBgColor);
		btnManageAccounts.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnManageAccounts.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnManageAccounts.setFocusPainted(false);
		btnManageAccounts.setBorderPainted(false);
		btnManageAccounts.setContentAreaFilled(true);
		btnManageAccounts.setOpaque(true);
		btnManageAccounts.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 10));
		
		JButton btnManageTransactions = new JButton("💸 Manage Transactions");
		btnManageTransactions.setHorizontalAlignment(SwingConstants.LEFT);
		btnManageTransactions.setBackground(navBgColor);
		btnManageTransactions.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnManageTransactions.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnManageTransactions.setFocusPainted(false);
		btnManageTransactions.setBorderPainted(false);
		btnManageTransactions.setContentAreaFilled(true);
		btnManageTransactions.setOpaque(true);
		btnManageTransactions.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 10));
		
		JButton btnReports = new JButton("📄  Reports");
		btnReports.setHorizontalAlignment(SwingConstants.LEFT);
		btnReports.setBackground(navBgColor);
		btnReports.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnReports.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnReports.setFocusPainted(false);
		btnReports.setBorderPainted(false);
		btnReports.setContentAreaFilled(true);
		btnReports.setOpaque(true);
		btnReports.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 10));
		
		JButton btnLogout = new JButton("🚪 Logout");
		btnLogout.setHorizontalAlignment(SwingConstants.LEFT);
		btnLogout.setBackground(navBgColor);
		btnLogout.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnLogout.setFocusPainted(false);
		btnLogout.setBorderPainted(false);
		btnLogout.setContentAreaFilled(true);
		btnLogout.setOpaque(true);
		btnLogout.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 10));

		// PANEL to contain buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(255, 255, 255));
		buttonPanel.setLayout(new GridLayout(0, 1, 0, 10)); // spacing
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		navPanel.add(buttonPanel);

		buttonPanel.add(btnManageAccounts);
		buttonPanel.add(btnManageTransactions);
		buttonPanel.add(btnReports);

		// SPACER
		buttonPanel.add(Box.createVerticalGlue());

		// INFO PANEL (Bottom user info)
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(infoBgColor);
		infoPanel.setLayout(new GridLayout(2, 1));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

		JLabel lblUsername = new JLabel("👤 " + admin.getName());
		lblUsername.setFont(new Font("SansSerif", Font.PLAIN, 14));
		JLabel lblId = new JLabel("🆔 ID: " + admin.getAccountNumber());
		lblId.setFont(new Font("SansSerif", Font.PLAIN, 14));

		infoPanel.add(lblUsername);
		infoPanel.add(lblId);
		buttonPanel.add(infoPanel);


		// LOGOUT BUTTON (separate)
		navPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonPanel.add(btnLogout);

		// Mouse Hover Effect for All Buttons
		buttons = new JButton[] {btnManageAccounts, btnManageTransactions, btnReports, btnLogout};
		activeButton = btnManageAccounts;

		for (JButton button : buttons) {
			button.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					if (button != activeButton) button.setBackground(hoverColor);
				}
				public void mouseExited(MouseEvent e) {
					if (button != activeButton) button.setBackground(navBgColor);
				}
			});
		}



		// Example: when you open dashboard by default
		setActiveButton(btnManageAccounts);

		// Example: If you want to change active panel when clicking other buttons
		btnManageAccounts.addActionListener(e -> setActiveButton(btnManageAccounts));
		btnManageTransactions.addActionListener(e -> setActiveButton(btnManageTransactions));
		btnReports.addActionListener(e -> setActiveButton(btnReports));

		// LOGOUT BUTTON BEHAVIOR
		btnLogout.addActionListener(e -> {
			dispose();
			Login login = new Login();
			login.setVisible(true);
			login.setLocationRelativeTo(null);
		});
	}
	
	private void setActiveButton(JButton selectedButton) {
		for (JButton button : buttons) {
			if (button == selectedButton) {
				button.setBackground(activeColor);
				button.setForeground(Color.WHITE);
			} else if (button != btnLogout) { 
				button.setBackground(navBgColor);
				button.setForeground(Color.BLACK);
			}
		}
		activeButton = selectedButton;
	}

}
