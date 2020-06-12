import bagel.util.Point;

/**
 * The ammunition used by the super tank.
 */
public class TankProjectile extends Projectile {

    private final static String IMAGE_SRC = "res/images/tank_projectile.png";
    public final static int DAMAGE = 1;

    /**
     * Creates a new tank projectile
     *
     * @param point    The starting point for the projectile
     * @param target   The enemy which the projectile is to follow and aim to hit.
     */
    protected TankProjectile(Point point, Enemy target) {
        super(point, IMAGE_SRC, target, DAMAGE);
    }
}
