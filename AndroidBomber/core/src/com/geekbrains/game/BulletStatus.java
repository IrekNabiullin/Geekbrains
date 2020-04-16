package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class BulletStatus implements Serializable {

    public static final BulletStatus INSTANCE = new BulletStatus();
    private int playerBulletOnOff;
    private int botBulletOnOff;

    private  BulletStatus() {
        this.playerBulletOnOff = 1;
        this.botBulletOnOff = 1;
    }

    public int getPlayerBulletStatus() {
        if ( playerBulletOnOff == 0) {
            return 0;
        }
        return 1;
    }

    public int getBotBulletStatus() {
        if ( botBulletOnOff == 0) {
            return 0;
        }
        return 1;
    }

    public void setPlayerBulletStatus(int playerBulletOn) {
        if ( playerBulletOn == 1) {
            playerBulletOnOff = 1;
            tryToSavePlayerBulletStatus();
        } else if (playerBulletOn == 0) {
            playerBulletOnOff = 0;
            tryToSavePlayerBulletStatus();
        }
    }

    public void setBotBulletStatus(int botBulletOn) {
        if ( botBulletOn == 1) {
            botBulletOnOff = 1;
            tryToSaveBotBulletStatus();
        } else if (botBulletOn == 0) {
            botBulletOnOff = 0;
            tryToSaveBotBulletStatus();
        }
    }

    //try save bullet status to the file by handle
    public void tryToSavePlayerBulletStatus() {
        FileHandle handleOut = null;
// debug information about eternal and local path to files
//        boolean isExtAvailable = Gdx.files.isExternalStorageAvailable();
//        boolean isLocAvailable = Gdx.files.isLocalStorageAvailable();
//        System.out.println("isExtAvailable: " + isExtAvailable);
//        System.out.println("isLocAvailable: " + isLocAvailable);
//        String extRoot = Gdx.files.getExternalStoragePath();
//        String locRoot = Gdx.files.getLocalStoragePath();
//        System.out.println(("extRoot = " + extRoot));
//        System.out.println(("locRoot = " + locRoot));
        try{
            String localSavePath = Gdx.files.getLocalStoragePath();
//            System.out.println("BulletStatus.tryToSavePlayerBulletStatus.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " ");

//            System.out.println("Local Storage Path: " + localSavePath + " saved that player bullet is " + playerBulletOnOff);
            handleOut = Gdx.files.local("pl_bullets.dat");
            handleOut.writeString(Integer.toString(playerBulletOnOff), false);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Coud not save player's bullet status.");
        }
    }

    //try save bot status to the file by handle
    public void tryToSaveBotBulletStatus() {
        FileHandle handleOut = null;
        try{
            String localSavePath = Gdx.files.getLocalStoragePath();
//            System.out.println("Local Storage Path: " + localSavePath + " saved that bot bullet is " + botBulletOnOff);
            handleOut = Gdx.files.local("botbullets.dat");
            handleOut.writeString(Integer.toString(botBulletOnOff), false);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Coud not save bot's bullet status.");
        }
    }

    //try save temporary bullet status to the file by handle
    public void tryToSaveTempPlayerBulletStatus() {
        FileHandle handleOut = null;
        try{
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("Local Storage Path: " + localSavePath + " saved that player bullet is " + playerBulletOnOff);
            handleOut = Gdx.files.local("pl_temp_bullets.dat");
            handleOut.writeString(Integer.toString(playerBulletOnOff), false);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Coud not save temporary player's bullet status.");
        }
    }

    //try save temporary bot status to the file by handle
    public void tryToSaveTempBotBulletStatus() {
        FileHandle handleOut = null;
        try{
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("Local Storage Path: " + localSavePath + " saved that bot bullet is " + botBulletOnOff);
            handleOut = Gdx.files.local("bottempbullets.dat");
            handleOut.writeString(Integer.toString(botBulletOnOff), false);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Coud not save temporary bot's bullet status.");
        }
    }


    //try to load bullet status from the file by fileHandle
    public void loadPlayerBulletStatus() {
        try {
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("loadPlayerBulletStatus from Local Storage Path: " + localSavePath);
            boolean exists = Gdx.files.local("pl_bullets.dat").exists();
            if (exists == true) {
                System.out.println(localSavePath + "pl_bullets.dat exists");
                try {
                    FileHandle handleIn = Gdx.files.local("pl_bullets.dat");
                    playerBulletOnOff = Integer.parseInt(handleIn.readString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(localSavePath + "pl_bullets.dat does not exist");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    //try to load bot's bullet status from the file by fileHandle
    public void loadBotBulletStatus() {
        try {
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("loadBotBulletStatus from Local Storage Path: " + localSavePath);
            boolean exists = Gdx.files.local("botbullets.dat").exists();
            if (exists == true) {
                System.out.println(localSavePath + "botbullets.dat exists");
                try {
                    FileHandle handleIn = Gdx.files.local("botbullets.dat");
                    botBulletOnOff = Integer.parseInt(handleIn.readString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(localSavePath + "botbullets.dat does not exist");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //try to load temporary player's bullet status from the file by fileHandle
    public void loadTempPlayerBulletStatus() {
        try {
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("loadPlayerBulletStatus from Local Storage Path: " + localSavePath);
            boolean exists = Gdx.files.local("pl_temp_bullets.dat").exists();
            if (exists == true) {
                System.out.println(localSavePath + "pl_temp_bullets.dat exists");
                try {
                    FileHandle handleIn = Gdx.files.local("pl_temp_bullets.dat");
                    playerBulletOnOff = Integer.parseInt(handleIn.readString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(localSavePath + "pl_temp_bullets.dat does not exist");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //try to load bot's bullet status from the file by fileHandle
    public void loadTempBotBulletStatus() {
        try {
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("loadBotBulletStatus from Local Storage Path: " + localSavePath);
            boolean exists = Gdx.files.local("bottempbullets.dat").exists();
            if (exists == true) {
                System.out.println(localSavePath + "bottempbullets.dat exists");
                try {
                    FileHandle handleIn = Gdx.files.local("bottempbullets.dat");
                    botBulletOnOff = Integer.parseInt(handleIn.readString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(localSavePath + "bottempbullets.dat does not exist");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }

}

