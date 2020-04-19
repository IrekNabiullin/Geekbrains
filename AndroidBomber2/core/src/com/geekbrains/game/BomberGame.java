package com.geekbrains.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BomberGame extends Game {
    private SpriteBatch batch;

    // Домашнее задание
    // 1. Разбор кода
    // 2. Реализовать пачку ботов, пусть каждые 10 секунд
    // в случайном месте карты появляется один новый,
    // но не более 10 одновременно
    // 3. Уничтожение ботов взрывом

    @Override
    public void create() {
        batch = new SpriteBatch();
        ScreenManager.getInstance().init(this, batch);
        ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        getScreen().render(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
