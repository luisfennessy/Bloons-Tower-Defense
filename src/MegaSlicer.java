import bagel.util.Point;

import java.util.List;

/**
 * The second largest slicer.
 */
public class MegaSlicer extends Enemy {

    private static final double INITIAL_HEALTH = 2;
    private static final int REWARD = 10;
    private static final int PENALTY = 4;
    private static final double SPEED = 1.5;


    /**
     * Creates a new mega slicer.
     *
     * @param polyline The path along which an enemy travels
     * @param imageSrc The file location of the slicer's image
     */
    public MegaSlicer(List<Point> polyline, String imageSrc) {
        super(polyline, imageSrc);
        this.healthRemaining = INITIAL_HEALTH;
    }
}
