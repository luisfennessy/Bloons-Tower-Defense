import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;

/**
 * The panel at the screen's top, depicting defenders available & money remaining - it's a singleton design.
 */
public class BuyPanel extends Panel {

    private final static String BUY_PANEL_IMAGE_SRC = "res/images/buypanel.png";

    private final static int MONEY_FONT_SIZE = 48;
    private final static int BINDS_FONT_SIZE = 14;
    private final static int PRICE_FONT_SIZE = 22;
    private final static String KEY_BINDS = "Key binds:\n\nS - Start Wave\nL - Increase Timescale\nK - " +
            "Decrease Timescale";

    // the points at which text and images are drawn to ensure appropriate spacing of panel.
    private final static Image BUY_PANEL_IMAGE = new Image(BUY_PANEL_IMAGE_SRC);
    private final static Point BUY_PANEL_CENTRE = new Point(ShadowDefend.WIDTH/2, BUY_PANEL_IMAGE.getHeight()/2);
    private final static int MONEY_TEXT_XVALUE = ShadowDefend.WIDTH - 200;
    private final static int MONEY_TEXT_YVALUE = 65;
    private final static int BINDS_TEXT_XVALUE = ShadowDefend.WIDTH/2 - 60;
    private final static int BINDS_TEXT_YVALUE = 20;
    private final static int DEFENCE_IMG_YVALUE = (int)BUY_PANEL_IMAGE.getHeight()/2 - 10;
    private final static Point TANK_POSITION = new Point(64, DEFENCE_IMG_YVALUE);
    private final static Point SUPER_TANK_POSITION = new Point(TANK_POSITION.x + 120, DEFENCE_IMG_YVALUE);
    private final static Point AIRPLANE_POSITION = new Point(SUPER_TANK_POSITION.x + 120, DEFENCE_IMG_YVALUE);
    private final static int DEFENCE_TEXT_YVALUE = DEFENCE_IMG_YVALUE + 50;

    // the model defenders to be used in the buy panel's shop window.
    private final static Tank modelTank = new Tank(TANK_POSITION);
    private final static SuperTank modelSuperTank = new SuperTank(SUPER_TANK_POSITION);
    private final static Airplane modelAirplane = new Airplane(AIRPLANE_POSITION);

    private static BuyPanel _instance;


    /**
     * Creates a new buy panel.
     */
    public BuyPanel() {
        super(BUY_PANEL_CENTRE, BUY_PANEL_IMAGE);
    }


    /**
     * Gets the singleton buy panel, initialising it if not yet done.
     *
     * @return the singleton buy panel
     */
    public static BuyPanel getBuyPanel() {
        if (_instance == null) {
            _instance = new BuyPanel();
        }
        return _instance;
    }

    /**
     * Updates the state of the buy panel, drawing it and all its contents.
     *
     * @param moneyLeft indicates the money remaining
     */
    public void update(int moneyLeft) {
        getImage().draw(this.getCentre().x, this.getCentre().y);
        Font moneyFont = new Font(getFontSrc(), MONEY_FONT_SIZE);
        Font bindsFont = new Font(getFontSrc(), BINDS_FONT_SIZE);
        Font pricesFont = new Font(getFontSrc(), PRICE_FONT_SIZE);

        moneyFont.drawString("$" + moneyLeft, MONEY_TEXT_XVALUE, MONEY_TEXT_YVALUE);
        bindsFont.drawString(KEY_BINDS, BINDS_TEXT_XVALUE, BINDS_TEXT_YVALUE);
        modelTank.drawModel();
        modelSuperTank.drawModel();
        modelAirplane.drawModel();

        // depending on money remaining, draw prices of defenders in red or green font as per simplified logic...
        if (moneyLeft >= modelTank.getPrice()) {
            pricesFont.drawString("$" + modelTank.getPrice(), TANK_POSITION.x -
                    modelTank.getImage().getWidth()/2, DEFENCE_TEXT_YVALUE, new
                    DrawOptions().setBlendColour(Colour.GREEN));
            if (moneyLeft >= modelAirplane.getPrice()) {
                pricesFont.drawString("$" + modelAirplane.getPrice(), AIRPLANE_POSITION.x -
                        modelAirplane.getImage().getWidth()/2, DEFENCE_TEXT_YVALUE, new
                        DrawOptions().setBlendColour(Colour.GREEN));
                if (moneyLeft >= modelSuperTank.getPrice()) {
                    pricesFont.drawString("$" + modelSuperTank.getPrice(), SUPER_TANK_POSITION.x -
                            modelSuperTank.getImage().getWidth()/2, DEFENCE_TEXT_YVALUE,
                            new DrawOptions().setBlendColour(Colour.GREEN));
                } else {
                    pricesFont.drawString("$" + modelSuperTank.getPrice(), SUPER_TANK_POSITION.x -
                                    modelSuperTank.getImage().getWidth()/2, DEFENCE_TEXT_YVALUE,
                            new DrawOptions().setBlendColour(Colour.RED));
                }
            } else {
                pricesFont.drawString("$" + modelAirplane.getPrice(), AIRPLANE_POSITION.x -
                        modelAirplane.getImage().getWidth()/2, DEFENCE_TEXT_YVALUE, new
                        DrawOptions().setBlendColour(Colour.RED));
                pricesFont.drawString("$" + modelSuperTank.getPrice(), SUPER_TANK_POSITION.x -
                                modelSuperTank.getImage().getWidth()/2, DEFENCE_TEXT_YVALUE,
                        new DrawOptions().setBlendColour(Colour.RED));
            }
        } else {
            pricesFont.drawString("$" + modelTank.getPrice(), TANK_POSITION.x -
                    modelTank.getImage().getWidth()/2, DEFENCE_TEXT_YVALUE, new
                    DrawOptions().setBlendColour(Colour.RED));
            pricesFont.drawString("$" + modelAirplane.getPrice(), AIRPLANE_POSITION.x -
                    modelAirplane.getImage().getWidth()/2, DEFENCE_TEXT_YVALUE, new
                    DrawOptions().setBlendColour(Colour.RED));
            pricesFont.drawString("$" + modelSuperTank.getPrice(), SUPER_TANK_POSITION.x -
                            modelSuperTank.getImage().getWidth()/2, DEFENCE_TEXT_YVALUE,
                    new DrawOptions().setBlendColour(Colour.RED));
        }
    }

    /**
     * Checks the model defender under which the cursor is placed.
     *
     * @param cursorPosition indicates the position of the cursor
     * @return the model defender the cursor is over, returns null if over none.
     */
    public Defender cursorOverDefender(Point cursorPosition) {
        if (modelTank.getRect().intersects(cursorPosition)) {
            return modelTank;
        } else if (modelSuperTank.getRect().intersects(cursorPosition)) {
            return modelSuperTank;
        } else if (modelAirplane.getRect().intersects(cursorPosition)) {
            return modelAirplane;
        }
        return null;
    }
}
