package frc.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

import java.nio.file.attribute.AclEntryPermission;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.*;
import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.cameraserver.CameraServer;






public class Robot extends TimedRobot {


private static final int rightMotor0ID = 1;
private static final int rightMotor1ID = 2;
private static final int leftMotor0ID = 3;
private static final int leftMotor1ID = 4;
private static final int armRetracterID = 6;
private static final int armRaiserID = 7;



private CANSparkMax leftMotor0;
private CANSparkMax leftMotor1;
private CANSparkMax rightMotor0;
private CANSparkMax rightMotor1;
private CANSparkMax armRetracter;
private CANSparkMax armRaiser;

double rotationSpeed;

PIDController turnController = new PIDController(0.3, 0, 0);



private DoubleSolenoid solenoid = new DoubleSolenoid(10, PneumaticsModuleType.REVPH, 7, 5);


PIDController pid = new PIDController(0.1, 0.01, 0);


PhotonCamera cam = new PhotonCamera("camera");


boolean onPlatform;
private double spinValue;

double percentPower = 0.8;


private Timer elapsedTime = new Timer();
private ADIS16470_IMU imu = new ADIS16470_IMU();
private final Joystick m_stick = new Joystick(0);
private DifferentialDrive m_robotDrive;





 @Override
 public void robotInit(){
  CameraServer.startAutomaticCapture();

 pid.setTolerance(1);


 leftMotor0 = new CANSparkMax(leftMotor0ID, MotorType.kBrushless);
 leftMotor1 = new CANSparkMax(leftMotor1ID, MotorType.kBrushless);
 rightMotor0 = new CANSparkMax(rightMotor0ID, MotorType.kBrushless);
 rightMotor1 = new CANSparkMax(rightMotor1ID, MotorType.kBrushless);
 
 armRetracter = new CANSparkMax(armRetracterID, MotorType.kBrushless);
 armRaiser = new CANSparkMax(armRaiserID, MotorType.kBrushless);

 MotorControllerGroup leftMotors = new MotorControllerGroup(leftMotor0, leftMotor1);
 MotorControllerGroup rightMotors = new MotorControllerGroup(rightMotor0, rightMotor1);


 leftMotors.setInverted(true);
 m_robotDrive = new DifferentialDrive(leftMotors, rightMotors);

 imu.calibrate();
 solenoid.set(DoubleSolenoid.Value.kForward);
 }


 @Override
 public void teleopPeriodic() {

  if(m_stick.getRawButton(8)){
    percentPower = 1;
  } else if (m_stick.getRawButton(10)){
    percentPower = 0.8;
  } else if(m_stick.getRawButton(12)){
    percentPower = 0.6;
  } else if(m_stick.getRawButtonPressed(11)){
    percentPower -= 0.1;
  } else if(m_stick.getRawButtonPressed(9)){
    percentPower += 0.1;
  }

  if(m_stick.getRawButton(6)){
    armRetracter.set(0.8);
  } else if (m_stick.getRawButton(4)){
    armRetracter.set(-0.8);
  } else {
    armRetracter.set(0);
  }


  if(m_stick.getRawButton(5)){
    armRaiser.set(0.8);
  } else if (m_stick.getRawButton(3)){
    armRaiser.set(-0.8);
  } else {
    armRaiser.set(0);
  }

 
  if(m_stick.getTriggerPressed()){
    if(solenoid.get() == DoubleSolenoid.Value.kForward){
      solenoid.set(DoubleSolenoid.Value.kReverse);
    } else if (solenoid.get() == DoubleSolenoid.Value.kReverse){
      solenoid.set(DoubleSolenoid.Value.kForward);
    }
  }

  if(m_stick.getRawButton(2)){
    var result = cam.getLatestResult();
    SmartDashboard.putBoolean("Has Target", result.hasTargets());
   
    if(result.hasTargets()){
      rotationSpeed = -turnController.calculate(result.getBestTarget().getYaw(), 0);
      SmartDashboard.putNumber("Target Yaw", result.getBestTarget().getYaw());
    } else {
      rotationSpeed = 0;
    }
  } else {
    rotationSpeed = m_stick.getX();
  }
 

   m_robotDrive.arcadeDrive( -m_stick.getY() * percentPower, rotationSpeed * percentPower);

   SmartDashboard.putNumber("%Power", percentPower);
   SmartDashboard.putNumber("Y Axis", -m_stick.getY());
   SmartDashboard.putNumber("X Axis", m_stick.getX());
   SmartDashboard.putString("Value", solenoid.get().toString());
   
 }



 public void autonomousInit(){
  imu.calibrate();
  elapsedTime.reset();
  elapsedTime.start();

  pid.setPID(0.05, 0, 0);
  pid.setTolerance(2);
 }


 public void autonomousPeriodic(){
  if(elapsedTime.get() < 4){
    m_robotDrive.arcadeDrive(0.5, 0);
  }



 }
}

