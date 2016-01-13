package com.example.bekirc.minigolfapp.data.db.model;

import com.example.bekirc.minigolfapp.data.db.DbHelper;
import com.example.bekirc.minigolfapp.data.db.dao.GameDao;

/**
 * @author bekirc on 02.12.15.
 */
public class GameModel {
    private static GameModel instance;
    private final GameDao gameDao;

    public synchronized static GameModel getInstance() {
        if (instance == null) {
            instance = new GameModel();
        }
        return instance;
    }

    private GameModel() {
        this.gameDao = DbHelper.getInstance().getDaoSession().getGameDao();
    }
}
