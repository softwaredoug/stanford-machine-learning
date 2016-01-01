package me.doug;

public class Main {

    static double squareFootage[] = {2100.0, 1000.0, 1500.0, 4000.0, 1805.0};
    static double price[] = {5000.0, 3500.0, 3900.0, 10000.0, 4000.0};

    static double theta1 = 0.0;
    static double theta2 = 0.0;

    public static double priceHypothesis(double squareFootage) {
        return theta1 * 1 + theta2 * squareFootage;
    }

    public static void main(String[] args) {

        double learningRate = 0.00000001;

        for (int j = 0; j < 500; j++) {

            double sumError = 0;
            double sumErrorWithTerm = 0;

            // sum error for all training data
            for (int i = 0; i < squareFootage.length; i++) {
                double hypothesis = priceHypothesis(squareFootage[i]);
                double actual = price[i];

                sumError += (actual - hypothesis);
                double errWithTerm = (actual - hypothesis) * squareFootage[i];
                sumErrorWithTerm += errWithTerm;
                System.out.printf("Error %f, w/term %f, sumErr w/term %f\n", (actual - hypothesis), errWithTerm, sumErrorWithTerm);

                //double adjustedTheta1 = theta1 - (learningRate * (actual - hypothesis));
                //theta1 = adjustedTheta1;
            }
            System.out.printf("ITER %d Total Error %f w/ term %f", j, sumError, sumErrorWithTerm);
            theta2 = theta2 + learningRate * (sumErrorWithTerm);

        }

        System.out.printf("1000 sq ft home %f", priceHypothesis(1000.0));




	// write your code here
    }
}