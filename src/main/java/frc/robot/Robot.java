// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.AnalogGyro;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * This is a sample program to demonstrate how to use a gyro sensor to make a robot drive straight.
 * This program uses a joystick to drive forwards and backwards while the gyro is used for direction
 * keeping.
 */

 
public class Robot extends TimedRobot {

  private ADIS16470_IMU imu = new ADIS16470_IMU();
  private final Joystick m_joystick = new Joystick(0);

  private GenericEntry m_maxSpeed;
  private NetworkTableEntry shuffle_angle;
  

  @Override
  public void robotInit(){
    imu.calibrate();


    // Add the tank drive and encoders to a 'Drivebase' tab

    /*// Put both encoders in a list layout
    ShuffleboardLayout encoders =
        driveBaseTab.getLayout("List Layout", "Encoders").withPosition(0, 0).withSize(2, 2);
    encoders.add("Left Encoder", m_leftEncoder);
    encoders.add("Right Encoder", m_rightEncoder);

    // Add the elevator motor and potentiometer to an 'Elevator' tab
    ShuffleboardTab elevatorTab = Shuffleboard.getTab("Elevator");
    elevatorTab.add("Motor", m_elevatorMotor);
    elevatorTab.add("Potentiometer", m_elevatorPot);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    */
  }

  /**
   * The motor speed is set from the joystick while the DifferentialDrive turning value is assigned
   * from the error between the setpoint and the gyro angle.
   */
  @Override
  public void teleopPeriodic() {
    System.out.println(imu.getAngle());
    SmartDashboard.putNumber("X angle", imu.getAngle());
    SmartDashboard.putNumber("X Angle", imu.g(getGyroInstantZ());
  }
}
