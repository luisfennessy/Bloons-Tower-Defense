import bagel.util.Point;

/**
 * The ammunition used by defenders to damage enemies.
 */
public abstract class Ammo extends Sprite {

    private int damage;

    /**
     * Creates a new piece of Ammo
     *
     * @param point    The starting point for the entity
     * @param imageSrc The image which will be rendered at the entity's point
     * @param damage   The damage inflicted by the ammo
     */
    protected Ammo(Point point, String imageSrc, int damage) {
        super(point, imageSrc);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
