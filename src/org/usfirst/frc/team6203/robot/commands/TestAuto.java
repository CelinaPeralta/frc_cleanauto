package org.usfirst.frc.team6203.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team6203.robot.Robot;
import org.usfirst.frc.team6203.robot.commands.*;

/**
 *
 */
public class TestAuto extends CommandGroup {

	private double delay = 0.5;

	public TestAuto() {
		System.out.println("doing test auto");
		addSequential(new SetElevator(0.5, 3.5));
	}
}
	