package Team4450.Robot19;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.imgproc.Imgproc;

import Team4450.Lib.Util;
import Team4450.Robot19.VisionFiles.GripPipelineReflectiveTape;

public class Vision 
{
	private Robot robot;
	public Rect   targetRectangleRight, targetRectangeLeft;
	private GripPipelineReflectiveTape pipeline = new GripPipelineReflectiveTape();
	
	private final double LEFT_ANGLE_THRESHOLD = 0.0;
	private final double RIGHT_ANGLE_THRESHOLD = 0.0;
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
		Mat image = null;

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

	public void getContourTargetAngled(){
		Mat image = null;
		RotatedRect rect2 = null, rect1 = null;
		image = robot.cameraThread.getCurrentImage();

		pipeline.process(image);

		int size = pipeline.filterContoursOutput().size();
		
		if( size > 0){
			MatOfPoint2f rect1Points = new MatOfPoint2f(pipeline.filterContoursOutput().get(0).toArray());
			rect1 = Imgproc.minAreaRect(rect1Points);
			
			Util.consoleLog("Rect1 Angle: d%", (int)rect1.angle);
			
			if(rect1.angle > LEFT_ANGLE_THRESHOLD){
				targetRectangeLeft = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
				return;
			}

			
		}

		if(size > 1){
			MatOfPoint2f rect2Points = new MatOfPoint2f(pipeline.filterContoursOutput().get(1).toArray());
			rect2 = Imgproc.minAreaRect(rect2Points);

			if(rect2.angle > LEFT_ANGLE_THRESHOLD){
				targetRectangeLeft = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
				targetRectangleRight = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
				return;
			}

			if(rect2.angle < RIGHT_ANGLE_THRESHOLD){
				targetRectangeLeft = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
				targetRectangleRight = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
				return;
			}
			
		}



	}
}
