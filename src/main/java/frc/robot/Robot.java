package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.*;

public class Robot extends TimedRobot {

private static final int rightMotor0ID = 1;
private static final int rightMotor1ID = 2;
private static final int leftMotor0ID = 3;
private static final int leftMotor1ID = 4;

PIDController pid = new PIDController(0.005, 0, 0);

private CANSparkMax leftMotor0;
private CANSparkMax leftMotor1;
private CANSparkMax rightMotor0;
private CANSparkMax rightMotor1;

private double spinValue;



private DifferentialDrive m_robotDrive;

private Timer elapsedTime = new Timer();

private ADIS16470_IMU imu = new ADIS16470_IMU();
private final Joystick m_stick = new Joystick(0);


  

  @Override
  public void robotInit(){
    CameraServer.startAutomaticCapture();

  leftMotor0 = new CANSparkMax(leftMotor0ID, MotorType.kBrushless);
  leftMotor1 = new CANSparkMax(leftMotor1ID, MotorType.kBrushless);
  rightMotor0 = new CANSparkMax(rightMotor0ID, MotorType.kBrushless);
  rightMotor1 = new CANSparkMax(rightMotor1ID, MotorType.kBrushless);

  MotorControllerGroup leftMotors = new MotorControllerGroup(leftMotor0, leftMotor1);
  MotorControllerGroup rightMotors = new MotorControllerGroup(rightMotor0, rightMotor1);

  leftMotors.setInverted(true);
  


  m_robotDrive = new DifferentialDrive(leftMotors, rightMotors);

    imu.calibrate();
    elapsedTime.reset();
    elapsedTime.start();

  }

  @Override
  public void teleopPeriodic() {

    m_robotDrive.arcadeDrive( -m_stick.getY(), m_stick.getX() );

    SmartDashboard.putNumber("Pitch Axis", imu.getAngle(imu.getPitchAxis()));
    SmartDashboard.putNumber("Roll Axis", imu.getAngle(imu.getRollAxis()));
    SmartDashboard.putNumber("Yaw Axis", imu.getAngle(imu.getYawAxis()));
    SmartDashboard.putNumber("Calculated speed", pid.calculate(imu.getAngle(imu.getYawAxis()), 180));
    SmartDashboard.putNumber("Y Axis", -m_stick.getY());
    SmartDashboard.putNumber("X Axis", m_stick.getX());


  }

  public void autonomousPeriodic(){
    spinValue = pid.calculate(imu.getAngle(imu.getYawAxis()), 180);

    if(spinValue > 1){
      spinValue = 0.7;
    }

    m_robotDrive.arcadeDrive(0, spinValue);

    SmartDashboard.putNumber("Pitch Axis", imu.getAngle(imu.getPitchAxis()));
    SmartDashboard.putNumber("Roll Axis", imu.getAngle(imu.getRollAxis()));
    SmartDashboard.putNumber("Yaw Axis", imu.getAngle(imu.getYawAxis()));
    SmartDashboard.putNumber("Calculated speed", pid.calculate(imu.getAngle(imu.getYawAxis()), 180));
    SmartDashboard.putNumber("Y Axis", -m_stick.getY());
    SmartDashboard.putNumber("X Axis", m_stick.getX());
  }

  

}
