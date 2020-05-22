/**
 * A specified event of a wave, either delaying or commencing enemy spawns.
 */
public abstract class WaveEvent {

    private final static int INDEX_OF_WAVE_NUM = 0;       // The index of the wave number in an event's description
    private int waveNumber;
    private boolean isActive = false;
    private double frameOfEventStart;

    /**
     * Creates a new instance of a wave event.
     *
     * @param eventInfo Describes the properties of the wave event
     */
    public WaveEvent(String[] eventInfo) {
        waveNumber = Integer.parseInt(eventInfo[INDEX_OF_WAVE_NUM]);
    }

    public boolean isActive() {
        return isActive;
    }

    public double getFrameOfEventStart() {
        return frameOfEventStart;
    }

    public abstract boolean isStillRunning();

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
     */
    public abstract void update(int timescale, double frameCount);

}
