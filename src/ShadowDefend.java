import bagel.AbstractGame;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.map.TiledMap;

public class ShadowDefend extends AbstractGame {

    private static final int HEIGHT = 768;                                  // Height of game window
    private static final int WIDTH = 1024;                                  // Width of game window
    private static final Keys slicerWaveKey = Keys.S;                       // Key pressed to commence wave of slicers
    private static final Keys speedUpKey = Keys.L;                          // Key pressed to increase speed of game
    private static final Keys slowDownKey = Keys.K;                         // Key pressed to decrease speed of game
    private final TiledMap map = new TiledMap("res/levels/1.tmx");  // Used map & polyline
    private final int polylineLength = map.getAllPolylines().get(0).size(); // Size of map's polyline

    private Wave currentWave;


    /* Constructs the game Shadow Defend; calls on constructor from AbstractGame */
    public ShadowDefend() {
        super(WIDTH, HEIGHT, "ShadowDefend");
    }


    /* Runs each frame of Shadow Defend */
    public static void main(String[] args) throws Exception {
        new ShadowDefend().run();
    }


    /* Dictates the updates instilled across each frame, ensures the appropriate ending of the game */
    @Override
    protected void update(Input input) {
        map.draw(0, 0, 0, 0, Window.getWidth(), Window.getHeight());

        /* Controls game-play as per the keyboard input */
        if (input.isDown(slicerWaveKey) && currentWave==null) {
            currentWave = new Wave(slicerWaveKey, System.currentTimeMillis(), map);
        }
        if (input.isDown(speedUpKey) && currentWave!=null) {
            currentWave.adjustTimescale(1, System.currentTimeMillis());
        }
        if (input.isDown(slowDownKey) && currentWave!=null) {
            currentWave.adjustTimescale(-1, System.currentTimeMillis());
        }

        /* Depending on state of wave, will refresh or close window */
        if (currentWave!=null) {
            if (currentWave.isActive()) {
                currentWave.refreshEnemies(System.currentTimeMillis(), polylineLength);
            } else {
                Window.close();
            }
        }
    }
}