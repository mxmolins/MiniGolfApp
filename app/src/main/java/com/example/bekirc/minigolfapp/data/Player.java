package com.example.bekirc.minigolfapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * @author bekirc on 22.11.15.
 */
public class Player implements Parcelable {

    private static final String NAME = "name";
    private static final String TOTAL_TURN_NUMBER = "totalTurnNumber";
    private static final String SCORE_LIST = "scoreList";

    private String name;
    private int totalTurnNumber;
    private int[] scoreList;

    public Player(String name, int totalTurnNumber) {
        this.name = name;
        this.totalTurnNumber = totalTurnNumber;
        this.scoreList = new int[totalTurnNumber];
        for(int i = 0; i < totalTurnNumber; i++){
            scoreList[i] = 1;
        }
    }

    public Player(JsonObject jsonObject, JsonDeserializationContext context) {
        this.name = context.deserialize(jsonObject.get(NAME), String.class);
        this.totalTurnNumber = context.deserialize(jsonObject.get(TOTAL_TURN_NUMBER), Integer.class);
        this.scoreList = new int[totalTurnNumber];
        JsonArray scoreListJsonArray = jsonObject.getAsJsonArray(SCORE_LIST);
        if (scoreListJsonArray != null) {
            int i = 0;
            for (JsonElement jsonElement : scoreListJsonArray) {
                scoreList[i] = jsonElement.getAsInt();
                i++;
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        int totalScore = 0;
        for (int score : scoreList) {
            totalScore += score;
        }
        return totalScore;
    }

    public int getScoreByTurnNumber(int turnNumber) {
        //cause array starts from 0 but turns starts from 1
        turnNumber = turnNumber - 1;
        if (scoreList == null || scoreList.length < turnNumber) {
            return 0;
        }
        return scoreList[turnNumber];
    }

    public void setScoreByTurnNumber(int turnNumber, int scoreByTurn) {
        turnNumber = turnNumber - 1;
        scoreList[turnNumber] = scoreByTurn;
    }

    public void increaseScoreByTurnNumber(int turnNumber) {
        scoreList[turnNumber - 1]++;
    }

    public void decreaseScoreByTurnNumber(int turnNumber) {
        scoreList[turnNumber - 1]--;
    }

    protected Player(Parcel in) {
        name = in.readString();
        totalTurnNumber = in.readInt();
        scoreList = new int[totalTurnNumber];
        in.readIntArray(scoreList);
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(totalTurnNumber);
        dest.writeIntArray(scoreList);
    }

    public static class GsonAdapter implements JsonSerializer<Player>, JsonDeserializer<Player> {
        @Override
        public JsonElement serialize(Player src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            if (src.name == null) {
                src.name = "";
            }
            result.add(NAME, new JsonPrimitive(src.name));
            result.add(TOTAL_TURN_NUMBER, new JsonPrimitive(src.totalTurnNumber));
            JsonArray scoreListJsonArray = new JsonArray();
            for (int score : src.scoreList) {
                scoreListJsonArray.add(new JsonPrimitive(score));
            }
            result.add(SCORE_LIST, scoreListJsonArray);
            return result;
        }

        @Override
        public Player deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Player(json.getAsJsonObject(), context);
        }
    }
}
