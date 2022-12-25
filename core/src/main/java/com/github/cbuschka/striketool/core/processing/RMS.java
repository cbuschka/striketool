package com.github.cbuschka.striketool.core.processing;

class RMS {
    static double findAbsRMSOverChannels(double[][] valueValues) {

        double squareSum = 0;
        int n = 0;
        for (double[] values : valueValues) {
            for (double value : values) {
                squareSum += Math.pow(value, 2);
                n++;
            }
        }
        double mean = (squareSum / (double) n);

        return Math.sqrt(mean);
    }
}
