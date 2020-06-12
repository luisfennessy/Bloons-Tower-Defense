import bagel.util.Point;

import java.util.ArrayList;

/**
 * The ammunition used by an airplane.
 */
public class Explosive extends Ammo {

    private final static String IMAGE_SRC = "res/images/explosive.png";
    private final static int DAMAGE = 500;
    private double dropTime;
    private double frameOfDrop;
    private boolean isDetonated = false;

    /**
     * Creates a new explosive
     *
     * @param point       The starting point for the entity
     * @param frameOfDrop the frame of drop
     * @param dropTime    explosive's drop time
     */
    protected Explosive(Point point, double frameOfDrop, double dropTime) {
        super(point, IMAGE_SRC, DAMAGE);
        this.dropTime = dropTime * ShadowDefend.FPS;
        this.frameOfDrop = frameOfDrop;
    }

    public boolean isDetonated() {
        return isDetonated;
    }

    /**
     * Update int.
     *
     * @param enemies    the enemies, some of which being in the radius of the explosion.
     * @param frameCount the frame count to ensure timely detonation
     * @return rewards earned by the exploding of enemies
     */
    public int update(ArrayList<Enemy> enemies, double frameCount) {
        int reward = 0;
        if (frameCount - frameOfDrop >= dropTime) {
            isDetonated = true;
            for (Enemy enemy : enemies) {
                if (enemy.isActive() && enemy.getCurrentPoint().distanceTo(getCenter()) <= Airplane.EFFECT_RADIUS) {
                    reward += enemy.damage(DAMAGE);
                }
            }
        } else {
            getImage().draw(getCenter().x, getCenter().y);
        }
        return reward;
    }
}
