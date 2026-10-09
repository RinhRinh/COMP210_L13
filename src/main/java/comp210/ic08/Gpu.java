package comp210.ic08;

/**
 * A cloud GPU you can rent, described by the two numbers the cost model needs:
 * how many floating-point operations it does per second, and what it costs per
 * hour. Throughput is the published dense fp16 / bf16 tensor-core peak; price is
 * the on-demand rate from cloud rate cards, about mid-2026.
 */
public class Gpu {

    // TODO 1: add two private final double fields, tflops and dollarsPerHour,
    // and a public constructor Gpu(double tflops, double dollarsPerHour) that
    // stores them.

    // TODO 1: build the two GPUs from the table in the README.
    public static final Gpu T4 = null;     // NVIDIA T4, the cheap one
    public static final Gpu H100 = null;   // NVIDIA H100 SXM, the fastest money rents

    public double getDollarsPerHour() {
        return 0; // TODO 1: return the field
    }

    /** One TFLOP is 10^12 floating-point operations. Assumes peak speed on one GPU. */
    public double flopsPerSecond() {
        return 0; // TODO 1: tflops times 10^12
    }
}
