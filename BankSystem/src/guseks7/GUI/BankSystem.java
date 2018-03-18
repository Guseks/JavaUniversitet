package guseks7.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.*;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class BankSystem extends JFrame {

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
        private JTextField workflow;
        
        
	
	
	public BankSystem() {
		
		
                createControlPanel();
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setTitle("A digital Bank");
                setVisible(true);
	}
        
        private void createControlPanel(){
            JPanel inputPanel = createInputPanel();
            JPanel displayPanel = createDisplayPanel();
            setLayout(new GridLayout(2,1));
            add(inputPanel);
            add(displayPanel);
        }
        
        private JPanel createInputPanel(){
            createButtons();
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setPreferredSize(new Dimension(400,200));
            JPanel customerOperationsWrapper = new JPanel(new GridBagLayout());
            JPanel accountOperationsWrapper = new JPanel(new GridBagLayout());
            
            GridBagConstraints createCustomerConfig = new GridBagConstraints();
            setConstraints(createCustomerConfig, 0, 0, 1, 1, "None", new Insets(5, 0, 0, 0), FIRST_LINE_START);
            
            GridBagConstraints deleteCustomerConfig = new GridBagConstraints();
            setConstraints(deleteCustomerConfig, 1, 0, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START);
            
            GridBagConstraints changeNameConfig = new GridBagConstraints();
            setConstraints(changeNameConfig, 2, 0, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START);
            
            customerOperationsWrapper.add(createCustomer, createCustomerConfig);
            customerOperationsWrapper.add(deleteCustomer, deleteCustomerConfig);
            customerOperationsWrapper.add(changeName, changeNameConfig);
            
            
            GridBagConstraints createSavingsConfig = new GridBagConstraints();
            setConstraints(createSavingsConfig, 0, 0, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START);
            GridBagConstraints createCreditConfig = new GridBagConstraints();
            setConstraints(createCreditConfig, 0, 1, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START);
            GridBagConstraints deleteAccountConfig = new GridBagConstraints();
            setConstraints(deleteAccountConfig, 0, 2, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START);
            GridBagConstraints getTransactionsConfig = new GridBagConstraints();
            setConstraints(getTransactionsConfig, 1, 0, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START);
            GridBagConstraints depositConfig = new GridBagConstraints();
            setConstraints(depositConfig, 1, 1, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START);
            GridBagConstraints withdrawConfig = new GridBagConstraints();
            setConstraints(withdrawConfig, 1, 2, 1, 1, "None", new Insets(5, 5, 0, 0), FIRST_LINE_START);
            accountOperationsWrapper.add(createSavings, createSavingsConfig);
            accountOperationsWrapper.add(createCredit, createCreditConfig);
            accountOperationsWrapper.add(deleteAccount, deleteAccountConfig);
            accountOperationsWrapper.add(getTransactions, getTransactionsConfig);
            accountOperationsWrapper.add(deposit, depositConfig);
            accountOperationsWrapper.add(withdraw, withdrawConfig);
            
            
            GridBagConstraints customerOperationsWrapperConfig = new GridBagConstraints();
            setConstraints(customerOperationsWrapperConfig, 0, 0, 1, 1, "None", new Insets(70, 5, 0, 0), FIRST_LINE_START);
            //customerOperationsWrapper.setBorder(new TitledBorder("Mark Customer in List and choose desired operation"));
            
            /*
            JLabel custOperationsInstructions = new JLabel("Mark desired customer in the list below and choose desired operation");
            GridBagConstraints custOperationsInstructionsConfig = new GridBagConstraints();
            setConstraints(custOperationsInstructionsConfig, 0, 1, 1, 1, "None", new Insets(10, 5, 0, 0), FIRST_LINE_START);
            customerOperationsWrapper.add(custOperationsInstructions, custOperationsInstructionsConfig);
*/
            panel.add(customerOperationsWrapper, customerOperationsWrapperConfig);
            
            GridBagConstraints accountOperationsWrapperConfig = new GridBagConstraints();
            setConstraints(accountOperationsWrapperConfig, 1, 0, 1, 1, "None", new Insets(0, 20, 0, 0), FIRST_LINE_START);
            //accountOperationsWrapper.setBorder(new TitledBorder("Mark desired account in the List and choose desired operation"));
            panel.add(accountOperationsWrapper, accountOperationsWrapperConfig);
            return panel;
        }
        
        private JPanel createDisplayPanel(){
            createDisplay();
            JPanel panel = new JPanel();
            return panel;
        }
        
        private void createDisplay(){
            
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
        private void setConstraints(GridBagConstraints c, int gridx, int gridy, int gridwidth, int gridheight, String fillConfig, Insets padding, int pos){
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