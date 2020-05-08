import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Duncan Grubbs
 * @date 2020-04-13
 * @license MIT
 */

/**
 * The Draw utility draws a given grid based on cell states.
 * As an input, it simply takes the 'grid' (2D array) of cells
 * and produces a png image in the root directory.
 */
public class Draw {
    /**
     * Each cell has a biome state, which represents its color.
     * These are the global color constants for each biome, but they
     * could be easily changed to textures.
     */
    public static final Color DESERT = new Color(205, 95, 20);
    public static final Color MOUNTAIN = new Color(230, 230, 230);
    public static final Color FOREST = new Color(97, 80, 68);
    public static final Color PLAIN = new Color(93, 192, 134);
    public static final Color LAKE = new Color(49, 157, 255);
    public static final Color RIVER = new Color(49, 157, 255);
    public static final Color NONE = new Color(0, 0, 0);
    public static int COUNT = 0;

    public static BufferedImage img = new BufferedImage(CellGrid.GRID_SIZE, CellGrid.GRID_SIZE, BufferedImage.TYPE_INT_RGB);

    /**
     * Saves a PNG image in a given location, if given
     * a previously instantiated BufferedImage and a location.
     * @param img Constructed image to be saved locally
     * @param location Directory location for the image
     */
    public static void SaveImage(BufferedImage img, String location) {
        File f = new File(location);
        try {
            ImageIO.write(img, "jpg", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adjusts the darkness of a color based on a
     * elevation value, this way, the biome color constants
     * are modified to created a more accurate topological image.
     * @param c Original color to be modified
     * @param elevation Elevation value
     * @return New, modified Color
     */
    public static Color adjustColorOnElevation(Color c, double elevation) {
        double alpha = (elevation/255.0);
        int r = (int) (c.getRed() * alpha);
        int g = (int) (c.getGreen() * alpha);
        int b = (int) (c.getBlue() * alpha);
        Color nc = new Color(r, g, b);
        return nc;
    }

    /**
     * Draws colors from the cell states in the CellGrid
     * in a BufferedImage and saves the image to the 'images'
     * directory.
     * @param grid Grid of current automata cell states.
     */
    public static void draw(CellGrid grid) {
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                CellState state = grid.getGrid()[i][j].getCurrentState();
                switch (state.getBiome()) {
                    case DESERT:
                        img.setRGB(i, j, adjustColorOnElevation(DESERT, state.getElevation()).getRGB());
                        break;
                    case MOUNTAIN:
                        img.setRGB(i, j, adjustColorOnElevation(MOUNTAIN, state.getElevation()).getRGB());
                        break;
                    case LAKE:
                        img.setRGB(i, j, adjustColorOnElevation(LAKE, state.getElevation()).getRGB());
                        break;
                    case PLAIN:
                        img.setRGB(i, j, adjustColorOnElevation(PLAIN, state.getElevation()).getRGB());
                        break;
                    case FOREST:
                        img.setRGB(i, j, adjustColorOnElevation(FOREST, state.getElevation()).getRGB());
                        break;
                    case RIVER:
                        img.setRGB(i, j, adjustColorOnElevation(RIVER, state.getElevation()).getRGB());
                        break;
                    case NONE:
                        img.setRGB(i, j, NONE.getRGB());
                        break;
                }

            }
        }

        String filename = "MAP_" + COUNT + ".jpg";
        SaveImage(img, filename);
        // Iterate so we get a unique filename each time
        COUNT++;
    }
}
