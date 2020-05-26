/*
 * Напишите публичный метод priceCalculation, который считает стоимость товара.
 * В метод передается цена товара (может быть дробной) и количество товара, метод должен возвращать итоговую цену покупки.
 *
 * Пример ввода: 8.50, 2
 * Пример вывода: 17.0
 */

public class Main {
    public static void main(String[] args) {
        System.out.println(new Main().priceCalculation(8.50, 2));
    }

    public double priceCalculation(double price, int count) {
        return price * count;
    }

}
