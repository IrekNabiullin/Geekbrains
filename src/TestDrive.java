
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class TestDrive {
    public static void main(String[] args) {
		System.out.println (getInputFromScanner());
		
	}

    private static String getInputFromScanner() {
        System.out.println("Режим работы с базой данных:");
               System.out.println("1. Чтобы узнать цену товара введите начиная со слеша слово \"/цена\" \n" +
                "и наименование товара,например: /цена товар 500");
        System.out.println("2. Чтобы сменить цену на товар введите начиная со слеша словосочетание \"/сменить цену\" \n" +
                "и через пробел наименование товара и новую цену,например: /сменить цену товар500 1000");
        System.out.println("3. Чтобы вывести товары в пределах ценового диапазона ведите начиная со слеша словосочетание \"/товары по цене\" \n" +
                " и через пробел нижнюю и верхнюю границу диапазона цены (включительно),например: /товары по цене 500 1000");
		Scanner sc = new Scanner(System.in);
        String userRequest = sc.next();
        try {
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userRequest;
    }
}