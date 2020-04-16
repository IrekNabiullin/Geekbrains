package com.geekbrains.game;

import java.io.ObjectStreamException;

public class TempSavedGameStatus {
    public static final TempSavedGameStatus INSTANCE = new TempSavedGameStatus();

    private boolean isTempSavedGame;

    private  TempSavedGameStatus() {
        this.isTempSavedGame = false;
    }

    public int getTempSavedGameState() {
        if ( isTempSavedGame == false) {
            return 0;
        }
        return 1;
    }

    public void setTempSavedGameState(int tempSavedGameExists) {
        if (tempSavedGameExists == 1) {
            isTempSavedGame = true;
        } else if (tempSavedGameExists == 0) {
            isTempSavedGame = false;
        }
    }


 //   private Object readResolve() throws ObjectStreamException {
 //       return INSTANCE;
 //   }

}
