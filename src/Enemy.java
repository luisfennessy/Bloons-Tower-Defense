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
    private double angle;               // the angle of the direction of the slicer's movement.
    private SpawnEvent spawnEvent;

    private double healthRemaining;
    private int reward;
    private int penalty;
    private int childrenSpawned;
    private double speed;


    /**
     * Creates a new instance of an enemy at the beginning of the polyline.
     *
     * @param polyline        The path along which an enemy travels
     * @param imageSrc        The file location of the enemy's image
     * @param spawnEvent      the spawn event of the enemy
     * @param initialHealth   enemy's initial health
     * @param reward          enemy's reward for user upon its death
     * @param penalty         enemy's penalty for the user, when is reaches the polyline's end
     * @param childrenSpawned enemy's children spawned upon its death
     * @param speed           enemy's speed
     */
    public Enemy(List<Point> polyline, String imageSrc, SpawnEvent spawnEvent, double initialHealth, int reward,
                 int penalty, int childrenSpawned, double speed) {
        super(polyline.get(0), imageSrc);
        isActive = false;
        nextPointIndex = 1;
        this.polyline = polyline;
        currentPoint = polyline.get(0);
        nextPoint = polyline.get(1);
        this.spawnEvent = spawnEvent;
        this.healthRemaining = initialHealth;
        this.reward = reward;
        this.penalty = penalty;
        this.childrenSpawned = childrenSpawned;
        this.speed = speed;
    }

    /**
     * Spawns an enemy at the point of death of its parent enemy, it's polyline position to be inherited.
     *
     * @param polyline        The path along which an enemy travels
     * @param imageSrc        The file location of the enemy's image
     * @param spawnEvent      the spawn event of the enemy
     * @param nextPointIndex  the next point index of the parent
     * @param currentPoint    the current point of the parent
     * @param nextPoint       the next point of the parent
     * @param initialHealth   enemy's initial health
     * @param reward          enemy's reward for user upon its death
     * @param penalty         enemy's penalty for the user, when is reaches the polyline's end
     * @param childrenSpawned enemy's children spawned upon its death
     * @param speed           enemy's speed
     */
    public Enemy(List<Point> polyline, String imageSrc, SpawnEvent spawnEvent, int nextPointIndex, Point currentPoint,
                 Point nextPoint, double initialHealth, int reward, int penalty, int childrenSpawned, double speed) {
        super(currentPoint, imageSrc);
        isActive = true;
        this.nextPointIndex = nextPointIndex;
        this.polyline = polyline;
        this.currentPoint = currentPoint;
        this.nextPoint = nextPoint;
        this.spawnEvent = spawnEvent;
        this.setAngle(Math.atan2(nextPoint.y - currentPoint.y, nextPoint.x - currentPoint.x));
        this.healthRemaining = initialHealth;
        this.reward = reward;
        this.penalty = penalty;
        this.childrenSpawned = childrenSpawned;
        this.speed = speed;
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

    public SpawnEvent getSpawnEvent() {
        return spawnEvent;
    }

    public int getNextPointIndex() {
        return nextPointIndex;
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

    public double getSpeed() {
        return speed;
    }

    public int getChildrenNum() {
        return childrenSpawned;
    }

    public int getPenalty() {
        return penalty;
    }

    public int getReward() {
        return reward;
    }

    /**
     * Changes movement of enemy once it reaches its next point. Redefines the next point and therefore its angle.
     */
    public void changeMovement() {
        currentPoint = polyline.get(nextPointIndex);
        nextPointIndex++;
        nextPoint = polyline.get(nextPointIndex);
        this.setAngle(Math.atan2(nextPoint.y - currentPoint.y, nextPoint.x - currentPoint.x));
    }

    /**
     * Moves the enemy as per a Sprite, but also updates its current point.
     *
     * @param dx the movement of the enemy
     */
    @Override
    public void move(Vector2 dx) {
        super.move(dx);
        Point prevPoint = currentPoint;
        currentPoint = new Point(prevPoint.x + dx.x, prevPoint.y + dx.y);
    }

    /**
     * Undertakes the damaging of an enemy who has been shot or exploded.
     *
     * @param healthDamage the health damage inflicted upon the enemy
     * @return the reward earned by the defender upon its damage of the enemy.
     */
    public int damage(int healthDamage) {
        healthRemaining -= healthDamage;
        if (healthRemaining <= 0) {
            spawnEvent.killSlicer(this);
            return getReward();
        }
        return 0;
    }


}
