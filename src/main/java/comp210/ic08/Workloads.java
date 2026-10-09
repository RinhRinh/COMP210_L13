package comp210.ic08;

import java.util.function.DoubleUnaryOperator;

/**
 * How much work each training algorithm does, as a function of n, the number of
 * training samples. Each one is a real ML cost; the constants come from the
 * README.
 *
 * Nothing here needs changing. Read the Big-O off each formula: that is the
 * thing the chart is about to show you.
 */
public final class Workloads {

    private Workloads() { }

    /** O(n): one pass of SGD over a 1-million-parameter model, about 6 * P * n FLOPs. */
    public static final DoubleUnaryOperator SGD_1M = n -> 6e6 * n;

    /** O(n): the same pass over a 10-million-parameter model, 10 times the constant. */
    public static final DoubleUnaryOperator SGD_10M = n -> 6e7 * n;

    /** O(n^2): a kernel method compares every pair of samples, about 2,000 FLOPs per pair. */
    public static final DoubleUnaryOperator KERNEL = n -> 2e3 * n * n;
}
