public interface RobotInterface {
	boolean autonPlaceCube_Done();
	boolean autonForward_Done();
	boolean autonReverse_Done();
	boolean autonTurn_Done();
	String autonGetMode();
	String autonSwitchSide();
	int autonRobotPosition();
	double getAutonWait();
	String autonSwitchPosition();
	void autonPlaceCube_Reset();
	void autonForward_Reset();
	void autonTurn_Reset();
	void firstTimeAuton(boolean set);
	void autonPlaceCube(double time);
	void autonTurn(double turnAngle, boolean flip);
	void autonReverse(double distance, boolean flip);
	void autonForward(double distance, boolean flip);
}
