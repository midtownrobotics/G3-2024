package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Ports;

public class IntakeBeamBreak {
    public void onTrue() {
        XboxController driver = new XboxController(Ports.USB.DRIVER_CONTROLLER);
        driver.setRumble(GenericHID.RumbleType.kRightRumble, 1);
        driver.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
    }
}
