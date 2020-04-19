package com.geekbrains.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BombEmitter extends ObjectPool<Bomb> {
    private AnimationEmitter animationEmitter;
    private TextureRegion textureRegion;
    private Map map;

    @Override
    protected Bomb newObject() {
        return new Bomb(animationEmitter, textureRegion, map);
    }

    public BombEmitter(AnimationEmitter animationEmitter, Map map) {
        this.animationEmitter = animationEmitter;
        this.textureRegion = Assets.getInstance().getAtlas().findRegion("bomb");
        this.map = map;
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
}