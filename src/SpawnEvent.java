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
    private final static double MILLI_TO_NORMAL = 0.001;

    // Identifiers of a particular slicer types in wave text file
    private final static String REG_SLICER_NAME = "slicer";
    private final static String SUPER_SLICER_NAME = "superslicer";
    private final static String MEGA_SLICER_NAME = "megaslicer";
    private final static String APEX_SLICER_NAME = "apexslicer";

    // The file location of each slicer's image
    private final static String REG_SLICER_IMAGE = "res/images/slicer.png";
    private final static String SUPER_SLICER_IMAGE = "res/images/superslicer.png";
    private final static String MEGA_SLICER_IMAGE = "res/images/megaslicer.png";
    private final static String APEX_SLICER_IMAGE = "res/images/apexslicer.png";

    private int numSpawned = 0;
    private int numToSpawn;
    private int numDeactivated = 0;
    private int spawnDelay;
    private double frameOfLatestChange;
    private boolean stillRunning;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private List<Point> polyline;

    /**
     * Creates a new spawn event, specific to the mentioned enemy type.
     *
     * @param eventInfo Describes the properties of the wave event
     * @param polyline The path along which an enemy travels
     */
    public SpawnEvent(String[] eventInfo, List<Point> polyline) {
        super(eventInfo);
        this.polyline = polyline;
        numToSpawn = Integer.parseInt(eventInfo[INDEX_OF_NUM_SPAWN]);
        int i;
        // Adds to ArrayList of enemies, as per specified slicer type
        if (eventInfo[INDEX_OF_ENEMY_TYPE].equals(REG_SLICER_NAME)) {
            for(i=0; i<numToSpawn; i++) {
                enemies.add(new RegularSlicer(polyline, REG_SLICER_IMAGE));
            }
        } else if (eventInfo[INDEX_OF_ENEMY_TYPE].equals(SUPER_SLICER_NAME)) {
            for(i=0; i<numToSpawn; i++) {
                enemies.add(new SuperSlicer(polyline, SUPER_SLICER_IMAGE));
            }
        } else if (eventInfo[INDEX_OF_ENEMY_TYPE].equals(MEGA_SLICER_NAME)) {
            for(i=0; i<numToSpawn; i++) {
                enemies.add(new MegaSlicer(polyline, MEGA_SLICER_IMAGE));
            }
        } else if (eventInfo[INDEX_OF_ENEMY_TYPE].equals(APEX_SLICER_NAME)) {
            for(i=0; i<numToSpawn; i++) {
                enemies.add(new ApexSlicer(polyline, APEX_SLICER_IMAGE));
            }
        }
        spawnDelay = Integer.parseInt(eventInfo[INDEX_OF_SPAWN_DELAY]);
        spawnDelay *=  MILLI_TO_NORMAL;
    }

    @Override
    public boolean isStillRunning() {
        return stillRunning;
    }

    @Override
    public void activate(double frameCount) {
        super.activate(frameCount);
        frameOfLatestChange = frameCount;
        stillRunning = true;
        enemies.get(numSpawned).activate();
        numSpawned++;
    }

    @Override
    public void update(int timescale, double frameCount) {

        /* activates new enemy once spawn delay has elapsed */
        if ((spawnDelay <= (frameCount - frameOfLatestChange)/ShadowDefend.FPS) && (numSpawned < numToSpawn)) {
            frameOfLatestChange = frameCount;
            enemies.get(numSpawned).activate();
            numSpawned++;
        }

        /* For each active enemy, move forwards by appropriate pixels until it reaches end of polyline */
        for (Enemy enemy : enemies) {
            if (enemy.isActive()) {
                if (enemy.getCurrentPoint().equals(enemy.getNextPoint())) {
                    if (!enemy.getNextPoint().equals(polyline.get(polyline.size() - 1)) && enemy.isActive()) {
                        enemy.changeMovement();
                    } else {
                        /* enemy has traversed whole polyline */
                        enemy.deactivate();
                        numDeactivated++;
                        if (numDeactivated == numToSpawn) {
                            this.deactivate();
                            frameOfLatestChange = frameCount;
                        }
                    }
                }
                if (enemy.getCurrentPoint().distanceTo(enemy.getNextPoint()) <= ONE_PIXEL * timescale) {
                    /* enemy close enough to reconnect to polyline's next point */
                    enemy.move(new Vector2(enemy.getNextPoint().x - enemy.getCurrentPoint().x,
                            enemy.getNextPoint().y - enemy.getCurrentPoint().y));
                } else {
                    Vector2 direction = new Vector2(Math.cos(enemy.getAngle()), Math.sin(enemy.getAngle()));
                    enemy.move(direction.mul(timescale));
                }
                /* ensures rotation of enemy matches its direction */
                DrawOptions rotation = new DrawOptions().setRotation(enemy.getAngle());
                enemy.getImage().draw(enemy.getCurrentPoint().x, enemy.getCurrentPoint().y, rotation);
            }
        }
    }
}
