package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by FlameXander on 12.03.2018.
 */

public class LoadingScreen implements Screen {
    private SpriteBatch batch;
    private Texture texture;
    private Texture texture2;
    private Texture texture3;
    private BitmapFont font96;
    private Texture background = new Texture(Gdx.files.internal("loadingscreen.png"));


    public LoadingScreen(SpriteBatch batch) {
        this.batch = batch;
        Pixmap pixmap = new Pixmap(600, 40, Pixmap.Format.RGB888);
        Pixmap pixmap2 = new Pixmap(640, 60, Pixmap.Format.RGB888);
        Pixmap pixmap3 = new Pixmap(600, 40, Pixmap.Format.RGB888);
        pixmap.setColor(Color.BLUE);
        pixmap2.setColor(Color.BROWN);
        pixmap3.setColor(Color.GRAY);
        pixmap2.fill();
        pixmap3.fill();
        pixmap.fill();
        this.texture = new Texture(pixmap);
        this.texture2 = new Texture(pixmap2);
        this.texture3 = new Texture(pixmap3);
        pixmap.dispose();
        pixmap2.dispose();
        pixmap3.dispose();
    }

    @Override
    public void show() {
 //       font96 = Assets.getInstance().getAssetManager().get("gomarice96.ttf", BitmapFont.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Assets.getInstance().getAssetManager().update()) {
            Assets.getInstance().makeLinks();
            ScreenManager.getInstance().goToTarget();
        }
        batch.begin();
 //       font96.draw(batch, "Loading...", 300, 500, 1280, 1, false);
        batch.draw(background, 320, 140);
        batch.draw(texture2, 300, 200, 670, 60);
        batch.draw(texture3, 310, 210, 650, 40);
        if(Assets.getInstance().getAssetManager().getProgress() < 0.65) {
            batch.draw(texture, 310, 210, 650 * Assets.getInstance().getAssetManager().getProgress(), 40);
        } else{
            batch.draw(texture, 310, 210, 650 , 40);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        ScreenManager.getInstance().resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
