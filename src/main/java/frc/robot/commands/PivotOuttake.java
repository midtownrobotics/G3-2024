package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Outtake;

public class PivotOuttake extends Command {
    private final Outtake outtake;
    private final double power;

    public PivotOuttake(Outtake outtake, double power) {
        this.outtake = outtake;
        this.power = power;
    }

    @Override
    public void initialize() {
        outtake.pivot(power);
    }

    @Override
    public void end(boolean interrupted) {
        outtake.pivot(0);
    }
}
