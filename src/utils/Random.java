package utils;

public class Random {
    // The states of the random number generator
    static double[] states = new double[2];

    // Initialize the states with the current time
    static {
        states[0] = System.nanoTime();
        states[1] = System.nanoTime();
    }

    /**
     * Generates a random number between 0 and 1.
     * Uses the xorshift128+ algorithm.
     *
     * @return a random number between 0 and 1
     */
    static double randomNext() {
        double x = states[0];
        long xbits = Double.doubleToLongBits(x);
        double y = states[1];
        long ybits = Double.doubleToLongBits(y);
        states[0] = y;
        xbits ^= xbits << 23;
        long res = xbits ^ ybits ^ (xbits >> 18) ^ (ybits >> 5);
        states[1] = Double.longBitsToDouble(res);
        return states[1] + y;
    }
}
