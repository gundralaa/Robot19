package Team4450.Robot19;

import java.text.BreakIterator;

import Team4450.Lib.Util;
import edu.wpi.first.wpilibj.PIDController;


public class Lift {
    private Robot robot;
	private boolean	holdingHeight;
    private final PIDController	pidController;
    
    private final int UPPER_LIMIT = 0;
    
    private final int FIRST_LEVEL_COUNT = 0;
    private final int SECOND_LEVEL_COUNT = 0;
    private final int THIRD_LEVEL_COUNT = 0;

    public enum Level {
        BASE, FIRST, SECOND, THIRD;
    }

    private Level level;
    

    // Public Constructor Method
    public Lift(Robot robot){
        this.robot = robot;
        // Initialize PID Controller and Values
        pidController = new PIDController(0.0, 0.0, 0.0, Devices.winchEncoder, Devices.winchDrive);
		// Reset Encoder	
        Devices.winchEncoder.reset();

        level = Level.BASE;
        		
		Util.consoleLog("Lift Instantiated");
    }

    public void setPower(double power)
	{
		if (Devices.winchEncoder != null)
		{
            // Checks for bottom
            if (Devices.winchSwitch.get()){
                    Devices.winchEncoder.reset();
            }
            // Checks for limits
			if (Devices.winchEncoder.get() < UPPER_LIMIT || !Devices.winchSwitch.get())
				Devices.winchDrive.set(power);
			else
			{
				Devices.winchDrive.set(0);
			}
			
		}
		else
			Devices.winchDrive.set(power);
    }

    public void runToLevel(Level level){
        switch (level) {
            case BASE:
                runToCount(0);
                break;
            case FIRST:
                runToCount(FIRST_LEVEL_COUNT);
                break;
            case SECOND:
                runToCount(SECOND_LEVEL_COUNT);
                break;
            case THIRD:
                runToCount(THIRD_LEVEL_COUNT);
                break;
            
        }

    }    
    private void runToCount(int count){
        Util.consoleLog("%d", count);
		
		if (count >= 0)
		{			
			pidController.setPID(0.0003, 0.00001, 0.0003, 0.0);
			pidController.setOutputRange(-1, 1);
			pidController.setSetpoint(count);
			pidController.setPercentTolerance(1);	// % error.
			pidController.enable();
		}
		else
		{
			pidController.disable();
		}
    }




    


}