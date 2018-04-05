package org.usfirst.frc.team6203.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestAuto extends CommandGroup {
	
	private double delay = 0.5;
	int switch_position = 0;

	public TestAuto() {
//		addSequential(new DriveAndRaiseElevator(0.8, 3.0, 0.5, 2.5));
//		addSequential(new Wait(delay));
		addSequential(new TurnToSetpoint(-90));
		addSequential(new Wait(2));
		addSequential(new TurnToSetpoint(90));
//		addSequential(new Wait(delay));
//		addSequential(new DriveToTimeout(0.4, 0.4, 1.0));
//		addSequential(new Wait(delay));
//		addSequential(new SetIntake(-0.4, 0.75));
//		addSequential(new DriveToTimeout(-0.25, -0.25, 1.0));
	}
}
