package gui;

import javax.swing.*;

import system.Account;
import system.Bank;
import system.BankLedger;
import system.CheckingAccount;
import system.LoanAccount;
import system.LowBalanceNotifier;
import system.Transaction;

import javax.swing.DefaultListModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Dashboard extends JFrame {
	private static final long serialVersionUID = 1L;
	private Color navBgColor = new Color(255, 255, 255);
	private Color activeColor = new Color(0, 128, 255);
	private Color hoverColor = new Color(220, 230, 240);
	private Color infoBgColor = new Color(240, 240, 240);
	private BankLedger bankLedger;
	private Bank bank;
	private JButton btnLogout;
	private JButton activeButton;
	private JButton[] buttons;
	private DefaultListModel<String> recentTransactionListModel;

	public Dashboard(Account account) {
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
		JButton btnDashboard = new JButton("📊 Dashboard");
		btnDashboard.setHorizontalAlignment(SwingConstants.LEFT);
		btnDashboard.setBackground(navBgColor);
		btnDashboard.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnDashboard.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDashboard.setFocusPainted(false);
		btnDashboard.setBorderPainted(false);
		btnDashboard.setContentAreaFilled(true);
		btnDashboard.setOpaque(true);
		btnDashboard.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 10));
		
		JButton btnTransaction = new JButton("💸 Transaction");
		btnTransaction.setHorizontalAlignment(SwingConstants.LEFT);
		btnTransaction.setBackground(navBgColor);
		btnTransaction.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnTransaction.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnTransaction.setFocusPainted(false);
		btnTransaction.setBorderPainted(false);
		btnTransaction.setContentAreaFilled(true);
		btnTransaction.setOpaque(true);
		btnTransaction.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 10));
		
		JButton btnReport = new JButton("📄 Transaction History");
		btnReport.setHorizontalAlignment(SwingConstants.LEFT);
		btnReport.setBackground(navBgColor);
		btnReport.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnReport.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnReport.setFocusPainted(false);
		btnReport.setBorderPainted(false);
		btnReport.setContentAreaFilled(true);
		btnReport.setOpaque(true);
		btnReport.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 10));
		
		JButton btnAccount = new JButton("👤 Account");
		btnAccount.setHorizontalAlignment(SwingConstants.LEFT);
		btnAccount.setBackground(navBgColor);
		btnAccount.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAccount.setFocusPainted(false);
		btnAccount.setBorderPainted(false);
		btnAccount.setContentAreaFilled(true);
		btnAccount.setOpaque(true);
		btnAccount.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 10));
		
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
		buttonPanel.setBackground(navBgColor);
		buttonPanel.setLayout(new GridLayout(0, 1, 0, 10)); // spacing
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		navPanel.add(buttonPanel);

		buttonPanel.add(btnDashboard);
		buttonPanel.add(btnTransaction);
		buttonPanel.add(btnReport);
		buttonPanel.add(btnAccount);

		// SPACER
		buttonPanel.add(Box.createVerticalGlue());

		// INFO PANEL (Bottom user info)
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(infoBgColor);
		infoPanel.setLayout(new GridLayout(2, 1));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

		JLabel lblUsername = new JLabel("👤 " + account.getOwner().getName());
		lblUsername.setFont(new Font("SansSerif", Font.PLAIN, 14));
		JLabel lblId = new JLabel("🆔 ID: " + account.getAccountNumber());
		lblId.setFont(new Font("SansSerif", Font.PLAIN, 14));

		infoPanel.add(lblUsername);
		infoPanel.add(lblId);
		buttonPanel.add(infoPanel);


		// LOGOUT BUTTON (separate)
		navPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonPanel.add(btnLogout);

		// Mouse Hover Effect for All Buttons
		buttons = new JButton[] {btnDashboard, btnTransaction, btnReport, btnAccount, btnLogout};
		activeButton = btnDashboard;

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
		setActiveButton(btnDashboard);

		// Example: If you want to change active panel when clicking other buttons
		btnDashboard.addActionListener(e -> setActiveButton(btnDashboard));
		btnTransaction.addActionListener(e -> setActiveButton(btnTransaction));
		btnReport.addActionListener(e -> setActiveButton(btnReport));
		btnAccount.addActionListener(e -> setActiveButton(btnAccount));

		// LOGOUT BUTTON BEHAVIOR
		btnLogout.addActionListener(e -> {
			dispose();
			Login login = new Login();
			login.setVisible(true);
			login.setLocationRelativeTo(null);
		});


		JPanel switchpanel = new JPanel();
		getContentPane().add(switchpanel, BorderLayout.CENTER);
		switchpanel.setLayout(new CardLayout(0, 0));

		JPanel dashboardpanel1 = new JPanel();
		dashboardpanel1.setBackground(new Color(192, 192, 192));
		switchpanel.add(dashboardpanel1, "Dashboard");
		dashboardpanel1.setLayout(new CardLayout(0, 0));

		JPanel dashboardpanel2 = new JPanel();
		dashboardpanel2.setBackground(new Color(240, 240, 240)); // Softer light gray
		dashboardpanel1.add(dashboardpanel2, "Dashboard");
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
		recentTransactionpanel.setLayout(new BorderLayout(10, 10));
		recentTransactionpanel.setPreferredSize(new Dimension(250, 0));

		// Add this panel to the dashboard
		dashboardpanel2.add(recentTransactionpanel, BorderLayout.EAST);

		// Create and add the "Recent Transactions" label
		JLabel lblRecentTransactions = new JLabel("Recent Transactions");
		lblRecentTransactions.setFont(new Font("Roboto", Font.BOLD, 18));
		lblRecentTransactions.setForeground(new Color(33, 37, 41));
		lblRecentTransactions.setHorizontalAlignment(SwingConstants.CENTER);
		recentTransactionpanel.add(lblRecentTransactions, BorderLayout.NORTH);

		// Create the DefaultListModel and JList for displaying the transactions
		recentTransactionListModel = new DefaultListModel<>();
		JList<String> recentTransactionList = new JList<>(recentTransactionListModel);
		recentTransactionList.setFont(new Font("Roboto", Font.PLAIN, 14));
		recentTransactionList.setForeground(new Color(85, 85, 85));

		// Make the JList scrollable
		JScrollPane transactionScrollPane = new JScrollPane(recentTransactionList);
		transactionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Add the scrollable list to the recent transaction panel
		recentTransactionpanel.add(transactionScrollPane, BorderLayout.CENTER);

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

		// --- NEW: Loan Balance ---
		JLabel lblLoanBalance = new JLabel("Loan Balance");
		lblLoanBalance.setFont(new Font("Poppins", Font.BOLD, 20));
		lblLoanBalance.setForeground(new Color(33, 37, 41));
		lblLoanBalance.setAlignmentX(Component.CENTER_ALIGNMENT);

		
		JLabel lblLoanBalanceAmount;
		if (account instanceof LoanAccount) {
			lblLoanBalanceAmount = new JLabel(String.format("₱%,.2f", ((LoanAccount) account).getLoanBalance()));
		} else {
			lblLoanBalanceAmount = new JLabel("₱0.00"); 
		}
		lblLoanBalanceAmount.setFont(new Font("Poppins", Font.BOLD, 28));
		lblLoanBalanceAmount.setForeground(new Color(220, 53, 69)); 
		lblLoanBalanceAmount.setAlignmentX(Component.CENTER_ALIGNMENT);

		balanceheaderpanel.add(lblbalance);
		balanceheaderpanel.add(Box.createRigidArea(new Dimension(0, 15))); 
		balanceheaderpanel.add(lblbalance1);
		balanceheaderpanel.add(Box.createRigidArea(new Dimension(0, 30))); 
		balanceheaderpanel.add(lblLoanBalance);
		balanceheaderpanel.add(Box.createRigidArea(new Dimension(0, 15)));
		balanceheaderpanel.add(lblLoanBalanceAmount);

		balancepanel.add(balanceheaderpanel, BorderLayout.CENTER);

		// --- FOOTER (Notifications) ---
		JPanel notificationpanel = new JPanel();
		notificationpanel.setBackground(new Color(248, 249, 250));
		notificationpanel.setPreferredSize(new Dimension(0, 120)); 
		notificationpanel.setLayout(new BorderLayout()); 

		
		DefaultListModel<String> notificationListModel = new DefaultListModel<>();

		
		JList<String> notificationList = new JList<>(notificationListModel);
		notificationList.setFont(new Font("Roboto", Font.PLAIN, 14));
		notificationList.setForeground(new Color(108, 117, 125));

		
		JScrollPane scrollPane1 = new JScrollPane(notificationList);
		scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always show vertical scroll bar

		
		notificationpanel.add(scrollPane1, BorderLayout.CENTER);

		dashboardpanel2.add(notificationpanel, BorderLayout.SOUTH);


		// TRANSACTION PANEL SETUP
		JPanel transactionPanel = new JPanel();
		switchpanel.add(transactionPanel, "name_695650254038700");
		transactionPanel.setLayout(new CardLayout(0, 0));
		transactionPanel.setVisible(false);
		transactionPanel.setBackground(new Color(245, 245, 245)); 

		// Main panel inside transaction
		JPanel transactionPanel1 = new JPanel();
		transactionPanel.add(transactionPanel1, "TransactionMain");
		transactionPanel1.setLayout(new BorderLayout(20, 20));
		transactionPanel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));//s

		// Title
		JLabel transactionTitle = new JLabel("💸 Transaction");
		transactionTitle.setFont(new Font("Roboto", Font.BOLD, 30));
		transactionTitle.setForeground(new Color(33, 37, 41)); 
		transactionTitle.setHorizontalAlignment(SwingConstants.CENTER);
		transactionPanel1.add(transactionTitle, BorderLayout.NORTH);

		// Main content panel
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(3, 1, 20, 20));
		contentPanel.setBackground(new Color(245, 245, 245));

		// Wrap contentPanel in a ScrollPane
		JScrollPane scrollPane11 = new JScrollPane(contentPanel);
		scrollPane11.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane11.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		transactionPanel1.add(scrollPane11, BorderLayout.CENTER);

		// ========== ACCOUNT INFORMATION PANEL ==========
		// Style this panel to make it more readable
		JPanel accountInfoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		accountInfoPanel.setBorder(BorderFactory.createTitledBorder("Account Information"));
		accountInfoPanel.setBackground(new Color(255, 255, 255)); 
		accountInfoPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); 

		// Labels and fields styling
		JLabel lblAccountNumber = new JLabel("Account Number:");
		lblAccountNumber.setHorizontalAlignment(SwingConstants.CENTER);
		JTextField txtAccountNumber = new JTextField(account.getAccountNumber());
		txtAccountNumber.setEditable(false);
		txtAccountNumber.setBackground(new Color(240, 240, 240));

		JLabel lblAccountHolder = new JLabel("Account Holder Name:");
		lblAccountHolder.setHorizontalAlignment(SwingConstants.CENTER);
		JTextField txtAccountHolder = new JTextField(account.getOwner().getName());
		txtAccountHolder.setEditable(false);
		txtAccountHolder.setBackground(new Color(240, 240, 240));

		JLabel lblAccountType = new JLabel("Account Type:");
		lblAccountType.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lblAccountTypeValue = new JLabel(account.getAccountType());
		lblAccountTypeValue.setForeground(new Color(33, 37, 41)); 
		
		accountInfoPanel.add(lblAccountNumber);
		accountInfoPanel.add(txtAccountNumber);
		accountInfoPanel.add(lblAccountHolder);
		accountInfoPanel.add(txtAccountHolder);
		accountInfoPanel.add(lblAccountType);
		accountInfoPanel.add(lblAccountTypeValue);

		contentPanel.add(accountInfoPanel);

		// ========== SELECT TRANSACTION TYPE PANEL ==========
		// Style the transaction type panel
		JPanel transactionTypePanel = new JPanel(new GridLayout(1, 3, 10, 10));
		transactionTypePanel.setBorder(BorderFactory.createTitledBorder("Select Transaction Type"));
		transactionTypePanel.setBackground(new Color(255, 255, 255)); 

		// Checkboxes styling
		JCheckBox cbDeposit = new JCheckBox("Deposit");
		cbDeposit.setFont(new Font("Roboto", Font.PLAIN, 16));
		JCheckBox cbWithdraw = new JCheckBox("Withdraw");
		cbWithdraw.setFont(new Font("Roboto", Font.PLAIN, 16));
		JCheckBox cbTransfer = new JCheckBox("Transfer");
		cbTransfer.setFont(new Font("Roboto", Font.PLAIN, 16));

		// Adding the checkboxes to the panel
		transactionTypePanel.add(cbDeposit);
		transactionTypePanel.add(cbWithdraw);
		transactionTypePanel.add(cbTransfer);

		contentPanel.add(transactionTypePanel);

		// ========== TRANSACTION DETAILS PANEL ==========
		// Style the transaction details panel
		JPanel transactionDetailsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
		transactionDetailsPanel.setBorder(BorderFactory.createTitledBorder("Transaction Details"));
		transactionDetailsPanel.setBackground(new Color(255, 255, 255)); // White background for this panel
		transactionDetailsPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // Soft border

		// Labels and fields styling
		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setHorizontalAlignment(SwingConstants.CENTER);
		JTextField txtAmount = new JTextField();

		JLabel lblDate = new JLabel("Date:");
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		JTextField txtDate = new JTextField();
		txtDate.setEditable(false);
		txtDate.setText(java.time.LocalDate.now().toString());
		txtDate.setBackground(new Color(240, 240, 240));

		JLabel lblRecipientAccount = new JLabel("Recipient Account Number:");
		lblRecipientAccount.setHorizontalAlignment(SwingConstants.CENTER);
		JTextField txtRecipientAccount = new JTextField();
		txtRecipientAccount.setEnabled(false);

		JLabel lblRecipientName = new JLabel("Recipient Account Holder Name:");
		lblRecipientName.setHorizontalAlignment(SwingConstants.CENTER);
		JTextField txtRecipientName = new JTextField();
		txtRecipientName.setEnabled(false);

		// Submit button styling
		JButton btnSubmitTransaction = new JButton("Submit Transaction");
		btnSubmitTransaction.setFont(new Font("Roboto", Font.BOLD, 16));
		btnSubmitTransaction.setBackground(new Color(0, 123, 255)); // Bootstrap blue
		btnSubmitTransaction.setForeground(Color.WHITE);
		btnSubmitTransaction.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding around the button

		transactionDetailsPanel.add(lblAmount);
		transactionDetailsPanel.add(txtAmount);
		transactionDetailsPanel.add(lblDate);
		transactionDetailsPanel.add(txtDate);
		transactionDetailsPanel.add(lblRecipientAccount);
		transactionDetailsPanel.add(txtRecipientAccount);
		transactionDetailsPanel.add(lblRecipientName);
		transactionDetailsPanel.add(txtRecipientName);
		transactionDetailsPanel.add(new JLabel()); // empty cell
		transactionDetailsPanel.add(btnSubmitTransaction);

		contentPanel.add(transactionDetailsPanel);

		// ========== Checkbox LOGIC ==========
		// Transaction type selection logic
		cbDeposit.addActionListener(e -> {
			if (cbDeposit.isSelected()) {
				cbWithdraw.setSelected(false);
				cbTransfer.setSelected(false);
			}
			boolean isTransferSelected = cbTransfer.isSelected();
			txtRecipientAccount.setEnabled(isTransferSelected);
			txtRecipientName.setEnabled(isTransferSelected);
		});
		cbWithdraw.addActionListener(e -> {
			if (cbWithdraw.isSelected()) {
				cbDeposit.setSelected(false);
				cbTransfer.setSelected(false);
			}
			boolean isTransferSelected = cbTransfer.isSelected();
			txtRecipientAccount.setEnabled(isTransferSelected);
			txtRecipientName.setEnabled(isTransferSelected);
		});
		cbTransfer.addActionListener(e -> {
			if (cbTransfer.isSelected()) {
				cbDeposit.setSelected(false);
				cbWithdraw.setSelected(false);
			}
			boolean isTransferSelected = cbTransfer.isSelected();
			txtRecipientAccount.setEnabled(isTransferSelected);
			txtRecipientName.setEnabled(isTransferSelected);
		});



		// ========== SUBMIT LOGIC (Sample) ==========
		btnSubmitTransaction.addActionListener(e -> {
			String selectedAccountType = lblAccountTypeValue.getText().toString();
			String transaction = "";
			if (cbDeposit.isSelected()) transaction = "Deposit";
			else if (cbWithdraw.isSelected()) transaction = "Withdraw";
			else if (cbTransfer.isSelected()) transaction = "Transfer";

			if (transaction.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please select a transaction type.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String message = "Transaction: " + transaction + "\n" +
					"Account No: " + txtAccountNumber.getText() + "\n" +
					"Amount: " + txtAmount.getText() + "\n" +
					"Date: " + txtDate.getText() + "\n" +
					"Account Type: " + selectedAccountType;

			if (cbTransfer.isSelected()) {
				message += "\nRecipient Account No: " + txtRecipientAccount.getText() +
						"\nRecipient Name: " + txtRecipientName.getText();
			}
			double amount;
			try {
				amount = Double.parseDouble(txtAmount.getText());
				if (amount <= 0) throw new NumberFormatException();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			boolean transactionSuccess = false;
			String transactionType = "";
			bank = BankLedger.getInstance().getBank();
			if (selectedAccountType.equals("Savings") || selectedAccountType.equals("Checking")) {

				if (cbWithdraw.isSelected()) {
					transactionSuccess = bank.withdraw(account, amount);
					transactionType = "Withdrawn";
				} 
				else if (cbDeposit.isSelected()) {
					transactionSuccess = bank.deposit(account, amount); 
					transactionType = "Deposited";
				} 
				else if (cbTransfer.isSelected()) {
					String recipientAccountNumber = txtRecipientAccount.getText().trim();
					String recipientAccountName = txtRecipientName.getText().trim();

					if (recipientAccountNumber.isEmpty() || recipientAccountName.isEmpty()) {
						JOptionPane.showMessageDialog(this, "Please enter complete recipient information.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					bankLedger = BankLedger.getInstance();

					Account recipientAccount = bankLedger.findAccountByAccountNumber(recipientAccountNumber);
					if (recipientAccount == null) {
						JOptionPane.showMessageDialog(this, "Recipient account not found.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (!recipientAccount.getOwner().getName().equals(recipientAccountName)) {
						JOptionPane.showMessageDialog(this, "Recipient name does not match the account.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					transactionSuccess = bank.transfer(account, recipientAccount, amount);
					transactionType = "Transferred";
				} 
				else {
					JOptionPane.showMessageDialog(this, "Please select a transaction type.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} 

			else if (selectedAccountType.equals("Loan")) {
				if (cbDeposit.isSelected()) {
					if (account instanceof LoanAccount) {
						transactionSuccess = ((LoanAccount) account).deposit(amount);
						transactionType = "Loan Payment";
					} 
				} else {
					JOptionPane.showMessageDialog(this, "Only deposits (payments) are allowed for Loan accounts.", 
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} 

			else {
				JOptionPane.showMessageDialog(this, "Unknown account type selected.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (transactionSuccess) {
				lblbalance1.setText("Balance: ₱" + String.format("%.2f", account.getBalance()));
				LowBalanceNotifier.checkAndNotify(account);
				if (account instanceof LoanAccount) {
					lblLoanBalanceAmount.setText(String.format("₱%,.2f", ((LoanAccount) account).getLoanBalance()));
				} else {
					lblLoanBalanceAmount.setText("₱0.00"); 
				}

				String successMessage = switch (transactionType) {
				case "Withdrawn" -> "Withdrawal successful!";
				case "Deposited" -> "Deposit successful!";
				case "Transferred" -> "Transfer successful!";
				case "Loan Payment" -> "Loan payment successful!";
				default -> "Transaction successful!";
				};

				JOptionPane.showMessageDialog(this, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(this, message, "Transaction Submitted", JOptionPane.INFORMATION_MESSAGE);
				
				if (account.getBalance() == 0) {
					if (account instanceof CheckingAccount) {
						((CheckingAccount) account).showRemainingOverdraft(); 
					}
				}

				// Update Notifications and Recent Transactions
				if (transactionType.equals("Transferred")) {
					notificationListModel.addElement("🔔 Transfer of ₱" + amount + " successful.");
					recentTransactionListModel.addElement("• Transferred: ₱" + amount);
				} 
				else if (transactionType.equals("Loan Payment")) {
					notificationListModel.addElement("🔔 Loan payment of ₱" + amount + " received.");
					recentTransactionListModel.addElement("• Loan Payment: ₱" + amount);
				} 
				else {
					notificationListModel.addElement("🔔 " + transactionType + " ₱" + amount + " successful.");
					recentTransactionListModel.addElement("• " + transactionType + ": ₱" + amount);
				}

				updateRecentTransactions();
			} 

			else {
				JOptionPane.showMessageDialog(this, "Transaction failed.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		});
		
		
		JPanel transactionHistory = new JPanel();
		switchpanel.add(transactionHistory, "name_695650282872100");
		transactionHistory.setLayout(new CardLayout(0, 0));
		transactionHistory.setVisible(false);
		
		JPanel transactionHistoryPanel = new JPanel(new BorderLayout(20, 20));
		transactionHistoryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		transactionHistory.add(transactionHistoryPanel);

		// Title
		JLabel historyTitle = new JLabel("📜 Transaction History");
		historyTitle.setFont(new Font("Roboto", Font.BOLD, 30));
		historyTitle.setHorizontalAlignment(SwingConstants.CENTER);
		transactionHistoryPanel.add(historyTitle, BorderLayout.NORTH);

		// Table Setup
		String[] columnNames = { "Date", "Type", "Amount", "Recipient" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
		
		JTable transactionTable = new JTable(tableModel);

		// Scrollable Table
		JScrollPane scrollPane = new JScrollPane(transactionTable);
		transactionHistoryPanel.add(scrollPane, BorderLayout.CENTER);

		// Only Refresh Button Panel
		JPanel buttonPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10)); // LEFT aligned
		JButton btnRefresh = new JButton("🔄 Refresh");
		buttonPanel1.add(btnRefresh);

		// Add only the refresh button panel to SOUTH
		transactionHistoryPanel.add(buttonPanel1, BorderLayout.SOUTH);
		
		btnRefresh.addActionListener(e -> {
		    updateTransactionTable(tableModel, account); // Refresh the table when the user clicks "Refresh"
		});



		
		// ACCOUNT INFORMATION PANEL SETUP
		JPanel accountInformationPanel = new JPanel();
		switchpanel.add(accountInformationPanel, "name_695650313995900");
		accountInformationPanel.setLayout(new CardLayout(0, 0));
		accountInformationPanel.setVisible(false);
		accountInformationPanel.setBackground(new Color(245, 245, 245)); // Light gray background

		// Main panel inside account information
		JPanel accountInformationPanel1 = new JPanel();
		accountInformationPanel.add(accountInformationPanel1, "AccountInfo");
		accountInformationPanel1.setLayout(new BorderLayout(20, 20));
		accountInformationPanel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

		// Title
		JLabel lblAccountInformationTitle = new JLabel("👤 Account Information");
		lblAccountInformationTitle.setFont(new Font("Roboto", Font.BOLD, 30));
		lblAccountInformationTitle.setForeground(new Color(33, 37, 41));
		lblAccountInformationTitle.setHorizontalAlignment(SwingConstants.CENTER);
		accountInformationPanel1.add(lblAccountInformationTitle, BorderLayout.NORTH);

		// Main content panel
		JPanel accountContentPanel = new JPanel();
		accountContentPanel.setLayout(new GridLayout(4, 1, 20, 20));
		accountContentPanel.setBackground(new Color(245, 245, 245));

		// Wrap accountContentPanel in a ScrollPane
		JScrollPane accountScrollPane = new JScrollPane(accountContentPanel);
		accountScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		accountScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		accountInformationPanel1.add(accountScrollPane, BorderLayout.CENTER);

		// ========== ACCOUNT HOLDER NAME ==========
		JPanel accountHolderPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		accountHolderPanel.setBorder(BorderFactory.createTitledBorder("Account Holder Name"));
		accountHolderPanel.setBackground(new Color(255, 255, 255));
		accountHolderPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

		JLabel lblUsernameAccount = new JLabel("Name:");
		lblUsernameAccount.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lblUsernameInfo = new JLabel(account.getOwner().getName());
		lblUsernameInfo.setHorizontalAlignment(SwingConstants.CENTER);

		accountHolderPanel.add(lblUsernameAccount);
		accountHolderPanel.add(lblUsernameInfo);
		accountContentPanel.add(accountHolderPanel);

		// ========== ACCOUNT NUMBER ==========
		JPanel accountNumberPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		accountNumberPanel.setBorder(BorderFactory.createTitledBorder("Account Number"));
		accountNumberPanel.setBackground(new Color(255, 255, 255));
		accountNumberPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

		JLabel lblAccountNumberInfo = new JLabel("Number:");
		lblAccountNumberInfo.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lblAccountNumberView = new JLabel(account.getAccountNumber());
		lblAccountNumberView.setHorizontalAlignment(SwingConstants.CENTER);

		accountNumberPanel.add(lblAccountNumberInfo);
		accountNumberPanel.add(lblAccountNumberView);
		accountContentPanel.add(accountNumberPanel);

		// ========== ACCOUNT TYPE ==========
		JPanel accountTypePanel = new JPanel(new GridLayout(1, 2, 10, 10));
		accountTypePanel.setBorder(BorderFactory.createTitledBorder("Account Type"));
		accountTypePanel.setBackground(new Color(255, 255, 255));
		accountTypePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

		JLabel lblAccountTypeInfo = new JLabel("Type:");
		lblAccountTypeInfo.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lblAccountTypeView = new JLabel(account.getAccountType());
		lblAccountTypeView.setHorizontalAlignment(SwingConstants.CENTER);

		accountTypePanel.add(lblAccountTypeInfo);
		accountTypePanel.add(lblAccountTypeView);
		accountContentPanel.add(accountTypePanel);

		// ========== ACCOUNT BALANCE ==========
		JPanel accountBalancePanel = new JPanel(new GridLayout(1, 2, 10, 10));
		accountBalancePanel.setBorder(BorderFactory.createTitledBorder("Account Balance"));
		accountBalancePanel.setBackground(new Color(255, 255, 255));
		accountBalancePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

		JLabel lblAccountBalanceInfo = new JLabel("Balance:");
		lblAccountBalanceInfo.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lblAccountBalanceView = new JLabel(String.format("₱%,.2f", account.getBalance()));
		lblAccountBalanceView.setHorizontalAlignment(SwingConstants.CENTER);

		accountBalancePanel.add(lblAccountBalanceInfo);
		accountBalancePanel.add(lblAccountBalanceView);
		accountContentPanel.add(accountBalancePanel);



		//Switching panel
		btnTransaction.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				dashboardpanel1.setVisible(false);
				transactionPanel.setVisible(true);
				transactionHistory.setVisible(false);
				accountInformationPanel.setVisible(false);
			}
		});

		btnDashboard.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				dashboardpanel1.setVisible(true);
				transactionPanel.setVisible(false);
				transactionHistory.setVisible(false);
				accountInformationPanel.setVisible(false);
				
			}
		});
		
		btnReport.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dashboardpanel1.setVisible(false);
				transactionPanel.setVisible(false);
				transactionHistory.setVisible(true);
				accountInformationPanel.setVisible(false);
			}
		});
		
		btnAccount.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dashboardpanel1.setVisible(false);
				transactionPanel.setVisible(false);
				transactionHistory.setVisible(false);
				accountInformationPanel.setVisible(true);
			}
		});
		
		
		
		
	}
	
	public void updateTransactionTable(DefaultTableModel tableModel, Account account) {
	    java.util.List<Transaction> transactions = account.getHistory().getHistoryList();
	    tableModel.setRowCount(0); // Clear existing rows

	    for (Transaction transaction : transactions) {
	        String date = transaction.getDate().toString();
	        String type = transaction.getAction();
	        String amount = String.format("₱%.2f", transaction.getAmount());
	        String recipient = account.getOwner().getName(); // Default recipient

	        // Handle transfers
	        if (type.startsWith("Transfer to ")) {
	            recipient = type.substring(12); // Extract recipient from action text
	            type = "Transfer Sent";
	        } else if (type.startsWith("Transfer from ")) {
	            recipient = type.substring(14); // Extract recipient from action text
	            type = "Transfer Received";
	        }

	        // Add row to table
	        tableModel.addRow(new Object[]{date, type, amount, recipient});
	    }
	}

	private void updateRecentTransactions() {
		if (recentTransactionListModel.size() > 50) {
			recentTransactionListModel.remove(0); 
		}
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
