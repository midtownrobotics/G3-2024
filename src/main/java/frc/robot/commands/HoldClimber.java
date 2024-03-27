package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;

public class HoldClimber extends Command{
    private final Climber climber;
    private double left;
    private double right;
    
    public HoldClimber(Climber climber) {
        this.climber = climber;
    }

    @Override
    public void initialize() {
        left = climber.getLeftWinchEncoder();
        right = climber.getRightWinchEncoder();
    }

    @Override
    public void execute() {
        climber.pidWinch(left, right);
    }

}
