# IC08: Complexity Matters (the GPU bill)

**COMP210, Fall 2026 · In-class activity, about 20 minutes**

Five years from now, you have landed your dream job as an ML engineer. Your first big model
is training, and the cloud bill that came with it makes Mimi sad. It's clear that your job ran too long
for the cloud services budget manager.  Your non-technical product manager has a plan:
rent the fastest GPU money can buy. The jerk on your team who can't say anything nice
thinks the algorithm is to blame. They both want your vote by Friday, and you will settle
it with SCIENCE....Computer Science.

## Background context

### The two GPUs

Both GPUs and their on-demand prices come from cloud rate cards (about mid-2026).

| GPU | Speed | Price |
|---|---|---|
| NVIDIA T4 | 65 TFLOPS | $0.35 per hour |
| NVIDIA H100 SXM | 990 TFLOPS | $3.50 per hour |

A TFLOP is a trillion (10^12) floating-point operations. The H100 is about 15
times faster than the T4 and costs 10 times as much per hour.

### The cost model

```
seconds = FLOPs / (TFLOPS * 10^12)
dollars = seconds / 3600 * price per hour
```

## What we give you

`Curve.java` does this math for you. You give it the GPUs and the training algorithms.

### The three training algorithms

`n` is the number of training samples. `Workloads.java` holds the formulas.

| Algorithm | Work in FLOPs | Big-O | Runs on |
|---|---|---|---|
| SGD, 1-million-parameter model | `6,000,000 * n` | O(n) | T4 |
| SGD, 10-million-parameter model | `60,000,000 * n` | O(n) | T4 |
| Kernel method (compares every pair of samples) | `2,000 * n^2` | O(n^2) | H100 |

The quadratic algorithm runs on the fast GPU, and the two linear ones run on the cheap one.

### The project

A Maven project set up the same way as A4, with BRIDGES pulled from JitPack
and your key read from `.env`.

| File | What it holds |
|---|---|
| `Curve.java` | The cost model. No edits needed. |
| `Workloads.java` | The three FLOP formulas. No edits needed. |
| `CostChart.java` | The chart code, plus TODO 2 and TODO 3 |
| `Gpu.java` | The GPU class, with TODO 1 |
| `BridgesConfig.java` | Reads `.env`, same as A4 |
| `pom.xml` | Maven build, pulls BRIDGES and dotenv-java |
| `.env.example` | Template for your credentials |

All the code lives in `src/main/java/comp210/ic08/`.

## What you need to do

### 1. Set up

1. In IntelliJ, use **File > Open** on the folder holding `pom.xml` and let the
   Maven import finish.
2. Copy `.env.example` to `.env` in the same folder and fill in the username and
   API key you used in A4. Leave `BRIDGES_ASSIGNMENT` at 8.

Leave `.env` out of anything you upload or share, since it holds your API key.

### 2. Write the code

Do the three TODOs in order. They add up to about 15 lines.

1. In `Gpu.java`, add the `tflops` and `dollarsPerHour` fields and the
   constructor, finish the two methods, and build `T4` and `H100` from the
   GPU table.
2. In `CostChart.main`, build the three curves, one `new Curve(label, workload, gpu)` each.
3. In `CostChart.crossover`, try n = 1, 2, 3, and so on, and return the first
   n where the expensive curve costs more than the cheap one.

### 3. Run it

Run `CostChart` with the green arrow next to `main`. It prints both crossover
points and pushes one chart to BRIDGES. Open the URL it prints.

The chart uses log scales on both axes. On a log-log chart, `c * n^k` is a
straight line, and its slope is the exponent k.

### 4. Take the Canvas quiz

The IC08 quiz on Canvas asks these three questions and has you upload a
screenshot of your chart.

1. At what n does the kernel method on the H100 start costing more than 1M-param
   SGD on the T4?
2. The 10M-param model's constant is 10 times bigger. At what n does the kernel
   method pass it, and how many times further out is that than your answer to
   question 1?
3. The H100 is about 15 times faster than the T4. Did it rescue the O(n^2)
   algorithm? Explain using the slopes of the lines on your chart.

The screenshot should show all three lines and the legend. Crop out anything
else on your screen.
