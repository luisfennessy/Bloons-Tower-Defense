import bagel.map.TiledMap;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * An event within a wave which prompts the spawn of particular enemies over a set interval.
 */
public class SpawnEvent extends WaveEvent {

    private final int INDEXOFNUMSPAWN = 2;           // The index of the number to spawn in an event's description
    private final int INDEXOFENEMYTYPE = 3;          // The index of the number to spawn in an event's description

    // Identifiers of a particular slicer types in wave text file
    private final String regularSlicerName = "slicer";
    private final String superSlicerName = "superslicer";
    private final String megaSlicerName = "megaslicer";
    private final String apexSlicerName = "apexslicer";

    // The file location of each slicer's image
    private final String regSlicerImage = "res/images/slicer.png";
    private final String superSlicerImage = "res/images/slicer.png";
    private final String megaSlicerImage = "res/images/slicer.png";
    private final String apexSlicerImage = "res/images/slicer.png";

    private int numToSpawn;
    private ArrayList<Enemy> slicers;

    /**
     * Creates a new spawn event, specific to the mentioned enemy type.
     *
     * @param eventInfo Describes the properties of the wave event
     * @param polyline The path along which an enemy travels
     */
    public SpawnEvent(String[] eventInfo, List<Point> polyline) {
        super(eventInfo, polyline);
        numToSpawn = Integer.parseInt(eventInfo[INDEXOFNUMSPAWN]);
        Class enemyType;
        int i;
        // Adds to ArrayList of enemies, as per specified slicer type
        if (eventInfo[INDEXOFENEMYTYPE] == regularSlicerName) {
            for(i=0; i<numToSpawn; i++) {
                slicers.add(new Slicer(polyline, regSlicerImage, false));
            }
        } else if (eventInfo[INDEXOFENEMYTYPE] == superSlicerName) {

        } else if (eventInfo[INDEXOFENEMYTYPE] == megaSlicerName) {

        } else if (eventInfo[INDEXOFENEMYTYPE] == apexSlicerName) {

        }
    }
}
