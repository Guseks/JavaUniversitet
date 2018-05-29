
package tentamen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * A class responsible for the logics of the game. Performs different operations
 * and tasks when prompted by the user. Calls other functions inside each instance
 * of the Word class to perform the operation. Also handles storage of the 
 * words to file, which uses a method in the Word class to also store highscores.
 * 
 * Name: Gustaf Ekström
 * Ltu-ID: guseks-7
 * 
 * @author Gustaf
 */
public class GameLogic {
    private ArrayList<Word> myWords;
    private String storageFile;
    
    /*
    The constructor. 
    */
    public GameLogic(){
        myWords = new ArrayList<Word>();
        //Sets the filepath to the list of words for the game. All words are stored here
        //between runs of the game.
        storageFile = "guseks7_files/words.txt";
        init();
    }
    
        
    /*
    A function that loads data from the saved file on game startup.
    */
    private void init(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(storageFile));
            String word;
            while((word = reader.readLine()) != null){
                myWords.add(new Word(word));
            }
            reader.close();
        }
        catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.WARNING_MESSAGE);
        }
        catch(IOException e){
            e.getMessage();
        }
        catch (Exception ex) {
            ex.getMessage();

        }
        for(Word w : myWords){
            w.readOldResults();
        }

    }
    
    /*
    A method responsible for saving data when the program is terminated by the user.
    Saves both words and highscore for each word by calling the appropriate help functions.
    Also makes sure to catch the different exceptions that can occur.
    */
    public void saveData(){
       try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(storageFile));
            for(Word w : myWords){
                writer.write(w.getText());
                writer.newLine();
            }
            
            writer.close();
        }
        catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.WARNING_MESSAGE);
        }
        catch(IOException e){
            e.getMessage();
        }
        catch (Exception ex) {
            ex.getMessage();

        }
        for(Word w : myWords){
            w.saveScore();
        }

    } 
    
    /*
    Prints all the words currently in the game for the user to see. 
    */
    public void printAllWords(){
        System.out.println("\nPossible words:");
        for(Word w : myWords){
            System.out.println(w.getText());
        }
    }
    
    /*
    Prints the a list of the high scores for each word. Does this by calling each
    word and telling it to print its own list of scores.
    */
    public void printHighScore(){
        for(Word w : myWords){
            System.out.println("---------------------------");
            System.out.println("Word: " + w.getText());
            System.out.println("High Score List: ");
            w.printHighScore();
        }
    }
    
    public int getNumberOfWords(){
        return myWords.size();
    }
    public Word getWord(int index){
        return myWords.get(index);
    }
    /*
    Adds a new word to the storage container.
    */
    public void addWord(String newWord){
        myWords.add(new Word(newWord));
    }
    /*
    Sets a new highscore for a specific word, with a specified player name and 
    what the result was, in other words how many attempts the user needed.
    Called from the class GameGUI where the user provides the desired name, and
    the program passes along the other information.
    */
    public void setHighScore(Word word, int result, String name){
        for(Word w : myWords){
            if(w.getText().equals(word.getText())){
                w.addHighScore(name, result);
            }
        }
    }
}
