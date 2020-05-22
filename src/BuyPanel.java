import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;

public class BuyPanel extends Panel {

    private final static String BUY_PANEL_IMAGE_SRC = "res/images/buypanel.png";

    private final static int MONEY_FONT_SIZE = 48;
    private final static int BINDS_FONT_SIZE = 14;
    private final static int PRICE_FONT_SIZE = 22;

    private final static Image BUY_PANEL_IMAGE = new Image(BUY_PANEL_IMAGE_SRC);
    private final static Point BUY_PANEL_CENTRE = new Point(ShadowDefend.WIDTH/2, BUY_PANEL_IMAGE.getHeight()/2);
    private final static String KEY_BINDS = "Key binds:\n\nS - Start Wave\nL - Increase Timescale\nK - " +
            "Decrease Timescale";
    private final static int MONEY_TEXT_XVALUE = ShadowDefend.WIDTH - 200;
    private final static int MONEY_TEXT_YVALUE = 65;
    private final static int BINDS_TEXT_XVALUE = ShadowDefend.WIDTH/2 - 60;
    private final static int BINDS_TEXT_YVALUE = 20;

    private final static int DEFENCE_IMG_YVALUE = (int)BUY_PANEL_IMAGE.getHeight()/2 - 10;
    private final static Point TANK_POSITION = new Point(64, DEFENCE_IMG_YVALUE);
    private final static Point SUPER_TANK_POSITION = new Point(TANK_POSITION.x + 120, DEFENCE_IMG_YVALUE);
    private final static Point AIRPLANE_POSITION = new Point(SUPER_TANK_POSITION.x + 120, DEFENCE_IMG_YVALUE);
    private final static Tank modelTank = new Tank(TANK_POSITION);
    private final static SuperTank modelSuperTank = new SuperTank(SUPER_TANK_POSITION);
    private final static Airplane modelAirplane = new Airplane(AIRPLANE_POSITION);

    private final static int DEFENCE_TEXT_YVALUE = DEFENCE_IMG_YVALUE + 50;


    public BuyPanel() {
        super(BUY_PANEL_CENTRE, BUY_PANEL_IMAGE);
    }

    public void update(int moneyLeft) {
        this.getImage().draw(this.getCentre().x, this.getCentre().y);
        Font moneyFont = new Font(getFontSrc(), MONEY_FONT_SIZE);
        Font bindsFont = new Font(getFontSrc(), BINDS_FONT_SIZE);
        Font pricesFont = new Font(getFontSrc(), PRICE_FONT_SIZE);

        /* & use money left to colour the font */

        moneyFont.drawString("$" + moneyLeft, MONEY_TEXT_XVALUE, MONEY_TEXT_YVALUE);
        bindsFont.drawString(KEY_BINDS, BINDS_TEXT_XVALUE, BINDS_TEXT_YVALUE);

        modelTank.draw();
        pricesFont.drawString("$" + Defender.getTankPrice(), TANK_POSITION.x -
                modelTank.getImage().getWidth()/2, DEFENCE_TEXT_YVALUE);
        modelSuperTank.draw();
        pricesFont.drawString("$" + Defender.getSuperTankPrice(), SUPER_TANK_POSITION.x -
                modelSuperTank.getImage().getWidth()/2, DEFENCE_TEXT_YVALUE);
        modelAirplane.draw();
        pricesFont.drawString("$" + Defender.getAirplanePrice(), AIRPLANE_POSITION.x -
                modelAirplane.getImage().getWidth()/2, DEFENCE_TEXT_YVALUE);
    }

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
