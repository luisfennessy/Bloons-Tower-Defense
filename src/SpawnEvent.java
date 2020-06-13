import bagel.DrawOptions;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * An event within a wave which prompts the spawn of particular enemies over a set interval.
 */
public class SpawnEvent extends WaveEvent {

    private final static int INDEX_OF_NUM_SPAWN = 2;      // The index of the number to spawn in an event's description
    private final static int INDEX_OF_ENEMY_TYPE = 3;     // The index of the enemy type in an event's description
    private final static int INDEX_OF_SPAWN_DELAY = 4;    // The index of the spawn delay in an event's description
    private final static int ONE_PIXEL = 1;
    private final static int SPREAD_DIST = 6;            // Minimum x & y distance two child slicers are spawned apart

    // Identifiers of a particular slicer types in wave text file
    private final static String REG_SLICER_NAME = "slicer";
    private final static String SUPER_SLICER_NAME = "superslicer";
    private final static String MEGA_SLICER_NAME = "megaslicer";
    private final static String APEX_SLICER_NAME = "apexslicer";

    private int numSpawned = 0;
    private int numToSpawn;
    private int numDeactivated = 0;
    private int numToDeactivate;
    private double spawnDelay;
    private double frameOfLatestChange;
    private boolean stillRunning;               // specifies whether some of spawn event's slicers are are still active
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private List<Point> polyline;
    private Level level;

    /**
     * Creates a new spawn event, specific to the mentioned enemy type.
     *
     * @param eventInfo Describes the properties of the wave event
     * @param polyline  The path along which an enemy travels
     * @param level     The level of the spawn event
     */
    public SpawnEvent(String[] eventInfo, List<Point> polyline, Level level) {
        super();
        this.polyline = polyline;
        this.level = level;
        numToSpawn = numToDeactivate = Integer.parseInt(eventInfo[INDEX_OF_NUM_SPAWN]);
        int i;
        // adds to ArrayList of enemies, as per specified slicer type
        switch (eventInfo[INDEX_OF_ENEMY_TYPE]) {
            case REG_SLICER_NAME:
                for (i = 0; i < numToSpawn; i++) {
                    enemies.add(new RegularSlicer(polyline, this));
                }
                break;
            case SUPER_SLICER_NAME:
                for (i = 0; i < numToSpawn; i++) {
                    enemies.add(new SuperSlicer(polyline, this));
                }
                break;
            case MEGA_SLICER_NAME:
                for (i = 0; i < numToSpawn; i++) {
                    enemies.add(new MegaSlicer(polyline, this));
                }
                break;
            case APEX_SLICER_NAME:
                for (i = 0; i < numToSpawn; i++) {
                    enemies.add(new ApexSlicer(polyline, this));
                }
                break;
        }
        spawnDelay = Integer.parseInt(eventInfo[INDEX_OF_SPAWN_DELAY]);
        spawnDelay *=  ShadowDefend.MILLI_TO_NORMAL;
    }

    /**
     * Kills the specified slicer. Updates slicers deactivated and slicers to be deactivated accordingly.
     *
     * @param enemy The slicer which is to be killed
     */
    public void killSlicer(Enemy enemy) {
        int i;
        enemy.deactivate();
        numDeactivated++;
        double spreadOffset = (enemy.getChildrenNum() - 1) / 2.0;
        if (enemy instanceof RegularSlicer) {
            if (numDeactivated == numToDeactivate) {
                stillRunning = false;
            }
        // if enemy is not a regular slicer, spawn its child slicers at a similar position, but spread out
        } else if (enemy instanceof SuperSlicer) {
            for (i = 0; i < enemy.getChildrenNum(); i++) {
                enemies.add(new RegularSlicer(polyline, this, enemy.getNextPointIndex(),
                        new Point(enemy.getCurrentPoint().x + (i - spreadOffset) * SPREAD_DIST,
                                enemy.getCurrentPoint().y + (i - spreadOffset) * SPREAD_DIST),
                        enemy.getNextPoint()));
                numToDeactivate++;
            }
        } else if (enemy instanceof MegaSlicer) {
            for (i = 0; i < enemy.getChildrenNum(); i++) {
                enemies.add(new SuperSlicer(polyline, this, enemy.getNextPointIndex(),
                        new Point(enemy.getCurrentPoint().x + (i - spreadOffset) * SPREAD_DIST,
                                enemy.getCurrentPoint().y + (i - spreadOffset) * SPREAD_DIST),
                        enemy.getNextPoint()));
                numToDeactivate++;
            }
        } else if (enemy instanceof ApexSlicer) {
            for (i = 0; i < enemy.getChildrenNum(); i++) {
                enemies.add(new MegaSlicer(polyline, this, enemy.getNextPointIndex(),
                        new Point(enemy.getCurrentPoint().x + (i - spreadOffset) * SPREAD_DIST,
                                enemy.getCurrentPoint().y + (i - spreadOffset) * SPREAD_DIST),
                        enemy.getNextPoint()));
                numToDeactivate++;
            }
        }
    }


    /**
     * A spawn event is still running if its enemies are still active on the map.
     *
     * @return whether the spawn event is still running
     */
    @Override
    public boolean isStillRunning() {
        return stillRunning;
    }

    /**
     * Upon activation, will also spawn its first enemy and start timing the length between two spawns.
     *
     * @param frameCount ensures timely spawning of 2nd enemy
     */
    @Override
    public void activate(double frameCount) {
        if (enemies.size() > 0) {
            super.activate(frameCount);
            frameOfLatestChange = frameCount;
            stillRunning = true;
            enemies.get(numSpawned).activate();
            numSpawned++;
        }
    }

    /**
     * Updates the state of a spawn event, moving, deactivating and spawning enemies when appropriate.
     *
     * @param timescale the pace at which the level is being run
     * @param frameCount ensures timely spawning of enemies
     * @return all enemies in the spawn event
     */
    @Override
    public ArrayList<Enemy> update(int timescale, double frameCount) {

        /* activates new enemy once spawn delay has elapsed */
        if ((spawnDelay <= (frameCount - frameOfLatestChange)/ShadowDefend.FPS) && (numSpawned < numToSpawn)) {
            frameOfLatestChange = frameCount;
            enemies.get(numSpawned).activate();
            numSpawned++;
            if (numSpawned == numToSpawn) {
                this.deactivate();
            }
        }

        /* For each active enemy, move forward by appropriate pixels until it reaches end of polyline */
        for (Enemy enemy : enemies) {
            if (enemy.isActive()) {
                if (enemy.getCurrentPoint().equals(enemy.getNextPoint())) {
                    if (!enemy.getNextPoint().equals(polyline.get(polyline.size() - 1)) && enemy.isActive()) {
                        enemy.changeMovement();
                    } else {
                        // enemy has traversed whole polyline - lose appropriate amount of lives and deactivate spawn
                        // event if all enemies are deactivated.
                        enemy.deactivate();
                        numDeactivated++;
                        level.loseLives(enemy.getPenalty());
                        if (numDeactivated == numToDeactivate) {
                            stillRunning = false;
                            frameOfLatestChange = frameCount;
                        }
                    }
                }
                if (enemy.getCurrentPoint().distanceTo(enemy.getNextPoint()) <= ONE_PIXEL * timescale * enemy.getSpeed()) {
                    // enemy close enough to reconnect to polyline's next point
                    enemy.move(new Vector2(enemy.getNextPoint().x - enemy.getCurrentPoint().x,
                            enemy.getNextPoint().y - enemy.getCurrentPoint().y));
                } else {
                    // move enemy closer to its next point
                    Vector2 direction = new Vector2(Math.cos(enemy.getAngle()), Math.sin(enemy.getAngle()));
                    enemy.move(direction.mul(timescale).mul(enemy.getSpeed()));
                }
                // ensures rotation of enemy matches its direction
                DrawOptions rotation = new DrawOptions().setRotation(enemy.getAngle());
                enemy.getImage().draw(enemy.getCurrentPoint().x, enemy.getCurrentPoint().y, rotation);
            }
        }
        return enemies;
    }
}
