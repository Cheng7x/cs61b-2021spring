public class exercise4 {
    public static void windowPosSum(int[] a, int n) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] < 0)
                continue;
            int temp = 0;
            for (int j = 0; j <= n; j++) {
                if (i + j > a.length - 1)
                    break;
                temp += a[i + j];
            }
            a[i] = temp;
        }
    }

    public static void main(String[] args) {
        int[] a = { 1, 2, -3, 4, 5, 4 };
        // int[] a = { 1, -1, -1, 10, 5, -1 };
        int n = 3;
        windowPosSum(a, n);

        System.out.println(java.util.Arrays.toString(a));
    }
}
