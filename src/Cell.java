/**
 * @author Duncan Grubbs
 * @date 2020-04-13
 * @license MIT
 */

/**
 * Represents a single Cellular Automaton, containing
 * a single cell state.
 */
public class Cell {
    private CellState currentState;

    public Cell(CellState startState) {
        this.currentState = startState;
    }

    public CellState getCurrentState() {
        return currentState;
    }
}
