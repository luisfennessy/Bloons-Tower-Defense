import bagel.DrawOptions;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

/**
 * The enemies of ShadowDefend, who aim to traverse the map.
 */
public abstract class Enemy extends Sprite {

    private boolean isActive;
    private List<Point> polyline;
    private Point currentPoint;
    private Point nextPoint;
    private int nextPointIndex;
    private double angle;

    private double healthRemaining;


    /**
     * Creates a new instance of an enemy.
     *
     * @param polyline The path along which an enemy travels
     * @param imageSrc The file location of the slicer's image
     */
    public Enemy(List<Point> polyline, String imageSrc) {
        super(polyline.get(0), imageSrc);
        isActive = false;
        nextPointIndex = 1;
        this.polyline = polyline;
        currentPoint = polyline.get(0);
        nextPoint = polyline.get(1);
    }

    public void activate() {
        isActive = true;
        this.setAngle(Math.atan2(nextPoint.y - currentPoint.y, nextPoint.x - currentPoint.x));
    }

    public void deactivate() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public Point getNextPoint() {
        return nextPoint;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public void changeMovement() {
        currentPoint = polyline.get(nextPointIndex);
        nextPointIndex++;
        nextPoint = polyline.get(nextPointIndex);
        this.setAngle(Math.atan2(nextPoint.y - currentPoint.y, nextPoint.x - currentPoint.x));
    }

    @Override
    public void move(Vector2 dx) {
        super.move(dx);
        Point prevPoint = currentPoint;
        currentPoint = new Point(prevPoint.x + dx.x, prevPoint.y + dx.y);
    }

    public void die() {

    }

}
