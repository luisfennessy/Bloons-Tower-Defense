import bagel.AbstractGame;
import bagel.Input;
import bagel.Window;

/**
 * A type of abstract game based off Bloons TD.
 */
public class ShadowDefend extends AbstractGame {

    public static final int HEIGHT = 768;                       // Height of game window
    public static final int WIDTH = 1024;                       // Width of game window
    private static final int MAX_LEVELS_SUPPORTED = 2;
    // Change FPS to suit system specifications.
    public static final double FPS = 60;
    public final static double MILLI_TO_NORMAL = 0.001;         // multiplier to convert milli units to units

    private Level currentLevel;
    private int levelNumber = 1;

    /**
     * Creates a new instance of the game.
     */
    public ShadowDefend() {
        super(WIDTH, HEIGHT, "ShadowDefend");
    }


    /**
     * Entry-point for the game
     *
     * @param args Optional command-line arguments
     */
    public static void main(String[] args) throws Exception {
        new ShadowDefend().run();
    }


    /**
     * Updates the state of the game at each frame
     *
     * @param input Transfers player's input to game-play alterations
     */
    @Override
    protected void update(Input input) {

        if (currentLevel != null) {
            if (currentLevel.isActive() || currentLevel.isGameOver()) {
                currentLevel.update(input);
            } else if (levelNumber < MAX_LEVELS_SUPPORTED) {
                levelNumber++;
                currentLevel = new Level(levelNumber);
            } else {
                currentLevel.setGameIsOver(true);
            }
        } else {
            currentLevel = new Level(levelNumber);
        }

    }
}