package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Outtake;

public class RunOuttake extends Command{

    private final Outtake outtake;
    private final double power;
    
    public RunOuttake(Outtake outtake, double power){
        this.power = power;
        this.outtake = outtake;
    }

    @Override
    public void initialize(){
        outtake.run(power);
    }

    @Override
    public void end(boolean interrupted){
        outtake.run(0);
    }



}
