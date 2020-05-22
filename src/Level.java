import bagel.Input;
import bagel.Keys;
import bagel.MouseButtons;
import bagel.Window;
import bagel.map.TiledMap;
import bagel.util.Point;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * A level of ShadowDefend game.
 */
public class Level {

    private final static String WAVES_SRC = "res/levels/waves.txt";

    private final static int INDEX_OF_WAVE_NUM = 0;
    private final static Keys NEW_WAVE_KEY = Keys.S;
    private final static Keys SPEED_UP_KEY = Keys.L;
    private final static Keys SLOW_DOWN_KEY = Keys.K;
    private final static double SENSITIVITY_FOR_KEY = 0.5;  // Interval in seconds after which timescale is realterable
    private final static int INITIAL_NUM_LIVES = 25;
    private final static int INITIAL_MONEY = 500;

    private final static String CURRENT_WAVE_STATUS = "Wave in Progress";
    private final static String PLACING_STATUS = "Placing";
    private final static String WINNER_STATUS = "Winner!";
    private final static String WAITING_STATUS = "Awaiting Start";
    /*private final static Map<String, Integer> pricesMap = new HashMap<String, Integer>() {
        {
            put("Tank", 300);
            put("Super Tank", 600);
            put("Airplane", 500);
        }
    };*/

    private double frameCount = 0;
    private double frameOfLatestChange = 0;
    private int timescale = 1;
    private int livesLeft = INITIAL_NUM_LIVES;
    private int moneyLeft = INITIAL_MONEY;
    private final TiledMap map;
    private ArrayList<Wave> waves = new ArrayList<>();
    private ArrayList<Defender> defenders = new ArrayList<>();
    private int wavesCompleted = 0;
    private Wave currentWave;
    private boolean isActive = true;
    private boolean gameIsOver = false;
    private boolean isPlacing = false;
    private Defender placingDefender;
    private StatusPanel statusPanel = new StatusPanel();
    private BuyPanel buyPanel = new BuyPanel();

    /**
     * Creates a new level.
     *
     * @param levelNumber The numbered level
     */
    public Level(int levelNumber) {
        map = new TiledMap("res/levels/" + levelNumber + ".tmx");
        ArrayList<String> levelWavePlan = new ArrayList<>();

        try (Scanner file = new Scanner(new FileReader(WAVES_SRC))) {
            while (file.hasNextLine()) {
                levelWavePlan.add(file.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Waves file not found.");
            e.printStackTrace();
        }

        // convert char form of the final wave's wave number to an int
        int numOfWaves = levelWavePlan.get(levelWavePlan.size() - 1).charAt(0) - '0';
        int i, j = 0;
        for (i = 1; i <= numOfWaves; i++) {
            Wave thisWave = new Wave(map.getAllPolylines().get(0), i);
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

    public boolean isActive() {
        return isActive;
    }

    public void setGameIsOver(boolean isOver) {
        gameIsOver = isOver;
    }

    /**
     * Updates the state of the level at each frame.
     *
     * @param input Allows alterations of state of level by keyboard input
     */
    public void update(Input input) {
        frameCount += timescale;

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
                && !isPlacing) {
            if (buyPanel.cursorOverDefender(input.getMousePosition()) instanceof Tank) {
                placingDefender = new Tank(input.getMousePosition());
            } else if (buyPanel.cursorOverDefender(input.getMousePosition()) instanceof SuperTank) {
                placingDefender = new SuperTank(input.getMousePosition());
            } else {
                placingDefender = new Airplane(input.getMousePosition());
            }
        }

        map.draw(0, 0, 0, 0, Window.getWidth(), Window.getHeight());
        buyPanel.update(moneyLeft);

        if (isPlacing) {
            if (!map.getPropertyBoolean((int)input.getMousePosition().x, (int)input.getMousePosition().y,
                    "blocked", false) && !buyPanel.getRect().intersects(input.getMousePosition())
                    && !statusPanel.getRect().intersects(input.getMousePosition())) {
                placingDefender.setPosition(input.getMousePosition());
                if (input.isDown(MouseButtons.LEFT)) {
                    moneyLeft -= placingDefender.purchase();
                    defenders.add(placingDefender);
                    isPlacing = false;
                    placingDefender = null;
                } else {

                }
            }
        }

        /* Depending on state of wave, might refresh or terminate it */
        if (currentWave!=null) {
            if (currentWave.isActive()) {
                currentWave.update(timescale, frameCount);
                statusPanel.update(wavesCompleted + 1, CURRENT_WAVE_STATUS, timescale, livesLeft);
            } else {
                currentWave = null;
                wavesCompleted++;
                if (wavesCompleted == waves.size()) {
                    isActive = false;
                } else {
                    statusPanel.update(wavesCompleted + 1, WAITING_STATUS, timescale, livesLeft);
                }
            }
        } else {
            statusPanel.update(wavesCompleted + 1, WAITING_STATUS, timescale, livesLeft);
        }
    }
}
