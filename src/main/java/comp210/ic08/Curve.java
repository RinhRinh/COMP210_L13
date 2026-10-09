package comp210.ic08;

import java.util.function.DoubleUnaryOperator;

/**
 * One line on the chart: one training algorithm running on one GPU.
 *
 * Nothing here needs changing. The whole cost model is the two methods below:
 * work divided by speed gives seconds, and seconds times the hourly rate gives
 * dollars.
 */
public class Curve {

    private final String label;
    private final DoubleUnaryOperator flops;
    private final Gpu gpu;

    /**
     * @param label what the chart's legend shows for this line
     * @param flops how many floating-point operations training takes for n samples
     * @param gpu   the GPU it runs on
     */
    public Curve(String label, DoubleUnaryOperator flops, Gpu gpu) {
        this.label = label;
        this.flops = flops;
        this.gpu = gpu;
    }

    public String getLabel() {
        return label;
    }

    /** Seconds to train on n samples: total work divided by the GPU's speed. */
    public double runtimeSeconds(double n) {
        return flops.applyAsDouble(n) / gpu.flopsPerSecond();
    }

    /** Dollars for one training run on n samples. */
    public double costDollars(double n) {
        return runtimeSeconds(n) / 3600.0 * gpu.getDollarsPerHour();
    }
}
