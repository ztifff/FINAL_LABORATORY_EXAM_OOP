package gui;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.EventObject;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import system.Account;
import system.AccountFactory;
import system.Admin;
import system.Bank;
import system.BankLedger;
import system.CheckingAccount;
import system.Customer;
import system.LoanAccount;
import system.LowBalanceNotifier;
import system.Notification;
import system.ReportGenerator;
import system.SavingsAccount;
import system.Transaction;

import javax.swing.SwingConstants;
import java.awt.CardLayout;
import javax.swing.JLayeredPane;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;

public class ControlPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private boolean reportsVisible = false;
	private Color navBgColor = new Color(255, 255, 255);
	private Color activeColor = new Color(128, 128, 128);
	private Color hoverColor = new Color(220, 230, 240);
	private JPanel tablePanel;
	private JScrollPane scrollPane;
	private JButton logoutBtn;
	private JButton activeButton;
	private JTable table;
	private JTextField textField;
	private JButton[] buttons;

	public ControlPanel(Admin admin) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\justf\\eclipse-workspace\\FINAL_LABORATORY_EXAM_OOP\\src\\photo\\bank.png"));
		setTitle("Monthly Transaction Summary");
		setSize(1200, 831);
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
		logoutBtn.setBounds(0, 700, 269, 85);
		sidebar.add(logoutBtn);

		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(0, 604, 269, 85);
		sidebar.add(infoPanel);
		infoPanel.setLayout(new GridLayout(2, 1, 0, 0));

		// Autofill Labels
		JLabel autofillName = new JLabel(admin.getName());
		autofillName.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(autofillName);

		JLabel autofillID = new JLabel(admin.getAccountNumber());
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
		JPanel manageAccountsPanel = new JPanel();
		manageAccountsPanel.setBounds(268, 0, 916, 881);
		switchPanel.add(manageAccountsPanel);
		manageAccountsPanel.setLayout(new CardLayout(0, 0));

		JPanel manageAccounts1 = new JPanel();
		manageAccountsPanel.add(manageAccounts1, "name_929505867159100");
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
		tablePanel.setBounds(51, 223, 826, 508); // Adjust height as needed
		tablePanel.setLayout(new BorderLayout());

		String[] manageAccountsHeaders = {"ID", "Name", "Email", "Contact Number", "Address", "Account Type", "Generate Report"};
		DefaultTableModel tableModel = new DefaultTableModel(manageAccountsHeaders, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 6; // Make only the button column editable (Generate Report)
			}
		};

		table = new JTable(tableModel);

		table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		table.setRowHeight(30); 
		table.setFillsViewportHeight(true);

		// Custom cell renderer for the button column (7th column, index 6)
		table.getColumnModel().getColumn(6).setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JButton button = (JButton) value;

				// Set padding (top, left, bottom, right)
				button.setMargin(new Insets(0, 10, 0, 10)); // Adjusted padding to make button smaller

				// Set a fixed height for the button to reduce the height
				button.setPreferredSize(new Dimension(button.getPreferredSize().width, 30)); // Fixed height of 30

				// Button design (rounded corners, font, color)
				button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				button.setBackground(new Color(0, 123, 255));  // Blue background
				button.setForeground(Color.WHITE);  // White text
				button.setFocusPainted(false);  // Remove focus border
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Change cursor on hover

				// Hover effect (change background color when hovered)
				button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						button.setBackground(new Color(0, 105, 217));  // Darker blue on hover
					}

					@Override
					public void mouseExited(MouseEvent e) {
						button.setBackground(new Color(0, 123, 255));  // Reset to original blue
					}
				});

				// Center the button in the cell
				JPanel panel = new JPanel(new GridBagLayout());
				panel.add(button);
				return panel; // Return the panel containing the button
			}
		});

		// Custom cell editor for the button column (7th column, index 6)
		table.getColumnModel().getColumn(6).setCellEditor(new TableCellEditor() {
			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
				JButton button = new JButton("Generate");

				// Set padding (top, left, bottom, right)
				button.setMargin(new Insets(0, 10, 0, 10)); // Adjusted padding to make button smaller

				// Set a fixed height for the button to reduce the height
				button.setPreferredSize(new Dimension(button.getPreferredSize().width, 30)); // Fixed height of 30

				// Button design (rounded corners, font, color)
				button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				button.setBackground(new Color(0, 123, 255));  // Blue background
				button.setForeground(Color.WHITE);  // White text
				button.setFocusPainted(false);  // Remove focus border
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Change cursor on hover

				// Hover effect (change background color when hovered)
				button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						button.setBackground(new Color(0, 105, 217));  // Darker blue on hover
					}

					@Override
					public void mouseExited(MouseEvent e) {
						button.setBackground(new Color(0, 123, 255));  // Reset to original blue
					}
				});

				button.addActionListener(e -> {
					// Assuming you have the selected account for the row
					Account selectedAccount = getAccountFromRow(row); // Method to retrieve the account based on the row index

					// Generate the account activity report
					String report = ReportGenerator.generateAccountActivityReport(selectedAccount);

					// Show the report in a message dialog
					JOptionPane.showMessageDialog(table, report, "Account Activity Report", JOptionPane.INFORMATION_MESSAGE);
				});

				// Center the button in the cell
				JPanel panel = new JPanel(new GridBagLayout());
				panel.add(button);

				return panel; // Return the panel containing the button
			}

			@Override
			public Object getCellEditorValue() {
				return null; // Since it's a button, no value needs to be returned
			}

			@Override
			public boolean isCellEditable(EventObject anEvent) {
				return true; // The button column is always editable
			}

			@Override
			public boolean shouldSelectCell(EventObject anEvent) {
				return true; // Allow the button to be selected
			}

			@Override
			public boolean stopCellEditing() {
				return true; // Allow the editor to stop after button is clicked
			}

			@Override
			public void cancelCellEditing() {
				// Optional: Implement any cancel logic here
			}

			@Override
			public void addCellEditorListener(CellEditorListener l) {

			}

			@Override
			public void removeCellEditorListener(CellEditorListener l) {

			}
		});



		// Fetch account data and add to table
		Bank bank = BankLedger.getInstance().getBank();
		List<Account> customers = bank.getAllAccounts();
		tableModel.setRowCount(0); // Clear existing rows

		for (Account account : customers) {
			String accountNumber = account.getAccountNumber();
			String name = account.getOwner().getName();
			String email = account.getOwner().getEmail();
			String contact = account.getOwner().getContactNumber();
			String address = account.getOwner().getAddress();
			String accountType = account.getAccountType();
			JButton generateButton = new JButton("Generate");
			generateButton.setMargin(new Insets(0, 10, 0, 10)); // Add padding to center button

			// Add row to table
			tableModel.addRow(new Object[]{accountNumber, name, email, contact, address, accountType, generateButton});
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

		// Button controls
		JButton btnNewButton = new JButton("➕ Create New");
		btnNewButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		btnNewButton.setBounds(50, 90, 160, 45);
		btnNewButton.setBackground(new Color(40, 167, 69)); // Green
		btnNewButton.setForeground(Color.WHITE);
		manageAccounts1.add(btnNewButton);
		
		btnNewButton.addActionListener(e -> openCreateAccountDialog(tableModel));


		JButton btnEdit = new JButton("✏️ Edit");
		btnEdit.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		btnEdit.setBounds(230, 90, 120, 45);
		btnEdit.setBackground(new Color(255, 193, 7)); // Yellow
		btnEdit.setForeground(Color.BLACK);
		manageAccounts1.add(btnEdit);

		JButton btnDelete = new JButton("🗑️ Delete");
		btnDelete.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		btnDelete.setBounds(370, 90, 130, 45);
		btnDelete.setBackground(new Color(220, 53, 69)); // Red
		btnDelete.setForeground(Color.WHITE);
		manageAccounts1.add(btnDelete);

		// Search Field and Buttons
		textField = new JTextField();
		textField.setBounds(520, 90, 220, 45);
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textField.setToolTipText("Search by name or ID");
		manageAccounts1.add(textField);

		JButton btnSearch = new JButton("🔍 Search");
		btnSearch.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		btnSearch.setBounds(750, 90, 115, 45);
		btnSearch.setBackground(new Color(0, 123, 255)); // Blue
		btnSearch.setForeground(Color.WHITE);
		manageAccounts1.add(btnSearch);

		// Refresh Button
		JButton btnRefresh = new JButton("🔄 Refresh");
		btnRefresh.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		btnRefresh.setBounds(50, 150, 120, 35);
		btnRefresh.setBackground(new Color(108, 117, 125)); // Gray
		btnRefresh.setForeground(Color.WHITE);
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
		tablePanel.setBounds(50, 100, 816, 479); // Adjust height as needed
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
		tableModel1.setRowCount(0); // Clear existing rows
		for (Account account : customers) {
			java.util.List<Transaction> transactions = account.getHistory().getHistoryList();

			for (Transaction transaction : transactions) {
				String date = transaction.getDate().toString();
				String iD = account.getAccountNumber();
				String type = transaction.getAction();
				String amount = String.format("₱%.2f", transaction.getAmount());
				String recipient = account.getOwner().getName();

				// Use appropriate balance based on account type
				double balance;
				if (account instanceof LoanAccount) {
					balance = ((LoanAccount) account).getLoanBalance();
				} else {
					balance = account.getBalance();
				}

				// Handle transfers
				if (type.startsWith("Transfer to ")) {
					recipient = type.substring(12); // Extract recipient from action text
					type = "Transfer Sent";
				} else if (type.startsWith("Transfer from ")) {
					recipient = type.substring(14); // Extract recipient from action text
					type = "Transfer Received";
				}

				// Add row to table with properly selected balance
				tableModel1.addRow(new Object[]{
						date,
						recipient,
						iD,
						amount,
						type,
						String.format("₱%.2f", balance)
				});
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
		JLabel dateRangeLabel = new JLabel("📆 Date Range:");
		dateRangeLabel.setBounds(50, 628, 120, 30);
		dateRangeLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		montlyTransactionSummaryPanel1.add(dateRangeLabel);

		String[] options = {"Select Option", "Last 7 Days", "Last 30 Days", "Last 60 Days", "Custom"};
		JComboBox<String> dateRangeDropdown = new JComboBox<>(options);
		dateRangeDropdown.setBounds(180, 628, 180, 30);
		dateRangeDropdown.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		montlyTransactionSummaryPanel1.add(dateRangeDropdown);

		// JPanel for From Date
		JPanel rowFromDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rowFromDate.setBackground(new Color(240, 240, 240));
		JLabel fromLabel = new JLabel("From:");
		fromLabel.setPreferredSize(new Dimension(130, 25));
		JSpinner fromDateSpinner = new JSpinner(new SpinnerDateModel());
		fromDateSpinner.setEditor(new JSpinner.DateEditor(fromDateSpinner, "MM/dd/yyyy"));
		rowFromDate.add(fromLabel);
		rowFromDate.add(fromDateSpinner);
		montlyTransactionSummaryPanel1.add(rowFromDate);
		rowFromDate.setBounds(50, 668, 350, 35);
		fromLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		montlyTransactionSummaryPanel1.add(rowFromDate);

		// JPanel for To Date
		JPanel rowToDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rowToDate.setBackground(new Color(240, 240, 240));
		JLabel toLabel = new JLabel("To:");
		toLabel.setPreferredSize(new Dimension(130, 25));
		JSpinner toDateSpinner = new JSpinner(new SpinnerDateModel());
		toDateSpinner.setEditor(new JSpinner.DateEditor(toDateSpinner, "MM/dd/yyyy"));
		rowToDate.add(toLabel);
		rowToDate.add(toDateSpinner);
		montlyTransactionSummaryPanel1.add(rowToDate);
		rowToDate.setBounds(50, 708, 350, 35);
		toLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		montlyTransactionSummaryPanel1.add(rowToDate);


		// Account selection dropdown
		JLabel accountLabel = new JLabel();
		accountLabel.setText("👤 Select Account:");
		accountLabel.setBounds(450, 628, 140, 30);
		accountLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		montlyTransactionSummaryPanel1.add(accountLabel);

		// Assuming 'customers' is a List<Account> containing all the customer accounts
		JComboBox<String> accountDropdown = new JComboBox<>();
		for (Account account : customers) {
			accountDropdown.addItem(account.getOwner().getName()); // You can add more identifiers like account number if needed
		}
		accountDropdown.setBounds(590, 628, 220, 30);
		accountDropdown.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		montlyTransactionSummaryPanel1.add(accountDropdown);

		// Generate Report Button
		JButton submitBtn = new JButton();
		submitBtn.setText("📄 Generate Report");
		submitBtn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		submitBtn.setBackground(new Color(40, 167, 69)); // Green
		submitBtn.setForeground(Color.WHITE);
		submitBtn.setBounds(590, 708, 220, 40);
		montlyTransactionSummaryPanel1.add(submitBtn);

		// Button Action Listener
		submitBtn.addActionListener(e -> {
			String selectedOption = (String) dateRangeDropdown.getSelectedItem();
			// Get the selected date range from JSpinner
			Date fromDateValue = (Date) fromDateSpinner.getValue();
			Date toDateValue = (Date) toDateSpinner.getValue();
			String selectedAccountName = (String) accountDropdown.getSelectedItem();

			if (selectedOption.equals("Select Option")) {
				JOptionPane.showMessageDialog(montlyTransactionSummaryPanel1, "Please select a date range option.");
				return;
			}

			try {
				LocalDate startDate = null;
				LocalDate endDate = null;

				if (selectedOption.equals("Last 7 Days")) {
					endDate = LocalDate.now();
					startDate = endDate.minusDays(7);
				} else if (selectedOption.equals("Last 30 Days")) {
					endDate = LocalDate.now();
					startDate = endDate.minusDays(30);
				} else if (selectedOption.equals("Last 60 Days")) {
					endDate = LocalDate.now();
					startDate = endDate.minusDays(60);
				} else if (selectedOption.equals("Custom")) {
					// Convert JSpinner Date to LocalDate for custom date selection
					startDate = fromDateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					endDate = toDateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				}

				Account selectedAccount = null;
				// If an account is selected from the dropdown, filter the report by that account
				if (selectedAccountName != null && !selectedAccountName.isEmpty()) {
					// Find the selected account based on the account name
					for (Account account : customers) {
						if (account.getOwner().getName().equals(selectedAccountName)) {
							selectedAccount = account;
							break;
						}
					}
				}

				if (selectedAccount != null) {
					// Generate report for the specific account
					String report = ReportGenerator.generateMonthlyTransactionSummary(selectedAccount, startDate.getMonthValue(), startDate.getYear());
					JOptionPane.showMessageDialog(montlyTransactionSummaryPanel1, report, "Monthly Transaction Summary", JOptionPane.INFORMATION_MESSAGE);
				} else {
					// Handle case when no specific account is selected (show a filtered report for all accounts)
					StringBuilder report = new StringBuilder("Summary for all accounts:\n");
					for (Account account : customers) {
						report.append(ReportGenerator.generateMonthlyTransactionSummary(account, startDate.getMonthValue(), startDate.getYear())).append("\n\n");
					}
					JOptionPane.showMessageDialog(montlyTransactionSummaryPanel1, report.toString(), "All Account Transaction Summary", JOptionPane.INFORMATION_MESSAGE);
				}

			} catch (DateTimeParseException ex) {
				JOptionPane.showMessageDialog(montlyTransactionSummaryPanel1, "Invalid date format. Please use MM/dd/yyyy.");


			}
		});




		logoutBtn.addActionListener(event -> {
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				dispose();
				Login login = new Login();
				login.setVisible(true);
				login.setLocationRelativeTo(null);
			}
		});



		

		JPanel dailyTransactionReportPanel = new JPanel();
		switchPanel.add(dailyTransactionReportPanel, "name_53657788313500");
		dailyTransactionReportPanel.setLayout(new CardLayout(0, 0));

		JPanel dailyTransactionReport1 = new JPanel();
		dailyTransactionReportPanel.add(dailyTransactionReport1, "name_53733243658400");
		dailyTransactionReport1.setLayout(null);

		// --- HEADER ---
		JPanel dailyTransactionheaderpanel = new JPanel();
		dailyTransactionheaderpanel.setBounds(0, 0, 916, 72);
		dailyTransactionheaderpanel.setBackground(new Color(52, 58, 64));
		dailyTransactionheaderpanel.setPreferredSize(new Dimension(0, 80));
		dailyTransactionheaderpanel.setLayout(new GridLayout(0, 2, 0, 0));
		dailyTransactionheaderpanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

		JLabel lbldailyTransactionheader = new JLabel("🏦 Northland Bank");
		lbldailyTransactionheader.setHorizontalAlignment(SwingConstants.CENTER);
		lbldailyTransactionheader.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
		lbldailyTransactionheader.setForeground(Color.WHITE);
		dailyTransactionheaderpanel.add(lbldailyTransactionheader);
		dailyTransactionReport1.add(dailyTransactionheaderpanel, BorderLayout.NORTH);

		JLabel dailyTransactionbankIcon = new JLabel(resizedIcon);
		dailyTransactionbankIcon.setHorizontalAlignment(SwingConstants.RIGHT);
		dailyTransactionheaderpanel.add(dailyTransactionbankIcon);


		// Sort By Label and Dropdown
		JLabel lblSortBy = new JLabel("Sort By:");
		lblSortBy.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSortBy.setBounds(50, 90, 80, 25);
		dailyTransactionReport1.add(lblSortBy);

		JComboBox<String> sortDropdown = new JComboBox<>(new String[]{"Select Option", "Date", "Amount", "Account Name"});
		sortDropdown.setBounds(130, 90, 150, 25);
		dailyTransactionReport1.add(sortDropdown);
		
		// Search Field and Search Button
		JTextField searchField = new JTextField();
		searchField.setBounds(520, 90, 220, 45);
		searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		searchField.setToolTipText("Search by name or ID");
		dailyTransactionReport1.add(searchField);

		JButton btndailySearch = new JButton("🔍 Search");
		btndailySearch.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		btndailySearch.setBounds(750, 90, 115, 45);
		btndailySearch.setBackground(new Color(0, 123, 255)); // Blue
		btndailySearch.setForeground(Color.WHITE);
		dailyTransactionReport1.add(btndailySearch);



		// --- TABLE DATA ---
		String[] columns = {"Date", "Account ID", "Account Name", "Amount", "Status"};
		Object[][] data = {
		    {"01-24-2023", "8956-8756-987", "Sheena Halili", "₱10,000", "Pending"},
		    {"04-13-2023", "7885-1344-765", "Kitryn Bane", "₱15,000", "Completed"},
		    {"05-16-2023", "7482-3434-322", "Kyle Polly", "₱9,000", "In Progress"},
		    {"05-17-2023", "9823-2234-564", "Monica Fajardo", "₱15,000", "Pending"},
		    {"08-16-2023", "7344-2344-988", "John Dee", "₱6,000", "Completed"},
		    {"08-22-2023", "6284-4333-232", "Lia Monkey", "₱15,000", "Completed"},
		    {"09-13-2023", "5248-2233-231", "Grace Montenegro", "₱123,000", "Pending"},
		    {"10-14-2023", "2123-2133-521", "Raven Montero", "₱8,000", "Completed"},
		    {"11-03-2023", "3482-2432-122", "Joshua Malitic", "₱25,000", "In Progress"},
		    {"11-10-2023", "4532-6783-911", "Johnro Malitic", "₱18,000", "Pending"},
		    {"12-01-2023", "6783-2344-231", "Mikha Aragon", "₱12,500", "Completed"},
		    {"12-14-2023", "8932-3485-434", "Colet Rivera", "₱7,000", "Completed"},
		    {"01-07-2024", "1345-2389-741", "Maloi Santos", "₱5,500", "Pending"},
		    {"01-15-2024", "3490-2394-123", "Genesis Abanales", "₱20,000", "Completed"},
		    {"02-05-2024", "4523-2344-658", "Maria Santos", "₱10,500", "Pending"},
		    {"02-10-2024", "9823-4344-223", "Liam Lopez", "₱13,000", "Completed"},
		    {"03-25-2024", "4532-6789-239", "Nico Garcia", "₱20,000", "In Progress"},
		    {"04-01-2024", "2341-2374-894", "Elena Cruz", "₱18,000", "Pending"}
		};

		DefaultTableModel tableModel11 = new DefaultTableModel(data, columns) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};

		JTable table = new JTable(tableModel11);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		table.setRowHeight(30);
		table.setFillsViewportHeight(true);

		// Table row striping and highlight
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

		// Styled header
		JTableHeader header11 = table.getTableHeader();
		header11.setFont(new Font("Segoe UI", Font.BOLD, 14));
		header11.setBackground(new Color(220, 220, 220));
		header11.setForeground(Color.BLACK);
		header11.setReorderingAllowed(false);
		header11.setResizingAllowed(false);

		// ScrollPane for table
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(24, 170, 847, 556);
		dailyTransactionReport1.add(scrollPane);

        
      //-----MANAGE TRANSACTIONS PANEL-----
        JPanel manageTransactionPanel = new JPanel();
        manageTransactionPanel.setBounds(268, 0, 916, 881);
        switchPanel.add(manageTransactionPanel);
        manageTransactionPanel.setLayout(new CardLayout(0, 0));
        manageTransactionPanel.setVisible(false);

        JPanel manageTransactionPanel1 = new JPanel();
        manageTransactionPanel.add(manageTransactionPanel1, "name_54659468661100");
        manageTransactionPanel1.setLayout(null);

        // --- HEADER ---
        JPanel manageTransactionheaderpanel1 = new JPanel();
        manageTransactionheaderpanel1.setBounds(0, 0, 916, 72);
        manageTransactionheaderpanel1.setBackground(new Color(52, 58, 64));
        manageTransactionheaderpanel1.setPreferredSize(new Dimension(0, 80));
        manageTransactionheaderpanel1.setLayout(new GridLayout(0, 2, 0, 0));
        manageTransactionheaderpanel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JLabel lblmanageTransactionheader1 = new JLabel("🏦 Northland Bank");
        lblmanageTransactionheader1.setHorizontalAlignment(SwingConstants.CENTER);
        lblmanageTransactionheader1.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        lblmanageTransactionheader1.setForeground(Color.WHITE);
        manageTransactionheaderpanel1.add(lblmanageTransactionheader1);
        manageTransactionPanel1.add(manageTransactionheaderpanel1, BorderLayout.NORTH);

        JLabel manageTransactionbankIcon1 = new JLabel(resizedIcon);
        manageTransactionbankIcon1.setHorizontalAlignment(SwingConstants.RIGHT);
        manageTransactionheaderpanel1.add(manageTransactionbankIcon1);
        

        // Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(51, 161, 809, 455);
        tablePanel.setLayout(new BorderLayout());

        String[] headers = {"Date", "ID", "Account Name", "Amount", "Status"};
        DefaultTableModel tableModel111 = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable manageTable = new JTable(tableModel111);
        manageTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        manageTable.setRowHeight(30);
        manageTable.setFillsViewportHeight(true);
        manageTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        // Add the scroll pane
        JTableHeader header111 = manageTable.getTableHeader();
        header111.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header111.setBackground(new Color(220, 220, 220));
        header111.setForeground(Color.BLACK);
        header111.setReorderingAllowed(false);
        header111.setResizingAllowed(false);

        JScrollPane scrollPane1 = new JScrollPane(manageTable);
        tablePanel.add(scrollPane1, BorderLayout.CENTER);
        manageTransactionPanel1.add(tablePanel);

     // --- Action Buttons (Create, Edit, Delete, Search) ---
        JButton btnCreateTrans = new JButton("➕ Create New");
        btnCreateTrans.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        btnCreateTrans.setBounds(50, 90, 160, 45);
        btnCreateTrans.setBackground(new Color(40, 167, 69)); // Green
        btnCreateTrans.setForeground(Color.WHITE);
        manageTransactionPanel1.add(btnCreateTrans);

        JButton btnEditTrans = new JButton("✏️ Edit");
        btnEditTrans.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        btnEditTrans.setBounds(230, 90, 120, 45);
        btnEditTrans.setBackground(new Color(255, 193, 7)); // Yellow
        btnEditTrans.setForeground(Color.BLACK);
        manageTransactionPanel1.add(btnEditTrans);

        JButton btnDeleteTrans = new JButton("🗑️ Delete");
        btnDeleteTrans.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        btnDeleteTrans.setBounds(370, 90, 130, 45);
        btnDeleteTrans.setBackground(new Color(220, 53, 69)); // Red
        btnDeleteTrans.setForeground(Color.WHITE);
        manageTransactionPanel1.add(btnDeleteTrans);

        JTextField searchTransactionField = new JTextField();
        searchTransactionField.setBounds(520, 90, 220, 45);
        searchTransactionField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchTransactionField.setToolTipText("Search by name, ID, or type");
        manageTransactionPanel1.add(searchTransactionField);

        JButton btnSearchTrans = new JButton("🔍 Search");
        btnSearchTrans.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        btnSearchTrans.setBounds(750, 90, 115, 45);
        btnSearchTrans.setBackground(new Color(0, 123, 255)); // Blue
        btnSearchTrans.setForeground(Color.WHITE);
        manageTransactionPanel1.add(btnSearchTrans);

        // --- Redesigned Sort Section and Type Filters ---
        JLabel lblSortBy1 = new JLabel("Sort By:");
        lblSortBy1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSortBy1.setBounds(51, 645, 100, 25);
        manageTransactionPanel1.add(lblSortBy1);

        JComboBox<String> sortDropdown1 = new JComboBox<>(new String[]{"Select Option", "Date", "Amount", "Name"});
        sortDropdown1.setBounds(121, 645, 160, 30);
        sortDropdown1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        manageTransactionPanel1.add(sortDropdown1);

        JButton depositBtn1 = new JButton("Deposits");
        depositBtn1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        depositBtn1.setForeground(Color.WHITE);
        depositBtn1.setBackground(new Color(0, 123, 255));
        depositBtn1.setBounds(311, 645, 110, 30);
        manageTransactionPanel1.add(depositBtn1);

        JButton withdrawalBtn1 = new JButton("Withdrawals");
        withdrawalBtn1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        withdrawalBtn1.setForeground(Color.WHITE);
        withdrawalBtn1.setBackground(new Color(0, 123, 255));
        withdrawalBtn1.setBounds(431, 645, 120, 30);
        manageTransactionPanel1.add(withdrawalBtn1);

        JButton transferBtn1 = new JButton("Transfers");
        transferBtn1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        transferBtn1.setForeground(Color.WHITE);
        transferBtn1.setBackground(new Color(0, 123, 255));
        transferBtn1.setBounds(561, 645, 110, 30);
        manageTransactionPanel1.add(transferBtn1);

        JButton generateReportBtn = new JButton("📄 Generate Report");
        generateReportBtn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        generateReportBtn.setBackground(new Color(100, 180, 255));
        generateReportBtn.setBounds(691, 645, 170, 30);
        manageTransactionPanel1.add(generateReportBtn);


     		
     		
        
      //Switching panel
      		btnmanageAccount.addMouseListener(new MouseAdapter() {

      			public void mouseClicked(MouseEvent e) {
      				montlyTransactionSummaryPanel.setVisible(false);
      				dailyTransactionReportPanel.setVisible(false);
      				manageTransactionPanel.setVisible(false);
      				manageAccountsPanel.setVisible(true);
      				setActiveButton(btnmanageAccount);
      				reportsVisible = false;
      				reportsPanel.setVisible(reportsVisible);
      				reportsToggle.setText(reportsVisible ? "Reports ▲" : "Reports ▼");
      			}
      		});

      		monthlyBtn.addMouseListener(new MouseAdapter() {

      			public void mouseClicked(MouseEvent e) {
      				montlyTransactionSummaryPanel.setVisible(true);
      				dailyTransactionReportPanel.setVisible(false);
      				manageTransactionPanel.setVisible(false);
      				manageAccountsPanel.setVisible(false);
      				reportsVisible = false;
      				reportsPanel.setVisible(reportsVisible);
      				reportsToggle.setText(reportsVisible ? "Reports ▲" : "Reports ▼");
      			}
      		});
      		
      		dailyBtn.addMouseListener(new MouseAdapter() {

      			public void mouseClicked(MouseEvent e) {
      				montlyTransactionSummaryPanel.setVisible(false);
      				manageTransactionPanel.setVisible(false);
      				dailyTransactionReportPanel.setVisible(true);
      				manageAccountsPanel.setVisible(false);
      				reportsVisible = false;
      				reportsPanel.setVisible(reportsVisible);
      				reportsToggle.setText(reportsVisible ? "Reports ▲" : "Reports ▼");
      			}
      		});
      		
      		btnmanageTransaction.addMouseListener(new MouseAdapter() {

      			public void mouseClicked(MouseEvent e) {
      				montlyTransactionSummaryPanel.setVisible(false);
      				manageTransactionPanel.setVisible(true);
      				dailyTransactionReportPanel.setVisible(false);
      				manageAccountsPanel.setVisible(false);
      				reportsVisible = false;
      				reportsPanel.setVisible(reportsVisible);
      				reportsToggle.setText(reportsVisible ? "Reports ▲" : "Reports ▼");
      			}
      		});

	}

	private void stylePrimaryButton(JButton btn) {
        btn.setBackground(new Color(0, 123, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
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
			JButton generateButton = new JButton("Generate");
			generateButton.setMargin(new Insets(10, 20, 10, 20));
			// Add row to table
			tableModel.addRow(new Object[]{accountNumber, name, email, contact, address, accountType, generateButton});
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

	private Account getAccountFromRow(int row) {
		// Assuming you have access to the list of accounts, and that you know the mapping
		// between rows in the JTable and the accounts in your data list
		Bank bank = BankLedger.getInstance().getBank();
		List<Account> customers = bank.getAllAccounts();
		return customers.get(row); // Return the account based on the row index
	}
	
	private void openCreateAccountDialog(DefaultTableModel tableModel) {
	    JTextField nameField = new JTextField();
	    JSpinner dobSpinner = new JSpinner(new SpinnerDateModel());
	    dobSpinner.setEditor(new JSpinner.DateEditor(dobSpinner, "yyyy-MM-dd"));
	    JTextField emailField = new JTextField();
	    JTextField contactField = new JTextField();
	    JTextField addressField = new JTextField();
	    JPasswordField passwordField = new JPasswordField();
	    JPasswordField confirmPasswordField = new JPasswordField();
	    String[] accountTypes = {"Checking", "Savings", "Loan"};
	    JComboBox<String> accountTypeBox = new JComboBox<>(accountTypes);
	    JLabel lblInitial = new JLabel("Initial Deposit:");
	    JTextField initialAmountField = new JTextField();

	    // Dynamic label update for initial amount
	    accountTypeBox.addActionListener(e -> {
	        String selected = (String) accountTypeBox.getSelectedItem();
	        if ("Loan".equalsIgnoreCase(selected)) {
	            lblInitial.setText("Borrow Amount:");
	        } else {
	            lblInitial.setText("Initial Deposit:");
	        }
	    });

	    JPanel panel = new JPanel(new GridLayout(0, 1));
	    panel.add(new JLabel("Name:"));
	    panel.add(nameField);
	    panel.add(new JLabel("Date of Birth:"));
	    panel.add(dobSpinner);
	    panel.add(new JLabel("Email:"));
	    panel.add(emailField);
	    panel.add(new JLabel("Contact Number:"));
	    panel.add(contactField);
	    panel.add(new JLabel("Address:"));
	    panel.add(addressField);
	    panel.add(new JLabel("Password:"));
	    panel.add(passwordField);
	    panel.add(new JLabel("Confirm Password:"));
	    panel.add(confirmPasswordField);
	    panel.add(new JLabel("Account Type:"));
	    panel.add(accountTypeBox);
	    panel.add(lblInitial);
	    panel.add(initialAmountField);

	    int result = JOptionPane.showConfirmDialog(null, panel, "Create New Account",
	            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

	    if (result == JOptionPane.OK_OPTION) {
	        String name = nameField.getText().trim();
	        Date dobDate = (Date) dobSpinner.getValue();
	        String dob = new SimpleDateFormat("yyyy-MM-dd").format(dobDate);
	        String email = emailField.getText().trim();
	        String contact = contactField.getText().trim();
	        String address = addressField.getText().trim();
	        String password = new String(passwordField.getPassword()).trim();
	        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
	        String type = (String) accountTypeBox.getSelectedItem();
	        String initialAmountStr = initialAmountField.getText().trim();

	        // --- VALIDATION ---
	        if (name.isEmpty() || dob.isEmpty() || email.isEmpty() || contact.isEmpty() ||
	                address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || initialAmountStr.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "All fields must be filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        if (!email.contains("@") || !email.contains(".com")) {
	            JOptionPane.showMessageDialog(null, "Invalid email format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        if (!contact.matches("\\d{10,11}")) {
	            JOptionPane.showMessageDialog(null, "Invalid contact number (must be 10 or 11 digits).", "Validation Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        if (!password.equals(confirmPassword)) {
	            JOptionPane.showMessageDialog(null, "Passwords do not match.", "Validation Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        double initialAmount;
	        try {
	            initialAmount = Double.parseDouble(initialAmountStr);
	            if (initialAmount < 0) throw new NumberFormatException();
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(null, "Invalid amount. Enter a valid positive number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        Bank bank = BankLedger.getInstance().getBank();
	        Customer existingCustomer = bank.findCustomerByEmail(email);

	        if (existingCustomer != null) {
	            int choice = JOptionPane.showConfirmDialog(null,
	                    "An account already exists with this email. Open a new account under the same user?",
	                    "Duplicate Email", JOptionPane.YES_NO_OPTION);

	            if (choice == JOptionPane.YES_OPTION) {
	                boolean hasSameType = existingCustomer.getAccount().stream()
	                        .anyMatch(acc -> acc.getAccountType().equalsIgnoreCase(type));

	                if (hasSameType) {
	                    JOptionPane.showMessageDialog(null,
	                            "This customer already has a " + type + " account.",
	                            "Duplicate Account Type", JOptionPane.WARNING_MESSAGE);
	                    return;
	                }

	                Account newAccount = AccountFactory.createAccount(type, existingCustomer);
	                
	             // Handle loan account
	                if ("Loan".equalsIgnoreCase(type)) {
	                    ((LoanAccount) newAccount).borrow(initialAmount); // Handle loan-specific deposit (loan disbursement)
	                    Notification loanNotification = new Notification(
	                            "Loan Granted", 
	                            "You borrowed PHP " + initialAmount + " as your starting loan.", 
	                            LocalDate.now().toString()
	                    );
	                    newAccount.addNotification(loanNotification);
	                } else {
	                    newAccount.deposit(initialAmount);  // Handle deposit for savings/checking
	                    Notification initialDepositNotification = new Notification(
	                            "Initial Deposit Completed", 
	                            "You deposited PHP " + initialAmount + " as initial deposit.", 
	                            LocalDate.now().toString()
	                    );
	                    newAccount.addNotification(initialDepositNotification);
	                }
	                
	                bank.addAccount(newAccount);
	                existingCustomer.addAccount(newAccount);
	                
	                LowBalanceNotifier lowBalanceNotifier = new LowBalanceNotifier();
	    	        newAccount.addObserver(lowBalanceNotifier);

	                tableModel.addRow(new Object[]{
	                        newAccount.getAccountNumber(),
	                        existingCustomer.getName(),
	                        email,
	                        contact,
	                        address,
	                        type,
	                        new JButton("Generate")
	                });

	                JOptionPane.showMessageDialog(null, "New account added to existing customer.", "Success", JOptionPane.INFORMATION_MESSAGE);
	                return;
	            } else {
	                return;
	            }
	        }

	        // New customer
	        Customer newCustomer = new Customer(name, dob, contact, email, address, password);
	        Account newAccount;
			
	        try {
	            newAccount = AccountFactory.createAccount(type, newCustomer);
	            // Handle loan account
	            if ("Loan".equalsIgnoreCase(type)) {
	                ((LoanAccount) newAccount).borrow(initialAmount); // Handle loan-specific deposit (loan disbursement)
	                Notification loanNotification = new Notification(
	                        "Loan Granted", 
	                        "You borrowed PHP " + initialAmount + " as your starting loan.", 
	                        LocalDate.now().toString()
	                );
	                newAccount.addNotification(loanNotification);
	            } else {
	                newAccount.deposit(initialAmount);  // Handle deposit for savings/checking
	                Notification initialDepositNotification = new Notification(
	                        "Initial Deposit Completed", 
	                        "You deposited PHP " + initialAmount + " as initial deposit.", 
	                        LocalDate.now().toString()
	                );
	                newAccount.addNotification(initialDepositNotification);
	            }
	        } catch (IllegalArgumentException ex) {
	            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        BankLedger.getInstance().addAccount(newAccount);
	        newCustomer.addAccount(newAccount);
	        
	        LowBalanceNotifier lowBalanceNotifier = new LowBalanceNotifier();
	        newAccount.addObserver(lowBalanceNotifier);

	        tableModel.addRow(new Object[]{
	                newAccount.getAccountNumber(),
	                name,
	                email,
	                contact,
	                address,
	                type,
	                new JButton("Generate")
	        });

	        JOptionPane.showMessageDialog(null, "New account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	}





}
