package frc.robot.commands.opcheck;

import frc.robot.subsystems.SwerveDrivetrain;
import edu.wpi.first.wpilibj2.command.Command;

public class FaceDriveTrainForward extends Command {

    SwerveDrivetrain drivetrain;
    
    public FaceDriveTrainForward(SwerveDrivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }
    
    @Override
    public void execute() {
        drivetrain.setFrontLeftRotation(0);
        drivetrain.setFrontRightRotation(0);
        drivetrain.setRearLeftRotation(0);
        drivetrain.setRearRightRotation(0);
    }
}