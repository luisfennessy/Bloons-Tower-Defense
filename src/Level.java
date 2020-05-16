import bagel.map.TiledMap;

/**
 * A level of ShadowDefend game.
 */
public class Level {

    private TiledMap map;
    private Wave[] waves;

    /**
     * Creates a new level.
     *
     * @param levelNumber The numbered level
     */
    public Level(int levelNumber) {
        map = new TiledMap("res/levels/" + levelNumber + ".tmx");
    }

}
