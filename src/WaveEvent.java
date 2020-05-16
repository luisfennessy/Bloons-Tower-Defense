import bagel.map.TiledMap;
import bagel.util.Point;

import java.util.List;

/**
 * A specified event of a wave, either delaying or commencing enemy spawns.
 */
public abstract class WaveEvent {

    private final int INDEXOFWAVENUM = 0;       // The index of the wave number in an event's description

    private int waveNumber;
    private List<Point> polyline;

    /**
     * Creates a new instance of a wave event.
     *
     * @param eventInfo Describes the properties of the wave event
     * @param polyline The path along which an enemy travels
     */
    public WaveEvent(String[] eventInfo, List<Point> polyline) {
        waveNumber = Integer.parseInt(eventInfo[INDEXOFWAVENUM]);
        this.polyline = polyline;
    }

}
