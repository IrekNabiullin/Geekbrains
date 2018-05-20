package com.geekbrains.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationEmitter {
    public enum AnimationType {
        EXPLOSION;
    }

    private final int MAX_COUNT = 200;
    private Animation[] animations;

    private TextureRegion[] explosionRegions;

    private Texture explosionTexture;

    public AnimationEmitter() {
        animations = new Animation[MAX_COUNT];
        for (int i = 0; i < animations.length; i++) {
            animations[i] = new Animation();
        }
        explosionTexture = new Texture("explosion.png");
        TextureRegion[][] tempRegions = new TextureRegion(explosionTexture).split(Rules.CELL_SIZE, Rules.CELL_SIZE);
        explosionRegions = new TextureRegion[tempRegions.length * tempRegions[0].length];
        int n = 0;
        for (int i = 0; i < tempRegions.length; i++) {
            for (int j = 0; j < tempRegions[0].length; j++) {
                explosionRegions[n] = tempRegions[i][j];
                n++;
            }
        }
    }

    public void createAnimation(float x, float y, AnimationType type) {
        for (int i = 0; i < animations.length; i++) {
            if (!animations[i].isActive()) {
                switch (type) {
                    case EXPLOSION:
                        animations[i].activate(x, y, explosionRegions, 0.02f);
                        break;
                }
                break;
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < animations.length; i++) {
            if (animations[i].isActive()) {
                animations[i].render(batch);
            }
        }
    }

    public void update(float dt) {
        for (int i = 0; i < animations.length; i++) {
            if (animations[i].isActive()) {
                animations[i].update(dt);
            }
        }
    }
}
