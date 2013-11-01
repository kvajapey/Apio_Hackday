import java.util.ArrayList;
	
	//a class that contains an array for the arrays of second of data
	//and an array for the actvity of each second
public class Data {
	//an array for the accelerometer samples
	ArrayList<Double> accelX = new ArrayList<Double>();
	ArrayList<Double> accelY = new ArrayList<Double>();
	ArrayList<Double> accelZ = new ArrayList<Double>();
	//an array for the times of the accel samples
	ArrayList<Double> accelTime = new ArrayList<Double>();
	//an array for the gyroscope samples
	ArrayList<Double> gyroX = new ArrayList<Double>();
	ArrayList<Double> gyroY = new ArrayList<Double>();
	ArrayList<Double> gyroZ = new ArrayList<Double>();
	//an array for the times of the gyro samples
	ArrayList<Double> gyroTime = new ArrayList<Double>();
	//an array for the magnetometer samples
	ArrayList<Double> magnetX = new ArrayList<Double>();
	ArrayList<Double> magnetY = new ArrayList<Double>();
	ArrayList<Double> magnetZ = new ArrayList<Double>();
	//an array for the times of the magnetometer samples
	ArrayList<Double> magnetTime = new ArrayList<Double>();
	//arrays for the roll, pitch, and yaw
	ArrayList<Double> roll = new ArrayList<Double>();
	ArrayList<Double> pitch = new ArrayList<Double>();
	ArrayList<Double> yaw = new ArrayList<Double>();
	//an array for the times of the roll pitch and yaw samples
	ArrayList<Double> RPYTime = new ArrayList<Double>();
	//accelerometer sampling rate
	Double accelSR;
	//gyroscope sampling rate
	Double gyroSR;
	//magnetometer sampling rate
	Double magnetSR;
	//roll pitch and yaw sampling rate
	Double RPYSR;
	//an array for the actvity of each second
	ArrayList<String> activity = new ArrayList<String>();
	public Data(ArrayList<Double> newAccelX, ArrayList<Double> newAccelY, ArrayList<Double> newAccelZ,
				ArrayList<Double> newGyroX, ArrayList<Double> newGyroY, ArrayList<Double> newGyroZ,
				ArrayList<Double> newMagnetX, ArrayList<Double> newMagnetY, ArrayList<Double> newMagnetZ,
				ArrayList<Double> newRoll, ArrayList<Double> newPitch, ArrayList<Double> newYaw,
				Double newAccelSR, Double newGyroSR, Double newMagnetSR, Double newRPYSR,
				ArrayList<Double> newAccelTime, ArrayList<Double> newGyroTime, ArrayList<Double> newMagnetTime, 
				ArrayList<Double> newRPYTime){
		accelX = newAccelX;	
		accelY = newAccelY;
		accelZ = newAccelZ;
		gyroX = newGyroX;
		gyroY = newGyroY;
		gyroZ = newGyroZ;
		magnetX = newMagnetX;
		magnetY = newMagnetY;
		magnetZ = newMagnetZ;
		roll = newRoll;
		pitch = newPitch;
		yaw = newYaw;
		accelSR = newAccelSR;
		gyroSR = newGyroSR;
		magnetSR = newMagnetSR;
		RPYSR = newRPYSR;
		accelTime = newAccelTime;
		gyroTime = newGyroTime;
		magnetTime = newMagnetTime;
		RPYTime = newRPYTime;
	}
	public void setAccelerometerXData(ArrayList<Double> newAccelX){
		accelX = newAccelX;
	}
	public void setAccelerometerYData(ArrayList<Double> newAccelY){
		accelY = newAccelY;
	}
	public void setAccelerometerZData(ArrayList<Double> newAccelZ){
		accelZ = newAccelZ;
	}
	public void setGyroscopeXData(ArrayList<Double> newGyroX){
		gyroX = newGyroX;
	}
	public void setGyroscopeYData(ArrayList<Double> newGyroY){
		gyroY = newGyroY;
	}
	public void setGyroscopeZData(ArrayList<Double> newGyroZ){
		gyroZ = newGyroZ;
	}
	public void setMagnetometerXData(ArrayList<Double> newMagnetX){
		magnetX = newMagnetX;
	}
	public void setMagnetometerYData(ArrayList<Double> newMagnetY){
		magnetY = newMagnetY;
	}
	public void setMagnetometerZData(ArrayList<Double> newMagnetZ){
		magnetZ = newMagnetZ;
	}
	public void setRollData(ArrayList<Double> newRoll){
		roll = newRoll;
	}
	public void setPitchData(ArrayList<Double> newPitch){
		pitch = newPitch;
	}
	public void setYawData(ArrayList<Double> newYaw){
		yaw = newYaw;
	}
	public void setAccelRate(Double newAccelSR){
		accelSR = newAccelSR;
	}
	public void setGyroRate(Double newGyroSR){
		gyroSR = newGyroSR;
	}
	public void setMagnetRate(Double newMagnetSR){
		magnetSR = newMagnetSR;
	}
	public void setRPYRate(Double newRPYSR){
		RPYSR = newRPYSR;
	}
	public void setAccelTime(ArrayList<Double> newAccelTime){
		accelTime = newAccelTime;
	}
	public void setGyroTime(ArrayList<Double> newGyroTime){
		gyroTime = newGyroTime;
	}
	public void setMagnetTime(ArrayList<Double> newMagnetTime){
		magnetTime = newMagnetTime;
	}
	public void setRPYTime(ArrayList<Double> newRPYTime){
		RPYTime = newRPYTime;
	}
	public ArrayList<Double> getAccelerometerXData(){
		return accelX;
	}
	public ArrayList<Double> getAccelerometerYData(){
		return accelY;
	}
	public ArrayList<Double> getAccelerometerZData(){
		return accelZ;
	}
	public ArrayList<Double> getGyroscopeXData(){
		return gyroX;
	}
	public ArrayList<Double> getGyroscopeYData(){
		return gyroY;
	}
	public ArrayList<Double> getGyroscopeZData(){
		return gyroZ;
	}
	public ArrayList<Double> getMagnetometerXData(){
		return magnetX;
	}
	public ArrayList<Double> getMagnetometerYData(){
		return magnetY;
	}
	public ArrayList<Double> getMagnetometerZData(){
		return magnetZ;
	}
	public ArrayList<Double> getRollData(){
		return roll;
	}
	public ArrayList<Double> getPitchData(){
		return pitch;
	}
	public ArrayList<Double> getYawData(){
		return yaw;
	}
	public Double getAccelRate(){
		return accelSR;
	}
	public Double getGyroRate(){
		return gyroSR;
	}
	public Double getMagnetRate(){
		return magnetSR;
	}
	public Double getRPYRate(){
		return RPYSR;
	}
	public ArrayList<Double> getAccelTime(){
		return accelTime;
	}
	public ArrayList<Double> getGyroTime(){
		return gyroTime;
	}
	public ArrayList<Double> getMagnetTime(){
		return magnetTime;
	}
	public ArrayList<Double> getRPYTime(){
		return RPYTime;
	}
}
