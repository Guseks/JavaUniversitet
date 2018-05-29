
package tentamen;

/**
 * A class responsible for storing data relating to a specific highscore.
 * 
 * Name: Gustaf Ekström
 * Ltu-ID: guseks-7
 * 
 * @author Gustaf
 */
public class HighScore {
    private String name;
    private int result;
    
    public HighScore(String newName, int newResult){
        name = newName;
        result = newResult;
    }
    public String getName(){
        return name;
    }
    public int getResult(){
        return result;
    }
    /*
    Used when printing the high scores for each word.
    */
    @Override
    public String toString() {
        return "Name: "+ name + ", Attempts: " +result;
    }
}
