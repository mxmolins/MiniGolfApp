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
import java.util.ArrayList;
import java.util.List;

/**
 * @author bekirc on 22.11.15.
 */
public class Game implements Parcelable {

    private static final String DATE = "date";
    private static final String STATUS = "status";
    private static final String PLAYER_LIST = "playerList";
    private static final String TOTAL_TURN_NUMBER = "totalTurnNumber";
    private static final String CURRENT_TURN = "currentTurn";
    private static final String MILISECOND = "milisecond";

    private String date;
    private String status = "";
    private int totalTurnNumber;
    private int currentTurn;
    private long startingTimeInMilisecond;
    private List<Player> playerList;

    public Game(String date, int totalTurnNumber, long startingTimeInMilisecond) {
        this.date = date;
        this.totalTurnNumber = totalTurnNumber;
        this.currentTurn = 0;
        this.startingTimeInMilisecond = startingTimeInMilisecond;
        playerList = new ArrayList<>();
    }

    public Game(JsonObject jsonObject, JsonDeserializationContext context) {
        this.date = context.deserialize(jsonObject.get(DATE), String.class);
        this.status = context.deserialize(jsonObject.get(STATUS), String.class);
        this.currentTurn = context.deserialize(jsonObject.get(CURRENT_TURN), Integer.class);
        this.totalTurnNumber = context.deserialize(jsonObject.get(TOTAL_TURN_NUMBER), Integer.class);
        this.startingTimeInMilisecond = context.deserialize(jsonObject.get(MILISECOND), Long.class);
        this.playerList = new ArrayList<>();
        JsonArray playerListJsonArray = jsonObject.getAsJsonArray(PLAYER_LIST);
        if (playerListJsonArray != null) {
            for (JsonElement jsonElement : playerListJsonArray) {
                playerList.add((Player) context.deserialize(jsonElement, Player.class));
            }
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public void addPlayer(Player player) {
        if (playerList != null) {
            playerList.add(player);
        }
    }

    public int getTotalTurnNumber() {
        return totalTurnNumber;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public long getStartingTimeInMilisecond() {
        return startingTimeInMilisecond;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public ArrayList<Player> getPlayerList() {
        return (ArrayList<Player>) playerList;
    }

    protected Game(Parcel in) {
        date = in.readString();
        status = in.readString();
        totalTurnNumber = in.readInt();
        currentTurn = in.readInt();
        startingTimeInMilisecond = in.readLong();
        playerList = new ArrayList<>();
        playerList = in.createTypedArrayList(Player.CREATOR);
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(status);
        dest.writeInt(totalTurnNumber);
        dest.writeInt(currentTurn);
        dest.writeLong(startingTimeInMilisecond);
        dest.writeTypedList(playerList);
    }

    public static class GsonAdapter implements JsonSerializer<Game>, JsonDeserializer<Game> {
        @Override
        public JsonElement serialize(Game src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.add(DATE, new JsonPrimitive(src.date));
            result.add(STATUS, new JsonPrimitive(src.status));
            result.add(TOTAL_TURN_NUMBER, new JsonPrimitive(src.totalTurnNumber));
            result.add(CURRENT_TURN, new JsonPrimitive(src.currentTurn));
            result.add(MILISECOND, new JsonPrimitive(src.startingTimeInMilisecond));
            JsonArray scoreListJsonArray = new JsonArray();
            for (Player player : src.playerList) {
                scoreListJsonArray.add(context.serialize(player));
            }
            result.add(PLAYER_LIST, scoreListJsonArray);
            return result;
        }

        @Override
        public Game deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Game(json.getAsJsonObject(), context);
        }
    }
}
