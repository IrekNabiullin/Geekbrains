package com.geekbrains.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Animation {
    private Vector2 position;
    private TextureRegion[] regions;
    private int framesCount;
    private float time;
    private float maxTime;
    private float timePerFrame;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public Animation() {
        this.position = new Vector2(0.0f, 0.0f);
        this.regions = null;
        this.framesCount = 0;
        this.time = 0.0f;
        this.maxTime = 0.0f;
        this.timePerFrame = 0.0f;
        this.active = false;
    }

    public void activate(float x, float y, TextureRegion[] regions, float timePerFrame) {
        this.position.set(x, y);
        this.regions = regions;
        this.timePerFrame = timePerFrame;
        this.framesCount = regions.length;
        this.maxTime = timePerFrame * framesCount;
        this.time = 0.0f;
        this.active = true;
    }

    public void render(SpriteBatch batch) {
        int frameIndex = (int) (time / timePerFrame);
        batch.draw(regions[frameIndex], position.x - Rules.CELL_HALF_SIZE, position.y - Rules.CELL_HALF_SIZE, Rules.CELL_HALF_SIZE, Rules.CELL_HALF_SIZE, Rules.CELL_SIZE, Rules.CELL_SIZE, 3.0f, 3.0f, 0.0f);
    }

    public void update(float dt) {
        time += dt;
        if (time >= maxTime) {
            active = false;
        }
    }
}
