/**
 * Java 1. Home work 7. Cats and Plate
 *
 * @author Irek Nabiullin
 * @version dated Feb 18, 2018
 * @link https://github.com/IrekNabiullin
 *
 *
 *  Задание:
 1. Расширить задачу про котов и тарелки с едой
 2. Сделать так, чтобы в тарелке с едой не могло получиться отрицательного количества еды (например, в миске 10 еды, а кот пытается покушать 15-20)
 3. Каждому коту нужно добавить поле сытость (когда создаем котов, они голодны). Если коту удалось покушать (хватило еды), сытость = true
 4. Считаем, что если коту мало еды в тарелке, то он ее просто не трогает, то есть не может быть наполовину сыт (это сделано для упрощения логики программы)
 5. Создать массив котов и тарелку с едой, попросить всех котов покушать из этой тарелки и потом вывести информацию о сытости котов в консоль
 6. Добавить в тарелку метод, с помощью которого можно было бы добавлять еду в тарелку
 *
 */

public class IrekHW007CatsAndPlate {

    public static void main(String[] args) {
        Cat[] cats = new Cat[6];  // создаем массив - инкубатор котов
        cats[0] = new Cat("Barsik", 10, false); // аппетит у котов разный
        cats[1] = new Cat("Murzik", 20, false);
        cats[2] = new Cat("Punshik", 30, false);
        cats[3] = new Cat("Vasyka", 40, false);
        cats[4] = new Cat("Kuzyka", 50, false);
        cats[5] = new Cat("Begemot", 80, false);

        String voice;                           // добавим котам голос
        Plate plate = new Plate(100);     // в тарелку помещается 100 единииц еды

        for (int i = 0; i < cats.length; i++) {
            System.out.println(plate);
            voice = cats[i].eat(plate);
            System.out.println("Is cat" + (i + 1) + " " + cats[i] + ". Cat says: " + voice);
            if (voice != "Mrrr...") {           // пока кота не накормим не остановимся
                System.out.println("Call mama!"); // зовем маму
                plate.callMama();           // чтобы подсыпать в тарелку еды
                i--;
            }
        }
        System.out.println(plate);
    }
}

class Cat {
    private String name;                        // уникальная кличка каждого кота
    private int appetite;                       // индивидуальный аппетит каждого кота
    private boolean hungry;                     // переменная голоден ли кот

    Cat(String name, int appetite, boolean hungry) {
        this.name = name;
        this.appetite = appetite;
        this.hungry = true;                     // коты рождаются голодными
    }

    String eat(Plate plate) {
        String voice;                           // все-таки коты должны уметь мяукать
                if (plate.food < appetite) {    //еды в тарелке хватает?
                    this.hungry = true;         // если нет
                    voice = "Meooow!!!";        // орем во все кошачье горло
                    return voice;
                } else {
                    plate.dicreaseFood(appetite);
                    this.hungry = false;
                    voice = "Mrrr...";          // насытившись, довольно мурчим
                }
        return voice;
    }

    @Override
    public String toString() {
        return name + " hungry?  -> " + hungry;  // выводим на консоль статус сытости кота
    }
}

class Plate {
    public int food;
    Plate(int food) {
        this.food = food;
    }

    void dicreaseFood(int food) {
        if (food <= this.food)
            this.food -= food;
    }

    void callMama () {
        System.out.println("Mama adds food.");
        this.food = 100;                        // наполняем тарлеку
    }

    @Override
    public String toString() {
        return "Food: " + food;
    }
}
