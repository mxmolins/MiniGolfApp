package com.example.bekirc.minigolfapp.misc;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bekirc.minigolfapp.data.Game;
import com.example.bekirc.minigolfapp.data.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author bekirc on 03.12.15.
 */
public class GameSaveSharePrefUtil {

    private static String GAME_FILE_NAME = "showCase";
    private static String GAME_LIST = "gameList";

    public static ArrayList<Game> getGameList(Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Game.class, new Game.GsonAdapter())
                .registerTypeAdapter(Player.class, new Player.GsonAdapter())
                .create();
        final SharedPreferences sharedPreferences = context.getSharedPreferences(GAME_FILE_NAME, Context.MODE_PRIVATE);
        HashSet<String> gameSet = (HashSet<String>) sharedPreferences.getStringSet(GAME_LIST, new HashSet<String>());
        return transformStringToGameList(gameSet, gson);
    }

    public static void setGameList(Context context, Game game) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Game.class, new Game.GsonAdapter())
                .registerTypeAdapter(Player.class, new Player.GsonAdapter())
                .create();
        ArrayList<Game> gameList = getGameList(context);
        ArrayList<Game> newGameList = new ArrayList<>();
        boolean gameIsAdded = false;
        for (Game gameItem : gameList) {
            if (gameItem.getStartingTimeInMillisecond() == game.getStartingTimeInMillisecond()) {
                newGameList.add(game);
                gameIsAdded = true;
            } else {
                newGameList.add(gameItem);
            }
        }

        if (!gameIsAdded) {
            newGameList.add(game);
        }

        HashSet<String> gameSet = new HashSet<>();
        for (Game gameItem : newGameList) {
            gameSet.add(gson.toJson(gameItem));
        }
        final SharedPreferences sharedPreferences = context.getSharedPreferences(GAME_FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putStringSet(GAME_LIST, gameSet).apply();
    }

    public static void deleteGame(Context context, Game game) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Game.class, new Game.GsonAdapter())
                .registerTypeAdapter(Player.class, new Player.GsonAdapter())
                .create();
        ArrayList<Game> gameList = getGameList(context);

        HashSet<String> gameSet = new HashSet<>();
        for (Game gameItem : gameList) {
            if (gameItem.getStartingTimeInMillisecond() != game.getStartingTimeInMillisecond()) {
                gameSet.add(gson.toJson(gameItem));
            }
        }
        final SharedPreferences sharedPreferences = context.getSharedPreferences(GAME_FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putStringSet(GAME_LIST, gameSet).apply();
    }

    private static ArrayList<Game> transformStringToGameList(HashSet<String> gameStringSet, Gson gson) {
        ArrayList<Game> gameList = new ArrayList<>();
        for (String gameAsString : gameStringSet) {
            Game game = gson.fromJson(gameAsString, Game.class);
            gameList.add(game);
        }
        return gameList;
    }
}
