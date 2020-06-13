import java.util.ArrayList;

/**
 * A specified event of a wave, either delaying or coordinating enemy spawns.
 */
public abstract class WaveEvent {

    private boolean isActive = false;
    private double frameOfEventStart;

    /**
     * Creates a new instance of a wave event.
     */
    public WaveEvent() {
    }

    public boolean isActive() {
        return isActive;
    }

    public double getFrameOfEventStart() {
        return frameOfEventStart;
    }

    /**
     * Checks whether a wave event is still taking place on the map.
     */
    public abstract boolean isStillRunning();

    /**
     * Activates the wave event, declaring the frame at which this was done.
     *
     * @param frameCount the frame at which this activation occurs
     */
    public void activate(double frameCount) {
        isActive = true;
        frameOfEventStart = frameCount;
    }


    public void deactivate() {
        isActive = false;
    }

    /**
     * Updates the state of the wave event at each frame.
     *
     * @param timescale the rate of movement
     * @param frameCount allows perspective of time elapsed
     * @return the enemies present in that wave event.
     */
    public abstract ArrayList<Enemy> update(int timescale, double frameCount);

}
