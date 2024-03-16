package frc.robot.commands.opcheck;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveDrivetrain;

public class RunTurnMotor extends Command{
    SwerveDrivetrain drivetrain;
    int motor;
    double rot;

    public RunTurnMotor(SwerveDrivetrain drivetrain, int motor, double rot) {
        this.drivetrain = drivetrain;
        this.motor = motor;
        this.rot = rot;
    }

    @Override
    public void execute() {
        switch (motor) {
            case 1:
                drivetrain.setFrontLeftRotation(rot);
            case 2:
                drivetrain.setFrontRightDriveSpeed(rot);
            case 3:
                drivetrain.setRearLeftDriveSpeed(rot);
            case 4:
                drivetrain.setRearRightDriveSpeed(rot);
        }
    }
}