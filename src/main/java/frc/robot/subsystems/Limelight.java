package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Limelight extends SubsystemBase{
    NetworkTable networkTable;
    public Limelight (NetworkTable networkTable){
        this.networkTable = networkTable;
    }

    public double getX() {
        return networkTable.getEntry("targetpose_robotspace").getDoubleArray(new Double[] {0.0})[0]*100;
    }

    public double getY() {
        return networkTable.getEntry("ty").getDouble(0.0);
    }

    public double getSkew() {
        return networkTable.getEntry("ts").getDouble(0.0);
    }

    public double getZ() {
        return networkTable.getEntry("targetpose_robotspace").getDoubleArray(new Double[] {0.0})[2]*100;
    }

    public double getAngleOffset() {
        return Math.atan(getX()/getZ());
    }
}
