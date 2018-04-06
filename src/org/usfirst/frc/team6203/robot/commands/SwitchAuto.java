package org.usfirst.frc.team6203.robot.commands;

import org.usfirst.frc.team6203.robot.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwitchAuto extends CommandGroup {

	private double delay = 0.4;

	public SwitchAuto() {
		if (Robot.robot_position == 1) {
			//lord have mercy
			addSequential(new TurnToSetpoint(Robot.switch_position == 0 ? -45 : 45));
			addSequential(new Wait(delay));
			addSequential(new DriveToTimeout(0.8, 0.8, .4));
			//			addSequential(new DriveAndRaiseElevator(0.6, 3.0, 0.5, 1));
			addSequential(new Wait(0.1));
			addSequential(new SetElevator(0.5, 3.5));
			addSequential(new Wait(delay));
			addSequential(new TurnToSetpoint(Robot.switch_position == 0 ? 45 : -45));
			addSequential(new Wait(delay));
			addSequential(new DriveToTimeout(0.6, 0.6, 2));
			addSequential(new Wait(delay));
			addSequential(new SetIntake(-0.4, 0.75));
			//addSequential(new DriveToTimeout(-0.25, -0.25, 1.0));
		} else if (Robot.robot_position == Robot.switch_position) {
			addSequential(new DriveToTimeout(0.8, 0.8, 1));
			addSequential(new Wait(0.1));
			addSequential(new SetElevator(0.5, 3.5));
			addSequential(new Wait(delay));
			addSequential(new TurnToSetpoint(Robot.switch_position == 0 ? 90 : -90));
			addSequential(new Wait(delay));
			addSequential(new DriveToTimeout(0.6, 0.6, 2));
			addSequential(new Wait(delay));
			addSequential(new SetIntake(-0.4, 0.75));
			//addSequential(new DriveToTimeout(-0.25, -0.25, 1.0));
		} else {
			addSequential(new BaseLineAuto());
		}
	}
}
