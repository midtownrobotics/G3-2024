package frc.utils;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;

public class ShooterUtils {
    public InterpolatingDoubleTreeMap lookupTableShooterAngle;
    public static ShooterUtils instance = new ShooterUtils();

    public ShooterUtils() {
        lookupTableShooterAngle = new InterpolatingDoubleTreeMap();
    }

    public double getAngleFromDistance(double distance) {
        return lookupTableShooterAngle.get(distance);
    }
}
