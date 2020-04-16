package com.geekbrains.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.lwjgl.Sys;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.geekbrains.game.Map.ArtefactStatus.LAYDOWN;
import static com.geekbrains.game.Map.ArtefactStatus.PICKEDUP;
import static com.geekbrains.game.Map.KeyStatus.KEYFOUND;

public class GameScreen implements Screen {
    private enum Status {
        PLAY, PAUSE, SHOP, CHOOSE, INFO, INFO1, INFO2, INFO3, INFO4, INFO5, SAVING, SAVED, LOADING, GAMEOVER, QUIT;
    }


    private SpriteBatch batch;
    private Map map;
    private Bomberman player;
    private BotEmitter botEmitter;
    private BulletEmitter bulletEmitter;
//    private BulletOfBotEmitter bulletOfBotEmitter;
    private AnimationEmitter animationEmitter;
    private BombEmitter bombEmitter;
    private BitmapFont guiFont;
    private Camera camera;
    private Stage stage;
    private Skin skin;
    private int level = 1;  //starting level
    private boolean continueSavedGame = false; //trigger for load/begin new game
    private int playerBulletStatus ;
    private int botBulletStatus;
    private int maxlevel = 15;   // maximal number of game levels
    private int costHp = 10000; // cost of HP upgrade
    private int hp = 3;  // Health Points of Bomberman
    private int costRadius = 50000; // cost of Bomb radius upgrade
    private Group upgradeGroup, chooseGroup, infoGroup, infoGroup1, infoGroup2, infoGroup3, infoGroup4, infoGroup5, infoGroup6, savingGroup, loadingGroup, quitGroup, pauseGroup, gameOverGroup;
    protected Status currentStatus;
    transient Music musicGame;
    private transient Sound upgradeSound;



    public GameScreen(SpriteBatch batch, Camera camera) { //add level number
        this.batch = batch;
        this.camera = camera;
    }

    public Map getMap() {
        return map;
    }


    public BombEmitter getBombEmitter() {
        return bombEmitter;
    }

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }

//    public BulletOfBotEmitter getBulletOfBotEmitter() {
//        return bulletOfBotEmitter;
//    }                                                   // Bullet of Bots

    public AnimationEmitter getAnimationEmitter() {
        return animationEmitter;
    }

    public Bomberman getPlayer() {
        return player;
    }

    public BotEmitter getBotEmitter() { return botEmitter; }


    public Status checkStatus() {
        if (player.getCurrentState() == 2) {
            return currentStatus = Status.GAMEOVER;
        }
        return this.currentStatus;
    }

    // Check music state
    public void checkMusic() {
        if (MusicStatus.INSTANCE.getMusicStatus() == 0) {
            musicGame.pause();
        }
        if (MusicStatus.INSTANCE.getMusicStatus() == 1) {
            musicGame.play();
        }
    }


    // Check player bullet state
    public void checkPlayerBulletState() {
        if (BulletStatus.INSTANCE.getPlayerBulletStatus() == 0) {
            playerBulletStatus = 0;
        }
        if (BulletStatus.INSTANCE.getPlayerBulletStatus() == 1) {
            playerBulletStatus = 1;
        }
//        System.out.println("GameScreen.checkPlayerBulletState. Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " ");
//        System.out.println("PlayerBulletStatus = " + playerBulletStatus);
    }

    // Check bot bullet state
    public void checkBotBulletState() {
        if (BulletStatus.INSTANCE.getBotBulletStatus() == 0) {
            botBulletStatus = 0;
        }
        if (BulletStatus.INSTANCE.getBotBulletStatus()  == 1) {
            botBulletStatus = 1;
        }
//        System.out.println("GameScreen.checkBotBulletState. Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " ");
//        System.out.println("BotBulletStatus = " + botBulletStatus);
    }



    // Check temporary saved game state
    public void checkTempSavedGame() {
        if (TempSavedGameStatus.INSTANCE.getTempSavedGameState() == 0) {
            continueSavedGame = false;
        } else if (TempSavedGameStatus.INSTANCE.getTempSavedGameState() == 1) {
            continueSavedGame = true;
        }
    }


    @Override
    public void show() {
        guiFont = Assets.getInstance().getAssetManager().get("gomarice48.ttf", BitmapFont.class);
        createGUI();
        map = new Map();
        animationEmitter = new AnimationEmitter();
        bombEmitter = new BombEmitter(this);
        botEmitter = new BotEmitter(this);
        bulletEmitter = new BulletEmitter(this);
//        bulletOfBotEmitter = new BulletOfBotEmitter(this);
        player = new Bomberman(this);

// **** try to continue saved game after options screen
        checkTempSavedGame();
        if (continueSavedGame == true) {
            continueSavedGame = false;
            loadTempFile();
        } else startLevel("map" + level + ".dat", hp); // use map+level number .dat file for map and health points(hp)
        System.out.println("GameScreen Row 176. Game level = " + level);

        // play music
        musicGame = Gdx.audio.newMusic(Gdx.files.internal("musicgame.mp3"));
        musicGame.setVolume(0.5f);
        musicGame.setLooping(true);

        musicGame.play();
        if (MusicStatus.INSTANCE.getMusicStatus() == 0){
            musicGame.pause();
        }

        // sound
        this.upgradeSound = Gdx.audio.newSound(Gdx.files.internal("bombSet.mp3"));

        //set player bullet status
        if (level <=3) {                      // no bullets at start
            BulletStatus.INSTANCE.setPlayerBulletStatus(0);
            playerBulletStatus = 0;
        }else {
            BulletStatus.INSTANCE.setPlayerBulletStatus(1);
            playerBulletStatus = 1;
        }
        //set bot's bullet status
        if (level <=1 || level ==15) {                      // no bullets at start and finish
            BulletStatus.INSTANCE.setBotBulletStatus(0);
            botBulletStatus = 0;
        }else {
            BulletStatus.INSTANCE.setBotBulletStatus(1);
            botBulletStatus =1;
        }

    }


    // save game

    public void saveGame() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(Gdx.files.local("save.dat").write(false));
            out.writeObject(botEmitter);
            out.writeObject(bombEmitter);
            out.writeObject(map);
            out.writeObject(player);
            out.writeInt(level);
            out.writeInt(hp); //try to save current HP
            out.writeInt(playerBulletStatus); //try to save player's bullet status
            out.writeInt(botBulletStatus);
//            out.write(Integer.toString(level, false));  // try to save game level
//            out.writeString(Integer.toString(level), false);
//            out.writeObject(level); //try to save current level

//            out.writeObject(MusicStatus.INSTANCE.getMusicStatus());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("GameScreen Row225. Saving game...Failed.");
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        GameLevel.INSTANCE.tryToSaveGameLevel();
        System.out.println("GameScreen. GameLevel saved. Level = " + level);
        BulletStatus.INSTANCE.tryToSavePlayerBulletStatus();
        BulletStatus.INSTANCE.tryToSaveBotBulletStatus();
    }

    // save temp file when go to options screen

    public void saveTempFile() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(Gdx.files.local("savetemp.dat").write(false));
            out.writeObject(botEmitter);
            out.writeObject(bombEmitter);
            out.writeObject(map);
            out.writeObject(player);
            out.writeInt(level); //try to save current level
            out.writeInt(hp); //try to save current HP
            out.writeInt(playerBulletStatus); //try to save player's bullet status
            out.writeInt(botBulletStatus);


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("GameScreen Row253. Saving temp game file... Failed.");
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        GameLevel.INSTANCE.tryToSaveTempGameLevel();
        System.out.println("GameScreen Row260. TempGameLevel saved. Level = " + level);
        BulletStatus.INSTANCE.tryToSaveTempPlayerBulletStatus();
        BulletStatus.INSTANCE.tryToSaveTempBotBulletStatus();
    }

    // load game

    public void loadGame() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(Gdx.files.local("save.dat").read());
            botEmitter = (BotEmitter) in.readObject();
            bombEmitter = (BombEmitter) in.readObject();
            map = (Map) in.readObject();
            player = (Bomberman) in.readObject();
            level = in.readInt(); //try to read level
            hp = in.readInt();    //try to read HP
            playerBulletStatus = in.readInt();
            BulletStatus.INSTANCE.setPlayerBulletStatus(playerBulletStatus);
            botBulletStatus = in.readInt();
            BulletStatus.INSTANCE.setBotBulletStatus(botBulletStatus);
//            player.setBulletStatus(in.readInt()); //Try to load Player's bullet status
            System.out.println("GameScreen.loadGame.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace information for debug);
            System.out.println("Load hp from saved game file. HP = " + hp);
            botEmitter.reloadResources(this);
            bombEmitter.reloadResources(this);
            bulletEmitter.reloadResources(this);
//            bulletOfBotEmitter.reloadResources(this);  //Bullet of Bots  ** not copied to Bot class
            map.reloadResources();
            player.reloadResources(this);
            animationEmitter.reset();
            checkPlayerBulletState();
            checkBotBulletState();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 //       currentStatus = Status.PLAY;
 //       level = GameLevel.INSTANCE.loadSavedgameLevel();
 //       level = GameLevel.INSTANCE.loadGameLevel();
        System.out.println("GameScreen.loadGame.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace information for debug
        System.out.println("Game level loaded from saved file. Level = " + level);
        System.out.println("GameScreen.loadGame.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace information for debug
        System.out.println("HP loaded from saved file. HP = " + hp);
        GameLevel.INSTANCE.setGameLevel(level);
        HpCount.INSTANCE.setHpCount(hp);

        System.out.println("GameScreen.loadGame.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace information for debug
        System.out.println("Game level set as " + level);
        if(level>3){
            BulletStatus.INSTANCE.setPlayerBulletStatus(1);
        }
 //       level = GameLevel.INSTANCE.getGameLevel();
 //       System.out.println("GameScreen Row300.Get game level. Level =" + level);
        BulletStatus.INSTANCE.loadPlayerBulletStatus();
        BulletStatus.INSTANCE.loadBotBulletStatus();
    }

    // load temp file when back from options screen

    public void loadTempFile() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(Gdx.files.local("savetemp.dat").read());
            botEmitter = (BotEmitter) in.readObject();
            bombEmitter = (BombEmitter) in.readObject();
            map = (Map) in.readObject();
            player = (Bomberman) in.readObject();
            level = in.readInt(); //try to read level
            hp = in.readInt();    //try to read HP
            playerBulletStatus = in.readInt();
            BulletStatus.INSTANCE.setPlayerBulletStatus(playerBulletStatus);
//            player.setBulletStatus(in.readInt()); //Try to load Player's bullet status
            botBulletStatus = in.readInt();
            BulletStatus.INSTANCE.setBotBulletStatus(botBulletStatus);
            System.out.println("GameScreen.loadTempFile.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace information for debug););
            System.out.println("Load hp from temp file. HP = " + hp);
            botEmitter.reloadResources(this);
            bombEmitter.reloadResources(this);
            bulletEmitter.reloadResources(this);
//            bulletOfBotEmitter.reloadResources(this);  //Bullet of Bots
            map.reloadResources();
            player.reloadResources(this);
            animationEmitter.reset();
//            player.setBulletStatus(this.playerBulletStatus); //Try to load Player's bullet status
            System.out.println("GameScreen.loadTempFile.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace information for debug);
            System.out.println("Temp file loaded. Game level = " + level);
            checkPlayerBulletState();
            checkBotBulletState();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        GameLevel.INSTANCE.loadTempGameLevel();
        System.out.println("GameScreen.loadTempFile.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace to debug
        System.out.println("This.hp = " + hp);
        System.out.println("GameScreen.loadTempFile. Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace to debug
        System.out.println("Temp game level loaded. Level =" + level);
        level = GameLevel.INSTANCE.getGameLevel();
        System.out.println("GameScreen.loadTempFile.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace to debug
        System.out.println("Get temp game level. Level =" + level);
        if(level>3){
            BulletStatus.INSTANCE.setPlayerBulletStatus(1);
        }
        BulletStatus.INSTANCE.loadTempPlayerBulletStatus();
        BulletStatus.INSTANCE.loadTempBotBulletStatus();
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
        bulletEmitter.render(batch);
//        botEmitter.getActiveElement().render(batch);
//        botEmitter.checkPool();  // check pool may be not needed. check leak of memory if removed
        animationEmitter.render(batch);
        ScreenManager.getInstance().resetCamera();
        player.renderGUI(batch, guiFont);
        batch.end();
        stage.draw();
    }

    public void startLevel(String mapName, int hp) {
        animationEmitter.reset();
        botEmitter.reset();
        bombEmitter.reset();
        bulletEmitter.reset();
        int m = botEmitter.getActiveList().size();

// **************try to fix ghost bullets when level changed to the next
        for (int i=0; i<m; i++){
            botEmitter.getActiveElement().destroy();
        }
//        bulletOfBotEmitter.reset();  // Bullet of Bots not copied to Bot class

        currentStatus = Status.PLAY;
        map.loadMap(mapName);
        this.hp = hp;
//        System.out.println("GameScreen.startLevel.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace to debug
//        System.out.println("This.hp = " + hp);
        player.startNewLevel(hp);

        try {
            level = GameLevel.INSTANCE.getGameLevel();
            System.out.println("GameScreen.startLevel.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace to debug
            System.out.println("Get Game Level = " + level);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("GameScreen.startLevel.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace to debug
            System.out.println("Level started.Game level = " + level);
        }
    }



    public void update(float dt) {

        // keys pressed

        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            if(currentStatus == Status.PLAY) {
                currentStatus = Status.SAVING;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
            if(currentStatus == Status.PLAY) {
                currentStatus = Status.LOADING;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if(currentStatus == Status.PLAY) {
                currentStatus = Status.CHOOSE;
            } else {
                if(currentStatus == Status.SAVED) {

//                    savingGroup.findActor("savinGroup.savingBtn").setVisible(true);
//                    savingGroup.findActor("savedBtn").setVisible(false);
//                    savingGroup.findActor("yesBtn").setVisible(true);
                    currentStatus = Status.SAVED;
                } else {
                    currentStatus = Status.PLAY;
                }
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            if(currentStatus == Status.PLAY) {
                currentStatus = Status.PAUSE;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            if(currentStatus == Status.PLAY) {
                currentStatus = Status.QUIT;
            }
        }


        cameraUpdate();

 //       if (currentStatus == Status.PLAY || currentStatus == Status.SHOP ) {
        if (currentStatus == Status.PLAY ) {
            player.update(dt);

            // check Cell for Bonus
            if (map.checkCellForBonus(player.getCellX(), player.getCellY())) {
                map.clearCell(player.getCellX(), player.getCellY());
                currentStatus = Status.SHOP;
            }

            // check cell for key
            if (map.checkCellForKey(player.getCellX(), player.getCellY())) {
                if (GameLevel.INSTANCE.getGameLevel() < 4) {
                    if (map.checkArtefactStatus() == LAYDOWN) {
                        currentStatus = Status.INFO;
                    } else if (map.checkArtefactStatus() == PICKEDUP) {
                        currentStatus = Status.PLAY;
                        map.clearCell(player.getCellX(), player.getCellY());
                        map.setKeyStatusFound();
                        map.setDoorStatusOpened();
                        //= map.currentDoorStatus.DOOROPENED;
                    }
                } else {
                    map.clearCell(player.getCellX(), player.getCellY());
                    map.setKeyStatusFound();
                    map.setDoorStatusOpened();
                }
            }


            // check cell for artefact
            if (map.checkCellForArtefact(player.getCellX(), player.getCellY())) {
                map.clearCell(player.getCellX(), player.getCellY());
                if (map.checkArtefactStatus() != PICKEDUP) {
                    map.setArtefactStatusPickedUp();
                    if(level == 1) {
                        currentStatus = Status.INFO1;  //to put INFO2 bar down to send player to look for a key
                    } else if(level == 2) {
                        currentStatus = Status.INFO2;  //to put INFO2 bar down to send player to look for a key
                    } else if(level == 3) {
                        currentStatus = Status.INFO3;  //to put INFO2 bar down to send player to look for a key
                        BulletStatus.INSTANCE.setPlayerBulletStatus(1);
                        playerBulletStatus = 1;
                    }
 //                   if (map.checkKeyStatus() == KEYFOUND) {
 //                       map.setDoorStatusOpened();
 //                       currentStatus = Status.PLAY;
 //                   }

                }
            }

            botEmitter.update(dt); // batch not added for render bullet of bot in botEmitter class
            bombEmitter.update(dt);
            bulletEmitter.update(dt);
//            bulletOfBotEmitter.update(dt);   // Bullet of Bots moved to BotEmitter line 69
            animationEmitter.update(dt);

            //********************************* LEVEL COMPLITE *****************************


            if (map.checkCellForDoor(player.getCellX(), player.getCellY()) && map.currentDoorStatus == Map.DoorStatus.DOOROPENED) {  //if bomberman reach the exit

                level++;
                //try fix HP count when level is finished
                hp = player.getHp();
                System.out.println("GameScreen.update.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " ");
                System.out.println("Current hp = " + hp);
                HpCount.INSTANCE.setHpCount(hp);

                HpCount.INSTANCE.setMaxHpCount(player.getMaxHp());  //Try to fix maxHp when level is finished

                System.out.println("GameScreen Row525. Game level = " + level);
//                GameLevel.INSTANCE.setGameLevel(level);
                if (level > maxlevel) {
                    level = 1;
                    GameLevel.INSTANCE.setGameLevel(1);
                    System.out.println("GameScreen Row 530. Game level = " + level);
                }
                //set player bullet status
                if (level == 1) {
                    BulletStatus.INSTANCE.setPlayerBulletStatus(0);
                    playerBulletStatus = 0;
                }else if(level >3 ){
                    BulletStatus.INSTANCE.setPlayerBulletStatus(1);
                    playerBulletStatus = 1;
                }
                //set bot's bullet status
                if (level <= 1 || level == 15) {
                    BulletStatus.INSTANCE.setBotBulletStatus(0);
                    botBulletStatus = 0;
                }else {
                    BulletStatus.INSTANCE.setBotBulletStatus(1);
                    botBulletStatus =1;
                }
                GameLevel.INSTANCE.setGameLevel(level);
                startLevel("map" + level + ".dat", hp);
                System.out.println("GameScreen Row 549. Level started. Game level = " + level);
            }

        }
        checkStatus();
        checkMusic();
        checkPlayerBulletState();
        checkBotBulletState();
        upgradeShop(dt);
        chooseShop(dt);
        infoBar(dt);
        infoBar1(dt);
        infoBar2(dt);
        infoBar3(dt);
        infoBar4(dt);
        infoBar5(dt);
        savingBar(dt);
        loadingBar(dt);
        quitBar(dt);
        pauseBar(dt);
        gameOverBar(dt);
        stage.act(dt);
    }


// upgradeShop
    public void upgradeShop(float dt) {
        if (currentStatus == Status.SHOP && upgradeGroup.getY() < 200.0f) {
            upgradeGroup.setY(upgradeGroup.getY() + 600.0f * dt);
        }
        if (currentStatus == Status.PLAY && upgradeGroup.getY() > -400.0f) {
            upgradeGroup.setY(upgradeGroup.getY() - 600.0f * dt);
        }
    }
// chooseShop
    public void chooseShop(float dt) {
        if (currentStatus == Status.CHOOSE && chooseGroup.getY() > 300.0f) {
            chooseGroup.setY(chooseGroup.getY() - 600.0f * dt);
        }
        if (currentStatus == Status.PLAY && chooseGroup.getY() < 800.0f) {
            chooseGroup.setY(chooseGroup.getY() + 600.0f * dt);
        }
    }

    // infoBar. When find artefact before key
    public void infoBar(float dt) {
        if (currentStatus == Status.INFO && infoGroup.getY() > 80.0f) {
            infoGroup.setY(infoGroup.getY() - 600.0f * 2 * dt);
        }
        if (currentStatus == Status.PLAY && infoGroup.getY() < 800.0f) {
            infoGroup.setY(infoGroup.getY() + 600.0f * 2 * dt);
        }
    }

    // infoBar 1  When find 1st artefact
    public void infoBar1(float dt) {
        if (currentStatus == Status.INFO1 && infoGroup1.getY() > 99.0f) {
            infoGroup1.setY(infoGroup1.getY() - 600.0f * 2 * dt);
        }
        if (currentStatus == Status.PLAY && infoGroup1.getY() < 800.0f) {
            infoGroup1.setY(infoGroup1.getY() + 600.0f * 2 * dt);
        }
    }

    // infoBar 2  When find 2d artefact
    public void infoBar2(float dt) {
        if (currentStatus == Status.INFO2 && infoGroup2.getY() > 99.0f) {
            infoGroup2.setY(infoGroup2.getY() - 600.0f * 2 * dt);
        }
        if (currentStatus == Status.PLAY && infoGroup2.getY() < 800.0f) {
            infoGroup2.setY(infoGroup2.getY() + 600.0f * 2 * dt);
        }
    }

    // infoBar 3  When find 3d artefact
    public void infoBar3(float dt) {
        if (currentStatus == Status.INFO3 && infoGroup3.getY() > 100.0f) {
            infoGroup3.setY(infoGroup3.getY() - 600.0f * 2 * dt);
        }
        if (currentStatus == Status.PLAY && infoGroup3.getY() < 800.0f) {
            infoGroup3.setY(infoGroup3.getY() + 600.0f * 2 * dt);
        }
    }

    // infoBar 4  When make Molotoff
    public void infoBar4(float dt) {
        if (currentStatus == Status.INFO4 && infoGroup4.getY() > 99.0f) {
            infoGroup4.setY(infoGroup4.getY() - 600.0f * 2 * dt);
        }
        if (currentStatus == Status.PLAY && infoGroup4.getY() < 800.0f) {
            infoGroup4.setY(infoGroup4.getY() + 600.0f * 2 * dt);
        }
    }

    // infoBar 5   When show rules for shout Molotof on Android(NOT ON DESKTOP)
    public void infoBar5(float dt) {
        if (currentStatus == Status.INFO5 && infoGroup5.getY() > 200.0f) {
            infoGroup5.setY(infoGroup5.getY() - 600.0f * 2 * dt);
        }
        if (currentStatus == Status.PLAY && infoGroup5.getY() < 800.0f) {
            infoGroup5.setY(infoGroup5.getY() + 600.0f * 2 * dt);
        }
    }
/*
    // infoBar 6  When show rules for shout Molotof on Android(NOT ON DESKTOP)
    public void infoBar6(float dt) {
        if (currentStatus == Status.INFO6 && infoGroup6.getY() > 200.0f) {
            infoGroup6.setY(infoGroup6.getY() - 600.0f * 2 * dt);
        }
        if (currentStatus == Status.PLAY && infoGroup5.getY() < 800.0f) {
            infoGroup6.setY(infoGroup6.getY() + 600.0f * 2 * dt);
        }
    }
*/


    // saving bar
    public void savingBar(float dt) {
        if (currentStatus == Status.SAVING && savingGroup.getY() > 500.0f) {
            savingGroup.setY(savingGroup.getY() - 600.0f * 2 * dt);
        }
        if (currentStatus == Status.PLAY && savingGroup.getY() < 800.0f) {
            savingGroup.setY(savingGroup.getY() + 600.0f * 2 * dt);
        }
        /**
        if(currentStatus == Status.SAVED){
            savingGroup.savingBtn.setVisible(false);
            savingGroup.savedBtn.setVisible(true);
 //           System.out.println("Game status = " + currentStatus);
        } else if(currentStatus != Status.SAVED){
            savingGroup.savingBtn.setVisible(true);
            savingGroup.savedBtn.setVisible(false);
//            System.out.println("Game status = " + currentStatus);
        }
         */
    }

    // loading bar
    public void loadingBar(float dt) {
        if (currentStatus == Status.LOADING && loadingGroup.getY() > 400.0f) {
            loadingGroup.setY(loadingGroup.getY() - 600.0f * 2 * dt);
        }
        if (currentStatus == Status.PLAY && loadingGroup.getY() < 800.0f) {
            loadingGroup.setY(loadingGroup.getY() + 600.0f * 2 * dt);
        }
    }

    // quit bar
    public void quitBar(float dt) {
        if (currentStatus == Status.QUIT && quitGroup.getY() > 300.0f) {
            quitGroup.setY(quitGroup.getY() - 600.0f * 2 * dt);
        }
        if (currentStatus == Status.PLAY && quitGroup.getY() < 800.0f) {
            quitGroup.setY(quitGroup.getY() + 600.0f * 2 * dt);
        }
    }

    // game over bar
    public void gameOverBar(float dt) {
        if (currentStatus == Status.GAMEOVER && gameOverGroup.getY() > 300.0f) {
            gameOverGroup.setY(gameOverGroup.getY() - 600.0f * 2 * dt);
        }
        if (currentStatus == Status.PLAY && gameOverGroup.getY() < 800.0f) {
            gameOverGroup.setY(gameOverGroup.getY() + 600.0f * 2 * dt);
        }
    }

    // pause bar
    public void pauseBar(float dt) {
        if (currentStatus == Status.PAUSE && pauseGroup.getY() > 600.0f) {
            pauseGroup.setY(pauseGroup.getY() - 600.0f * 2 * dt);
        }
        if (currentStatus == Status.PLAY && pauseGroup.getY() < 800.0f) {
            pauseGroup.setY(pauseGroup.getY() + 600.0f * 2 * dt);
        }
    }

    // camera scrolling
    public void cameraUpdate() {
        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        if (camera.position.x < Rules.WORLD_WIDTH / 2) {
            camera.position.x = Rules.WORLD_WIDTH / 2;
        }
        if (camera.position.y < Rules.WORLD_HEIGHT / 2) {
            camera.position.y = Rules.WORLD_HEIGHT / 2;
        }
        if (camera.position.x > map.getMapX() * Rules.CELL_SIZE - Rules.WORLD_WIDTH / 2) {
            camera.position.x = map.getMapX() * Rules.CELL_SIZE - Rules.WORLD_WIDTH / 2;
        }
        if (camera.position.y > map.getMapY() * Rules.CELL_SIZE - Rules.WORLD_HEIGHT / 2) {
            camera.position.y = map.getMapY() * Rules.CELL_SIZE - Rules.WORLD_HEIGHT / 2;
        }
        // camera.rotate(new Vector3(0,0,1),0.05f);
        camera.update();
    }

    @Override
    public void dispose() {
//        batch.dispose(); // do not dispose batch!
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

    public void createGUI() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());


        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = guiFont;
        skin.add("simpleSkin", textButtonStyle);


        // savingGroup

        savingGroup = new Group();
        savingGroup.setPosition(Rules.WORLD_WIDTH / 2 - 200, 800);
        Image imageSaving = new Image(skin.getDrawable("upgPanel"));

        final Button yesBtn = new Button(skin.getDrawable("yesButton"));

        // *** Experiment witn animation instead of button
//        Button yesBtn = new Button(skin.getDrawable("bot4x3"));
//        this.getBombEmitter().getActiveElement().activate(player, (int) savingGroup.getX(), (int) savingGroup.getY(), 2.5f, 1);

//        player2 = new Bomberman(this);
//        bombEmitter2 = new BombEmitter(this);
//        bombEmitter2.getActiveElement().activate(player2,1, 1, 2.5f, 1);
                //.animationEmitter.createAnimation(savingGroup.getX(), savingGroup.getY(), 4.0f, AnimationEmitter.AnimationType.EXPLOSION);



        final Button savingBtn = new TextButton("Save game?", skin, "simpleSkin");
        final Button savedBtn = new TextButton("Game saved", skin, "simpleSkin");

        Button noBtn = new Button(skin.getDrawable("noButton"));

        yesBtn.setPosition(0, 0);
        savingBtn.setPosition(98,20);
        savedBtn.setPosition(68,20);
        noBtn.setPosition(300, 0);
 //       if (currentStatus == Status.PLAY){
            savingBtn.setVisible(true);
            savedBtn.setVisible(false);
//        } else if (currentStatus == Status.SAVED){
//            savingBtn.setVisible(false);
//            savedBtn.setVisible(true);
//        }
        savingGroup.addActor(imageSaving);
        savingGroup.addActor(yesBtn);
        savingGroup.addActor(savingBtn);
        savingGroup.addActor(savedBtn);
        savingGroup.addActor(noBtn);


        yesBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                saveGame();
                savingBtn.setVisible(false);
                savedBtn.setVisible(true);
                yesBtn.setVisible(false);
 //               savingBtn.setPosition(150,20);
                currentStatus = Status.SAVED;

 //               savingBtn.setVisible(true);
 //               savedBtn.setVisible(false);
 //               currentStatus = Status.PLAY;

            }
        });

        noBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                savingBtn.setVisible(true);
                savedBtn.setVisible(false);
                yesBtn.setVisible(true);
                currentStatus = Status.PLAY;
            }
        });


//        private void reloadBattons(){
//            savingBtn.setVisible(true);
//            savedBtn.setVisible(false);
//            yesBtn.setVisible(true);
//        }

        stage.addActor(savingGroup);


        // loadingGroup

        loadingGroup = new Group();
        loadingGroup.setPosition(Rules.WORLD_WIDTH / 2 - 200, 800);
        Image imageLoading = new Image(skin.getDrawable("upgPanel"));

        Button yesLoadBtn = new Button(skin.getDrawable("yesButton"));
        Button loadingBtn = new TextButton("Load game?", skin, "simpleSkin");
        Button noLoadBtn = new Button(skin.getDrawable("noButton"));

        yesLoadBtn.setPosition(0, 0);
        loadingBtn.setPosition(98,20);
        noLoadBtn.setPosition(300, 0);

        loadingGroup.addActor(imageLoading);
        loadingGroup.addActor(yesLoadBtn);
        loadingGroup.addActor(loadingBtn);
        loadingGroup.addActor(noLoadBtn);

        yesLoadBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                loadGame();
                currentStatus = Status.PLAY;
            }
        });

        noLoadBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentStatus = Status.PLAY;
            }
        });

        stage.addActor(loadingGroup);

        // quitGroup

        quitGroup = new Group();
        quitGroup.setPosition(Rules.WORLD_WIDTH / 2 - 200, 800);
        Image imageQuit = new Image(skin.getDrawable("upgPanel"));

        Button yesQuitBtn = new Button(skin.getDrawable("yesButton"));
        Button quitBtn = new TextButton("Exit game?", skin, "simpleSkin");
        Button noQuitBtn = new Button(skin.getDrawable("noButton"));

        yesQuitBtn.setPosition(0, 0);
        quitBtn.setPosition(98,20);
        noQuitBtn.setPosition(300, 0);

        quitGroup.addActor(imageQuit);
        quitGroup.addActor(yesQuitBtn);
        quitGroup.addActor(quitBtn);
        quitGroup.addActor(noQuitBtn);

        yesQuitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                saveTempFile();
                TempSavedGameStatus.INSTANCE.setTempSavedGameState(1);
                continueSavedGame = true;
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.EXIT);
            }
        });

        noQuitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentStatus = Status.PLAY;
            }
        });

        stage.addActor(quitGroup);

        // gameOverGroup

        gameOverGroup = new Group();
        gameOverGroup.setPosition(Rules.WORLD_WIDTH / 2 - 200, 800);
        Image imageGameOver = new Image(skin.getDrawable("upgPanel4x2"));

        Button gameOverBtn = new TextButton("Game Over", skin, "simpleSkin");
        Button gameOverRestartBtn = new Button(skin.getDrawable("resratButton"));
//        Button gameOverMenuBtn = new Button(skin.getDrawable("menuButton"));
        Button gameOverExitBtn = new Button(skin.getDrawable("exitButton"));
        Button gameOverLoadBtn = new Button(skin.getDrawable("loadButton"));


        gameOverBtn.setPosition(100,120);
        gameOverRestartBtn.setPosition(20, 20);
//        gameOverMenuBtn.setPosition(150, 20);
        gameOverExitBtn.setPosition(280, 20);
        gameOverLoadBtn.setPosition(140, 20);

        gameOverGroup.addActor(imageGameOver);
        gameOverGroup.addActor(gameOverBtn);
        gameOverGroup.addActor(gameOverRestartBtn);
//        gameOverGroup.addActor(gameOverMenuBtn);
        gameOverGroup.addActor(gameOverExitBtn);
        gameOverGroup.addActor(gameOverLoadBtn);

        gameOverRestartBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.setScore(0);
                HpCount.INSTANCE.setHpCount(3);
                level = 1;
                GameLevel.INSTANCE.setGameLevel(1);
                TempSavedGameStatus.INSTANCE.setTempSavedGameState(0);
                BulletStatus.INSTANCE.setPlayerBulletStatus(0); //switch off player's bullet on restart
//                System.out.println("GameScreen Row 804. Resrat Btn. Game level = " + level);
//                startLevel("map" + level + ".dat", 3);
//                startLevel("map1.dat", 3);
//                System.out.println("Game level = " + level);
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);

            }
        });

        gameOverLoadBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                loadGame();
                currentStatus = Status.PLAY;

            }
        });

 //       gameOverMenuBtn.addListener(new ChangeListener() {
 //           @Override
 //           public void changed(ChangeEvent event, Actor actor) {
 //               saveTempFile();
 //               ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.OPTIONS);
 //           }
 //       });

        gameOverExitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.EXIT);
            }
        });

        stage.addActor(gameOverGroup);


        // pauseGroup

        pauseGroup = new Group();
        pauseGroup.setPosition(Rules.WORLD_WIDTH / 2 - 200, 800);
        Image imagePause = new Image(skin.getDrawable("upgPanel"));

        Button pauseBtn2 = new TextButton("Game paused", skin, "simpleSkin");
        Button continueBtn = new Button(skin.getDrawable("backButton"));

        pauseBtn2.setPosition(50,20);
        continueBtn.setPosition(300, 0);

        pauseGroup.addActor(imagePause);
        pauseGroup.addActor(pauseBtn2);
        pauseGroup.addActor(continueBtn);

        continueBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentStatus = Status.PLAY;
            }
        });

        stage.addActor(pauseGroup);

// Upgrade group

        upgradeGroup = new Group();
        upgradeGroup.setPosition(Rules.WORLD_WIDTH / 2 - 200, -400);
//        Image imageUpgrade = new Image(skin.getDrawable("upgPanel4x3"));
        Image imageUpgrade = new Image(skin.getDrawable("upgPanelBig"));
        Button upgTextBtn = new TextButton("Choose upgrade ", skin, "simpleSkin");
        Button upgHp = new Button(skin.getDrawable("upgHp"));
        Button upgHpTextBtn = new TextButton("Cost " + costHp, skin, "simpleSkin");
//        Button upgHpTextBtn = new TextButton("Cost " + player.getCostHP(), skin, "simpleSkin");
        Button upgBombRadius = new Button(skin.getDrawable("upgRad"));
        Button upgRadTextBtn = new TextButton("Cost " + costRadius, skin, "simpleSkin");
//        Button upgRadTextBtn = new TextButton("Cost " + player.getCostRAD(), skin, "simpleSkin");
        Button closeBtn = new Button(skin.getDrawable("closeButtonLittle"));
        Button backBttn = new Button(skin.getDrawable("backButton"));
        Button backTextBttn = new TextButton("Back to game", skin, "simpleSkin");

        upgTextBtn.setPosition(60, 320);
        upgHp.setPosition(20, 120);
        upgHpTextBtn.setPosition(140, 140);
        upgBombRadius.setPosition(20, 220);
        upgRadTextBtn.setPosition(140, 240);
        closeBtn.setPosition(360, 360);
        backBttn.setPosition(20, 20);
        backTextBttn.setPosition(130, 40);

        upgradeGroup.addActor(imageUpgrade);
        upgradeGroup.addActor(upgHp);
        upgradeGroup.addActor(upgTextBtn);
        upgradeGroup.addActor(upgHpTextBtn);
        upgradeGroup.addActor(upgBombRadius);
        upgradeGroup.addActor(upgRadTextBtn);
        upgradeGroup.addActor(closeBtn);
        upgradeGroup.addActor(backBttn);
        upgradeGroup.addActor(backTextBttn);

        upgHp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                if(player.getScore() > costHp) {
                if(player.getScore() >= player.getCostHP()) {
                    if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
                        upgradeSound.play();
                    }
//                    upgradeSound.play();
                    player.upgrade(Bomberman.UpgradeType.HP);
//                    player.reduceScore(costHp);
//                    player.scoreUpdate();
//                    currentStatus = Status.PLAY;
                }
            }
        });
        upgBombRadius.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                if(player.getScore() > costRadius) {
                if(player.getScore() > player.getCostRAD()) {
                    if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
                        upgradeSound.play();
                    }
//                    upgradeSound.play();
                    player.upgrade(Bomberman.UpgradeType.BOMBRADIUS);
//                    player.reduceScore(costRadius);
//                    player.scoreUpdate();
//                    currentStatus = Status.PLAY;
                }
            }
        });
        closeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                    currentStatus = Status.PLAY;
            }
        });
        backBttn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentStatus = Status.PLAY;
            }
        });


        stage.addActor(upgradeGroup);


/*
        upgradeGroup = new Group();
        upgradeGroup.setPosition(Rules.WORLD_WIDTH / 2 - 200, -400);
        Image imageUpgrade = new Image(skin.getDrawable("upgPanelBig"));
        Button upgHp = new Button(skin.getDrawable("upgHp"));
        Button upgBombRadius = new Button(skin.getDrawable("upgRad"));
        Button upgScore = new Button(skin.getDrawable("upgScore"));
 //       Button upgLife = new Button(skin.getDrawable("upgLife"));
        upgHp.setPosition(0, 0);
        upgBombRadius.setPosition(100, 0);
        upgScore.setPosition(200, 0);
 //       upgLife.setPosition(300, 0);
        upgradeGroup.addActor(imageUpgrade);
        upgradeGroup.addActor(upgHp);
        upgradeGroup.addActor(upgBombRadius);
        upgradeGroup.addActor(upgScore);
//        upgradeGroup.addActor(upgLife);
        upgHp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.upgrade(Bomberman.UpgradeType.HP);
                currentStatus = Status.PLAY;
            }
        });
        upgBombRadius.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.upgrade(Bomberman.UpgradeType.BOMBRADIUS);
                currentStatus = Status.PLAY;
            }
        });
        upgScore.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.upgrade(Bomberman.UpgradeType.MONEY);
                currentStatus = Status.PLAY;
            }
        });
 //       upgLife.addListener(new ChangeListener() {
 //           @Override
 //           public void changed(ChangeEvent event, Actor actor) {
 //               player.upgrade(Bomberman.UpgradeType.LIFE);
 //               currentStatus = Status.PLAY;
 //           }
 //       });
        stage.addActor(upgradeGroup);
*/

// chooseGroup

        chooseGroup = new Group();
        chooseGroup.setPosition(Rules.WORLD_WIDTH / 2 - 200, 800);
        Image imageChoose = new Image(skin.getDrawable("upgPanel"));

        Button menuBtn = new Button(skin.getDrawable("menuButton"));
        Button backBtn = new Button(skin.getDrawable("backButton"));
//        Button pauseBtn = new Button(skin.getDrawable("pauseButton"));
        Button helpBtn = new Button(skin.getDrawable("helpButton"));
        Button exitBtn = new Button(skin.getDrawable("exitButton"));

        menuBtn.setPosition(0, 0);
        backBtn.setPosition(100, 0);
//        pauseBtn.setPosition(200, 0);
        helpBtn.setPosition(200,0);
        exitBtn.setPosition(300, 0);

        chooseGroup.addActor(imageChoose);
        chooseGroup.addActor(menuBtn);
        chooseGroup.addActor(backBtn);
//        chooseGroup.addActor(pauseBtn);
        chooseGroup.addActor(helpBtn);
        chooseGroup.addActor(exitBtn);
        menuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                saveTempFile();
                continueSavedGame = true;
                TempSavedGameStatus.INSTANCE.setTempSavedGameState(1);
                ScreenManager.getInstance().setPreviousScreenType(ScreenManager.ScreenType.GAME);
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.OPTIONS);
            }
        });

        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentStatus = Status.PLAY;
            }
        });

        //        pauseBtn.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                currentStatus = Status.PAUSE;
//            }
//        });

                helpBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                saveTempFile();
                continueSavedGame = true;
                TempSavedGameStatus.INSTANCE.setTempSavedGameState(1);
                ScreenManager.getInstance().setPreviousScreenType(ScreenManager.ScreenType.GAME);  // try to remember ScreenType
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.RULES);
            }
        });

        exitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                saveTempFile();
                continueSavedGame = true;
                TempSavedGameStatus.INSTANCE.setTempSavedGameState(1);
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.EXIT);
            }
        });
        stage.addActor(chooseGroup);


// infoGroup. When find key before artefact

        infoGroup = new Group();
        infoGroup.setPosition(Rules.WORLD_WIDTH / 2 - 200, 800);
//        Image imageInfo = new Image(skin.getDrawable("upgPanelBig2"));
        Image imageInfo = new Image(skin.getDrawable("findkey"));
        Button infoTextBtn1 = new TextButton("Find artefact", skin, "simpleSkin");
        Button infoTextBtn2 = new TextButton("and then", skin, "simpleSkin");
        Button infoTextBtn3 = new TextButton("take the key ", skin, "simpleSkin");
        Button okBtn = new Button(skin.getDrawable("backButton"));
        okBtn.setPosition(20, 20);
        infoTextBtn1.setPosition(20, 530);
        infoTextBtn2.setPosition(50, 470);
        infoTextBtn3.setPosition(20, 410);

        infoGroup.addActor(imageInfo);
        infoGroup.addActor(okBtn);
        infoGroup.addActor(infoTextBtn1);
        infoGroup.addActor(infoTextBtn2);
        infoGroup.addActor(infoTextBtn3);

        okBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                map.setArtefactStatusInformed();
                currentStatus = Status.PLAY;
            }
        });

        stage.addActor(infoGroup);


        // infoGroup#1 When find 1d artefact

        infoGroup1 = new Group();
        infoGroup1.setPosition(100, 800);
        Image imageInfo1 = new Image(skin.getDrawable("paneldrink1020x560"));
        Button infoTextBtn1_1 = new TextButton("You got a bottle!", skin, "simpleSkin");
        Button infoTextBtn2_1 = new TextButton("Want to drink it?", skin, "simpleSkin");
        Button infoTextBtn3_1 = new TextButton("Find the key! ", skin, "simpleSkin");
        Button drinkBtn = new Button(skin.getDrawable("drinkButton"));
        Button keepBtn1 = new Button(skin.getDrawable("keepButton"));

        drinkBtn.setPosition(620, 250);
        keepBtn1.setPosition(720,250);
        infoTextBtn1_1.setPosition(550, 480);
        infoTextBtn2_1.setPosition(550, 400);
        infoTextBtn3_1.setPosition(600, 60);

        infoGroup1.addActor(imageInfo1);
        infoGroup1.addActor(drinkBtn);
        infoGroup1.addActor(keepBtn1);
        infoGroup1.addActor(infoTextBtn1_1);
        infoGroup1.addActor(infoTextBtn2_1);
        infoGroup1.addActor(infoTextBtn3_1);

        keepBtn1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                map.setArtefactStatusInformed();
                currentStatus = Status.PLAY;
            }
        });
        drinkBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                level = 1;
                GameLevel.INSTANCE.setGameLevel(1);
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.DRUNKEXIT);

            }
        });

        stage.addActor(infoGroup1);


// infoGroup#2 When find 2d artefact

        infoGroup2 = new Group();
        infoGroup2.setPosition(100, 800);
        Image imageInfo2 = new Image(skin.getDrawable("panelSmoke1020x560"));
        Button infoTextBtn1_2 = new TextButton("You got a Zippo!", skin, "simpleSkin");
        Button infoTextBtn2_2 = new TextButton("Want to smoke?", skin, "simpleSkin");
        Button infoTextBtn3_2 = new TextButton("Find the key! ", skin, "simpleSkin");
        Button smokeBtn = new Button(skin.getDrawable("smokeButton"));
        Button keepBtn2 = new Button(skin.getDrawable("keepButton"));
//        Button keepBtn2 = new Button(skin.getDrawable("smokeButton"));

        smokeBtn.setPosition(650, 250);
        keepBtn2.setPosition(750,250);
        infoTextBtn1_2.setPosition(600, 480);
        infoTextBtn2_2.setPosition(600, 400);
        infoTextBtn3_2.setPosition(640, 100);

        infoGroup2.addActor(imageInfo2);
        infoGroup2.addActor(smokeBtn);
        infoGroup2.addActor(keepBtn2);
        infoGroup2.addActor(infoTextBtn1_2);
        infoGroup2.addActor(infoTextBtn2_2);
        infoGroup2.addActor(infoTextBtn3_2);

        keepBtn2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                map.setArtefactStatusInformed();
                currentStatus = Status.PLAY;
            }
        });
        smokeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                level = 1;
                GameLevel.INSTANCE.setGameLevel(1);
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.SMOKEEXIT);

            }
        });

        stage.addActor(infoGroup2);



        // infoGroup#3 When find 3d artefact

        infoGroup3 = new Group();
        infoGroup3.setPosition(Rules.WORLD_WIDTH / 2 - 400, 800);
        Image imageInfo3 = new Image(skin.getDrawable("panelGotRag"));
        Button okBtn3 = new Button(skin.getDrawable("okButton"));
        okBtn3.setPosition(200, 30);

        infoGroup3.addActor(imageInfo3);
        infoGroup3.addActor(okBtn3);

        okBtn3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (Gdx.app.getType() == Application.ApplicationType.Android) {
                    currentStatus = Status.INFO5;
                } else {
                    currentStatus = Status.INFO4;
                }
            }
        });

        stage.addActor(infoGroup3);

        // infoGroup#4 When make Molotoff

        infoGroup4 = new Group();
        infoGroup4.setPosition(Rules.WORLD_WIDTH / 2 - 400, 800);
        Image imageInfo4 = new Image(skin.getDrawable("panelmolotof"));
        Button okBtn4 = new Button(skin.getDrawable("okButton"));
        okBtn4.setPosition(60, 30);

        infoGroup4.addActor(imageInfo4);
        infoGroup4.addActor(okBtn4);

        okBtn4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentStatus = Status.PLAY;
            }
        });

        stage.addActor(infoGroup4);

        /*
        // infoGroup#5 When show rules to shout Molotoff

        infoGroup5 = new Group();
        infoGroup5.setPosition(Rules.WORLD_WIDTH / 2 - 200, 800);
        Image imageInfo5 = new Image(skin.getDrawable("panelmolotoff"));
//        Button infoTextBtn1_5 = new TextButton("You got", skin, "simpleSkin");
//        Button infoTextBtn2_5 = new TextButton("a rag.", skin, "simpleSkin");
//        Button infoTextBtn3_5 = new TextButton("All 3 elements! ", skin, "simpleSkin");
        Button okBtn5 = new Button(skin.getDrawable("okButton"));
        okBtn5.setPosition(200, 30);
//        infoTextBtn1_5.setPosition(170, 300);
//        infoTextBtn2_5.setPosition(160, 240);
//        infoTextBtn3_5.setPosition(130, 180);

        infoGroup5.addActor(imageInfo5);
        infoGroup5.addActor(okBtn5);
//        infoGroup5.addActor(infoTextBtn1_5);
//        infoGroup5.addActor(infoTextBtn2_5);
//        infoGroup5.addActor(infoTextBtn3_5);

        okBtn5.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                map.setArtefactStatusInformed();
                currentStatus = Status.PLAY;
            }
        });

        stage.addActor(infoGroup5);
*/

        // infoGroup#5 When show rules to shout Molotoff for Android (not for Desktop)

        infoGroup5 = new Group();
        infoGroup5.setPosition(Rules.WORLD_WIDTH / 2 - 400, 800);
        Image imageInfo5 = new Image(skin.getDrawable("panelmolotofandroid"));

        Button okBtn5 = new Button(skin.getDrawable("okButton"));
        okBtn5.setPosition(60, 30);

        infoGroup5.addActor(imageInfo5);
        infoGroup5.addActor(okBtn5);

        okBtn5.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentStatus = Status.PLAY;
            }
        });

        stage.addActor(infoGroup5);


        // Put buttons to screen in Android version of the game
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            final Group arrowGroup = new Group();
            Button buttonLeft = new Button(skin.getDrawable("leftArrow"));
            Button buttonRight = new Button(skin.getDrawable("rightArrow"));
            Button buttonUp = new Button(skin.getDrawable("upArrow"));
            Button buttonDown = new Button(skin.getDrawable("downArrow"));
            buttonLeft.setPosition(10, 10);
            buttonUp.setPosition(120, 120);
            buttonRight.setPosition(230, 10);
            buttonDown.setPosition(120, 10);
            arrowGroup.setPosition(50, 50);
            Button[] buttons = new Button[]{buttonLeft, buttonRight, buttonUp, buttonDown};
            final char[] chars = new char[]{'L', 'R', 'U', 'D'};
            for (int i = 0; i < buttons.length; i++) {
                final int innerI = i;
                arrowGroup.addActor(buttons[i]);
                buttons[i].addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        player.setPrefferedDirection(chars[innerI]);
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        player.setPrefferedDirection('-');
                    }
                });
            }
            Button buttonBomb = new Button(skin.getDrawable("bombButton"));
            buttonBomb.setPosition(1080, 10);
            buttonBomb.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    player.setupBomb();
                    return true;
                }
            });
            arrowGroup.addActor(buttonBomb);

            Button menuButton = new Button(skin.getDrawable("menuButton"));
            menuButton.setPosition(1080,110);
            menuButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
                    return true;
                }
            });
            arrowGroup.addActor(menuButton);

            Button pauseButton = new Button (skin.getDrawable("upArrow"));
            pauseButton.setPosition(1080, 210);
            pauseButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
                    return true;
                }
            });
            arrowGroup.addActor(pauseButton);

            // arrowGroup.setColor(1,1,1,1f);
            stage.addActor(arrowGroup);
        }
    }

}
