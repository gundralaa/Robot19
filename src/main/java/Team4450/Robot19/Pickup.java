package Team4450.Robot19;

import Team4450.Lib.Util;

public class Pickup {

    final double DRIVE_POWER = 0.6;
    final double STOP_POWER = 0.0;
    
    private Robot robot;
    private boolean isExtended;

    public Pickup(Robot robot){
        this.robot = robot;
    }

    public void extend()
	{		
		Devices.pickupValve.SetA();
		isExtended = true;
	}
	
	public void retract()
	{
		Devices.pickupValve.SetB();
		isExtended = false;
	}
	
	public boolean isExtended()
	{
		return isExtended;
    }
    
    public void runPickup(){
        Devices.pickupMotor.set(DRIVE_POWER);
    }

    public void stopPickup(){
        Devices.pickupMotor.set(STOP_POWER);
    }

}