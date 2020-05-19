import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * An organised wave of enemies.
 */
public class Wave {

    private ArrayList<WaveEvent> waveEvents = new ArrayList<>();
    private List<Point> polyline;
    private static final int INDEX_OF_WAVE_TYPE = 1;
    private static final String SPAWN_EVENT_TYPE = "spawn";
    private static final String DELAY_EVENT_TYPE = "delay";
    private boolean isActive;
    private int eventsCommenced;



    /**
     * Creates a new instance of a wave.
     *
     * @param polyline The polyline that the enemies must traverse
     */
    public Wave(List<Point> polyline) {
        isActive = false;
        this.polyline = polyline;
        eventsCommenced = 0;
    }

    /**
     * Creates a new instance of a wave event.
     *
     * @param waveEventInfo Provides description of wave event
     */
    public void addWaveEvent(String[] waveEventInfo) {
        if (waveEventInfo[INDEX_OF_WAVE_TYPE].equals(SPAWN_EVENT_TYPE)) {
            waveEvents.add(new SpawnEvent(waveEventInfo, polyline));
        } else if (waveEventInfo[INDEX_OF_WAVE_TYPE].equals(DELAY_EVENT_TYPE)) {
            waveEvents.add(new DelayEvent(waveEventInfo));
        }

    }

    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        isActive = true;
    }


    /**
     * Updates the state of the wave at each frame.
     *
     * @param timescale the rate of movement
     * @param frameCount allows perspective of time elapsed
     */
    public void update(int timescale, double frameCount) {

        /* Depending on state of wave event, might refresh or terminate it
        if (currentEvent != null) {
            if (currentEvent.isActive()) {
                currentEvent.update(timescale, frameCount);
            } else if (eventsCompleted < waveEvents.size() - 1) {
                eventsCompleted++;
                currentEvent = waveEvents.get(eventsCompleted);
                currentEvent.activate(frameCount);
            } else {
                isActive = false;
            }
        } else {
            currentEvent = waveEvents.get(0);
        }*/

        for (WaveEvent waveEvent : waveEvents) {
            boolean needNewEvent = true;
            if (waveEvent.isStillRunning()) {
                waveEvent.update(timescale, frameCount);
                if (waveEvent.isActive()) {
                    needNewEvent = false;
                }
            }
            if (needNewEvent) {
                if (eventsCommenced == waveEvents.size()) {
                    isActive = false;
                } else {
                    waveEvents.get(eventsCommenced).activate(frameCount);
                    eventsCommenced++;
                }
            }
        }
    }
}