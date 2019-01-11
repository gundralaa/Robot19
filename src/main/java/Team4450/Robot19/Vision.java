package Team4450.Robot19;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import Team4450.Lib.Util;
import Team4450.Robot19.VisionFiles.GripPipelineReflectiveTape;

public class Vision 
{
	private Robot robot;
	public Rect   targetRectangleRight, targetRectangeLeft;
	private GripPipelineReflectiveTape pipeline;
	
	// This variable and method make sure this class is a singleton.
	
	public static Vision vision = null;
	
	public static Vision getInstance(Robot robot) 
	{
		if (vision == null) vision = new Vision(robot);
		
		return vision;
	}
	
	// This is the rest of the class.
	
	private Vision(Robot robot) 
	{
		this.robot = robot;
		
		Util.consoleLog("Vision created!");
	}

	public double getContourDistanceBox(){
		double offset = 0.0;
		double centerXLeft = 0.0, centerXRight = 0.0;
		Mat image;

	    image = robot.cameraThread.getCurrentImage();

		pipeline.process(image);
		
		if(pipeline.filterContoursOutput().size() > 1){
			targetRectangeLeft = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
			targetRectangleRight = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
		}

		if(targetRectangeLeft != null && targetRectangleRight != null){
			centerXLeft = targetRectangeLeft.x + targetRectangeLeft.width / 2;
			centerXRight = targetRectangleRight.x + targetRectangleRight.width / 2;

			offset = Math.abs((centerXLeft - centerXRight));
		}

		return offset;
	}
}
