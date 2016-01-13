package com.example.bekirc.minigolfapp.data.db.dao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.example.bekirc.minigolfapp.data.db.entity.Player;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PLAYER.
*/
public class PlayerDao extends AbstractDao<Player, Void> {

    public static final String TABLENAME = "PLAYER";

    /**
     * Properties of entity Player.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Name = new Property(0, String.class, "name", false, "NAME");
        public final static Property ScoreList = new Property(1, String.class, "scoreList", false, "SCORE_LIST");
        public final static Property TotalScore = new Property(2, Integer.class, "totalScore", false, "TOTAL_SCORE");
        public final static Property TotalTurnNumber = new Property(3, Integer.class, "totalTurnNumber", false, "TOTAL_TURN_NUMBER");
        public final static Property Date = new Property(4, String.class, "date", false, "DATE");
        public final static Property Id = new Property(5, long.class, "id", true, "_id");
    };

    private Query<Player> game_PlayerListQuery;

    public PlayerDao(DaoConfig config) {
        super(config);
    }
    
    public PlayerDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PLAYER' (" + //
                "'NAME' TEXT NOT NULL ," + // 0: name
                "'SCORE_LIST' TEXT," + // 1: scoreList
                "'TOTAL_SCORE' INTEGER," + // 2: totalScore
                "'TOTAL_TURN_NUMBER' INTEGER," + // 3: totalTurnNumber
                "'DATE' TEXT," + // 4: date
                "'_id' INTEGER PRIMARY KEY NOT NULL );"); // 5: id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PLAYER'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Player entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getName());
 
        String scoreList = entity.getScoreList();
        if (scoreList != null) {
            stmt.bindString(2, scoreList);
        }
 
        Integer totalScore = entity.getTotalScore();
        if (totalScore != null) {
            stmt.bindLong(3, totalScore);
        }
 
        Integer totalTurnNumber = entity.getTotalTurnNumber();
        if (totalTurnNumber != null) {
            stmt.bindLong(4, totalTurnNumber);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(5, date);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public Player readEntity(Cursor cursor, int offset) {
        Player entity = new Player( //
            cursor.getString(offset + 0), // name
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // scoreList
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // totalScore
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // totalTurnNumber
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // date
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Player entity, int offset) {
        entity.setName(cursor.getString(offset + 0));
        entity.setScoreList(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTotalScore(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setTotalTurnNumber(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setDate(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(Player entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(Player entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "playerList" to-many relationship of Game. */
    public List<Player> _queryGame_PlayerList(long id) {
        synchronized (this) {
            if (game_PlayerListQuery == null) {
                QueryBuilder<Player> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Id.eq(null));
                game_PlayerListQuery = queryBuilder.build();
            }
        }
        Query<Player> query = game_PlayerListQuery.forCurrentThread();
        query.setParameter(0, id);
        return query.list();
    }

}
