/**
 * @author Duncan Grubbs
 * @date 2020-04-13
 * @license MIT
 */

/*
Biome of cells, essentially representing the color
or texture of the cell when it is drawn.
This is also used for automata rule creation
and cell state updating.
*/
public enum Biome {
    FOREST,
    MOUNTAIN,
    PLAIN,
    DESERT,
    LAKE,
    RIVER,
    NONE
}
