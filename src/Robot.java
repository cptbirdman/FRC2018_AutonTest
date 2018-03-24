/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/



/*import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;*/

// PWM ID 0 -- two front motors with compliant wheels
// PWM ID 1 -- two rear motors with four compliant wheel shafts
// PWM ID 2 -- two intake motors
// CAN ID 9 -- front intake bar 
public class Robot /*extends IterativeRobot*/ implements RobotInterface {
	/*UsbCamera intakeCamera = CameraServer.getInstance().startAutomaticCapture("intake", "/dev/v4l/by-path/platform-ci_hdrc.0-usb-0:1.1:1.0-video-index0");
	CvSource intakeOutputStream;
	VisionThread intakeVisionThread;
	DigitalInput beamBreak;*/
	
	//UsbCamera backCamera = CameraServer.getInstance().startAutomaticCapture("intake", "/dev/v4l/by-path/platform-ci_hdrc.0-usb-0:1.2:1.0-video-index0");
	public int moveX;
	public int moveY;
	public int facingO;
	
	int contourNumber = 0;
	
	int pipelineRunning = 0;
	
	int exposureValue = 10;
	boolean exposureChanged = false;
	
	boolean foundTarget = false;
	
	// Vision Parameters
	double hueMin = 50;
	double hueMax = 122;
	double satMin = 105;
	double satMax = 255;
	double valMin = 50;
	double valMax = 255;
	
	double F = 0.027;
	double P = 0.05;
	double I = 0.0;
	double D = 1.0;
	
	//Test Variables
	String switchSide = "left";

	String autonSwitchPlace = "forward";
	double autonCrossDistance;
	
	double autonDistance;
	
	double inchPerSecond = 3.0;
	
	double targetDistance;
	
	double aspectRatio;
	
	boolean firstTimeAuton = true;
	boolean autonCrossDone = false;
	boolean autonTurnDone = false;
	boolean autonReverseDone = false;
	boolean autonForwardDone = false;
	
	boolean intakePistonToggle = true;
	
	double autonCrossTime;
	double autonCrossWait = 3000;
	
	double autonPlaceTime;
	double autonPlaceWait = 3000;
	
	double autonSpeed;
	
	boolean autonPlaceCubeStart;
	boolean autonPlaceCubeDone;
	double autonPlaceCubeTime;
	
	boolean startStep = true;
	
	boolean highGear = false;
	
	/*	
	AHRS ahrs;
	
	TalonSRX frontLeft = new TalonSRX(1);
	TalonSRX middleLeft = new TalonSRX(2);
    TalonSRX rearLeft = new TalonSRX(3);

    TalonSRX frontRight = new TalonSRX(4);
    TalonSRX middleRight = new TalonSRX(5);
    TalonSRX rearRight = new TalonSRX(6);
    
    Talon intake;
    Talon upperRamp;
    Talon lowerRamp;
    
    TalonSRX beatingStick;
    */
    //TalonSRX climberBottom;
    
    //Ultrasonic leftUltrasonic = new Ultrasonic(1,1);
    //Ultrasonic rightUltrasonic = new Ultrasonic(2,2);
    /*
    DoubleSolenoid driveTrainShift = new DoubleSolenoid(0, 0, 1);
    DoubleSolenoid ptoShift = new DoubleSolenoid(0, 4, 5);
    Compressor compressor = new Compressor(0);
    
    DoubleSolenoid intakePiston = new DoubleSolenoid(0, 2, 3);
    */
    boolean autonTurn1Done = false;
    
    //Encoder encLeft;
	//Encoder encRight;
	
	int countR;
	double rawDistanceR;
	double distanceR;
	double rateR;
	boolean directionR;
	boolean stoppedR;

	int countL;
	double rawDistanceL;
	double distanceL;
	double rateL;
	boolean directionL;
	boolean stoppedL;
    
    String gameData;
    
    int controllerMode = 1; // 1 is normal, 2 is experimental Attack 3 mode, and 3 is Joystick normal
    
    double[] lowerRampSpeeds = {0.65, 0.75};
    double[] upperRampSpeeds = {0.65};
    
    int lowerRampSpeed = 0;
    int upperRampSpeed = 0;
    
	//private Joystick leftStick;
	//private Joystick rightStick;
	//Joystick operatorStick;
	
	//SendableChooser<Double> autonWaitChoose;
	double autonWait;
	
	//SendableChooser<String> autonChooseLeft;
	String autonModeLeft;
	
	//SendableChooser<String> autonChooseRight;
	String autonModeRight;
	
	//SendableChooser<Integer> robotPositionChoose;
	int robotPosition;
	
	//SendableChooser<String> switchPositionChoose;
	String switchPosition;
	
	//SendableChooser<String> autonAllianceChoose;
	String autonAlliance;
	
	public Auton rr;
	
	public void runAuton() {
		//run the loop which has the logic to tell us what to do
		//e.g. we are the controlled and robotrunnable is the controller
		rr.Run();
	}
	
	public boolean nearZero(double in) {
		if (in >= -0.01 && in <= 0.01) {
			return true;
		} else {
			return false;
		}
	}

	

	
	public void setDriveMotors(double l, double r) {

	}
	
	public void adaptiveDrive(double l, double r) {

	}
	
	void resetDistanceAndYaw () {

	}
	


	// Auton helpers
	@Override
	public double getAutonWait() {
		return autonWait;
	}
	
	@Override
	public String autonSwitchPosition() {
		return switchPosition;
	}
	
	@Override
	public String autonSwitchSide() {
		return switchSide;
	}
	
	@Override
	public String autonGetMode() {
		if (switchSide == "left") {
			return autonModeLeft;
		} else if (switchSide == "right") {
			return autonModeRight;
		} else {
			return "cross";
		}
	}
	
	@Override
	public int autonRobotPosition() {
		return robotPosition;
	}
	
	@Override
	public boolean autonPlaceCube_Done() {
		return autonPlaceCubeDone;
	}
	
	@Override
	public boolean autonForward_Done() {
		return autonForwardDone;
	}
	
	@Override
	public boolean autonReverse_Done() {
		return autonReverseDone;
	}
	
	@Override
	public boolean autonTurn_Done() {
		return autonTurnDone;
	}
	
	@Override
	public void firstTimeAuton(boolean set) {
		firstTimeAuton = set;
	}
	
	@Override
	public void autonPlaceCube(double time) {
		if (firstTimeAuton) {
			autonPlaceCubeDone = false;
			autonPlaceCubeTime = System.currentTimeMillis();
			firstTimeAuton = false;
		}
		
		if ((System.currentTimeMillis() - autonPlaceCubeTime) <= time) {
			outakeUpperRamp(1);
			System.out.println("Place Cube");
		} else {
			outakeUpperRamp(1);
			autonPlaceCubeDone = true;
		}
	}
	
	@Override
	public void autonForward(double distance, boolean flip) {
		if (firstTimeAuton) {
			firstTimeAuton = false;
			autonCrossTime = System.currentTimeMillis();
			autonForwardDone = false;
			System.out.println("Foward");
		}
		
		if ((System.currentTimeMillis() - autonCrossTime) <= ((distance / inchPerSecond) * 1000) + autonCrossWait && (System.currentTimeMillis() - autonCrossTime) >= autonCrossWait) {
			if (!flip) {
				adaptiveDrive(1.0 * autonSpeed, 1.0 * autonSpeed);
			} else {
				adaptiveDrive(-1.0 * autonSpeed, -1.0 * autonSpeed);
			}
		} else {
			adaptiveDrive(0, 0);
			autonForwardDone = true;
		}
	}
	
	@Override
	public void autonReverse(double distance, boolean flip) {
		if (firstTimeAuton == true) {
			firstTimeAuton = false;
			autonCrossTime = System.currentTimeMillis();
			autonReverseDone = false;
			System.out.println("Reverse");
		}
		
		if ((System.currentTimeMillis() - autonCrossTime) <= ((distance / inchPerSecond) * 1000) + autonCrossWait && (System.currentTimeMillis() - autonCrossTime) >= autonCrossWait) {
			if (!flip) {
				adaptiveDrive(-1.0 * autonSpeed, -1.0 * autonSpeed);
			} else {
				adaptiveDrive(1.0 * autonSpeed, 1.0 * autonSpeed);
			}
		} else {
			adaptiveDrive(0, 0);
			autonReverseDone = true;
		}
	}
	
	@Override
	public void autonTurn(double turnAngle, boolean flip) {
		autonTurnDone = true;
		System.out.println("Turn " + Double.toString(turnAngle));
	}
	
	public void autonomousInit() {
		rr = new Auton(this);
		
		//intakePiston.set(DoubleSolenoid.Value.kForward);
		
		//gameData = DriverStation.getInstance().getGameSpecificMessage();
		

					

		
		//autonCrossDistance = SmartDashboard.getNumber("Auton Cross Distance", 119.0);
		//autonSpeed = SmartDashboard.getNumber("Auton Speed", 0.65);
		//inchPerSecond = SmartDashboard.getNumber("Inch Per Second", 53.0);
		
		autonTurnDone = false;
		autonTurn1Done = false;
		firstTimeAuton = true;

		resetDistanceAndYaw();
		
		//autonModeLeft = autonChooseLeft.getSelected();

	}
	

	public void autonomousPeriodic() {
		//driveTrainShift.set(DoubleSolenoid.Value.kOff);
		//updateEncoders();
		//SmartDashboard.getNumber("Auton Wait Choose", 0.0);
		runAuton();
	}
	
	public void teleopUpdateDashboard() {

	}
	

	
	public void driverController() {

	}
	
	public void intakeRoller(double run) {

	}
	
	public void intakePiston(boolean toggle) {

	}
	
	public void outakeUpperRamp(double run) {

	}
	
	public void reverseOutake(boolean run) {

	}
	 
	public void operaterController() {

	}
	

	public void teleopInit() {
		//intakePiston.set(DoubleSolenoid.Value.kForward);
		
		//gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if (gameData.length() > 0) {
			if (gameData.charAt(0) == 'L') {
				switchSide = "left";
			} else {
				switchSide = "right";
			}
		} else {
			switchSide = null;
		}
		
		//SmartDashboard.putNumber("filterContoursMinArea", 200.0);
		
		resetDistanceAndYaw();
	}
	

	public void teleopPeriodic() {
		//updateEncoders();
		driverController();
		operaterController();
		teleopUpdateDashboard();
	}
}
