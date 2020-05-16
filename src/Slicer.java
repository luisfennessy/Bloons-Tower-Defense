import bagel.map.TiledMap;
import bagel.util.Point;

import java.util.List;

/**
 * The most basic type of slicer.
 */
public class Slicer extends Enemy {

    private static final double INITIALHEALTH = 1;

    /**
     * Creates a new regular slicer.
     *
     * @param polyline The path along which an enemy travels
     * @param imagesrc The file location of the slicer's image
     * @param isActive Whether the slicer is currently traversing path
     */
    public Slicer(List<Point> polyline, String imageSrc, boolean isActive) {
        super(polyline, imageSrc, isActive);
        this.health = INITIALHEALTH;
    }
}
