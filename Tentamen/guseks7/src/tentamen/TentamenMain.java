

package tentamen;

/**
 * The main class, launching the program
 * 
 * Name: Gustaf Ekström
 * Ltu-ID: guseks-7  
 * @author Gustaf
 */
public class TentamenMain {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameGUI();
            }
        });
    }
}
