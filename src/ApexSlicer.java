import bagel.util.Point;

import java.util.List;

/**
 * The largest slicer.
 */
public class ApexSlicer extends Enemy {

    private final static String APEX_SLICER_IMAGE = "res/images/apexslicer.png";
    private static final double INITIAL_HEALTH = 25 * RegularSlicer.INITIAL_HEALTH;
    private static final int REWARD = 150;
    private static final int CHILDREN_SPAWNED = 4;
    private static final int PENALTY = CHILDREN_SPAWNED * MegaSlicer.PENALTY;
    private static final double SPEED = MegaSlicer.SPEED / 2;

    /**
     * Creates a new apex slicer.
     *
     * @param polyline   The path along which an enemy travels
     * @param spawnEvent the spawn event in which it's spawned in.
     */
    public ApexSlicer(List<Point> polyline, SpawnEvent spawnEvent) {
        super(polyline, APEX_SLICER_IMAGE, spawnEvent, INITIAL_HEALTH, REWARD, PENALTY, CHILDREN_SPAWNED, SPEED);
    }
}
