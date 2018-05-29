
package tentamen;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class is responsible for handling the interaction with the user
 * Displays the menu and runs the game.
 * The user can choose what they want to do using the keyboard.
 * Acts as a interface between the user and the logics of the game.
 * 
 * Name: Gustaf Ekström
 * Ltu-ID: guseks-7
 * @author Gustaf
 */
public class GameGUI {
    private GameLogic myLogic;
    boolean exit = false;
    Scanner scanner;
    public GameGUI(){
        myLogic = new GameLogic();
        scanner = new Scanner(System.in);
        runMenu();
    }
    /*
    A function that displays the menu and responds to the inputs from the user
    Calls other help functions to perform different tasks.
    */
    private void runMenu(){
        int selector;
        while(!exit){
            System.out.println("\n----------------Menu------------------");
            System.out.println("\nWelcome to my Game");
            System.out.println("Pick desired operation:\n");
            System.out.println("1: Print all words currently in Game");
            System.out.println("2: Add new word");
            System.out.println("3: Start game");
            System.out.println("4: Print High Score");
            System.out.println("5: Exit");
            selector = scanner.nextInt();
            switch(selector){
                case 1:
                    myLogic.printAllWords();
                    break;
                case 2:
                    JPanel panel = new JPanel(new GridLayout(0,1));
                    JLabel label = new JLabel("input new word to add to Game:");
                    JTextField newWord = new JTextField();
                    panel.add(label);
                    panel.add(newWord);
                    int result = JOptionPane.showConfirmDialog(null, panel, "Add new word",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        myLogic.addWord(newWord.getText());
                        
                    }
                    break;
                case 3:
                    playGame();
                    break;
                case 4:
                    myLogic.printHighScore();
                    break;
                case 5:
                    exit = true;
                    myLogic.saveData();
                    break;
            }
        }
        
    }
    /*
    The function that runs the entire game. Each call of the function runs one
    full game, from start to finish. Launched from the menu. Contains prompts and 
    information the player. Also saves the highscore of the player if they are succesful.
    */
    private void playGame(){
        int numberOfWords = myLogic.getNumberOfWords();
        int selectedWord = (int)(Math.random() * (numberOfWords));
        System.out.println(numberOfWords);
        System.out.println(selectedWord);
        Word word = myLogic.getWord(selectedWord);
        ArrayList<String> help = new ArrayList();
        for(int i = 0; i< word.getLength(); i++){
            help.add("_");
        }
        boolean done = false;
        int round = 0;
        int guessNumber = 0;
        while(!done){
            System.out.println("\n------------ Next Round -------------\n");
            drawGuy(round);
            System.out.println("\nTime until death: " + (11-round) +" error");
            System.out.println("Current guess: ");
            for(String s : help){
                System.out.print(s + " ");
            }
            System.out.println("\nPick a letter");
            String guess = scanner.next();
            while(guess.length() != 1){
                System.out.println("\nOnly one letter allowed at a time!");
                System.out.println("Pick a letter");
                guess = scanner.next();
            }
            boolean goodGuess = false;
            for(int j = 0; j<word.getLength(); j++){
                if(guess.charAt(0) == ((word.getText()).charAt(j))){
                    help.add(j, guess);
                    help.remove(j+1);
                    goodGuess = true;
                }
            }
            if(!goodGuess){
                round++;
            }
            
            String answer = "";
            for(String s : help){
                answer =answer +" "+ s;
            }
            System.out.println("Current Answer: " + answer);
            guessNumber++;
            System.out.println("Guesses made: " +guessNumber);
            if(word.getText().equals(answer.replace(" ", ""))){
                System.out.println("Rätt svar! Bra jobbat!");
                JPanel panel = new JPanel(new GridLayout(0,1));
                JTextField playerName = new JTextField();
                JLabel label = new JLabel("Input your name:");
                panel.add(label);
                panel.add(playerName);
                int result = JOptionPane.showConfirmDialog(null, panel, "Record High Score",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        myLogic.setHighScore(word, guessNumber, playerName.getText());
                        
                    }
                
                done = true;
            }
            if(round >= 11){
                System.out.println("Your friend has died, Game over!");
                System.out.println("Returning to game menu");
                System.out.println("\n------------------------------");
                done = true;
            }
            
        }
        
        
            
        
        
    }
    
    /*
    A helpfunction to draw the guy that is the core of the game. Shows in a graphical way
    how many guesses the player has left. Uses a switch case to print the different
    stages of the drawing.
    */
    private void drawGuy(int round){
        System.out.println("Current State of your friend:\n");
        switch(round){
            case 0:
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                break;
            case 1:
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("/");
                
                break;
            case 2:
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("/ \\");
               
                break;
            case 3:
                System.out.println("");
                System.out.println(" |");
                System.out.println(" |");
                System.out.println(" |");
                System.out.println(" |");
                System.out.println("/ \\");
                
                break;
            case 4:
                System.out.println("  _______");
                System.out.println(" |");
                System.out.println(" |");
                System.out.println(" |");
                System.out.println(" |");
                System.out.println("/ \\");
                
                break;
            case 5:
                System.out.println("  _______");
                System.out.println(" |\t |");
                System.out.println(" |");
                System.out.println(" |");
                System.out.println(" |");
                System.out.println("/ \\");
                
                break;
            case 6:
                System.out.println("  _______");
                System.out.println(" |\t |");
                System.out.println(" |\t o");
                System.out.println(" |");
                System.out.println(" |");
                System.out.println("/ \\");
                
                break;
            case 7:
                System.out.println("  _______");
                System.out.println(" |\t |");
                System.out.println(" |\t o");
                System.out.println(" |\t |");
                System.out.println(" |");
                System.out.println("/ \\");
                break;
            case 8:
                System.out.println("  _______");
                System.out.println(" |\t |");
                System.out.println(" |\t o");
                System.out.println(" |\t/|");
                System.out.println(" |");
                System.out.println("/ \\");
                break;
            case 9:
                System.out.println("  _______");
                System.out.println(" |\t |");
                System.out.println(" |\t o");
                System.out.println(" |\t/|\\");
                System.out.println(" |");
                System.out.println("/ \\");
                break;
            case 10:
                System.out.println("  _______");
                System.out.println(" |\t |");
                System.out.println(" |\t o");
                System.out.println(" |\t/|\\");
                System.out.println(" |\t/");
                System.out.println("/ \\");
                break;
            case 11:
                System.out.println("  _______");
                System.out.println(" |\t |");
                System.out.println(" |\t o");
                System.out.println(" |\t/|\\");
                System.out.println(" |\t/ \\");
                System.out.println("/ \\");
                break;
        }
    }
}