import bagel.util.Point;

import java.util.List;

/**
 * The second smallest slicer.
 */
public class SuperSlicer extends Enemy {

    private final static String SUPER_SLICER_IMAGE = "res/images/superslicer.png";
    public static final double INITIAL_HEALTH = RegularSlicer.INITIAL_HEALTH;
    private static final int REWARD = 15;
    private static final int CHILDREN_SPAWNED = 2;
    public static final int PENALTY = CHILDREN_SPAWNED * RegularSlicer.PENALTY;
    public static final double SPEED = 3 * RegularSlicer.SPEED / 4;


    /**
     * Creates a new super slicer.
     *
     * @param polyline The path along which an enemy travels
     * @param spawnEvent the spawn event in which it's spawned in.
     */
    public SuperSlicer(List<Point> polyline, SpawnEvent spawnEvent) {
        super(polyline, SUPER_SLICER_IMAGE, spawnEvent, INITIAL_HEALTH, REWARD, PENALTY, CHILDREN_SPAWNED, SPEED);
    }

    /**
     * Creates and spawns a new Mega slicer at the position of its parent.
     *
     * @param polyline       its polyline, to be followed.
     * @param spawnEvent     its spawn event
     * @param nextPointIndex its next point index, inherited from its parent
     * @param currentPoint   its current point, inherited from its parent
     * @param nextPoint      its next point, inherited from its parent
     */
    public SuperSlicer(List<Point> polyline, SpawnEvent spawnEvent, int nextPointIndex, Point currentPoint, Point
            nextPoint) {
        super(polyline, SUPER_SLICER_IMAGE, spawnEvent, nextPointIndex, currentPoint, nextPoint, INITIAL_HEALTH,
                REWARD, PENALTY, CHILDREN_SPAWNED, SPEED);
    }

}
