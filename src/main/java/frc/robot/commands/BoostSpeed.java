package frc.robot.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class BoostSpeed extends Command {

    @Override
    public void initialize() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        SmartDashboard.putString("boost", dtf.format(now));
        RobotContainer.doSpeedBoost = true;
    }

    @Override
    public void end(boolean inInterupped) {
        RobotContainer.doSpeedBoost = false;
    }
}
