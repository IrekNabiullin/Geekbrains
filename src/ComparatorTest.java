import java.util.*;

public class ComparatorTest
{
    public static void main(String[] args) {
        ArrayList<String> str = new ArrayList<>(Arrays.asList("A", "BB", "AAA", "B", "CCCC", "DD"));
        System.out.println(str);
        Collections.sort(str);
        System.out.println(str);
        Collections.sort(str, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
            //объясняем компаратору какой объект больше и по какому принципу. Например длина:
            if (o1.length() > o2.length()) {
                return 1; // возвращаем положительное число, если длина о1 больше о2
                }
            if (o1.length() < o2.length()) {
                return -1; // возвращаем отрицательное число, если длина о1 меньше о2
            }
            else // иначе возвращаем 0
                return 0;
            }
        });
        System.out.println(str);
// теперь сделаем то же самое в обратную сторону, но через вычитание для получения отрицательного или положительного значения       
        
        Collections.sort(str, (o1, o2) -> {
			return o2.length() - o1.length();
			});
        System.out.println(str);
   }   
}