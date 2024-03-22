package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Outtake;

public class SpeedPID extends Command {
    private final Outtake outtake;

    public SpeedPID(Outtake outtake) {
        this.outtake = outtake;
    }

    @Override
    public void initialize() {
        outtake.pidWheel();
    }

    @Override
    public void end(boolean interrupted) {
        outtake.pidWheel(0);
    }
}
