/*
 * Leonard Maxim
 *
 * PRNG using the Linear congruential generator algorithm (https://en.wikipedia.org/wiki/Linear_congruential_generator)
 * Used only for illustrating a possible implementation of a random number generator class
 */

public class SimpleRandom {
    int seed = 49;									//initial value for the algorithm
    private int iterator = 0;						//keeps track of the last random number generated
    private int a = 8121, m = 134456, c = 28411;	// static variables (more info in the wiki page)

    //constructor with given seed
    SimpleRandom(int seed)
    {
        this.seed = seed;
        iterator = seed;
    }

    //default constructor
    SimpleRandom()
    {
        iterator = seed;
    }

    //returns a random number
    int nextInt()
    {
        iterator = rand(iterator);
        return iterator;
    }

    //returns a random number in range [min, max]
    int nextInt(int min, int max)
    {
        iterator = rand(iterator);
        return (min + (iterator) % (max - min + 1));
    }

    //actual LCG algorithm
    private int rand(int val)
    {
        double r = (a*val + c) % m;
        return (int)r;
    }

    int[] randArray(int n, int min, int max)
    {
        int[] rand_arr = new int[n];
        for (int i = 0; i < n; i++) {
            rand_arr[i] = nextInt(min, max);
        }
        return rand_arr;
    }

}