package org.usfirst.frc.team6203.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestAuto extends CommandGroup {
	
	private double delay = 0.5;
	int switch_position = 0;

	public TestAuto() {
		addSequential(new TurnToSetpoint(-90));
		addSequential(new Wait(2));
		addSequential(new TurnToSetpoint(90));
	}
}
