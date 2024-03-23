package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Outtake;

public class SpeedPID extends Command {
    private final Outtake outtake;
    private String mode;
    private double speed;

    public SpeedPID(Outtake outtake, double speed, String mode) {
        this.outtake = outtake;
        this.mode = mode;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        outtake.pidWheel(speed);
        outtake.setMode(mode);
    }

    public void execute() {
        outtake.pidWheel(speed);
        outtake.setRightSpeed();
        outtake.setMode(mode);
    }

    @Override
    public void end(boolean interrupted) {
        outtake.pidWheel(0);
    }
}
