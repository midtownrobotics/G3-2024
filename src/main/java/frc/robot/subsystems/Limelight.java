package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;

public class Limelight extends SubsystemBase{
    NetworkTable networkTable;
    public Limelight (NetworkTable networkTable){
        this.networkTable = networkTable;
    }

    public int getTagId() {
        return (int) networkTable.getEntry("tid").getInteger(0);
    }

    public double getX() {
        return networkTable.getEntry("targetpose_robotspace").getDoubleArray(new Double[] {0.0})[0] * 100;
    }

    public double getY() {
        return networkTable.getEntry("ty").getDouble(0.0);
    }

    public double getSkew() {
        return networkTable.getEntry("ts").getDouble(0.0);
    }

    public double getZ() {
        return networkTable.getEntry("targetpose_robotspace").getDoubleArray(new Double[] {0.0, 0.0, 0.0})[2] * 100;
    }

    public double getAngleOffset() {
        if (getX() == 0 && getZ() == 0) {
            return 0;
        }
        return Math.atan2(getX(), getZ());
    }

    public double getDistance() {
        double rawDistance = Math.sqrt(Math.pow(getX(), 2) + Math.pow(getZ(), 2));
        return rawDistance * Math.cos(Math.toRadians(25));
    }
}
