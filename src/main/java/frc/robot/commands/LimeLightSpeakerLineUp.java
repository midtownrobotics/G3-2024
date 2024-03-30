package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveDrivetrain;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.Constants;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.controller.PIDController;

public class LimeLightSpeakerLineUp extends Command{
    Limelight limelight;
    SwerveDrivetrain swerveDrivetrain;
    double previousAngle;
    PIDController limelightPID;

    public LimeLightSpeakerLineUp (Limelight limelight, SwerveDrivetrain swerveDrivetrain) {
        this.limelight = limelight;
        this.swerveDrivetrain = swerveDrivetrain;
        limelightPID = new PIDController(Constants.LimeLightPostioningConstants.P, Constants.LimeLightPostioningConstants.I, Constants.LimeLightPostioningConstants.D);
        addRequirements(limelight);
        addRequirements(swerveDrivetrain);
    }

    @Override
    public void execute() {
        // swerveDrivetrain.turnAngleUsingPidController(limelight.getAngleOffset());
        // swerveDrivetrain.calculateTurnAngleUsingPidController();\
        /* 
        if (limelight.getAngleOffset() == 0){
            swerveDrivetrain.getRearRightModule().setDesiredState(new SwerveModuleState(0, new Rotation2d(previousAngle)));
        } else {
            previousAngle = limelight.getAngleOffset();
            swerveDrivetrain.getRearRightModule().setDesiredState(new SwerveModuleState(0, new Rotation2d(limelight.getAngleOffset())));
        }
        */
        swerveDrivetrain.drive(0, 0, limelightPID.calculate(limelight.getAngleOffset()), isScheduled());

    }

    @Override
    public void end(boolean interrupted) {
        swerveDrivetrain.drive(0, 0, 0, false, false);
    }

    @Override
    public boolean isFinished() {
        //return (Math.abs(targetX - limelight.getX()) < Constants.LimeLightPostioningConstants.deviation && Math.abs(targetY - limelight.getY()) < Constants.LimeLightPostioningConstants.deviation);
        return false;
    }


}
