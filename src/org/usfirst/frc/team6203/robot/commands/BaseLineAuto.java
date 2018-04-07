package org.usfirst.frc.team6203.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BaseLineAuto extends CommandGroup {

	public BaseLineAuto() {
		addSequential(new DriveToTimeout(0.8, 0.75, 1.1));
		//addSequential(new DriveStraightToTimeout(0.8, 1.1));
	}
}
