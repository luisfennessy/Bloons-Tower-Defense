/**
 * An event within a wave which delays the subsequent spawning of slicers over a set interval.
 */
public class DelayEvent extends WaveEvent {

    private final static int INDEX_OF_DELAY_LENGTH = 2;           // The index of the millisecond length of the delay
    private int delayLength;

    /**
     * Creates a new delay event.
     *
     * @param eventInfo Describes the properties of the wave event
     */
    public DelayEvent(String[] eventInfo) {
        super(eventInfo);
        delayLength = Integer.parseInt(eventInfo[INDEX_OF_DELAY_LENGTH]);
    }

    @Override
    public boolean isStillRunning() {
        return this.isActive();
    }

    @Override
    public void update(int timescale, double frameCount) {
        if (this.getFrameOfEventStart() + delayLength * ShadowDefend.FPS <= frameCount) {
            this.deactivate();
        }
    }
}
