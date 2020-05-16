import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

/**
 * The enemies of ShadowDefend, who aim to traverse the map.
 */
public abstract class Enemy extends Sprite {

    private boolean isActive;
    private Vector2 direction;
    private List<Point> polyline;
    private Point currentPoint;
    private Point nextPoint;
    private int nextPointIndex;

    protected double health;

    private int level;

    /**
     * Creates a new instance of an enemy.
     *
     * @param polyline The path along which an enemy travels
     * @param imagesrc The file location of the slicer's image
     * @param isActive Whether the slicer is currently traversing path
     */
    public Enemy(List<Point> polyline, String imageSrc, boolean isActive) {
        super(polyline.get(0), imageSrc);
        this.isActive = isActive;
        nextPointIndex = 1;
        this.polyline = polyline;
        currentPoint = polyline.get(0);
        nextPoint = polyline.get(1);
        direction = new Vector2(nextPoint.x - currentPoint.x, nextPoint.y - currentPoint.y).normalised();
    }

    public void setDirection() {
        nextPointIndex++;
        nextPoint = polyline.get(nextPointIndex);
        direction = new Vector2(nextPoint.x - currentPoint.x, nextPoint.y - currentPoint.y).normalised();
    }
}
