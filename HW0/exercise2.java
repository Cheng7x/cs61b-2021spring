public class exercise2 {
    public static int max(int[] m) {
        int max = -1;
        for (int i : m) {
            if (i > max)
                max = i;
        }
        return max;
    }

    public static void main(String[] args) {
        int[] numbers = new int[] { 9, 2, 15, 2, 22, 10, 6};
        int max = max(numbers);
        System.out.print(max);
    }
}