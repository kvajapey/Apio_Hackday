import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kvajapey
 * Date: 11/1/13
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class FeatureCalculations {


    //hello
    //hello

    //i am testing this right now

    public static void main(String args[]){


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

    private static double goertzel(ArrayList<Double> accData, double freq, double sr)
    {
        double s_prev = 0;
        double s_prev2 = 0;
        double coeff = 2 * Math.cos((2 * Math.PI * freq) / sr);
        double s;
        for (int i = 0; i < accData.size(); i++) {
            double sample = accData.get(i);
            s = sample + coeff * s_prev - s_prev2;
            s_prev2 = s_prev;
            s_prev = s;
        }
        double power = s_prev2 * s_prev2 + s_prev * s_prev - coeff * s_prev2 * s_prev;
        return power;
    }

}
