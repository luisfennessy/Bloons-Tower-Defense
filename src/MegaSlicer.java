import bagel.util.Point;

import java.util.List;

/**
 * The second largest slicer.
 */
public class MegaSlicer extends Enemy {

    private static final double INITIALHEALTH = 2;
    private static final int REWARD = 10;
    private static final int PENALTY = 4;
    private static final double SPEED = 1.5;


    /**
     * Creates a new mega slicer.
     *
     * @param polyline The path along which an enemy travels
     * @param imageSrc The file location of the slicer's image
     * @param isActive Whether the slicer is currently traversing path
     */
    public MegaSlicer(List<Point> polyline, String imageSrc) {
        super(polyline, imageSrc);
        this.healthRemaining = INITIALHEALTH;
    }
}
