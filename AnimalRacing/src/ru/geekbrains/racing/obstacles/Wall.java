package ru.geekbrains.racing.obstacles;

        import ru.geekbrains.racing.participants.Animal;
        import ru.geekbrains.racing.participants.Participant;

public class Wall extends Obstacle {
    private int height; //высота стены

    public Wall(int height) {
        this.height = height;
    }

    @Override
    public void doIt(Participant p) {
        p.jump(height); //участник должен перепрыгнуть стену
    }
}
