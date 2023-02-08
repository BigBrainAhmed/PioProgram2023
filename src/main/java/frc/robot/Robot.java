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

    m_robotDrive.arcadeDrive(x_stick.getLeftY() * 0.5, x_stick.getRightX());

    System.out.println(imu.getAngle());
<<<<<<< HEAD
    SmartDashboard.putNumber("X angle", imu.getAngle());
    SmartDashboard.putNumber("X Angle", imu.g(getGyroInstantZ());
=======

//previous code
    SmartDashboard.putNumber("Angle", imu.getAngle());
    SmartDashboard.putNumber("AccelX", imu.getAccelX());
    SmartDashboard.putNumber("AccelY", imu.getAccelY());
    SmartDashboard.putNumber("AccelZ", imu.getAccelZ());
//.
    SmartDashboard.putBoolean("Bridge Limit", bridgeTipper.atBridge());
    SmartDashboard.putNumber("Bridge Angle", bridgeTipper.getPosition());
    SmartDashboard.putNumber("Swerve Angle", drivetrain.getSwerveAngle());
    SmartDashboard.putNumber("Left Drive Encoder", drivetrain.getLeftEncoder());
    SmartDashboard.putNumber("Right Drive Encoder", dri vetrain.getRightEncoder());
    SmartDashboard.putNumber("Turret Pot", turret.getCurrentAngle());
    SmartDashboard.putNumber("Turret Pot Voltage", turret.getAverageVoltage());
    SmartDashboard.putNumber("RPM", shooter.getRPM());

>>>>>>> 2a280f3f8c4e61467dfdf3135f71e03dacba8252
  }
}
