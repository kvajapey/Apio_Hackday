import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ReadFile {
	//reads data in from file
		public static Data readfile(String FileName) throws IOException{
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
			Double accelSR = new Double(32);
			//gyroscope sampling rate
			Double gyroSR = new Double(32);
			//magnetometer sampling rate
			Double magnetSR = new Double(32);
			//roll pitch and yaw sampling rate
			Double RPYSR = new Double(32);
			File file = new File(FileName);
			FileReader fr = new FileReader(file.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);	
			//reads first line of file
			String line = br.readLine();
			Double startTime = new Double(0);
			//read in the lines of the file			
			while(line!=null){
				//split string into each element
				String lineElems[] = line.split("\t");
				if(lineElems[1].equals("Event")){
					if(lineElems[2].equals("Test Begin")){
						//set start time
						startTime = getTime(lineElems[0]);
					}
					else if(lineElems[2].equals("Test End")){
						break;
					}
				}
				else if(lineElems[1].equals("Config")){
					//get sample rates
					RPYSR = Double.valueOf(lineElems[4]);
					magnetSR = Double.valueOf(lineElems[4]);
					//magnetSR = Double.valueOf(lineElems[6]);
					accelSR = Double.valueOf(lineElems[10]);
					gyroSR = Double.valueOf(lineElems[12]);
				}
				/*else if(lineElems[1].equals("CMDevMotMag")){
					magnetX.add(Double.valueOf(lineElems[4]));
					magnetY.add(Double.valueOf(lineElems[5]));
					magnetZ.add(Double.valueOf(lineElems[6]));
					magnetTime.add(getTime(lineElems[0]));
				}*/
				/*else if(lineElems[1].equals("CMGyro")){
					gyroX.add(Double.valueOf(lineElems[4]));
					gyroY.add(Double.valueOf(lineElems[5]));
					gyroZ.add(Double.valueOf(lineElems[6]));
					gyroTime.add(getTime(lineElems[0]));
				}*/
				else if(lineElems[1].equals("CMDevMot")){
					roll.add(Double.valueOf(lineElems[17]));
					pitch.add(Double.valueOf(lineElems[18]));
					yaw.add(Double.valueOf(lineElems[19]));
					RPYTime.add(getTime(lineElems[0]) - startTime);
					gyroX.add(Double.valueOf(lineElems[20]));
					gyroY.add(Double.valueOf(lineElems[21]));
					gyroZ.add(Double.valueOf(lineElems[22]));
					gyroTime.add(getTime(lineElems[0]) - startTime);
					accelX.add(Double.valueOf(lineElems[26]));
					accelY.add(Double.valueOf(lineElems[27]));
					accelZ.add(Double.valueOf(lineElems[28]));
					accelTime.add(getTime(lineElems[0]) - startTime);
					accelX.add(Double.valueOf(lineElems[29]));
					accelY.add(Double.valueOf(lineElems[30]));
					accelZ.add(Double.valueOf(lineElems[31]));
					magnetTime.add(getTime(lineElems[0]) - startTime);
				}
				//read next line
				line = br.readLine();
			}
			br.close();
			//create object containing activity array and data arrays
			Data data = new Data(accelX, accelY, accelZ,
					gyroX, gyroY, gyroZ,
					magnetX, magnetY, magnetZ,
					roll, pitch, yaw,
					accelSR, gyroSR, magnetSR, RPYSR,
					accelTime, gyroTime, magnetTime, 
					RPYTime);
			return data;
		}
		//return double value of timestamp in string format
		private static Double getTime(String time){
			return Double.valueOf(time.substring(8,9))*3600 + 		//Hours
				   Double.valueOf(time.substring(10,11))*60 + 		//Minutes
				   Double.valueOf(time.substring(12,13)) +	  		//Seconds
				   Double.valueOf(time.substring(14,16))*0.001; 	//milliseconds
		}
}
