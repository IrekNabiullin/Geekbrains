package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.*;

public class ScreenManager {

    public enum ScreenType {
        MENU, RULES, GAME, OPTIONS, EXIT, DRUNKEXIT, SMOKEEXIT;
    }

    private static ScreenManager ourInstance = new ScreenManager();

    public static ScreenManager getInstance() {
        return ourInstance;
    }


    private BomberGame game;
    private Screen targetScreen;
    private LoadingScreen loadingScreen;
    private RulesScreen rulesScreen;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private OptionsScreen optionsScreen;
    private ExitScreen exitScreen;
    private DrunkExitScreen drunkExitScreen;
    private SmokeExitScreen smokeExitScreen;

    private SpriteBatch batch;
    private Viewport viewport;
    private Camera camera;

    private ScreenType previousScreenType = ScreenType.MENU;  //try to remember Previous Screen type
    private ScreenType currentScreenType;

    public Viewport getViewport() {
        return viewport;
    }

    public ScreenType getCurrenScreenType(){
        System.out.println("Current Screen Type = " + currentScreenType);
        return currentScreenType;
    }

    public void setCurrentScreenType(ScreenType type){
        currentScreenType = type;
    }

    public ScreenType getPreviousScreenType(){
        System.out.println("Previous Screen Type = " + previousScreenType);
        return previousScreenType;
    }

    public void setPreviousScreenType(ScreenType type){
        previousScreenType = type;
    }

    private ScreenManager() {
    }

    public void init(BomberGame game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
        this.camera = new OrthographicCamera(1280, 720);
        this.viewport = new FitViewport(1280, 720, camera);
        this.gameScreen = new GameScreen(batch, camera);
        this.rulesScreen =  new RulesScreen(batch);
        this.menuScreen = new MenuScreen(batch);
        this.optionsScreen = new OptionsScreen(batch);
        this.exitScreen = new ExitScreen(batch);
        this.drunkExitScreen = new DrunkExitScreen(batch);
        this.smokeExitScreen = new SmokeExitScreen(batch);
        this.loadingScreen = new LoadingScreen(batch);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
    }

    public void changeScreen(ScreenType type) {
        Screen screen = game.getScreen();
 //       System.out.println("Current screen: " + type);
 //       previousScreen = type;
        Gdx.input.setInputProcessor(null); // ?
        Assets.getInstance().clear();
        if (screen != null) {
            screen.dispose();
        }
        resetCamera();
        game.setScreen(loadingScreen);
        switch (type) {
            case MENU:
                targetScreen = menuScreen;
                Assets.getInstance().loadAssets(ScreenType.MENU);
                break;
            case RULES:
                targetScreen = rulesScreen;
                Assets.getInstance().loadAssets(ScreenType.RULES);
                break;
            case GAME:
                targetScreen = gameScreen;
                Assets.getInstance().loadAssets(ScreenType.GAME);
                break;
            case OPTIONS:
                targetScreen = optionsScreen;
                Assets.getInstance().loadAssets(ScreenType.OPTIONS);
                break;
            case EXIT:
                targetScreen = exitScreen;
                Assets.getInstance().loadAssets(ScreenType.EXIT);
                break;
            case DRUNKEXIT:
                targetScreen = drunkExitScreen;
                Assets.getInstance().loadAssets(ScreenType.DRUNKEXIT);
                break;
            case SMOKEEXIT:
                targetScreen = smokeExitScreen;
                Assets.getInstance().loadAssets(ScreenType.SMOKEEXIT);
                break;
        }
    }

    public void resetCamera() {
        camera.position.set(640, 360, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public void goToTarget() {
        game.setScreen(targetScreen);
    }
}
