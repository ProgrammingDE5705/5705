/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5705.robot;

import org.usfirst.frc.team5705.robot.subsystems.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * La VM est� configurada para ejecutar autom�ticamente esta clase y para llamar
 * a las funciones correspondientes a cada modo, como se describe en la
 * documentaci�n de TimedRobot. Si cambia el nombre de esta clase o el paquete
 * despu�s de crear este proyecto, tambi�n debe actualizar el archivo
 * build.properties en el proyecto.
 */
public class Robot extends TimedRobot {
	public static double chassisSpeed;
	public static Drivetrain drivetrain;
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * Esta funci�n se ejecuta cuando el robot se inicia por primera vez y debe ser
	 * utilizado para cualquier c�digo de inicializaci�n.
	 */
	public void robotInit() {
		chassisSpeed = 0.8;
		drivetrain = new Drivetrain();

		oi = new OI();

		chooser.addDefault("Default Auto", null);
		chooser.addObject("My Auto", null);
		chooser.addObject("La Buena dijo el Frank", null);
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * Esta funci�n se llama una vez cada vez que el robot ingresa al modo
	 * Deshabilitado. Puede usarlo para restablecer cualquier informaci�n del
	 * subsistema que desee borrar cuando el robot est� deshabilitado.
	 */
	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * Este modo aut�nomo (junto con el c�digo de selecci�n anterior) muestra c�mo
	 * seleccionar entre diferentes modos aut�nomos utilizando el panel de control.
	 * El c�digo de selecci�n enviable funciona con Java SmartDashboard. Si prefiere
	 * el Tablero de LabVIEW, elimine todo el c�digo del selector y elimine el
	 * comentario del c�digo getString para obtener el nombre autom�tico del cuadro
	 * de texto debajo de Gyro
	 *
	 * <p>
	 * Puede agregar modos autom�ticos adicionales agregando comandos adicionales al
	 * c�digo de selecci�n anterior (como el ejemplo comentado) o comparaciones
	 * adicionales a la estructura del conmutador a continuaci�n con cadenas y
	 * comandos adicionales.
	 */
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// programar el comando aut�nomo (ejemplo)
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	/**
	 * Esta funci�n se llama peri�dicamente durante el aut�nomo.
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// Esto asegura que la autonom�a se detiene cuando se inicia la ejecuci�n de
		// teleop. Si desea que la autonom�a contin�e hasta que sea interrumpida por
		// otro comando, elimine esta l�nea o com�ntela.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	/**
	 * Esta funci�n se llama peri�dicamente durante el control del operador.
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * Esta funci�n se llama peri�dicamente durante el modo de prueba.
	 */
	public void testPeriodic() {
	}
}
