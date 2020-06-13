import bagel.util.Point;
import bagel.util.Vector2;

/**
 * Ammunition shot by a tower.
 */
public abstract class Projectile extends Ammo {

    private Enemy target;                   // the particular enemy which a projectile tracks to hit.
    private static final int SPEED = 10;
    private boolean isAirborne = true;

    /**
     * Creates a projectile at the point of its tower
     *
     * @param point    The starting point for the entity (at its tower)
     * @param imageSrc The image which will be rendered at the entity's point
     * @param target   the target enemy of the projectile
     * @param damage   the damage which the projectile will inflict upon hitting.
     */
    protected Projectile(Point point, String imageSrc, Enemy target, int damage) {
        super(point, imageSrc, damage);
        this.target = target;
    }

    public Enemy getTarget() {
        return target;
    }

    public boolean isAirborne() {
        return isAirborne;
    }

    /**
     * Updates the position and state of the projectile, damages enemy if it hits.
     *
     * @param timescale the pace factor of the level
     * @return the rewards earned by the killing of slicers
     */
    public int update(int timescale) {
        int reward = 0;
        if (target != null && target.isActive()) {
            if (getRect().intersects(target.getCenter())) {
                reward += target.damage(getDamage());
                isAirborne = false;
            } else {
                this.move(new Vector2(target.getCurrentPoint().x - getCenter().x, target.getCurrentPoint().y -
                        getCenter().y).normalised().mul(SPEED * timescale));
                this.getImage().draw(getCenter().x, getCenter().y);
            }
        }
        return reward;
    }
}
