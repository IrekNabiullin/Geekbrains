package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map {
    public static final int CELL_EMPTY = 0;
    public static final int CELL_WALL = 1;
    public static final int CELL_BOX = 2;
    public static final int CELL_BOMB = 3;
    public static final int CELL_EXIT_CLOSED = 4;
    public static final int CELL_EXIT_OPENED = 5;

    private int mapX, mapY;
    private byte[][] data;
    private TextureRegion textureGrass, textureWall, textureBox, textureExitClosed, textureExitOpened;
    private Vector2 startPosition;

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public Vector2 getStartPosition() {
        return startPosition;
    }

    public Map() {
        textureBox = Assets.getInstance().getAtlas().findRegion("box");
        textureGrass = Assets.getInstance().getAtlas().findRegion("grass");
        textureWall = Assets.getInstance().getAtlas().findRegion("wall");
        textureExitClosed = Assets.getInstance().getAtlas().findRegion("box");
        textureExitOpened = Assets.getInstance().getAtlas().findRegion("exit");
        loadMap("map2.dat");
    }

    public void loadMap(String mapName) {
        BufferedReader br = null;
        try {
            br = Gdx.files.internal(mapName).reader(8192);
            String str;
            mapX = Integer.parseInt(br.readLine());
            mapY = Integer.parseInt(br.readLine());
            data = new byte[mapX][mapY];
            for (int i = mapY - 1; i >= 0; i--) {
                str = br.readLine();
                for (int j = 0; j < mapX; j++) {
                    char c = str.charAt(j);
                    switch (c) {
                        case '0':
                            data[j][i] = CELL_EMPTY;
                            break;
                        case '1':
                            data[j][i] = CELL_WALL;
                            break;
                        case '2':
                            data[j][i] = CELL_BOX;
                            System.out.println("j = " + j + " i = " + i);
                            break;
 //                       case '4':     if we want to put stable exit (not random)
//                            data[j][i] = CELL_EXIT_CLOSED;
                        case 's':
                            startPosition = new Vector2(j * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, i * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setRandomExit();
    }

    public void setRandomExit() {
        int cx = -1, cy = -1;
        do {
            cx = MathUtils.random(0, this.getMapX() - 1);
            cy = MathUtils.random(0, this.getMapY() - 1);
        } while (!this.isCellDestructable(cx, cy));
        data[cx][cy] = CELL_EXIT_CLOSED;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < mapX; i++) {
            for (int j = 0; j < mapY; j++) {
                batch.draw(textureGrass, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                if (data[i][j] == CELL_WALL) {
                    batch.draw(textureWall, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                }
                if (data[i][j] == CELL_BOX) {
                    batch.draw(textureBox, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                }
                if (data[i][j] == CELL_EXIT_CLOSED) {
                    batch.draw(textureExitClosed, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                }
                if (data[i][j] == CELL_EXIT_OPENED) {
                    batch.draw(textureExitOpened, i * Rules.CELL_SIZE, j * Rules.CELL_SIZE);
                }
            }
        }
    }

    public boolean isCellEmpty(int cellX, int cellY) {
        return data[cellX][cellY] == CELL_EMPTY;
    }

    public boolean isCellDestructable(int cellX, int cellY) {
        return data[cellX][cellY] == CELL_BOX;
    }

    public boolean isCellBomb(int cellX, int cellY) {
        return data[cellX][cellY] == CELL_BOMB;
    }

    public boolean isCellUndestructable(int cellX, int cellY) {
        return data[cellX][cellY] == CELL_WALL;
    }
    public boolean isCellExitClosed(int cellX, int cellY) {
        return data[cellX][cellY] == CELL_EXIT_CLOSED;
    }

    public boolean isCellExitOpened(int cellX, int cellY) {
        return data[cellX][cellY] == CELL_EXIT_OPENED;
    }

    public void openExit(int cellX, int cellY) {
        data[cellX][cellY] = CELL_EXIT_OPENED;
    }

    public void setBombCell(int cellX, int cellY) {
        data[cellX][cellY] = CELL_BOMB;
    }

    public void clearCell(int cellX, int cellY) {
        data[cellX][cellY] = CELL_EMPTY;
    }

}
