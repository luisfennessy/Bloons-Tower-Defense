import bagel.Image;
import bagel.util.Point;

public class Airplane extends Defender {


    private final static int AIRPLANE_PRICE = 500;
    private boolean isHorizontal;
    private final static int SPEED = 5;
    private final static int EFFECT_RADIUS = 200;
    private final static int DETONATION_TIME = 2;
    private final static int DAMAGE = 500;
    private final static String AIRPLANE_SRC = "res/images/airsupport.png";


    protected Airplane(Point point) {
        super(point, new Image(AIRPLANE_SRC));
    }

    public void deploy() {
        if (isHorizontal) {

        } else {

        }
    }
}
