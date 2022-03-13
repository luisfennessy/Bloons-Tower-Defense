import bagel.util.Point;

import java.util.List;

/**
 * The most basic type of slicer.
 */
public class RegularSlicer extends Enemy {

    private final static String REG_SLICER_IMAGE = "res/images/slicer.png";
    public static final double INITIAL_HEALTH = 1;
    public static final int REWARD = 2;
    public static final int PENALTY = 1;
    private static final int CHILDREN_SPAWNED = 0;
    public static final double SPEED = 1;


    /**
     * Creates a new regular slicer.
     *
     * @param polyline   The path along which an enemy travels
     * @param spawnEvent the spawn event in which it's spawned in.
     */
    public RegularSlicer(List<Point> polyline, SpawnEvent spawnEvent) {
        super(polyline, REG_SLICER_IMAGE, spawnEvent, INITIAL_HEALTH, REWARD, PENALTY, CHILDREN_SPAWNED, SPEED);
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
    public RegularSlicer(List<Point> polyline, SpawnEvent spawnEvent, int nextPointIndex, Point currentPoint,
                         Point nextPoint) {
        super(polyline, REG_SLICER_IMAGE, spawnEvent, nextPointIndex, currentPoint, nextPoint, INITIAL_HEALTH, REWARD,
                PENALTY, CHILDREN_SPAWNED, SPEED);
    }
}

