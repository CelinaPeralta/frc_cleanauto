package org.usfirst.frc.team6203.robot.subsystems;

import org.usfirst.frc.team6203.robot.OI;
import org.usfirst.frc.team6203.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LED extends Subsystem {

	I2C p;
	DigitalOutput rst;
	byte[] prev = { -1 };

	boolean keyboard_mode = false;
	boolean keyboard_input_mode = false;
	String s = "";

	public LED() {
		p = new I2C(I2C.Port.kOnboard, 9);
		rst = new DigitalOutput(3);
	}

	public void set(byte[] data) {
		boolean eq = prev.length == data.length;
		for (int i = 0; eq && i < data.length; i++)
			eq = data[i] == prev[i];

		if (eq)
			return;

		prev = data;

		System.out.print("doing transaction: ");
		for (int i = 0; i < data.length; i++)
			System.out.print(data[i] + " ");
		System.out.println("");
		p.transaction(data, data.length, new byte[0], 0);
	}

	public void initDefaultCommand() {
		pong_or_gg();
	}

	public byte getEmote() {
		int a = OI.emojiStick.getRawButton(RobotMap.emoji0) ? 1 : 0;
		int b = OI.emojiStick.getRawButton(RobotMap.emoji1) ? 1 : 0;
		int c = OI.emojiStick.getRawButton(RobotMap.emoji2) ? 1 : 0;
		int d = OI.emojiStick.getRawButton(RobotMap.emoji3) ? 1 : 0;

		return (byte) (8 * a + 4 * b + 2 * c + d);
	}

	public void emote() {
		if (keyboard_mode && !s.equals("")) return;
		
		byte x = getEmote();
		byte[] data = { 1, 0, x };
		set(data);
	}

	public void glhf() {
		byte[] data = { 1, 1 };
		set(data);
	}

	public void pong_or_gg() {
		byte[] data = { 1, 2 };
		set(data);
	}

	public void text() {
		byte[] data = { 1, 2 };
		set(data);
	}
	
	public void check_reset(){
		rst.set(!OI.driverStick.getRawButton(7));	
		SmartDashboard.putBoolean("pressed thing", OI.driverStick.getRawButton(7));
//		SmartDashboard.putNumber("pressed test thing", System.currentTimeMillis()%1000);
	}
	
	public void check_keyboard(){
		keyboard_mode = OI.VStick2.getRawButton(13);
		
		boolean tmp = OI.VStick2.getRawButton(12);
		
		if (keyboard_input_mode && !tmp){
			//just finished input
			System.out.println("final = " + s);
			s = "";
		}else if (keyboard_input_mode){
			//still inputting

			for (int i=1;i<=16;i++){
				if (OI.VStick1.getRawButtonPressed(i))
					s += (char)(i-1+(int)'a');
			}

			for (int i=1;i<=10;i++){
				if (OI.VStick2.getRawButtonPressed(i))
					s += (char)(i+15+(int)'a');
			}

			if (OI.VStick2.getRawButtonPressed(11))
				s += " ";
		}
		
		keyboard_input_mode = tmp;

		if (keyboard_mode) update_keyboard();
	}
	
	public void update_keyboard(){
		if (s.equals("")) return;

		byte[] data = new byte[s.length() + 1];
		data[0] = 2;
		
		for (int i=0;i<s.length();i++)
			data[i+1] = (byte) s.charAt(i); 
		
		set(data);
	}

}
