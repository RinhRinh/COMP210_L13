package comp210.ic08;

import bridges.base.LineChart;
import bridges.connect.Bridges;
import bridges.validation.RateLimitException;

import java.io.IOException;

/**
 * Builds one chart: the dollar cost of one training run against the number of
 * training samples, for two linear algorithms on a cheap GPU and one quadratic
 * algorithm on the fastest GPU. Then finds where the lines cross.
 */
public class CostChart {

    /** The chart runs from 1 sample to 100 million. */
    static final double MAX_N = 1e8;

    public static void main(String[] args) throws IOException, RateLimitException {
        // TODO 2: build the three curves. Each one is new Curve(label, workload, gpu),
        // using a workload from Workloads and a GPU from Gpu. The label is what
        // the chart's legend shows, so say what it is and its Big-O.
        //   sgd1M:  1M-param SGD on the T4
        //   sgd10M: 10M-param SGD on the T4
        //   kernel: the kernel method on the H100
        Curve sgd1M = null;
        Curve sgd10M = null;
        Curve kernel = null;

        System.out.println("The kernel method costs more than 1M-param SGD from n = "
                + crossover(sgd1M, kernel, (long) MAX_N));
        System.out.println("The kernel method costs more than 10M-param SGD from n = "
                + crossover(sgd10M, kernel, (long) MAX_N));

        LineChart chart = costChart(new Curve[] { sgd1M, sgd10M, kernel });

        BridgesConfig config = BridgesConfig.load();
        System.out.println("Loaded credentials: " + config.describe());
        Bridges bridges = new Bridges(config.getAssignmentNumber(), config.getUsername(), config.getApiKey());
        bridges.setTitle("IC08: " + config.getUsername() + "'s GPU bill");
        bridges.setDataStructure(chart);
        bridges.visualize();
    }

    /**
     * Returns the smallest n, from 1 up to maxN, where expensive costs more than
     * cheap for one training run. Returns -1 if that never happens by maxN.
     */
    public static long crossover(Curve cheap, Curve expensive, long maxN) {
        // TODO 3: try n = 1, 2, 3, ... and return the first n where
        // expensive.costDollars(n) is greater than cheap.costDollars(n).
        return -1;
    }

    // ------------------------------------------------------------------
    // Provided. Draws every curve on one chart with log scales on both axes.
    // ------------------------------------------------------------------

    static LineChart costChart(Curve[] curves) {
        LineChart chart = new LineChart();
        chart.setTitle("Cost of one training run vs number of samples");
        chart.setXLabel("n (training samples)");
        chart.setYLabel("dollars per run");
        // On log-log axes, c * n^k is a straight line with slope k.
        chart.toggleLogarithmicX(true);
        chart.toggleLogarithmicY(true);

        double[] ns = sampleLog(1, MAX_N, 81);
        for (Curve c : curves) {
            if (c == null) {
                throw new IllegalStateException("A curve is still null. Finish TODO 2 in CostChart.main.");
            }
            double[] dollars = new double[ns.length];
            for (int i = 0; i < ns.length; i++) {
                dollars[i] = c.costDollars(ns[i]);
            }
            chart.setDataSeries(c.getLabel(), ns.clone(), dollars);
        }
        return chart;
    }

    /** k values of n spread evenly on a log scale from lo to hi. */
    static double[] sampleLog(double lo, double hi, int k) {
        double[] xs = new double[k];
        double a = Math.log10(lo);
        double b = Math.log10(hi);
        for (int i = 0; i < k; i++) {
            xs[i] = Math.pow(10, a + (b - a) * i / (k - 1));
        }
        return xs;
    }
}
