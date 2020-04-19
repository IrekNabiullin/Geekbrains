package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Map map;
    private Bomberman player;
    private Bot bot;
    private AnimationEmitter animationEmitter;
    private BombEmitter bombEmitter;
    private BotEmitter botEmitter;
    private BitmapFont font32;
    Music musicGame;

//    private Sound blowSound;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    public Map getMap() {
        return map;
    }

    public BotEmitter getBotEmitter() {
        return botEmitter;
    }

    public BombEmitter getBombEmitter() {
        return bombEmitter;
    }

    public AnimationEmitter getAnimationEmitter() {
        return animationEmitter;
    }

    @Override
    public void show() {
        map = new Map();
        animationEmitter = new AnimationEmitter();
        bombEmitter = new BombEmitter(this);
        player = new Bomberman(this);
        botEmitter = new BotEmitter(this);
        bot = new Bot(this);
        font32 = Assets.getInstance().getAssetManager().get("gomarice32.ttf", BitmapFont.class);

        musicGame = Gdx.audio.newMusic(Gdx.files.internal("musicgame.mp3"));
        musicGame.setVolume(0.1f);
        musicGame.play();
//        blowSound = Gdx.audio.newSound(Gdx.files.internal("blow2.wav"));
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        map.render(batch);
        player.render(batch);
        bot.render(batch);
        bombEmitter.render(batch);
        botEmitter.render(batch);
        animationEmitter.render(batch);
        player.renderGUI(batch, font32);
        batch.end();
    }

    public void update(float dt) {
        map.update(dt);
        player.update(dt);
        bot.update(dt);
        bombEmitter.update(dt);
        botEmitter.update(dt);
        animationEmitter.update(dt);

 //       if (Gdx.input.justTouched()) {
 //           animationEmitter.createAnimation(Gdx.input.getX(), 720 - Gdx.input.getY(), MathUtils.random(1.0f, 10.0f), AnimationEmitter.AnimationType.EXPLOSION);
 //       }
    }

    @Override
    public void dispose() {
        batch.dispose();
        musicGame.stop();
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
