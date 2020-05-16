import bagel.AbstractGame;
import bagel.Input;
import bagel.Keys;
import bagel.Window;

/**
 * A type of abstract game based off Bloons TD.
 */
public class ShadowDefend extends AbstractGame {

    private static final int HEIGHT = 768;                                  // Height of game window
    private static final int WIDTH = 1024;                                  // Width of game window
    private static final Keys slicerWaveKey = Keys.S;                       // Key pressed to commence wave of slicers
    private static final Keys speedUpKey = Keys.L;                          // Key pressed to increase speed of game
    private static final Keys slowDownKey = Keys.K;                         // Key pressed to decrease speed of game

    private Level currentLevel;

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

    }
}