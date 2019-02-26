package com.shepherdjerred.capstone.logic.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.shepherdjerred.capstone.logic.board.BoardPieces;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class BoardPiecesDeserializer implements JsonDeserializer<BoardPieces> {

  @Override
  public BoardPieces deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    var piecesObj = json.getAsJsonObject().get("pieces");
    var piecesType = new TypeToken<HashMap<Coordinate, Piece>>() {
    }.getType();

    System.out.println(piecesObj.getAsJsonObject());

    Map<Coordinate, Piece> pieces = context.deserialize(piecesObj.getAsJsonObject(), piecesType);

    var pawnLocationsType = new TypeToken<HashMap<PlayerId, Coordinate>>() {
    }.getType();
    var pawnLocationsObj = json.getAsJsonObject().get("pawnLocations");
    Map<PlayerId, Coordinate> pawnLocations = context.deserialize(pawnLocationsObj.getAsJsonObject(),
        pawnLocationsType);

    var boardSettingsObj = json.getAsJsonObject().get("boardSettings");
    BoardSettings boardSettings = context.deserialize(boardSettingsObj, BoardSettings.class);

    return new BoardPieces(pieces, pawnLocations, boardSettings);
  }
}
