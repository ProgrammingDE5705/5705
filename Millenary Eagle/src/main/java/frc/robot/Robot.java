/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static Powertrain powertrain;
  public static Gripper gripper; 
  public static Elevator elevator;
  
  //public static final Object imgLock = new Object();

  NetworkTable table;
  NetworkTable subtable;
  NetworkTableEntry centerX;
  public static double center;

  Command autonomousCommand;
  public static String mode;
  String auto;

  public static OI oi;
  public static GlobalVariables globalvariables;
  
  SendableChooser<Command> chooser = new SendableChooser<>();
  SendableChooser<String> mode_chooser = new SendableChooser<>();
  SendableChooser<String> autonomous = new SendableChooser<>();
  
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    GlobalVariables.chassisSpeed = 0.6;

    powertrain = new Powertrain();
    gripper = new Gripper();
    elevator = new Elevator(); 
    
    oi = new OI();

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    table = inst.getTable("CenterVisionTarget");
    centerX = table.getEntry("centerVT");
    inst.startClientTeam(5705);

    chooser.setDefaultOption("Automatic Autonomous", null);
    chooser.addOption("Estation 1", null);
    chooser.addOption("Estation 2", null);
    chooser.addOption("Estation 3", null);
    
    mode_chooser.setDefaultOption("Automated Mode", "AM");
    mode_chooser.addOption("Manual Mode", "MM");

    autonomous.setDefaultOption("Yes", "Y");
    autonomous.addOption("No", "N");
    
    SmartDashboard.putData("Select Autonomous", chooser);
    SmartDashboard.putData("Select Mode", mode_chooser);
    SmartDashboard.putData("Autonomous On?", autonomous);
  }
  
  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    mode = mode_chooser.getSelected();

    center = centerX.getDouble(0);
    SmartDashboard.putNumber("CenterX", center);

  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }
  
  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    autonomousCommand = chooser.getSelected();
    auto = autonomous.getSelected();
    
    /*
    * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (autonomousCommand != null && auto != "N") {
      autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();

    if (auto != "Y") autonomousCommand.cancel();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  
}