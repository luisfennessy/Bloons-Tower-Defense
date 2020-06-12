import bagel.util.Point;

/**
 * The ammunition used by the super tank.
 */
public class SuperTankProjectile extends Projectile {

    private final static String IMAGE_SRC = "res/images/tank_projectile.png";
    private final static int DAMAGE = 3 * TankProjectile.DAMAGE;

    /**
     * Creates a new super tank projectile
     *
     * @param point    The starting point for the projectile
     * @param target   The enemy which the projectile is to follow and aim to hit.
     */
    protected SuperTankProjectile(Point point, Enemy target) {
        super(point, IMAGE_SRC, target, DAMAGE);
    }
}
