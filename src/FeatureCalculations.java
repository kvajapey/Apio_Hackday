import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: kvajapey
 * Date: 11/1/13
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class FeatureCalculations {

    //frames rate for collected data
    public static final int FPS = 32;
    //number of directions for each sensor (X,Y,Z)
    public static final int numDirections = 3;
    //maximum frequency for FFT calculation
    public static final int maxFreq = 16;
    //file name to pull raw data from


    public static void main(String args[]) throws IOException {

        /*
        * 0 - X, 1 - Y, 2 - Z
        * 0 - roll, 1 - pitch, 2 - yaw
        */

        Scanner scan;
        scan = new Scanner(System.in);

        String fileName;

        System.out.print("Please print the filename of input data: ");
        fileName = scan.toString();


        //Arraylists to store mean, variance, FFT for each type of sensor
        ArrayList<Double> meanAccelList[] = new ArrayList[numDirections];
        ArrayList<Double> meanGyroList[] = new ArrayList[numDirections];
        ArrayList<Double> meanMagnetList[] = new ArrayList[numDirections];
        ArrayList<Double> meanRotateList[] = new ArrayList[numDirections];

        ArrayList<Double> fourierAccelList[][] = new ArrayList[numDirections][maxFreq];
        ArrayList<Double> fourierGyroList[][] = new ArrayList[numDirections][maxFreq];
        ArrayList<Double> fourierMagnetList[][] = new ArrayList[numDirections][maxFreq];
        ArrayList<Double> fourierRotateList[][] = new ArrayList[numDirections][maxFreq];

        ArrayList<Double> varAccelList[] = new ArrayList[numDirections];
        ArrayList<Double> varGyroList[] = new ArrayList[numDirections];
        ArrayList<Double> varMagnetList[] = new ArrayList[numDirections];
        ArrayList<Double> varRotateList[] = new ArrayList[numDirections];

        //Arraylists to get the input raw data (+1 for time arraylist for each sensor)
        ArrayList<Double> inAccelList[] = new ArrayList[numDirections+1];
        ArrayList<Double> inGyroList[] = new ArrayList[numDirections+1];
        ArrayList<Double> inMagnetList[] = new ArrayList[numDirections+1];
        ArrayList<Double> inRotateList[] = new ArrayList[numDirections+1];

        //current window of data that is being processed
        ArrayList<Double> currAccelWindow = new ArrayList<Double>();
        ArrayList<Double> currGyroWindow = new ArrayList<Double>();
        ArrayList<Double> currMagnetWindow = new ArrayList<Double>();
        ArrayList<Double> currRotateWindow = new ArrayList<Double>();

        ReadFile reader = new ReadFile();
        Data rawData = (reader.readfile(fileName));

        //set all the raw data from data file
        inAccelList[0] = rawData.getAccelerometerXData();
        inAccelList[1] = rawData.getAccelerometerYData();
        inAccelList[2] = rawData.getAccelerometerZData();
        inAccelList[3] = rawData.getAccelTime();


        inGyroList[0] = rawData.getGyroscopeXData();
        inGyroList[1] = rawData.getGyroscopeYData();
        inGyroList[2] = rawData.getGyroscopeZData();
        inGyroList[3] = rawData.getGyroTime();


        inMagnetList[0] = rawData.getMagnetometerXData();
        inMagnetList[1] = rawData.getMagnetometerYData();
        inMagnetList[2] = rawData.getMagnetometerZData();
        inMagnetList[3] = rawData.getMagnetTime();


        inRotateList[0] = rawData.getRollData();
        inRotateList[1] = rawData.getPitchData();
        inRotateList[2] = rawData.getYawData();
        inRotateList[3] = rawData.getRPYTime();


        //length (number of entries) of each type of sensor data
        int accelLength = inAccelList[0].size();
        int gyroLength = inGyroList[0].size();
        int magentLength = inMagnetList[0].size();
        int rotateLength = inRotateList[0].size();
        int timeLength = inAccelList[3].size();


        int i, j, k, n;
        //for loop to go through x, y, z, directions for each sensor
        for(i = 0; i < numDirections; i++){

            //go through the length of the accel data
            for(j = 0; j < (accelLength - FPS); j++){

                //get 32 frames at a time to process with a step size of 1 (overlapping windows)
                for(k = 0; k < FPS; k++){
                    currAccelWindow.add(k, inAccelList[i].get(j+k));
                }
                meanAccelList[i].add(j, calcMean(currAccelWindow));
                varAccelList[i].add(j, calcVariance(currAccelWindow, meanAccelList[i].get(j)));
                //get the fourier transforms of maxFreq different frequencies
                for(n = 0; n < maxFreq; n++){
                    fourierAccelList[i][n].add(j, goertzel(currAccelWindow, n+1, currAccelWindow.size()));
                }

            }

            //go through length of gyro data
            for(j = 0; j < (gyroLength - FPS); j++){

                for(k = 0; k < FPS; k++){
                    currGyroWindow.add(k, inGyroList[i].get(j+k));
                }
                meanGyroList[i].add(j, calcMean(currGyroWindow));
                varGyroList[i].add(j, calcVariance(currGyroWindow, meanGyroList[i].get(j)));
                for(n = 0; n < maxFreq; n++){
                    fourierGyroList[i][n].add(j, goertzel(currGyroWindow, n+1, currGyroWindow.size()));
                }

            }

            for(j = 0; j < (magentLength - FPS); j++){

                for(k = 0; k < FPS; k++){
                    currMagnetWindow.add(k, inMagnetList[i].get(j+k));
                }
                meanMagnetList[i].add(j, calcMean(currMagnetWindow));
                varMagnetList[i].add(j, calcVariance(currMagnetWindow, meanMagnetList[i].get(j)));
                for(n = 0; n < maxFreq; n++){
                    fourierMagnetList[i][n].add(j, goertzel(currMagnetWindow, n+1, currMagnetWindow.size()));
                }

            }

            for(j = 0; j < (rotateLength - FPS); j++){

                for(k = 0; k < FPS; k++){
                    currRotateWindow.add(k, inRotateList[i].get(j+k));
                }
                meanRotateList[i].add(j, calcMean(currRotateWindow));
                varRotateList[i].add(j, calcVariance(currRotateWindow, meanRotateList[i].get(j)));
                for(n = 0; n < maxFreq; n++){
                    fourierRotateList[i][n].add(j, goertzel(currRotateWindow, n+1, currRotateWindow.size()));
                }
            }
        }

        FileWriter fstream = new FileWriter("classifier_in.csv");
        BufferedWriter wr = new BufferedWriter(fstream);

        String output;

        //print to CSV -> timestamp, meanaccelx, meangyrox, meanmagnetx, meanrotatex, varaccelx, vargyrox, varmagnetx, varrotatex,
        // fourieracclx 1-16, fouriergyrox 1-16, fouriermagnetx 1-16, fourierrotatex 1-16,
        //meanaccely, meangyroy, meanmagnety, meanrotatey, varaccely, vargyroy, varmagnety, varrotatey,
        // fourieraccly 1-16, fouriergyroy 1-16, fouriermagnety 1-16, fourierrotatey 1-16,
        //meanaccelz, meangyroz, meanmagnetz, meanrotatez, varaccelz, vargyroz, varmagnetz, varrotatez,
        // fourieracclz 1-16, fouriergyroz 1-16, fouriermagnetz 1-16, fourierrotatez 1-16,

        output = "TimeStamp, Mean Accel X, Mean Gyro X, Mean Magnet X, Mean Roll, Variance Accel X, Variance Gyro X," +
                "Variance Magnet X, Variance Roll,";
        for(i = 0; i < maxFreq; i++){
            output += "FFT Accel X" + i + "," + "FFT Gyro X" + i + "," + "FFT Magnet X" + i + "," + "FFT Roll" + i + ",";
        }

        output += "Mean Accel Y, Mean Gyro Y, Mean Magnet Y, Mean Pitch, Variance Accel Y, Variance Gyro Y," +
                "Variance Magnet Y, Variance Pitch,";
        for(i = 0; i < maxFreq; i++){
            output += "FFT Accel Y" + i + "," + "FFT Gyro Y" + i + "," + "FFT Magnet Y" + i + "," + "FFT Pitch" + i + ",";
        }

        output += "Mean Accel Z, Mean Gyro Z, Mean Magnet Z, Mean Yaw, Variance Accel Z, Variance Gyro Z," +
                "Variance Magnet Z, Variance Yaw,";
        for(i = 0; i < maxFreq; i++){
            output += "FFT Accel Z" + i + "," + "FFT Gyro Z" + i + "," + "FFT Magnet Z" + i + "," + "FFT Yaw" + i + ",";
        }

        output += "classification\n";

        wr.write(output);

        for(i = 0; i < timeLength; i++){
            output = "" + inAccelList[3].get(i);
            for(j = 0; j < numDirections; j++){
                output += "," + meanAccelList[j].get(i) + "," + meanGyroList[j].get(i) + "," + meanMagnetList[j].get(i) +
                        "," + meanRotateList[j].get(i) + "," + varAccelList[j].get(i) + "," + varGyroList[j].get(i) +
                        "," + varMagnetList[j].get(i) + "," + varRotateList[j].get(i) + ",";
                for(k = 0; k < maxFreq; k++){
                    output += fourierAccelList[j][k].get(i) + "," + fourierGyroList[j][k].get(i) + "," +
                            fourierMagnetList[j][k].get(i) + "," + fourierRotateList[j][k].get(i) + ",";
                }
            }
            output += "classification\n";
            wr.write(output);
        }

        wr.close();

    }

    public static double calcMean(ArrayList<Double> d){
        double total = 0;
        double mean = 0;
        int i=0;

        if(d.isEmpty()){
            return 0;
        }
        while(i<d.size()){
            total += d.get(i);
            i++;
        }
        mean = total/d.size();

        return mean;
    }

    public static double calcVariance(ArrayList<Double> ar, double mean){
        int i = 0;
        double variance = 0;
        double totdiffsq = 0;
        double[] diffsq = new double[ar.size()];
        for(i=0; i<ar.size(); i++){
            totdiffsq += Math.pow((ar.get(i)-mean), 2);
            //System.out.println(totdiffsq + "\n");
        }
        variance = totdiffsq/ar.size();
        return variance;
    }

    private static double goertzel(ArrayList<Double> Data, double freq, double sr)
    {
        double s_prev = 0;
        double s_prev2 = 0;
        double coeff = 2 * Math.cos((2 * Math.PI * freq) / sr);
        double s;
        for (int i = 0; i < Data.size(); i++) {
            double sample = Data.get(i);
            s = sample + coeff * s_prev - s_prev2;
            s_prev2 = s_prev;
            s_prev = s;
        }
        double power = s_prev2 * s_prev2 + s_prev * s_prev - coeff * s_prev2 * s_prev;
        return power;
    }

}
