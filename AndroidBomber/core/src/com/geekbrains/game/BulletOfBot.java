package com.geekbrains.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
//import org.lwjgl.Sys;

import java.io.Serializable;
import java.util.List;

public class BulletOfBot implements Poolable, Serializable {
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

    private Bot owner;
    private transient GameScreen gs;
    private transient Animation[] animations;
    private Vector2 position;
    private Vector2 velocity;
    private float pathCounter;
    private float speed;
    private BulletOfBot.State currentState;
    private boolean active;
//    private Rectangle hitBox;
    private int direction;
    private int cellX, cellY;

    public Vector2 getPosition() {
        return position;
    }

//    public Rectangle getBulletHitBox() {
//        return hitBox;
//    }

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
        this.animations = new Animation[BulletOfBot.State.values().length];
        for (int i = 0; i < BulletOfBot.State.values().length; i++) {
            this.animations[i] = new Animation();
            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("bulletvirus")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
        }
    }

    public BulletOfBot(GameScreen gs, Bot owner) {
        this.gs = gs;
//        this.position = new Vector2(440.0f, 440.0f);
        this.position = new Vector2(0.0f, 0.0f);
        this.velocity = new Vector2(0.0f, 0.0f);
        this.speed = 400.0f;
        this.pathCounter = -1;
        this.animations = new Animation[BulletOfBot.State.values().length];
        this.active = false;  // it was true on first version
//        this.hitBox = new Rectangle(position.x, position.y, Rules.CELL_SIZE, Rules.CELL_SIZE);
        for (int i = 0; i < BulletOfBot.State.values().length; i++) {
            this.animations[i] = new Animation();
            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("bulletvirus")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
        }
        this.currentState = BulletOfBot.State.IDLE;
//        this.owner = null;
        this.owner = owner;
    }

    public void destroy() {
        active = false;
    }

    public void init(Bot owner, int fireDirection) {
        this.owner = owner;
//        position.set(gs.getPlayer().getPosition());
//        System.out.println ("BB1.Bullet " + this.hashCode() + " init in position: " + owner.getBotPosition());
        position.set(owner.getBotPosition());
//        System.out.println ("BB2.Bullet " + this.hashCode() + " of bot " + this.owner.hashCode() + " init in Cell X: " + this.getCellX() + " Y:" + this.getCellY());
        currentState = BulletOfBot.State.IDLE;
        pathCounter = -1;
        velocity.set(0.0f, 0.0f);
        active = true;
        direction = fireDirection;
//        hitBox.setPosition(position);
    }

// move bullets


    public void render(SpriteBatch batch) {
        if (currentState == BulletOfBot.State.MOVE) {
            batch.draw(animations[currentState.animationIndex].getCurrentRegion(), position.x - Rules.CELL_HALF_SIZE, position.y - Rules.CELL_HALF_SIZE);
        }
    }

    public void update(float dt) {
        animations[currentState.animationIndex].update(dt);

        if (pathCounter > 0.0f) {
            position.mulAdd(velocity, dt);
            pathCounter += velocity.len() * dt;
//            System.out.println ("3.Bullet " + this.hashCode() + " owner: " + owner.hashCode() + " position: " + this.getPosition());

            if (pathCounter >= Rules.CELL_SIZE) {
                position.x = getCellX() * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;
                position.y = getCellY() * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;
                pathCounter = -1.0f;
                currentState = BulletOfBot.State.IDLE;
 //               System.out.println ("4.Bullet " + this.hashCode() + " owner: " + owner.hashCode() + " position:" + this.getPosition());
            }
        }

        if (currentState == BulletOfBot.State.IDLE) {
            BulletOfBot.Direction direction = BulletOfBot.Direction.values() [this.direction];
            if (gs.getMap().isCellPassable(getCellX() + direction.dx, getCellY() + direction.dy)) {
//                System.out.println("BB.Bullet " + this.hashCode() + " checks if cell is passable X:" + getCellX() + " Y:" + getCellY());
                velocity.set(direction.dx * speed, direction.dy * speed);
                pathCounter = 0.1f;
                currentState = BulletOfBot.State.MOVE;
 //               System.out.println("5.Bullet " + this.hashCode() + " state is IDLE/MOVE. Position: " + this.getPosition());
                if (gs.getBotEmitter().isBotInCell(getCellX(), getCellY())) {  // Check for Bot in cell
//                    System.out.println("There is bot in cell X:" + getCellX() + ", Y:" + getCellY() );
//                    System.out.println("BB.Owner " + owner.hashCode() + " of bullet " + this.hashCode() + " is in Cell X:" + owner.getCellX() + " Y:" + owner.getCellY());
//                    System.out.println("Bullet " + this.hashCode() + " in cell x:" + this.getCellX() + " y:" + this.getCellY());
//                    System.out.println("Bullet " + this.hashCode() + " getCellX:" + getCellX() + " getCellY:" + getCellY());
                    if (owner.getCellX() == this.getCellX() && owner.getCellY() == this.getCellY()) {
//                        System.out.println("Bullet and owner at same Cell");
                    } else {
//                    System.out.println("BB.Owner.getPosition:" + owner.getPosition());
//                    System.out.println("BB.This.GetPosition:" + this.getPosition());
//                    if (owner.getPosition() != this.getPosition()) {
//                        System.out.println("Bullet " + this.hashCode() + " in cell x:" + getCellX() + " y:" + getCellY() + " destroyed.");
                        destroy();
                        gs.getBotEmitter().tryToShoutBot(getCellX(),getCellY());
//                        System.out.println("BB. Bot shout at Cell X:" + getCellX() + " Y:" + getCellY());
                    }
                }
            }
            else {
//                System.out.println ("666.Bullet " + this.hashCode() + " owner: " + owner.hashCode() + " destroyed at position:" + this.getPosition());
                destroy();
            }
        }

        //       if (position.x < 0 || position.x > 1280 || position.y < 0 || position.y > 720) {
        //           destroy();
        //       }

        // ********* hit with bot****************
 //       if (gs.getBotEmitter().isBotInCell(getCellX(), getCellY())) {
        if (gs.getPlayer().getCellX() == getCellX() && gs.getPlayer().getCellY() == getCellY()){
            shout();
        }

//        hitBox.setPosition(position);
    }

    // Bullet hits Bot********

    public void shout() {
        active = false;
//        gs.getBotEmitter().tryToShoutBot(cellX, cellY);
//        gs.getPlayer().getHitBoxBomber(); // fix it
        gs.getPlayer().takeDamage(1);
        if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
            gs.getPlayer().hitPlayerSoundPlay();
        }
    }

    public void fire(int fireDirection){
        active = true;
        currentState = State.MOVE;
    }


    public Vector2 getBulletPosition(){
        return position;
    }
}
