import java.util.Random;

/**
 * @author Duncan Grubbs
 * @date 2020-04-13
 * @license MIT
 */

/**
 * Defines a 2D array (grid) of cells, constructing
 * the grid and populating it with initial state, as well
 * as updating the grid according to the cellular automata
 * rules.
 */
public class CellGrid {
    public static final int GRID_SIZE = 500;
    public static int TIME = 0;
    public PerlinNoise noise;

    private Cell[][] grid = new Cell[GRID_SIZE][GRID_SIZE];

    public CellGrid(int initialCellsToCreate) {
        // Instantiate the Perlin Noise object with a new random
        // seed so that the terrain will be unique.
        int seed = new Random().nextInt(100);
        noise = new PerlinNoise(seed);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                /*
                    Compute all of the initial state properties for
                    each cell before adding it into the grid.
                    This instantiation of the state should only
                    be done once, and then only modified by the rules
                    governing the automata.
                 */
                Position p = this.computeCellPosition(i, j);
                double elevation = noise.noise((double)i / 72.0, (double)j / 72.0);
                elevation = map(elevation, -1.0, 1.0, 0, 255);

                CellState startState = new CellState(Biome.NONE, p, elevation);
                this.grid[i][j] = new Cell(startState);
            }
        }

        /*
            Generate two arrays of random (x, y) pairs of positions to
            the given number of cells with biomes based purely on elevation.
            This is done so that when the states are updated through the automata
            rules there is some initial state to work with, otherwise nothing would
            change. To begin with, only mountain and lake biomes are populated.
         */
        SimpleRandom simpleRandom = new SimpleRandom();
        int[] xChoices = simpleRandom.randArray(initialCellsToCreate, 0, GRID_SIZE-1);
        int[] yChoices = simpleRandom.randArray(initialCellsToCreate, 0, GRID_SIZE-1);

        for (int c = 0; c < initialCellsToCreate; c++) {
            double elevation = grid[xChoices[c]][yChoices[c]].getCurrentState().getElevation();
            if (elevation < 80) {
                grid[xChoices[c]][yChoices[c]].getCurrentState().setBiome(Biome.LAKE);
            } else if (elevation > 180) {
                grid[xChoices[c]][yChoices[c]].getCurrentState().setBiome(Biome.MOUNTAIN);
            }
        }
    }

    /**
     * Computes the Position tuple values of a cell
     * given its location in the grid.
     * @param i Row in the grid
     * @param j Column in the grid
     * @return Position tuple value to be set in state
     */
    public Position computeCellPosition(int i, int j) {
        if (i < GRID_SIZE/2) {
            if (j < GRID_SIZE/2) {
                return Position.NW;
            } else {
                return Position.NE;
            }
        } else {
            if (j < GRID_SIZE/2) {
                return Position.SW;
            } else {
                return Position.SE;
            }
        }
    }

    /**
     * Utility: Maps a number from one range to another
     * @param value - value that has to be remapped
     * @param inStart - lower bound of first range
     * @param inStop - upper bound of second range
     * @param outStart - lower bound of first range
     * @param outStop - upper bound of second range
     * **/
    public static final double map(double value, double inStart, double inStop, double outStart, double outStop)
    {
        return outStart + (outStop - outStart) * ((value - inStart) / (inStop - inStart));
    }

    /**
     * Utility for printing out the entire grid
     * to the console for debugging.
     */
    public void print() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(this.grid[i][j].getCurrentState().getPosition());
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * Checks whether a given cell is
     * adjacent to a given biome. In this case adjacent
     * means in the cell's Moore neighborhood
     * @param i Row index of cell
     * @param j Column index of cell
     * @param b Biome to check adjacency to
     * @return If there is a cell with the given biome in
     * the Moore neighborhood
     */
    public boolean isAdjacentToBiome(int i, int j, Biome b) {
        // Get surrounding cell's states
        CellState top = grid[i][j-1].getCurrentState();
        CellState bottom = grid[i][j+1].getCurrentState();
        CellState right = grid[i+1][j].getCurrentState();
        CellState left = grid[i-1][j].getCurrentState();

        CellState upperLeft = grid[i-1][j+1].getCurrentState();
        CellState upperRight = grid[i+1][j+1].getCurrentState();
        CellState lowerLeft = grid[i-1][j-1].getCurrentState();
        CellState lowerRight = grid[i+1][j-1].getCurrentState();

        if (right.getBiome() == b
            || left.getBiome() == b
            || top.getBiome() == b
            || bottom.getBiome() == b
            || upperRight.getBiome() == b
            || upperLeft.getBiome() == b
            || lowerLeft.getBiome() == b
            || lowerRight.getBiome() == b
        ) {
            return true;
        }
        return false;
    }

    public int numAdjacent(int i, int j, Biome b) {
        // Get surrounding cell's states
        CellState top = grid[i][j-1].getCurrentState();
        CellState bottom = grid[i][j+1].getCurrentState();
        CellState right = grid[i+1][j].getCurrentState();
        CellState left = grid[i-1][j].getCurrentState();

        CellState upperLeft = grid[i-1][j+1].getCurrentState();
        CellState upperRight = grid[i+1][j+1].getCurrentState();
        CellState lowerLeft = grid[i-1][j-1].getCurrentState();
        CellState lowerRight = grid[i+1][j-1].getCurrentState();

        int num = 0;

        if (right.getBiome() == b) {
            num++;
        }

        if (left.getBiome() == b) {
            num++;
        }

        if (top.getBiome() == b) {
            num++;
        }

        if (bottom.getBiome() == b) {
            num++;
        }

        if (upperLeft.getBiome() == b) {
            num--;
        }

        if (upperRight.getBiome() == b) {
            num--;
        }

        if (lowerLeft.getBiome() == b) {
            num--;
        }

        if (lowerRight.getBiome() == b) {
            num--;
        }

        return num;
    }

    /**
     * Given a grid position, construct the new state through automata rules
     * for the cell to take on after this iteration.
     * @param i Row position in the grid
     * @param j Column position in the grid
     * @return Updated cell state
     */
    public CellState generateNewCellState(int i, int j) {
        CellState activeState = grid[i][j].getCurrentState();
        // Ff we are a border cell, keep the state the same
        if (j == 0 ||
            j == GRID_SIZE-1 ||
            i == GRID_SIZE-1 ||
            i == 0) {
            return activeState;
        }

        CellState top = grid[i][j-1].getCurrentState();
        CellState bottom = grid[i][j+1].getCurrentState();
        CellState right = grid[i+1][j].getCurrentState();
        CellState left = grid[i-1][j].getCurrentState();

        boolean inCanyon = (left.getElevation() > activeState.getElevation() && right.getElevation() > activeState.getElevation())
                || (top.getElevation() > activeState.getElevation() && bottom.getElevation() > activeState.getElevation());

        boolean onSlope = ((left.getElevation() > activeState.getElevation() && right.getElevation() < activeState.getElevation())
        || (left.getElevation() < activeState.getElevation() && right.getElevation() > activeState.getElevation())
        || (top.getElevation() < activeState.getElevation() && bottom.getElevation() > activeState.getElevation())
        || (top.getElevation() > activeState.getElevation() && bottom.getElevation() < activeState.getElevation()));

        /*
        We want rivers to flow from the tops of mountains, and then form lakes.
        From there, we want forest and grass to populate based on the proximity to water
        and other plain/forest, and some elevation threshold.
         */

        // RULES
        if (((left.getElevation() > activeState.getElevation() && right.getElevation() > activeState.getElevation())
            || (top.getElevation() > activeState.getElevation() && bottom.getElevation() > activeState.getElevation()))
            && (isAdjacentToBiome(i, j, Biome.RIVER) || isAdjacentToBiome(i, j, Biome.LAKE))
        ) {
            return new CellState(Biome.RIVER, Position.NONE, activeState.getElevation());
        }

        if ((left.getBiome() == Biome.NONE && right.getBiome() == Biome.NONE
             || top.getBiome() == Biome.NONE && bottom.getBiome() == Biome.NONE)
        && (isAdjacentToBiome(i, j, Biome.MOUNTAIN))) {
            return new CellState(Biome.RIVER, Position.NONE, activeState.getElevation());
        }

        if (inCanyon && numAdjacent(i, j, Biome.RIVER) == 1 &&
                (isAdjacentToBiome(i, j, Biome.FOREST) || isAdjacentToBiome(i, j, Biome.PLAIN))) {
            return new CellState(Biome.RIVER, Position.NONE, activeState.getElevation());
        }

        if ((isAdjacentToBiome(i, j, Biome.MOUNTAIN) && isAdjacentToBiome(i, j, Biome.FOREST))
            && (onSlope)) {
            return new CellState(Biome.RIVER, Position.NONE, activeState.getElevation());
        }

        if ((isAdjacentToBiome(i, j, Biome.RIVER) || isAdjacentToBiome(i, j, Biome.LAKE))
            && activeState.getElevation() <= 100) {
            return new CellState(Biome.LAKE, Position.NONE, activeState.getElevation());
        }

        if ((isAdjacentToBiome(i, j, Biome.PLAIN)
            || isAdjacentToBiome(i, j, Biome.RIVER)
            || isAdjacentToBiome(i, j, Biome.LAKE))
            && activeState.getElevation() > 100
            && activeState.getElevation() < 150) {
            return new CellState(Biome.PLAIN, Position.NONE, activeState.getElevation());
        }

        if (activeState.getElevation() > 180 && (isAdjacentToBiome(i, j, Biome.MOUNTAIN))) {
            return new CellState(Biome.MOUNTAIN, Position.NONE, activeState.getElevation());
        }

        if ((onSlope)
                && (isAdjacentToBiome(i, j, Biome.MOUNTAIN))
        ) {
            return new CellState(Biome.RIVER, Position.NONE, activeState.getElevation());
        }

        if ((isAdjacentToBiome(i, j, Biome.FOREST) || (isAdjacentToBiome(i, j, Biome.RIVER) && !isAdjacentToBiome(i, j, Biome.FOREST)))
                && activeState.getElevation() > 150
                && activeState.getElevation() < 180) {
            return new CellState(Biome.FOREST, Position.NONE, activeState.getElevation());
        }

        return activeState;
    }

    /**
     * Updates each cell state in the grid according to
     * the cellular automata rules.
     */
    public void update() {
        Cell[][] gridCopy = new Cell[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                CellState newState = generateNewCellState(i, j);
                gridCopy[i][j] = new Cell(newState);
            }
        }
        this.grid = gridCopy;
        TIME = TIME + 1;
    }

    public Cell[][] getGrid() {
        return grid;
    }
}
