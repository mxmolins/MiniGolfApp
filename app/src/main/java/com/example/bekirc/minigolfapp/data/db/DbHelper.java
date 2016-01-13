package com.example.bekirc.minigolfapp.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.bekirc.minigolfapp.App;
import com.example.bekirc.minigolfapp.data.db.dao.DaoMaster;
import com.example.bekirc.minigolfapp.data.db.dao.DaoSession;

/**
 * @author bekirc on 02.12.15.
 */
public class DbHelper {
    private static DbHelper instance;
    private final DaoSession daoSession;
    private final DaoMaster daoMaster;

    public synchronized static DbHelper getInstance() {
        if (instance == null) {
            instance = new DbHelper();
        }
        return instance;
    }

    public DbHelper() {
        Context appContext = App.getInstance().getApplicationContext();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(appContext, "minigolfapp", null);
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }
}
