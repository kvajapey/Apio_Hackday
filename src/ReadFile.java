import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


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
			Double accelSR;
			//gyroscope sampling rate
			Double gyroSR;
			//magnetometer sampling rate
			Double magnetSR;
			//roll pitch and yaw sampling rate
			Double RPYSR;
			double x = 0,y = 0,z = 0,time = 0,lasttime = 0;
			File file = new File(FileName);
			FileReader fr = new FileReader(file.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);	
			int sec = 0;
			//reads first line of file
			String line = br.readLine();
			//read in the lines of the file			
			while(line!=null){
				//split string into each element
				String lineElems[] = line.split("\t");
				if(lineElems[1].equals("Event")){
					if(lineElems[2].equals("Test Begin")){
						//set start time
						Double startTime = getTime(lineElems[0]);
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
					RPYTime.add(getTime(lineElems[0]));
					accelX.add(Double.valueOf(lineElems[26]));
					accelY.add(Double.valueOf(lineElems[27]));
					accelZ.add(Double.valueOf(lineElems[28]));
					accelTime.add(getTime(lineElems[0]));
					accelX.add(Double.valueOf(lineElems[29]));
					accelY.add(Double.valueOf(lineElems[30]));
					accelZ.add(Double.valueOf(lineElems[31]));
					magnetTime.add(getTime(lineElems[0]));
				}
				
				
				//scans line of file
				Scanner sc = new Scanner(line);
				if(sc.hasNext())
					//gets time
					time = Double.valueOf(sc.next().replace(',',' ').trim());			
				if(sc.hasNext())
					//gets x accel value
					x = Double.valueOf(sc.next().replace(',',' ').trim());
				if(sc.hasNext())
					//gets y accel value
					y = Double.valueOf(sc.next().replace(',',' ').trim());
				if(sc.hasNext())
					//gets z accel value
					z = Double.valueOf(sc.next().replace(',',' ').trim());		
				if(sc.hasNext())
					//gets the activity
					activity = sc.next();
				//if a second of data has been collected or the activity has changed
				if(((time-lasttime)>=1000 || !lastactivity.equals(activity)) && lasttime!=0){
					//create new array for new second
					data.add(new ArrayList<Double>());
					//increment counter for what second is being recorded
					sec++;
					//record what activity the second is for
					activities.add(lastactivity);
					lasttime = time;
				}
				//for first time through to initialize lasttime
				else if(lasttime==0){
					lasttime = time;
				}
				//add mag of data to second array
				data.get(sec).add(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)));
				lastactivity = activity;
				//read next line
				line = br.readLine();
				sc.close();
			}
			//record final activity
			activities.add(lastactivity);
			br.close();
			//create object containing activity array and data arrays
			DataandActivity DandA = new DataandActivity(data, activities);
			return DandA;
		}
		//return double value of timestamp in string format
		private static Double getTime(String time){
			return Double.valueOf(time.substring(8,9))*3600 + 		//Hours
				   Double.valueOf(time.substring(10,11))*60 + 		//Minutes
				   Double.valueOf(time.substring(12,13)) +	  		//Seconds
				   Double.valueOf(time.substring(14,16))*0.001; 	//milliseconds
		}
}
