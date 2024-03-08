package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Outtake;

public class ChangeSpeed extends Command{
    private final Outtake outtake;
    private final double speed;

    public ChangeSpeed(Outtake outtake, double speed) {
        this.outtake = outtake;
        this.speed = speed;
        addRequirements(outtake);
    }

    @Override
    public void initialize() {
        outtake.setSpeed(speed);
    }

}
