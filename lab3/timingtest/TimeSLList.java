package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        AList<Integer> Ns = new AList<Integer>();
        AList<Double> times = new AList<Double>();
        AList<Integer> opCounts = new AList<Integer>();

        int[] counts = new int[]{1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        for (int count : counts) {
            timeGetLast(count, 10000, Ns, times, opCounts);
        }
        printTimingTable(Ns, times, opCounts);
    }

    public static void timeGetLast(int N, int M, AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        // TODO: YOUR CODE HERE
        SLList<Integer> empty = new SLList<Integer>();
        for (int i = 0; i < N; i += 1) {
            empty.addLast(i);
        }
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < M; i += 1) {
            int last = empty.getLast();
        }
        double timeInSeconds = sw.elapsedTime();
        times.addLast(timeInSeconds);
        Ns.addLast(N);
        opCounts.addLast(M);
    }

}
