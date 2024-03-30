package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Outtake;

public class PivotOuttake extends Command {
    private final Outtake outtake;
    private final boolean up;

    public PivotOuttake(Outtake outtake, boolean up) {
        this.outtake = outtake;
        this.up = up;
    }

    @Override
    public void initialize() {
        if(up) {
            outtake.setAngle(outtake.getAngle() - .01);
        } else {
            outtake.setAngle(outtake.getAngle() + .01);
        }
    }

    @Override
    public void end(boolean interrupted) {
        outtake.pivot(0);
    }
}
