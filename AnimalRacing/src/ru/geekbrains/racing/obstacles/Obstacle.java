package ru.geekbrains.racing.obstacles;

import ru.geekbrains.racing.participants.Participant;

public abstract class Obstacle {
    public abstract void doIt(Participant p); //метод, принимающий участника, для выполнения определенного задания
}
