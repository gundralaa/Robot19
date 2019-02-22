package Team4450.Robot19;

import Team4450.Lib.Util;

public class Raiser {
    private Robot robot;
    private boolean frontClimbExtended, rearClimbExtended;

    public Raiser(Robot robot){
        this.robot = robot;

    }

    // This is the rest of the class.
	
	public boolean isFrontExtended()
	{
		return frontClimbExtended;
	}
	
	public boolean isRearExtended()
	{
		return rearClimbExtended;
	}
	
	public void extendFrontClimb(boolean override)
	{
		Util.consoleLog();
		
		if (!override && rearClimbExtended) return;
		
		Devices.frontClimbValve.Open();
		
		frontClimbExtended = true;
	}
	
	public void retractFrontClimb(boolean override)
	{
		Util.consoleLog();
		
		if (!override && !rearClimbExtended) return;
		
		Devices.frontClimbValve.Close();
		
		frontClimbExtended = false;
	}
	
	public void extendRearClimb(boolean override)
	{
		Util.consoleLog();
		
		if (!override && !frontClimbExtended) return;
		
		Devices.rearClimbValve.Open();
		
		rearClimbExtended = true;
	}
	
	public void retractRearClimb()
	{
		Util.consoleLog();
		
		Devices.rearClimbValve.Close();
		
		rearClimbExtended = false;
	}

}