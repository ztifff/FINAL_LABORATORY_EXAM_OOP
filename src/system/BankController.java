package system;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

public class BankController {
	private final BankLedger bankLedger;
	private Bank bank = BankLedger.getInstance().getBank();

	public BankController() {
		this.bankLedger = BankLedger.getInstance();
	}

	public void openCreateTransactionDialog(DefaultTableModel tableModel) {
		JTextField accountNumberField = new JTextField();
		JTextField amountField = new JTextField();
		String[] transactionTypes = {"Deposit", "Withdraw", "Transfer", "Loan Repayment", "Borrow"};
		JComboBox<String> typeDropdown = new JComboBox<>(transactionTypes);

		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel("Account Number:"));
		panel.add(accountNumberField);
		panel.add(new JLabel("Amount:"));
		panel.add(amountField);
		panel.add(new JLabel("Transaction Type:"));
		panel.add(typeDropdown);

		int result = JOptionPane.showConfirmDialog(null, panel, "Create New Transaction",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			String accNum = accountNumberField.getText().trim();
			String amountText = amountField.getText().trim();
			String type = (String) typeDropdown.getSelectedItem();

			try {
				double amount = Double.parseDouble(amountText);
				BankLedger bankLedger = BankLedger.getInstance();
				Account selectedAccount = bankLedger.getAccountByNumber(accNum);
				
				String transaction_1 = null;
				for (Transaction transaction1 : selectedAccount.getHistory().getHistoryList()) {
					transaction_1 = transaction1.getId();
				}

				if (selectedAccount != null) {
					switch (type) {
					case "Deposit":
						if (selectedAccount instanceof LoanAccount) {
							JOptionPane.showMessageDialog(null, "You cannot directly use Deposit in Loan use Loan Repayment");
							return;
						}
						selectedAccount.deposit(amount);
						selectedAccount.addNotification(new Notification(
								"Deposit Made", "You deposited PHP " + amount, LocalDate.now().toString(), transaction_1));
						break;

					case "Withdraw":
						if (selectedAccount instanceof LoanAccount) {
							JOptionPane.showMessageDialog(null, "You cannot directly use Withdraw in Loan use Borrow");
							return;
						}
						selectedAccount.withdraw(amount);
						selectedAccount.addNotification(new Notification(
								"Withdrawal Made", "You withdrew PHP " + amount, LocalDate.now().toString(), transaction_1));
						break;

					case "Transfer":
						if (selectedAccount instanceof LoanAccount) {
							JOptionPane.showMessageDialog(null, "Loan accounts cannot transfer money to others.");
							return;
						}
						String recipient = JOptionPane.showInputDialog("Enter recipient account number:");
						Account recipientAccount = bankLedger.getAccountByNumber(recipient);
						if (recipientAccount != null) {
							selectedAccount.transfer(recipientAccount, selectedAccount.getOwner(), amount);
							selectedAccount.addNotification(new Notification(
									"Transfer Sent", "You transferred PHP " + amount + " to " + recipient, LocalDate.now().toString(), transaction_1));
							recipientAccount.addNotification(new Notification(
									"Transfer Received", "You received PHP " + amount + " from " + selectedAccount.getAccountNumber(), LocalDate.now().toString(), transaction_1));
						} else {
							JOptionPane.showMessageDialog(null, "Recipient not found.");
							return;
						}
						break;

					case "Loan Repayment":
						Account loanAcc = bankLedger.getAccountByNumber(accNum);

						if (loanAcc == null || !(loanAcc instanceof LoanAccount)) {
							JOptionPane.showMessageDialog(null, "Loan account not found.");
							return;
						}

						// Get the payer (selectedAccount) and the recipient (loanAccount)
						LoanAccount loanAccount = (LoanAccount) loanAcc;

						if (loanAccount.getLoanBalance() < amount) {
							JOptionPane.showMessageDialog(null, "Insufficient balance for repayment.");
							return;
						}

						boolean success = loanAccount.repayLoan(amount);

						if (success) {

							selectedAccount.addNotification(new Notification(
									"Loan Repayment Sent",
									"You repaid PHP " + amount + " to loan account " + accNum,
									LocalDate.now().toString(), transaction_1));

							loanAccount.addNotification(new Notification(
									"Loan Repayment Received",
									"Your loan account received PHP " + amount + " from " + selectedAccount.getAccountNumber(),
									LocalDate.now().toString(), transaction_1));
						}
						break;
					case "Borrow":
						if (selectedAccount instanceof LoanAccount) {
							LoanAccount loanAccount1 = (LoanAccount) selectedAccount;
							loanAccount1.borrow(amount);
							selectedAccount.addNotification(new Notification(
									"Loan Borrowed", "You borrowed PHP " + amount + " from your loan account.", LocalDate.now().toString(), transaction_1));
						} else {
							JOptionPane.showMessageDialog(null, "Only loan accounts can borrow money.");
							return;
						}
						break;
					}

					JOptionPane.showMessageDialog(null, "Transaction successful!");

				} else {
					JOptionPane.showMessageDialog(null, "Account not found.");
				}

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Invalid amount.");
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Transaction Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	public void openCreateAccountDialog(DefaultTableModel tableModel) {
		JTextField nameField = new JTextField(20);
		JSpinner dobSpinner = new JSpinner(new SpinnerDateModel());
		dobSpinner.setEditor(new JSpinner.DateEditor(dobSpinner, "yyyy-MM-dd"));
		JTextField emailField = new JTextField(20);
		JTextField contactField = new JTextField(15);
		JTextField addressField = new JTextField(30);
		JPasswordField passwordField = new JPasswordField(20);
		JPasswordField confirmPasswordField = new JPasswordField(20);
		String[] accountTypes = {"Checking", "Savings", "Loan"};
		JComboBox<String> accountTypeBox = new JComboBox<>(accountTypes);
		JLabel lblInitial = new JLabel("Initial Deposit:");
		JTextField initialAmountField = new JTextField(10);

		accountTypeBox.addActionListener(a -> {
			lblInitial.setText("Loan".equalsIgnoreCase((String) accountTypeBox.getSelectedItem())
					? "Borrow Amount:" : "Initial Deposit:");
		});

		JPanel personalPanel = new JPanel(new GridLayout(0, 2, 5, 5));
		personalPanel.setBorder(BorderFactory.createTitledBorder("Personal Information"));
		personalPanel.add(new JLabel("Name:")); personalPanel.add(nameField);
		personalPanel.add(new JLabel("Date of Birth:")); personalPanel.add(dobSpinner);
		personalPanel.add(new JLabel("Email:")); personalPanel.add(emailField);
		personalPanel.add(new JLabel("Contact Number:")); personalPanel.add(contactField);
		personalPanel.add(new JLabel("Address:")); personalPanel.add(addressField);
		personalPanel.add(new JLabel("Password:")); personalPanel.add(passwordField);
		personalPanel.add(new JLabel("Confirm Password:")); personalPanel.add(confirmPasswordField);

		JPanel accountPanel = new JPanel(new GridLayout(0, 2, 5, 5));
		accountPanel.setBorder(BorderFactory.createTitledBorder("Account Details"));
		accountPanel.add(new JLabel("Account Type:")); accountPanel.add(accountTypeBox);
		accountPanel.add(lblInitial); accountPanel.add(initialAmountField);

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		contentPanel.add(personalPanel);
		contentPanel.add(Box.createVerticalStrut(10));
		contentPanel.add(accountPanel);

		int result = JOptionPane.showConfirmDialog(null, contentPanel, "Create New Account",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			try {
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
				double initialAmount = Double.parseDouble(initialAmountStr);

				String creationResult = createNewAccount(name, dob, email, contact, address,
						password, confirmPassword, type, initialAmount);

				if (creationResult.startsWith("NEW:") || creationResult.startsWith("EXISTING:")) {
					String accNum = creationResult.split(":")[1];
					Account acc = getAccountByNumber(accNum);
					Customer cust = acc.getOwner();

					tableModel.addRow(new Object[]{
							acc.getAccountNumber(),
							cust.getName(),
							cust.getEmail(),
							cust.getContactNumber(),
							acc.getAccountType(),
							cust.getPassword(),
							new JButton("Generate")
					});

					String msg = creationResult.startsWith("NEW") ?
							"New account created!" : "New account added to existing customer!";
					JOptionPane.showMessageDialog(null, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, creationResult, "Error", JOptionPane.ERROR_MESSAGE);
				}

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Invalid amount. Enter a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}



	public String createNewAccount(
			String name, String dob, String email, String contact, String address,
			String password, String confirmPassword, String type, double initialAmount
			) {
		// Validation
		if (name.isEmpty() || dob.isEmpty() || email.isEmpty() || contact.isEmpty() ||
				address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
			return "All fields must be filled.";
		}

		if (!email.contains("@") || !email.contains(".com")) {
			return "Invalid email format.";
		}

		if (!contact.matches("\\d{10,11}")) {
			return "Invalid contact number (must be 10 or 11 digits).";
		}

		if (!password.equals(confirmPassword)) {
			return "Passwords do not match.";
		}

		if (initialAmount < 0) {
			return "Initial amount must be a positive number.";
		}

		Customer existingCustomer = bank.findCustomerByEmail(email);

		if (existingCustomer != null) {
			boolean hasSameType = existingCustomer.getAccount().stream()
					.anyMatch(acc -> acc.getAccountType().equalsIgnoreCase(type));

			if (hasSameType) {
				return "This customer already has a " + type + " account.";
			}

			// Add new account under existing customer
			Account newAccount = AccountFactory.createAccount(type, existingCustomer);
			handleInitialAmount(newAccount, type, initialAmount);
			existingCustomer.addAccount(newAccount);
			bank.addAccount(newAccount);
			newAccount.addObserver(new LowBalanceNotifier());

			return "EXISTING:" + newAccount.getAccountNumber();
		}

		// New Customer
		Customer newCustomer = new Customer(name, dob, contact, email, address, password);
		Account newAccount = AccountFactory.createAccount(type, newCustomer);
		handleInitialAmount(newAccount, type, initialAmount);
		newCustomer.addAccount(newAccount);
		bankLedger.addAccount(newAccount);
		newAccount.addObserver(new LowBalanceNotifier());

		return "NEW:" + newAccount.getAccountNumber();
	}

	private void handleInitialAmount(Account account, String type, double amount) {
		String transaction_1 = null;
		for (Transaction transaction1 : account.getHistory().getHistoryList()) {
			transaction_1 = transaction1.getId();
		}
		if ("Loan".equalsIgnoreCase(type)) {
			((LoanAccount) account).borrow(amount);
			account.addNotification(new Notification("Loan Granted",
					"You borrowed PHP " + amount + " as your starting loan.",
					LocalDate.now().toString(), transaction_1));
		} else {
			account.deposit(amount);
			account.addNotification(new Notification("Initial Deposit Completed",
					"You deposited PHP " + amount + " as initial deposit.",
					LocalDate.now().toString(), transaction_1));
		}
	}

	public Account getAccountByNumber(String accountNumber) {
		return bankLedger.getAccountByNumber(accountNumber);
	}

	public Customer getCustomerByEmail(String email) {
		return bank.findCustomerByEmail(email);
	}


	public boolean deleteAccountByNumber(String accountNumber) {
		Account account = bankLedger.getAccountByNumber(accountNumber);
		if (account != null) {
			bankLedger.removeAccount(account); // Directly remove from ledger
			return true;
		}
		return false;
	}

	public void openDeleteTransactionDialog(DefaultTableModel tableModel, JTable table) {
	    int selectedRow = table.getSelectedRow();

	    if (selectedRow == -1) {
	        JOptionPane.showMessageDialog(null, "Please select a transaction to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    String transactionID = table.getValueAt(selectedRow, 1).toString(); // Assuming column 1 is the ID

	    int confirm = JOptionPane.showConfirmDialog(
	            null,
	            "Are you sure you want to delete this transaction?\nThis action cannot be undone.",
	            "Confirm Deletion",
	            JOptionPane.YES_NO_OPTION,
	            JOptionPane.WARNING_MESSAGE
	    );

	    if (confirm == JOptionPane.YES_OPTION) {
	        // Optional: remove from actual transaction list if needed
	         boolean success = deleteTransactionById(transactionID); 
	         if (success) {
	        	 ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
	 	        JOptionPane.showMessageDialog(null, "Transaction deleted successfully.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Transaction not found.", "Error", JOptionPane.ERROR_MESSAGE);
			}
	        
	    }
	}
	
	public boolean deleteTransactionById(String transactionId) {
	    for (Account acc : bankLedger.getAllAccounts()) {
	        List<Transaction> historyList = acc.getHistory().getHistoryList();
	        for (Transaction tx : new ArrayList<>(historyList)) {
	            if (tx.getId().equals(transactionId)) {
	                String action = tx.getAction();
	                double amount = tx.getAmount();

	                switch (action) {
	                case "Deposit":
	                    acc.setBalance(acc.getBalance() - amount); // reverse deposit
	                    break;

	                case "Withdraw":
	                    acc.setBalance(acc.getBalance() + amount); // reverse withdrawal
	                    break;

	                case String s when s.startsWith("Transfer to "): {
	                    String recipientAccNum = s.replace("Transfer to ", "").trim();
	                    Account recipient = bankLedger.getAccountByNumber(recipientAccNum);
	                    if (recipient != null) {
	                        recipient.setBalance(recipient.getBalance() - amount); // reverse recipient gain
	                        acc.setBalance(acc.getBalance() + amount);             // refund sender
	                    }
	                    break;
	                }

	                case String s when s.startsWith("Transfer from "): {
	                    // Optional: handle if needed, but normally mirrored above.
	                    break;
	                }

	                case "Loan Repayment":
	                    if (acc instanceof LoanAccount loanAcc) {
	                        loanAcc.setLoanBalance(loanAcc.getLoanBalance() + amount); // undo repayment
	                    }
	                    break;

	                case "Loan Borrowed":
	                    if (acc instanceof LoanAccount loanAcc) {
	                        loanAcc.setLoanBalance(loanAcc.getLoanBalance() - amount); // undo borrowed amount
	                    }
	                    break;

	                default:
	                    break;
	            }


	                // Remove notifications if needed (optional)
	                acc.removeNotificationRelatedTo(transactionId); // ← you'll need to implement this

	                // Remove the transaction from history
	                historyList.remove(tx);
	                return true;
	            }
	        }
	    }
	    return false;
	}



	public void openDeleteAccountDialog(DefaultTableModel tableModel, JTable table) {
		int selectedRow = table.getSelectedRow();

		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "Please select a row to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String accountNumber = table.getValueAt(selectedRow, 0).toString();

		int confirm = JOptionPane.showConfirmDialog(
				null,
				"Are you sure you want to delete this account?\nThis action cannot be undone.",
				"Confirm Deletion",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE
				);

		if (confirm == JOptionPane.YES_OPTION) {
			boolean success = deleteAccountByNumber(accountNumber);

			if (success) {
				((DefaultTableModel) table.getModel()).removeRow(selectedRow);
				JOptionPane.showMessageDialog(null, "Account deleted successfully.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void openEditAccountDialog(DefaultTableModel tableModel, JTable table) {
		int selectedRow = table.getSelectedRow();

		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "Please select a row to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String accountNumber = table.getValueAt(selectedRow, 0).toString();
		BankLedger bankLedger = BankLedger.getInstance();
		Account selectedAccount = bankLedger.getAccountByNumber(accountNumber);

		if (selectedAccount == null) {
			JOptionPane.showMessageDialog(null, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Customer owner = selectedAccount.getOwner();

		// Create text fields with current values
		JTextField nameField = new JTextField(owner.getName());
		JTextField contactField = new JTextField(owner.getContactNumber());
		JPasswordField passwordField = new JPasswordField();

		nameField.setPreferredSize(new Dimension(250, 28));
		contactField.setPreferredSize(new Dimension(250, 28));
		passwordField.setPreferredSize(new Dimension(250, 28));

		JLabel nameLabel = new JLabel("Full Name:");
		JLabel contactLabel = new JLabel("Contact Number:");
		JLabel passwordLabel = new JLabel("New Password:");
		JLabel noteLabel = new JLabel("(Enter current pasword to keep current password)");
		noteLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
		noteLabel.setForeground(Color.GRAY);

		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Edit Account Information"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 5, 10);
		gbc.anchor = GridBagConstraints.WEST;

		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(nameLabel, gbc);
		gbc.gridx = 1;
		formPanel.add(nameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		formPanel.add(contactLabel, gbc);
		gbc.gridx = 1;
		formPanel.add(contactField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		formPanel.add(passwordLabel, gbc);
		gbc.gridx = 1;
		formPanel.add(passwordField, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		formPanel.add(noteLabel, gbc);

		nameField.requestFocusInWindow();

		int result = JOptionPane.showConfirmDialog(
				null,
				formPanel,
				"Edit Account",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE
				);

		if (result == JOptionPane.OK_OPTION) {
			String newName = nameField.getText().trim();
			String newContact = contactField.getText().trim();
			String newPassword = new String(passwordField.getPassword()).trim();

			if (newName.isEmpty() || newContact.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Name and contact number cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Send empty password if unchanged (controller can handle that)
			boolean updated = updateAccount(accountNumber, newName, newContact, newPassword);

			if (updated) {
				tableModel.setValueAt(newName, selectedRow, 1);
				tableModel.setValueAt(newContact, selectedRow, 3);

				// Don't display password in the table for security reasons

				JOptionPane.showMessageDialog(null, "Account updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to update account. Please try again.", "Update Failed", JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	public boolean updateAccount(String accountNumber, String newName, String newContact, String newPassword) {
		Account account = bankLedger.getAccountByNumber(accountNumber);
		if (account == null) {
			return false; // account doesn't exist
		}

		Customer owner = account.getOwner();

		// Validation (can be expanded further)
		if (newName == null || newName.isEmpty() ||
				newContact == null || !newContact.matches("\\d{10,11}") ||
				newPassword == null || newPassword.isEmpty()) {
			return false;
		}

		owner.setName(newName);
		owner.setContactNumber(newContact);
		owner.setPassword(newPassword);
		return true;
	}

	public void openEditTransactionDialog(DefaultTableModel tableModel, JTable table) {
		int selectedRow = table.getSelectedRow();

		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "Please select a transaction to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Extract transaction details
		String date = table.getValueAt(selectedRow, 0).toString();
		String accountID = table.getValueAt(selectedRow, 1).toString();
		String accountName = table.getValueAt(selectedRow, 2).toString();
		String transactionID = table.getValueAt(selectedRow, 3).toString();
		String amountStr = table.getValueAt(selectedRow, 4).toString();
		String status = table.getValueAt(selectedRow, 5).toString();

		JTextField amountField = new JTextField(amountStr);

		amountField.setPreferredSize(new Dimension(250, 28));

		JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("Edit Transaction"));

		formPanel.add(new JLabel("Transaction Amount:"));
		formPanel.add(amountField);

		int result = JOptionPane.showConfirmDialog(
				null,
				formPanel,
				"Edit Transaction",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE
				);

		if (result == JOptionPane.OK_OPTION) {
		    String cleanAmountStr = amountField.getText().replace("₱", "").replace(",", "").trim();

		    try {
		        double newAmount = Double.parseDouble(cleanAmountStr);

		        // Update table view
		        tableModel.setValueAt("₱" + String.format("%.2f", newAmount), selectedRow, 4);

		     // Update backend
		        Account account = BankLedger.getInstance().findAccountByAccountNumber(accountID);

		        if (account != null) {
		            Transaction targetTransaction = account.getHistory().findTransactionID(transactionID);

		            if (targetTransaction != null) {
		                // Step 1: Update transaction amount
		                targetTransaction.setAmount(newAmount);

		                // Step 2: Recalculate balance or loan balance
		                account.recalculateBalance();

		                // Step 3 (Optional): Update notification content if needed
		                Notification relatedNotification = account.findNotificationByTransactionID(transactionID);

		                if (relatedNotification != null) {
		                    relatedNotification.setMessage("Transaction updated: New amount ₱" + String.format("%.2f", newAmount));
		                }

		                // Optional: Update UI / table again here if needed
		                tableModel.setValueAt("₱" + String.format("%.2f", newAmount), selectedRow, 4);

		                JOptionPane.showMessageDialog(null, "Transaction updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		            } else {
		                JOptionPane.showMessageDialog(null, "Transaction not found in backend.", "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Account not found in backend.", "Error", JOptionPane.ERROR_MESSAGE);
		        }

		    } catch (NumberFormatException e) {
		        JOptionPane.showMessageDialog(null, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
	}


	public List<Account> getAllAccounts() {
		return bankLedger.getAllAccounts();
	}
}

