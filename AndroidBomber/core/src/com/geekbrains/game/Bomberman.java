package com.geekbrains.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.IOError;
import java.io.Serializable;
import java.util.Random;

public class Bomberman implements Serializable {

    public Rectangle getHitBoxBomber() {
        return hitBoxBomber;
    }

    public enum State {
        IDLE(0), MOVE(1), GAMEOVER(2);

        private int animationIndex;

        State(int animationIndex) {
            this.animationIndex = animationIndex;
        }
    }

    public enum UpgradeType {
        HP, BOMBRADIUS, MONEY, LIFE;
    }


    private transient GameScreen gs;
    private transient Animation[] animations;
    private transient TextureRegion hpTexture, baseHpTexture, molotoffTexture;
    private Vector2 position;
    private Vector2 velocity;
    private State currentState;
    private transient StringBuilder tmpStringBuilder;
    private char prefferedDirection;
    private float pathCounter;
    private float speed;
    private int score, scoreToShow;
    private int costHP = 10000;
    private int costRAD = 50000;
//    private int lifes;
    private int hp = 3;
    private int maxHp;
    private int bombPower;
    private int bombMaxPower = 5;
    private float damagedTimer;
    private transient Sound playersound;
    private transient Sound bombSet;
    private transient Sound hitPlayerSound;
    private transient Sound fireSound;
    private int gameLevel;
    private int bulletStatus;
    private Rectangle hitBoxBomber;
    private int fireCounter;
    private int fireDelay = 5; //time between shouts, it is 25 recommended when bullits more than 1
//    private float countDown;  // countdown random number jusy for fun
//    private int randomNumber;  // --- //----

    public void reloadResources(GameScreen gs) {
        this.gs = gs;
        this.animations = new Animation[State.values().length];
        for (int i = 0; i < State.values().length; i++) {
            this.animations[i] = new Animation();
            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("rider4x3german")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
        }
        this.tmpStringBuilder = new StringBuilder(32);
        this.baseHpTexture = Assets.getInstance().getAtlas().findRegion("hpBar3").split(80, 80)[0][1];
        this.molotoffTexture =  Assets.getInstance().getAtlas().findRegion("fireButton");
        this.hpTexture = Assets.getInstance().getAtlas().findRegion("hpBar3").split(80, 80)[0][0];
        this.playersound = Gdx.audio.newSound(Gdx.files.internal("playersound.mp3"));
        this.bombSet = Gdx.audio.newSound(Gdx.files.internal("bombSet.mp3"));
        this.hitPlayerSound = Gdx.audio.newSound(Gdx.files.internal("oops.mp3"));
        this.fireSound = Gdx.audio.newSound(Gdx.files.internal("fire_drop.mp3"));
    }

    public void setPrefferedDirection(char prefferedDirection) {
        this.prefferedDirection = prefferedDirection;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getCellX() {
        return (int) (position.x / Rules.CELL_SIZE);
    }

    public int getCellY() {
        return (int) (position.y / Rules.CELL_SIZE);
    }

    public boolean isVulnerable() {
        return damagedTimer < 0.001f;
    }

    public int getCurrentState() {
        if (currentState == State.IDLE) {
            return 0;
        }
        if (currentState == State.MOVE) {
            return 1;
        }
        if (currentState == State.GAMEOVER) {
            return 2;
        }
        return 3;
    }

 //   public int getGameLevel(){
 //       return gameLevel;
 //   }

//    public void setBulletStatus(int bulletStatus) {  DELETE IT IF Player bullet status loaded correctly
//        bulletStatus = this.bulletStatus;
//    }

//    public int getBulletStatus(){
//        return bulletStatus;
//    }

    public void startNewLevel(int hp) {
        this.position.set(gs.getMap().getStartPosition());
        this.damagedTimer = 0.0f;
        this.currentState = State.IDLE;
        this.velocity.set(0, 0);
        this.pathCounter = -1;
        this.prefferedDirection = '-';
        if (hp>maxHp) {
            this.maxHp = hp;
        } else {
            this.maxHp = HpCount.INSTANCE.getMaxHpCount();
        }
//        System.out.println("Bomberman Row131. ThisMaxhp.hp = " + hp);
        this.hp = HpCount.INSTANCE.getHpCount();
//        System.out.println("Bomberman Row132. This.hp = " + hp);
//        this.bulletStatus = BulletStatus.INSTANCE.getPlayerBulletStatus();
    }

    public Bomberman(GameScreen gs) {
        this.gs = gs;
        this.playersound = Gdx.audio.newSound(Gdx.files.internal("playersound.mp3"));
        this.bombSet = Gdx.audio.newSound(Gdx.files.internal("bombSet.mp3"));
        this.hitPlayerSound = Gdx.audio.newSound(Gdx.files.internal("oops.mp3"));
        this.fireSound = Gdx.audio.newSound(Gdx.files.internal("fire_drop.mp3"));
        this.position = new Vector2(0.0f, 0.0f);
        this.velocity = new Vector2(0.0f, 0.0f);
        this.speed = 200.0f;
        this.pathCounter = -1;
        this.bombPower = 1;
        this.animations = new Animation[State.values().length];
        this.maxHp = 3;
 //       this.lifes = 0;
 //       this.hp = this.maxHp;
//        System.out.println("Bomberman Row150. This.hp = " + hp);
        this.hp = HpCount.INSTANCE.getHpCount();
//        System.out.println("Bomberman Row152. This.hp = " + hp);
        for (int i = 0; i < State.values().length; i++) {
            this.animations[i] = new Animation();
            this.animations[i].activate(0, 0, 1, new TextureRegion(Assets.getInstance().getAtlas().findRegion("rider4x3german")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
        }
        this.currentState = State.IDLE;
//        this.score = 0;
        this.setScore(0); //FIXIT set score to 0 when restart level
        this.scoreToShow = 0;
        this.tmpStringBuilder = new StringBuilder(32);
        this.baseHpTexture = Assets.getInstance().getAtlas().findRegion("hpBar3").split(80, 80)[0][1];
        this.molotoffTexture =  Assets.getInstance().getAtlas().findRegion("fireButton");
        this.hpTexture = Assets.getInstance().getAtlas().findRegion("hpBar3").split(80, 80)[0][0];
        this.hitBoxBomber = new Rectangle(position.x, position.y, Rules.CELL_SIZE, Rules.CELL_SIZE);
        this.fireCounter = 0;
//        this.bulletStatus = 0;
    }

//  BONUS BAR
    public void upgrade(UpgradeType type) {
        switch (type) {
            case HP:
//                System.out.println("Bomberman.upgrade.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " ");
//                System.out.println("hp = " + hp);
//                System.out.println("GetHp = " + HpCount.INSTANCE.getHpCount());
//                System.out.println("maxHp = " + maxHp);

                if (hp < maxHp) {
                        hp++;
                        HpCount.INSTANCE.setHpCount(hp);
                    score = score - costHP;
                    scoreReduceUpdate();

                } else if (maxHp < 5) {
                        maxHp++;
                        HpCount.INSTANCE.setMaxHpCount(maxHp);
                        hp++;
                        HpCount.INSTANCE.setHpCount(hp);
                    score = score - costHP;
                    scoreReduceUpdate();
                }
                /*
                if (hp < maxHp) {
                    if (score >= 100000) {
                        score = score - 100000;
                        hp++;
                        }
                    } else if (maxHp < 5) {
                    if (score >= 100000) {
                        score = score - 100000;
                        maxHp++;
                        hp++;
                    }
                }
                */
                break;
//            case LIFE:
//                if (MathUtils.random(0, 100) < 8) {
//                    lifes++;
//                }
//                break;
//            case MONEY:
//                score += 1000;
//                break;
            case BOMBRADIUS:
                if (bombPower < bombMaxPower) {
                        bombPower++;
                        score = score - costRAD;
                    scoreReduceUpdate();
                }
                /*
                 if (bombPower < bombMaxPower) {
                    if (score >= 500000) {
                        score = score - 500000;
                        bombPower++;
                    }
                }
                 */
                break;
        }
    }



    // damage animation
    public void render(SpriteBatch batch) {
        if (isVulnerable()) {
            batch.draw(animations[currentState.animationIndex].getCurrentRegion(), position.x - Rules.CELL_HALF_SIZE, position.y - Rules.CELL_HALF_SIZE);
        } else {
            if (damagedTimer % 0.4f < 0.2f) {
                batch.draw(animations[currentState.animationIndex].getCurrentRegion(), position.x - Rules.CELL_HALF_SIZE, position.y - Rules.CELL_HALF_SIZE);
            }
        }
    }

    // damage to Bomberman:

//    public void takeDamage(int dmg, SpriteBatch batch, BitmapFont font) {
      public void takeDamage(int dmg) {
        if (isVulnerable()) {
            hp -= dmg;
            damagedTimer = 3.0f;
            HpCount.INSTANCE.setHpCount(hp);
            if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
                hitPlayerSound.play();
            }
            if (hp <= 0) {
//                if (lifes>0) {
//                    lifes--;
//                    maxHp = 1;
//                    hp = maxHp;
//                    bombPower = 1;
//                }else {
//                    tmpStringBuilder.setLength(0);
//                    tmpStringBuilder.append("GAME OVER");
//                    currentState = State.IDLE;
//                    font.draw(batch, tmpStringBuilder, 300, 380);
                if(!isVulnerable()) {  //FIXIT*********************************************
                    damagedTimer = 3.0f;
                    currentState = State.GAMEOVER;
                }
//                        ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.EXIT);
                    }

//            }
        }
    }

    public void renderGUI(SpriteBatch batch, BitmapFont font) {

        // Molotoff button render
        if (BulletStatus.INSTANCE.getPlayerBulletStatus() == 1){
            batch.draw(molotoffTexture, 2, 610);
        }

 // Score render
        tmpStringBuilder.setLength(0);
        tmpStringBuilder.append("Score: ").append(scoreToShow);
        font.draw(batch, tmpStringBuilder, 940, 680);

 // HP render

        //Trace current line number
        /*
        System.out.print("Bomberman.renderGUI.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " ");
        System.out.println("Current HP = " + hp);
        System.out.print("Bomberman.renderGUI.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " ");
        System.out.println("Current maxHP = " + maxHp);
*/
        for (int i = 0; i < maxHp; i++) {
            batch.draw(baseHpTexture, 150 + i * 40, 640);
            if (i < hp) {
                batch.draw(hpTexture, 150 + i * 40, 640);
            }
        }
        // lifes render removed
//        batch.setColor(0.2f, 1f, 0.2f, 1);
//        batch.draw(baseHpTexture, 20, 610, 100, 100);
        batch.setColor(1, 1, 1, 1);
        tmpStringBuilder.setLength(0);
//        tmpStringBuilder.append("x").append(lifes);
        tmpStringBuilder.append("HP");
        font.draw(batch, tmpStringBuilder, 100, 680);

        /*
        //render countdown just for fun
        tmpStringBuilder.setLength(0);
 //       tmpStringBuilder.append("Countdown: ").append(randomNumber);
        tmpStringBuilder.append("Countdown: ").append(countDown);
        font.draw(batch, tmpStringBuilder, 440, 680);
*/
    }

    // update screen

    // smooth score adding
    public void scoreUpdate() {
        if (scoreToShow < score) {
            int amountToAdd = (int) ((score - scoreToShow) * 0.04f);
            if (amountToAdd < 4) {
                amountToAdd = 4;
            }
            scoreToShow += amountToAdd;
            if (scoreToShow - score < 4) {
                scoreToShow = score;
            }
        }
    }

    public void scoreReduceUpdate() {
        if (scoreToShow > score) {
            int amountToReduce = (int) ((scoreToShow - score) * 0.04f);
            if (amountToReduce < 4) {
                amountToReduce = 4;
            }
            scoreToShow -= amountToReduce;
            if (score - scoreToShow < 4) {
                scoreToShow = score;
            }
        }
    }

    public void update(float dt) {
        animations[currentState.animationIndex].update(dt);
        if (!isVulnerable()) {
            damagedTimer -= dt;
            if (damagedTimer < 0.0f) {
                damagedTimer = 0.0f;
            }
        }
        scoreUpdate();
        scoreReduceUpdate();
        /**
        //countdown added just for fun
 //       countDown(dt);
        countDown -= dt;
        if (countDown <= 0) {
            Random random = new Random();
            randomNumber = random.nextInt(3) + 1;
            countDown += 1000; // add one second
        }
*/

        /**
// smooth score adding
        if (scoreToShow < score) {
            int amountToAdd = (int)((score - scoreToShow) * 0.04f);
            if(amountToAdd < 4) {
                amountToAdd = 4;
            }
            scoreToShow += amountToAdd;
            if (scoreToShow > score) {
                scoreToShow = score;
            }
        }
        */

// smooth cell centring when move done
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

// Bomber touch Bot's hitbox

//        if (this.hitBoxBomber.contains(Bot.getBotPosition(position))) {
//            playersound.setVolume(1, 0.9f);
//        }
 /**
 if (asteroids[j].hitBox.contains(bullets[i].position)) {
 asteroids[j].blowSound.setVolume(1, 0.3f);


        if (gs.getPlayer().getPosition().dst(cellX * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, cellY * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE) < Rules.CELL_SIZE * 0.8f) {
            gs.getPlayer().takeDamage(1);
        }
*/
//key listener
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || prefferedDirection == 'R') && pathCounter < 0.0f && gs.getMap().isCellPassable(getCellX() + 1, getCellY())) {
            velocity.set(speed, 0.0f);
            pathCounter = 0.1f;
            currentState = State.MOVE;
            if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
//                playersound.play();
            }
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || prefferedDirection == 'L') && pathCounter < 0.0f && gs.getMap().isCellPassable(getCellX() - 1, getCellY())) {
            velocity.set(-speed, 0.0f);
            pathCounter = 0.1f;
            currentState = State.MOVE;
            if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
//                playersound.play();
            }
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.UP) || prefferedDirection == 'U') && pathCounter < 0.0f && gs.getMap().isCellPassable(getCellX(), getCellY() + 1)) {
            velocity.set(0.0f, speed);
            pathCounter = 0.1f;
            currentState = State.MOVE;
            if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
//                playersound.play();
            }
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || prefferedDirection == 'D') && pathCounter < 0.0f && gs.getMap().isCellPassable(getCellX(), getCellY() - 1)) {
            velocity.set(0.0f, -speed);
            pathCounter = 0.1f;
            currentState = State.MOVE;
            if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
//                playersound.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            setupBomb();
            if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
                bombSet.play();
            }
        }
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            prefferedDirection = '-';
        }

//        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
//            ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.EXIT);
//        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            ScreenManager.getInstance().setPreviousScreenType(ScreenManager.ScreenType.GAME);  // try to remember ScreenType
            ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.OPTIONS);
        }

// keys for fire
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (BulletStatus.INSTANCE.getPlayerBulletStatus() == 1) {
//            if (bulletStatus ==1){
                fireCounter++;
                if (fireCounter >= fireDelay) {
                    fireCounter = 0;
                    fire(0);
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    if (BulletStatus.INSTANCE.getPlayerBulletStatus() == 1) {
//            if (bulletStatus ==1){
                fireCounter++;
                if (fireCounter >= fireDelay) {
                    fireCounter = 0;
                    fire(1);
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    if (BulletStatus.INSTANCE.getPlayerBulletStatus() == 1) {
//            if (bulletStatus ==1){
                fireCounter++;
                if (fireCounter >= fireDelay) {
                    fireCounter = 0;
                    fire(2);
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    if (BulletStatus.INSTANCE.getPlayerBulletStatus() == 1) {
//            if (bulletStatus ==1){
                fireCounter++;
                if (fireCounter >= fireDelay) {
                    fireCounter = 0;
                    fire(3);
                }
            }
        }




        hitBoxBomber.setPosition(position);
    }

    public void setupBomb() {
        if (!gs.getBombEmitter().isBombInCell(getCellX(), getCellY()) && gs.getMap().isCellEmpty(getCellX(), getCellY())) {
            gs.getBombEmitter().getActiveElement().activate(this, getCellX(), getCellY(), 2.5f, bombPower);
        }
    }

    public void addScore(int amount) {
        score += amount;
    }

//    public void reduceScore(int amount) {
//        score -=amount;
//    }

    public int getScore() {
        return score;
    }

    public void setScore(int scoreToSet) {
        score = scoreToSet;
    }

    public int getCostHP() {
        return costHP;
    }

    public int getCostRAD() {
        return costRAD;
    }



    public void hitPlayerSoundPlay() {
        if (isVulnerable()) {
            hitPlayerSound.play();
        }
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void fire(int fireDirection) {
//d        gs.getBulletEmitter().getActiveElement().init(this, fireDirection);
        if (gs.getBulletEmitter().getActiveList().size() < gs.getBulletEmitter().getMaxBulletCount()) {
            gs.getBulletEmitter().getActiveElement().init(this, fireDirection);
//            gs.getBulletEmitter().getActiveElement().init(fireDirection);
            if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
                fireSound.play();
            }
        }
    }


}
