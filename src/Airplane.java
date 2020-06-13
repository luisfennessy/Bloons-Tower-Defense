import bagel.DrawOptions;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

/**
 * Aerial defender.
 */
public class Airplane extends Defender {

    private final static int PRICE = 500;
    private final static int SPEED = 3;
    public final static int EFFECT_RADIUS = 200;
    private final static int PIXEL_OF_START = 0;
    private final static double FACING_DOWN = Math.PI;
    private final static double FACING_RIGHT = Math.PI / 2;
    private final static String AIRPLANE_SRC = "res/images/airsupport.png";

    private boolean isHorizontal = true;
    private boolean onMap = true;
    private ArrayList<Explosive> explosives = new ArrayList<Explosive>();
    private double coolDownDelay = Math.random() * 3;
    private double frameOfLastDrop;

    /**
     * Creates a airplane for defending.
     *
     * @param point               gives the point at which airplane is centred
     * @param lastPlaneHorizontal indicates whether previously created plane flew horizontally
     */
    public Airplane(Point point, boolean lastPlaneHorizontal) {
        super(point, AIRPLANE_SRC, PRICE, EFFECT_RADIUS);
        if (lastPlaneHorizontal) {
            isHorizontal = false;
        }
    }

    /**
     * Creates a airplane not to be deployed (used as a model for the buy panel).
     *
     * @param point     gives the point at which airplane is centred
     */
    public Airplane(Point point) {
        super(point, AIRPLANE_SRC, PRICE, EFFECT_RADIUS);
        isHorizontal = false;
    }

    /**
     * Moves airplane to the start point of its trajectory.
     *
     * @param frameCount    allows for accurate drop of first explosive
     */
    public void deploy(double frameCount) {
        // initialise deployed airplane at edge of screen
        if (isHorizontal) {
            move(new Vector2(PIXEL_OF_START - getCenter().x, 0));
        } else {
            move(new Vector2(0, PIXEL_OF_START - getCenter().y));
        }
        this.frameOfLastDrop = frameCount;
    }


    /**
     * Updates the state of the airplane, dropping explosives when the timing permits.
     *
     * @param enemies       ensures all slicers within drop zone are annihilated
     * @param frameCount    allows for accurate drop of first explosive
     * @param timescale     denotes the pace of the level, and therefore the pace of updating
     * @return the rewards from the killing of slicers.
     */
    @Override
    public int update(ArrayList<Enemy> enemies, double frameCount, int timescale) {

        if (getCenter().x >= ShadowDefend.WIDTH || getCenter().y >= ShadowDefend.HEIGHT) {
            // plane has left map
            onMap = false;
            if (explosives.size() == 0) {
                deactivate();
                return 0;
            }
        }

        if (coolDownDelay < (frameCount - frameOfLastDrop) / ShadowDefend.FPS && onMap) {
            // cool down period has elapsed; reset new cool down length
            drop(frameCount);
            frameOfLastDrop = frameCount;
            coolDownDelay = Math.random() * 3;
        }

        int rewards = 0;
        ArrayList<Explosive> explosivesToRemove = new ArrayList<Explosive>();
        for (Explosive explosive : explosives) {
            int new_reward = explosive.update(enemies, frameCount);
            if (new_reward > 0) {
                // explosive must have blown up
                rewards += new_reward;
                explosivesToRemove.add(explosive);
            } else if (explosive.isDetonated()) {
                explosivesToRemove.add(explosive);
            }
        }
        explosives.removeAll(explosivesToRemove);

        if (onMap) {
            if (isHorizontal) {
                move(new Vector2(SPEED, 0).mul(timescale));
                DrawOptions rotation = new DrawOptions().setRotation(FACING_RIGHT);
                getImage().draw(getCenter().x, getCenter().y, rotation);
            } else {
                move(new Vector2(0, SPEED).mul(timescale));
                DrawOptions rotation = new DrawOptions().setRotation(FACING_DOWN);
                getImage().draw(getCenter().x, getCenter().y, rotation);
            }
        }

        return rewards;
    }

    /**
     * Drops an explosive from airplane's current position
     *
     * @param frameCount    indicates the frame at which explosive was dropped
     */
    public void drop(double frameCount) {
        explosives.add(new Explosive(getCenter(), frameCount, Math.random() + 1));
    }


}
