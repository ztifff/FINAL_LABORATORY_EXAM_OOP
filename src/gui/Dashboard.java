package gui;

import javax.swing.*;

import system.Account;
import system.AccountFactory;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dashboard extends JFrame {
	
	public Dashboard(Account account) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\justf\\Downloads/bank.png"));
		setTitle("Northland Bank");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		JPanel navPanel = new JPanel();
		navPanel.setBackground(new Color(255, 255, 255));
		getContentPane().add(navPanel, BorderLayout.WEST);
		navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
		navPanel.setPreferredSize(new Dimension(250, 0));

		JPanel leftheaderPanel = new JPanel();
		leftheaderPanel.setBackground(new Color(255, 255, 255));
		navPanel.add(leftheaderPanel);
		leftheaderPanel.setPreferredSize(new Dimension(0, 0));
		leftheaderPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblheader = new JLabel("Northland Bank");
		lblheader.setFont(new Font("Montserrat", Font.BOLD, 18));
		lblheader.setHorizontalAlignment(SwingConstants.CENTER);
		leftheaderPanel.add(lblheader, BorderLayout.CENTER);

		JPanel leftPanel1 = new JPanel();
		leftPanel1.setBackground(new Color(0, 128, 255));
		navPanel.add(leftPanel1);
		leftPanel1.setLayout(new BorderLayout(0, 0));

		JPanel navPanel1 = new JPanel();
		navPanel1.setBackground(new Color(255, 255, 255));
		leftPanel1.add(navPanel1, BorderLayout.CENTER);
		navPanel1.setLayout(new GridLayout(0, 1, 0, 10));

		JButton btnDashboard = new JButton("Dashboard");
		btnDashboard.setHorizontalAlignment(SwingConstants.LEFT);
		btnDashboard.setBackground(new Color(255, 255, 255));
		btnDashboard.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnDashboard.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDashboard.setFocusPainted(false);
		btnDashboard.setBorderPainted(false);
		btnDashboard.setContentAreaFilled(true); // Optional, true means still fill the background color
		btnDashboard.setOpaque(true);            // Important for custom background color
		navPanel1.add(btnDashboard);

		

		JButton btnTransaction = new JButton("Transaction");
		btnTransaction.setHorizontalAlignment(SwingConstants.LEFT);
		btnTransaction.setBackground(new Color(255, 255, 255));
		btnTransaction.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTransaction.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnTransaction.setFocusPainted(false);
		btnTransaction.setBorderPainted(false);
		btnTransaction.setContentAreaFilled(true);
		btnTransaction.setOpaque(true);
		navPanel1.add(btnTransaction);

		

		JPanel spacing = new JPanel();
		spacing.setBackground(new Color(255, 255, 255));
		leftPanel1.add(spacing, BorderLayout.WEST);

		JPanel spacing1 = new JPanel();
		spacing1.setBackground(new Color(255, 255, 255));
		leftPanel1.add(spacing1, BorderLayout.EAST);

		JPanel leftPanel2 = new JPanel();
		navPanel.add(leftPanel2);
		leftPanel2.setLayout(new BorderLayout(0, 0));

		JPanel spacing4 = new JPanel();
		spacing4.setBackground(new Color(255, 255, 255));
		leftPanel2.add(spacing4, BorderLayout.WEST);

		JPanel spacing6 = new JPanel();
		spacing6.setBackground(new Color(255, 255, 255));
		FlowLayout fl_spacing6 = (FlowLayout) spacing6.getLayout();
		leftPanel2.add(spacing6, BorderLayout.EAST);

		JPanel navPanel2 = new JPanel();
		navPanel2.setBackground(new Color(255, 255, 255));
		leftPanel2.add(navPanel2, BorderLayout.CENTER);
		navPanel2.setLayout(new GridLayout(0, 1, 0, 10));

		JButton btnReport = new JButton("Report");
		btnReport.setHorizontalAlignment(SwingConstants.LEFT);
		navPanel2.add(btnReport);
		btnReport.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnReport.setFocusPainted(false);
		btnReport.setBorderPainted(false);
		btnReport.setContentAreaFilled(true); // Optional, true means still fill the background color
		btnReport.setOpaque(true);   
		btnReport.setBackground(new Color(255, 255, 255));
		btnReport.setFont(new Font("SansSerif", Font.BOLD, 14));

		btnReport.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnReport.setBackground(new Color(0, 128, 255));
			}
			public void mouseExited(MouseEvent e) {
				btnReport.setBackground(new Color(255, 255, 255));
			}
		});

		JButton btnAccount = new JButton("Account");
		btnAccount.setHorizontalAlignment(SwingConstants.LEFT);
		navPanel2.add(btnAccount);
		btnAccount.setOpaque(true);
		btnAccount.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnAccount.setFocusPainted(false);
		btnAccount.setContentAreaFilled(true);
		btnAccount.setBorderPainted(false);
		btnAccount.setBackground(Color.WHITE);
		btnAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));

		btnAccount.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnAccount.setBackground(new Color(0, 128, 255));
			}
			public void mouseExited(MouseEvent e) {
				btnAccount.setBackground(new Color(255, 255, 255));
			}
		});

		JPanel leftPanel3 = new JPanel();
		leftPanel3.setBackground(new Color(255, 255, 255));
		navPanel.add(leftPanel3);
		leftPanel3.setLayout(new BorderLayout(0, 0));

		JPanel leftPanel4 = new JPanel();
		leftPanel4.setBackground(new Color(255, 255, 255));
		navPanel.add(leftPanel4);
		leftPanel4.setLayout(new BorderLayout(0, 0));

		JPanel leftPanel5 = new JPanel();
		leftPanel5.setBackground(new Color(255, 255, 255));
		navPanel.add(leftPanel5);
		leftPanel5.setLayout(new BorderLayout(0, 0));
		
		JPanel logoutpanel = new JPanel();
		logoutpanel.setBackground(new Color(255, 255, 255));
		leftPanel5.add(logoutpanel, BorderLayout.CENTER);
		logoutpanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		logoutpanel.add(panel);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setOpaque(true);
		btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnLogout.setHorizontalAlignment(SwingConstants.LEFT);
		btnLogout.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLogout.setFocusPainted(false);
		btnLogout.setContentAreaFilled(true);
		btnLogout.setBorderPainted(false);
		btnLogout.setBackground(Color.WHITE);
		logoutpanel.add(btnLogout);
		
		btnLogout.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnLogout.setBackground(new Color(0, 128, 255));
			}
			public void mouseExited(MouseEvent e) {
				btnLogout.setBackground(new Color(255, 255, 255));
			}
			public void mouseClicked(MouseEvent e) {
				dispose();
				Login login= new Login();
				login.setVisible(true);
				login.setLocationRelativeTo(null);
			}
		});
		
		JPanel spacing7 = new JPanel();
		spacing7.setBackground(new Color(255, 255, 255));
		leftPanel5.add(spacing7, BorderLayout.WEST);
		
		JPanel spacing8 = new JPanel();
		spacing8.setBackground(new Color(255, 255, 255));
		leftPanel5.add(spacing8, BorderLayout.EAST);

		JPanel leftpanel6 = new JPanel();
		leftpanel6.setBackground(new Color(255, 255, 255));
		navPanel.add(leftpanel6);
		leftpanel6.setLayout(new BorderLayout(0, 0));
		
		JPanel spacing9 = new JPanel();
		spacing9.setBackground(new Color(255, 255, 255));
		leftpanel6.add(spacing9, BorderLayout.WEST);
		
		JPanel spacing10 = new JPanel();
		spacing10.setBackground(new Color(255, 255, 255));
		leftpanel6.add(spacing10, BorderLayout.EAST);
		
		JPanel infopanel = new JPanel();
		infopanel.setBackground(new Color(255, 255, 255));
		leftpanel6.add(infopanel, BorderLayout.CENTER);
		infopanel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lbluserName = new JLabel("Username: "+account.getOwner().getName());
		lbluserName.setHorizontalAlignment(SwingConstants.LEFT);
		lbluserName.setFont(new Font("SansSerif", Font.BOLD, 14));
		infopanel.add(lbluserName);
		
		JLabel lblId = new JLabel("ID: "+ account.getAccountNumber());
		lblId.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblId.setHorizontalAlignment(SwingConstants.LEFT);
		infopanel.add(lblId);

		JPanel leftpanel7 = new JPanel();
		leftpanel7.setBackground(new Color(255, 255, 255));
		navPanel.add(leftpanel7);
		leftpanel7.setLayout(new BorderLayout(0, 0));

		JPanel dashboardpanel = new JPanel();
		getContentPane().add(dashboardpanel, BorderLayout.CENTER);
		dashboardpanel.setLayout(new BorderLayout(0, 0));
		
		JPanel dashboardpanel1 = new JPanel();
		dashboardpanel1.setBackground(new Color(192, 192, 192));
		dashboardpanel.add(dashboardpanel1);
		dashboardpanel1.setLayout(new CardLayout(0, 0));
		
		JPanel dashboardpanel2 = new JPanel();
		dashboardpanel2.setBackground(new Color(240, 240, 240)); // Softer light gray
		dashboardpanel1.add(dashboardpanel2, "name_525434880627800");
		dashboardpanel2.setLayout(new BorderLayout(20, 20)); // Added gaps

		// --- HEADER ---
		JPanel dashboardheaderpanel = new JPanel();
		dashboardheaderpanel.setBackground(new Color(52, 58, 64)); // Darker header (professional)
		dashboardheaderpanel.setPreferredSize(new Dimension(0, 80));
		dashboardheaderpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 25));

		JLabel lblNewLabel = new JLabel("🏦 Northland Bank Dashboard"); // Icon touch
		lblNewLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
		lblNewLabel.setForeground(Color.WHITE); // White text on dark background
		dashboardheaderpanel.add(lblNewLabel);
		dashboardpanel2.add(dashboardheaderpanel, BorderLayout.NORTH);

		// --- RECENT TRANSACTIONS ---
		JPanel recentTransactionpanel = new JPanel();
		recentTransactionpanel.setBackground(Color.WHITE);
		dashboardpanel2.add(recentTransactionpanel, BorderLayout.EAST);
		recentTransactionpanel.setPreferredSize(new Dimension(250, 0));
		recentTransactionpanel.setLayout(new BorderLayout(10, 10));

		JLabel lblRecentTransactions = new JLabel("Recent Transactions");
		lblRecentTransactions.setFont(new Font("Roboto", Font.BOLD, 18));
		lblRecentTransactions.setForeground(new Color(33, 37, 41));
		lblRecentTransactions.setHorizontalAlignment(SwingConstants.CENTER);
		recentTransactionpanel.add(lblRecentTransactions, BorderLayout.NORTH);

		JPanel transactionsList = new JPanel();
		transactionsList.setBackground(Color.WHITE);
		transactionsList.setLayout(new BoxLayout(transactionsList, BoxLayout.Y_AXIS));

		// Sample transactions
		for (int i = 1; i <= 5; i++) {
		    JLabel transaction = new JLabel("• Paid to Merchant #" + i);
		    transaction.setFont(new Font("Roboto", Font.PLAIN, 14));
		    transaction.setForeground(new Color(85, 85, 85));
		    transaction.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
		    transactionsList.add(transaction);
		}

		JScrollPane scrollPane = new JScrollPane(transactionsList);
		scrollPane.setBorder(null);
		scrollPane.getViewport().setBackground(Color.WHITE);
		recentTransactionpanel.add(scrollPane, BorderLayout.CENTER);

		// --- CENTER BALANCE PANEL ---
		JPanel balancepanel = new JPanel();
		balancepanel.setBackground(Color.WHITE);
		balancepanel.setLayout(new BorderLayout(10, 10));
		dashboardpanel2.add(balancepanel, BorderLayout.CENTER);

		// Center Content
		JPanel balanceheaderpanel = new JPanel();
		balanceheaderpanel.setBackground(Color.WHITE);
		balanceheaderpanel.setLayout(new BoxLayout(balanceheaderpanel, BoxLayout.Y_AXIS));
		balanceheaderpanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

		JLabel lblbalance = new JLabel("Available Balance");
		lblbalance.setFont(new Font("Poppins", Font.BOLD, 20));
		lblbalance.setForeground(new Color(33, 37, 41));
		lblbalance.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblbalance1 = new JLabel(String.format("₱%,.2f", account.getBalance()));
		lblbalance1.setFont(new Font("Poppins", Font.BOLD, 32));
		lblbalance1.setForeground(new Color(0, 128, 0)); // Nice green color for money
		lblbalance1.setAlignmentX(Component.CENTER_ALIGNMENT);

		balanceheaderpanel.add(lblbalance);
		balanceheaderpanel.add(Box.createRigidArea(new Dimension(0, 15))); // Space between
		balanceheaderpanel.add(lblbalance1);

		balancepanel.add(balanceheaderpanel, BorderLayout.CENTER);

		// --- FOOTER (Notifications) ---
		JPanel notificationpanel = new JPanel();
		notificationpanel.setBackground(new Color(248, 249, 250));
		notificationpanel.setPreferredSize(new Dimension(0, 80));
		notificationpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 25));

		JLabel lblNotification = new JLabel("🔔 No new notifications");
		lblNotification.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblNotification.setForeground(new Color(108, 117, 125));
		notificationpanel.add(lblNotification);
		dashboardpanel2.add(notificationpanel, BorderLayout.SOUTH);


		
		JPanel transactionpanel = new JPanel();
		dashboardpanel.add(transactionpanel, BorderLayout.NORTH);
		transactionpanel.setLayout(new CardLayout(0, 0));
		transactionpanel.setVisible(false);
		
		JPanel transactionpanel1 = new JPanel();
		transactionpanel.add(transactionpanel1, "name_525509104762200");
		transactionpanel1.setLayout(new BorderLayout(0, 0));

		btnTransaction.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnTransaction.setBackground(new Color(0, 128, 255));
			}
			public void mouseExited(MouseEvent e) {
				btnTransaction.setBackground(new Color(255, 255, 255));
			}
			public void mouseClicked(MouseEvent e) {
				dashboardpanel1.setVisible(false);
				transactionpanel.setVisible(true);
			}
		});
		
		btnDashboard.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnDashboard.setBackground(new Color(0, 128, 255));
			}
			public void mouseExited(MouseEvent e) {
				btnDashboard.setBackground(new Color(255, 255, 255));
			}
			public void mouseClicked(MouseEvent e) {
				dashboardpanel1.setVisible(true);
				transactionpanel.setVisible(false);
			}
		});

	}

}
