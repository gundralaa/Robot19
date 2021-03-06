package Team4450.Robot19;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.imgproc.Imgproc;

import Team4450.Lib.Util;
import Team4450.Robot19.VisionFiles.GripPipelineReflectiveTape;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision 
{
	private Robot robot;
	public Rect   targetRectangleRight, targetRectangeLeft;
	private GripPipelineReflectiveTape pipeline = new GripPipelineReflectiveTape();
	
	private final double LEFT_ANGLE_THRESHOLD = 0.0;
	private final double RIGHT_ANGLE_THRESHOLD = 0.0;
	// This variable and method make sure this class is a singleton.
	
	public static Vision vision = null;

	private NetworkTableInstance nsit;
	private NetworkTable vision_table;
	private NetworkTableEntry inner_dist;
	private NetworkTableEntry turn_angle;
	private NetworkTableEntry angle_offset;
	
	/**
	* Get reference to the single instance of this class shared by any caller of
	* this method.
	* @return Reference to single shared instance of this class.
	*/
	public static Vision getInstance(Robot robot) 
	{
		if (vision == null) vision = new Vision(robot);
		
		return vision;
	}
	
	// Private constructor prevents multiple instances from being created.
	
	private Vision(Robot robot) 
	{
		this.robot = robot;

		nsit = NetworkTableInstance.getDefault();
		vision_table = nsit.getTable("PiVision");
		inner_dist = vision_table.getEntry("inner_dist");
		turn_angle = vision_table.getEntry("turn_angle");
		angle_offset = vision_table.getEntry("angle_offset");
		
		Util.consoleLog("Vision created!");
	}
	
	/**
	* Release any resources allocated and the singleton object.
	*/
	public void dispose()
	{
		vision =  null;
	}

	// Turn angle Relative
	public double getTurnAngle(){
		return turn_angle.getDouble(0.0);
	}

	// Inner Distance
	public double getInnerDist(){
		double offset = inner_dist.getDouble(0.0);
		//Util.consoleLog(".2f", offset );
		return offset;
	}

	// Angle Offset Public
	public double getAngleOffset(){
		return angle_offset.getDouble(0.0);
	}
	
	
}
