package gui;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EventObject;
import java.util.List;
import java.util.stream.Collectors;
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
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SpinnerDateModel;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import controller.BankController;
import data.Bank;
import data.BankLedger;
import model.Account;
import model.Admin;
import model.LoanAccount;
import model.Transaction;
import service.ReportGenerator;

import javax.swing.SwingConstants;
import java.awt.CardLayout;

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
	private JTextField textField;
	private JButton[] buttons;
	private BankController controller = new BankController();
	private Bank bank = BankLedger.getInstance().getBank();
	private List<Account> customers = bank.getAllAccounts();
	private JTextField textField_1;

	@SuppressWarnings("serial")
	public ControlPanel(Admin admin) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
			    getClass().getResource("/photo/bank.png")
			));
		setTitle("Monthly Transaction Summary");
		setSize(1243, 831);
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

		logoutBtn.addActionListener(event -> {
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				dispose();
				Login login = new Login();
				login.setVisible(true);
				login.setLocationRelativeTo(null);
			}
		});

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
		switchPanel.setBounds(268, 0, 959, 881);
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
		manageAccountsheaderpanel.setBounds(0, 0, 959, 72);
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

		ImageIcon originalIcon = new ImageIcon(
			    getClass().getResource("/photo/bankicon-removebg-preview.png")
			);

		Image scaledImage = originalIcon.getImage().getScaledInstance(80, 70, Image.SCALE_SMOOTH); // width, height
		ImageIcon resizedIcon = new ImageIcon(scaledImage);

		JLabel manageAccountsbankIcon = new JLabel(resizedIcon);
		manageAccountsbankIcon.setHorizontalAlignment(SwingConstants.RIGHT);
		manageAccountsheaderpanel.add(manageAccountsbankIcon);

		// Table Panel with JTable
		tablePanel = new JPanel();
		tablePanel.setBounds(10, 223, 939, 526); // Adjust height as needed
		tablePanel.setLayout(new BorderLayout());

		String[] manageAccountsHeaders = {"ID", "Name", "Email", "Contact", "Type", "Password", "Report"};
		DefaultTableModel tableModel = new DefaultTableModel(manageAccountsHeaders, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 6; // Make only the button column editable (Generate Report)
			}
		};

		JTable accounTable = new JTable(tableModel) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				if (comp instanceof JComponent) {
					Object value = getValueAt(row, column);
					if (value != null) {
						((JComponent) comp).setToolTipText(value.toString());
					} else {
						((JComponent) comp).setToolTipText(null);
					}
				}
				return comp;
			}
		};

		accounTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		accounTable.setEnabled(true);
		accounTable.setRowSelectionAllowed(true);
		accounTable.setColumnSelectionAllowed(false);
		accounTable.setCellSelectionEnabled(false);
		accounTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		accounTable.setRowHeight(30); 
		accounTable.setFillsViewportHeight(true);




		// Custom cell renderer for the button column 
		accounTable.getColumnModel().getColumn(6).setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JButton button = new JButton("Generate");

				// Set button appearance (same as before)
				button.setMargin(new Insets(0, 10, 0, 10));
				button.setPreferredSize(new Dimension(button.getPreferredSize().width, 30));
				button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				button.setBackground(new Color(0, 123, 255));
				button.setForeground(Color.WHITE);
				button.setFocusPainted(false);
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));

				// Hover effect
				button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						button.setBackground(new Color(0, 105, 217));
					}

					@Override
					public void mouseExited(MouseEvent e) {
						button.setBackground(new Color(0, 123, 255));
					}
				});

				JPanel panel = new JPanel(new GridBagLayout());
				panel.add(button);
				return panel;
			}
		});


		// Custom cell editor for the button column 
		accounTable.getColumnModel().getColumn(6).setCellEditor(new TableCellEditor() {
			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
				JButton button = new JButton("Generate");

				// Set padding (top, left, bottom, right)
				button.setMargin(new Insets(0, 10, 0, 10)); // Adjusted padding to make button smaller
				table.setRowSelectionInterval(row, row);
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
		updateAccountsTable(tableModel);




		JTableHeader header = accounTable.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 14));
		header.setBackground(new Color(220, 220, 220));
		header.setForeground(Color.BLACK);
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);

		scrollPane = new JScrollPane(accounTable);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		manageAccounts1.add(tablePanel);


		accounTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int row = accounTable.rowAtPoint(point);

				// Ensure the row gets selected even if button is clicked
				if (row != -1) {
					accounTable.setRowSelectionInterval(row, row);
				}
			}
		});


		// Button controls
		JButton btnNewButton = new JButton("➕ Create New");
		btnNewButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		btnNewButton.setBounds(50, 90, 160, 45);
		btnNewButton.setBackground(new Color(40, 167, 69)); // Green
		btnNewButton.setForeground(Color.WHITE);
		manageAccounts1.add(btnNewButton);

		btnNewButton.addActionListener(e -> controller.openCreateAccountDialog(tableModel));

		JButton btnEdit = new JButton("✏️ Edit");
		btnEdit.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		btnEdit.setBounds(230, 90, 120, 45);
		btnEdit.setBackground(new Color(255, 193, 7)); // Yellow
		btnEdit.setForeground(Color.BLACK);
		manageAccounts1.add(btnEdit);

		btnEdit.addActionListener(e -> controller.openEditAccountDialog(tableModel, accounTable));


		JButton btnDelete = new JButton("🗑️ Delete");
		btnDelete.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		btnDelete.setBounds(370, 90, 130, 45);
		btnDelete.setBackground(new Color(220, 53, 69)); // Red
		btnDelete.setForeground(Color.WHITE);
		manageAccounts1.add(btnDelete);

		btnDelete.addActionListener(e -> controller.openDeleteAccountDialog(tableModel, accounTable));

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

		btnSearch.addActionListener(e -> {
			String keyword = textField.getText().trim().toLowerCase();

			if (keyword.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter a keyword to search.", "Empty Search", JOptionPane.WARNING_MESSAGE);
				return;
			}

			DefaultTableModel model = (DefaultTableModel) accounTable.getModel();
			TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
			accounTable.setRowSorter(sorter);

			// Filters rows that contain the keyword in *any column*
			RowFilter<DefaultTableModel, Object> filter = new RowFilter<>() {
				@Override
				public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
					for (int i = 0; i < entry.getValueCount(); i++) {
						Object value = entry.getValue(i);
						if (value != null && value.toString().toLowerCase().contains(keyword)) {
							return true;
						}
					}
					return false;
				}
			};

			sorter.setRowFilter(filter);
		});


		// Refresh Button
		JButton btnRefresh = new JButton("🔄 Refresh");
		btnRefresh.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		btnRefresh.setBounds(50, 150, 120, 35);
		btnRefresh.setBackground(new Color(108, 117, 125)); // Gray
		btnRefresh.setForeground(Color.WHITE);
		manageAccounts1.add(btnRefresh);
		btnRefresh.addActionListener(e -> {
			textField.setText("");
			TableRowSorter<?> sorter = (TableRowSorter<?>) accounTable.getRowSorter();
			if (sorter != null) {
				sorter.setRowFilter(null); // Remove the filter
			}
			updateAccountsTable(tableModel);
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
		montlyTransactionheaderpanel.setBounds(0, 0, 959, 72);
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
		tablePanel.setBounds(10, 100, 939, 479); // Adjust height as needed
		tablePanel.setLayout(new BorderLayout());

		String[] monthlyTransactionHeaders = {"Date", "Name", "Transaction ID", "Amount", "Type", "Balance"};
		DefaultTableModel tableModel1 = new DefaultTableModel(monthlyTransactionHeaders, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable monthlyTransactionTable = new JTable(tableModel1){
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				if (comp instanceof JComponent) {
					Object value = getValueAt(row, column);
					if (value != null) {
						((JComponent) comp).setToolTipText(value.toString());
					} else {
						((JComponent) comp).setToolTipText(null);
					}
				}
				return comp;
			}
		};
		monthlyTransactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		monthlyTransactionTable.setEnabled(true);
		monthlyTransactionTable.setRowSelectionAllowed(true);
		monthlyTransactionTable.setColumnSelectionAllowed(false);
		monthlyTransactionTable.setCellSelectionEnabled(false);
		monthlyTransactionTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		monthlyTransactionTable.setRowHeight(30); 
		monthlyTransactionTable.setFillsViewportHeight(true);

		monthlyTransactionTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
		refreshTransactionsTable(tableModel1);

		JTableHeader header1 = monthlyTransactionTable.getTableHeader();
		header1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		header1.setBackground(new Color(220, 220, 220));
		header1.setForeground(Color.BLACK);
		header1.setReorderingAllowed(false);
		header1.setResizingAllowed(false);

		scrollPane = new JScrollPane(monthlyTransactionTable);
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

		List<String> accountList = new ArrayList<>();
		accountList.add("Select Option");

		for (Account account : customers) {
			String displayText = account.getOwner().getName() + " - "  + account.getAccountNumber() + " - " + account.getAccountType();
			accountList.add(displayText);
		}

		List<String> accountsOnly = accountList.subList(1, accountList.size());
		Collections.sort(accountsOnly);

		// Assuming 'customers' is a List<Account> containing all the customer accounts
		JComboBox<String> accountDropdown = new JComboBox<>();
		for (String accountDisplay : accountList) {
			accountDropdown.addItem(accountDisplay);
		}
		accountDropdown.setBounds(590, 628, 220, 30);
		accountDropdown.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		montlyTransactionSummaryPanel1.add(accountDropdown);

		JButton btnRefresh_1 = new JButton("🔄 Refresh");
		btnRefresh_1.setForeground(Color.WHITE);
		btnRefresh_1.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		btnRefresh_1.setBackground(new Color(108, 117, 125));
		btnRefresh_1.setBounds(590, 668, 120, 35);
		montlyTransactionSummaryPanel1.add(btnRefresh_1);

		btnRefresh_1.addActionListener(e -> {
			refreshTransactionsTable(tableModel1);
			accountDropdown.removeAllItems(); 
			List<String> accountList1 = new ArrayList<>();
			accountList1.add("Select Option");

			for (Account account : customers) {
				String displayText = account.getOwner().getName() + " - " + account.getAccountNumber() + " - " + account.getAccountType();
				accountList1.add(displayText);
			}

			// Sort alphabetically
			List<String> accountsOnly1 = accountList1.subList(1, accountList1.size());
			Collections.sort(accountsOnly1);
			for (String accountDisplay : accountList1) {
				accountDropdown.addItem(accountDisplay);  
			}
		});


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

				if (selectedAccountName != null && !selectedAccountName.isEmpty()) {
					String[] parts = selectedAccountName.split(" - ");
					if (parts.length == 3) {
						String selectedName = parts[0];
						String selectedNumber = parts[1];
						String selectedType = parts[2];

						for (Account account : customers) {
							if (
									account.getOwner().getName().equals(selectedName) &&
									account.getAccountNumber().equals(selectedNumber) &&
									account.getAccountType().equals(selectedType)
									) {
								selectedAccount = account;
								break;
							}
						}
					}
				}

				if (selectedAccount != null) {
					if (selectedAccount.getAccountType().equalsIgnoreCase("Loan")) {
						String report = ReportGenerator.generateLoanTransactionSummary(selectedAccount, startDate, endDate);
						JOptionPane.showMessageDialog(montlyTransactionSummaryPanel1, report, "Loan Account Summary", JOptionPane.INFORMATION_MESSAGE);
					} else {
						String report = ReportGenerator.generateMonthlyTransactionSummary(selectedAccount, startDate.getMonthValue(), startDate.getYear());
						JOptionPane.showMessageDialog(montlyTransactionSummaryPanel1, report, "Monthly Transaction Summary", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					StringBuilder report = new StringBuilder("Summary for all accounts:\n\n");

					for (Account account : customers) {
						report.append("===== ")
						.append(account.getOwner().getName())
						.append(" - ")
						.append(account.getAccountNumber())
						.append(" - ")
						.append(account.getAccountType())
						.append(" =====\n");

						if (account.getAccountType().equalsIgnoreCase("Loan")) {
							report.append(ReportGenerator.generateLoanTransactionSummary(account, startDate, endDate));
						} else {
							report.append(ReportGenerator.generateMonthlyTransactionSummary(account, startDate.getMonthValue(), startDate.getYear()));
						}
						report.append("\n\n");
					}

					JTextArea textArea = new JTextArea(report.toString(), 25, 60); // Set rows & columns
					textArea.setWrapStyleWord(true);
					textArea.setLineWrap(true);
					textArea.setEditable(false);
					textArea.setCaretPosition(0); // Start from top

					JScrollPane scrollPane = new JScrollPane(textArea);
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

					JOptionPane.showMessageDialog(
							montlyTransactionSummaryPanel1,
							scrollPane,
							"All Account Transaction Summary",
							JOptionPane.INFORMATION_MESSAGE
							);
				}
			} catch (DateTimeParseException ex) {
				JOptionPane.showMessageDialog(montlyTransactionSummaryPanel1, "Invalid date format. Please use MM/dd/yyyy.");
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
		dailyTransactionheaderpanel.setBounds(0, 0, 959, 72);
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

		// --- TABLE DATA ---
		String[] columns = {"Date", "Account Id", "Account Name", "Transaction Id", "Amount", "Type"};
		DefaultTableModel tableModel11 = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable dailyTransactionReportTable = new JTable(tableModel11){
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				if (comp instanceof JComponent) {
					Object value = getValueAt(row, column);
					if (value != null) {
						((JComponent) comp).setToolTipText(value.toString());
					} else {
						((JComponent) comp).setToolTipText(null);
					}
				}
				return comp;
			}
		};
		dailyTransactionReportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		dailyTransactionReportTable.setEnabled(true);
		dailyTransactionReportTable.setRowSelectionAllowed(true);
		dailyTransactionReportTable.setColumnSelectionAllowed(false);
		dailyTransactionReportTable.setCellSelectionEnabled(false);
		dailyTransactionReportTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		dailyTransactionReportTable.setRowHeight(30); 
		dailyTransactionReportTable.setFillsViewportHeight(true);

		// Table row striping and highlight
		dailyTransactionReportTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
		loadManageTransactions(tableModel11, customers);

		// Styled header
		JTableHeader header11 = dailyTransactionReportTable.getTableHeader();
		header11.setFont(new Font("Segoe UI", Font.BOLD, 14));
		header11.setBackground(new Color(220, 220, 220));
		header11.setForeground(Color.BLACK);
		header11.setReorderingAllowed(false);
		header11.setResizingAllowed(false);

		// ScrollPane for table
		JScrollPane scrollPane = new JScrollPane(dailyTransactionReportTable);
		scrollPane.setBounds(10, 83, 939, 559);
		dailyTransactionReport1.add(scrollPane);

		// Sort By Label and Dropdown
		JLabel lblSortBy = new JLabel("Sort By:");
		lblSortBy.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSortBy.setBounds(44, 674, 80, 25);
		dailyTransactionReport1.add(lblSortBy);

		JComboBox<String> sortDropdown = new JComboBox<>(new String[]{"Select Option", "Date", "Amount", "Account Name"});
		sortDropdown.setBounds(124, 674, 150, 25);
		dailyTransactionReport1.add(sortDropdown);

		// Create a TableRowSorter for your table model
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel11);
		dailyTransactionReportTable.setRowSorter(sorter);

		// Add sorting behavior to the dropdown
		sortDropdown.addActionListener(e -> {
			String selected = (String) sortDropdown.getSelectedItem();

			if (selected == null) return;

			switch (selected) {
			case "Date":
				// Sort by Date column (column index 0), descending (latest first)
				sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
				break;

			case "Amount":
				sorter.setSortKeys(List.of(new RowSorter.SortKey(4, SortOrder.ASCENDING)));
				break;

			case "Account Name":
				// Sort by Account Name (column index 2), ascending alphabetical
				sorter.setSortKeys(List.of(new RowSorter.SortKey(2, SortOrder.ASCENDING)));
				break;

			default:
				sorter.setSortKeys(null); // No sorting for "Select Option"
				break;
			}

			sorter.sort();
		});

		JButton depositBtn1 = new JButton("Deposits");
		depositBtn1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		depositBtn1.setForeground(Color.WHITE);
		depositBtn1.setBackground(new Color(0, 123, 255));
		depositBtn1.setBounds(314, 720, 120, 45);
		dailyTransactionReport1.add(depositBtn1);

		JButton withdrawalBtn1 = new JButton("Withdrawals");
		withdrawalBtn1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		withdrawalBtn1.setForeground(Color.WHITE);
		withdrawalBtn1.setBackground(new Color(0, 123, 255));
		withdrawalBtn1.setBounds(444, 720, 120, 45);
		dailyTransactionReport1.add(withdrawalBtn1);

		JButton transferBtn1 = new JButton("Transfers");
		transferBtn1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		transferBtn1.setForeground(Color.WHITE);
		transferBtn1.setBackground(new Color(0, 123, 255));
		transferBtn1.setBounds(314, 664, 120, 45);
		dailyTransactionReport1.add(transferBtn1);

		JButton repayBtn = new JButton("Repayments");
		repayBtn.setForeground(Color.WHITE);
		repayBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
		repayBtn.setBackground(new Color(0, 123, 255));
		repayBtn.setBounds(444, 664, 120, 45);
		dailyTransactionReport1.add(repayBtn);

		JButton borrowBtn = new JButton("Borrowed");
		borrowBtn.setForeground(Color.WHITE);
		borrowBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
		borrowBtn.setBackground(new Color(0, 123, 255));
		borrowBtn.setBounds(574, 664, 120, 45);
		dailyTransactionReport1.add(borrowBtn);

		JButton btnRefresh_3 = new JButton("🔄 Refresh");
		btnRefresh_3.setForeground(Color.WHITE);
		btnRefresh_3.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		btnRefresh_3.setBackground(new Color(108, 117, 125));
		btnRefresh_3.setBounds(124, 710, 120, 35);
		dailyTransactionReport1.add(btnRefresh_3);

		JButton btnGenerateDaily = new JButton("📊  Generate Daily Transaction Report");
		btnGenerateDaily.setForeground(Color.WHITE);
		btnGenerateDaily.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		btnGenerateDaily.setFocusPainted(false);
		btnGenerateDaily.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		btnGenerateDaily.setBackground(new Color(33, 150, 243));
		btnGenerateDaily.setBounds(574, 720, 375, 45);
		dailyTransactionReport1.add(btnGenerateDaily);

		JLabel accountLabel_1_1 = new JLabel();
		accountLabel_1_1.setText("👤 Select Account:");
		accountLabel_1_1.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		accountLabel_1_1.setBounds(714, 653, 140, 30);
		dailyTransactionReport1.add(accountLabel_1_1);

		List<String> accountList2 = new ArrayList<>();
		accountList2.add("Select Option");

		for (Account account : customers) {
			String displayText = account.getOwner().getName() + " - "  + account.getAccountNumber() + " - " + account.getAccountType();
			accountList2.add(displayText);
		}

		List<String> accountsOnly2 = accountList2.subList(1, accountList2.size());
		Collections.sort(accountsOnly2);

		JComboBox<String> accountDropdown_1_1 = new JComboBox<String>();
		for (String accountDisplay : accountList2) {
			accountDropdown_1_1.addItem(accountDisplay);
		}
		accountDropdown_1_1.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		accountDropdown_1_1.setBounds(714, 679, 220, 30);
		dailyTransactionReport1.add(accountDropdown_1_1);

		btnGenerateDaily.addActionListener(e -> {
		    int selectedIndex = accountDropdown_1_1.getSelectedIndex();

		    // Create a date picker first (same for both cases)
		    JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
		    JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
		    dateSpinner.setEditor(dateEditor);

		    int option = JOptionPane.showOptionDialog(
		            dailyTransactionReport1,
		            dateSpinner,
		            "🗓️ Select Report Date",
		            JOptionPane.OK_CANCEL_OPTION,
		            JOptionPane.PLAIN_MESSAGE,
		            null, null, null
		    );

		    if (option == JOptionPane.CANCEL_OPTION) return;

		    Date selectedDate = (Date) dateSpinner.getValue();
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    String formattedDate = dateFormat.format(selectedDate);

		    StringBuilder finalReport = new StringBuilder();

		    if (selectedIndex <= 0) {
		        // Generate report for all accounts
		        finalReport.append("📅 DAILY TRANSACTION REPORT FOR ALL ACCOUNTS\n")
		                   .append("Date: ").append(formattedDate).append("\n")
		                   .append("=".repeat(50)).append("\n\n");

		        for (Account acc : customers) {
		            String accountReport = ReportGenerator.generateDailyDepositsAndWithdrawalsReport(acc, formattedDate);
		            if (!accountReport.trim().isEmpty()) {
		                finalReport.append(accountReport)
		                           .append("\n")
		                           .append("-".repeat(50)).append("\n\n");
		            }
		        }

		        if (finalReport.toString().trim().isEmpty()) {
		            finalReport.append("✅ No transactions found for any account on ")
		                       .append(formattedDate).append(".");
		        }

		    } else {
		        // Single account selected
		        String selectedAccountText = (String) accountDropdown_1_1.getSelectedItem();
		        String selectedAccountNumber = selectedAccountText.split(" - ")[1]; // Get account number

		        Account selectedAccount = customers.stream()
		                .filter(acc -> acc.getAccountNumber().equals(selectedAccountNumber))
		                .findFirst()
		                .orElse(null);

		        if (selectedAccount == null) {
		            JOptionPane.showMessageDialog(
		                    dailyTransactionReport1,
		                    "❌ Selected account could not be found.",
		                    "Error",
		                    JOptionPane.ERROR_MESSAGE
		            );
		            return;
		        }

		        finalReport.append(ReportGenerator.generateDailyDepositsAndWithdrawalsReport(selectedAccount, formattedDate));
		    }

		    // Show the report in a scrollable text area
		    JTextArea textArea = new JTextArea(finalReport.toString(), 20, 50);
		    textArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
		    textArea.setEditable(false);
		    textArea.setWrapStyleWord(true);
		    textArea.setLineWrap(true);
		    textArea.setCaretPosition(0);
		    textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		    JScrollPane scrollPane_6 = new JScrollPane(textArea);
		    scrollPane_6.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		    scrollPane_6.setBorder(BorderFactory.createTitledBorder("📋 Daily Transaction Summary"));

		    JOptionPane.showMessageDialog(
		            dailyTransactionReport1,
		            scrollPane_6,
		            "Daily Transaction Report",
		            JOptionPane.INFORMATION_MESSAGE
		    );
		});



		btnRefresh_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadManageTransactions(tableModel11, customers);
				accountDropdown_1_1.removeAllItems(); 
				List<String> accountList3 = new ArrayList<>();
				accountList3.add("Select Option");

				for (Account account : customers) {
					String displayText = account.getOwner().getName() + " - " + account.getAccountNumber() + " - " + account.getAccountType();
					accountList3.add(displayText);
				}

				// Sort alphabetically
				List<String> accountsOnly2 = accountList3.subList(1, accountList3.size());
				Collections.sort(accountsOnly2);
				for (String accountDisplay : accountList3) {
					accountDropdown_1_1.addItem(accountDisplay);  
				}
				
			}
		});

		depositBtn1.addActionListener(e -> {
			loadFilteredTransactions(tableModel11, "Deposit");
		});

		withdrawalBtn1.addActionListener(e -> {
			loadFilteredTransactions(tableModel11, "Withdraw");
		});

		transferBtn1.addActionListener(e -> {
			loadFilteredTransactions(tableModel11, "Transfer from", "Transfer to", "Loan Repayment from");
		});

		repayBtn.addActionListener(e -> {
			loadFilteredTransactions(tableModel11, "Loan Repayment");
		});

		borrowBtn.addActionListener(e -> {
			loadFilteredTransactions(tableModel11, "Loan Disbursement");
		});


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
		manageTransactionheaderpanel1.setBounds(0, 0, 959, 72);
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
		tablePanel.setBounds(10, 214, 939, 534);
		tablePanel.setLayout(new BorderLayout());

		String[] headers = {"Date", "Account Id", "Account Name", "Transaction Id", "Amount", "Type"};
		DefaultTableModel tableModel111 = new DefaultTableModel(headers, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable manageTable = new JTable(tableModel111) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				if (comp instanceof JComponent) {
					Object value = getValueAt(row, column);
					if (value != null) {
						((JComponent) comp).setToolTipText(value.toString());
					} else {
						((JComponent) comp).setToolTipText(null);
					}
				}
				return comp;
			}
		};;
		manageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		manageTable.setEnabled(true);
		manageTable.setRowSelectionAllowed(true);
		manageTable.setColumnSelectionAllowed(false);
		manageTable.setCellSelectionEnabled(false);
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
		loadManageTransactions(tableModel111, customers);


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
		btnCreateTrans.setBounds(60, 105, 160, 45);
		btnCreateTrans.setBackground(new Color(40, 167, 69)); // Green
		btnCreateTrans.setForeground(Color.WHITE);
		manageTransactionPanel1.add(btnCreateTrans);
		btnCreateTrans.addActionListener(e -> {controller.openCreateTransactionDialog(tableModel111);
		loadManageTransactions(tableModel111, customers);
		});

		JButton btnEditTrans = new JButton("✏️ Edit");
		btnEditTrans.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		btnEditTrans.setBounds(230, 105, 120, 45);
		btnEditTrans.setBackground(new Color(255, 193, 7)); // Yellow
		btnEditTrans.setForeground(Color.BLACK);
		manageTransactionPanel1.add(btnEditTrans);
		btnEditTrans.addActionListener(e -> {
			controller.openEditTransactionDialog(tableModel111, manageTable);
		});

		JButton btnRefresh_2 = new JButton("🔄 Refresh");
		btnRefresh_2.setForeground(Color.WHITE);
		btnRefresh_2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		btnRefresh_2.setBackground(new Color(108, 117, 125));
		btnRefresh_2.setBounds(60, 161, 120, 35);
		manageTransactionPanel1.add(btnRefresh_2);

		JButton btnDelete_1 = new JButton("🗑️ Delete");
		btnDelete_1.setForeground(Color.WHITE);
		btnDelete_1.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		btnDelete_1.setBackground(new Color(220, 53, 69));
		btnDelete_1.setBounds(371, 105, 130, 45);
		manageTransactionPanel1.add(btnDelete_1);

		btnDelete_1.addActionListener(e -> controller.openDeleteTransactionDialog(tableModel111, manageTable));


		textField_1 = new JTextField();
		textField_1.setToolTipText("Search by name or ID");
		textField_1.setText("");
		textField_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textField_1.setBounds(527, 105, 220, 45);
		manageTransactionPanel1.add(textField_1);

		JButton btnSearch_1 = new JButton("🔍 Search");
		btnSearch_1.setForeground(Color.WHITE);
		btnSearch_1.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		btnSearch_1.setBackground(new Color(0, 123, 255));
		btnSearch_1.setBounds(757, 105, 115, 45);
		manageTransactionPanel1.add(btnSearch_1);

		btnSearch_1.addActionListener(e -> {
			String keyword = textField_1.getText().trim().toLowerCase();

			if (keyword.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter a keyword to search.", "Empty Search", JOptionPane.WARNING_MESSAGE);
				return;
			}

			DefaultTableModel model1 = (DefaultTableModel) manageTable.getModel();
			TableRowSorter<DefaultTableModel> sorter1 = new TableRowSorter<>(model1);
			manageTable.setRowSorter(sorter1);

			// Filters rows that contain the keyword in *any column*
			RowFilter<DefaultTableModel, Object> filter1 = new RowFilter<>() {
				@Override
				public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry1) {
					for (int i = 0; i < entry1.getValueCount(); i++) {
						Object value1 = entry1.getValue(i);
						if (value1 != null && value1.toString().toLowerCase().contains(keyword)) {
							return true;
						}
					}
					return false;
				}
			};

			sorter1.setRowFilter(filter1);
		});

		btnRefresh_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText("");
				TableRowSorter<?> sorter = (TableRowSorter<?>) manageTable.getRowSorter();
				if (sorter != null) {
					sorter.setRowFilter(null); // Remove the filter
				}
				loadManageTransactions(tableModel111, customers);
			}
		});


		JPanel accountBalanceReportPanel = new JPanel();
		switchPanel.add(accountBalanceReportPanel, "accountBalanceReport");
		accountBalanceReportPanel.setLayout(new CardLayout(0, 0));
		accountBalanceReportPanel.setVisible(false);

		JPanel accountBalanceReportPanel1 = new JPanel();
		accountBalanceReportPanel.add(accountBalanceReportPanel1, "panelView");
		accountBalanceReportPanel1.setLayout(null);

		// --- HEADER ---
		JPanel accountBalanceReportheaderpanel = new JPanel();
		accountBalanceReportheaderpanel.setBounds(0, 0, 959, 72);
		accountBalanceReportheaderpanel.setBackground(new Color(52, 58, 64));
		accountBalanceReportheaderpanel.setPreferredSize(new Dimension(0, 80));
		accountBalanceReportheaderpanel.setLayout(new GridLayout(0, 2, 0, 0));
		accountBalanceReportheaderpanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

		JLabel lblaccountBalanceReporthheader = new JLabel("🏦 Northland Bank");
		lblaccountBalanceReporthheader.setHorizontalAlignment(SwingConstants.CENTER);
		lblaccountBalanceReporthheader.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
		lblaccountBalanceReporthheader.setForeground(Color.WHITE);
		accountBalanceReportheaderpanel.add(lblaccountBalanceReporthheader);
		accountBalanceReportPanel1.add(accountBalanceReportheaderpanel, BorderLayout.NORTH);

		JLabel accountBalanceReportbankIcon = new JLabel(resizedIcon);
		accountBalanceReportbankIcon.setHorizontalAlignment(SwingConstants.RIGHT);
		accountBalanceReportheaderpanel.add(accountBalanceReportbankIcon);

		// Table Panel for Account Balance Report
		JPanel balanceTablePanel = new JPanel();
		balanceTablePanel.setBounds(10, 80, 939, 534);
		balanceTablePanel.setLayout(new BorderLayout());

		// Table Headers
		String[] balanceHeaders = {"Account Number", "Account Holder", "Account Type", "Balance"};
		DefaultTableModel balanceTableModel = new DefaultTableModel(balanceHeaders, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Make cells non-editable
			}
		};

		// Styled Table
		JTable accountBalanceTable = new JTable(balanceTableModel) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				if (comp instanceof JComponent) {
					Object value = getValueAt(row, column);
					((JComponent) comp).setToolTipText(value != null ? value.toString() : null);
				}
				return comp;
			}
		};
		accountBalanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		accountBalanceTable.setEnabled(true);
		accountBalanceTable.setRowSelectionAllowed(true);
		accountBalanceTable.setColumnSelectionAllowed(false);
		accountBalanceTable.setCellSelectionEnabled(false);
		accountBalanceTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		accountBalanceTable.setRowHeight(30); 
		accountBalanceTable.setFillsViewportHeight(true);
		
		accountBalanceTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

		// Table Header Styling
		JTableHeader balanceHeader = accountBalanceTable.getTableHeader();
		balanceHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
		balanceHeader.setBackground(new Color(220, 220, 220));
		balanceHeader.setForeground(Color.BLACK);
		balanceHeader.setReorderingAllowed(false);
		balanceHeader.setResizingAllowed(false);

		// Add Scroll Pane to Table Panel
		JScrollPane balanceScrollPane = new JScrollPane(accountBalanceTable);
		balanceTablePanel.add(balanceScrollPane, BorderLayout.CENTER);

		// Add to your main panel (e.g., accountBalanceReportPanel1)
		accountBalanceReportPanel1.add(balanceTablePanel);

		JLabel accountLabel_1 = new JLabel();
		accountLabel_1.setText("👤 Select Account:");
		accountLabel_1.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		accountLabel_1.setBounds(549, 637, 140, 30);
		accountBalanceReportPanel1.add(accountLabel_1);

		List<String> accountList1 = new ArrayList<>();

		for (Account account : customers) {
			String displayText = account.getOwner().getName() + " - "  + account.getAccountNumber() + " - " + account.getAccountType();
			accountList1.add(displayText);
		}

		List<String> accountsOnly1 = accountList1.subList(1, accountList1.size());
		Collections.sort(accountsOnly1);

		JComboBox<String> accountDropdown_1 = new JComboBox<String>();
		accountDropdown_1.addItem("None");
		for (String accountDisplay : accountList1) {
			accountDropdown_1.addItem(accountDisplay);
		}
		accountDropdown_1.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		accountDropdown_1.setBounds(689, 637, 220, 30);
		accountBalanceReportPanel1.add(accountDropdown_1);

		// Create a panel for the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10)); // 30px gap between buttons
		buttonPanel.setBounds(20, 627, 519, 99); // Adjust bounds depending on your frame/panel
		buttonPanel.setBackground(new Color(240, 240, 240)); // Light neutral background
		accountBalanceReportPanel1.add(buttonPanel);

		// Button styles
		Font buttonFont = new Font("Segoe UI Emoji", Font.BOLD, 14);
		Color primaryColor = new Color(33, 150, 243); // Nice blue
		Color secondaryColor = new Color(255, 87, 34); // Orange

		// High Loan Balance Report Button
		JButton highLoanBalanceBtn = new JButton("💸 High Loan Balance Report");
		highLoanBalanceBtn.setFont(buttonFont);
		highLoanBalanceBtn.setBackground(new Color(76, 175, 80)); // Green
		highLoanBalanceBtn.setForeground(Color.WHITE);
		highLoanBalanceBtn.setFocusPainted(false);
		highLoanBalanceBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		highLoanBalanceBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Hover effect
		highLoanBalanceBtn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				highLoanBalanceBtn.setBackground(new Color(56, 142, 60)); // Darker green
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				highLoanBalanceBtn.setBackground(new Color(76, 175, 80));
			}
		});

		// Add the new button to the panel
		buttonPanel.add(highLoanBalanceBtn);


		// Generate Balance Report Button
		JButton generateBalanceReportBtn = new JButton("📊  Generate Balance Report");
		generateBalanceReportBtn.setFont(buttonFont);
		generateBalanceReportBtn.setBackground(primaryColor);
		generateBalanceReportBtn.setForeground(Color.WHITE);
		generateBalanceReportBtn.setFocusPainted(false);
		generateBalanceReportBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		generateBalanceReportBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Add hover effects
		generateBalanceReportBtn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				generateBalanceReportBtn.setBackground(primaryColor.darker());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				generateBalanceReportBtn.setBackground(primaryColor);
			}
		});

		// Low Balance Report Button
		JButton lowBalanceAlertBtn = new JButton("⚠️  Low Balance Report");
		lowBalanceAlertBtn.setFont(buttonFont);
		lowBalanceAlertBtn.setBackground(secondaryColor);
		lowBalanceAlertBtn.setForeground(Color.WHITE);
		lowBalanceAlertBtn.setFocusPainted(false);
		lowBalanceAlertBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		lowBalanceAlertBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		lowBalanceAlertBtn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				lowBalanceAlertBtn.setBackground(secondaryColor.darker());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				lowBalanceAlertBtn.setBackground(secondaryColor);
			}
		});
		buttonPanel.add(lowBalanceAlertBtn);

		// Low Balance Alert
		lowBalanceAlertBtn.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(
					accountBalanceReportPanel1,
					"Enter balance threshold:",
					"🔻 Low Balance Threshold",
					JOptionPane.PLAIN_MESSAGE
					);
			if (input == null) return;

			double threshold;
			try {
				threshold = Double.parseDouble(input);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(accountBalanceReportPanel1, "❌ Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String selectedAccountName = (String) accountDropdown_1.getSelectedItem();
			List<Account> allAccounts = BankLedger.getInstance().getBank().getAllAccounts();
			List<Account> filteredAccounts = allAccounts.stream()
					.filter(acc -> !(acc instanceof LoanAccount))
					.collect(Collectors.toList());

			if (selectedAccountName != null && !selectedAccountName.equals("None")) {
				filteredAccounts = filteredAccounts.stream()
						.filter(acc -> {
							String label = acc.getOwner().getName() + " - " + acc.getAccountNumber() + " - " + acc.getAccountType();
							return label.equals(selectedAccountName);
						})
						.collect(Collectors.toList());
			}

			String report = ReportGenerator.generateLowBalanceAlertReport(filteredAccounts, threshold);
			displayReport("Low Balance Alert Report", "📋 Accounts Below Threshold", report, JOptionPane.WARNING_MESSAGE, accountBalanceReportPanel1);
		});


		// Add buttons to panel
		buttonPanel.add(generateBalanceReportBtn);


		// Generate Full Balance Report
		generateBalanceReportBtn.addActionListener(e -> {
			String selectedAccountName = (String) accountDropdown_1.getSelectedItem();
			List<Account> allAccounts = BankLedger.getInstance().getBank().getAllAccounts();

			StringBuilder report = new StringBuilder("📊 ACCOUNT BALANCE REPORT 📊\n\n");

			if (selectedAccountName != null && !selectedAccountName.equals("None")) {
				for (Account account : allAccounts) {
					String fullLabel = account.getOwner().getName() + " - " + account.getAccountNumber() + " - " + account.getAccountType();
					if (fullLabel.equals(selectedAccountName)) {
						report.append(ReportGenerator.generateAccountBalanceReport(account)).append("\n--------------------------------------------------\n");
						break;
					}
				}
			} else {
				for (Account account : allAccounts) {
					report.append(ReportGenerator.generateAccountBalanceReport(account)).append("\n--------------------------------------------------\n");
				}
			}

			displayReport("Full Account Balance Report", "Account Details", report.toString(), JOptionPane.INFORMATION_MESSAGE, accountBalanceReportPanel1);
		});


		// High Loan Balance Alert
		highLoanBalanceBtn.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(
					accountBalanceReportPanel1,
					"Enter loan balance threshold:",
					"💸 High Loan Balance Threshold",
					JOptionPane.PLAIN_MESSAGE
					);
			if (input == null) return;

			double threshold;
			try {
				threshold = Double.parseDouble(input);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(accountBalanceReportPanel1, "❌ Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String selectedAccountName = (String) accountDropdown_1.getSelectedItem();
			List<Account> allAccounts = BankLedger.getInstance().getBank().getAllAccounts();
			List<Account> loanAccounts = allAccounts.stream()
					.filter(acc -> acc instanceof LoanAccount)
					.collect(Collectors.toList());

			if (selectedAccountName != null && !selectedAccountName.equals("None")) {
				loanAccounts = loanAccounts.stream()
						.filter(acc -> {
							String label = acc.getOwner().getName() + " - " + acc.getAccountNumber() + " - " + acc.getAccountType();
							return label.equals(selectedAccountName);
						})
						.collect(Collectors.toList());
			}

			String report = ReportGenerator.generateHighLoanBalanceReport(loanAccounts, threshold);
			displayReport("High Loan Balance Report", "📋 High Loan Accounts", report, JOptionPane.WARNING_MESSAGE, accountBalanceReportPanel1);
		});


		JButton btnRefresh_4 = new JButton("🔄 Refresh");
		btnRefresh_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshAccountBalanceTable(balanceTableModel);
				accountDropdown_1.removeAllItems(); 
				List<String> accountList4 = new ArrayList<>();
				accountList4.add("None");

				for (Account account : customers) {
					String displayText = account.getOwner().getName() + " - " + account.getAccountNumber() + " - " + account.getAccountType();
					accountList4.add(displayText);
				}

				// Sort alphabetically
				List<String> accountsOnly2 = accountList4.subList(1, accountList4.size());
				Collections.sort(accountsOnly2);
				for (String accountDisplay : accountList4) {
					accountDropdown_1.addItem(accountDisplay);  
				}
			}
		});
		btnRefresh_4.setForeground(Color.WHITE);
		btnRefresh_4.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		btnRefresh_4.setBackground(new Color(108, 117, 125));
		btnRefresh_4.setBounds(549, 678, 120, 35);
		accountBalanceReportPanel1.add(btnRefresh_4);

		refreshAccountBalanceTable(balanceTableModel);


		//Switching panel
		btnmanageAccount.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				accountBalanceReportPanel.setVisible(false);
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
				accountBalanceReportPanel.setVisible(false);
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
				accountBalanceReportPanel.setVisible(false);
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
				accountBalanceReportPanel.setVisible(false);
				montlyTransactionSummaryPanel.setVisible(false);
				manageTransactionPanel.setVisible(true);
				dailyTransactionReportPanel.setVisible(false);
				manageAccountsPanel.setVisible(false);
				reportsVisible = false;
				reportsPanel.setVisible(reportsVisible);
				reportsToggle.setText(reportsVisible ? "Reports ▲" : "Reports ▼");
			}
		});

		balanceBtn.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				accountBalanceReportPanel.setVisible(true);
				montlyTransactionSummaryPanel.setVisible(false);
				manageTransactionPanel.setVisible(false);
				dailyTransactionReportPanel.setVisible(false);
				manageAccountsPanel.setVisible(false);
				reportsVisible = false;
				reportsPanel.setVisible(reportsVisible);
				reportsToggle.setText(reportsVisible ? "Reports ▲" : "Reports ▼");
			}
		});

	}

	private void displayReport(String dialogTitle, String borderTitle, String reportContent, int messageType, JPanel balanceReport) {
		JTextArea textArea = new JTextArea(reportContent, 25, 60);
		textArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 11));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setCaretPosition(0);
		textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createTitledBorder(borderTitle));

		JOptionPane.showMessageDialog(
				balanceReport,
				scrollPane,
				dialogTitle,
				messageType
				);
	}


	private void refreshTransactionsTable(DefaultTableModel tableModel1) {
		tableModel1.setRowCount(0); // Clear existing rows
		Bank bank = BankLedger.getInstance().getBank();
		List<Account> customers =  bank.getAllAccounts();
		for (Account account : customers) {
			java.util.List<Transaction> transactions = account.getHistory().getHistoryList();

			for (Transaction transaction : transactions) {
				String date = transaction.getDate().toString();
				String iD = transaction.getId();
				String type = transaction.getAction();
				String amount = String.format("₱%.2f", transaction.getAmount());
				String recipient = account.getOwner().getName();

				double balance;
				if (account instanceof LoanAccount) {
					balance = ((LoanAccount) account).getLoanBalance();
				} else {
					balance = account.getBalance();
				}

				// Adjust for transfer descriptions
				if (type.startsWith("Transfer to ")) {
					recipient = type.substring(12);
					type = "Transfer Sent";
				} else if (type.startsWith("Transfer from ")) {
					recipient = type.substring(14);
					type = "Transfer Received";
				}

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
	}

	public void refreshAccountBalanceTable(DefaultTableModel tableModel) {
		Bank bank = BankLedger.getInstance().getBank();
		List<Account> allAccounts = bank.getAllAccounts();
		tableModel.setRowCount(0);
		for (Account acc : allAccounts) {
			String accountNumber = acc.getAccountNumber();
			String accountHolder = acc.getOwner().getName();
			String accountType = acc.getAccountType(); 

			double balance = (acc instanceof LoanAccount) 
					? ((LoanAccount) acc).getLoanBalance() 
							: acc.getBalance();

			tableModel.addRow(new Object[] {
					accountNumber,
					accountHolder,
					accountType,
					String.format("₱%.2f", balance)
			});
		}
	}


	public void updateAccountsTable(DefaultTableModel tableModel) {
		Bank bank = BankLedger.getInstance().getBank();
		List<Account> customers =  bank.getAllAccounts();
		tableModel.setRowCount(0); // Clear existing rows

		for (Account account : customers) {
			String accountNumber = account.getAccountNumber();
			String name = account.getOwner().getName();
			String email = account.getOwner().getEmail();
			String contact = account.getOwner().getContactNumber();
			String accountType = account.getAccountType();
			String password = account.getOwner().getPassword();
			// Add row to table
			tableModel.addRow(new Object[]{accountNumber, name, email, contact, accountType, password, "Generate"});
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

	public void loadManageTransactions(DefaultTableModel tableModel111, List<Account> customers) {
		tableModel111.setRowCount(0); // Clear existing rows

		for (Account account : customers) {
			List<Transaction> transactions = account.getHistory().getHistoryList();

			for (Transaction transaction : transactions) {
				String date = transaction.getDate().toString();
				String transactionid = transaction.getId();
				String accountid = account.getAccountNumber();
				String name = account.getOwner().getName();
				String amount = String.format("₱%.2f", transaction.getAmount());
				String status = transaction.getAction();

				// Normalize transfer types
				if (status.startsWith("Transfer to ")) {
					status = "Transfer Sent";
				} else if (status.startsWith("Transfer from ")) {
					status = "Transfer Received";
				}

				tableModel111.addRow(new Object[]{
						date, accountid, name, transactionid, amount, status
				});
			}
		}
	}

	public void loadFilteredTransactions(DefaultTableModel model, String... filterStatuses) {
		model.setRowCount(0); // Clear table

		for (Account account : customers) {
			for (Transaction t : account.getHistory().getHistoryList()) {
				for (String filter : filterStatuses) {
					if (t.getAction().startsWith(filter)) { 
						model.addRow(new Object[]{
								t.getDate().toString(),
								account.getAccountNumber(),
								account.getOwner().getName(),
								t.getId(),
								"₱" + String.format("%.2f", t.getAmount()),
								t.getAction()
						});
						break; // No need to check other filters
					}
				}
			}
		}
	}
}
