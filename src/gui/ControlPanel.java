package gui;
import java.awt.event.*;
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import system.Account;
import system.Admin;
import system.Bank;
import system.BankLedger;
import system.Customer;
import system.LoanAccount;
import system.ReportGenerator;
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
				// TODO Auto-generated method stub
				
			}

			@Override
			public void removeCellEditorListener(CellEditorListener l) {
				// TODO Auto-generated method stub
				
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
		JLabel dateRangeLabel = new JLabel("Date Range:");
		dateRangeLabel.setBounds(81, 694, 100, 25);
		montlyTransactionSummaryPanel1.add(dateRangeLabel);

		String[] options = {"Select Option", "Last 7 Days", "Last 30 Days", "Last 60 Days", "Custom"};
		JComboBox<String> dateRangeDropdown = new JComboBox<>(options);
		dateRangeDropdown.setBounds(181, 694, 150, 25);
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
		rowFromDate.setBounds(81, 734, 350, 40);  // Adjust positioning

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
		rowToDate.setBounds(81, 774, 350, 40);  // Adjust positioning


		// Account selection dropdown
		JLabel accountLabel = new JLabel("Select Account:");
		accountLabel.setBounds(81, 814, 100, 25);
		montlyTransactionSummaryPanel1.add(accountLabel);

		// Assuming 'customers' is a List<Account> containing all the customer accounts
		JComboBox<String> accountDropdown = new JComboBox<>();
		for (Account account : customers) {
		    accountDropdown.addItem(account.getOwner().getName()); // You can add more identifiers like account number if needed
		}
		accountDropdown.setBounds(181, 814, 150, 25);
		montlyTransactionSummaryPanel1.add(accountDropdown);

		// Generate Report Button
		JButton submitBtn = new JButton("Generate Report");
		submitBtn.setBackground(new Color(100, 180, 255));
		submitBtn.setBounds(441, 784, 150, 30); // Adjusted width for clarity
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
}
