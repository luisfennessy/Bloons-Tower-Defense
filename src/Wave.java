import bagel.util.Point;

import java.util.List;

/**
 * An organised wave of enemies.
 */
public class Wave {

    private WaveEvent[] waveEvents;
    private List<Point> polyline;

    /**
     * Creates a new instance of a wave.
     *
     * @param polyline The polyline that the enemies must traverse
     */
    public Wave(List<Point> polyline) {
        this.polyline = polyline;
    }
}
