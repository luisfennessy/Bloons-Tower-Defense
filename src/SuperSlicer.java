import bagel.util.Point;

import java.util.List;

/**
 * The second smallest slicer.
 */
public class SuperSlicer extends Enemy {

    private static final double INITIAL_HEALTH = 1;
    private static final int REWARD = 15;
    private static final int PENALTY = 2;
    private static final double SPEED = 1.5;


    /**
     * Creates a new super slicer.
     *
     * @param polyline The path along which an enemy travels
     * @param imageSrc The file location of the slicer's image
     * @param isActive Whether the slicer is currently traversing path
     */
    public SuperSlicer(List<Point> polyline, String imageSrc) {
        super(polyline, imageSrc);
        setHealthRemaining(INITIAL_HEALTH);
    }
}
