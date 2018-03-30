

public class RunningRobot {
	 
	public static void main(String[] args)
	{
		System.out.println("Start Test");
		Robot r = new Robot();
		r.moveX = 0;
		r.moveY = 0;
		r.facingO = 0;
		
		r.autonomousInit();
		
		r.switchSide = "left";
		r.autonModeRight = "cross";
		r.autonModeLeft = "cross";
		r.robotPosition = 1;
		r.switchPosition = "front";
		r.autonAlliance = "blue";
		r.autonWait = 1.0;
		
		//for( int a = 0; a < 10; a++ )
		while( r.rr.autonStep != "end")
		{
			r.autonomousPeriodic();
		}
	}

}
