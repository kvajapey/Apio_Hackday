import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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


    public static void main(String args[]) throws IOException {

        /*
        * 0 - X, 1 - Y, 2 - Z
        * 0 - roll, 1 - pitch, 2 - yaw
        */

        //Arraylists to store mean, variance, FFT
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

        ArrayList<Double> inAccelList[] = new ArrayList[numDirections];
        ArrayList<Double> inGyroList[] = new ArrayList[numDirections];
        ArrayList<Double> inMagnetList[] = new ArrayList[numDirections];
        ArrayList<Double> inRotateList[] = new ArrayList[numDirections];

        ArrayList<Double> currAccelWindow = new ArrayList<Double>();
        ArrayList<Double> currGyroWindow = new ArrayList<Double>();
        ArrayList<Double> currMagnetWindow = new ArrayList<Double>();
        ArrayList<Double> currRotateWindow = new ArrayList<Double>();

        //Data rawData = new Data();

        int accelLength = inAccelList[0].size();
        int gyroLength = inGyroList[0].size();
        int magentLength = inMagnetList[0].size();
        int rotateLength = inRotateList[0].size();


        int i, j, k, n;
        //need to get the means, FFT, and variances for each type of data
        for(i = 0; i < numDirections; i++){

            for(j = 0; j < accelLength; j++){

                for(k = 0; k < FPS; k++){
                    currAccelWindow.add(k, inAccelList[i].get(j+k));
                }
                meanAccelList[i].add(j, calcMean(currAccelWindow));
                varAccelList[i].add(j, calcVariance(currAccelWindow, meanAccelList[i].get(j)));
                for(n = 0; n < maxFreq; n++){
                    fourierAccelList[i][n].add(j, goertzel(currAccelWindow, n+1, currAccelWindow.size()));
                }

            }

            for(j = 0; j < gyroLength; j++){

                for(k = 0; k < FPS; k++){
                    currGyroWindow.add(k, inGyroList[i].get(j+k));
                }
                meanGyroList[i].add(j, calcMean(currGyroWindow));
                varGyroList[i].add(j, calcVariance(currGyroWindow, meanGyroList[i].get(j)));
                for(n = 0; n < maxFreq; n++){
                    fourierGyroList[i][n].add(j, goertzel(currGyroWindow, n+1, currGyroWindow.size()));
                }

            }

            for(j = 0; j < magentLength; j++){

                for(k = 0; k < FPS; k++){
                    currMagnetWindow.add(k, inMagnetList[i].get(j+k));
                }
                meanMagnetList[i].add(j, calcMean(currMagnetWindow));
                varMagnetList[i].add(j, calcVariance(currMagnetWindow, meanMagnetList[i].get(j)));
                for(n = 0; n < maxFreq; n++){
                    fourierMagnetList[i][n].add(j, goertzel(currMagnetWindow, n+1, currMagnetWindow.size()));
                }

            }

            for(j = 0; j < rotateLength; j++){

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
