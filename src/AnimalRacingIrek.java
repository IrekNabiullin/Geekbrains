/**
 * Java Core. Home work 1. Animal Racing
 *
 * @author Irek Nabiullin
 * @version dated Feb 25, 2018
 * @link https://github.com/IrekNabiullin
 *
 *
 *  Задание:
 * 1. Разобраться с имеющимся кодом;
 * 2. Добавить класс Team, который будет содержать: название команды, массив из 4-х участников (т.е. в конструкторе можно сразу всех участников указывать), метод для вывода информации о членах команды прошедших дистанцию, метод вывода информации обо всех членах команды.
 * 3. Добавить класс Course (полоса препятствий), в котором будут находиться: массив препятствий, метод который будет просить команду пройти всю полосу;
 *
 * То есть в итоге должно быть что-то вроде:
 * public static void main(String[] args) {
 * Course c = new Course(...); // Создаем полосу препятствий
 * Team team = new Team(...); // Создаем команду
 * c.doIt(team); // Просим команду пройти полосу
 * team.showResults(); // Показываем результаты
 * }
 *
 */

package ru.geekbrains.racing;
import ru.geekbrains.racing.obstacles.*;
import ru.geekbrains.racing.participants.*;
import java.util.Arrays;

public class AnimalRacingIrek {
    public static void main(String[] args) {

        // создаем полосу препятствий
        Course c = new Course();

        // создаем команду:
        Team team = new Team ("Team1");

        // Просим команду пройти полосу:
        c.doIt(team);

        // Показываем результаты:
        team.showResults();

    }
}


public class Team {
    String teamName;            // переменная для названия команды
    public Participant[] teamMembers; // массив для членов команды

    // создаем конструктор для класса:
        public Team(String teamName) {
        this.teamName = teamName;
        teamMembers[0] = new Cat("Barsik");
        teamMembers[1] = new Dog("Bobik");
        teamMembers[2] = new Human("Bob");
        teamMembers[3] = new Human("Mary");

    teamInfo();
    }

    // метод для вывода информации о членах команды, прошедших дистанцию
    public void showResults () {
        for (int i = 0; i < teamMembers.length; i++) {
            if (teamMembers[i].isOnDistance() == true)
                System.out.println(teamMembers[i]);
            else continue;
        }
    }


    // метод для вывода информации обо всех  членах команды

    @Override
    public String toString() {
        return Arrays.toString(teamMembers);
    }

    public void teamInfo() {
        System.out.print("This is team " + teamName + ".");
        System.out.print("We have "  + teamMembers.length + " members.");
        System.out.println("Our members are: ");
        for(Participant t: teamMembers) {
            System.out.println(teamMembers); //HELP! Не работает @Override toString() Что делаю не так?
        }
    }
}

public interface Participant {
    void run(int distance);
    void jump(int height);
    void swim(int distance);
    boolean isOnDistance();
    void info();
}


public abstract class Animal implements Participant {
    String type;
    String name;

    int maxRunDistance;
    int maxJumpHeight;
    int maxSwimDistance;

    boolean onDistance;

    @Override
    public boolean isOnDistance() {
        return onDistance;
    }

    public Animal(String type, String name, int maxRunDistance, int maxJumpHeight, int maxSwimDistance) {
        this.type = type;
        this.name = name;
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
        this.maxSwimDistance = maxSwimDistance;
        this.onDistance = true;
    }

    @Override
    public void run(int distance) {
        if (distance <= maxRunDistance) {
            System.out.println(type + " " + name + " - Run OK");
        } else {
            System.out.println(type + " " + name + " - Run FAILED");
            onDistance = false;
        }
    }

    @Override
    public void jump(int height) {
        if (height <= maxJumpHeight) {
            System.out.println(type + " " + name + " - Jump OK");
        } else {
            System.out.println(type + " " + name + " - Jump FAILED");
            onDistance = false;
        }
    }

    @Override
    public void swim(int distance) {
        if (maxSwimDistance == 0) {
            System.out.println(type + " " + name + " can't swim");
            onDistance = false;
            return;
        }
        if (distance <= maxSwimDistance) {
            System.out.println(type + " " + name + " - Swim OK");
        } else {
            System.out.println(type + " " + name + " - Swim FAILED");
            onDistance = false;
        }
    }

    @Override
    public void info() {
        System.out.println(type + " " + name + ": " + onDistance);
    }
}


public class Cat extends Animal {
    public Cat(String name) {
        super("Cat", name, 500, 100, 0);
    }
}


public class Dog extends Animal {
    public Dog(String name) {
        super("Dog", name, 1000, 50, 50);
    }
}


public class Human implements Participant {
    String name;

    int maxRunDistance;
    int maxJumpHeight;
    int maxSwimDistance;

    boolean active;

    @Override
    public boolean isOnDistance() {
        return active;
    }

    public Human(String name) {
        this.name = name;
        this.maxRunDistance = 10000;
        this.maxJumpHeight = 150;
        this.maxSwimDistance = 1000;
        this.active = true;
    }

    @Override
    public void run(int distance) {
        if (distance <= maxRunDistance) {
            System.out.println(name + " - Run OK");
        } else {
            System.out.println(name + " - Run FAILED");
            active = false;
        }
    }

    @Override
    public void jump(int height) {
        if (height <= maxJumpHeight) {
            System.out.println(name + " - Jump OK");
        } else {
            System.out.println(name + " - Jump FAILED");
            active = false;
        }
    }

    @Override
    public void swim(int distance) {
        if (distance <= maxSwimDistance) {
            System.out.println(name + " - Swim OK");
        } else {
            System.out.println(name + " - Swim FAILED");
            active = false;
        }
    }

    @Override
    public void info() {
        System.out.println(name + ": " + active);
    }
}



public class Course {
    public Obstacle [] obstacles; //массив для препятствий

    // создаем конструктор для класса. Для упрощения не будем наполнять разными препятствиями, оставим 3.
    public Course() {
        obstacles [0] = new Cross(100);
        obstacles [1] = new Wall(10);
        obstacles [2] = new Water(5);
    }

    public void doIt(Team team) {
        for (Participant p : teamMembers) {
             for (Obstacle o : obstacles) {
                o.doIt(p);
                if (!p.isOnDistance()) {
                    break;
                }
            }
        }
    }
}


public abstract class Obstacle {
    public abstract void doIt(Participant p);
}


public class Cross extends Obstacle {
    private int length;

    public Cross(int length) {
        this.length = length;
    }

    @Override
    public void doIt(Participant p) {
        p.run(length);
    }
}


public class Wall extends Obstacle {
    private int height;

    public Wall(int height) {
        this.height = height;
    }

    @Override
    public void doIt(Participant p) {
        p.jump(height);
    }
}


public class Water extends Obstacle {
    private int length;

    public Water(int length) {
        this.length = length;
    }

    @Override
    public void doIt(Participant p) {
        p.swim(length);
    }
}
