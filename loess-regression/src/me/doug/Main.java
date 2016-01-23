package me.doug;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.Math;
import java.io.InputStreamReader;


public class Main {

    static double squareFootage[] = {1000.0, 1500.0, 1805.0, 2100.0, 3000.0, 4000.0, 6000.0};
    static double price[] =         {3500.0, 3900.0, 4000.0, 5000.0, 5500.0, 10000.0, 11000.0};


    static double thetaConst = 0.0;
    static double thetaSquareFootage = 0.0;

    public static double priceHypothesis(double squareFootage) {
        return thetaConst * 1 + thetaSquareFootage * squareFootage;
    }

    // How much weight should dataX have relative to queryPointX?
    //
    public static double dataPointWeight(double queryPointX, double dataX) {
        double tau = 100.0;
        double weight = Math.exp(- (dataX - queryPointX) /  (2 * tau * tau));

        System.out.printf("Query Point %f This Data Point %f Weight %f", queryPointX, dataX, weight);
        return weight;
    }

    // Update theta in one pass through training set at
    // a given learning rate
    public static double[] locallyWeightedTotalError(double queryPointX) {
        // Error with respect to square footage
        double sumConstError = 0;
        double errorsByTheta[] = {0,0};
        double sumSqFootageError = 0;
        for (int i = 0; i < squareFootage.length; i++) {
            double hypothesis = priceHypothesis(squareFootage[i]);
            double actual = price[i];

            sumConstError += (actual - hypothesis);
            double weight = dataPointWeight(queryPointX, squareFootage[i]); // how much weight to give this data point for the current query point
            double errWithTerm = weight * (actual - hypothesis) * squareFootage[i];
            sumSqFootageError += errWithTerm;
            System.out.printf("Error %f, w/term %f, sumErr w/term %f\n", (actual - hypothesis), errWithTerm, sumSqFootageError);
        }
        errorsByTheta[0] = sumConstError;
        errorsByTheta[1] = sumSqFootageError;
        return errorsByTheta;
    }

    public static double prediction(double squarFootage) {
        double learningRate = 0.0000000001;
        for (int i = 0; i < 10000; i++) {
            double errorsByDim[] = locallyWeightedTotalError(squarFootage);
            thetaConst = thetaConst + learningRate * (errorsByDim[0]);
            thetaSquareFootage = thetaSquareFootage + learningRate * (errorsByDim[1]);
        }
        return priceHypothesis(squarFootage);
    }

    public static void main(String[] args) {
        BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));

        String input;

        try {
            while((input=br.readLine())!=null){
                // reset the system to be recomputed from the new query point,
                // which is the whole point of non-parametric learning algos
                thetaConst = thetaSquareFootage = 0.0;
                double sqFootage = Double.parseDouble(input);
                System.out.printf("%f\n", prediction(sqFootage));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // write your code here
    }
}
