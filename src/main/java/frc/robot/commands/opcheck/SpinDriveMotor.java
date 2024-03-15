package frc.robot.commands.opcheck;

import frc.robot.subsystems.SwerveDrivetrain;
import edu.wpi.first.wpilibj2.command.Command;

public class SpinDriveMotor extends Command {
    boolean direction;
    SwerveDrivetrain drivetrain;
    int motor;
    
    public SpinDriveMotor(SwerveDriveTrain drivetrain, boolean direction, int motor){
        this.direction = direction;
        this.drivetrain = drivetrain;
        this.motor = motor;
    }

    @Override
    public void execute() {
        switch (motor){
            case 1:
                drivetrain.setFrontLeftDriveSpeed(1);
            case 2:
                drivetrain.setFrontRightDriveSpeed(1);
            case 3:
                drivetrain.setRearLeftDriveSpeed(1);
            case 4:
                drivetrain.setRearRightDriveSpeed(1);
        }

    }
}