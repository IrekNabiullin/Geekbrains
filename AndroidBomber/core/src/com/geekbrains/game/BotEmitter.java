package com.geekbrains.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FlameXander on 01.06.2018.
 */

public class BotEmitter extends ObjectPool<Bot> implements Serializable {
    private transient GameScreen gs;
    private int maxBotsCount;
    private float spawnTimer;
    private List<Bot> tmpList;
    private Rectangle tmpRect;

    @Override
    protected Bot newObject() {
        return new Bot(gs);
    }

    public void reloadResources(GameScreen gs) {
        this.gs = gs;
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).reloadResources(gs);
        }
        for (int i = 0; i < freeList.size(); i++) {
            freeList.get(i).reloadResources(gs);
        }
    }

    public BotEmitter(GameScreen gs) {
        this.gs = gs;
//        System.out.println("BotEmitter.BotEmitter.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace to debug
//        System.out.println("GameLevel.INSTANCE.getGameLevel()" + GameLevel.INSTANCE.getGameLevel() );

        if (GameLevel.INSTANCE.getGameLevel() == 15) {
//            System.out.println("BotEmitter.BotEmitter.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace to debug);
//            System.out.println(" getGameLevel = " + GameLevel.INSTANCE.getGameLevel());
            this.maxBotsCount = 100;
//            System.out.println("BotEmitter.BotEmitter.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace to debug);
//            System.out.println("this.maxBotCount = " + this.maxBotsCount);
        } else {
            this.maxBotsCount = 4;
        }
        this.tmpList = new ArrayList<Bot>();
        this.tmpRect = new Rectangle(0, 0, Rules.CELL_SIZE - 10, Rules.CELL_SIZE - 10);
        System.out.println("BotEmitter.BotEmitter.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace to debug);
        System.out.println("maxBotCount = " + maxBotsCount);
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
        checkPool();
    }

    public List<Bot> getBotsInCell(int cellX, int cellY) {
        tmpList.clear();
        tmpRect.x = cellX * Rules.CELL_SIZE + 5;
        tmpRect.y = cellY * Rules.CELL_SIZE + 5;
        for (int i = 0; i < activeList.size(); i++) {
            if (tmpRect.overlaps(activeList.get(i).getHitBox())) {
                tmpList.add(activeList.get(i));
            }
            checkPool();
        }
        return tmpList;
    }

    public void update(float dt) {
        // for level 15 max bot size =100
        if (GameLevel.INSTANCE.getGameLevel() == 15) {
            this.maxBotsCount = 50;
        } else {
            this.maxBotsCount = 4;
        }

        if (activeList.size() < maxBotsCount) {
//            System.out.println("BotEmitter.update.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace to debug
//            System.out.println("maxBotsCount = " + maxBotsCount);
            if (GameLevel.INSTANCE.getGameLevel()<15) {  //line added for quick bot init at last level 15
                spawnTimer += dt;
                if (spawnTimer > 2.0f) {
                    spawnTimer = 0.0f;
                    getActiveElement().init();
//                System.out.println("BotEmitter.getActiveElement().hashCode() " + getActiveElement().hashCode()); //info for debugging
                }
            } else {                                    //line added for quick bot init at last level 15
                getActiveElement().init();
            }                                           //line added for quick bot init at last level 15
            checkPool();
        }
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
            activeList.get(i).getBulletOfBotEmitter().update(dt);    // Bullet of Bots
//            activeList.get(i).getBulletOfBotEmitter().checkPool();
        }
        checkPool();
    }
    /**
     * Created by IrekNabiullin on 09.05.2019.
     */

    public boolean isBotInCell(int cellX, int cellY) {
        for (int i = 0; i < activeList.size(); i++) {
            Bot botty = activeList.get(i);
            if (botty.getCellX() == cellX && botty.getCellY() == cellY) {
//                System.out.println("BotEmitter. There is Botty " + botty.hashCode() + " in cell X:" + cellX + " Y:" + cellY);
                checkPool();
                return true;
            }
            checkPool();
        }
        return false;
    }

    public void tryToShoutBot(int cellX, int cellY) {
        for (int i = 0; i < activeList.size(); i++) {
            Bot botty = activeList.get(i);
            if (botty.getCellX() == cellX && botty.getCellY() == cellY) {
                botty.destroy();
//try to animate Bot destroy when shout by bulletOfBot
                gs.getAnimationEmitter().createAnimation(botty.getPosition().x, botty.getPosition().y, 2.0f, AnimationEmitter.AnimationType.BLOOD);

                botty.hitBotSoundPlay();
                checkPool();
                return;
            }
            checkPool();
        }
    }



}
