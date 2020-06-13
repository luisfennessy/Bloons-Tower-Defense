import bagel.Input;
import bagel.Keys;
import bagel.MouseButtons;
import bagel.Window;
import bagel.map.TiledMap;
import bagel.util.Point;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * A level of ShadowDefend game.
 */
public class Level {

    private final static String WAVES_SRC = "res/levels/waves.txt";

    private final static int INDEX_OF_WAVE_NUM = 0;         // the index of the wave number in each wave text line
    private final static Keys NEW_WAVE_KEY = Keys.S;
    private final static Keys SPEED_UP_KEY = Keys.L;
    private final static Keys SLOW_DOWN_KEY = Keys.K;
    private final static double SENSITIVITY_FOR_KEY = 0.5;  // Interval in seconds after which timescale is realterable
    private final static int INITIAL_NUM_LIVES = 25;
    private final static int INITIAL_MONEY = 500;
    private final static int BASE_WAVE_REWARD = 150;
    private final static int WAVE_NO_REWARD = 100;

    private double frameCount = 0;              // defined as a double to allow for slow-motion analysis upon debugging
    private double frameOfLatestChange = 0;
    private int timescale = 1;
    private int livesLeft = INITIAL_NUM_LIVES;
    private int moneyLeft = INITIAL_MONEY;
    private final TiledMap map;                 // the map upon which the level is played out
    private ArrayList<Wave> waves = new ArrayList<>();          // the waves to be performed in this level
    private ArrayList<Defender> defenders = new ArrayList<>();  // all defenders placed
    private int wavesCompleted = 0;
    private Wave currentWave;
    private boolean isActive = true;
    private boolean gameIsOver = false;                // denotes whether the overall ShadowDefend game is over
    private boolean isPlacing = false;
    private Defender placingDefender;
    private boolean lastPlaneHorizontal = false;

    private StatusPanel statusPanel = StatusPanel.getStatusPanel();
    private BuyPanel buyPanel = BuyPanel.getBuyPanel();

    /**
     * Creates a new level, and loads its respective map, reading in the wave information.
     *
     * @param levelNumber The numbered level
     */
    public Level(int levelNumber) {
        map = new TiledMap("res/levels/" + levelNumber + ".tmx");
        ArrayList<String> levelWavePlan = new ArrayList<>();

        // attempts to read in the waves text file, to be parsed into the array lists below.
        try (Scanner file = new Scanner(new FileReader(WAVES_SRC))) {
            while (file.hasNextLine()) {
                levelWavePlan.add(file.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Waves file not found.");
            e.printStackTrace();
        }

        // convert char form of the final wave's wave number to an int.
        int numOfWaves = levelWavePlan.get(levelWavePlan.size() - 1).charAt(0) - '0';

        int i, j = 0;
        for (i = 1; i <= numOfWaves; i++) {
            Wave thisWave = new Wave(map.getAllPolylines().get(0), this);
            while(j < levelWavePlan.size())  {
                String[] waveEventText = levelWavePlan.get(j).split(",");
                if (Integer.parseInt(waveEventText[INDEX_OF_WAVE_NUM]) == i) {
                    thisWave.addWaveEvent(waveEventText);
                    j++;
                } else {
                    break;
                }
            }
            waves.add(thisWave);
        }
    }

    /**
     * Updates the timescale if allowed.
     *
     * @param adjustment Indicates whether timescale is to be increased or decreased
     * @param frameCount Allows perspective of time elapsed
     */
    private void adjustTimescale(int adjustment, double frameCount) {
        if ((((adjustment == -1) && (timescale >= 2)) || (adjustment == 1) && (timescale <= 4)) && (frameCount -
                frameOfLatestChange) / ShadowDefend.FPS > SENSITIVITY_FOR_KEY) {
            frameOfLatestChange = frameCount;
            timescale += adjustment;
        }
    }

    /**
     * Checks whether the cursor is in the window's bounds.
     *
     * @param cursorPt the position of the cursor
     * @return whether the cursor is within the window's bounds
     */
    private boolean isInWindow(Point cursorPt) {
        if (cursorPt.x >= 0 && cursorPt.x <= ShadowDefend.WIDTH && cursorPt.y >= 0 && cursorPt.y <
                ShadowDefend.HEIGHT) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the cursor is over the position of a previously placed tower to ensure no overlap in placing.
     *
     * @param cursorPosition the position of the cursor
     * @return whether the cursor is over the position of a previously placed tower.
     */
    private boolean isOverExistingTower(Point cursorPosition) {
        for (Defender defender : defenders) {
            if (defender.getRect().intersects(cursorPosition)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Undertakes the subtraction of lives of the user upon an enemy's traversal of the entire polyline.
     *
     * @param penalty the penalty of the particular enemy.
     */
    public void loseLives(int penalty) {
        livesLeft -= penalty;
        if (livesLeft <= 0) {
            Window.close();
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setGameIsOver(boolean isOver) {
        gameIsOver = isOver;
    }

    public boolean isGameOver() {
        return gameIsOver;
    }

    /**
     * Updates the state of the level at each frame.
     *
     * @param input Allows alterations of state of level by keyboard input
     */
    public void update(Input input) {
        frameCount += timescale;

        if (!gameIsOver) {
            /* Controls game-play as per the keyboard input */
            if (input.isDown(NEW_WAVE_KEY) && (currentWave == null || !currentWave.isActive())) {
                currentWave = waves.get(wavesCompleted);
                currentWave.activate();
            }
            if (input.isDown(SPEED_UP_KEY)) {
                adjustTimescale(1, frameCount);
            }
            if (input.isDown(SLOW_DOWN_KEY)) {
                adjustTimescale(-1, frameCount);
            }
            if (input.isDown(MouseButtons.LEFT) && buyPanel.cursorOverDefender(input.getMousePosition())!=null
                    && !isPlacing && buyPanel.cursorOverDefender(input.getMousePosition()).getPrice() <= moneyLeft) {
                if (buyPanel.cursorOverDefender(input.getMousePosition()) instanceof Tank) {
                    placingDefender = new Tank(input.getMousePosition());
                } else if (buyPanel.cursorOverDefender(input.getMousePosition()) instanceof SuperTank) {
                    placingDefender = new SuperTank(input.getMousePosition());
                } else if (buyPanel.cursorOverDefender(input.getMousePosition()) instanceof Airplane) {
                    placingDefender = new Airplane(input.getMousePosition(), lastPlaneHorizontal);
                }
                isPlacing = true;
            }
        }

        map.draw(0, 0, 0, 0, Window.getWidth(), Window.getHeight());

        if (isPlacing) {
            // Draw the model defender where the mouse is, if in a valid position, to give option of placing it
            if (input.isDown(MouseButtons.RIGHT)) {
                // don't undertake the placing of this defender.
                isPlacing = false;
            } else if (isInWindow(input.getMousePosition())) {
                if (!map.getPropertyBoolean((int)input.getMousePosition().x, (int)input.getMousePosition().y,
                        "blocked", false)
                        && !buyPanel.getRect().intersects(input.getMousePosition())
                        && !statusPanel.getRect().intersects(input.getMousePosition())
                        && !isOverExistingTower(input.getMousePosition())) {
                    // the defender is over an appropriate tile.
                    placingDefender.setPosition(input.getMousePosition());
                    if (input.isDown(MouseButtons.LEFT)) {
                        // place the defender in this position.
                        moneyLeft -= placingDefender.getPrice();
                        defenders.add(placingDefender);
                        if (placingDefender instanceof Airplane) {
                            lastPlaneHorizontal = !(lastPlaneHorizontal);
                            Airplane newAirplane = (Airplane)placingDefender;
                            newAirplane.deploy(frameCount);
                        }
                        isPlacing = false;
                        placingDefender = null;
                    } else {
                        placingDefender.drawModel();
                    }
                }
            }
        }

        // Depending on state of wave, might refresh or terminate it the level, updating the status panel accordingly
        ArrayList<Enemy> currentEnemies = new ArrayList<Enemy>();
        if (currentWave != null) {
            if (currentWave.isActive()) {
                currentEnemies.addAll(currentWave.update(timescale, frameCount));
                statusPanel.update(wavesCompleted + 1, timescale, livesLeft, gameIsOver, isPlacing,
                        true, lastPlaneHorizontal);
            } else {
                currentWave = null;
                wavesCompleted++;
                moneyLeft += BASE_WAVE_REWARD + WAVE_NO_REWARD * wavesCompleted;
                if (wavesCompleted == waves.size()) {
                    isActive = false;
                } else {
                    statusPanel.update(wavesCompleted + 1, timescale, livesLeft, gameIsOver, isPlacing,
                            false, lastPlaneHorizontal);
                }
            }
        } else {
            statusPanel.update(wavesCompleted + 1, timescale, livesLeft, gameIsOver, isPlacing,
                    false, lastPlaneHorizontal);
        }

        // updates the state of all placed defenders
        for (Defender defender : defenders) {
            if (defender.isActive()) {
                moneyLeft += defender.update(currentEnemies, frameCount, timescale);
            }
        }

        buyPanel.update(moneyLeft);
    }
}
