package frc.robot.commands.opcheck;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class RunIntakes extends Command{
    Intake intake;
    double power;

    public RunIntakes(Intake intake, double power) {
        this.intake = intake;
        this.power = power;
    }

    @Override
    public void execute() {
        intake.run(power);
    }
}
