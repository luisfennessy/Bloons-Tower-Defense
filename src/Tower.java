import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

public abstract class Tower extends Defender {

    private int radius;
    private int coolDownTime;
    private int timeSincePrevShot;
    private ArrayList<Enemy> enemiesInRadius;

    protected Tower(Point point, Image image) {
        super(point, image);
    }

    public void shoot() {

    }

}
