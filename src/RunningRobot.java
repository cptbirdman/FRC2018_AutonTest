

public class RunningRobot {
	 
	public static void main(String[] args)
	{
		Robot r = new Robot();
		
		r.autonomousInit();
		for( int a = 0; a < 10; a++ )
		{
			r.autonomousPeriodic();
		}
	}

}
