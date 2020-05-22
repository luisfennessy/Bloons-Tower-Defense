import bagel.Font;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class Panel {

    private final static String FONT_SRC = "res/fonts/DejaVuSans-Bold.ttf";

    private final Image image;
    private final Rectangle rect;

    protected Panel(Point point, Image image) {
        this.image = image;
        this.rect = image.getBoundingBoxAt(point);
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getRect() {
        return rect;
    }

    public Point getCentre() {
        return rect.centre();
    }

    public static String getFontSrc() {
        return FONT_SRC;
    }
}
