package org.usfirst.frc.team6203.robot.commands;

import org.usfirst.frc.team6203.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SameScaleAuto extends CommandGroup {
	
	double delay = 0.4;

	public SameScaleAuto() {
		addSequential(new DriveToTimeout(0.8, 0.75, 2.5));
		addSequential(new Wait(2));
		addSequential(new TurnToSetpoint(Robot.robot_position == 0 ? 40 : -40));
		addSequential(new Wait(delay));
		addSequential(new SetElevator(0.7, 5.1));
		addSequential(new Wait(delay));
		addSequential(new DriveToTimeout(0.5, 0.5, .9));
		addSequential(new Wait(delay));
		addSequential(new SetIntake(-0.4, 0.75));
		addSequential(new Wait(delay));
		addSequential(new DriveToTimeout(-0.5, -0.5, 2));
	}
}