public class exercise1b {
    public static void DrawTriangle(int nums) {
        for (int i = 0; i < nums; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.print("*");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        int N = 10;
        exercise1b.DrawTriangle(N);
    }
}
