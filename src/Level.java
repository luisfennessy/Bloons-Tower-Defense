import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.map.TiledMap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * A level of ShadowDefend game.
 */
public class Level {

    private static final String WAVES_TEXT_FILE = "res/levels/waves.txt";
    private static final int INDEX_OF_WAVE_NUM = 0;
    private static final Keys NEW_WAVE_KEY = Keys.S;
    private static final Keys SPEED_UP_KEY = Keys.L;
    private static final Keys SLOW_DOWN_KEY = Keys.K;
    private static final double SENSITIVITY_FOR_KEY = 0.2;  // Interval in seconds after which timescale is realterable

    private double frameCount;
    private double frameOfLatestChange;
    private int timescale;
    private TiledMap map;
    private ArrayList<Wave> waves = new ArrayList<>();

    private int wavesCompleted;
    private Wave currentWave;
    private boolean isActive;

    /**
     * Creates a new level.
     *
     * @param levelNumber The numbered level
     */
    public Level(int levelNumber) {
        map = new TiledMap("res/levels/" + levelNumber + ".tmx");
        ArrayList<String> levelWavePlan = new ArrayList<>();
        timescale = 1;
        frameOfLatestChange = 0;
        frameCount = 0;
        isActive = true;

        try (Scanner file = new Scanner(new FileReader(WAVES_TEXT_FILE))) {
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
            Wave thisWave = new Wave(map.getAllPolylines().get(0));
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
        wavesCompleted = 0;
    }

    /**
     * Updates the timescale if allowed.
     *
     * @param adjustment Indicates whether timescale is to be increased or decreased
     * @param frameCount Allows perspective of time elapsed
     */
    public void adjustTimescale(int adjustment, double frameCount) {
        if ((((adjustment == -1) && (timescale >= 2)) || (adjustment == 1)) && (frameCount - frameOfLatestChange) /
                ShadowDefend.FPS > SENSITIVITY_FOR_KEY) {
            frameOfLatestChange = frameCount;
            timescale += adjustment;
        }
    }

    public boolean isActive() {
        return isActive;
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
        if (input.isDown(SPEED_UP_KEY) && currentWave!=null) {
            adjustTimescale(1, frameCount);
        }
        if (input.isDown(SLOW_DOWN_KEY) && currentWave!=null) {
            adjustTimescale(-1, frameCount);
        }

        map.draw(0, 0, 0, 0, Window.getWidth(), Window.getHeight());

        /* Depending on state of wave, might refresh or terminate it */
        if (currentWave!=null) {
            if (currentWave.isActive()) {
                currentWave.update(timescale, frameCount);
            } else {
                currentWave = null;
                wavesCompleted++;
                if (wavesCompleted == waves.size()) {
                    isActive = false;
                }
            }
        }
    }
}
