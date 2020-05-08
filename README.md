# Biome Evolution
> Growing biomes with Perlin Noise and Cellular Automata.

### 💻 Implementation
My implementation works in the following way. First, it creates a 500x500 2D array. Next, it populates the arraywithautomatacells that justcontain an elevationin their state. Then, a number(by default 1500)of random pixels are assigned the mountain (snow) biome based purely on their elevation(i.e. if their elevation is greater than 180). At this point, there is enough information in the map to start updating cells based on the automatarules. The biome(cell)that has the most complex update rulesand starts growing firstis RIVER. In one update iteration, each cell is updated based on the states of the cells in its Moore neighborhood. The most common update rule checks if there is a neighboring cell with a biome defined and the elevation. If the biome is compatible, the cell takes on that biome. When drawn, the color of each pixel on the map visualizes itscell’s biomewhich could beLAKE, RIVER, MOUNTAIN, FOREST, orPLAIN.MOUNTAIN(snow) is white, FORESTis a brown, PLAINis green, and RIVERand LAKEare blue. The colors are also adjusted according to the cell’s elevation so that lower elevations are darkened, and higher elevations are brightened.

### 🔨 How to Build
- `cd src`
- `javac *.java`
- `java Main`

