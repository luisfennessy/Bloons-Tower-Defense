import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;

/**
 * The panel upon which the state of play of the game and level is shown - is designed as a singleton.
 */
public class StatusPanel extends Panel {

    private final static String STATUS_PANEL_IMAGE_SRC = "res/images/statuspanel.png";
    private final static Image STATUS_PANEL_IMG = new Image(STATUS_PANEL_IMAGE_SRC);
    private final static Point STATUS_PANEL_CENTRE = new Point(ShadowDefend.WIDTH/2,
            ShadowDefend.HEIGHT - STATUS_PANEL_IMG.getHeight()/2);
    private final static int STATUS_FONT_SIZE = 15;

    // the points at which text and images are drawn to ensure appropriate spacing of panel.
    private final static int WAVE_NO_TEXT_XVALUE = 10;
    private final static int TSCALE_TEXT_XVALUE = 220;
    private final static int STATUS_TEXT_XVALUE = ShadowDefend.WIDTH/2 - 90;
    private final static int LIVES_TEXT_XVALUE = ShadowDefend.WIDTH - 80;
    private final static int APLANE_TEXT_XVALUE = ShadowDefend.WIDTH/2 + 150;
    private final static int ALL_TEXT_YVALUE = (int)(ShadowDefend.HEIGHT - STATUS_PANEL_IMG.getHeight()/2 + 5);

    // the string representation of they key states of play of a level
    private final static String CURRENT_WAVE_STATUS = "Wave in Progress";
    private final static String PLACING_STATUS = "Placing";
    private final static String WINNER_STATUS = "Winner!";
    private final static String WAITING_STATUS = "Awaiting Start";

    private static StatusPanel _instance;

    /**
     * Instantiates a new Status panel.
     */
    private StatusPanel() {
        super(STATUS_PANEL_CENTRE, STATUS_PANEL_IMG);
    }

    /**
     * Gets the singleton status panel, initialising it if not yet done.
     *
     * @return the singleton status panel
     */
    public static StatusPanel getStatusPanel() {
        if (_instance == null) {
            _instance = new StatusPanel();
        }
        return _instance;
    }

    /**
     * Updates the contents of the status bar.
     *
     * @param waveNumber          the wave number
     * @param timescale           the timescale
     * @param livesLeft           lives remaining
     * @param hasWon              whether the game has ended and the user has won
     * @param isPlacing           whether the user is currently placing
     * @param waveActive          whether the wave is currently active
     * @param lastPlaneHorizontal whether the last plane was horizontal
     */
    public void update(int waveNumber, int timescale, int livesLeft, boolean hasWon, boolean isPlacing, boolean
            waveActive, boolean lastPlaneHorizontal) {

        this.getImage().draw(this.getCentre().x, this.getCentre().y);
        Font font = new Font(getFontSrc(), STATUS_FONT_SIZE);

        // use green text is the timescale is greater than one.
        font.drawString("Wave: " + waveNumber, WAVE_NO_TEXT_XVALUE, ALL_TEXT_YVALUE);
        if (timescale > 1) {
            font.drawString("Timescale: " + (double)timescale, TSCALE_TEXT_XVALUE, ALL_TEXT_YVALUE, new
                    DrawOptions().setBlendColour(Colour.GREEN));
        } else {
            font.drawString("Timescale: " + (double)timescale, TSCALE_TEXT_XVALUE, ALL_TEXT_YVALUE);
        }

        // according to hierarchy of importance, gives the current status of the game.
        if (hasWon) {
            font.drawString("Status: " + WINNER_STATUS, STATUS_TEXT_XVALUE, ALL_TEXT_YVALUE);
        } else if (isPlacing) {
            font.drawString("Status: " + PLACING_STATUS, STATUS_TEXT_XVALUE, ALL_TEXT_YVALUE);
        } else if (waveActive) {
            font.drawString("Status: " + CURRENT_WAVE_STATUS, STATUS_TEXT_XVALUE, ALL_TEXT_YVALUE);
        } else {
            font.drawString("Status: " + WAITING_STATUS, STATUS_TEXT_XVALUE, ALL_TEXT_YVALUE);
        }

        // ADDITION FOR PROJECT: denotes the direction of the next plane to be placed; allows for clearer game-play.
        font.drawString("Lives: " + livesLeft, LIVES_TEXT_XVALUE, ALL_TEXT_YVALUE);
        if (lastPlaneHorizontal) {
            font.drawString("Next Airplane: VERTICAL", APLANE_TEXT_XVALUE, ALL_TEXT_YVALUE);
        } else {
            font.drawString("Next Airplane: HORIZONTAL", APLANE_TEXT_XVALUE, ALL_TEXT_YVALUE);
        }

    }

}
