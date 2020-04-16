package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
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

public class OptionsScreen implements Screen {
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private BitmapFont font32;
    private BitmapFont font96;
    private BitmapFont font48s;
    private transient Music musicMenu;
    private transient Sound soundMenu;
    private Texture background = new Texture(Gdx.files.internal("optionsscreen.png"));
    private int musicOnOff;
    private int soundOnOff = 1;

//    private Bomberman player;

    public OptionsScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    // Check music state
    public void checkMusic() {
        if (MusicStatus.INSTANCE.getMusicStatus() == 0) {
            musicOnOff = 0;
            musicMenu.pause();
        }
        if (MusicStatus.INSTANCE.getMusicStatus() == 1) {
            musicOnOff = 1;
            musicMenu.play();
        }
    }

    // Check sound state
    public void checkTheSound() {
        if (MusicStatus.INSTANCE.getSoundStatus() == 0) {
            soundOnOff = 0;
        }
        if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
            soundOnOff = 1;
        }
    }

    public String textForMusicButton() {
        if (MusicStatus.INSTANCE.getMusicStatus() == 0) {
            return "Music is OFF";
        }
        if (MusicStatus.INSTANCE.getMusicStatus() == 1) {
            return "Music is ON";
        }
        return "Music is ON";
    }

    public String textForSoundButton() {
        if (MusicStatus.INSTANCE.getSoundStatus() == 0) {
            return "Sound is OFF";
        }
        if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
            return "Sound is ON";
        }
        return "Sound is ON";
    }

    @Override
    public void show() {
        font32 = Assets.getInstance().getAssetManager().get("gomarice32.ttf", BitmapFont.class);
        font96 = Assets.getInstance().getAssetManager().get("gomarice96.ttf", BitmapFont.class);
        font48s = Assets.getInstance().getAssetManager().get("gomarice48s.ttf", BitmapFont.class);  // add font to highlight selected text
        createGUI();
        musicMenu = Gdx.audio.newMusic(Gdx.files.internal("musicoptions.mp3"));
        soundMenu = Gdx.audio.newSound(Gdx.files.internal("bombSet.mp3"));
        musicMenu.setVolume(0.5f);
        musicMenu.setLooping(true);

        // Check music state
        checkMusic();
        checkTheSound();
    }

    @Override
    public void render(float delta) {
        update(delta);
//        Gdx.gl.glClearColor(0.4f, 0.8f, 1.0f, 1.0f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        font96.draw(batch, "Options", 300, 590, 1280, 1, false);
        batch.end();
        stage.draw();
    }

    public void update(float dt) {
        stage.act(dt);
    }

    public void createGUI() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());
        skin.add("font32", font32);

        //first TextButtonStyle

        final TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.up = skin.getDrawable("simpleButton");
        textButtonStyle.up = skin.getDrawable("transparentButton2");
//        textButtonStyle.down = skin.getDrawable("transparentButton");
//        textButtonStyle.checked = skin.getDrawable("transparentButton");
        textButtonStyle.font = font32;
        skin.add("simpleSkin", textButtonStyle);

        // second textButtonStyle ***************************
        final TextButton.TextButtonStyle textButtonStyle2 = new TextButton.TextButtonStyle();
        textButtonStyle2.up = skin.getDrawable("transparentButton2");
//        textButtonStyle2.up = skin.getDrawable("transparentButton2");
        textButtonStyle2.down = skin.getDrawable("simpleButton");
//        textButtonStyle.checked = skin.getDrawable("transparentButton");
        textButtonStyle2.font = font48s;
        skin.add("font48s", textButtonStyle2);

        //*******************************************

        final Button btnContinueGame = new TextButton("Go Back", skin, "simpleSkin");
//        final Button btnSoundOnOff = new TextButton("Sound is ON", skin, "simpleSkin");
        final Button btnSoundOnOff = new TextButton(textForSoundButton(), skin, "simpleSkin");
//        final Button btnMusicOnOff = new TextButton("Music is ON", skin, "simpleSkin");
        final Button btnMusicOnOff = new TextButton(textForMusicButton(), skin, "simpleSkin");
        final Button btnExitGame = new TextButton("Exit Game", skin, "simpleSkin");
        btnContinueGame.setPosition(780 , 380);
        btnMusicOnOff.setPosition(780, 280);
        btnSoundOnOff.setPosition(780, 180);
        btnExitGame.setPosition(780, 80);
        stage.addActor(btnContinueGame);

        stage.addActor(btnMusicOnOff);
        stage.addActor(btnSoundOnOff);
        stage.addActor(btnExitGame);

        btnContinueGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
 //               ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
                ScreenManager.getInstance().changeScreen(ScreenManager.getInstance().getPreviousScreenType());  // try to remember ScreenType
            }
        });

        btnMusicOnOff.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (musicOnOff == 0) {
                musicMenu.play();
                    ((TextButton) (btnMusicOnOff)).setText("Music is ON");
                    musicOnOff = 1;
                    MusicStatus.INSTANCE.setMusicState(1);
                } else {
                    musicMenu.pause();
                    ((TextButton) (btnMusicOnOff)).setText("Music is OFF");
                    musicOnOff = 0;
                    MusicStatus.INSTANCE.setMusicState(0);
                }

            }
        });

        btnSoundOnOff.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                soundOnOff = -1 * soundOnOff ;
//                if (soundOnOff > 0) {
                if(soundOnOff == 0){
                    soundMenu.setVolume(1, 1.0f);
                    soundMenu.play();
                    soundMenu.setVolume(1, 0.5f);
                    ((TextButton) (btnSoundOnOff)).setText("Sound is ON");
                    soundOnOff = 1;
                    MusicStatus.INSTANCE.setSoundState(1);
                } else {
                    ((TextButton) (btnSoundOnOff)).setText("Sound is OFF");
                    soundMenu.setVolume(1, 1.0f);
                    soundMenu.play();
                    soundMenu.setVolume(1, 0.5f);
                    soundOnOff = 0;
                    MusicStatus.INSTANCE.setSoundState(0);
                }

            }
        });


/**
  //**************BUTTON COLOR CHANGE*****************
        //change Color of button when mouse enters
        btnMusicOn.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                ((TextButton) btnMusicOn).setColor(Color.YELLOW);


            }
        });
        //change Color of button when mouse exits
        btnMusicOn.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                ((TextButton) btnMusicOn).setColor(Color.BLACK);
            }
        });
//***************************************************
*/

        //**************BUTTON TEXT COLOR CHANGE*****************

        //change Color of button when mouse enters MUSIC
        btnMusicOnOff.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnMusicOnOff).setStyle(textButtonStyle2);
            }
        });
        //change Color of button when mouse exits
        btnMusicOnOff.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnMusicOnOff).setStyle(textButtonStyle);
            }
        });


        //change Color of button when mouse enters SOUND
        btnSoundOnOff.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnSoundOnOff).setStyle(textButtonStyle2);
            }
        });
        //change Color of button when mouse exits
        btnSoundOnOff.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnSoundOnOff).setStyle(textButtonStyle);
            }
        });

        //change Color of button when mouse enters CONTINUE GAME
        btnContinueGame.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnContinueGame).setStyle(textButtonStyle2);
            }
        });
        //change Color of button when mouse exits
        btnContinueGame.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (btnContinueGame).setStyle(textButtonStyle);
            }
        });

        //change Color of button when mouse enters EXIT GAME
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

//***************************************************

        /**
        btnMusicOff.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicOnOff = -1 ;
                musicMenu.pause();
//                musicMenu.play();
            }
        });

        btnSoundOn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundOnOff = true;

                //        setMusicState(true);
            }
        });
        btnSoundOff.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
 //               if (soundOnOff == false) {
                    soundOnOff = true;
 //               } else soundOnOff = false;
            }
        });
*/
        btnExitGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.EXIT);
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


