// Задание 2

public class Array2 {
    public boolean task2 (int[] inputArr) {
        boolean one = false;
        boolean four = false;
        for (int a : inputArr) {
            if (a != 1 && a !=4)
                return false;
            if (a == 1)
                one = true;
            if (a == 4)
                four = true;
        }
        return (one && four);
    }
}

