import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SomeCalculations {
    public static void main(String[] args) {

    //напишите тут ваш код
    int lng = 20; //длина списка по условию

    // инициализируем все 4 списка
    ArrayList<Integer> arrayAll = new ArrayList<>();
    ArrayList<Integer> arrayThree = new ArrayList<>();
    //        List<Integer> arrayTwo = new ArrayList<>();
    ArrayList<Integer> arrayOther = new ArrayList<>();

//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

// формируем первый список

    for(int i=0; i<lng; i++){
//          arrayAll.add(reader.readLine());
        arrayAll.add(i);
    }
        System.out.println(arrayAll);

    // используем Srream API и фильтруем первый список

    Stream<Integer> stream = arrayAll.stream();
    stream.filter(new Predicate<Integer>(){
        @Override
        public boolean test(Integer integer){
            return integer %2 ==0;
                      }
                  });

//    stream.filter(x -> x % 2=0).forEach(x -> System.out.print(x));

    //  List<Integer> arrayTwo  = arrayAll.stream().filter((n) -> n % 2 = 0).collect(Collectors.toList());
    // forEach(System.out::println);
}
    public static void printList(ArrayList<Integer> list) {
        //напишите тут ваш код
        list.stream().forEach(System.out :: println);
    }
}
