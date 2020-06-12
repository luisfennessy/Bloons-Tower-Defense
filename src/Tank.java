import bagel.util.Point;

/**
 * The less powerful of the towers.
 */
public class Tank extends Tower {

    private final static int PRICE = 250;
    private final static int EFFECT_RADIUS = 100;
    private final static int COOL_DOWN = 1000;
    private final static String TANK_SRC = "res/images/tank.png";

    /**
     * Instantiates a new tank.
     *
     * @param point the point at which the tank is to be placed.
     */
    public Tank(Point point) {
        super(point, TANK_SRC, PRICE, EFFECT_RADIUS, COOL_DOWN);
    }

    /**
     * Performs the shooting of tank projectiles.
     *
     * @param target the enemy at which the projectile is to shoot at and follow
     */
    @Override
    public void shoot(Enemy target) {
        if (target != null) {
            getProjectiles().add(new TankProjectile(getCenter(), target));
        }
    }
}
