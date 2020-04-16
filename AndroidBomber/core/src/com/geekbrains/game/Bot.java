package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Bot implements Poolable, Serializable {
    public Rectangle getHitBox() {
        return hitBox;
    }

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

    private transient GameScreen gs;
    private transient Animation[] animations;
    private Vector2 position;
    private Vector2 velocity;
    private float pathCounter;
    private float speed;
    private State currentState;
    private boolean active;
    private Rectangle hitBox;
    private BulletOfBotEmitter bulletOfBotEmitter; //Bullet of Bots
    private int preferredDirection;
    private transient Sound botFireSound;
    private transient Sound botShoutSound;

    public Vector2 getPosition() {
        return position;
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
        this.animations = new Animation[Bot.State.values().length];
        for (int i = 0; i < Bot.State.values().length; i++) {
            this.animations[i] = new Animation();
//            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("bot4x3")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("botzombie")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
        }
        this.bulletOfBotEmitter = new BulletOfBotEmitter(gs, this);  // Bullet of Bots
        this.botFireSound = Gdx.audio.newSound(Gdx.files.internal("vystrel.mp3"));
        this.botShoutSound = Gdx.audio.newSound(Gdx.files.internal("bot_aah.mp3"));
    }

    public Bot(GameScreen gs) {
        this.gs = gs;
        this.position = new Vector2(440.0f, 440.0f);
        this.velocity = new Vector2(0.0f, 0.0f);
        this.speed = 40.0f;
        this.pathCounter = -1;
        this.animations = new Animation[Bot.State.values().length];
        this.active = false; // change to true and see waterfall
        this.hitBox = new Rectangle(position.x, position.y, Rules.CELL_SIZE, Rules.CELL_SIZE);
        for (int i = 0; i < Bot.State.values().length; i++) {
            this.animations[i] = new Animation();
//            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("bot4x3")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("botzombie")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
        }
        this.currentState = State.IDLE;
        this.bulletOfBotEmitter = new BulletOfBotEmitter(gs, this);  // Bullet of Bots
        this.botFireSound = Gdx.audio.newSound(Gdx.files.internal("vystrel.mp3"));
        this.botShoutSound = Gdx.audio.newSound(Gdx.files.internal("bot_aah.mp3"));
    }

    public void destroy() {
        active = false;
        for (int i=0; i<getBulletOfBotEmitter().getActiveList().size();i++) {
            //           System.out.println("Bot.Bot:" + this.hashCode() + "getBulletOfBotEmitter().getActiveList().size():" + getBulletOfBotEmitter().getActiveList().size());
            getBulletOfBotEmitter().getActiveList().get(i).destroy();
            //           System.out.println ("Bot.Bot:" + this.hashCode() + "render bullet");

        }
        getBulletOfBotEmitter().checkPool();

    }

    public BulletOfBotEmitter getBulletOfBotEmitter() {
        return bulletOfBotEmitter;
    } // Bullet of Bots

    public void init() {
        int cx = -1, cy = -1;
        int distX =0, distY =0;
        do {
            cx = MathUtils.random(0, gs.getMap().getMapX() - 1);
            cy = MathUtils.random(0, gs.getMap().getMapY() - 1);
 //           System.out.println("cx=" + cx + " cy=" + cy);
 //           System.out.println("Player x:" + gs.getPlayer().getCellX() + " y:" + gs.getPlayer().getCellY());
            distX = Math.abs(cx - gs.getPlayer().getCellX());
            distY = Math.abs(cy - gs.getPlayer().getCellY());
 //           System.out.println("cx-player.x =" + distX );
 //           System.out.println("cy-player.y = " + distY);
        } while (!gs.getMap().isCellEmpty(cx, cy) || distX < 3 || distY < 3); // Bots not appear near player
//        }while (!gs.getMap().isCellEmpty(cx, cy));
        position.set(cx * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, cy * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE);
        currentState = State.IDLE;
        pathCounter = -1;
        velocity.set(0.0f, 0.0f);
        active = true;
        hitBox.setPosition(position);
    }

    public void render(SpriteBatch batch) {
        batch.draw(animations[currentState.animationIndex].getCurrentRegion(), position.x - Rules.CELL_HALF_SIZE, position.y - Rules.CELL_HALF_SIZE);
        // Bullets of Bot
        for (int i=0; i<getBulletOfBotEmitter().getActiveList().size();i++) {
//            System.out.println("Bot.Bot:" + this.hashCode() + "getBulletOfBotEmitter().getActiveList().size():" + getBulletOfBotEmitter().getActiveList().size());
            getBulletOfBotEmitter().getActiveList().get(i).render(batch);
//            System.out.println ("Bot.Bot:" + this.hashCode() + "render bullet");
        }
        getBulletOfBotEmitter().checkPool();
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
                currentState = State.IDLE;
            }
        }

        if (currentState == State.IDLE) {
 //           Direction direction = Direction.values()[MathUtils.random(0, 3)];
            // claculate direction to player instead of random direction
            // 0 == Left
            // 1 == Right
            // 2 == up
            // 3 == down
            int randomIndex = new MathUtils().random(2);
 //           Direction direction = Direction.values()[MathUtils.random(1, 3)];  //first version of random move of bot
 //           Direction direction = Direction.values()[fireDirection()];  // not random move to player. bot stops at obstacle
            Direction direction = Direction.values()[setPreferredDirections()[randomIndex]];
            if (gs.getMap().isCellPassable(getCellX() + direction.dx, getCellY() + direction.dy)) {
                velocity.set(direction.dx * speed, direction.dy * speed);
                pathCounter = 0.1f;
                currentState = State.MOVE;
                if (BulletStatus.INSTANCE.getBotBulletStatus()==1) {
                    if (isEmptySpaceToTarget()) {
                        fireBullet(fireDirection()); // add direction calculation to the player position
                    }
                }
            }
        }

// Bot hits Bomberman

        if (gs.getPlayer().getPosition().dst(position.x, position.y) < Rules.CELL_SIZE * 0.8f) {
            gs.getPlayer().takeDamage(1);
            if (MusicStatus.INSTANCE.getSoundStatus() == 1){
                gs.getPlayer().hitPlayerSoundPlay();
            }
        }
        hitBox.setPosition(position);
    }

    public Vector2 getBotPosition(){
        return position;
    }

    public void fireBullet(int fireDirection) {
// first version of BulletOfBotEmitter in GameScreen class:

        if (getBulletOfBotEmitter().getActiveList().size() < getBulletOfBotEmitter().getMaxBulletCount()) {

            getBulletOfBotEmitter().getActiveElement().init(this, fireDirection);
            if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
                botFireSound.play();
            }
        }
 //       }
        getBulletOfBotEmitter().checkPool(); //added when bullet of bot written
    }

    // calculate player direction to bot position
    public int fireDirection() {
        int directionX = this.getCellX() - gs.getPlayer().getCellX();
        int directionY = this.getCellY() - gs.getPlayer().getCellY();
        if (directionX == 0){
            if (directionY >= 0) {
                return 3;  // Down
            } else {
                return 2; // Up
            }
        }
        if (directionY == 0){
            if (directionX >= 0) {
                return 0;  // Left
            } else {
                return 1; // Right
            }
        }
        if (directionX >0 && directionY >0){
            if (directionX > directionY) {
                return 0;  // Left
            } else {
                return 3; // Down
            }
        } else if (directionX < 0 && directionY <0) {
            if (directionX < directionY) {
                return 1;  // Right
            } else {
                return 2; // Up
            }
        } else if (directionX < 0 && directionY >0){
            int modDirectionX = -1*directionX;
            if(modDirectionX <directionY){
                return 3; //Down
            } else{
                return 1; // Right
            }
        } else if (directionX >0 && directionY <0){
            int modDirectionY = -1*directionY;
            if (directionX < modDirectionY){
                return 2; //Up
            } else {
                return 0;  // Left
            }
        } else return 1;
    }

    //is there empty space between bot and player
    public boolean isEmptySpaceToTarget(){
        if (fireDirection() == 0){  // Left
            if (Math.abs(this.getCellY() - gs.getPlayer().getCellY()) < 2 ) {
                int distanceToTarget = this.getCellX() - gs.getPlayer().getCellX();
                int emptyCellsToTarget = 0;
                int beginPosition = gs.getPlayer().getCellX();
                int endPosition = this.getCellX();
                int otherDimention = this.getCellY();
                for (int i = beginPosition; i < endPosition; i++) {
 //                   System.out.println("Bot " + this.hashCode() + " calculates empty space between " + i + " and " + endPosition);
                    if (gs.getMap().isCellEmpty(i, otherDimention)) {
                        emptyCellsToTarget++;
 //                       System.out.println("EmptyCellsToTarget = " + emptyCellsToTarget);
                    }
                }
                return emptyCellsToTarget == distanceToTarget;
            }
        } else if (fireDirection() == 1){  //Right
            if (Math.abs(this.getCellY() - gs.getPlayer().getCellY()) < 2 ) {
                int distanceToTarget = gs.getPlayer().getCellX() - this.getCellX();
                int beginPosition = this.getCellX();
                int endPosition = gs.getPlayer().getCellX();
                int otherDimention = this.getCellY();
                int emptyCellsToTarget = 0;
                for (int i = beginPosition; i < endPosition; i++) {
                    if (gs.getMap().isCellEmpty(i, otherDimention)) {
                        emptyCellsToTarget++;
                    }
                }
                return emptyCellsToTarget == distanceToTarget;
            }
        } else if (fireDirection() == 2){ //Up
            if (Math.abs(this.getCellX() - gs.getPlayer().getCellX()) < 2 ) {
                int distanceToTarget = gs.getPlayer().getCellY() - this.getCellY();
                int beginPosition = this.getCellY();
                int endPosition = gs.getPlayer().getCellY();
                int otherDimention = this.getCellX();
                int emptyCellsToTarget = 0;
                for (int i = beginPosition; i < endPosition; i++) {
                    if (gs.getMap().isCellEmpty(otherDimention, i)) {
                        emptyCellsToTarget++;
                    }
                }
                return emptyCellsToTarget == distanceToTarget;
            }
        } else if (fireDirection() == 3) { //Down
            if (Math.abs(this.getCellX() - gs.getPlayer().getCellX()) < 2) {
                int distanceToTarget = this.getCellY() - gs.getPlayer().getCellY();
                int beginPosition = gs.getPlayer().getCellY();
                int endPosition = this.getCellY();
                int otherDimention = this.getCellX();
                int emptyCellsToTarget = 0;
                for (int i = beginPosition; i < endPosition; i++) {
                    if (gs.getMap().isCellEmpty(otherDimention, i)) {
                        emptyCellsToTarget++;
                    }
                }
                return emptyCellsToTarget == distanceToTarget;
            }
        }

      return false;
    }

    // determine preferred 3 directions to move toward player

    public int[] setPreferredDirections () {
//        int [] temparrLeft = {0,2,3};
//        int [] temparrRight = {1,2,3};
//        int [] temparrUp = {0,1,2};
//        int [] temparrDown = {0,2,3};
        int[] tempArr = {0,2,3};
        switch (fireDirection()) {
            case 0:
                tempArr[0] = 0;
                tempArr[1] = 2;
                tempArr[2] = 3;
                break;
            case 1:
                tempArr[0] = 1;
                tempArr[1] = 2;
                tempArr[2] = 3;
                break;
            case 2:
                tempArr[0] = 0;
                tempArr[1] = 1;
                tempArr[2] = 2;
                break;
            case 3:
                tempArr[0] = 0;
                tempArr[1] = 2;
                tempArr[2] = 3;
                break;
            default:
                tempArr[0] = 0;
                tempArr[1] = 1;
                tempArr[2] = 2;
                break;
        }
        return tempArr;
    }

    public void hitBotSoundPlay() {
        if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
            botShoutSound.play();
        }
    }

    public void checkDistanceToTarget (int cx, int cy){
        System.out.println("cx:" + cx + "cy:" + cy);
    }

}
