import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class Defender {

    private final static int TANK_PRICE = 250;
    private final static int SUPER_TANK_PRICE = 600;
    private final static int AIRPLANE_PRICE = 500;

    private final Image image;
    private final Rectangle rect;
    private double damage;
    private int price;
    private Point position;
    private boolean isDeployed = false;

    protected Defender(Point point, Image image) {
        this.image = image;
        this.rect = image.getBoundingBoxAt(point);
        this.position = point;
    }

    public void draw() {
        image.draw(position.x, position.y);
    }

    public static int getAirplanePrice() {
        return AIRPLANE_PRICE;
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getRect() {
        return rect;
    }

    public static int getSuperTankPrice() {
        return SUPER_TANK_PRICE;
    }

    public static int getTankPrice() {
        return TANK_PRICE;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int purchase() {
        this.isDeployed = true;
        return price;
    }


}
