package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Outtake;

public class ChangeSpeed extends Command{
    private final Outtake outtake;
    private final double speed;
    private final String mode;

    public ChangeSpeed(Outtake outtake, double speed, String newMode) {
        this.outtake = outtake;
        this.speed = speed;
        this.mode = newMode;
        addRequirements(outtake);
    }

    @Override
    public void initialize() {
        outtake.setSpeed(speed);
        outtake.setMode(mode);
    }

}
