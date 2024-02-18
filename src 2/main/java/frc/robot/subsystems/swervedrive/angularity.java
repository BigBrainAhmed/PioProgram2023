package frc.robot.subsystems.swervedrive;

import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PS4Controller.Button;

//https://codedocs.revrobotics.com/java/com/revrobotics/sparkmaxalternateencoder
public class angularity 
{
    private final CANSparkMax angleMotorA;
    private final CANSparkMax angleMotorB;
    private int angle;
    private long seconds = 0;
    public angularity(int driveMotorChannelA,int driveMotorChannelB)
    {
        angleMotorA = new CANSparkMax(driveMotorChannelA, MotorType.kBrushless);
        angleMotorB = new CANSparkMax(driveMotorChannelB, MotorType.kBrushless);
    }

    public void grab(Button buttonType)
    {
        if(buttonType.equals(buttonType))
        {

        }
        else if(angle == 1)
        {
            long startTime = System.currentTimeMillis();
            while(seconds <=1)
            {
                angleMotorA.set(1);
                angleMotorB.set(1);
                long endTime = System.currentTimeMillis();
                seconds = endTime - startTime;
            }
            angleMotorA.set(0); 
            angleMotorB.set(0); 
        }
        else
        {
            long startTime = System.currentTimeMillis();
            while(seconds <=1)
            {
                angleMotorA.set(1);
                angleMotorB.set(1);
                long endTime = System.currentTimeMillis();
                seconds = endTime - startTime;
            }
            angleMotorA.set(0); 
            angleMotorB.set(0); 
        } 
        angle = 0;
    }
    public void amp()
    {
        if(angle ==0)
        {
            long startTime = System.currentTimeMillis();
            while(seconds <=1)
            {
                angleMotorA.set(1);
                angleMotorB.set(1);
                long endTime = System.currentTimeMillis();
                seconds = endTime - startTime;
            }
            angleMotorA.set(0); 
            angleMotorB.set(0); 
        }
        else if(angle ==1)
        {

        }
        else
        {
            long startTime = System.currentTimeMillis();
            while(seconds <=1)
            {
                angleMotorA.set(1);
                angleMotorB.set(1);
                long endTime = System.currentTimeMillis();
                seconds = endTime - startTime;
            }
            angleMotorA.set(0); 
            angleMotorB.set(0); 
        }
        angle = 1;
    }
    public void shoot()
    {
        if(angle == 0)
        {
            long startTime = System.currentTimeMillis();
            while(seconds <=1)
            {
                angleMotorA.set(1);
                angleMotorB.set(1);
                long endTime = System.currentTimeMillis();
                seconds = endTime - startTime;
            }
            angleMotorA.set(0); 
            angleMotorB.set(0); 
        }
        else if(angle ==1)
        {
            long startTime = System.currentTimeMillis();
            while(seconds <=1)
            {
                angleMotorA.set(1);
                angleMotorB.set(1);
                long endTime = System.currentTimeMillis();
                seconds = endTime - startTime;
            }
            angleMotorA.set(0); 
            angleMotorB.set(0);  
        }
        else
        {
            
        }
        angle = 2;
    }
}

