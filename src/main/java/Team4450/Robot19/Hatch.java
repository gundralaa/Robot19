package Team4450.Robot19;

import Team4450.Lib.Util;
import edu.wpi.first.wpilibj.PIDController;

public class Hatch {
    public enum Position {
        HIGH, MIDDLE, LOW
    }

    private Position currentPos;
    private Robot robot;
    private PIDController pidController;

    private final int UPPER_LIMIT = 0;
    
    private final int HIGH_COUNT = 0;
    private final int MIDDLE_COUNT = 0;
    private final int LOW_COUNT = 0;

    private final double P_VALUE = 0.0003;
    private final double I_VALUE = 0.00001;
    private final double D_VALUE = 0.0003;

    public Hatch(Robot robot){
        this.robot = robot;

        // Initialize PID Controller and Values
        pidController = new PIDController(0.0, 0.0, 0.0, Devices.hatchEncoder, Devices.hatchWinch);
		// Reset Encoder	
        Devices.hatchEncoder.reset();

        currentPos = Position.LOW;
        		
		Util.consoleLog("Hatch Mechanism Instantiated");

    }

    public void releaseHatch()
	{
		Util.consoleLog();
		
		Devices.hatchKickValve.Open();
    }
    
    public void runToPosition(Position pos){
        switch(pos){
            case LOW:
                runToCount(LOW_COUNT);
                break;
            case MIDDLE:
                runToCount(MIDDLE_COUNT);
                break;
            case HIGH:
                runToCount(HIGH_COUNT);
                break;
        }

    }

    private void runToCount(int count){
        Util.consoleLog("%d", count);
		
		if (count >= 0)
		{	
            		
			pidController.setPID(P_VALUE, I_VALUE, D_VALUE, 0.0);
            // Power Output Ranges
            pidController.setOutputRange(-1, 1);
            // Target Count
            pidController.setSetpoint(count);
            // Tolerance To Setpoint
			pidController.setPercentTolerance(1);	// % error.
			pidController.enable();
		}
		else
		{
			pidController.disable();
		}
    }



}