package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveDrivetrain;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.Constants;
import edu.wpi.first.math.controller.PIDController;

public class LimeLightSpeakerLineUp extends Command{
    double targetX;
    double targetY;
    NetworkTableEntry entryX;
    NetworkTableEntry entryY;
    double currentX;
    double currentY;
    Limelight limelight;
    SwerveDrivetrain swerveDrivetrain;
    PIDController limelightPID;
    PIDController limelightRotationPID;
    

    public LimeLightSpeakerLineUp (double targetX, double targetY, Limelight limelight, SwerveDrivetrain swerveDrivetrain) {
        this.targetX = targetX;
        this.targetY = targetY;
        entryX = limelight.getX();
        entryY = limelight.getY();
        addRequirements(limelight);
        addRequirements(swerveDrivetrain);
        limelightPID = new PIDController(Constants.LimeLightPostioningConstants.P, Constants.LimeLightPostioningConstants.I, Constants.LimeLightPostioningConstants.D);
        limelightRotationPID = new PIDController(Constants.LimeLightPostioningConstants.rotP, Constants.LimeLightPostioningConstants.rotI, Constants.LimeLightPostioningConstants.rotD);
    }

    @Override
    public void execute() {
        currentX = entryX.getDouble(0);
        currentY = entryY.getDouble(0);
        swerveDrivetrain.drive(limelightPID.calculate(targetX, currentX), limelightPID.calculate(targetY, currentY), , true);
    }

    @Override
    public boolean isFinished() {
        return (Math.abs(targetX - currentX) < Constants.LimeLightPostioningConstants.deviation && Math.abs(targetY - currentY) < Constants.LimeLightPostioningConstants.deviation);
    }


}
