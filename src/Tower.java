import bagel.DrawOptions;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * The defenders which are immobile, but can rotate and shoot.
 */
public abstract class Tower extends Defender {

    private final static double ROTATION_OFFSET = Math.PI / 2;

    private double frameOfLastShot = 0;
    private double coolDownDelay;
    private Enemy target;                                 // the current enemy which the tower is to face and shoot at
    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    private double angleFacing = 3 * Math.PI / 2;


    /**
     * Creates a new Tower
     *
     * @param point         The point at which the tower is placed
     * @param imageSrc      The image of the tower
     * @param price         tower's price
     * @param effectRadius  tower's effect radius
     * @param coolDownDelay tower's cool down delay
     */
    protected Tower(Point point, String imageSrc, int price, int effectRadius, double coolDownDelay) {
        super(point, imageSrc, price, effectRadius);
        this.coolDownDelay = coolDownDelay;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    /**
     * Updates the rotation and state of the tower, shooting when and where appropriate
     *
     * @param enemies all enemies, and potential targets, on the map
     * @param frameCount ensures timely shooting
     * @param timescale the pace of the level
     * @return the rewards won from the killing of enemies
     */
    @Override
    public int update(ArrayList<Enemy> enemies, double frameCount, int timescale) {

        // update the target if necessary, and rotate to the target's direction.
        if (target == null || !target.isActive() || target.getCurrentPoint().distanceTo(getCenter()) >
                getEffectRadius()) {
            target = null;
            for (Enemy enemy : enemies) {
                if (enemy.getCurrentPoint().distanceTo(getCenter()) <= getEffectRadius() && enemy.isActive()) {
                    // System.out.println("New enemy: " + enemy);
                    target = enemy;
                    angleFacing = Math.atan2(target.getCurrentPoint().y - getCenter().y, target.getCurrentPoint().x -
                            getCenter().x);
                    break;
                }
            }
        } else {
            angleFacing = Math.atan2(target.getCurrentPoint().y - getCenter().y, target.getCurrentPoint().x -
                    getCenter().x);
        }

        // shoot when the tower is cool enough
        if (target != null) {
            if (coolDownDelay * ShadowDefend.MILLI_TO_NORMAL < (frameCount - frameOfLastShot) / ShadowDefend.FPS &&
                    target.getSpawnEvent().isStillRunning()) {
                // System.out.println("Shooting at " + frameCount + "from tower: " + this);
                shoot(target);
                frameOfLastShot = frameCount;
            }
        }

        // updates the positions of the projectiles, removing them if they have hit, or they have left the map
        int rewards = 0;
        ArrayList<Projectile> projectilesToRemove = new ArrayList<Projectile>();
        for (Projectile projectile : projectiles) {
            if (projectile.getTarget() != null) {
                if (projectile.getCenter().x > 0 && projectile.getCenter().x < ShadowDefend.WIDTH &&
                        projectile.getTarget().isActive() && projectile.getCenter().y > 0 && projectile.getCenter().y <
                        ShadowDefend.HEIGHT) {
                    int new_reward = projectile.update(timescale);
                    if (new_reward > 0 || !projectile.isAirborne()) {
                        rewards += new_reward;
                        projectilesToRemove.add(projectile);
                    }
                } else {
                    projectilesToRemove.add(projectile);
                }
            } else {
                projectilesToRemove.add(projectile);
            }
        }
        projectiles.removeAll(projectilesToRemove);

        // draw tower as per its rotation
        DrawOptions rotation = new DrawOptions().setRotation(angleFacing + ROTATION_OFFSET);
        getImage().draw(getCenter().x, getCenter().y, rotation);

        return rewards;
    }

    /**
     * Shoots at the enemy target.
     *
     * @param target the enemy target
     */
    public abstract void shoot(Enemy target);

}
