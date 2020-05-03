package com.geekbrains.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BulletOfBotEmitter extends ObjectPool<BulletOfBot> implements Serializable {
    private transient GameScreen gs;
    private int maxBulletsCount;
    private float spawnTimer;
    private List<BulletOfBot> tmpList;
    private Rectangle tmpRect;
    private Bot owner; //for Bullet Of Bots

    @Override
    protected BulletOfBot newObject() {
//        int a = 9/0;
//        System.out.println(a);
//        System.out.println("BBE. ActiveList: " + activeList.hashCode() + " size:" + activeList.size());
//        if (activeList.size() < maxBulletsCount) {
//            System.out.println("II.Bullet:" + this.hashCode() + " Owner: " + owner.hashCode());
//            return new BulletOfBot(gs, owner);
//        }
        checkPool();
        return new BulletOfBot(gs, owner);
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

    public BulletOfBotEmitter(GameScreen gs, Bot owner) {
        this.gs = gs;
        this.maxBulletsCount = 1;  // maximum bullets on the game screen
        this.tmpList = new ArrayList<BulletOfBot>();
        this.tmpRect = new Rectangle(0, 0, Rules.CELL_SIZE - 10, Rules.CELL_SIZE - 10);
        this.owner = owner; // for Bullet Of Bots
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
 //           System.out.println("BulletOfBotEmitter. Render. activeList.size:" + activeList.size());
            activeList.get(i).render(batch);
 //           System.out.println ("BulletOfBotEmitter:" + thisHashCode() + " owner:" + owner.hashCode() + " render active element " + activeList.get(i).hashCode());
        }
        checkPool();
    }

    public List<BulletOfBot> getBulletsOfBot() {
        tmpList.clear();
        for (int i = 0; i < activeList.size(); i++) {
            if (activeList.get(i).isActive()) {
                tmpList.add(activeList.get(i));
            }
        }
        return tmpList;
    }

    public void update(float dt) {

        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
//            System.out.print ("I. Active list.get(i):" + this.activeList.get(i).hashCode());
        }
        checkPool();
    }

    public boolean hasBotABullit(int cellX, int cellY) {
        for (int i = 0; i <  activeList.size(); i++) {
            BulletOfBot bullet = activeList.get(i);
            if (bullet.getCellX() == cellX && bullet.getCellY() == cellY) {
                checkPool();
                return true;
            }
        }
        checkPool();
        return false;
    }

    public int getMaxBulletCount() {
        return maxBulletsCount;
    }

    public int thisHashCode(){
        return this.hashCode();
    }

}
