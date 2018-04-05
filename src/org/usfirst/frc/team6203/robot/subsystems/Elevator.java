package org.usfirst.frc.team6203.robot.subsystems;

import org.usfirst.frc.team6203.robot.Constants;
import org.usfirst.frc.team6203.robot.OI;
import org.usfirst.frc.team6203.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	boolean b_bottom, b_switch, b_top, halt, preset_switch, preset_scale, move_switch, move_top;
	public Victor elevatorMotor;
	public DigitalInput DI_bottom, DI_switch, DI_scale, DI_top;

	int state = 0;

	public Elevator() {
		elevatorMotor = new Victor(RobotMap.elevatorMotor);
		elevatorMotor.setInverted(true);

		// Instantiate limit switches
		DI_bottom = new DigitalInput(RobotMap.DI_bottom);
		DI_switch = new DigitalInput(RobotMap.DI_switch);
		DI_top = new DigitalInput(RobotMap.DI_top);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	private void updateButtons() {
		b_bottom = !DI_bottom.get();
		b_switch = !DI_switch.get();
		b_top = !DI_top.get();

		if (!move_switch && preset_switch)
			move_switch = true;
		if (!move_top && preset_scale)
			move_top = true;

		halt = OI.driverStick.getRawButton(11);
	}

	public void drive() {
		updateButtons();

		if (b_switch)
			if (elevatorMotor.get() > 0)
				state = 1;
			else if (elevatorMotor.get() < 0)
				state = 0;

		if (!halt) {
			if (move_switch) {
				if (b_switch)
					move_switch = false;
				else if (state == 0)
					elevatorMotor.set(Constants.m_ElevatorPresetSpeed);
				else if (state == 1)
					elevatorMotor.set(-Constants.m_ElevatorPresetSpeed);
				return;
			}
			if (move_top) {
				if (b_top)
					move_top = false;
				else
					elevatorMotor.set(Constants.m_ElevatorPresetSpeed);
				return;
			}
		} else
			move_switch = move_top = false;

		double x = OI.elevatorStick.getX();

		if (b_bottom && x < 0)
			return;
		if (b_top && x > 0)
			return;

		elevatorMotor.set(x);
	}

	public void setElevator(double speed) {

		if (b_bottom && speed < 0)
			return;
		if (b_top && speed > 0)
			return;

		elevatorMotor.set(speed);

	}

}
