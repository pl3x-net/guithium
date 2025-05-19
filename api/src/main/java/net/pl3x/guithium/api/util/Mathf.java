package net.pl3x.guithium.api.util;

/**
 * Math utility that works with floats rather than doubles
 */
public abstract class Mathf {
    private Mathf() {
        // Empty constructor to pacify javadoc lint
    }

    private final static float DEG2RAD = (float) (Math.PI / 180D);
    private final static float RAD2DEG = (float) (180D / Math.PI);

    /**
     * Converts an angle measured in degrees to an approximately
     * equivalent angle measured in radians.  The conversion from
     * degrees to radians is generally inexact.
     *
     * @param degrees an angle, in degrees
     * @return the measurement of the angle {@code degrees} in radians.
     */
    public static float toRadians(final float degrees) {
        return degrees * DEG2RAD;
    }

    /**
     * Converts an angle measured in radians to an approximately
     * equivalent angle measured in degrees.  The conversion from
     * radians to degrees is generally inexact; users should
     * <i>not</i> expect {@code cos(toRadians(90.0))} to exactly
     * equal {@code 0.0}.
     *
     * @param radians an angle, in radians
     * @return the measurement of the angle {@code radians} in degrees.
     */
    public static float toDegrees(final float radians) {
        return radians * RAD2DEG;
    }
}
