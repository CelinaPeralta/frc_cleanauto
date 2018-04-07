package org.usfirst.frc.team6203.robot.commands;

import org.usfirst.frc.team6203.robot.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SameSwitchAuto extends CommandGroup {

	private double delay = 0.4;

	public SameSwitchAuto() {
		addSequential(new SetElevator(0.8, 1));
		addSequential(new Wait(0.2));
		addSequential(new SetElevator(-0.4, .5));
		addSequential(new SetIntake(0.5, 0.2));
		addSequential(new Wait(delay));
		addSequential(new DriveToTimeout(0.8, 0.8, 1));
		addSequential(new SetElevator(0.8, 1.5));
		addSequential(new Wait(1.7));
		addSequential(new TurnToSetpoint(Robot.robot_position == 0 ? 90 : -90));
		addSequential(new DriveToTimeout(0.65, 0.65, .65));
		addSequential(new SetIntake(-0.4, 0.75));
		addSequential(new Wait(1.5));
		addSequential(new DriveToTimeout(-0.6, -0.6, 1));
	}
}
