package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Map map;
    private Bomberman player;
    private BotEmitter botEmitter;
    private AnimationEmitter animationEmitter;
    private BombEmitter bombEmitter;
    private BitmapFont font32;
    private Camera camera;

    /*
    1. Разбор кода
    2. Точка перехода на новый уровень, под случайной коробкой
    3. Переход на другую карту
     */

    public GameScreen(SpriteBatch batch, Camera camera) {
        this.batch = batch;
        this.camera = camera;
    }

    public Map getMap() {
        return map;
    }

    public BombEmitter getBombEmitter() {
        return bombEmitter;
    }

    public AnimationEmitter getAnimationEmitter() {
        return animationEmitter;
    }

    public BotEmitter getBotEmitter() {
        return botEmitter;
    }

    @Override
    public void show() {
        map = new Map();
        animationEmitter = new AnimationEmitter();
        bombEmitter = new BombEmitter(this);
        botEmitter = new BotEmitter(this);
        player = new Bomberman(this);
        font32 = Assets.getInstance().getAssetManager().get("gomarice32.ttf", BitmapFont.class);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        map.render(batch);
        player.render(batch);
        botEmitter.render(batch);
        bombEmitter.render(batch);
        animationEmitter.render(batch);
        ScreenManager.getInstance().resetCamera();
        player.renderGUI(batch, font32);
        batch.end();
    }

    public void update(float dt) {
        player.update(dt);
        cameraUpdate();
        botEmitter.update(dt);
        bombEmitter.update(dt);
        animationEmitter.update(dt);
        if (Gdx.input.justTouched()) {
            animationEmitter.createAnimation(Gdx.input.getX(), 720 - Gdx.input.getY(), MathUtils.random(1.0f, 10.0f), AnimationEmitter.AnimationType.EXPLOSION);
        }
    }

    public void cameraUpdate() {
        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        if(camera.position.x < 640) {
            camera.position.x = 640;
        }
        if(camera.position.y < 360) {
            camera.position.y = 360;
        }
        if(camera.position.x > map.getMapX() * Rules.CELL_SIZE - 640) {
            camera.position.x = map.getMapX() * Rules.CELL_SIZE - 640;
        }
        if(camera.position.y > map.getMapY() * Rules.CELL_SIZE - 360) {
            camera.position.y = map.getMapY() * Rules.CELL_SIZE - 360;
        }
        // camera.rotate(new Vector3(0,0,1),0.05f);
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
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
}
