package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bomberman {
    public enum State {
        IDLE(0), MOVE(1);

        private int animationIndex;

        State(int animationIndex) {
            this.animationIndex = animationIndex;
        }
    }

    private GameScreen gs;
    private Animation[] animations;
    private Vector2 position;
    private Vector2 velocity;
    private float pathCounter;
    private float speed;
    private State currentState;
    private int score;
    private int scoreToShow;
    private StringBuilder tmpStringBuilder;
    private float time;

    public int getCellX() {
        return (int) (position.x / Rules.CELL_SIZE);
    }

    public int getCellY() {
        return (int) (position.y / Rules.CELL_SIZE);
    }

    public Bomberman(GameScreen gs) {
        this.gs = gs;
        this.position = new Vector2(120.0f, 120.0f);
        this.velocity = new Vector2(0.0f, 0.0f);
        this.speed = 200.0f;
        this.pathCounter = -1;
        this.animations = new Animation[State.values().length];
        for (int i = 0; i < State.values().length; i++) {
            this.animations[i] = new Animation();
            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("bomber")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
        }
        this.currentState = State.IDLE;
        this.score = 0;
        this.scoreToShow = 0;
        this.tmpStringBuilder = new StringBuilder(32);
    }

    public void render(SpriteBatch batch) {
        batch.draw(animations[currentState.animationIndex].getCurrentRegion(), position.x - Rules.CELL_HALF_SIZE, position.y - Rules.CELL_HALF_SIZE);
    }

    public void renderGUI(SpriteBatch batch, BitmapFont font) {
        tmpStringBuilder.setLength(0);
        tmpStringBuilder.append("Score: ").append(scoreToShow);
        font.draw(batch, tmpStringBuilder, 20, 700);
    }

    public void update(float dt) {
        animations[currentState.animationIndex].update(dt);
        time += dt;
        if (scoreToShow < score) {
            scoreToShow += 4;
            if (scoreToShow > score) {
                scoreToShow = score;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) && pathCounter < 0.0f && gs.getMap().isCellEmpty(getCellX() + 1, getCellY())) {
            velocity.set(speed, 0.0f);
            pathCounter = 0.1f;
            currentState = State.MOVE;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && pathCounter < 0.0f && gs.getMap().isCellEmpty(getCellX() - 1, getCellY())) {
            velocity.set(-speed, 0.0f);
            pathCounter = 0.1f;
            currentState = State.MOVE;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && pathCounter < 0.0f && gs.getMap().isCellEmpty(getCellX(), getCellY() + 1)) {
            velocity.set(0.0f, speed);
            pathCounter = 0.1f;
            currentState = State.MOVE;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && pathCounter < 0.0f && gs.getMap().isCellEmpty(getCellX(), getCellY() - 1)) {
            velocity.set(0.0f, -speed);
            pathCounter = 0.1f;
            currentState = State.MOVE;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (!gs.getBombEmitter().isBombInCell(getCellX(), getCellY())) {
                Bomb b = gs.getBombEmitter().getActiveElement();
                b.activate(this, getCellX(), getCellY(), 2.0f, 3);
            }

            if (!gs.getBotEmitter().isBotInCell(getCellX(), getCellY())) {
                Bot bot = gs.getBotEmitter().getActiveElement();
                bot.activate(getCellX(), getCellY());
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


        if ((int)time%10000 == 9) {
            time = 0.0f;
            createBot();  // Ne ponyatno, pochemu BotEmitter sozdaet bolee 10 botov esli ego size ukazan kak 10
        }
    }

    public void createBot() {
        int CellX ;
        int CellY;
        do {
        CellX = MathUtils.random(0, (1280- Rules.CELL_SIZE) / Rules.CELL_SIZE);
        CellY = MathUtils.random(0, (720 - Rules.CELL_SIZE)  / Rules.CELL_SIZE);
               }while(!gs.getMap().isCellEmpty(CellX, CellY));

            if (!gs.getBotEmitter().isBotInCell(CellX, CellY)) {
                Bot bot = gs.getBotEmitter().getActiveElement();
                bot.activate(CellX, CellY);

        }
    }

    public void addScore(int amount) {
        score += amount;
    }
}
