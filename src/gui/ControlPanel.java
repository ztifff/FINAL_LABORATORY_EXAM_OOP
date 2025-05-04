package gui;
import java.awt.event.*;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import system.Account;
import system.Admin;
import system.Bank;
import system.BankLedger;
import system.Customer;
import system.Transaction;

import javax.swing.SwingConstants;
import java.awt.CardLayout;

public class ControlPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private boolean reportsVisible = false;
	private Color navBgColor = new Color(255, 255, 255);
	private Color activeColor = new Color(128, 128, 128);
	private Color hoverColor = new Color(220, 230, 240);
	private Color infoBgColor = new Color(240, 240, 240);
	private JPanel tablePanel;
	private JScrollPane scrollPane;
	private JButton logoutBtn;
	private JButton activeButton;
	private JTable table;
	private JTextField textField;
	private JButton[] buttons;

	public ControlPanel(Admin admin) {
		setTitle("Monthly Transaction Summary");
		setSize(1200, 920);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false);

		// Sidebar
		JPanel sidebar = new JPanel();
		sidebar.setBackground(new Color(255, 255, 255));
		sidebar.setBounds(0, 0, 269, 881);
		sidebar.setLayout(null);
		getContentPane().add(sidebar);

		JLabel adminLabel = new JLabel("🏦 ADMINISTRATOR", SwingConstants.CENTER);
		adminLabel.setBounds(0, 11, 269, 53);
		adminLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
		adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		adminLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // padding
		sidebar.add(adminLabel);

		JButton btnmanageAccount = new JButton("👤 Manage Accounts");
		btnmanageAccount.setBackground(navBgColor);
		btnmanageAccount.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnmanageAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnmanageAccount.setFocusPainted(false);
		btnmanageAccount.setBorderPainted(false);
		btnmanageAccount.setContentAreaFilled(true);
		btnmanageAccount.setOpaque(true);
		btnmanageAccount.setBounds(0, 86, 269, 85);
		sidebar.add(btnmanageAccount);

		JButton btnmanageTransaction = new JButton("💸 Manage Transaction");
		btnmanageTransaction.setBackground(navBgColor);
		btnmanageTransaction.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnmanageTransaction.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnmanageTransaction.setFocusPainted(false);
		btnmanageTransaction.setBorderPainted(false);
		btnmanageTransaction.setContentAreaFilled(true);
		btnmanageTransaction.setOpaque(true);
		btnmanageTransaction.setBounds(0, 186, 269, 85);
		sidebar.add(btnmanageTransaction);

		// Reports Dropdown
		JButton reportsToggle = new JButton("Reports ▼");
		reportsToggle.setBackground(navBgColor);
		reportsToggle.setFont(new Font("SansSerif", Font.BOLD, 15));
		reportsToggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
		reportsToggle.setFocusPainted(false);
		reportsToggle.setBorderPainted(false);
		reportsToggle.setContentAreaFilled(true);
		reportsToggle.setOpaque(true);
		reportsToggle.setBounds(0, 289, 269, 85);
		sidebar.add(reportsToggle);

		// Reports Panel (initially hidden)
		JPanel reportsPanel = new JPanel();
		reportsPanel.setLayout(new GridLayout(3, 1));
		reportsPanel.setBounds(0, 374, 269, 186);
		reportsPanel.setBackground(new Color(220, 220, 220));
		reportsPanel.setVisible(false);

		// Report Buttons
		JButton monthlyBtn = new JButton("Monthly Transaction Summary");
		monthlyBtn.setBackground(navBgColor);
		monthlyBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
		monthlyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		monthlyBtn.setFocusPainted(false);
		monthlyBtn.setBorderPainted(false);
		monthlyBtn.setContentAreaFilled(true);
		monthlyBtn.setOpaque(true);

		JButton balanceBtn = new JButton("Account Balance Report");
		balanceBtn.setBackground(navBgColor);
		balanceBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
		balanceBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		balanceBtn.setFocusPainted(false);
		balanceBtn.setBorderPainted(false);
		balanceBtn.setContentAreaFilled(true);
		balanceBtn.setOpaque(true);

		JButton dailyBtn = new JButton("Daily Transaction Report");
		dailyBtn.setBackground(navBgColor);
		dailyBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
		dailyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		dailyBtn.setFocusPainted(false);
		dailyBtn.setBorderPainted(false);
		dailyBtn.setContentAreaFilled(true);
		dailyBtn.setOpaque(true);

		reportsPanel.add(monthlyBtn);
		reportsPanel.add(balanceBtn);
		reportsPanel.add(dailyBtn);
		sidebar.add(reportsPanel);

		// Toggle Reports Visibility
		reportsToggle.addActionListener(event -> {
			reportsVisible = !reportsVisible;
			reportsPanel.setVisible(reportsVisible);
			reportsToggle.setText(reportsVisible ? "Reports ▲" : "Reports ▼");
		});

		// Logout Button (Styled like others)
		logoutBtn = new JButton("Logout");
		logoutBtn.setBackground(navBgColor);
		logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
		logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logoutBtn.setFocusPainted(false);
		logoutBtn.setBorderPainted(false);
		logoutBtn.setContentAreaFilled(true);
		logoutBtn.setOpaque(true);
		logoutBtn.setBounds(0, 785, 269, 85);
		sidebar.add(logoutBtn);

		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(0, 689, 269, 85);
		sidebar.add(infoPanel);
		infoPanel.setLayout(new GridLayout(2, 1, 0, 0));

		// Autofill Labels
		JLabel autofillName = new JLabel("Admin Name (Autofill)");
		autofillName.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(autofillName);

		JLabel autofillID = new JLabel("Account ID (Autofill)");
		autofillID.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(autofillID);
		autofillID.setBackground(new Color(255, 255, 255));

		// Mouse Hover Effect for All Buttons
		buttons = new JButton[] {btnmanageAccount, btnmanageTransaction, reportsToggle, logoutBtn};
		activeButton = btnmanageAccount;

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
		setActiveButton(btnmanageAccount);
		// Example: If you want to change active panel when clicking other buttons
		btnmanageTransaction.addActionListener(e -> {
			setActiveButton(btnmanageTransaction);
			reportsPanel.setVisible(false);
			reportsToggle.setText("Reports ▼");
		});
		reportsToggle.addActionListener(e -> setActiveButton(reportsToggle));

		JPanel switchPanel = new JPanel();
		switchPanel.setBounds(268, 0, 916, 881);
		getContentPane().add(switchPanel);
		switchPanel.setLayout(new CardLayout(0, 0));

		//-----MANAGE ACCOUNTS-----
		JPanel manageAccounts = new JPanel();
		manageAccounts.setBounds(268, 0, 916, 881);
		switchPanel.add(manageAccounts);
		manageAccounts.setLayout(new CardLayout(0, 0));

		JPanel manageAccounts1 = new JPanel();
		manageAccounts.add(manageAccounts1, "name_929505867159100");
		manageAccounts1.setLayout(null);

		// --- HEADER ---
		JPanel manageAccountsheaderpanel = new JPanel();
		manageAccountsheaderpanel.setBounds(0, 0, 916, 72);
		manageAccountsheaderpanel.setBackground(new Color(52, 58, 64)); // Darker header (professional)
		manageAccountsheaderpanel.setPreferredSize(new Dimension(0, 80));
		manageAccountsheaderpanel.setLayout(new GridLayout(0, 2, 0, 0));
		manageAccountsheaderpanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

		JLabel lblmanageAccounts = new JLabel("🏦 Northland Bank");
		lblmanageAccounts.setHorizontalAlignment(SwingConstants.CENTER);
		lblmanageAccounts.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
		lblmanageAccounts.setForeground(Color.WHITE); // White text on dark background
		manageAccountsheaderpanel.add(lblmanageAccounts);
		manageAccounts1.add(manageAccountsheaderpanel, BorderLayout.NORTH);

		ImageIcon originalIcon = new ImageIcon("C:\\Users\\justf\\eclipse-workspace\\FINAL_LABORATORY_EXAM_OOP\\src\\photo\\bankicon-removebg-preview.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(80, 70, Image.SCALE_SMOOTH); // width, height
		ImageIcon resizedIcon = new ImageIcon(scaledImage);

		JLabel manageAccountsbankIcon = new JLabel(resizedIcon);
		manageAccountsbankIcon.setHorizontalAlignment(SwingConstants.RIGHT);
		manageAccountsheaderpanel.add(manageAccountsbankIcon);

		// Table Panel with JTable
		tablePanel = new JPanel();
		tablePanel.setBounds(51, 223, 826, 627); // Adjust height as needed
		tablePanel.setLayout(new BorderLayout());

		String[] manageAccountsHeaders = {"ID", "Name", "Email", "Contact Number", "Address", "Account Type"};
		DefaultTableModel tableModel = new DefaultTableModel(manageAccountsHeaders, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(tableModel);

		table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		table.setRowHeight(30); 
		table.setFillsViewportHeight(true); 

		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (!isSelected) {
					c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE); 
				} else {
					c.setBackground(new Color(200, 230, 255)); 
				}
				return c;
			}
		});

		Bank bank = BankLedger.getInstance().getBank();
		List<Account> customers =  bank.getAllAccounts();
		tableModel.setRowCount(0); // Clear existing rows

		for (Account account : customers) {
			String accountNumber = account.getAccountNumber();
			String name = account.getOwner().getName();
			String email = account.getOwner().getEmail();
			String contact = account.getOwner().getContactNumber();
			String address = account.getOwner().getAddress();
			String accountType = account.getAccountType();
			// Add row to table
			tableModel.addRow(new Object[]{accountNumber, name, email, contact, address, accountType});
		}

		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 14));
		header.setBackground(new Color(220, 220, 220));
		header.setForeground(Color.BLACK);
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);

		scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		manageAccounts1.add(tablePanel);

		JButton btnNewButton = new JButton("Create New");
		btnNewButton.setBounds(72, 106, 141, 56);
		manageAccounts1.add(btnNewButton);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(411, 106, 141, 56);
		manageAccounts1.add(btnDelete);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(242, 106, 141, 56);
		manageAccounts1.add(btnEdit);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(765, 106, 141, 56);
		manageAccounts1.add(btnSearch);

		textField = new JTextField();
		textField.setBounds(593, 115, 164, 38);
		manageAccounts1.add(textField);
		textField.setColumns(10);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(51, 181, 89, 31);
		manageAccounts1.add(btnRefresh);

		btnRefresh.addActionListener(e -> {
			updateTransactionTable(tableModel);
		});


		//-----MONTHLY TRANSACTION SUMMARY-----
		JPanel montlyTransactionSummaryPanel = new JPanel();
		montlyTransactionSummaryPanel.setBounds(268, 0, 916, 881);
		switchPanel.add(montlyTransactionSummaryPanel);
		montlyTransactionSummaryPanel.setLayout(new CardLayout(0, 0));
		montlyTransactionSummaryPanel.setVisible(false);

		JPanel montlyTransactionSummaryPanel1 = new JPanel();
		montlyTransactionSummaryPanel.add(montlyTransactionSummaryPanel1, "name_929505867159100");
		montlyTransactionSummaryPanel1.setLayout(null);

		// --- HEADER ---
		JPanel montlyTransactionheaderpanel = new JPanel();
		montlyTransactionheaderpanel.setBounds(0, 0, 916, 72);
		montlyTransactionheaderpanel.setBackground(new Color(52, 58, 64)); // Darker header (professional)
		montlyTransactionheaderpanel.setPreferredSize(new Dimension(0, 80));
		montlyTransactionheaderpanel.setLayout(new GridLayout(0, 2, 0, 0));
		montlyTransactionheaderpanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

		JLabel lblmontlyTransactionheader = new JLabel("🏦 Northland Bank");
		lblmontlyTransactionheader.setHorizontalAlignment(SwingConstants.CENTER);
		lblmontlyTransactionheader.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
		lblmontlyTransactionheader.setForeground(Color.WHITE); // White text on dark background
		montlyTransactionheaderpanel.add(lblmontlyTransactionheader);
		montlyTransactionSummaryPanel1.add(montlyTransactionheaderpanel, BorderLayout.NORTH);

		JLabel montlyTransactionbankIcon = new JLabel(resizedIcon);
		montlyTransactionbankIcon.setHorizontalAlignment(SwingConstants.RIGHT);
		montlyTransactionheaderpanel.add(montlyTransactionbankIcon);

		// Table Panel with JTable
		tablePanel = new JPanel();
		tablePanel.setBounds(51, 83, 809, 600); // Adjust height as needed
		tablePanel.setLayout(new BorderLayout());

		String[] monthlyTransactionHeaders = {"Date", "Name", "ID", "Amount", "Type", "Balance"};
		DefaultTableModel tableModel1 = new DefaultTableModel(monthlyTransactionHeaders, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(tableModel1);

		table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		table.setRowHeight(30); 
		table.setFillsViewportHeight(true); 

		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (!isSelected) {
					c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE); 
				} else {
					c.setBackground(new Color(200, 230, 255)); 
				}
				return c;
			}
		});
		
		for (Account account : customers) {
			java.util.List<Transaction> transactions = account.getHistory().getHistoryList();
			tableModel1.setRowCount(0); // Clear existing rows
			
			for (Transaction transaction : transactions) {
				String date = transaction.getDate().toString();
				String iD = account.getAccountNumber();
				String type = transaction.getAction();
				String amount = String.format("₱%.2f", transaction.getAmount());
				double balance = account.getBalance();
				String recipient = account.getOwner().getName();
				
				// Handle transfers
				if (type.startsWith("Transfer to ")) {
					recipient = type.substring(12); // Extract recipient from action text
					type = "Transfer Sent";
				} else if (type.startsWith("Transfer from ")) {
					recipient = type.substring(14); // Extract recipient from action text
					type = "Transfer Received";
				}

				// Add row to table
				tableModel1.addRow(new Object[]{date, recipient, iD, amount, type, balance});
			}
			
		}
		
		

		

		JTableHeader header1 = table.getTableHeader();
		header1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		header1.setBackground(new Color(220, 220, 220));
		header1.setForeground(Color.BLACK);
		header1.setReorderingAllowed(false);
		header1.setResizingAllowed(false);

		scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		montlyTransactionSummaryPanel1.add(tablePanel);

		// Transaction filter
		JLabel dateRangeLabel = new JLabel("Date Range:");
		dateRangeLabel.setBounds(85, 736, 100, 25);
		montlyTransactionSummaryPanel1.add(dateRangeLabel);

		String[] options = {"Select Option", "Last 7 Days", "Last 30 Days", "Last 60 Days", "Custom"};
		JComboBox<String> dateRangeDropdown = new JComboBox<>(options);
		dateRangeDropdown.setBounds(185, 736, 150, 25);
		montlyTransactionSummaryPanel1.add(dateRangeDropdown);

		JLabel fromLabel = new JLabel("From:");
		fromLabel.setBounds(85, 776, 100, 25);
		montlyTransactionSummaryPanel1.add(fromLabel);

		JTextField fromDate = new JTextField("mm/dd/yyyy");
		fromDate.setBounds(185, 776, 150, 25);
		montlyTransactionSummaryPanel1.add(fromDate);

		JLabel toLabel = new JLabel("To:");
		toLabel.setBounds(85, 816, 100, 25);
		montlyTransactionSummaryPanel1.add(toLabel);

		JTextField toDate = new JTextField("mm/dd/yyyy");
		toDate.setBounds(185, 816, 150, 25);
		montlyTransactionSummaryPanel1.add(toDate);

		JButton submitBtn = new JButton("Submit");
		submitBtn.setBackground(new Color(100, 180, 255));
		submitBtn.setBounds(396, 811, 100, 30);
		montlyTransactionSummaryPanel1.add(submitBtn);

		logoutBtn.addActionListener(event -> {
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				dispose();
				Login login = new Login();
				login.setVisible(true);
				login.setLocationRelativeTo(null);
			}
		});

		submitBtn.addActionListener(event -> {
			String range = (String) dateRangeDropdown.getSelectedItem();
			String from = fromDate.getText();
			String to = toDate.getText();
			JOptionPane.showMessageDialog(this, "Fetching transactions from " + from + " to " + to + " for range: " + range);
		});


		//Switching panel
		btnmanageAccount.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				montlyTransactionSummaryPanel.setVisible(false);
				manageAccounts.setVisible(true);
				setActiveButton(btnmanageAccount);
				reportsVisible = false;
				reportsPanel.setVisible(reportsVisible);
				reportsToggle.setText(reportsVisible ? "Reports ▲" : "Reports ▼");
			}
		});

		monthlyBtn.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				montlyTransactionSummaryPanel.setVisible(true);
				manageAccounts.setVisible(false);
				reportsVisible = false;
				reportsPanel.setVisible(reportsVisible);
				reportsToggle.setText(reportsVisible ? "Reports ▲" : "Reports ▼");
			}
		});
	}

	public void updateTransactionTable(DefaultTableModel tableModel) {
		Bank bank = BankLedger.getInstance().getBank();
		List<Account> customers =  bank.getAllAccounts();
		tableModel.setRowCount(0); // Clear existing rows

		for (Account account : customers) {
			String accountNumber = account.getAccountNumber();
			String name = account.getOwner().getName();
			String email = account.getOwner().getEmail();
			String contact = account.getOwner().getContactNumber();
			String address = account.getOwner().getAddress();
			String accountType = account.getAccountType();
			// Add row to table
			tableModel.addRow(new Object[]{accountNumber, name, email, contact, address, accountType});

		}
	}

	private void setActiveButton(JButton selectedButton) {
		for (JButton button : buttons) {
			if (button == selectedButton) {
				button.setBackground(activeColor);
				button.setForeground(Color.WHITE);
			} else if (button != logoutBtn) { 
				button.setBackground(navBgColor);
				button.setForeground(Color.BLACK);
			}
		}
		activeButton = selectedButton;
	}
}
