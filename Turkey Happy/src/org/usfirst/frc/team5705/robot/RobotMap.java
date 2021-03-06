/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5705.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

public class RobotMap {

	public static final Port drivetrain_gyro = SPI.Port.kOnboardCS0;
	//Motores
	public static final int drivetrain_leftFrontMotor = 7;
	public static final int drivetrain_rigthFrontMotor = 8;
	public static final int drivetrain_leftRearMotor = 2;
	public static final int drivetrain_rightRearMotor = 3;
	public static final int elevator_rM = 6;
	public static final int elevator_lM = 5;
	//Solenoides
	public static final int solenoid_arm = 0;
	
	public static final int Joystick_1 = 0;
	public static final int cam = 0;

	
}
