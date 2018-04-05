package org.usfirst.frc.team6203.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScaleAuto extends CommandGroup {

	public ScaleAuto(int robot_position, int scale_position) {
		if (robot_position == scale_position) {
			addSequential(new DriveToTimeout(0.8, 0.8, 2.0));
			addSequential(new DriveAndRaiseElevator(0.4, 6.0, 0.5, 5.0));
			addSequential(new Wait(0.5));
			addSequential(new TurnToSetpoint(scale_position == 0 ? 30 : -30));
			addSequential(new DriveToTimeout(0.3, 0.3, 2.0));
			addSequential(new Wait(0.5));
			addSequential(new SetIntake(-0.5, 1.0));
			addSequential(new DriveAndRaiseElevator(-0.1, 3.0, -0.5, 3.0));
		} else { // Let's do this when we actually have time :)
			addSequential(new BaseLineAuto());
		}
	}
}