import bagel.util.Point;

import java.util.List;

/**
 * The second largest slicer.
 */
public class MegaSlicer extends Enemy {

    private final static String MEGA_SLICER_IMAGE = "res/images/megaslicer.png";
    private static final double INITIAL_HEALTH = 2 * SuperSlicer.INITIAL_HEALTH;
    private static final int REWARD = 10;
    private static final int CHILDREN_SPAWNED = 2;
    public static final int PENALTY = CHILDREN_SPAWNED * SuperSlicer.PENALTY;
    public static final double SPEED = SuperSlicer.SPEED;

    /**
     * Creates a new mega slicer.
     *
     * @param polyline   The path along which an enemy travels
     * @param spawnEvent the spawn event in which it's spawned in.
     */
    public MegaSlicer(List<Point> polyline, SpawnEvent spawnEvent) {
        super(polyline, MEGA_SLICER_IMAGE, spawnEvent, INITIAL_HEALTH, REWARD, PENALTY, CHILDREN_SPAWNED,
                SPEED);
    }

    /**
     * Spawns a new Mega slicer at the position of its parent.
     *
     * @param polyline       its polyline, to be followed.
     * @param spawnEvent     its spawn event
     * @param nextPointIndex its next point index, inherited from its parent
     * @param currentPoint   its current point, inherited from its parent
     * @param nextPoint      its next point, inherited from its parent
     */
    public MegaSlicer(List<Point> polyline, SpawnEvent spawnEvent, int nextPointIndex, Point
            currentPoint, Point nextPoint) {
        super(polyline, MEGA_SLICER_IMAGE, spawnEvent, nextPointIndex, currentPoint, nextPoint, INITIAL_HEALTH, REWARD,
                PENALTY, CHILDREN_SPAWNED, SPEED);
    }

}
