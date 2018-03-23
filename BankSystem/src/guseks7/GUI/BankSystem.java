package guseks7.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.*;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import guseks7.logic.BankLogic;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class BankSystem extends JFrame {

    private BankLogic myBank;
    private static final int FRAME_WIDTH = 1400;
    private static final int FRAME_HEIGHT = 700;

    private JButton createSavings;
    private JButton createCredit;
    private JButton createCustomer;
    private JButton deleteCustomer;
    private JButton changeName;
    private JButton deleteAccount;
    private JButton getTransactions;
    private JButton deposit;
    private JButton withdraw;

    private JTable customerTable;
    private JTable accountTable;
    private JList transactionsList;
    //private JTextArea transactionsList;
    private JComboBox chooseCustomer;
    private JTextField workflow;

    public BankSystem() {
        myBank = new BankLogic();
        myBank.createCustomer("Gustaf", "Ekström", "9306025595");
        myBank.createCustomer("Marcus", "Ekström", "9306026197");
        createControlPanel();
        //updateSystem();
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("A digital Bank");
        setVisible(true);
        
    }

    private void createControlPanel() {
        JPanel inputPanel = createInputPanel();
        JPanel displayPanel = createDisplayPanel();
        JPanel bottomPanel = createBottomPanel();
        setLayout(new GridBagLayout());

        GridBagConstraints inputPanelConfig = new GridBagConstraints();
        setConstraints(inputPanelConfig, 0, 0, 3, 1, "both", new Insets(0, 0, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        inputPanel.setPreferredSize(new Dimension(600, 150));
        add(inputPanel, inputPanelConfig);
        GridBagConstraints displayPanelConfig = new GridBagConstraints();
        setConstraints(displayPanelConfig, 0, 1, 2, 3, "both", new Insets(0, 0, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        displayPanel.setPreferredSize(new Dimension(1300, 400));
        add(displayPanel, displayPanelConfig);
        /*
        GridBagConstraints bottomPanelConfig = new GridBagConstraints();
        setConstraints(bottomPanelConfig, 3, 2, 1, 1, "both", new Insets(0, 50, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        bottomPanel.setPreferredSize(new Dimension(400, 200));
        add(bottomPanel, bottomPanelConfig);
        */
    }
    
    private JPanel createBottomPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel transactionsListWrapper = new JPanel(new BorderLayout());
        transactionsListWrapper.add(transactionsList, BorderLayout.CENTER);
        transactionsListWrapper.setBorder(new TitledBorder("Made Transactions on chosen account"));
        
        GridBagConstraints transactionsListWrapperConfig = new GridBagConstraints();
        setConstraints(transactionsListWrapperConfig, 2, 0, 1, 1, "both", new Insets(0, 0, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        panel.add(transactionsListWrapper, transactionsListWrapperConfig);
        
        return panel;
    }
    
    private JPanel createInputPanel() {
        createButtons();
        JPanel panel = new JPanel(new BorderLayout());
        //panel.setPreferredSize(new Dimension(400,200));
        JPanel customerOperationsWrapper = new JPanel(new GridBagLayout());
        JPanel accountOperationsWrapper = new JPanel(new GridBagLayout());

        GridBagConstraints createCustomerConfig = new GridBagConstraints();
        setConstraints(createCustomerConfig, 0, 0, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);

        GridBagConstraints deleteCustomerConfig = new GridBagConstraints();
        setConstraints(deleteCustomerConfig, 1, 0, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);

        GridBagConstraints changeNameConfig = new GridBagConstraints();
        setConstraints(changeNameConfig, 2, 0, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);

        GridBagConstraints createSavingsConfig = new GridBagConstraints();
        setConstraints(createSavingsConfig, 0, 1, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        GridBagConstraints createCreditConfig = new GridBagConstraints();
        setConstraints(createCreditConfig, 1, 1, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);

        customerOperationsWrapper.add(createCustomer, createCustomerConfig);
        customerOperationsWrapper.add(deleteCustomer, deleteCustomerConfig);
        customerOperationsWrapper.add(changeName, changeNameConfig);
        customerOperationsWrapper.add(createSavings, createSavingsConfig);
        customerOperationsWrapper.add(createCredit, createCreditConfig);

        GridBagConstraints customerOperationsWrapperConfig = new GridBagConstraints();
        setConstraints(customerOperationsWrapperConfig, 0, 0, 1, 1, "None", new Insets(0, 0, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        //customerOperationsWrapper.setBorder(new TitledBorder("Mark Customer in List and choose desired operation"));

        /*
            JLabel custOperationsInstructions = new JLabel("Mark desired customer in the list below and choose desired operation");
            GridBagConstraints custOperationsInstructionsConfig = new GridBagConstraints();
            setConstraints(custOperationsInstructionsConfig, 0, 1, 1, 1, "None", new Insets(10, 5, 0, 0), FIRST_LINE_START);
            customerOperationsWrapper.add(custOperationsInstructions, custOperationsInstructionsConfig);
         */
        //panel.add(customerOperationsWrapper, customerOperationsWrapperConfig);
        panel.add(customerOperationsWrapper, BorderLayout.WEST);

        GridBagConstraints accountOperationsWrapperConfig = new GridBagConstraints();
        setConstraints(accountOperationsWrapperConfig, 1, 0, 1, 1, "None", new Insets(0, 20, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        //accountOperationsWrapper.setBorder(new TitledBorder("Mark desired account in the List and choose desired operation"));
        //panel.add(accountOperationsWrapper, accountOperationsWrapperConfig);
        return panel;
    }

    private JPanel createDisplayPanel() {
        createDisplay();
        JPanel panel = new JPanel(new BorderLayout());

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBorder(new TitledBorder("Mark the desired Customer and choose operation"));
        tableWrapper.setPreferredSize(new Dimension(400, 100));
        GridBagConstraints customerTableConfig = new GridBagConstraints();
        setConstraints(customerTableConfig, 1, 1, 3, 3, "both", new Insets(0, 0, 0, 0), FIRST_LINE_START, 1, 1);
        tableWrapper.add(new JScrollPane(customerTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);

        GridBagConstraints tableWrapperConfig = new GridBagConstraints();
        setConstraints(tableWrapperConfig, 0, 0, 1, 1, "both", new Insets(0, 20, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        panel.add(tableWrapper, BorderLayout.WEST);

        JPanel accountOperationsWrapper = new JPanel(new GridBagLayout());
        

        //accountListWrapper.setBorder(new TitledBorder("Choose desired account and operation"));
        JPanel chooseCustomerWrapper = new JPanel();
        chooseCustomerWrapper.setBorder(new TitledBorder("Choose customer to display Accounts"));
        chooseCustomerWrapper.add(chooseCustomer);

        JPanel accountTableWrapper = new JPanel(new BorderLayout());
        accountTableWrapper.setPreferredSize(new Dimension(500, 200));
        accountTableWrapper.setBorder(new TitledBorder("Choose desired account and operation"));
        JScrollPane accountTableScroll = new JScrollPane();
        accountTableScroll.setPreferredSize(new Dimension(500,100));
        //accountTable.setPreferredSize(new Dimension(450,150));
        //JScrollPane accountTableScroll = new JScrollPane(accountTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
        accountTableWrapper.add(new JScrollPane(accountTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        
        GridBagConstraints chooseCustomerWrapperConfig = new GridBagConstraints();
        setConstraints(chooseCustomerWrapperConfig, 0, 0, 1, 1, "both", new Insets(0, 0, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        //accountOperationsWrapper.add(chooseCustomerWrapper, chooseCustomerWrapperConfig);
        GridBagConstraints accountTableWrapperConfig = new GridBagConstraints();
        setConstraints(accountTableWrapperConfig, 0, 1, 1, 1, "both", new Insets(5, 0, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        accountOperationsWrapper.add(accountTableWrapper, accountTableWrapperConfig);

        JPanel OperationsWrapper = new JPanel(new GridBagLayout());

        GridBagConstraints deleteAccountConfig = new GridBagConstraints();
        setConstraints(deleteAccountConfig, 0, 0, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        GridBagConstraints getTransactionsConfig = new GridBagConstraints();
        setConstraints(getTransactionsConfig, 1, 0, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        GridBagConstraints depositConfig = new GridBagConstraints();
        setConstraints(depositConfig, 0, 1, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        GridBagConstraints withdrawConfig = new GridBagConstraints();
        setConstraints(withdrawConfig, 1, 1, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);

        OperationsWrapper.add(deleteAccount, deleteAccountConfig);
        OperationsWrapper.add(getTransactions, getTransactionsConfig);
        OperationsWrapper.add(deposit, depositConfig);
        OperationsWrapper.add(withdraw, withdrawConfig);
        
        GridBagConstraints OperationsWrapperConfig = new GridBagConstraints();
        setConstraints(OperationsWrapperConfig, 0, 2, 1, 1, "both", new Insets(20, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        accountOperationsWrapper.add(OperationsWrapper, OperationsWrapperConfig);
        GridBagConstraints accountListWrapperConfig = new GridBagConstraints();
        setConstraints(accountListWrapperConfig, 1, 0, 1, 1, "both", new Insets(0, 40, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        panel.add(accountOperationsWrapper, BorderLayout.CENTER);
        
        JPanel transactionsListWrapper = new JPanel(new BorderLayout());
        JScrollPane transactionsScroll = new JScrollPane(transactionsList);
        transactionsScroll.setPreferredSize(new Dimension(300,100));
        transactionsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //transactionsListWrapper.add(transactionsList, BorderLayout.CENTER);
        //transactionsListWrapper.add(transactionsList, BorderLayout.CENTER);
        transactionsListWrapper.add(transactionsScroll, BorderLayout.CENTER);
        transactionsListWrapper.setBorder(new TitledBorder("Made Transactions on chosen account"));
        //transactionsListWrapper.setPreferredSize(new Dimension(360, 100));
        
        GridBagConstraints transactionsListWrapperConfig = new GridBagConstraints();
        setConstraints(transactionsListWrapperConfig, 2, 0, 1, 1, "both", new Insets(0, 0, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        panel.add(transactionsListWrapper, BorderLayout.EAST);

        return panel;
    }

    private void createDisplay() {
        chooseCustomer = new JComboBox();
        ArrayList<String> myCustomers = myBank.getAllCustomers();
        DefaultTableModel myTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        myTableModel.addColumn("First Name:");
        myTableModel.addColumn("Last Name:");
        myTableModel.addColumn("Personal Number:");

        for (String info : myCustomers) {
            Vector<String> displayInfo = new Vector<>();
            String[] temp = info.split(" ");
            myTableModel.addRow(temp);
            chooseCustomer.addItem(info);

        }

        customerTable = new JTable(myTableModel);
        ListSelectionModel myCustomerSelectionModel = customerTable.getSelectionModel();
        myCustomerSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //customerTable.setPreferredSize(new Dimension(500, 200));
        DefaultTableModel myAccountTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        
        myAccountTableModel.addColumn("Account ID:");
        myAccountTableModel.addColumn("Saldo:");
        myAccountTableModel.addColumn("Account Type:");
        myAccountTableModel.addColumn("Interest:");
        accountTable = new JTable(myAccountTableModel);
        ListSelectionModel myAccountSelectionModel = accountTable.getSelectionModel();
        myAccountSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //accountTable.setPreferredSize(new Dimension(400, 150));
        
        customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                DefaultTableModel myTableModel = (DefaultTableModel) customerTable.getModel();
                DefaultTableModel myAccountTableModel = (DefaultTableModel)accountTable.getModel();
                int selectedRow = customerTable.getSelectedRow();
                if(selectedRow != -1){
                    String personalNumber = (String)myTableModel.getValueAt(selectedRow, 2);
                    //accountTable.removeAll();
                
                    while(myAccountTableModel.getRowCount() > 0){
                        for(int i = 0; i < myAccountTableModel.getRowCount(); i++){
                        myAccountTableModel.removeRow(i);
                        accountTable.repaint();
                        }
                    }
                    ArrayList<String> accountNumbers = myBank.getAccountNrs((personalNumber));
                    for(String accountID : accountNumbers){
                        myAccountTableModel.addRow(myBank.getAccount(personalNumber, Integer.parseInt(accountID)).split(" "));
                    }
                }
                else {
                    
                }
                
            }
        });
        /*
        accountTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                DefaultTableModel myTableModel = (DefaultTableModel) customerTable.getModel();
                DefaultTableModel myAccountTableModel = (DefaultTableModel)accountTable.getModel();
                DefaultListModel myTransactionListModel = (DefaultListModel)transactionsList.getModel();
                int selectedRow = accountTable.getSelectedRow();
                int selectedCustomer = customerTable.getSelectedRow();
                String personalNumber = (String)myTableModel.getValueAt(selectedCustomer, 2);
                //accountTable.removeAll();
                
                myTransactionListModel.removeAllElements();
                
                int selectedAccount = Integer.parseInt((String)myAccountTableModel.getValueAt(selectedRow, 0));
                
                
                
                ArrayList<String> madeTransactions = myBank.getTransactions(personalNumber, selectedAccount);
                for(String info : madeTransactions){
                    myTransactionListModel.addElement(info);
                }
                transactionsList.repaint();
            }
        });
        */
        /*
        DefaultListModel myTransactionListModel = new DefaultListModel();
        myTransactionListModel.addElement((String)"Hej");
        transactionsList = new JList(myTransactionListModel);
        transactionsList.setPreferredSize(new Dimension(300,100));
        */
        DefaultListModel myTransactionListModel = new DefaultListModel();
        transactionsList = new JList(myTransactionListModel);
        //transactionsList.setSize(new Dimension(100, 100));
        transactionsList.setPreferredSize(new Dimension(200,100));
        //transactionsList.append("Hallå");
        //transactionsList.append("hur mår du\n");
    }

    private void createButtons() {
        createSavings = new JButton("Create new SavingsAccount");
        createCredit = new JButton("Create new CreditAccount");
        createCustomer = new JButton("Create new Customer");
        deleteCustomer = new JButton("Delete Customer");
        changeName = new JButton("Change customers name");
        deposit = new JButton("Make deposit");
        withdraw = new JButton("Make withdrawal");
        getTransactions = new JButton("Get Transactions");
        deleteAccount = new JButton("Delete account");

        createCustomer.setPreferredSize(new Dimension(200, 30));
        deleteCustomer.setPreferredSize(new Dimension(200, 30));
        changeName.setPreferredSize(new Dimension(200, 30));
        createSavings.setPreferredSize(new Dimension(200, 30));
        createCredit.setPreferredSize(new Dimension(200, 30));
        deleteAccount.setPreferredSize(new Dimension(200, 30));
        getTransactions.setPreferredSize(new Dimension(200, 30));
        deposit.setPreferredSize(new Dimension(200, 30));
        withdraw.setPreferredSize(new Dimension(200, 30));
        
       addButtonFunctions();

    }

    private void addButtonFunctions(){
        createSavings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                DefaultTableModel myTableModel = (DefaultTableModel) customerTable.getModel();
                int selectedRow = customerTable.getSelectedRow();
                
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Customer Selected", "", JOptionPane.PLAIN_MESSAGE);
                } else {
                    
                    String personalNumber = (String) myTableModel.getValueAt(selectedRow, 2);
                    int accountID = myBank.createSavingsAccount(personalNumber);
                    //System.out.println(myBank.getCustomer((String) myTableModel.getValueAt(selectedRow, 2)));
                    DefaultTableModel myAccountTableModel = (DefaultTableModel)accountTable.getModel();
                    myAccountTableModel.addRow(myBank.getAccount(personalNumber, accountID).split(" "));
                }

            }
        });
        createCredit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                DefaultTableModel myTableModel = (DefaultTableModel) customerTable.getModel();
                int selectedRow = customerTable.getSelectedRow();
                
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Customer Selected", "", JOptionPane.PLAIN_MESSAGE);
                } else {
                    String personalNumber = (String) myTableModel.getValueAt(selectedRow, 2);
                    int accountID = myBank.createCreditAccount(personalNumber);
                    //System.out.println(myBank.getCustomer((String) myTableModel.getValueAt(selectedRow, 2)));
                    String selectedItem = (String)chooseCustomer.getSelectedItem();
                    String selectedPersonalNumber = selectedItem.split(" ")[2];
                    if(personalNumber.equals(selectedPersonalNumber)){
                        DefaultTableModel myAccountTableModel = (DefaultTableModel)accountTable.getModel();
                        myAccountTableModel.addRow(myBank.getAccount(personalNumber, accountID).split(" "));
                    }
                    
                }

            }
        });
        createCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JTextField firstName = new JTextField();
                JTextField lastName = new JTextField();
                JTextField personalNumber = new JTextField();
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("First Name:"));
                panel.add(firstName);
                panel.add(new JLabel("Last Name:"));
                panel.add(lastName);
                panel.add(new JLabel("Personal Number"));
                panel.add(personalNumber);
                String[] customerData = new String[3];
                int result = JOptionPane.showConfirmDialog(null, panel, "Add Loot",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
                if (result == JOptionPane.OK_OPTION) {
                    customerData[0] = firstName.getText();
                    customerData[1] = lastName.getText();
                    customerData[2] = personalNumber.getText();
                    if (!customerData[0].isEmpty() && !customerData[1].isEmpty() && !customerData[2].isEmpty()) {
                        myBank.createCustomer(customerData[0], customerData[1], customerData[2]);
                        DefaultTableModel myTableModel = (DefaultTableModel) customerTable.getModel();
                        myTableModel.addRow(customerData);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Data", "", JOptionPane.WARNING_MESSAGE);

                    }
                }
                
            }
        });
        deleteCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                DefaultTableModel myTableModel = (DefaultTableModel) customerTable.getModel();
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Customer Selected", "", JOptionPane.PLAIN_MESSAGE);
                } else {
                    myBank.deleteCustomer((String) myTableModel.getValueAt(selectedRow, 2));
                    myTableModel.removeRow(selectedRow);
                    while (accountTable.getRowCount()> 0){
                        ((DefaultTableModel)accountTable.getModel()).removeRow(0);
                    }
                    accountTable.repaint();
                    
                    
                }

            }
        });
        changeName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                int selectedRow = customerTable.getSelectedRow();
                JTextField firstName = new JTextField();
                JTextField lastName = new JTextField();
                JTextField personalNumber = new JTextField();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Customer Selected", "", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JPanel panel = new JPanel(new GridLayout(0, 1));
                    panel.add(new JLabel("First Name:"));
                    panel.add(firstName);
                    panel.add(new JLabel("Last Name:"));
                    panel.add(lastName);
                    panel.setPreferredSize(new Dimension(300, 120));

                    int result = JOptionPane.showConfirmDialog(null, panel, "Change a Customers name",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        updateCustomer(firstName.getText(), lastName.getText(), selectedRow);

                    }

                }
            }
        });
        
        deleteAccount.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                DefaultTableModel myAccountTableModel = (DefaultTableModel) accountTable.getModel();
                DefaultTableModel myCustomerTableModel = (DefaultTableModel) customerTable.getModel();
                int selectedRow = accountTable.getSelectedRow();
                int selectedCustomer = customerTable.getSelectedRow();

                String personalNumber = (String)myCustomerTableModel.getValueAt(selectedCustomer, 2);
                
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Account Selected", "", JOptionPane.PLAIN_MESSAGE);
                } else {
                    myBank.closeAccount(personalNumber, Integer.parseInt((String)myAccountTableModel.getValueAt(selectedRow, 0)));
                    System.out.println(myBank.getCustomer(personalNumber));
                    myAccountTableModel.removeRow(selectedRow);
                    accountTable.repaint();
                }

            }
        });
        
        getTransactions.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                DefaultTableModel myAccountTableModel = (DefaultTableModel) accountTable.getModel();
                DefaultTableModel myCustomerTableModel = (DefaultTableModel) customerTable.getModel();
                DefaultListModel myListModel = (DefaultListModel)transactionsList.getModel();
                int selectedRow = accountTable.getSelectedRow();
                int selectedCustomer = customerTable.getSelectedRow();
                String personalNumber = (String)myCustomerTableModel.getValueAt(selectedCustomer, 2);
                int accountID = Integer.parseInt((String)myAccountTableModel.getValueAt(selectedRow, 0));
                
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Account Selected", "", JOptionPane.PLAIN_MESSAGE);
                } else {
                    ArrayList<String> madeTransactions = myBank.getTransactions(personalNumber, accountID);
                    
                    if(madeTransactions.isEmpty()){
                        JOptionPane.showMessageDialog(null, "No Transactions made for current account", "", JOptionPane.PLAIN_MESSAGE);
                    }
                    else {
                            myListModel.removeAllElements();
                        for (String info : madeTransactions){
                            System.out.println(info);
                            myListModel.addElement(info);
                            
                        }
                    }
                    
                }
            }
        });
        
        deposit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                
                DefaultTableModel myAccountTableModel = (DefaultTableModel) accountTable.getModel();
                DefaultTableModel myCustomerTableModel = (DefaultTableModel) customerTable.getModel();
                int selectedCustomer = customerTable.getSelectedRow();
                int selectedRow = accountTable.getSelectedRow();
                
                String personalNumber = (String)myCustomerTableModel.getValueAt(selectedCustomer, 2);
               
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Account Selected", "", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                int accountID = Integer.parseInt((String)myAccountTableModel.getValueAt(selectedRow, 0));
                double amount = getInput("deposit");
                if(amount != -1){
                   
                    myBank.deposit(personalNumber, accountID, amount);
                    myAccountTableModel.removeRow(selectedRow);
                    String [] accountInfo = myBank.getAccount(personalNumber, accountID).split(" ");
                    myAccountTableModel.addRow(accountInfo);
                }
                
            }
            
        });
        
        withdraw.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                DefaultTableModel myAccountTableModel = (DefaultTableModel) accountTable.getModel();
                DefaultTableModel myCustomerTableModel = (DefaultTableModel) customerTable.getModel();
                int selectedCustomer = customerTable.getSelectedRow();
                int selectedRow = accountTable.getSelectedRow();
                
                String personalNumber = (String)myCustomerTableModel.getValueAt(selectedCustomer, 2);
                //System.out.println(personalNumber);
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Account Selected", "", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                int accountID = Integer.parseInt((String)myAccountTableModel.getValueAt(selectedRow, 0));
                double saldo = Double.parseDouble((String)myAccountTableModel.getValueAt(selectedRow, 1));
                boolean done = false;
                while (!done){
                    double amount = getInput("withdraw");
                    if(amount != -1 && myBank.withdraw(personalNumber, accountID, amount)){   
                    
                        myAccountTableModel.removeRow(selectedRow);
                        String [] accountInfo = myBank.getAccount(personalNumber, accountID).split(" ");
                        myAccountTableModel.addRow(accountInfo);
                        done = true;
                    }
                    else if(amount == 0){
                        return;
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Not enough Money in account", "", JOptionPane.ERROR_MESSAGE);
                        
                    }
                }
                
                    
                    
                
            }
        });
    
    }
    
    private boolean validateInput(JTextField amount){
        try{
            double value = Double.parseDouble(amount.getText());
            if(Double.compare(value, 0) < 0){
                return false;
            }
            else{
                return true;
            }
        }
        catch (Exception e){
            return false;
        }
    }
    
    private double getInput(String type){
        String label;
        String title;
        if(type.equals("deposit")){
            label = "Amount to deposit:";
            title = "Make Deposit";
        }
        else {
            label = "Amount to withdraw:";
            title = "Make Withdrawal";
        }
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField amount = new JTextField();
        panel.add(new JLabel(label));
        panel.add(amount);
        
        
        int result = JOptionPane.showConfirmDialog(null, panel, title,
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if(validateInput(amount)){
                return Double.parseDouble(amount.getText());
            }
            else {
                JOptionPane.showMessageDialog(null, "Invalid amount", "", JOptionPane.WARNING_MESSAGE);
                return getInput(type);
            }
        }
        else if(result == JOptionPane.CANCEL_OPTION){
            return 0;
        }
        else{
            return -1;
        }
    }
    
    private void updateCustomer(String newFirstName, String newLastName, int selectedRow) {
        DefaultTableModel myTableModel = (DefaultTableModel) customerTable.getModel();
        if (!newFirstName.isEmpty() && !newLastName.isEmpty()) {
            myBank.changeCustomerName(newFirstName, newLastName, (String) myTableModel.getValueAt(selectedRow, 2));
            myTableModel.setValueAt(newFirstName, selectedRow, 0);
            myTableModel.setValueAt(newLastName, selectedRow, 1);
        }
    }

    private void setConstraints(GridBagConstraints c, int gridx, int gridy, int gridwidth, int gridheight, String fillConfig, Insets padding, int pos, double weightx, double weighty) {
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        if (fillConfig.equals("horizontal")) {
            c.fill = GridBagConstraints.HORIZONTAL;
        }
        if (fillConfig.equals("vertical")) {
            c.fill = GridBagConstraints.VERTICAL;
        }
        if (fillConfig.equals("both")) {
            c.fill = GridBagConstraints.BOTH;
        }
        if (fillConfig.equals("None")) {
            c.fill = GridBagConstraints.NONE;
        }
        c.insets = padding;

        switch (pos) {

            case FIRST_LINE_START:
                c.anchor = GridBagConstraints.FIRST_LINE_START;
                break;

            case FIRST_LINE_END:
                c.anchor = GridBagConstraints.FIRST_LINE_END;
                break;
            case PAGE_START:
                c.anchor = GridBagConstraints.PAGE_START;
                break;
            case LINE_START:
                c.anchor = GridBagConstraints.LINE_START;
                break;
            case CENTER:
                c.anchor = GridBagConstraints.CENTER;
                break;
            case LINE_END:
                c.anchor = GridBagConstraints.LINE_END;
                break;
            case LAST_LINE_START:
                c.anchor = GridBagConstraints.LAST_LINE_START;
                break;
            case PAGE_END:
                c.anchor = GridBagConstraints.PAGE_END;
                break;
            case LAST_LINE_END:
                c.anchor = GridBagConstraints.LAST_LINE_END;
                break;

        }
    }

    /*
        private void updateSystem(){
            ArrayList<String> myCustomers = myBank.getAllCustomers();
            DefaultTableModel myTableModel = (DefaultTableModel)customerTable.getModel();
            customerTable.removeAll();
            for (String info : myCustomers){
                String [] temp = info.split(" ");
                myTableModel.addRow(temp);
            }
            Customer
        }
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BankSystem().setVisible(true);
            }
        });
    }

}
