import bagel.Image;
import bagel.util.Point;

public class Tank extends Tower {

    // private final static TANK_PRICE = 250;
    private final static String TANK_SRC = "res/images/tank.png";


    public Tank(Point point) {
        super(point, new Image(TANK_SRC));
    }

}
