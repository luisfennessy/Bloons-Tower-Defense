import java.util.ArrayList;

/**
 * An event within a wave which delays the subsequent spawning of slicers over a set interval.
 */
public class DelayEvent extends WaveEvent {

    private final static int INDEX_OF_DELAY_LENGTH = 2;           // The index of the millisecond length of the delay
    private double delayLength;

    /**
     * Creates a new delay event.
     *
     * @param eventInfo Describes the properties of the wave event
     */
    public DelayEvent(String[] eventInfo) {
        super();
        delayLength = ShadowDefend.MILLI_TO_NORMAL * Integer.parseInt(eventInfo[INDEX_OF_DELAY_LENGTH]);
    }

    /**
     * A delay event is still running so long as it is active; ie, it's delay is still elapsing.
     *
     * @return whether the delay event is still running.
     */
    @Override
    public boolean isStillRunning() {
        return this.isActive();
    }

    /**
     * Updates the delay event, ensuring it's only terminated once the delay has elapsed
     *
     * @return an empty array list of enemies; no enemies are present in a delay event
     */
    @Override
    public ArrayList<Enemy> update(int timescale, double frameCount) {
        if (this.getFrameOfEventStart() + delayLength * ShadowDefend.FPS <= frameCount) {
            deactivate();
        }
        return new ArrayList<Enemy>();
    }
}
