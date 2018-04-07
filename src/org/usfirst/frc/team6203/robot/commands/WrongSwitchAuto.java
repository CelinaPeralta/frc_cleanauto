package org.usfirst.frc.team6203.robot.commands;

import org.usfirst.frc.team6203.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class WrongSwitchAuto extends CommandGroup {

	private double delay = 0.4;

	public WrongSwitchAuto() {
			addSequential(new DriveToTimeout(0.8, 0.8, 1.3));
			addSequential(new Wait(0.4));
			addSequential(new TurnToSetpoint(Robot.robot_position == 0 ? 90 : -90));
			addSequential(new Wait(0.4));
			addSequential(new DriveToTimeout(0.8, 0.8, 2));
			addSequential(new Wait(0.4));
			addSequential(new TurnToSetpoint(Robot.robot_position == 0 ? 90 : -90));
			addSequential(new Wait(0.4));
			addSequential(new SetElevator(0.5, 3.5));
			addSequential(new Wait(0.4));
			addSequential(new DriveToTimeout(0.6, 0.6, 2));
			addSequential(new Wait(delay));
			addSequential(new SetIntake(-0.4, 0.75));
	}
}
