/**
 * @author Duncan Grubbs
 * @date 2020-04-13
 * @license MIT
 */

public class Main {

    /**
     * Run to demo the project.
     * @param args
     */
    public static void main(String[] args) {
        /*
        SEED_CELLS specifies the number of
        initialized cells before the automata
        iterations are started. These seed the
        construction of biomes, and are only
        mountain (snow), where water can 'flow'
        down from. This shouldn't be more than
        a few thousand for best performance.

        ITERATIONS specifies the number of automata
        iterations to do (i.e. how many times to
        update cells according to the automata rules.
        Considering the way biomes 'grow' in this algorithm,
        this should be a highish number (something in the
        hundreds for good demonstrations).
         */
        int SEED_CELLS = 2500; // 1% of total pixels
        int ITERATIONS = 500;

        CellGrid c = new CellGrid(SEED_CELLS);
        for (int i = 0; i < ITERATIONS; i++) {
            c.update();
            Draw.draw(c);
        }
    }
}
