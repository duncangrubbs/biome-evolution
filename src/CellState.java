/**
 * @author Duncan Grubbs
 * @date 2020-04-13
 * @license MIT
 */

/**
 * For each Cellular Automaton, the state is made up of
 * four main components, the biome is one of a Tuple defined
 * in the Biome tuple, but essentially represents the color
 * of the cell. The position is also represented by a tuple
 * which represents which region of the grid the cell is in.
 * Time helps synchronize cell states across the grid during the
 * update phase.
 */
public class CellState {
    private Biome biome;
    private Position position;
    private int time;
    private double elevation;

    public CellState(Biome biome, Position position, double elevation) {
        this.biome = biome;
        this.position = position;
        this.elevation = elevation;

        this.time = CellGrid.TIME;
    }

    public Biome getBiome() {
        return biome;
    }

    public Position getPosition() {
        return position;
    }

    public double getElevation() {
        return elevation;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }
}
