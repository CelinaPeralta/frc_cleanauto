package org.usfirst.frc.team6203.robot.commands;

import org.usfirst.frc.team6203.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SameSwitchAuto extends CommandGroup {

	private double delay = 0.4;

	public SameSwitchAuto() {
			addParallel(new DriveToTimeout(0.8, 0.8, 1));
			addParallel(new SetElevator(0.5, 3.5));
			addSequential(new Wait(delay));
			addSequential(new TurnToSetpoint(Robot.robot_position == 0 ? 90 : -90));
			addSequential(new Wait(delay));
			addSequential(new DriveToTimeout(0.6, 0.6, 2));
			addSequential(new Wait(delay));
			addSequential(new SetIntake(-0.4, 0.75));
	}
}
