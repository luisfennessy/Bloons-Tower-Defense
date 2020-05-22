import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;

public class StatusPanel extends Panel {

    private final static String STATUS_PANEL_IMAGE_SRC = "res/images/statuspanel.png";
    private final static Image STATUS_PANEL_IMG = new Image(STATUS_PANEL_IMAGE_SRC);
    private final static Point STATUS_PANEL_CENTRE = new Point(ShadowDefend.WIDTH/2,
            ShadowDefend.HEIGHT - STATUS_PANEL_IMG.getHeight()/2);
    private final static int STATUS_FONT_SIZE = 15;

    private final static int WAVE_NO_TEXT_XVALUE = 10;
    private final static int TSCALE_TEXT_XVALUE = 220;
    private final static int STATUS_TEXT_XVALUE = ShadowDefend.WIDTH/2 - 70;
    private final static int LIVES_TEXT_XVALUE = ShadowDefend.WIDTH - 80;
    private final static int ALL_TEXT_YVALUE = (int)(ShadowDefend.HEIGHT - STATUS_PANEL_IMG.getHeight()/2 + 5);

    public StatusPanel() {
        super(STATUS_PANEL_CENTRE, STATUS_PANEL_IMG);
    }

    public void update(int waveNumber, String status, int timescale, int livesLeft) {
        this.getImage().draw(this.getCentre().x, this.getCentre().y);
        Font font = new Font(getFontSrc(), STATUS_FONT_SIZE);
        font.drawString("Wave: " + waveNumber, WAVE_NO_TEXT_XVALUE, ALL_TEXT_YVALUE);
        if (timescale > 1) {
            font.drawString("Timescale: " + (double)timescale, TSCALE_TEXT_XVALUE, ALL_TEXT_YVALUE, new
                    DrawOptions().setBlendColour(Colour.GREEN));
        } else {
            font.drawString("Timescale: " + (double)timescale, TSCALE_TEXT_XVALUE, ALL_TEXT_YVALUE);
        }
        font.drawString("Status: " + status, STATUS_TEXT_XVALUE, ALL_TEXT_YVALUE);
        font.drawString("Lives: " + livesLeft, LIVES_TEXT_XVALUE, ALL_TEXT_YVALUE);
    }

}
