import bagel.DrawOptions;
import bagel.Keys;
import bagel.map.TiledMap;
import bagel.util.Point;
import bagel.util.Vector2;

import java.lang.Math;

public class Wave {

    private final double milliToNormal = Math.pow(10, -3);  // Allows simple conversion from milli-units to std units
    private static final Keys slicerWaveKey = Keys.S;       // Key pressed to commence wave of slicers
    private final int numberSlicers = 5;                    // Quantity of slicers in slicer wave
    private final double onePixel = 1;                      // Width of 1 pixel
    private final double sensitivityForKey = 0.2;           // Minimum interval between timescale alterations
    private final double standardSpawnDelay = 5;            // Non-timescale-affected delay between slicer spawns
    private final int standardTimescale = 1;                // Standard (and minimum) timescale multiplier

    private int timescale;                                  // The adjustment of the pace of each wave
    private double prevTimeElapsed;                         // Prior time elapsed; counters timescale disruption
    private double timeOfLatestChange;                      // Time signature of most recent spawn/timescale change
    private Enemy[] enemies;
    private int enemiesInWave;
    private int enemiesReleased;
    private int enemiesDeactivated;
    private double spawnDelay;
    private boolean isActive = true;


    /* Constructs and initialises wave of enemies, as outlined by the wave's type of enemy */
    public Wave(Keys enemyType, long startTime, TiledMap map) {
        timeOfLatestChange = startTime * milliToNormal;
        timescale = standardTimescale;
        spawnDelay = standardSpawnDelay;
        enemiesInWave = numberSlicers;
        if (enemyType == slicerWaveKey) {
            enemies = new Slicer[numberSlicers];
            enemies[0] = new Slicer("res/images/slicer.png", true, map);
            int i;
            for (i=1; i<numberSlicers; i++) {
                enemies[i] = new Slicer("res/images/slicer.png", false, map);
            }
        }
        enemiesReleased = 1;
        enemiesDeactivated = 0;
        prevTimeElapsed = 0;
    }


    /* Between each frame, ensures enemies traverse path appropriately */
    public void refreshEnemies(long currentTime, int polylineLength) {

        /* activates new enemy once spawn delay has elapsed */
        if (((spawnDelay - prevTimeElapsed) / timescale <  currentTime * milliToNormal - timeOfLatestChange) &&
                (enemiesReleased < enemiesInWave)) {
            timeOfLatestChange = currentTime * milliToNormal;
            enemies[enemiesReleased].activate();
            enemiesReleased++;
            prevTimeElapsed = 0;
        }

        /* For each active enemy, move forwards by appropriate pixels until it reaches end of polyline */
        for (Enemy enemy : enemies) {
            if (enemy.isActive()) {
                if (enemy.getCurrentPoint().equals(enemy.getNextPoint())) {
                    if ((enemy.getNextPointIndex() < polylineLength - 1) && (enemy.isActive())) {
                        enemy.setDirection();
                    } else {
                        /* enemy has traversed whole polyline */
                        enemy.deactivate();
                        enemiesDeactivated++;
                        if (enemiesDeactivated == enemiesInWave) {
                            isActive = false;
                            timeOfLatestChange = currentTime * milliToNormal;
                            prevTimeElapsed = 0;
                        }
                    }
                }
                if (enemy.getCurrentPoint().distanceTo(enemy.getNextPoint()) <= onePixel * timescale) {
                    /* enemy close enough to reconnect to polyline's next point */
                    enemy.setCurrentPoint(enemy.getNextPoint().x, enemy.getNextPoint().y);
                } else {
                    Point prevPoint = enemy.getCurrentPoint();
                    Vector2 movement = enemy.getDirection().mul(timescale);
                    enemy.setCurrentPoint(prevPoint.x + movement.x, prevPoint.y + movement.y);
                }
                /* ensures rotation of enemy matches its direction */
                DrawOptions rotation = new DrawOptions().setRotation(enemy.getDirectionFromXAxis());
                enemy.draw(enemy.getCurrentPoint().x, enemy.getCurrentPoint().y, rotation);
            }
        }
    }


    /* Adjusts timescale, aggregating prior time passed to ensure timely spawning of subsequent enemies */
    public void adjustTimescale(int adjustment, long currentTime) {
        if ((((adjustment == -1) && (timescale >= 2)) || (adjustment == 1)) && (currentTime * milliToNormal -
                timeOfLatestChange > sensitivityForKey)) {
            prevTimeElapsed += timescale * (currentTime * milliToNormal - timeOfLatestChange);
            timeOfLatestChange = currentTime * milliToNormal;
            timescale += adjustment;
        }
    }

    public boolean isActive() {
        return isActive;
    }
}
