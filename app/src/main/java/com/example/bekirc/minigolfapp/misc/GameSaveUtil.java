package com.example.bekirc.minigolfapp.misc;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.bekirc.minigolfapp.data.Game;
import com.example.bekirc.minigolfapp.data.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author bekirc on 03.12.15.
 */
public class GameSaveUtil {

    private static final String FILE_NAME = "gameList";

    public static void writeGameToFile(Context context,
                                       Game game) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Game.class, new Game.GsonAdapter())
                .registerTypeAdapter(Player.class, new Player.GsonAdapter())
                .create();

        String json = gson.toJson(game);

        try {
            FileOutputStream outputStream = context.openFileOutput(
                    FILE_NAME,
                    Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readGameFromFile(Context context,
                                        @NonNull Game game) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Game.class, new Game.GsonAdapter())
                .registerTypeAdapter(Player.class, new Player.GsonAdapter())
                .create();

        StringBuilder stringBuilder = getStringFromFile(context);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static StringBuilder getStringFromFile(final Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream inputStream = context.openFileInput(
                    FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder;
    }
}
