import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

/**
 * Represents a game entity; code used from Project 1 solutions
 */
public abstract class Sprite {

    private final Image image;
    private final Rectangle rect;

    /**
     * Creates a new Sprite (game entity)
     *
     * @param point    The starting point for the entity
     * @param imageSrc The image which will be rendered at the entity's point
     */
    protected Sprite(Point point, String imageSrc) {
        this.image = new Image(imageSrc);
        this.rect = image.getBoundingBoxAt(point);
    }


    public Rectangle getRect() {
        return new Rectangle(rect);
    }

    public Image getImage() {
        return image;
    }

    /**
     * Moves the Sprite by a specified delta
     *
     * @param dx The move delta vector
     */
    public void move(Vector2 dx) {
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }

    /**
     * Sets position, moving its rectangle accordingly.
     *
     * @param position the position to which the defender is to move
     */
    public void setPosition(Point position) {
        this.move(new Vector2(position.x - getCenter().x, position.y - getCenter().y));
    }

    public Point getCenter() {
        return getRect().centre();
    }

}
