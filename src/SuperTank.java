import bagel.util.Point;

/**
 * The more powerful of the towers.
 */
public class SuperTank extends Tower {

    private final static int PRICE = 600;
    private final static int EFFECT_RADIUS = 150;
    private final static int COOL_DOWN = 500;
    private final static String SUPER_TANK_SRC = "res/images/supertank.png";

    /**
     * Instantiates a new Super tank.
     *
     * @param point the point at which the super tank is to be placed.
     */
    protected SuperTank(Point point) {
        super(point, SUPER_TANK_SRC, PRICE, EFFECT_RADIUS, COOL_DOWN);
    }

    /**
     * Performs the shooting of super tank projectiles.
     *
     * @param target the enemy at which the projectile is to shoot at and follow
     */
    @Override
    public void shoot(Enemy target) {
        if (target != null) {
            getProjectiles().add(new SuperTankProjectile(getCenter(), target));
        }
    }
}
