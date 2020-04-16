package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Map implements Serializable {
    public enum CellType {
        EMPTY('0'), WALL('1'), BOX('2'), BOMB('_'), PLAYER_START('s'), BONUS('_');
//        , BOTTLE('b'), ZIPPO('z'), RAG('r');

        private char fileSymbol;

        CellType(char fileSymbol) {
            this.fileSymbol = fileSymbol;
        }

        public static CellType getCellTypeFromChar(char in) {
            for (int i = 0; i < CellType.values().length; i++) {
                if (CellType.values()[i].fileSymbol == in) {
                    return CellType.values()[i];
                }
            }
            throw new IllegalArgumentException("Invalid map cell symbol");
        }
    }

    protected enum DoorStatus {
        DOORCLOSED, DOOROPENED;
    }

    protected enum ArtefactStatus {
        PICKEDUP, LAYDOWN, INFORMED;
    }

    protected enum KeyStatus {
        KEYFOUND, KEYNOTFOUND;
    }

//    private Bomberman playerOwner;
    private int mapX, mapY;
    private CellType[][] data;
    private transient TextureRegion textureGrass, textureWall, textureBox, textureKey, textureDoor, textureDoorOpen, textureBonus, textureBottle, textureZippo, textureRag, textureArtefact;
    private Vector2 startPosition;
    private int doorPositionX, doorPositionY;
    private int keyPositionX, keyPositionY;
    private int artefactPositionX, artefactPositionY;
    protected DoorStatus currentDoorStatus;
    protected ArtefactStatus currentArtefactStatus;
    protected KeyStatus currentKeyStatus;
    private transient Sound doorOpenSound;
    private transient Sound artefactPickUpSound;



    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public Vector2 getStartPosition() {
        return startPosition;
    }

    public CellType getCellType(int cellX, int cellY) {
        return data[cellX][cellY];
    }

    public void reloadResources() {

//        textureBox = Assets.getInstance().getAtlas().findRegion("box");
        textureBox = Assets.getInstance().getAtlas().findRegion("can");
//        textureBox = Assets.getInstance().getAtlas().findRegion("cone");
        textureGrass = Assets.getInstance().getAtlas().findRegion("grass");
        textureWall = Assets.getInstance().getAtlas().findRegion("wall2");
        textureKey = Assets.getInstance().getAtlas().findRegion("key");
        textureDoor = Assets.getInstance().getAtlas().findRegion("door");
        textureDoorOpen = Assets.getInstance().getAtlas().findRegion("doorOpen");
        textureBonus = Assets.getInstance().getAtlas().findRegion("bonus");
        textureBottle = Assets.getInstance().getAtlas().findRegion("bottle2");
        textureZippo = Assets.getInstance().getAtlas().findRegion("zippo02");
        textureRag = Assets.getInstance().getAtlas().findRegion("rag");
//        this.doorOpenSound = Gdx.audio.newSound(Gdx.files.internal("dooropen.mp3"));
        this.doorOpenSound = Gdx.audio.newSound(Gdx.files.internal("artefactpickup.mp3"));
        this.artefactPickUpSound = Gdx.audio.newSound(Gdx.files.internal("artefactpickup.mp3"));
    }

    public Map() {

//        textureBox = Assets.getInstance().getAtlas().findRegion("box");
        textureBox = Assets.getInstance().getAtlas().findRegion("can");
//        textureBox = Assets.getInstance().getAtlas().findRegion("cone");
        textureGrass = Assets.getInstance().getAtlas().findRegion("grass");
        textureWall = Assets.getInstance().getAtlas().findRegion("wall2");
        textureKey = Assets.getInstance().getAtlas().findRegion("key");
        textureDoor = Assets.getInstance().getAtlas().findRegion("door");
        textureDoorOpen = Assets.getInstance().getAtlas().findRegion("doorOpen");
        textureBonus = Assets.getInstance().getAtlas().findRegion("bonus");
//        textureBottle = Assets.getInstance().getAtlas().findRegion("bottle2");
//        textureZippo = Assets.getInstance().getAtlas().findRegion("zippo");
//        textureRag = Assets.getInstance().getAtlas().findRegion("rag");
//        this.doorOpenSound = Gdx.audio.newSound(Gdx.files.internal("dooropen.mp3"));
        this.doorOpenSound = Gdx.audio.newSound(Gdx.files.internal("artefactpickup.mp3"));
        this.artefactPickUpSound = Gdx.audio.newSound(Gdx.files.internal("artefactpickup.mp3"));
    }

    public void loadMap(String mapName) {
        BufferedReader br = null;
        try {
            br = Gdx.files.internal(mapName).reader(8192);
            String str;
            mapX = Integer.parseInt(br.readLine());
            mapY = Integer.parseInt(br.readLine());
            data = new CellType[mapX][mapY];
            for (int i = mapY - 1; i >= 0; i--) {
                str = br.readLine();
                for (int j = 0; j < mapX; j++) {
                    char c = str.charAt(j);
                    data[j][i] = CellType.getCellTypeFromChar(c);
                    if (data[j][i] == CellType.PLAYER_START) {
                        startPosition = new Vector2(j * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, i * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE);
                        data[j][i] = CellType.EMPTY;
                    }
                }
            }

            // set key cell
            do {
                keyPositionX = MathUtils.random(0, mapX - 1);
                keyPositionY = MathUtils.random(0, mapY - 1);
//                System.out.println("keyX = " + keyPositionX + " keyY = " + keyPositionY);
            } while (data[keyPositionX][keyPositionY] != CellType.BOX);

            // set door cell
            do {
                do {
                    doorPositionX = MathUtils.random(0, mapX - 1);
                    doorPositionY = MathUtils.random(0, mapY - 1);
//                    System.out.println("doorX = " + doorPositionX + " doorY = " + doorPositionY);
                }while (checkCellForKey(doorPositionX, doorPositionY));
            }while (data[doorPositionX][doorPositionY] != CellType.BOX);

            // set artefact cell  ** fix it when reload

            do {
                do {
                    artefactPositionX = MathUtils.random(0, mapX - 1);
                    artefactPositionY = MathUtils.random(0, mapY - 1);
//                    System.out.println("artefactX = " + artefactPositionX + " artefactY = " + artefactPositionY);
                }while (checkCellForKey(artefactPositionX, artefactPositionY) || checkCellForDoor(artefactPositionX, artefactPositionY));
            }while (data[artefactPositionX][artefactPositionY] != CellType.BOX);

            currentDoorStatus = DoorStatus.DOORCLOSED;
            currentArtefactStatus = ArtefactStatus.LAYDOWN;
            currentKeyStatus = KeyStatus.KEYNOTFOUND;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < mapX; i++) {
            for (int j = 0; j < mapY; j++) {
                batch.draw(textureGrass, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);

                // draw key
                if (i == keyPositionX && j == keyPositionY && currentDoorStatus == DoorStatus.DOORCLOSED) {
                    batch.draw(textureKey, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                }
                if (i == keyPositionX && j == keyPositionY && currentDoorStatus == DoorStatus.DOOROPENED) {
                    batch.draw(textureGrass, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                }
                // draw artefact
                if (i == artefactPositionX && j == artefactPositionY && currentArtefactStatus != ArtefactStatus.PICKEDUP) {
                    if (GameLevel.INSTANCE.getGameLevel() == 1) {
                        textureArtefact = Assets.getInstance().getAtlas().findRegion("bottle3");  //choose artefact scin depends on game level
                    } else if(GameLevel.INSTANCE.getGameLevel() == 2){
                        textureArtefact = Assets.getInstance().getAtlas().findRegion("zippo02");
                    } else if(GameLevel.INSTANCE.getGameLevel() == 3) {
                        textureArtefact = Assets.getInstance().getAtlas().findRegion("rag");
                    } else
                        textureArtefact = Assets.getInstance().getAtlas().findRegion("grass");
                    batch.draw(textureArtefact, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                }
                if (i == artefactPositionX && j == artefactPositionY && currentArtefactStatus == ArtefactStatus.PICKEDUP) {
                    batch.draw(textureGrass, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                }
                // draw Door
                if (i == doorPositionX && j == doorPositionY && currentDoorStatus == DoorStatus.DOORCLOSED) {
                    batch.draw(textureDoor, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                }

                if (i == doorPositionX && j == doorPositionY && currentDoorStatus == DoorStatus.DOOROPENED) {
                    batch.draw(textureDoorOpen, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                }
                if (data[i][j] == CellType.WALL) {
                    batch.draw(textureWall, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                }
                if (data[i][j] == CellType.BONUS) {
                    batch.draw(textureBonus, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                }
                if (data[i][j] == CellType.BOX) {
                    batch.draw(textureBox, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                }
//                if (data[i][j] == CellType.BOTTLE) {
//                    batch. draw(textureBottle, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
//                }
//                if (data[i][j] == CellType.ZIPPO) {
//                    batch.draw(textureZippo, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
//                }
//                if (data[i][j] == CellType.RAG) {
//                    batch.draw(textureRag, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
//                }

            }
        }
    }

    // check properties of cells

    public boolean isCellEmpty(int cellX, int cellY) {
        return data[cellX][cellY] == CellType.EMPTY;
    }

     public boolean isCellPassable(int cellX, int cellY) {  //* FIX IT ArrayIndexOfBoundsException  плавающая ошибка
//         System.out.println("Map.isCellPassable X:" + cellX + " Y:" + cellY);
//        return data[cellX][cellY] == CellType.EMPTY || data[cellX][cellY] == CellType.BONUS || data[cellX][cellY] == CellType.PLAYER_START; // to fix frozen bot at start
         return data[cellX][cellY] == CellType.EMPTY || (data[cellX][cellY] == CellType.BONUS && data[cellX][cellY] != CellType.BOX) || data[cellX][cellY] == CellType.PLAYER_START; // to fix frozen bot at start
//         return data[cellX][cellY] != CellType.WALL || data[cellX][cellY] != CellType.BOX;
     }

    public boolean isCellDestructable(int cellX, int cellY) {
        return data[cellX][cellY] == CellType.BOX;
    }

    public boolean isCellBomb(int cellX, int cellY) {
        return data[cellX][cellY] == CellType.BOMB;
    }

    public boolean isCellUndestructable(int cellX, int cellY) {
        return data[cellX][cellY] == CellType.WALL;
    }

    public void setBombCell(int cellX, int cellY) {
        data[cellX][cellY] = CellType.BOMB;
    }

    public void clearCell(int cellX, int cellY) {
        data[cellX][cellY] = CellType.EMPTY;
    }

    public boolean checkCellForDoor(int cellX, int cellY) {
        return doorPositionX == cellX && doorPositionY == cellY;
    }

    public boolean checkCellForKey(int cellX, int cellY) {
        return keyPositionX == cellX && keyPositionY == cellY;
    }

    public boolean checkCellForBonus(int cellX, int cellY) {
        return data[cellX][cellY] == CellType.BONUS;
    }

    public boolean checkCellForArtefact(int cellX, int cellY){
        return artefactPositionX == cellX && artefactPositionY == cellY;
    }


    public void setBonus(int cellX, int cellY) {
//        if (!checkCellForDoor(cellX, cellY )|| !checkCellForKey(cellX, cellY)) { - first version of check key
        if (cellX != keyPositionX && cellY != keyPositionY) {
            if (cellX != doorPositionX && cellY != doorPositionY) {
                if (cellX != artefactPositionX && cellY != artefactPositionY) {  // check artefact position (bottle, rag, zippo)
                    data[cellX][cellY] = CellType.BONUS;
                }
            }
        }
    }

    public boolean setArtefact(int cellX, int cellY) {
        if (data[cellX][cellY] == CellType.BOX) {
            if (cellX != keyPositionX && cellY != keyPositionY) {
                if (cellX != doorPositionX && cellY != doorPositionY) {
                    artefactPositionX = cellX;
                    artefactPositionY = cellY;
                    return true;
                }
            }
        }
        return false;
    }
/**
    **/

    public DoorStatus setDoorStatusOpened() {
        if(currentDoorStatus == DoorStatus.DOORCLOSED) {
            if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
                doorOpenSound.play();
            }
        }
        return currentDoorStatus = currentDoorStatus.DOOROPENED;
    }

    public ArtefactStatus setArtefactStatusPickedUp() {
        if (MusicStatus.INSTANCE.getSoundStatus() == 1) {
            if(GameLevel.INSTANCE.getGameLevel() < 4) {
//DELETE IF NOT NEEDED               int currentlevel = GameLevel.INSTANCE.getGameLevel(); //for debug sount picked up artefact
//DELETE IF NOT NEEDED               System.out.println("Map.setArtefactStatusPickedUp.Row" + Thread.currentThread().getStackTrace()[1].getLineNumber() + " "); //Trace to debug);
//DELETE IF NOT NEEDED               System.out.println("Current level for artefact pick up" + currentlevel);//for debug sount picked up artefact
                artefactPickUpSound.play();
            }
        }
        return currentArtefactStatus = currentArtefactStatus.PICKEDUP;
    }

    public ArtefactStatus setArtefactStatusInformed() {
        return currentArtefactStatus = currentArtefactStatus.INFORMED;
    }

    public ArtefactStatus checkArtefactStatus(){
        return currentArtefactStatus;
    }

    public KeyStatus checkKeyStatus() {
        return currentKeyStatus;
    }

    public KeyStatus setKeyStatusFound() {
        return currentKeyStatus = currentKeyStatus.KEYFOUND;
    }
}
