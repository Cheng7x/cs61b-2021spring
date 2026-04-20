public class exercise3 {
    public static int forMax(int[] m) {
        int max = -1;
        for (int i : m) {
            if (i > max)
                max = i;
        }
        return max;
    }

    public static void main(String[] args) {
        int[] numbers = new int[] { 9, 2, 15, 2, 22, 10, 6};
        int max = forMax(numbers);
        System.out.print(max);
    }
}