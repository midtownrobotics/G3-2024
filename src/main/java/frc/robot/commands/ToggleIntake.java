package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class ToggleIntake extends Command{
    private final Intake intake;

    public ToggleIntake(Intake intake) {
        this.intake = intake;
    }

    @Override
    public void initialize() {
        intake.togglePivot();
    }
}
