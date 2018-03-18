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
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class BankSystem extends JFrame {
        
        private BankLogic myBank;
	private static final int FRAME_WIDTH = 1200;
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
        private JList accountList;
        private JComboBox chooseCustomer;
        private JTextField workflow;
        
        
	
	
	public BankSystem() {
		myBank = new BankLogic();
		myBank.createCustomer("Gustaf", "Ekström", "9306025595");
                myBank.createCustomer("Marcus", "Ekström", "9306026197");
                createControlPanel();
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setTitle("A digital Bank");
                setVisible(true);
	}
        
        private void createControlPanel(){
            JPanel inputPanel = createInputPanel();
            JPanel displayPanel = createDisplayPanel();
            setLayout(new GridBagLayout());
            
            GridBagConstraints inputPanelConfig = new GridBagConstraints();
            setConstraints(inputPanelConfig, 0, 0, 1, 1, "both", new Insets(0, 0, 0, 0), FIRST_LINE_START, 0.5, 0.5);
            add(inputPanel, inputPanelConfig);
            GridBagConstraints displayPanelConfig = new GridBagConstraints();
            setConstraints(displayPanelConfig, 0, 1, 1, 1, "both", new Insets(20, 0, 0, 0), FIRST_LINE_START, 0.5, 0.5);
            //displayPanel.setPreferredSize(new Dimension(800, 400))
            add(displayPanel, displayPanelConfig);
        }
        
        private JPanel createInputPanel(){
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
            setConstraints(customerOperationsWrapperConfig, 0, 0, 1, 1, "None", new Insets(70, 5, 0, 0), FIRST_LINE_START, 0.5, 0.5);
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
        
        private JPanel createDisplayPanel(){
            createDisplay();
            JPanel panel = new JPanel(new GridBagLayout());
            
            JPanel tableWrapper = new JPanel(new BorderLayout());
            tableWrapper.setBorder(new TitledBorder("Mark the desired Customer and choose operation"));
           
            tableWrapper.add(new JScrollPane(customerTable), BorderLayout.CENTER);
            
            
            GridBagConstraints tableWrapperConfig = new GridBagConstraints();
            setConstraints(tableWrapperConfig, 0, 0, 1, 1, "None", new Insets(0, 20, 0, 0), FIRST_LINE_START, 0.5, 0.5);
            panel.add(tableWrapper, tableWrapperConfig);
            
            JPanel accountOperationsWrapper = new JPanel(new BorderLayout());
            GridBagConstraints accountListWrapperConfig = new GridBagConstraints();
            setConstraints(accountListWrapperConfig, 1, 0, 1, 1, "None", new Insets(0, 20, 0, 0), FIRST_LINE_START, 0.5, 0.5);
            
            //accountListWrapper.setBorder(new TitledBorder("Choose desired account and operation"));
            JPanel chooseCustomerWrapper = new JPanel();
            chooseCustomerWrapper.setBorder(new TitledBorder("Choose customer to display Accounts"));
            chooseCustomerWrapper.add(chooseCustomer);
            
            JPanel accountListWrapper = new JPanel();
            accountListWrapper.setBorder(new TitledBorder("Choose desired account and operation"));
            accountListWrapper.add(accountList);
            
            //accountOperationsWrapper.add(chooseCustomerWrapper, BorderLayout.PAGE_START);
            accountOperationsWrapper.add(accountListWrapper, BorderLayout.CENTER);
            
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
            
            accountOperationsWrapper.add(OperationsWrapper, BorderLayout.SOUTH);
            panel.add(accountOperationsWrapper, accountListWrapperConfig);
            
            
            
            

            
            return panel;
        }
        
        private void createDisplay(){
            chooseCustomer = new JComboBox();
            
            ArrayList<String> myCustomers = myBank.getAllCustomers();
            
            DefaultTableModel tableModel = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                //all cells false
                    return false;
                }
            };
            tableModel.addColumn("First Name:");
            tableModel.addColumn("Last Name:");
            tableModel.addColumn("Personal Number:");
            DefaultListModel listModel = new DefaultListModel();
            for(String info : myCustomers){
                Vector<String> displayInfo = new Vector<>();
                String[] temp = info.split(" ");
                
                tableModel.addRow(temp);
                
                
                
                chooseCustomer.addItem(info);
                
                
                
            }
            Vector<String> columnNames = new Vector<>();
            columnNames.addElement("First Name: ");
            columnNames.addElement("Last Name: ");
            columnNames.addElement("Personal Number: ");
            
          
            //model.addRow(displayInfo);
            customerTable = new JTable(tableModel);
            

            //customerTable.setPreferredSize(new Dimension(500, 200));
            accountList = new JList();
            accountList.setPreferredSize(new Dimension(400, 200));
            //chooseCustomer.setPreferredSize(new Dimension(200, 100));
            
            
        }
        
        
	
	private void createButtons() {
		createSavings = new JButton ("Create new SavingsAccount");
		createCredit = new JButton ("Create new CreditAccount");
                createCustomer = new JButton("Create new Customer");
                deleteCustomer = new JButton("Delete Customer");
                changeName = new JButton("Change customers name");
                deposit = new JButton("Make deposit");
                withdraw = new JButton("Make withdrawal");
                getTransactions = new JButton("Get Transactions");
                deleteAccount = new JButton("Delete account");
                
                createCustomer.setPreferredSize(new Dimension(200, 30));
                deleteCustomer.setPreferredSize(new Dimension(200, 30));
                changeName.setPreferredSize(new Dimension(200,30));
                createSavings.setPreferredSize(new Dimension(200, 30));
                createCredit.setPreferredSize(new Dimension(200, 30));
                deleteAccount.setPreferredSize(new Dimension(200, 30));
                getTransactions.setPreferredSize(new Dimension(200,30));
                deposit.setPreferredSize(new Dimension(200, 30));
                withdraw.setPreferredSize(new Dimension(200, 30));
                
                
		createSavings.addActionListener(new ActionListener()
			{

				public void actionPerformed(ActionEvent arg0) {
					System.out.println("Knapp fungerar");
					
				}
			});
		createCredit.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Knapp fungerar");
				
			}
		});
			
			
			
            }
        private void setConstraints(GridBagConstraints c, int gridx, int gridy, int gridwidth, int gridheight, String fillConfig, Insets padding, int pos, double weightx, double weighty){
            c.gridx = gridx;
            c.gridy = gridy;
            c.gridwidth = gridwidth;
            c.gridheight = gridheight;
            if(fillConfig.equals("horizontal")){
                c.fill = GridBagConstraints.HORIZONTAL;
            }
            if(fillConfig.equals("vertical")){
                c.fill = GridBagConstraints.VERTICAL;
            }
            if(fillConfig.equals("both")){
                c.fill = GridBagConstraints.BOTH;
            }
            if(fillConfig.equals("None")){
                c.fill = GridBagConstraints.NONE;
            }
            c.insets = padding;
            
            switch(pos){
                
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
		
	
	public static void main(String[] args){
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                new BankSystem().setVisible(true);
                }
            });
        }
  
}