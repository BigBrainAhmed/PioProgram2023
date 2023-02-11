package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.ADIS16470_IMU;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NTSendableBuilder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Robot extends TimedRobot {

private static final int rightMotor0ID = 1;
private static final int rightMotor1ID = 2;
private static final int leftMotor0ID = 3;
private static final int leftMotor1ID = 4;




private CANSparkMax leftMotor0;
private CANSparkMax leftMotor1;
private CANSparkMax rightMotor0;
private CANSparkMax rightMotor1;

private DifferentialDrive m_robotDrive;

private Timer elapsedTime = new Timer();

private ADIS16470_IMU imu = new ADIS16470_IMU();
private final Joystick m_stick = new Joystick(0);
private final XboxController x_stick = new XboxController(1);


  

  @Override
  public void robotInit(){
    CameraServer.startAutomaticCapture();

  leftMotor0 = new CANSparkMax(leftMotor0ID, MotorType.kBrushless);
  leftMotor1 = new CANSparkMax(leftMotor1ID, MotorType.kBrushless);
  rightMotor0 = new CANSparkMax(rightMotor0ID, MotorType.kBrushless);
  rightMotor1 = new CANSparkMax(rightMotor1ID, MotorType.kBrushless);

  leftMotor0.setInverted(true);


  m_robotDrive = new DifferentialDrive(leftMotor0, rightMotor0);

    imu.calibrate();
    elapsedTime.reset();
    elapsedTime.start();
  }

  @Override
  public void teleopPeriodic() {

    m_robotDrive.arcadeDrive( -m_stick.getY(),m_stick.getX() );


    SmartDashboard.putNumber("getAngle", imu.getAngle() % 360.0);
    SmartDashboard.putNumber("getAccelX", imu.getAccelX());
    SmartDashboard.putNumber("getAccelY", imu.getAccelY());
    SmartDashboard.putNumber("getAccelZ", imu.getAccelZ());
    SmartDashboard.putNumber("getRate", imu.getRate());
    SmartDashboard.putNumber("getXComplementaryAngle", imu.getXComplementaryAngle());
    SmartDashboard.putNumber("getYComplementaryAngle", imu.getYComplementaryAngle());
    SmartDashboard.putNumber("getPort", imu.getPort());


  }

  public void autonomousInit(){
    while(imu.getAngle() < 180){
      m_robotDrive.arcadeDrive(0.2, 1);
    }
  }

}
