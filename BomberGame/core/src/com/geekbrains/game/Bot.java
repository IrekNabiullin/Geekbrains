package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bot implements Poolable  {
    public enum State {
        IDLE(0), MOVE(1);

        private int animationIndex;

        State(int animationIndex) {
            this.animationIndex = animationIndex;
        }
    }

    public enum Direction {
        LEFT(-1, 0), RIGHT(1, 0), UP(0, 1), DOWN(0, -1);

        private int dx;
        private int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    private GameScreen gs;
    private Animation[] animations;
    private Vector2 position;
    private Vector2 velocity;
    private float pathCounter;
    private float speed;
    private State currentState;
    private boolean active;
    private Sound botsound;


    @Override
    public boolean isActive() {
        return active;
    }

    public int getCellX() {
        return (int) (position.x / Rules.CELL_SIZE);
    }

    public int getCellY() {
        return (int) (position.y / Rules.CELL_SIZE);
    }

    public Bot(GameScreen gs) {
        this.gs = gs;
        this.position = new Vector2(440.0f, 440.0f);
        this.velocity = new Vector2(0.0f, 0.0f);
        this.speed = 40.0f;
        this.pathCounter = -1;
        this.animations = new Animation[Bomberman.State.values().length];
        this.botsound = Gdx.audio.newSound(Gdx.files.internal("botsound.mp3"));
        for (int i = 0; i < Bomberman.State.values().length; i++) {
            this.animations[i] = new Animation();
            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("bot")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
        }
        this.currentState = State.IDLE;
    }

    public void render(SpriteBatch batch) {
        batch.draw(animations[currentState.animationIndex].getCurrentRegion(), position.x - Rules.CELL_HALF_SIZE, position.y - Rules.CELL_HALF_SIZE);
    }

    public void update(float dt) {
        animations[currentState.animationIndex].update(dt);

        if (currentState == State.IDLE) {
            Direction direction = Direction.values()[MathUtils.random(0, 3)];
            if (gs.getMap().isCellEmpty(getCellX() + direction.dx, getCellY() + direction.dy)) {
                velocity.set(direction.dx * speed, direction.dy * speed);
                pathCounter = 0.1f;
                currentState = State.MOVE;
                botsound.play();
            }
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

    public void activate(int cellX, int cellY) {
        this.position.x = cellX * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;
        this.position.y = cellY  * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;
        this.active = true;
    }
}
