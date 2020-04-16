package com.geekbrains.game;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen implements Screen {
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private BitmapFont font48;
    private BitmapFont font48s;
    private BitmapFont font96;
//    transient Music musicMenu;
    private Music musicMenu;
    private Texture background = new Texture(Gdx.files.internal("menuscreen.png"));

    public MenuScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    private boolean hover;

    // Check music state
    public void checkMusic() {
        if (MusicStatus.INSTANCE.getMusicStatus() == 0) {
            musicMenu.pause();
        }
        if (MusicStatus.INSTANCE.getMusicStatus() == 1) {
            musicMenu.play();
        }
    }


    @Override
    public void show() {
        font48 = Assets.getInstance().getAssetManager().get("gomarice48.ttf", BitmapFont.class);
        font48s = Assets.getInstance().getAssetManager().get("gomarice48s.ttf", BitmapFont.class);
        font96 = Assets.getInstance().getAssetManager().get("gomarice96.ttf", BitmapFont.class);

        createGUI();

        //add Music to game in MenuScreen
        musicMenu = Gdx.audio.newMusic(Gdx.files.internal("musicmenu.mp3"));
        musicMenu.setVolume(0.5f);
        musicMenu.setLooping(true);
        MusicStatus.INSTANCE.loadMusicState();
        MusicStatus.INSTANCE.loadSoundState();
        checkMusic();
    }

    @Override
    public void render(float delta) {
        update(delta);
 //       Gdx.gl.glClearColor(0.4f, 0.8f, 1.0f, 1.0f);
 //       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        font96.draw(batch, "Bomber Bobber", 0, 495, 1280, 1, false);
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
        skin.add("font48s", font48s);


        final TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.up = skin.getDrawable("simpleButton");
        textButtonStyle.up = skin.getDrawable("transparentButton2");
        textButtonStyle.font = font48;
        skin.add("simpleSkin", textButtonStyle);

        // second textButtonStyle ***************************
        final TextButton.TextButtonStyle textButtonStyle2 = new TextButton.TextButtonStyle();
        textButtonStyle2.up = skin.getDrawable("transparentButton2");
        textButtonStyle2.down = skin.getDrawable("simpleButton");
        textButtonStyle2.font = font48s;
        skin.add("font48s", textButtonStyle2);

        //*******************************************

 /**
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("transparentButton2");
        textButtonStyle.checked = skin.getDrawable("transparentButton2"); //new
        textButtonStyle.down = skin.getDrawable("simpleButton");
 //       textButtonStyle.checkedOverFontColor = Color.BLUE;
        textButtonStyle.font = font48;

//        skin.add("simpleSkin", textButtonStyle);

//RED COLOR CLASS (NOT USED)
        /**
//        RedColorOnMouseOverButton btnNewGame2 = new RedColorOnMouseOverButton("Start New Game", skin, "transparentButton");
        RedColorOnMouseOverButton btnNewGame2 = new RedColorOnMouseOverButton("Start New Game", skin, "simpleSkin");
        btnNewGame2.setPosition(640 - 160, 310);
        stage.addActor(btnNewGame2);

         */
        // change highlighted text color

//        private boolean hover;

//        btnNewGame.addListener(new InputListener() {
        /*
        btnNewGame2.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    hover = true;
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    hover = false;
                }
            });

*/

        final Button btnNewGame = new TextButton("Start New Game", skin, "simpleSkin");
        btnNewGame.setPosition(640 - 160, 310);
        stage.addActor(btnNewGame);

        final Button btnOptions = new TextButton("Options", skin, "simpleSkin");
        btnOptions.setPosition(640 - 160, 230);
        stage.addActor(btnOptions);

        final Button btnRules = new TextButton("Help", skin, "simpleSkin");
        btnRules.setPosition(640 - 160, 150);
        stage.addActor(btnRules);


        final Button btnExitGame = new TextButton("Exit Game", skin, "simpleSkin");
        btnExitGame.setPosition(640 - 160, 70);
        stage.addActor(btnExitGame);



        btnNewGame.addListener(new ChangeListener() {
 //         btnNewGame2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
            }
        });
        btnOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setPreviousScreenType(ScreenManager.ScreenType.MENU);  // try to remember ScreenType
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.OPTIONS);
            }
        });


        btnRules.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setPreviousScreenType(ScreenManager.ScreenType.MENU);  // try to remember ScreenType
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.RULES);
            }
        });


        btnExitGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        //change Color of button when mouse enters NEWGAME
        btnNewGame.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnNewGame).setStyle(textButtonStyle2);
            }
        });
        //change Color of button when mouse exits
        btnNewGame.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnNewGame).setStyle(textButtonStyle);
            }
        });

        //change Color of button when mouse enters OPTIONS-BUTTON
        btnOptions.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnOptions).setStyle(textButtonStyle2);
            }
        });
        //change Color of button when mouse exits
        btnOptions.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnOptions).setStyle(textButtonStyle);
            }
        });


        //change Color of button when mouse enters KEYS-BUTTON
        btnRules.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnRules).setStyle(textButtonStyle2);
            }
        });
        //change Color of button when mouse exits
        btnRules.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnRules).setStyle(textButtonStyle);
            }
        });


        //change Color of button when mouse enters EXIT-BUTTON
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
        musicMenu.stop();
    }
}
