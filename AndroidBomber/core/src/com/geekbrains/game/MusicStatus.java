package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;

public class MusicStatus implements Serializable {

    public static final MusicStatus INSTANCE = new MusicStatus();
    private int musicOnOff;
    private int soundOnOff;

    private  MusicStatus() {
        this.musicOnOff = 1;
        this.soundOnOff = 1;
    }

    public int getMusicStatus() {
        if ( musicOnOff == 0) {
            return 0;
        }
        return 1;
    }

    public int getSoundStatus() {
        if ( soundOnOff == 0) {
            return 0;
        }
        return 1;
    }

    public void setMusicState(int musicOn) {
        if ( musicOn == 1) {
            musicOnOff = 1;
            tryToSaveMusicState();
        } else if (musicOn == 0) {
            musicOnOff = 0;
            tryToSaveMusicState();
        }
    }

    public void setSoundState(int soundOn) {
        if ( soundOn == 1) {
            soundOnOff = 1;
            tryToSaveSoundState();
        } else if (soundOn == 0) {
            soundOnOff = 0;
            tryToSaveSoundState();
        }
    }

    //try save music state to the file by handle
    public void tryToSaveMusicState() {
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
        System.out.println("Local Storage Path: " + localSavePath + " saved that music is " + musicOnOff);
        handleOut = Gdx.files.local("musicstate.dat");
        handleOut.writeString(Integer.toString(musicOnOff), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

    //try save sound state to the file by handle
    public void tryToSaveSoundState() {
        FileHandle handleOut = null;
        try{
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("Local Storage Path: " + localSavePath + " saved that sound is " + soundOnOff);
            handleOut = Gdx.files.local("soundstate.dat");
            handleOut.writeString(Integer.toString(soundOnOff), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*** Get system information
    //try to get property information
    public boolean tryToGetPropertyInformation() {
        System.out.println("java.version: " + System.getProperty("java.version"));
        System.out.println("java.home: " + System.getProperty("java.home"));
        System.out.println("java.class.path: " + System.getProperty("java.class.path"));
        System.out.println("java.io.tmpdir: " + System.getProperty("java.io.tmpdir"));
        System.out.println("os.name: " + System.getProperty("os.name"));
        System.out.println("user.name: " + System.getProperty("user.name"));
        System.out.println("user.home: " + System.getProperty("user.home"));
        System.out.println("user.dir: " + System.getProperty("user.dir"));
        System.out.println("user.country: " + System.getProperty("user.country"));
        System.out.println("user.timezone: " + System.getProperty("user.timezone"));
        System.out.println("java.language: " + System.getProperty("java.language"));
        System.out.println("Properties:");
        Properties mapProperties = System.getProperties();
        for (String objProperties: mapProperties.stringPropertyNames()){
            System.out.println(objProperties);
        }
         ***/


    //try to load music state from the file by fileHandle
    public void loadMusicState() {
        try {
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("loadMusicAndSoundStatte from Local Storage Path: " + localSavePath);
            boolean exists = Gdx.files.local("musicstate.dat").exists();
            if (exists == true) {
                System.out.println(localSavePath + "musicstate.dat exists");
            try {
                FileHandle handleIn = Gdx.files.local("musicstate.dat");
                musicOnOff = Integer.parseInt(handleIn.readString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            } else {
                System.out.println(localSavePath + "musicstate.dat does not exist");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    //try to load sound state from the file by fileHandle
    public void loadSoundState() {
        try {
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("loadSoundState from Local Storage Path: " + localSavePath);
            boolean exists = Gdx.files.local("soundstate.dat").exists();
            if (exists == true) {
                System.out.println(localSavePath + "soundstate.dat exists");
                try {
                    FileHandle handleIn = Gdx.files.local("soundstate.dat");
                    soundOnOff = Integer.parseInt(handleIn.readString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(localSavePath + "soundstate.dat does not exist");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }

}

