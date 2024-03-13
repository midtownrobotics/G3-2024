package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class BoostSpeed extends Command {
    public BoostSpeed() {
        if (RobotContainer.doSpeedBoost) {
            RobotContainer.doSpeedBoost = false;
        } else {
            RobotContainer.doSpeedBoost = true;
        }
    }
}
