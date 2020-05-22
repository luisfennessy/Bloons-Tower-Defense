import bagel.Image;
import bagel.util.Point;

public class SuperTank extends Tower {

    private final static int SUPER_TANK_PRICE = 600;
    private final static String SUPER_TANK_SRC = "res/images/supertank.png";

    protected SuperTank(Point point) {
        super(point, new Image(SUPER_TANK_SRC));
    }

}
