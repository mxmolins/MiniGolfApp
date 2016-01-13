package com.example.bekirc.minigolfapp.daogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class MyDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.example.bekirc.minigolfapp.data.db.entity");
        schema.setDefaultJavaPackageDao("com.example.bekirc.minigolfapp.data.db.dao");
        schema.enableKeepSectionsByDefault();

        Entity game = schema.addEntity("Game");
        Property gameId = game.addIdProperty().index().notNull().getProperty();
        game.addStringProperty("status");
        game.addDateProperty("date");

        Entity player = schema.addEntity("Player");
        player.addStringProperty("name").notNull();
        player.addStringProperty("scoreList");
        player.addIntProperty("totalScore");
        player.addIntProperty("totalTurnNumber");
        player.addStringProperty("date");

        ToMany gameToPlayer = game.addToMany(player, gameId);
        gameToPlayer.setName("playerList");

        new DaoGenerator().generateAll(schema, "../MiniGolfApp/app/src/main/java");
    }
}
