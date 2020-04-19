package ru.geekbrains.racing;

import ru.geekbrains.racing.obstacles.Cross;
import ru.geekbrains.racing.obstacles.Obstacle;
import ru.geekbrains.racing.obstacles.Wall;
import ru.geekbrains.racing.obstacles.Water;
import ru.geekbrains.racing.participants.*;

public class Main {
    public static void main(String[] args) {
        Participant[] participants = {new Cat("Barsik"), new Dog("Bobik"), new Human("Bob")};
        Obstacle[] obstacles = {new Cross(100), new Wall(10), new Water(5)};

        for (Participant p : participants) {
            for (Obstacle o : obstacles) {
                o.doIt(p);
                if (!p.isOnDistance()) {
                    break;
                }
            }
        }
        System.out.println("RESULTS:");
        for (Participant p : participants) {
            p.info();
        }
    }
}
