package ru.geekbrains.racing.participants;

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
