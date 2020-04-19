package com.geekbrains.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class BotEmitter extends ObjectPool<Bot>  {
    private GameScreen gs;
    private TextureRegion textureRegion;

    @Override
    protected Bot newObject() {
        return new Bot(gs);
    }

    public BotEmitter(GameScreen gs) {
        this.gs = gs;
        this.textureRegion = Assets.getInstance().getAtlas().findRegion("bot");
        this.addObjectsToFreeList(10);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
    }

    public boolean isBotInCell(int cellX, int cellY) {
        for (int i = 0; i < activeList.size(); i++) {
            Bot bot = activeList.get(i);
            if (bot.getCellX() == cellX && bot.getCellY() == cellY) {
                return true;
            }
        }
        return false;
    }

    public void activateBot(int cellX, int cellY) {
        for (int i = 0; i < activeList.size(); i++) {
            Bot bot = activeList.get(i);
            bot.activate(cellX,cellY);
                return;
            }
        }



}

