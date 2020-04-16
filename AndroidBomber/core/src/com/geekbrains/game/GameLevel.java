package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class GameLevel implements Serializable {
    public static final GameLevel INSTANCE = new GameLevel();
    private int level;

    private  GameLevel() {
        this.level = 1;
    }

    public int getGameLevel() {
            return level;
        }


    public void setGameLevel(int levelNumber) {
        level = levelNumber;
        tryToSaveGameLevel();
        }


    //try save game level to the file by handle
    public void tryToSaveGameLevel() {
        FileHandle handleOut = null;
        try{
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("GameLevel Row33.Local Storage Path: " + localSavePath + " saved that level is " + level);
            handleOut = Gdx.files.local("gamelevel.dat");
            handleOut.writeString(Integer.toString(level), false);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GameLevel Row38. Could not save game level.");
        }
    }

/**
    //try save savedgame level to the file by handle
    public void tryToSaveSavedgameLevel() {
        FileHandle handleOut = null;
        try{
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("GameLevel Row47. Local Storage Path: " + localSavePath + " saved that level is " + level);
            handleOut = Gdx.files.local("gamesavedlevel.dat");
            handleOut.writeString(Integer.toString(level), false);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GameLevel Row52.Coud not save game level.");
        }
    }
*/

    //try save temporary game level to the file by handle
    public void tryToSaveTempGameLevel() {
        FileHandle handleOut = null;
        try{
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("GameLevel Row61.Local Storage Path: " + localSavePath + " saved that level is " + level);
            handleOut = Gdx.files.local("gametemplevel.dat");
            handleOut.writeString(Integer.toString(level), false);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GameLevel Row66.Could not save temp game level.");
        }
    }


    //try to load game level from the file by fileHandle
    public int loadGameLevel() {
        try {
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("GameLevel Row75.load game level from Local Storage Path: " + localSavePath);
            boolean exists = Gdx.files.local("gamelevel.dat").exists();
            if (exists == true) {
                System.out.println("GameLevel Row78." + localSavePath + "gamelevel.dat exists");
                try {
                    FileHandle handleIn = Gdx.files.local("gamelevel.dat");
                    level = Integer.parseInt(handleIn.readString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(localSavePath + "gamelevel.dat does not exist");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return level;
    }

    //try to load game level from the file by fileHandle
    public int loadSavedgameLevel() {
        try {
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("GameLevel Row98.Load savedgame level from Local Storage Path: " + localSavePath);
            boolean exists = Gdx.files.local("gamesavedlevel.dat").exists();
            if (exists == true) {
                System.out.println("GameLevel Row101." + localSavePath + "gamesavedlevel.dat exists");
                try {
                    FileHandle handleIn = Gdx.files.local("gamesavedlevel.dat");
                    level = Integer.parseInt(handleIn.readString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("GameLevel Row109." + localSavePath + "gamesavedlevel.dat does not exist");
//                System.out.println("GameLevel Row110. Current game level = " + level);
                HpCount.INSTANCE.setHpCount(3);
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return level;
    }

    //try to load game level from the file by fileHandle
    public void loadTempGameLevel() {
        try {
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("GameLevel Row121. Load game level from Local Storage Path: " + localSavePath);
            boolean exists = Gdx.files.local("gametemplevel.dat").exists();
            if (exists == true) {
                System.out.println("GameLevel Row124. " + localSavePath + "gametemplevel.dat exists");
                try {
                    FileHandle handleIn = Gdx.files.local("gametemplevel.dat");
                    level = Integer.parseInt(handleIn.readString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("GameLevel Row132. " + localSavePath + "gametemplevel.dat does not exist");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }
}
