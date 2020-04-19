package com.geekbrains.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BombEmitter extends ObjectPool<Bomb> {
    private GameScreen gs;
    private TextureRegion textureRegion;

    @Override
    protected Bomb newObject() {
        return new Bomb(gs, textureRegion);
    }

    public BombEmitter(GameScreen gs) {
        this.gs = gs;
        this.textureRegion = Assets.getInstance().getAtlas().findRegion("bomb");
        this.addObjectsToFreeList(10);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
    }

    public boolean isBombInCell(int cellX, int cellY) {
        for (int i = 0; i < activeList.size(); i++) {
            Bomb b = activeList.get(i);
            if (b.getCellX() == cellX && b.getCellY() == cellY) {
                return true;
            }
        }
        return false;
    }

    public void tryToDetonateBomb(int cellX, int cellY) {
        for (int i = 0; i < activeList.size(); i++) {
            Bomb b = activeList.get(i);
            if (b.getCellX() == cellX && b.getCellY() == cellY) {
                b.detonate();
                return;
            }
        }
    }
}
