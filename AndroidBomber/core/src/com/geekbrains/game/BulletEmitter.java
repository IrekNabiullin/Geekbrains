package com.geekbrains.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BulletEmitter extends ObjectPool<Bullet> implements Serializable {
    private transient GameScreen gs;
    private int maxBulletsCount;
    private List<Bullet> tmpList;
    private Rectangle tmpRect;

    @Override
    protected Bullet newObject() {
        return new Bullet(gs);
    }

    public void reloadResources(GameScreen gs) {
        this.gs = gs;
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).reloadResources(gs);
        }
        for (int i = 0; i < freeList.size(); i++) {
            freeList.get(i).reloadResources(gs);
        }
    }

    public BulletEmitter(GameScreen gs) {
        this.gs = gs;
        this.maxBulletsCount = 1;  // maximum bullets on the game screen
        this.tmpList = new ArrayList<Bullet>();
        this.tmpRect = new Rectangle(0, 0, Rules.CELL_SIZE - 10, Rules.CELL_SIZE - 10);
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
        checkPool();
    }

    public void update(float dt) {

        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }

    public int getMaxBulletCount() {
        return maxBulletsCount;
    }
}
