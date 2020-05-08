/**
 * @author Duncan Grubbs
 * @date 2020-04-13
 * @license MIT
 */

/*
Position of cells, parsing the grid into nine
regions for easier automata rule creation because
of simpler state.
*/
public enum Position {
    NW,
    NE,
    SE,
    SW,
    NONE
}
