package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Outtake;

public class IntakeOuttake extends Command {
    private final Intake intake;
    private final Outtake outtake;
    private final double power;

    public IntakeOuttake(Intake intake, Outtake outtake, double power) {
        this.intake = intake;
        this.outtake = outtake;
        this.power = power;
    }

    @Override
    public void initialize() {
        intake.run(power);
        if (outtake.getSpeed() != 0) {
            // outtake.run();
            outtake.roller(power);
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.run(0);
        outtake.run(0);
    }
    
}
