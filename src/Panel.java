import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * The panel shown to the user to allow for clear game-play. Concrete inheritors of this class may be singletons.
 */
public abstract class Panel {

    private final static String FONT_SRC = "res/fonts/DejaVuSans-Bold.ttf";    // the source of the font used on panels

    private final Image image;
    private final Rectangle rect;

    /**
     * Instantiates a new Panel.
     *
     * @param point the point of the panel's centre
     * @param image the panel's image.
     */
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
