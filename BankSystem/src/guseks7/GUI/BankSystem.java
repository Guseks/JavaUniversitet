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

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import guseks7.BankAccounts.Transaction;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * A Class that is responsible for creating a GUI for my Banksystem. 
 * Contains actionlistener to enable the user to access every functionality in 
 * the original bankssystem.
 * @author guseks7
 * 
 * Namn: Gustaf Ekström
 * Ltu-id: guseks-7
 *
 */

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
    private JButton printAccount;
    private JButton openSavedTransaction;

    private JTable customerTable;
    private JTable accountTable;
    private JMenu myMenu;
    private JMenuBar myMenuBar;
    private String personalNumberSelected;

    public BankSystem() {
        myBank = new BankLogic();
        createMenu();
        createControlPanel();
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("A digital Bank");
        setVisible(true);
        setLocation(200, 100);
        
        
        
    }
    /**
     * Creates a menu for the program and places it at the top of the window.
     * The menu contains a exit button and other common functions such as 
     * save and load from file. Also contains calls to functions that handle the
     * file management and enables the saving and loading of bank information.
     */
    private void createMenu(){
        myMenu = new JMenu("File");
        myMenuBar = new JMenuBar();
        
        myMenuBar.setPreferredSize(new Dimension(100, 25));
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setPreferredSize(new Dimension(50,20));
       
        exitItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        JMenuItem saveToFile = new JMenuItem("Save to file");
        JMenuItem loadFromFile = new JMenuItem("Load data from file");
        
        saveToFile.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                saveToFileFunction();
            }
        });
        
        loadFromFile.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                loadFromFileFunction();
            }
            
        });
        
        myMenu.add(saveToFile);
        myMenu.add(loadFromFile);
        
        myMenu.add(exitItem);
        
        
        myMenuBar.add(myMenu, BorderLayout.CENTER);
        setJMenuBar(myMenuBar);
    }
    
    /**
     * A function responsible for saving the current Bank and its data to a file.
     * Handles exceptions that can occur to give stability to the program.
     */
    private void saveToFileFunction(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("guseks7_Files"));
        

        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()));
                out.writeObject(myBank);
                out.writeInt(myBank.getLastAssigned());
                out.close();
            } 
            catch (FileNotFoundException e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.WARNING_MESSAGE);
                saveToFileFunction();
            }
            catch(IOException e){
                e.getMessage();
            }
            catch (Exception e) {
                e.getMessage();
                
            }
        } 
    }
    

    /**
     * lets the user specify a file to load data from, then replaces the current
     * bank, with the neewly loaded one. Then updates the interface.
     * Handles the possible exceptions that
     * can occur to make sure the program runs without crashing.
     */
    private void loadFromFileFunction(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("guseks7_Files"));
        
        

        int retrival = chooser.showOpenDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()));
                
                myBank = (BankLogic)in.readObject();
                myBank.setLastAssigned(in.readInt());
                updateDisplay();
                in.close();
            } 
            catch (FileNotFoundException e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.WARNING_MESSAGE);
                loadFromFileFunction();
            }
            catch(IOException e){
                e.getMessage();
            }
            catch (Exception ex) {
                ex.getMessage();
                
            }
        } 
    }
    
    
    /**
     * Updates the display, in other words the tables located in the display. 
     * Used when loading new data in to the bank, from a external source to make
     * the interface displays the correct information currently inside the 
     * banksystem.
     */
    private void updateDisplay(){
        DefaultTableModel myCustomerTableModel = (DefaultTableModel)customerTable.getModel();
        ArrayList<String> myCustomers = myBank.getAllCustomers();
        for(int i = 0; i < myCustomerTableModel.getRowCount(); i++){
            myCustomerTableModel.removeRow(i);
        }
        
        for (String info : myCustomers) {
            String[] temp = info.split(" ");
            myCustomerTableModel.addRow(temp);
        }
        DefaultTableModel myAccountTableModel = (DefaultTableModel)accountTable.getModel();
        for (int j = 0; j < myAccountTableModel.getRowCount(); j++){
            myAccountTableModel.removeRow(j);
        }
        for(int i = 0; i<myCustomerTableModel.getRowCount(); i++){
            ArrayList<String> accountNrs = myBank.getAccountNrs((String)myCustomerTableModel.getValueAt(i, 2));
            for (String accountID : accountNrs){
                myAccountTableModel.addRow(myBank.getAccount(personalNumberSelected, Integer.parseInt(accountID)).split(" "));
            }
            
        }
    }
/**
 * A function that creates a panel that contains everything inside the GUI.
 * The different components are then places in different subpanels inside 
 * the big one. Contains calls to similar functions responsible
 * for each subpanel.
 */
    private void createControlPanel() {
        JPanel inputPanel = createInputPanel();
        JPanel displayPanel = createDisplayPanel();
        
        setLayout(new GridBagLayout());

        GridBagConstraints inputPanelConfig = new GridBagConstraints();
        setConstraints(inputPanelConfig, 0, 0, 3, 1, "both", new Insets(0, 0, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        inputPanel.setPreferredSize(new Dimension(600, 150));
        add(inputPanel, inputPanelConfig);
        GridBagConstraints displayPanelConfig = new GridBagConstraints();
        setConstraints(displayPanelConfig, 0, 1, 2, 3, "both", new Insets(0, 0, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        displayPanel.setPreferredSize(new Dimension(1100, 400));
        add(displayPanel, displayPanelConfig);
       
    }
    
    /**
     * Creates the subpanel containing input operations, and places 
     * it at the top of the window. Uses a subpanel to wrap all the components
     * in and the places them inside the bigger panel, called inputPanel
     * Contains code for creating buttons and their functionality.
     *  
     */
    private JPanel createInputPanel() {
        createButtons();
        JPanel panel = new JPanel(new BorderLayout());
      
        JPanel customerOperationsWrapper = new JPanel(new GridBagLayout());
        
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
        
        panel.add(customerOperationsWrapper, BorderLayout.WEST);
        
        return panel;
    }
    
    /**
     * Creates the subpanel containing all the components for displaying data
     * Uses several LayoutManagers to place them inside panels and to place 
     * them in a appropriate position in the GUI. Uses wrappers to create
     * borders around groups of components containing instructions 
     * to the user. 
     *  
     */
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
             

        JPanel accountTableWrapper = new JPanel(new BorderLayout());
        accountTableWrapper.setPreferredSize(new Dimension(500, 200));
        accountTableWrapper.setBorder(new TitledBorder("Choose desired account and operation"));
        JScrollPane accountTableScroll = new JScrollPane();
        accountTableScroll.setPreferredSize(new Dimension(500,100));
    
        accountTableWrapper.add(new JScrollPane(accountTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        
        
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
        GridBagConstraints printAccountConfig = new GridBagConstraints();
        setConstraints(printAccountConfig, 0, 2, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        GridBagConstraints openSavedTransactionConfig = new GridBagConstraints();
        setConstraints(openSavedTransactionConfig, 1, 2, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        
        OperationsWrapper.add(deleteAccount, deleteAccountConfig);
        OperationsWrapper.add(getTransactions, getTransactionsConfig);
        OperationsWrapper.add(deposit, depositConfig);
        OperationsWrapper.add(withdraw, withdrawConfig);
        OperationsWrapper.add(printAccount, printAccountConfig);
        OperationsWrapper.add(openSavedTransaction, openSavedTransactionConfig);
        
        GridBagConstraints OperationsWrapperConfig = new GridBagConstraints();
        setConstraints(OperationsWrapperConfig, 0, 2, 1, 1, "both", new Insets(20, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        accountOperationsWrapper.add(OperationsWrapper, OperationsWrapperConfig);
        GridBagConstraints accountListWrapperConfig = new GridBagConstraints();
        setConstraints(accountListWrapperConfig, 1, 0, 1, 1, "both", new Insets(0, 40, 0, 0), FIRST_LINE_START, 0.5, 0.5);
        panel.add(accountOperationsWrapper, BorderLayout.CENTER);
        
     
        return panel;
    }

    /**
     *Creates the different components in the display area, except the buttons. 
     * Inits the JTables with the desired column names and properties. Also adds
     * a listener responisble for checking when the user selects a customer in
     * the customer Table, to display the customers accounts
     */
    private void createDisplay() {
        
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
            String[] temp = info.split(" ");
            myTableModel.addRow(temp);
           
        }

        customerTable = new JTable(myTableModel);
        ListSelectionModel myCustomerSelectionModel = customerTable.getSelectionModel();
        myCustomerSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
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
    
        
        customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                DefaultTableModel myTableModel = (DefaultTableModel) customerTable.getModel();
                DefaultTableModel myAccountTableModel = (DefaultTableModel)accountTable.getModel();
                int selectedRow = customerTable.getSelectedRow();
                if(selectedRow != -1){
                    String personalNumber = (String)myTableModel.getValueAt(selectedRow, 2);
                    
                
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
        
    }
/**
 * Inits the different buttons in the GUI, sets their name and also specifies 
 * their desired size. 
 */
    private void createButtons() {
        createSavings = new JButton("Create new SavingsAccount");
        createCredit = new JButton("Create new CreditAccount");
        createCustomer = new JButton("Create new Customer");
        deleteCustomer = new JButton("Delete Customer");
        changeName = new JButton("Change customers name");
        deposit = new JButton("Make Deposit");
        withdraw = new JButton("Make Withdrawal");
        getTransactions = new JButton("Get Transactions");
        deleteAccount = new JButton("Delete Account");
        printAccount = new JButton("Print Account History");
        openSavedTransaction = new JButton("Open Saved Transactions");

        createCustomer.setPreferredSize(new Dimension(200, 30));
        deleteCustomer.setPreferredSize(new Dimension(200, 30));
        changeName.setPreferredSize(new Dimension(200, 30));
        createSavings.setPreferredSize(new Dimension(200, 30));
        createCredit.setPreferredSize(new Dimension(200, 30));
        deleteAccount.setPreferredSize(new Dimension(200, 30));
        getTransactions.setPreferredSize(new Dimension(200, 30));
        deposit.setPreferredSize(new Dimension(200, 30));
        withdraw.setPreferredSize(new Dimension(200, 30));
        printAccount.setPreferredSize(new Dimension(200, 30));
        openSavedTransaction.setPreferredSize(new Dimension(200, 30));
        
       addButtonFunctions();

    }
    
    /**
     * A help function to make the code more readable. Responsible for adding
     * all the actionslisteners to the buttons, which implements the desired
     * functions in the GUI.
     */
    private void addButtonFunctions(){
        /**
         * Gathers the required info to creata a new savingsAccount 
         * and calls the function in BankLogic to create a new savingsAccount.
         * Also adds it to the accountTable, to display the created account
         */
        createSavings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                DefaultTableModel myTableModel = (DefaultTableModel) customerTable.getModel();
                int selectedRow = customerTable.getSelectedRow();
                
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Customer Selected", "", JOptionPane.PLAIN_MESSAGE);
                } else {
                    
                    personalNumberSelected = (String) myTableModel.getValueAt(selectedRow, 2);
                    int accountID = myBank.createSavingsAccount(personalNumberSelected);
                    //System.out.println(myBank.getCustomer((String) myTableModel.getValueAt(selectedRow, 2)));
                    DefaultTableModel myAccountTableModel = (DefaultTableModel)accountTable.getModel();
                    myAccountTableModel.addRow(myBank.getAccount(personalNumberSelected, accountID).split(" "));
                }

            }
        });
        /**
         * Gathers the required info to creata a new savingsAccount 
         * and calls the function in BankLogic to create a new savingsAccount.
         * Also adds it to the accountTable, to display the created account
         */
        createCredit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                DefaultTableModel myTableModel = (DefaultTableModel) customerTable.getModel();
                int selectedRow = customerTable.getSelectedRow();
                
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Customer Selected", "", JOptionPane.PLAIN_MESSAGE);
                } else {
                    personalNumberSelected = (String) myTableModel.getValueAt(selectedRow, 2);
                    int accountID = myBank.createCreditAccount(personalNumberSelected);
                    
                    DefaultTableModel myAccountTableModel = (DefaultTableModel)accountTable.getModel();
                    myAccountTableModel.addRow(myBank.getAccount(personalNumberSelected, accountID).split(" "));
                }
                
            }
        });
        /**
         * Opens a separate window, where the user is asked to provide the new 
         * data for the customer that they wish to create. All variables used
         * are created locally.
         */
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
                        ((DefaultTableModel) customerTable.getModel()).addRow(customerData);
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Data", "", JOptionPane.WARNING_MESSAGE);

                    }
                }
                
            }
        });
        /**
         * Functionality for deleting the selected customer from the system, 
         * and the tables displaying the customers data. If the customer has 
         * any accounts in the bank when deleted these are displayed and the 
         * resulting interest is displayed in a separate window.
         */
        deleteCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                DefaultTableModel myCustomerTableModel = (DefaultTableModel) customerTable.getModel();
                DefaultTableModel myAccountTableModel = (DefaultTableModel) accountTable.getModel();
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Customer Selected", "", JOptionPane.PLAIN_MESSAGE);
                } else {
                     personalNumberSelected = (String)myCustomerTableModel.getValueAt(selectedRow, 2);
                
                    JPanel panel = new JPanel(new GridBagLayout());
                    DefaultTableModel myModel = new DefaultTableModel(){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            //all cells false
                            return false;
                        }
                    };
                    myModel.addColumn("Account ID");
                    myModel.addColumn("Saldo");
                    myModel.addColumn("Account Type");
                    myModel.addColumn("Interest rate");
                    myModel.addColumn("Interest");


                    JTable display = new JTable(myModel);

                    JScrollPane displayScroll = new JScrollPane(display);
                    displayScroll.setPreferredSize(new Dimension(400, 100));
                    GridBagConstraints customerLabelConfig = new GridBagConstraints();
                    setConstraints(customerLabelConfig, 0, 0, 1, 1, "both", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
                    GridBagConstraints labelConfig = new GridBagConstraints();
                    setConstraints(labelConfig, 0, 1, 1, 1, "both", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);

                    panel.add(new JLabel("Deleted Customer: " + myBank.getCustomer(personalNumberSelected).get(0)));
                    panel.add(new JLabel("The following accounts were deleted"), labelConfig);
                    GridBagConstraints displayScrollConfig = new GridBagConstraints();
                    setConstraints(displayScrollConfig, 0, 2, 1, 1, "both", new Insets(10, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
                    panel.add(displayScroll, displayScrollConfig);

                    display.removeAll();


                    ArrayList<String> accountNumbers = myBank.getAccountNrs(personalNumberSelected);
                    if(!accountNumbers.isEmpty()){
                        ArrayList<String> help = new ArrayList<String>();
                        for (String number : accountNumbers){
                            help.add(myBank.closeAccount(personalNumberSelected, Integer.parseInt(number)));
                        }
                        for(String info : help){
                            myModel.addRow(info.split(" "));
                        }

                    }
                    if(!accountNumbers.isEmpty()){
                        JOptionPane.showMessageDialog(null, panel, "Accounts Deleted", JOptionPane.INFORMATION_MESSAGE);
                    }

                    myBank.deleteCustomer((String) myCustomerTableModel.getValueAt(selectedRow, 2));

                    while (accountTable.getRowCount()> 0){
                        ((DefaultTableModel)accountTable.getModel()).removeRow(0);
                    }
                    myCustomerTableModel.removeRow(selectedRow);
                    accountTable.repaint();
                    customerTable.repaint();

                }
            }
        });
        /**
         * Changes the name of the selected customer, by opening a separate
         * window which promts the user for the new name of the customer. Then 
         * calls a help function to perform the operation.
         */
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
        
        /**
         * Deletes the selected account from the currently selected customer. 
         * Displays the deleted account in a separate window, providing all the
         * information required about the deleted account.
         */
        deleteAccount.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                DefaultTableModel myAccountTableModel = (DefaultTableModel) accountTable.getModel();
                DefaultTableModel myCustomerTableModel = (DefaultTableModel) customerTable.getModel();
                int selectedRow = accountTable.getSelectedRow();
                int selectedCustomer = customerTable.getSelectedRow();

                
                if(selectedCustomer == -1){
                    JOptionPane.showMessageDialog(null, "No Customer Selected", "", JOptionPane.PLAIN_MESSAGE);
                }
                else if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Account Selected", "", JOptionPane.PLAIN_MESSAGE);
                } 
                
                else {
                    personalNumberSelected = (String)myCustomerTableModel.getValueAt(selectedCustomer, 2);
                    int accountID = Integer.parseInt((String)myAccountTableModel.getValueAt(selectedRow, 0));
                    JPanel panel = new JPanel(new GridBagLayout());
                    DefaultTableModel myModel = new DefaultTableModel(){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            //all cells false
                            return false;
                        }
                    };
                    myModel.addColumn("Account ID");
                    myModel.addColumn("Saldo");
                    myModel.addColumn("Account Type");
                    myModel.addColumn("Interest rate");
                    myModel.addColumn("Interest");
                    
                    
                    JTable display = new JTable(myModel);

                    JScrollPane displayScroll = new JScrollPane(display);
                    displayScroll.setPreferredSize(new Dimension(400, 50));
                    GridBagConstraints labelConfig = new GridBagConstraints();
                    setConstraints(labelConfig, 0, 0, 1, 1, "both", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
                    panel.add(new JLabel("The following account were deleted"), labelConfig);
                    GridBagConstraints displayScrollConfig = new GridBagConstraints();
                    setConstraints(displayScrollConfig, 0, 1, 1, 1, "both", new Insets(10, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
                    panel.add(displayScroll, displayScrollConfig);
                           
                    display.removeAll();
                    
                    myModel.addRow(myBank.closeAccount(personalNumberSelected, accountID).split(" "));
                 
                    myAccountTableModel.removeRow(selectedRow);
                    accountTable.repaint();
                    JOptionPane.showMessageDialog(null, panel, "Account Deleted", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        
        /**
         * Responsible for getting and displaying the performed
         * transactions on the selected account belonging to the selected
         * Customer to the user. Creates a new simple separate window to 
         * display the information inside a table.
         */
        getTransactions.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                DefaultTableModel myAccountTableModel = (DefaultTableModel) accountTable.getModel();
                DefaultTableModel myCustomerTableModel = (DefaultTableModel) customerTable.getModel();
                
                int selectedRow = accountTable.getSelectedRow();
                int selectedCustomer = customerTable.getSelectedRow();
                
                
                
                if (selectedCustomer == -1) {
                    JOptionPane.showMessageDialog(null, "No Customer Selected", "", JOptionPane.PLAIN_MESSAGE);
                    
                }
                else if(selectedRow == -1) {
                    
                    JOptionPane.showMessageDialog(null, "No Account Selected", "", JOptionPane.PLAIN_MESSAGE);
                    
                }
                else {
                    int accountID = Integer.parseInt((String)myAccountTableModel.getValueAt(selectedRow, 0));
                    personalNumberSelected = (String)myCustomerTableModel.getValueAt(selectedCustomer, 2);
                    ArrayList<Transaction> madeTransactions = myBank.getTransactions(personalNumberSelected, accountID);
                    
                    if(madeTransactions.isEmpty()){
                        JOptionPane.showMessageDialog(null, "No Transactions made for current account", "", JOptionPane.PLAIN_MESSAGE);
                    }
                    else {
                            JPanel panel = new JPanel(new GridBagLayout());
                            DefaultListModel myModel = new DefaultListModel();
                            
                            JList display = new JList(myModel);
                            
                            JScrollPane displayScroll = new JScrollPane(display, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                            displayScroll.setPreferredSize(new Dimension(300, 150));
                            GridBagConstraints labelConfig = new GridBagConstraints();
                            setConstraints(labelConfig, 0, 0, 1, 1, "both", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
                            panel.add(new JLabel("Made Transactions on selected Account"), labelConfig);
                            GridBagConstraints displayScrollConfig = new GridBagConstraints();
                            setConstraints(displayScrollConfig, 0, 1, 1, 1, "both", new Insets(10, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
                            panel.add(displayScroll, displayScrollConfig);
                           
                            myModel.removeAllElements();
                            for(Transaction t : madeTransactions){
                                myModel.addElement(" " + t);
                            }
                        JOptionPane.showMessageDialog(null, panel, "Transactions", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                }
            }
        });
        
        /**
         * Responsible for performing the required operations 
         * connected with depositing money into a account. Checks to see which
         * customer and which account is selected
         */
        deposit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                
                DefaultTableModel myAccountTableModel = (DefaultTableModel) accountTable.getModel();
                DefaultTableModel myCustomerTableModel = (DefaultTableModel) customerTable.getModel();
                int selectedCustomer = customerTable.getSelectedRow();
                int selectedRow = accountTable.getSelectedRow();
                
                
                if(selectedCustomer == -1){
                    JOptionPane.showMessageDialog(null, "No Customer Selected", "", JOptionPane.PLAIN_MESSAGE);
                    
                }
                else if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Account Selected", "", JOptionPane.PLAIN_MESSAGE);
                    
                }
                else {
                    personalNumberSelected = (String)myCustomerTableModel.getValueAt(selectedCustomer, 2);
                
                
                    int accountID = Integer.parseInt((String)myAccountTableModel.getValueAt(selectedRow, 0));
                    double amount = getInput("deposit");
                    // If the provided input is ok, performs the deposit and changes
                    //the information in the table
                    if(amount != -1){

                        myBank.deposit(personalNumberSelected, accountID, amount);
                        myAccountTableModel.removeRow(selectedRow);
                        String [] accountInfo = myBank.getAccount(personalNumberSelected, accountID).split(" ");
                        myAccountTableModel.addRow(accountInfo);
                    }
                }

            }
            
        });
        
        /**
         * Contains the functionality required to perform withdrawals from 
         * a specified account. Similar structure to the function for deposit
         * with the addition of code responsible to make sure the withdrawal is 
         * possible. If the input is wrong in any way, the user is informed and
         * asked for a new input
         */
        withdraw.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                DefaultTableModel myAccountTableModel = (DefaultTableModel) accountTable.getModel();
                DefaultTableModel myCustomerTableModel = (DefaultTableModel) customerTable.getModel();
                int selectedCustomer = customerTable.getSelectedRow();
                int selectedRow = accountTable.getSelectedRow();
                
                if(selectedCustomer == -1){
                    JOptionPane.showMessageDialog(null, "No Customer Selected", "", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                else if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Account Selected", "", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                else {
                    personalNumberSelected = (String)myCustomerTableModel.getValueAt(selectedCustomer, 2);
                }
                int accountID = Integer.parseInt((String)myAccountTableModel.getValueAt(selectedRow, 0));
                double saldo = Double.parseDouble((String)myAccountTableModel.getValueAt(selectedRow, 1));
                boolean done = false;
                while (!done){
                    double amount = getInput("withdraw");
                    if(amount != -1 && myBank.withdraw(personalNumberSelected, accountID, amount)){   
                    
                        myAccountTableModel.removeRow(selectedRow);
                        String [] accountInfo = myBank.getAccount(personalNumberSelected, accountID).split(" ");
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
        
        /**
         * Contains functionality for printing the Account history for the
         * selected account to a file. Calls a help function to perform the
         * actual saving operation after gathering all the information needed.
         */
        printAccount.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                DefaultTableModel myCustomerTableModel = (DefaultTableModel) customerTable.getModel();
                DefaultTableModel myAccountTableModel = (DefaultTableModel) accountTable.getModel();
                int selectedCustomer = customerTable.getSelectedRow();
                int selectedAccount = accountTable.getSelectedRow();
                if(selectedCustomer == -1 ){
                    JOptionPane.showMessageDialog(null, "No Customer Selected", "", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                else {
                    personalNumberSelected = (String)myCustomerTableModel.getValueAt(selectedCustomer, 2);
                }
                if(selectedAccount == -1) {
                    JOptionPane.showMessageDialog(null, "No Account Selected", "", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                int accountID = Integer.parseInt((String)myAccountTableModel.getValueAt(selectedAccount, 0));
                double saldo = Double.parseDouble((String)myAccountTableModel.getValueAt(selectedAccount, 1));
                ArrayList<Transaction> transactions = myBank.getTransactions(personalNumberSelected, accountID);
                printAccountHistory(saldo, transactions, personalNumberSelected, accountID);
            }
            
        });
        
        /**
         * The ActionListener for the button with the name Open Saved Transactions
         * Calls a help function to perform the task of opening and displaying 
         * the saved transactions located in a saved file selected by the user.
         */
        openSavedTransaction.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                displayAccountHistory();
            }
            
        });
    
    }
    
    /**
     * 
     * @param saldo
     * @param myTransactions
     * @param personalNumber
     * @param accountID 
     * 
     * A Function that is responsible for printing the Account History of the selected account
     * to a text file, which is placed inside the storage folder with an appropriate name
     * containing information about the account. 
     */
    private void printAccountHistory(double saldo, ArrayList<Transaction> myTransactions, String personalNumber, int accountID){
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
            try {
                File file = new File ("guseks7_Files\\" + personalNumber + "_" + accountID + ".txt");
                BufferedWriter out = new BufferedWriter(new FileWriter(file));
                out.write("Dagens Datum: " + dateFormat.format(new Date()));
                out.newLine();
                out.newLine();
                
                out.write("Utförda Transaktioner: ");
                out.newLine();
                
                for(Transaction info : myTransactions){
                    out.write(info.toString());
                    out.newLine();
                }
                out.newLine();
                out.write("Aktuellt Saldo på konto: ");
                out.write(String.valueOf(saldo));
                out.close();
                JOptionPane.showMessageDialog(null, "Account History printed to File!", "", JOptionPane.INFORMATION_MESSAGE);
                
            } 
            catch (FileNotFoundException e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.WARNING_MESSAGE);
            }
            
            catch (Exception ex) {
                ex.getMessage();
                
            }
        
    }
    
    /**
     * Lets the user choose a file and then displays the content in the selected
     * file to the user in a separate window. The structure of the function is 
     * dependent on how the information was saved to the file. Makes sure to
     * catch the relevant exceptions to make sure the program runs smoothly.
     */
    private void displayAccountHistory(){
        
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("guseks7_Files"));
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        

        int retrival = chooser.showOpenDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                
                String help = reader.readLine();
                String savedDate = help.substring(14);
                reader.readLine();
                reader.readLine();

                ArrayList<String> madeTransactions = new ArrayList();
                String temp = reader.readLine();
                while(!(temp.isEmpty())){
                    madeTransactions.add(temp);
                    temp = reader.readLine();
                }
                double saldo = Double.parseDouble(reader.readLine().substring(24));
                
                JPanel panel = new JPanel(new GridBagLayout());
                DefaultListModel myModel = new DefaultListModel();
                String info = file.getName();
                String [] accountInfo = info.split("_");
                accountInfo[1] = accountInfo[1].substring(0, 4);
                JList display = new JList(myModel);

                JScrollPane displayScroll = new JScrollPane(display, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                displayScroll.setPreferredSize(new Dimension(300, 150));
                GridBagConstraints dateLabelConfig = new GridBagConstraints();
                setConstraints(dateLabelConfig, 0, 0, 1, 1, "both", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
                GridBagConstraints customerLabelConfig = new GridBagConstraints();
                setConstraints(customerLabelConfig, 0, 1, 1, 1, "both", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
                GridBagConstraints accountLabelConfig = new GridBagConstraints();
                setConstraints(accountLabelConfig, 0, 2, 1, 1, "both", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
                GridBagConstraints transactionsLabelConfig = new GridBagConstraints();
                setConstraints(transactionsLabelConfig, 0, 3, 1, 1, "both", new Insets(5, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
                
                panel.add(new JLabel("Date: " +savedDate), dateLabelConfig);
                panel.add(new JLabel("Customer: " + accountInfo[0]), customerLabelConfig);
                panel.add(new JLabel("account ID: "+accountInfo[1]), accountLabelConfig);
                panel.add(new JLabel("Made Transactions on selected Account:"), transactionsLabelConfig);
                GridBagConstraints displayScrollConfig = new GridBagConstraints();
                setConstraints(displayScrollConfig, 0, 4, 1, 1, "both", new Insets(10, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
                panel.add(displayScroll, displayScrollConfig);

                myModel.removeAllElements();
                for(String s : madeTransactions){
                    myModel.addElement(s);
                }
                JOptionPane.showMessageDialog(null, panel, "Saved Transactions", JOptionPane.INFORMATION_MESSAGE);
                
                reader.close();
            } 
            catch (FileNotFoundException e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.WARNING_MESSAGE);
                loadFromFileFunction();
            }
            catch(IOException e){
                e.getMessage();
            }
            catch (Exception ex) {
                ex.getMessage();
                
            }
        } 
    }
    
    /**
     * 
     * a help function to make sure the given input when making a deposit or 
     * withdrawal is a suitable type, a input of type double.
     */
    
    
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
    
    /**
     * Another helpfunction responsible for displaying and getting the input
     * when the user wants to do a deposit or a withdrawal. uses the function
     * validateInput, if the value provided is not suitable, informs the user
     * and ask the user for a new value by a recursive call to the function
     * itself.
     *  
     */
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
    
    /**
     * A function responsible for updating the information about a customer
     * when the user wants to change the name of a customer. Both inside the 
     * structures storing the data about the customer but also in the table that 
     * displays the data about the customers.
     *  
     */
    private void updateCustomer(String newFirstName, String newLastName, int selectedRow) {
        DefaultTableModel myTableModel = (DefaultTableModel) customerTable.getModel();
        if (!newFirstName.isEmpty() && !newLastName.isEmpty()) {
            myBank.changeCustomerName(newFirstName, newLastName, (String) myTableModel.getValueAt(selectedRow, 2));
            myTableModel.setValueAt(newFirstName, selectedRow, 0);
            myTableModel.setValueAt(newLastName, selectedRow, 1);
        }
    }

    /**
     * A function that is used to make the code more readible by creating 
     * some abstraction. The function handles specifying the constraints for
     * each component that is placed inside a panel using the GridBagLayout
     * manager
     *
     */
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

   /**
    * The main function, creates a new instance of my GUI and runs it until
    * the user choses to close the program
    *  
    */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BankSystem().setVisible(true);
            }
        });
    }

}
