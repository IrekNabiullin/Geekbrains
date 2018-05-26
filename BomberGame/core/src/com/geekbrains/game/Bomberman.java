package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Bomberman {
    public enum State {
        IDLE(0), MOVE(1);

        private int animationIndex;

        State(int animationIndex) {
            this.animationIndex = animationIndex;
        }
    }

    private Map map;
    private Animation[] animations;
    public Vector2 position;
    private Vector2 velocity;
    private float pathCounter;
    private float speed;
    private State currentState;
    private TextureAtlas atlas;
    public Bomb bomb;
    boolean isActive = true;

    public int getCellX() {
        return (int) (position.x / Rules.CELL_SIZE);
    }

    public int getCellY() {
        return (int) (position.y / Rules.CELL_SIZE);
    }

    public Bomberman(Map map, TextureAtlas atlas) {
        this.map = map;
        this.position = new Vector2(120.0f, 120.0f);
        this.velocity = new Vector2(0.0f, 0.0f);
        this.speed = 200.0f;
        this.pathCounter = -1;
        this.animations = new Animation[State.values().length];
        this.atlas = atlas;
        for (int i = 0; i < State.values().length; i++) {
            this.animations[i] = new Animation();
            this.animations[i].activate(0, 0, 1, new TextureRegion(atlas.findRegion("bomberA")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
        }
        this.currentState = State.IDLE;
    }

    public void render(SpriteBatch batch) {
        batch.draw(animations[currentState.animationIndex].getCurrentRegion(), position.x - Rules.CELL_HALF_SIZE, position.y - Rules.CELL_HALF_SIZE);
 //       bomb.render(batch);
    }

    public void update(float dt) {
        animations[currentState.animationIndex].update(dt);

        if (Gdx.input.isKeyPressed(Input.Keys.D) && pathCounter < 0.0f && map.isCellEmpty(getCellX() + 1, getCellY())) {
            velocity.set(speed, 0.0f);
            pathCounter = 0.1f;
            currentState = State.MOVE;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && pathCounter < 0.0f && map.isCellEmpty(getCellX() - 1, getCellY())) {
            velocity.set(-speed, 0.0f);
            pathCounter = 0.1f;
            currentState = State.MOVE;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && pathCounter < 0.0f && map.isCellEmpty(getCellX(), getCellY() + 1)) {
            velocity.set(0.0f, speed);
            pathCounter = 0.1f;
            currentState = State.MOVE;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && pathCounter < 0.0f && map.isCellEmpty(getCellX(), getCellY() - 1)) {
            velocity.set(0.0f, -speed);
            pathCounter = 0.1f;
            currentState = State.MOVE;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
//            bomb = new Bomb(atlas, true, 40.0f, 40.0f);
//            bomb = new Bomb(atlas, animationEmitter,true,getCellX()*Rules.CELL_SIZE, getCellY()* Rules.CELL_SIZE );
//            bomb.putBomb (getCellX()*Rules.CELL_SIZE,getCellY()* Rules.CELL_SIZE );
//            bomb.putBomb (getCellX(),getCellY());
            if (isActive == true) {
                this. isActive = false;
            }else this. isActive = true;
           bomb.setActive(isActive);
        }

        if (pathCounter > 0.0f) {
            position.mulAdd(velocity, dt);
            pathCounter += velocity.len() * dt;
            if (pathCounter >= Rules.CELL_SIZE) {
                position.x = getCellX() * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;
                position.y = getCellY() * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;
                pathCounter = -1.0f;
                currentState = State.IDLE;
            }
        }
    }
}
