package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveDrivetrain;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.Constants;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class LimeLightSpeakerLineUp extends Command{
    double targetX;
    double targetY;
    Limelight limelight;
    SwerveDrivetrain swerveDrivetrain;
    double previousAngle;

    public LimeLightSpeakerLineUp (double targetX, double targetY, Limelight limelight, SwerveDrivetrain swerveDrivetrain) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.limelight = limelight;
        this.swerveDrivetrain = swerveDrivetrain;
        addRequirements(limelight);
        addRequirements(swerveDrivetrain);
    }

    @Override
    public void execute() {
        // swerveDrivetrain.turnAngleUsingPidController(limelight.getAngleOffset());
        // swerveDrivetrain.calculateTurnAngleUsingPidController();
        if (limelight.getAngleOffset() == 0){
            swerveDrivetrain.getRearRightModule().setDesiredState(new SwerveModuleState(0, new Rotation2d(previousAngle)));
        } else {
            previousAngle = limelight.getAngleOffset();
            swerveDrivetrain.getRearRightModule().setDesiredState(new SwerveModuleState(0, new Rotation2d(limelight.getAngleOffset())));
        }
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
