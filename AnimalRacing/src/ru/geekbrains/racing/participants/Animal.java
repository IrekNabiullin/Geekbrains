package ru.geekbrains.racing.participants;

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
