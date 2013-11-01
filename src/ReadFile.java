import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class ReadFile {
	//reads data in from file
		private static Data readfile(String FileName) throws IOException{
			ArrayList<Double> accel = new ArrayList<Double>();
			ArrayList<Double> gyro = new ArrayList<Double>();
			ArrayList<Double> magnet = new ArrayList<Double>();
			ArrayList<String> activities = new ArrayList<String>();
			double x = 0,y = 0,z = 0,time = 0,lasttime = 0;
			String activity = new String();
			String lastactivity = new String();
			File file = new File(FileName);
			FileReader fr = new FileReader(file.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);	
			int sec = 0;
			data.add(new ArrayList<Double>());
			//reads first line of file
			String line = br.readLine();
			while(line!=null){
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
}
