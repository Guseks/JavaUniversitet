package guseks7.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class BankSystem extends JFrame {

	private static final int FRAME_WIDTH = 1200;
	private static final int FRAME_HEIGHT = 700;
	
	private JButton createSavings;
	private JButton createCredit;
        private JButton createCustomer;
        private JButton deleteCustomer;
        private JButton deleteAccount;
        private JButton getTransactions;
	private JTable customerTable;
        private JList accountList;
        private JTextField workflow;
        
        
	
	
	public BankSystem() {
		createButtons();
		createDisplay();
                createControlPanel();
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setTitle("A digital Bank");
                setVisible(true);
	}
        
        private void createControlPanel(){
            JPanel inputPanel = new JPanel();
            JPanel displayPanel = new JPanel();
        }
        
        private void createDisplay(){
            
        }
	
	private void createButtons() {
		createSavings = new JButton ("Create new SavingsAccount");
		createCredit = new JButton ("Create new CreditAccount");
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
		
	
	public static void main(String[] args){
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                new BankSystem().setVisible(true);
                }
            });
        }
  
}