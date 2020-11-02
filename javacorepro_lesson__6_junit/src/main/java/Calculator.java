public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int sub(int a, int b) {
        return a - b;
    }

    public int mul(int a, int b) {
        return a * b;
    }

    public int div(int a, int b) {
        return a / b;
    }

    public int pow(int a, int b) {
        int result = 1;
        for (int i = 0; i < b; i++) {
            result = result * a;
        }
        return result;
    }
}
