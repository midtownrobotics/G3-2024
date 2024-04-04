package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Outtake;

public class PivotPID extends Command{
    private Outtake outtake;
    private double angle;

    public PivotPID(Outtake outtake, double angle) {
        this.outtake = outtake;
        this.angle = angle;
    }

    @Override
    public void execute() {
        outtake.setPivot(angle);
    }

}
