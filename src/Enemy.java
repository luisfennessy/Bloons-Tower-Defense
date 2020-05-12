import bagel.Image;
import bagel.map.TiledMap;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

public abstract class Enemy extends Image {

    private boolean isActive;
    private Vector2 direction;
    private List<Point> polyline;
    private Point currentPoint;
    private Point nextPoint;
    private int nextPointIndex;

    /* Constructs a particular type of enemy */
    public Enemy(String filename, boolean isActive, TiledMap map) {
        super(filename);
        this.isActive = isActive;
        nextPointIndex = 1;
        polyline = map.getAllPolylines().get(0);
        currentPoint = polyline.get(0);
        nextPoint = polyline.get(1);
        direction = new Vector2(nextPoint.x - currentPoint.x, nextPoint.y - currentPoint.y).normalised();
    }

    public Point getNextPoint() {
        return nextPoint;
    }

    public int getNextPointIndex() {
        return nextPointIndex;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public double getDirectionFromXAxis() {
        return Math.atan2(direction.y, direction.x);
    }

    /* Assigns each enemy's direction with the appropriate unit vector */
    public void setDirection() {
        nextPointIndex++;
        nextPoint = polyline.get(nextPointIndex);
        direction = new Vector2(nextPoint.x - currentPoint.x, nextPoint.y - currentPoint.y).normalised();
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(double x, double y) {
        currentPoint = new Point(x, y);
    }

    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }
}
