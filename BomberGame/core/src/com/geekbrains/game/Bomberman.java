package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bomberman {
    private Map map;
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float pathCounter;
    private float speed;

    public int getCellX() {
        return (int)(position.x / Rules.CELL_SIZE);
    }

    public int getCellY() {
        return (int)(position.y / Rules.CELL_SIZE);
    }

    public Bomberman(Map map) {
        this.map = map;
        this.texture = new Texture("bomber.png");
        this.position = new Vector2(120.0f, 120.0f);
        this.velocity = new Vector2(0.0f, 0.0f);
        this.speed = 200.0f;
        this.pathCounter = -1;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - Rules.CELL_HALF_SIZE, position.y - Rules.CELL_HALF_SIZE);
    }

    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.D) && pathCounter < 0.0f && map.isCellEmpty(getCellX() + 1, getCellY())) {
            velocity.set(speed, 0.0f);
            pathCounter = 0.1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && pathCounter < 0.0f && map.isCellEmpty(getCellX() - 1, getCellY())) {
            velocity.set(-speed, 0.0f);
            pathCounter = 0.1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && pathCounter < 0.0f && map.isCellEmpty(getCellX(), getCellY() + 1)) {
            velocity.set(0.0f, speed);
            pathCounter = 0.1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && pathCounter < 0.0f && map.isCellEmpty(getCellX(), getCellY() - 1)) {
            velocity.set(0.0f, -speed);
            pathCounter = 0.1f;
        }

        if (pathCounter > 0.0f) {
            position.mulAdd(velocity, dt);
            pathCounter += velocity.len() * dt;
            if (pathCounter >= Rules.CELL_SIZE) {
                position.x = getCellX() * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;
                position.y = getCellY() * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;
                pathCounter = -1.0f;
            }
        }
    }
}
