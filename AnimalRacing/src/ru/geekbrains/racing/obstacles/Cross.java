package ru.geekbrains.racing.obstacles;

import ru.geekbrains.racing.participants.Animal;
import ru.geekbrains.racing.participants.Participant;

public class Cross extends Obstacle {
    private int length; //длина дистанции

    public Cross(int length) {

        this.length = length;
    }

    @Override
    public void doIt(Participant p) {

        p.run(length); //участник должен пробежать установленную дистанцию
    }
}
