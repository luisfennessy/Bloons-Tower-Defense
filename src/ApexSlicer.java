import bagel.util.Point;

import java.util.List;

/**
 * The largest slicer.
 */
public class ApexSlicer extends Enemy {

    private static final double INITIALHEALTH = 25;
    private static final int REWARD = 150;
    private static final int PENALTY = 16;
    private static final double SPEED = 0.75;


    /**
     * Creates a new apex slicer.
     *
     * @param polyline The path along which an enemy travels
     * @param imageSrc The file location of the slicer's image
     * @param isActive Whether the slicer is currently traversing path
     */
    public ApexSlicer(List<Point> polyline, String imageSrc) {
        super(polyline, imageSrc);
        this.healthRemaining = INITIALHEALTH;
    }
}
