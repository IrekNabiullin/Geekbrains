package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
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
 * Created by Irek Nabiullin on 09.06.2018.
 */

public class ExitScreen implements Screen {
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private BitmapFont font48;
    private BitmapFont font48s;
    private BitmapFont font96;
    transient Music musicExit;
    private Texture background = new Texture(Gdx.files.internal("exitscreen.png"));

    // Check music state
    public void checkMusic() {
        if (MusicStatus.INSTANCE.getMusicStatus() == 0) {
            musicExit.pause();
        }
        if (MusicStatus.INSTANCE.getMusicStatus() == 1) {
            musicExit.play();
        }
    }

    public ExitScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        font48 = Assets.getInstance().getAssetManager().get("gomarice48.ttf", BitmapFont.class);
        font96 = Assets.getInstance().getAssetManager().get("gomarice96.ttf", BitmapFont.class);
        font48s = Assets.getInstance().getAssetManager().get("gomarice48s.ttf", BitmapFont.class);  // add font to highlight selected text
        createGUI();
//        musicExit = Gdx.audio.newMusic(Gdx.files.internal("musicexit.mp3"));
        musicExit = Gdx.audio.newMusic(Gdx.files.internal("exitmenumusic.mp3"));
        musicExit.setVolume(0.5f);
        musicExit.setLooping(true);
        musicExit.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
//        Gdx.gl.glClearColor(0.4f, 0.4f, 1.0f, 1.0f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        font96.draw(batch, "Exit Game?", 0, 350, 1280, 1, false);
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

        final Button btnNoExitGame = new TextButton("No", skin, "simpleSkin");
        final Button btnYesExitGame = new TextButton("Yes", skin, "simpleSkin");
        btnNoExitGame.setPosition(640 - 60, 150);
        btnYesExitGame.setPosition(640 - 300, 150);
        stage.addActor(btnNoExitGame);
        stage.addActor(btnYesExitGame);

        btnNoExitGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("ExitScreen row 110. HpCount = " + HpCount.INSTANCE.getHpCount());
                if(HpCount.INSTANCE.getHpCount() == 0) {
                    HpCount.INSTANCE.setHpCount(3);
                    ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
                } else {
                    System.out.println("Exit? No! HpCount = " + HpCount.INSTANCE.getHpCount());
                    ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
                }
            }
        });
        btnYesExitGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });


        //**************BUTTON TEXT COLOR CHANGE*****************

        //change Color of button when mouse enters NO-button
        btnNoExitGame.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnNoExitGame).setStyle(textButtonStyle2);
            }
        });
        //change Color of button when mouse exits
        btnNoExitGame.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnNoExitGame).setStyle(textButtonStyle);
            }
        });

        //change Color of button when mouse enters NO-button
        btnYesExitGame.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnYesExitGame).setStyle(textButtonStyle2);
            }
        });
        //change Color of button when mouse exits
        btnYesExitGame.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnYesExitGame).setStyle(textButtonStyle);
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
            musicExit.stop();
    }
}
