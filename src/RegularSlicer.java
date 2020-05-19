import bagel.util.Point;

import java.util.List;

/**
 * The most basic type of slicer.
 */
public class RegularSlicer extends Enemy {

    private static final double INITIALHEALTH = 1;
    private static final int REWARD = 2;
    private static final int PENALTY = 1;
    private static final double SPEED = 2;


    /**
     * Creates a new regular slicer.
     *
     * @param polyline The path along which an enemy travels
     * @param imageSrc The file location of the slicer's image
     * @param isActive Whether the slicer is currently traversing path
     */
    public RegularSlicer(List<Point> polyline, String imageSrc) {
        super(polyline, imageSrc);
        this.healthRemaining = INITIALHEALTH;
    }
}
