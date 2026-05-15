package bstmap;

public class Test {
    public static void main(String[] args) {
        BSTMap<String, Integer> map = new BSTMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("e", 5);
        map.put("s", 9);
        map.put("f", 4);
        for (String x : map.keySet()) {
            System.out.println(x);
        }
    }
}
