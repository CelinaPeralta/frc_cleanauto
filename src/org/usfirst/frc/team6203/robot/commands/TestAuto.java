package org.usfirst.frc.team6203.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestAuto extends CommandGroup {

	//for putting a bunch of random crap in
	public TestAuto() {
		System.out.println("doing test auto");
		addSequential(new SetElevator(0.5, 3.5));
	}
}
	