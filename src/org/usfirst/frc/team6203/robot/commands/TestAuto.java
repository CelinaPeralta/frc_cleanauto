package org.usfirst.frc.team6203.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestAuto extends CommandGroup {

    public TestAuto() {
        addSequential(new DriveToTimeout(0.5, 0.5, 3.0));
        addSequential(new Wait(4.0));
        addSequential(new TurnToSetpoint(90));
        addSequential(new Wait(1.0));
        addSequential(new TurnToSetpoint(90));
        addSequential(new DriveAndRaiseElevator(0.5, 3.0, 0.4, 2.0));
        addSequential(new SetIntake(-0.25, 1.0));
        addSequential(new SetElevator(-0.25, 3.0));
    }
}
