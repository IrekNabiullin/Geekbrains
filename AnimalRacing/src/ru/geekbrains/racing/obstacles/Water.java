package ru.geekbrains.racing.obstacles;

import ru.geekbrains.racing.participants.Animal;
import ru.geekbrains.racing.participants.Participant;

public class Water extends Obstacle {
    private int length; //длина, котрую нужно проплыть

    public Water(int length) {
        this.length = length;
    }

    @Override
    public void doIt(Participant p) {
        p.swim(length); //участник должен проплыт установленное расстояние
    }
}
