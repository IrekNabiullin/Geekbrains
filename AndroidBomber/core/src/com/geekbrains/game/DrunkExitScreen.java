package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


/**
 * Created by Irek Nabiullin on 05.04.2020.
 */

public class DrunkExitScreen implements Screen {
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private BitmapFont font48;
    private BitmapFont font48s;
    private BitmapFont font96;
    transient Music musicDrunkExit;
    private Texture background = new Texture(Gdx.files.internal("screendrunk.png"));

    // Check music state
    public void checkMusic() {
        if (MusicStatus.INSTANCE.getMusicStatus() == 0) {
            musicDrunkExit.pause();
        }
        if (MusicStatus.INSTANCE.getMusicStatus() == 1) {
            musicDrunkExit.play();
        }
    }

    public DrunkExitScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        font48 = Assets.getInstance().getAssetManager().get("gomarice48.ttf", BitmapFont.class);
        font96 = Assets.getInstance().getAssetManager().get("gomarice96.ttf", BitmapFont.class);
        font48s = Assets.getInstance().getAssetManager().get("gomarice48s.ttf", BitmapFont.class);  // add font to highlight selected text
        createGUI();
//        musicExit = Gdx.audio.newMusic(Gdx.files.internal("musicexit.mp3"));
        musicDrunkExit = Gdx.audio.newMusic(Gdx.files.internal("naulitselenina.mp3"));
        musicDrunkExit.setVolume(0.5f);
        musicDrunkExit.setLooping(true);
        musicDrunkExit.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
//        Gdx.gl.glClearColor(0.4f, 0.4f, 1.0f, 1.0f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        font96.draw(batch, "Game Over", 0, 660, 1280, 1, false);
        batch.end();
        stage.draw();
    }

    public void update(float dt) {
        stage.act(dt);
        checkMusic();
    }

    public void createGUI() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());
        skin.add("font48", font48);

        final TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("transparentButton2");
        textButtonStyle.font = font48;
        skin.add("simpleSkin", textButtonStyle);

        // second textButtonStyle ***************************
        final TextButton.TextButtonStyle textButtonStyle2 = new TextButton.TextButtonStyle();
        textButtonStyle2.up = skin.getDrawable("transparentButton2");
        textButtonStyle2.up = skin.getDrawable("transparentButton2");
//        textButtonStyle2.down = skin.getDrawable("simpleButton");
//        textButtonStyle.checked = skin.getDrawable("transparentButton");
        textButtonStyle2.font = font48s;
        skin.add("font48s", textButtonStyle2);

        //*******************************************

        final Button btnRestartGame = new TextButton("Restart", skin, "simpleSkin");
        final Button btnExitGame = new TextButton("Exit Game", skin, "simpleSkin");
        btnRestartGame.setPosition(640 - 0, 130);
        btnExitGame.setPosition(640 - 360, 130);
        stage.addActor(btnRestartGame);
        stage.addActor(btnExitGame);

        btnRestartGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameLevel.INSTANCE.setGameLevel(1);
                BulletStatus.INSTANCE.setPlayerBulletStatus(0); //switch off player's bullet on restart
                HpCount.INSTANCE.setHpCount(3);
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
            }
        });
        btnExitGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });


        //**************BUTTON TEXT COLOR CHANGE*****************

        //change Color of button when mouse enters NO-button
        btnRestartGame.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnRestartGame).setStyle(textButtonStyle2);
            }
        });
        //change Color of button when mouse exits
        btnRestartGame.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnRestartGame).setStyle(textButtonStyle);
            }
        });

        //change Color of button when mouse enters NO-button
        btnExitGame.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnExitGame).setStyle(textButtonStyle2);
            }
        });
        //change Color of button when mouse exits
        btnExitGame.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnExitGame).setStyle(textButtonStyle);
            }
        });

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
        musicDrunkExit.stop();
    }
}
