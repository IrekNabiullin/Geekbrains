package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.RefCountedContainer;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

public class Assets {
    private static Assets ourInstance = new Assets();

    public static Assets getInstance() {
        return ourInstance;
    }

    private AssetManager assetManager;
    private TextureAtlas atlas;
    private int packVersion = 31;

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    private Assets() {
        this.assetManager = new AssetManager();
    }

    public void loadAssets(ScreenManager.ScreenType type) {
        switch (type) {
            case MENU:
                assetManager.load("game" + packVersion + ".pack", TextureAtlas.class);
                createStdFont(32);
                createStdFont(48);
                createStdFont(96);
                createSelectFont(48);

 //               Music music = Gdx.audio.newMusic(Gdx.files.internal("musicmenu.mp3"));
                assetManager.load("musicmenu.mp3", Music.class);
 //               createMusic("musicmenu.mp3");
                break;
            case RULES:
                assetManager.load("game" + packVersion + ".pack", TextureAtlas.class);
                createStdFont(32);
                createStdFont(96);
                createSelectFont(48);
            case GAME:
                assetManager.load("game" + packVersion + ".pack", TextureAtlas.class);
                createStdFont(48);
                createSelectFont(48);
                break;
            case OPTIONS:
                assetManager.load("game" + packVersion + ".pack", TextureAtlas.class);
                createStdFont(32);
                createStdFont(96);
                createSelectFont(48);
            case EXIT:
                assetManager.load("game" + packVersion + ".pack", TextureAtlas.class);
                createStdFont(48);
                createStdFont(96);
                createSelectFont(48);
                break;
            case DRUNKEXIT:
                assetManager.load("game" + packVersion + ".pack", TextureAtlas.class);
                createStdFont(48);
                createStdFont(96);
                createSelectFont(48);
                break;
            case SMOKEEXIT:
                assetManager.load("game" + packVersion + ".pack", TextureAtlas.class);
                createStdFont(48);
                createStdFont(96);
                createSelectFont(48);
                break;

        }
    }

    public void makeLinks() {
        atlas = assetManager.get("game" + packVersion + ".pack", TextureAtlas.class);
    }

// create standart font

    public void createStdFont(int size) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        fontParameter.fontFileName = "gomarice.ttf";
//        fontParameter.fontFileName = "FasterOne-Regular.ttf";
        fontParameter.fontParameters.size = size;
        fontParameter.fontParameters.color = Color.FIREBRICK;
        fontParameter.fontParameters.borderWidth = 1;
        fontParameter.fontParameters.borderColor = Color.BLACK;
        fontParameter.fontParameters.shadowOffsetX = 3;
        fontParameter.fontParameters.shadowOffsetY = 3;
        fontParameter.fontParameters.shadowColor = Color.BLACK;
        assetManager.load("gomarice" + size + ".ttf", BitmapFont.class, fontParameter);
//        assetManager.load("FasterOne-Regular" + size + ".ttf", BitmapFont.class, fontParameter);
    }

    // create selected font (for text highlight

    public void createSelectFont(int size) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        fontParameter.fontFileName = "gomarice.ttf";

        fontParameter.fontParameters.size = size;
//        fontParameter.fontParameters.color = Color.GOLDENROD;
        fontParameter.fontParameters.color = Color.RED;
        fontParameter.fontParameters.borderWidth = 1;
        fontParameter.fontParameters.borderColor = Color.BLACK;
        fontParameter.fontParameters.shadowOffsetX = 3;
        fontParameter.fontParameters.shadowOffsetY = 3;
        fontParameter.fontParameters.shadowColor = Color.BLACK;
//        assetManager.load("gomarice" + size + "s.ttf", BitmapFont.class, fontParameter);
        assetManager.load("gomarice" + size + "s.ttf", BitmapFont.class, fontParameter);
    }


//    public void createMusic(String musicFileName) {
//        FileHandleResolver resolver = new InternalFileHandleResolver();
//        assetManager.setLoader(Music.class, new MusicLoader(resolver));
//        assetManager.load(musicFileName, Music.class);
 //       setLoader(Music.class, new MusicLoader(resolver));
 //       Music music = Gdx.audio.newMusic(Gdx.files.internal("musicmenu.mp3"));
//        Music music = Gdx.audio.newMusic(Gdx.files.internal(musicFileName));

//    }

    public void clear() {
        assetManager.clear();
    }
}
