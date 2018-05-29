
package tentamen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * A class representing a word. Contains info about the specific word, also
 * stores the high scores for the word, so that each word has its own highscore list. 
 * Handles the storage of the highscores on a file, specific for each
 * different word.
 * 
 * Name: Gustaf Ekström
 * Ltu-ID: guseks-7
 * @author Gustaf
 */
public class Word {
    private String word;
    private int length;
    private ArrayList<HighScore> myScore;
    private String storageFile;
    public Word(String newWord){
        word = newWord;
        length = word.length();
        myScore = new ArrayList();
        storageFile = "guseks7_files/highScore/";
        
    }
    /*
    Reads the file containing the stored highscores from previous runs of the game
    Called during the setup of the game from the init function in GameLogic.
    Uses a predefined filepath, unique for every different word through using the
    actual value of the variable word to create the file.
    */
    public void readOldResults(){
         try {
            File file = new File(storageFile + word+".txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String newScore;
            while((newScore = reader.readLine()) != null){
                String name = newScore.split(" ")[0];
                int result = Integer.parseInt(newScore.split(" ")[1]);
                addHighScore(name, result);
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
    }
    public String getText(){
        return word;
    }
    public int getLength(){
        return length;
    }
    /*
    Prints the highscores that players have gotten.
    */
    public void printHighScore(){
        for(HighScore score : myScore){
            System.out.println(score.toString());
            
        }
    }
    /*
    Adds a new highscore when a player manages to solve the word.
    Stores the highscore with information about the player name and the actual result.
    */
    public void addHighScore(String name, int numberOfGuesses){
        myScore.add(new HighScore(name, numberOfGuesses));
    }
    /*
    A function responsible for saving all the highscores to a file, to be loaded when the program is started.
    Creates a new file if the file doesn't exist, with the same file name as the one being read on startup.
    Called from GameLogic whenever the user terminates the program to save data between runs of the program.
    */
    public void saveScore(){
        String name = "";
        String result="";
        for(HighScore score : myScore){
            name = score.getName();
            result = Integer.toString(score.getResult());
        }
        try {
            File file = new File(storageFile + word+".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            //Adds each score to the file containing all results for the specific word
            writer.append(name + " " + result);
            writer.newLine();
            
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
    }
}
