package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class HpCount implements Serializable {
    public static final HpCount INSTANCE = new HpCount();
    private int hpCount;
    private int maxHpCount;

    private  HpCount() {
        this.hpCount = 3;
        this.maxHpCount = 3;
    }

    public int getHpCount() {
        return hpCount;
    }

    public int getMaxHpCount()  { return maxHpCount; }

    public void setHpCount(int hp) {
        hpCount = hp;
        System.out.println("HpCount.setHpCount. Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " ");
        System.out.println("hpCount = " + hpCount);
        tryToSaveHpCount();
    }

    public void setMaxHpCount(int maxHp) {
        maxHpCount = maxHp;
        System.out.println("HpCount.setMaxHpCount. Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " ");
        System.out.println("maxHpCount = " + maxHpCount);
        tryToSaveMaxHpCount();
    }


    //try save hp count to the file by handle
    public void tryToSaveHpCount() {
        FileHandle handleOut = null;
        try{
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("HpCount.tryToSaveHpCount.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " ");
            System.out.println("Local Storage Path: " + localSavePath + " saved that HP is " + hpCount);
            handleOut = Gdx.files.local("hpcount.dat");
            handleOut.writeString(Integer.toString(hpCount), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //try save maxHp count to the file by handle
    public void tryToSaveMaxHpCount() {
        FileHandle handleOut = null;
        try{
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("HpCount.tryToSaveMaxHpCount.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " ");
            System.out.println("Local Storage Path: " + localSavePath + " saved that MaxHp is " + maxHpCount);
            handleOut = Gdx.files.local("maxhpcount.dat");
            handleOut.writeString(Integer.toString(maxHpCount), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //try to load hp count from the file by fileHandle
    public void loadHpCount() {
        try {
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("HpCount.loadHpCount.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " " );
            System.out.println("Load hp count from Local Storage Path: " + localSavePath);
            boolean exists = Gdx.files.local("hpcount.dat").exists();
            if (exists == true) {
                System.out.println("HpCount row77." + localSavePath + "hpcount.dat exists");
                try {
                    FileHandle handleIn = Gdx.files.local("hpcount.dat");
                    hpCount = Integer.parseInt(handleIn.readString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("HpCount row85." + localSavePath + "hpcount.dat does not exist");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    //try to load hp count from the file by fileHandle
    public void loadMaxHpCount() {
        try {
            String localSavePath = Gdx.files.getLocalStoragePath();
            System.out.println("HpCount.loadMaxHpCount.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " " );
            System.out.println("Load hp count from Local Storage Path: " + localSavePath);
            boolean exists = Gdx.files.local("maxhpcount.dat").exists();
            if (exists == true) {
                System.out.println("HpCount row101." + localSavePath + "maxhpcount.dat exists");
                try {
                    FileHandle handleIn = Gdx.files.local("maxhpcount.dat");
                    maxHpCount = Integer.parseInt(handleIn.readString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("HpCount row109." + localSavePath + "maxhpcount.dat does not exist");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }
}
