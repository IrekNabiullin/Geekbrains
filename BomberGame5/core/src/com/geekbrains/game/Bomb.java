package com.geekbrains.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Bomb implements Poolable {
    private TextureRegion texture;
    private AnimationEmitter animationEmitter;
    private int cellX, cellY;
    private int radius;
    private float time;
    private float maxTime;
    private boolean active;
    private Map map;

    public int getCellX() {
        return cellX;
    }

    public int getCellY() {
        return cellY;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public Bomb(AnimationEmitter animationEmitter, TextureRegion texture, Map map) {
        this.texture = texture;
        this.animationEmitter = animationEmitter;
        this.map = map;
    }

    public void update(float dt) {
        time += dt;
        if (time >= maxTime) {
            boom(map);
        }
    }

    public void boom(Map map) {
        active = false;
        animationEmitter.createAnimation(cellX * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, cellY * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);
        int kRight = 1;
        int kLeft = 1;
        int kUp = 1;
        int kDown = 1;
        for (int i = 1; i <= radius; i++) {
            if (kRight < radius + 1 && map.isCellWall(getCellX() + kRight, getCellY())) {
                kRight = radius + 1;
            }else if (kRight < radius + 1) {
                    if (map.isCellEmpty(getCellX() + kRight, getCellY()) || map.isCellDestructable(getCellX() + kRight, getCellY())) {
                        animationEmitter.createAnimation((cellX + kRight) * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, cellY * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);
                    }
                    if (map.isCellDestructable(getCellX() + kRight, getCellY())) {
                        map.clearCell(getCellX() + kRight, getCellY());
                        kRight = radius + 1;
                    }
                    kRight++;
            }

            if (kLeft < radius + 1 && map.isCellWall(getCellX() - kLeft, getCellY())) {
                kLeft = radius + 1;
            }else if (kLeft < radius + 1) {
                    if (map.isCellEmpty(getCellX() - kLeft, getCellY()) || map.isCellDestructable(getCellX() - kLeft, getCellY())) {
                        animationEmitter.createAnimation((cellX - kLeft) * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, cellY * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);
                    }
                    if (map.isCellDestructable(getCellX() - kLeft, getCellY())) {
                        map.clearCell(getCellX() - kLeft, getCellY());
                        kLeft = radius + 1;
                    }
                    kLeft++;
            }

            if (kUp < radius + 1 && map.isCellWall(getCellX(), getCellY() + kUp)) {
                kUp = radius + 1;
            } else if (kUp < radius + 1) {
                    if (map.isCellEmpty(getCellX(), getCellY() + kUp) || map.isCellDestructable(getCellX(), getCellY() + kUp)) {
                        animationEmitter.createAnimation(cellX * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, (cellY + kUp) * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);
                    }
                    if (map.isCellDestructable(getCellX(), getCellY() + kUp)) {
                        map.clearCell(getCellX(), getCellY() + kUp);
                        kUp = radius + 1;
                    }
                    kUp++;
            }

            if (kDown < radius + 1 && map.isCellWall(getCellX(), getCellY() - kDown)) {
                kDown = radius + 1;
            } else if (kDown < radius + 1) {
                    if (map.isCellEmpty(getCellX(), getCellY() - kDown) || map.isCellDestructable(getCellX(), getCellY() - kDown)) {
                        animationEmitter.createAnimation(cellX * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, (cellY - kDown) * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);
                    }
                    if (map.isCellDestructable(getCellX(), getCellY() - kDown)) {
                        map.clearCell(getCellX(), getCellY() - kDown);
                        kDown = radius + 1;
                    }
                    kDown++;
            }

        }
    }

    public void activate(int cellX, int cellY, float maxTime, int radius) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.maxTime = maxTime;
        this.radius = radius;
        this.time = 0.0f;
        this.active = true;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, cellX * Rules.CELL_SIZE, cellY * Rules.CELL_SIZE);
    }
}
