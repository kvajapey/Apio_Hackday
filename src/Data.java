import java.util.ArrayList;
	
	//a class that contains an array for the arrays of second of data
	//and an array for the actvity of each second
public class Data {
	//an array for the accelerometer samples
	ArrayList<Double> accelX = new ArrayList<Double>();
	ArrayList<Double> accelY = new ArrayList<Double>();
	ArrayList<Double> accelZ = new ArrayList<Double>();
	//an array for the gyroscope samples
	ArrayList<Double> gyroX = new ArrayList<Double>();
	ArrayList<Double> gyroY = new ArrayList<Double>();
	ArrayList<Double> gyroZ = new ArrayList<Double>();
	//an array for the magnetometer samples
	ArrayList<Double> magnetX = new ArrayList<Double>();
	ArrayList<Double> magnetY = new ArrayList<Double>();
	ArrayList<Double> magnetZ = new ArrayList<Double>();
	//an array for the actvity of each second
	ArrayList<String> activity = new ArrayList<String>();
	public Data(ArrayList<Double> newAccelX, ArrayList<Double> newAccelY, ArrayList<Double> newAccelZ,
				ArrayList<Double> newGyroX, ArrayList<Double> newGyroY, ArrayList<Double> newGyroZ,
				ArrayList<Double> newMagnetX, ArrayList<Double> newMagnetY, ArrayList<Double> newMagnetZ){
		accelX = newAccelX;	
		accelY = newAccelY;
		accelZ = newAccelZ;
		gyroX = newGyroX;
		gyroY = newGyroY;
		gyroZ = newGyroZ;
		magnetX = newMagnetX;
		magnetY = newMagnetY;
		magnetZ = newMagnetZ;
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
	public void setGyroscopeData(ArrayList<Double> newGyro){
		gyro = newGyro;
	}
	public void setMagnetometerData(ArrayList<Double> newMagnet){
		magnet = newMagnet;
	}
	public ArrayList<Double> getAccelerometerData(){
		return accel;
	}
	public ArrayList<Double> getGyroscopeData(){
		return gyro;
	}
	public ArrayList<Double> getMagnetometerData(){
		return magnet;
	}
}
