package frc.robot.subsystems.swervedrive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Shoter 
{
    private final CANSparkMax shooterMotorA;
    private final CANSparkMax shooterMotorB;
    private long seconds = 0;
    public Shoter(int driveMotorChannelA,int driveMotorChannelB)
    {
        shooterMotorA = new CANSparkMax(driveMotorChannelA, MotorType.kBrushless);
        shooterMotorB = new CANSparkMax(driveMotorChannelB, MotorType.kBrushless);
    }

    public void grab()
    {
        long startTime = System.currentTimeMillis();
        while(seconds <=1)
        {
            shooterMotorA.set(1);
            shooterMotorB.set(1);
            long endTime = System.currentTimeMillis();
            seconds = endTime - startTime;
        }
        shooterMotorA.set(0);
        shooterMotorB.set(0);  
    }
}
