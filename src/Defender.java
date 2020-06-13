import bagel.util.Point;

import java.util.ArrayList;

/**
 * The defender who use ammunition to damage and kill enemies, earning rewards.
 */
public abstract class Defender extends Sprite {

    private boolean isActive = true;
    private int price;
    private int effectRadius;

    /**
     * Creates a new Defender
     *
     * @param point        The starting point for the defener
     * @param imageSrc     The image which will be rendered at the defender's point
     * @param price        the price of the defender
     * @param effectRadius the effect radius of the defender
     */
    protected Defender(Point point, String imageSrc, int price, int effectRadius) {
        super(point, imageSrc);
        this.price = price;
        this.effectRadius = effectRadius;
    }

    /**
     * Draws a model of a defender for buy panel & placing process, based on its center.
     */
    public void drawModel() {
        getImage().draw(getCenter().x, getCenter().y);
    }

    public void deactivate() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getPrice() {
        return price;
    }

    public int getEffectRadius() {
        return effectRadius;
    }

    /**
     * Updates the defender, attacking where necessary and collecting rewards from kills.
     *
     * @param enemies    the enemies of the current wave, to be targeted by the
     * @param frameCount the position to which the defender is to move
     * @param timescale  the pace modifier of the level
     * @return the rewards from the killing of enemies.
     */
    public abstract int update(ArrayList<Enemy> enemies, double frameCount, int timescale);

}
