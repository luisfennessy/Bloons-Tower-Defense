import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * An organised wave of enemies.
 */
public class Wave {

    private ArrayList<WaveEvent> waveEvents = new ArrayList<>();
    private List<Point> polyline;
    private static final int INDEX_OF_WAVE_TYPE = 1;           // index of entry of wave type in each line of wave text
    private static final String SPAWN_EVENT_TYPE = "spawn";    // used in wave text file to denote the spawn event type
    private static final String DELAY_EVENT_TYPE = "delay";    // used in wave text file to denote the delay event type
    private Level level;
    private boolean isActive = false;
    private int eventsCommenced = 0;


    /**
     * Creates a new instance of a wave.
     *
     * @param polyline The polyline that the enemies must traverse
     * @param level    the level in which the wave takes place
     */
    public Wave(List<Point> polyline, Level level) {
        this.polyline = polyline;
        this.level = level;
    }

    /**
     * Creates a new instance of a wave event, and adds its to the wave events of the wave.
     *
     * @param waveEventInfo Provides description of wave event
     */
    public void addWaveEvent(String[] waveEventInfo) {
        if (waveEventInfo[INDEX_OF_WAVE_TYPE].equals(SPAWN_EVENT_TYPE)) {
            waveEvents.add(new SpawnEvent(waveEventInfo, polyline, level));
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
     * @param timescale  the rate of movement
     * @param frameCount allows perspective of time elapsed
     * @return enemies which are active in the current wave.
     */
    public ArrayList<Enemy> update(int timescale, double frameCount) {

        ArrayList<Enemy> currentEnemies = new ArrayList<>();
        boolean needNewEvent = true;
        boolean needToDeactivate = true;

        // confirms no new event required or that current event is to be deactivated judging off the currently running
        // wave events
        for (WaveEvent waveEvent : waveEvents) {
            if (waveEvent.isStillRunning()) {
                needToDeactivate = false;
                currentEnemies.addAll(waveEvent.update(timescale, frameCount));
                if (waveEvent.isActive()) {
                    needNewEvent = false;
                }
            }
        }

        // activates new wave event if necessary
        if (needNewEvent) {
            if (eventsCommenced == waveEvents.size()) {
                if (needToDeactivate) {
                    isActive = false;
                }
            } else {
                waveEvents.get(eventsCommenced).activate(frameCount);
                eventsCommenced++;
            }
        }
        return currentEnemies;
    }
}