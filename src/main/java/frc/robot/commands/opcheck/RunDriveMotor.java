package frc.robot.commands.opcheck;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveDrivetrain;

public class RunDriveMotor extends Command{
    SwerveDrivetrain drivetrain;
    int motor;
    double power;

    public RunDriveMotor(SwerveDrivetrain drivetrain, int motor, double power) {
        this.drivetrain = drivetrain;
        this.motor = motor;
        this.power = power;
    }

    @Override
    public void execute() {
        switch (motor) {
            case 1:
                drivetrain.setFrontLeftDriveSpeed(power);
            case 2:
                drivetrain.setFrontRightDriveSpeed(power);
            case 3:
                drivetrain.setRearLeftDriveSpeed(power);
            case 4:
                drivetrain.setRearRightDriveSpeed(power);
        }
    }
    @Override
    public void end(boolean interrupted) {
        drivetrain.setFrontLeftDriveSpeed(0);
        drivetrain.setFrontRightDriveSpeed(0);
        drivetrain.setRearLeftDriveSpeed(0);
        drivetrain.setRearRightDriveSpeed(0);
    }
}
