package com.example.bekirc.minigolfapp.data.db.entity;

import java.util.List;
import com.example.bekirc.minigolfapp.data.db.dao.DaoSession;
import de.greenrobot.dao.DaoException;

import com.example.bekirc.minigolfapp.data.db.dao.GameDao;
import com.example.bekirc.minigolfapp.data.db.dao.PlayerDao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table GAME.
 */
public class Game {

    private long id;
    private String status;
    private java.util.Date date;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient GameDao myDao;

    private List<Player> playerList;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Game() {
    }

    public Game(long id) {
        this.id = id;
    }

    public Game(long id, String status, java.util.Date date) {
        this.id = id;
        this.status = status;
        this.date = date;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGameDao() : null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Player> getPlayerList() {
        if (playerList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlayerDao targetDao = daoSession.getPlayerDao();
            List<Player> playerListNew = targetDao._queryGame_PlayerList(id);
            synchronized (this) {
                if(playerList == null) {
                    playerList = playerListNew;
                }
            }
        }
        return playerList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetPlayerList() {
        playerList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
