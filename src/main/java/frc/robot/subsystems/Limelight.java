package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ChangeSpeed;;

public class Limelight extends SubsystemBase{
    NetworkTable networkTable;
    Outtake outtake;
    public static enum states {
		SPEAKER,
        AMP,
        STOP
	}
    states state = states.STOP;
    public Limelight (NetworkTable networkTable, Outtake outtake){
        this.networkTable = networkTable;
        this.outtake = outtake;
    }

    public int getID() {
        return (int) networkTable.getEntry("tid").getInteger(0);
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
        return networkTable.getEntry("targetpose_robotspace").getDoubleArray(new Double[] {0.0, 0.0, 0.0})[2]*100;
    }

    public double getAngleOffset() {
        if (getX() == 0 && getZ() == 0) {
            return 0;
        }
        return Math.atan2(getX(), getZ());
    }

    @Override
    public void periodic() {
        if (getID() == -1 && state != states.STOP) {
            state = states.STOP;
            new ChangeSpeed(outtake, 0, "stop");
        }

        if (DriverStation.getAlliance().get() == Alliance.Blue && (getID() == 7 || getID() == 5 || getID() == 6)) {
            if ((getID() == 8 || getID() == 7) && state != states.SPEAKER){
                state = states.SPEAKER;
                new ChangeSpeed(outtake, 4500, "speaker").schedule();
            }
            if (getID() == 6 && state != states.AMP){
                state = states.AMP;
                new ChangeSpeed(outtake, 700, "amp").schedule();
            }
        }

        if (DriverStation.getAlliance().get() == Alliance.Red && (getID() == 4 || getID() == 3 || getID() == 5)) {
            if ((getID() == 4 || getID() == 3) && state != states.SPEAKER){
                state = states.SPEAKER;
                new ChangeSpeed(outtake, 4500, "speaker").schedule();
            }
            if (getID() == 5 && state != states.AMP){
                state = states.AMP;
                new ChangeSpeed(outtake, 700, "amp").schedule();
            }
            SmartDashboard.putBoolean("isthere", true);
        } else {
            SmartDashboard.putBoolean("isthere", false);
        }
        switch (state) {
            case SPEAKER:
                SmartDashboard.putString("state", "speaker");
                break;
            case AMP:
                SmartDashboard.putString("state", "amp");
                break;
            case STOP:
                SmartDashboard.putString("state", "stop");
            default:
                break;
        }
    }
}
