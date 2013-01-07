package com.tagtraum.perf.gcviewer.math;

import java.io.Serializable;

/**
 *
 * @author <a href="mailto:hs@tagtraum.com">Hendrik Schreiber</a>
 * Date: May 20, 2005
 * Time: 5:08:33 PM
 *
 */
public class DoubleData implements Serializable {

    private int n;
    private double sum;
    private double sumSquares;
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;

    public void add(double x) {
        sum += x;
        sumSquares += x*x;
        n++;
        min = Math.min(min, x);
        max = Math.max(max, x);
    }

    public void add(double x, int weight) {
        sum += x * weight;
        n += weight;
        sumSquares += x*x*weight;
        min = Math.min(min, x);
        max = Math.max(max, x);
    }

    public int getN() {
        return n;
    }

    public double getSum() {
        return sum;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double average() {
        if (n == 0) throw new IllegalStateException("n == 0");
        return sum / n;
    }

    public double standardDeviation() {
        if (n == 0) throw new IllegalStateException("n == 0");
        if (n==1) return 0;
        return Math.sqrt(variance());
    }

    public double variance() {
        if (n == 0) throw new IllegalStateException("n == 0");
        if (n==1) return 0;
        return (sumSquares - sum*sum/n)/(n-1);
    }

    public void reset() {
        sum = 0;
        sumSquares = 0;
        n = 0;
    }

    public static double average(double[] n) {
        return Sum.sum(n) / n.length;
    }

    public static double weightedAverage(double[] n, int[] weight) {
        double sum = 0;
        int m = 0;
        for (int i=0; i<n.length; i++) {
            sum += n[i]*weight[i];
            m += weight[i];
        }
        return sum / m;
    }
}
