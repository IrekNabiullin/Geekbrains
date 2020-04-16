package com.geekbrains.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;
import java.util.List;

public class Bullet implements Poolable, Serializable {

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

    private Bomberman owner;
    private transient GameScreen gs;
    private transient Animation[] animations;
    private Vector2 position;
    private Vector2 velocity;
    private float pathCounter;
    private float speed;
    private Bullet.State currentState;
    private boolean active;
    private Rectangle hitBox;
    private int direction;
    private int cellX, cellY;
    private int voiceDelay;

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBulletHitBox() {
        return hitBox;
    }

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


    public void reloadResources(GameScreen gs) {
        this.gs = gs;
        this.animations = new Animation[Bullet.State.values().length];
        for (int i = 0; i < Bullet.State.values().length; i++) {
            this.animations[i] = new Animation();
//            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("bot")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("molotoff5")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
        }
    }

    public Bullet(GameScreen gs) {
        this.gs = gs;
        this.position = new Vector2(440.0f, 440.0f);
        this.velocity = new Vector2(0.0f, 0.0f);
        this.speed = 400.0f;
        this.pathCounter = -1;
        this.animations = new Animation[Bullet.State.values().length];
        this.active = false;
        this.hitBox = new Rectangle(position.x, position.y, Rules.CELL_SIZE, Rules.CELL_SIZE);
        for (int i = 0; i < Bullet.State.values().length; i++) {
            this.animations[i] = new Animation();
//            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("bot")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("molotoff5")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
        }
        this.currentState = Bullet.State.IDLE;
        this.owner = null;
    }

    public void destroy() {
        active = false;
    }

    public void init(Bomberman owner, int fireDirection) {
        this.owner = owner;
//    public void init(int fireDirection) {
        position.set(gs.getPlayer().getPosition());
        currentState = Bullet.State.IDLE;
        pathCounter = -1;
        velocity.set(0.0f, 0.0f);
        active = true;
        direction = fireDirection;
        hitBox.setPosition(position);
    }

// move bullets


    public void render(SpriteBatch batch) {
        batch.draw(animations[currentState.animationIndex].getCurrentRegion(), position.x - Rules.CELL_HALF_SIZE, position.y - Rules.CELL_HALF_SIZE);
    }

    public void update(float dt) {
        animations[currentState.animationIndex].update(dt);

        if (pathCounter > 0.0f) {
            position.mulAdd(velocity, dt);
            pathCounter += velocity.len() * dt;

            if (pathCounter >= Rules.CELL_SIZE) {
                position.x = getCellX() * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;
                position.y = getCellY() * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;
                pathCounter = -1.0f;
                currentState = Bullet.State.IDLE;
            }
        }

        if (currentState == Bullet.State.IDLE) {
            Bullet.Direction direction = Bullet.Direction.values() [this.direction];
            if (gs.getMap().isCellPassable(getCellX() + direction.dx, getCellY() + direction.dy)) {
                velocity.set(direction.dx * speed, direction.dy * speed);
                pathCounter = 0.1f;
                currentState = Bullet.State.MOVE;
            }
            else destroy();
        }

 //       if (position.x < 0 || position.x > 1280 || position.y < 0 || position.y > 720) {
 //           destroy();
 //       }

    // ********* hit with bot****************
        if (gs.getBotEmitter().isBotInCell(getCellX(), getCellY())) {
            shout();
        }

        hitBox.setPosition(position);
    }

    // Bullet hits Bot********

    public void shout() {
        active = false;
        gs.getBotEmitter().tryToShoutBot(cellX, cellY);
        shoutInCell(getCellX(), getCellY());
    }

    private boolean shoutInCell(int cellX, int cellY) {

        List<Bot> damagedBots = gs.getBotEmitter().getBotsInCell(cellX, cellY);
        for (int i = 0; i < damagedBots.size(); i++) {
            if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
                damagedBots.get(i).hitBotSoundPlay();
            }
            damagedBots.get(i).destroy();
            gs.getAnimationEmitter().createAnimation(damagedBots.get(i).getPosition().x, damagedBots.get(i).getPosition().y, 2.0f, AnimationEmitter.AnimationType.FIRE);
            owner.addScore(500);
        }

        return true;
    }

    public Vector2 getBulletPosition(){
        return position;
    }
}

