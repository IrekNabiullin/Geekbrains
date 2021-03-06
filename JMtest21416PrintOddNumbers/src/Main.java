public class Main {
    public static void main(String[] args) {
        int[] arr = {3, 5, 20, 8, 7, 3, 100};
        printOddNumbers(arr);
    }

    public static void printOddNumbers(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % 2 != 0) {
                sb.append(arr[i]);
                sb.append(",");
            }
            if (i == arr.length - 1) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        System.out.println(sb);
    }

}
