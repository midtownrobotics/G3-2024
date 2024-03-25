package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Limelight extends SubsystemBase{
    NetworkTable networkTable;
    public Limelight (NetworkTable networkTable){
        this.networkTable = networkTable;
    }

    public NetworkTableEntry getX() {
        return networkTable.getEntry("tx");
    }

    public NetworkTableEntry getY() {
        return networkTable.getEntry("ty");
    }
}
