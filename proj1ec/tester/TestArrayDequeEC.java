package tester;

import static org.junit.Assert.*;
import org.junit.Test;
import student.StudentArrayDeque;
import edu.princeton.cs.algs4.StdRandom;

public class TestArrayDequeEC {
    @Test
    public void isTheirResultEqual() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> good = new ArrayDequeSolution<>();

        StringBuilder message = new StringBuilder();

        int N = 1000;

        for (int i = 0; i < N; i += 1) {
            int random = StdRandom.uniform(0, 4);
            int value = StdRandom.uniform(0, 100);

            switch (random) {
                case 0:
                    sad.addFirst(value);
                    good.addFirst(value);
                    message.append("addFirst(").append(value).append(")\n");
                    break;

                case 1:
                    sad.addLast(value);
                    good.addLast(value);
                    message.append("addLast(").append(value).append(")\n");
                    break;

                case 2:
                    if (!good.isEmpty()) {
                        Integer goodVal = good.removeFirst();
                        Integer sadVal = sad.removeFirst();

                        String errorMessage = message.toString() + "removeFirst()\n";
                        assertEquals(errorMessage, goodVal, sadVal);

                        message.append("removeFirst()\n");
                    }
                    break;

                case 3:
                    if (!good.isEmpty()) {
                        Integer goodVal = good.removeLast();
                        Integer sadVal = sad.removeLast();

                        String errorMessage = message.toString() + "removeLast()\n";
                        assertEquals(errorMessage, goodVal, sadVal);

                        message.append("removeLast()\n");
                    }
                    break;
            }
        }
    }
}