package gui;

import javax.swing.*;

import system.Account;
import system.AccountFactory;
import system.BankLedger;
import system.Customer;
import system.LoanAccount;

import javax.swing.DefaultListModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dashboard extends JFrame {
	private Color navBgColor = new Color(255, 255, 255);
	private Color activeColor = new Color(0, 128, 255);
	private Color hoverColor = new Color(220, 230, 240);
	private Color infoBgColor = new Color(240, 240, 240);
	private JButton btnLogout;
	private JButton activeButton;
	private JButton[] buttons;
	private DefaultListModel<String> recentTransactionListModel;
	
	public Dashboard(Account account) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\justf\\Downloads/bank.png"));
		setTitle("Northland Bank");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
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
		JButton btnDashboard = createNavButton("📊 Dashboard");
		JButton btnTransaction = createNavButton("💸 Transaction");
		JButton btnReport = createNavButton("📄 Report");
		JButton btnAccount = createNavButton("👤 Account");
		JButton btnLogout = createNavButton("🚪 Logout");
		
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
		switchpanel.setLayout(new BorderLayout(0, 0));
		
		JPanel dashboardpanel1 = new JPanel();
		dashboardpanel1.setBackground(new Color(192, 192, 192));
		switchpanel.add(dashboardpanel1);
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

		// example: you need to track this loan amount from your account class
		JLabel lblLoanBalanceAmount;
		if (account instanceof LoanAccount) {
		    lblLoanBalanceAmount = new JLabel(String.format("₱%,.2f", ((LoanAccount) account).getLoanBalance()));
		} else {
		    lblLoanBalanceAmount = new JLabel("₱0.00"); // No loan if not a LoanAccount
		}
		lblLoanBalanceAmount.setFont(new Font("Poppins", Font.BOLD, 28));
		lblLoanBalanceAmount.setForeground(new Color(220, 53, 69)); // Red color for debt
		lblLoanBalanceAmount.setAlignmentX(Component.CENTER_ALIGNMENT);

		balanceheaderpanel.add(lblbalance);
		balanceheaderpanel.add(Box.createRigidArea(new Dimension(0, 15))); // Space between
		balanceheaderpanel.add(lblbalance1);
		balanceheaderpanel.add(Box.createRigidArea(new Dimension(0, 30))); // more space
		balanceheaderpanel.add(lblLoanBalance);
		balanceheaderpanel.add(Box.createRigidArea(new Dimension(0, 15)));
		balanceheaderpanel.add(lblLoanBalanceAmount);

		balancepanel.add(balanceheaderpanel, BorderLayout.CENTER);

		// --- FOOTER (Notifications) ---
				JPanel notificationpanel = new JPanel();
				notificationpanel.setBackground(new Color(248, 249, 250));
				notificationpanel.setPreferredSize(new Dimension(0, 120)); // Increased height to allow scrolling
				notificationpanel.setLayout(new BorderLayout()); // Use BorderLayout for scrolling panel

				// Create a DefaultListModel to hold notification messages
				DefaultListModel<String> notificationListModel = new DefaultListModel<>();

				// Create a JList with the model
				JList<String> notificationList = new JList<>(notificationListModel);
				notificationList.setFont(new Font("Roboto", Font.PLAIN, 14));
				notificationList.setForeground(new Color(108, 117, 125));

				// Make the JList scrollable
				JScrollPane scrollPane1 = new JScrollPane(notificationList);
				scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always show vertical scroll bar

				// Add the scrollable list to the notification panel
				notificationpanel.add(scrollPane1, BorderLayout.CENTER);

				// Add the notification panel to your dashboard
				dashboardpanel2.add(notificationpanel, BorderLayout.SOUTH);

		
				// TRANSACTION PANEL SETUP
				JPanel transactionPanel = new JPanel();
				switchpanel.add(transactionPanel, BorderLayout.NORTH);
				transactionPanel.setLayout(new CardLayout(0, 0));
				transactionPanel.setVisible(false);
				transactionPanel.setBackground(new Color(245, 245, 245)); // Light gray background for the whole panel

				// Main panel inside transaction
				JPanel transactionPanel1 = new JPanel();
				transactionPanel.add(transactionPanel1, "TransactionMain");
				transactionPanel1.setLayout(new BorderLayout(20, 20));
				transactionPanel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

				// Title
				JLabel transactionTitle = new JLabel("💸 Transaction");
				transactionTitle.setFont(new Font("Roboto", Font.BOLD, 30));
				transactionTitle.setForeground(new Color(33, 37, 41)); // Dark text color
				transactionTitle.setHorizontalAlignment(SwingConstants.CENTER);
				transactionPanel1.add(transactionTitle, BorderLayout.NORTH);

				// Main content panel
				// Create the content panel
				JPanel contentPanel = new JPanel();
				contentPanel.setLayout(new GridLayout(3, 1, 20, 20));
				contentPanel.setBackground(new Color(245, 245, 245)); // Light gray background for content panel

				// Wrap contentPanel in a ScrollPane
				JScrollPane scrollPane11 = new JScrollPane(contentPanel);
				scrollPane11.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPane11.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				transactionPanel1.add(scrollPane11, BorderLayout.CENTER);

				// ========== ACCOUNT INFORMATION PANEL ==========
				// Style this panel to make it more readable
				JPanel accountInfoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
				accountInfoPanel.setBorder(BorderFactory.createTitledBorder("Account Information"));
				accountInfoPanel.setBackground(new Color(255, 255, 255)); // White background for this panel
				accountInfoPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // Soft border

				// Labels and fields styling
				JLabel lblAccountNumber = new JLabel("Account Number:");
				JTextField txtAccountNumber = new JTextField(account.getAccountNumber());
				txtAccountNumber.setEditable(false);
				txtAccountNumber.setBackground(new Color(240, 240, 240));

				JLabel lblAccountHolder = new JLabel("Account Holder Name:");
				JTextField txtAccountHolder = new JTextField(account.getOwner().getName());
				txtAccountHolder.setEditable(false);
				txtAccountHolder.setBackground(new Color(240, 240, 240));

				JLabel lblAccountType = new JLabel("Account Type:");
				JLabel lblAccountTypeValue = new JLabel(account.getAccountType());
				lblAccountTypeValue.setForeground(new Color(33, 37, 41)); // Dark text color

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
				transactionTypePanel.setBackground(new Color(255, 255, 255)); // White background for this panel

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
				JTextField txtAmount = new JTextField();

				JLabel lblDate = new JLabel("Date:");
				JTextField txtDate = new JTextField();
				txtDate.setEditable(false);
				txtDate.setText(java.time.LocalDate.now().toString());
				txtDate.setBackground(new Color(240, 240, 240));

				JLabel lblRecipientAccount = new JLabel("Recipient Account Number:");
				JTextField txtRecipientAccount = new JTextField();
				txtRecipientAccount.setEnabled(false);

				JLabel lblRecipientName = new JLabel("Recipient Account Holder Name:");
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
                if (amount < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
		    
		    boolean transactionSuccess = false;
		    String transactionType = "";

		    if (selectedAccountType.equals("Savings") || selectedAccountType.equals("Checking")) {
		        
		        if (cbWithdraw.isSelected()) {
		            transactionSuccess = account.withdraw(amount);
		            transactionType = "Withdrawn";
		        } 
		        else if (cbDeposit.isSelected()) {
		            transactionSuccess = account.deposit(amount); // Deposit usually always succeeds
		            transactionType = "Deposited";
		        } 
		        else if (cbTransfer.isSelected()) {
		            String recipientAccountNumber = txtRecipientAccount.getText().trim();
		            String recipientAccountName = txtRecipientName.getText().trim();

		            if (recipientAccountNumber.isEmpty() || recipientAccountName.isEmpty()) {
		                JOptionPane.showMessageDialog(this, "Please enter complete recipient information.", "Error", JOptionPane.ERROR_MESSAGE);
		                return;
		            }
		            
		            BankLedger bankLedger = BankLedger.getInstance();

		            Account recipientAccount = bankLedger.findAccountByAccountNumber(recipientAccountNumber);
		            if (recipientAccount == null) {
		                JOptionPane.showMessageDialog(this, "Recipient account not found.", "Error", JOptionPane.ERROR_MESSAGE);
		                return;
		            }

		            if (!recipientAccount.getOwner().getName().equals(recipientAccountName)) {
		                JOptionPane.showMessageDialog(this, "Recipient name does not match the account.", "Error", JOptionPane.ERROR_MESSAGE);
		                return;
		            }

		            transactionSuccess = account.transfer(recipientAccount, account.getOwner(), amount);
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
		            JOptionPane.showMessageDialog(this, "Only deposits (payments) are allowed for Loan accounts.", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		    } 
		    
		    else {
		        JOptionPane.showMessageDialog(this, "Unknown account type selected.", "Error", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

		    if (transactionSuccess) {
		        lblbalance1.setText("Balance: ₱" + String.format("%.2f", account.getBalance()));
		        
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



		btnTransaction.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				dashboardpanel1.setVisible(false);
				transactionPanel.setVisible(true);
			}
		});
		
		btnDashboard.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				dashboardpanel1.setVisible(true);
				transactionPanel.setVisible(false);
			}
		});

	}
	
	private void updateRecentTransactions() {
	    // Ensure that the list doesn't grow too large
	    if (recentTransactionListModel.size() > 5) {
	        recentTransactionListModel.remove(0); // Remove the oldest transaction if there are more than 5
	    }
	}
	
	private void setActiveButton(JButton selectedButton) {
	    for (JButton button : buttons) {
	        if (button == selectedButton) {
	            button.setBackground(activeColor);
	            button.setForeground(Color.WHITE);
	        } else if (button != btnLogout) { // Logout should not be blue
	            button.setBackground(navBgColor);
	            button.setForeground(Color.BLACK);
	        }
	    }
	    activeButton = selectedButton;
	}
	
	// BUTTON CREATION METHOD (to avoid repeating code)
			private JButton createNavButton(String text) {
			    JButton button = new JButton(text);
			    button.setHorizontalAlignment(SwingConstants.LEFT);
			    button.setBackground(navBgColor);
			    button.setFont(new Font("SansSerif", Font.BOLD, 15));
			    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			    button.setFocusPainted(false);
			    button.setBorderPainted(false);
			    button.setContentAreaFilled(true);
			    button.setOpaque(true);
			    button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10)); // left padding
			    return button;
			}
	

}
