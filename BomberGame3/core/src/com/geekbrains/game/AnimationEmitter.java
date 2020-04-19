package com.geekbrains.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import javafx.scene.text.Text;

public class AnimationEmitter {
    public enum AnimationType {
        EXPLOSION,
        TESTANIMATION;
    }

    private final int MAX_COUNT = 200;
    private Animation[] animations;

    private TextureRegion[] explosionRegions;
    private TextureRegion[] testAnimationRegions;

    private Texture explosionTexture;
    private Texture testAnimationTexture;

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

        testAnimationTexture = new Texture("testAnimation.png");

        TextureRegion[][] testAnimationTempRegions = new TextureRegion(testAnimationTexture).split(Rules.CELL_SIZE, Rules.CELL_SIZE);
        testAnimationRegions = new TextureRegion[testAnimationTempRegions.length* testAnimationTempRegions[0].length];
        int m = 0;
        for (int i = 0; i < testAnimationTempRegions.length; i++) {
            for (int j = 0; j < testAnimationTempRegions[0].length; j++) {
                testAnimationRegions[m] = testAnimationTempRegions[i][j];
                m++;
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
                    case TESTANIMATION:
                        animations[i].activate(x, y, testAnimationRegions, 0.1f);
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
