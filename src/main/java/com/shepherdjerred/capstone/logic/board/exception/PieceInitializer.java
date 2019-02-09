package com.shepherdjerred.capstone.logic.board.exception;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.player.Player;
import java.util.HashMap;
import java.util.Map;

// TODO refactor
public class PieceInitializer {
  /**
   * Initialize pawn pieces for the players
   */
  public static Map<Player, Coordinate> initializePawnLocations(BoardSettings boardSettings) {
    var gridSize = boardSettings.getGridSize();

    Map<Player, Coordinate> pawns = new HashMap<>();

    pawns.put(Player.ONE, getStartingPawnCoordinateForPlayerOne(gridSize));
    pawns.put(Player.TWO, getStartingPawnCoordinateForPlayerTwo(gridSize));

    if (boardSettings.getPlayerCount() == PlayerCount.FOUR) {
      pawns.put(Player.THREE, getStartingPawnCoordinateForPlayerThree(gridSize));
      pawns.put(Player.FOUR, getStartingPawnCoordinateForPlayerFour(gridSize));
    }

    return pawns;
  }

  public static Map<Coordinate, Piece> initializePieces(Map<Player, Coordinate> pawnLocations) {
    Map<Coordinate, Piece> pieces = new HashMap<>();
    pawnLocations.forEach((player, coordinate) -> pieces.put(coordinate, new PawnPiece(player)));
    return pieces;
  }

  private static Coordinate getStartingPawnCoordinateForPlayerOne(int gridSize) {
    var midpoint = gridSize / 2;
    return new Coordinate(midpoint, 0);
  }

  private static Coordinate getStartingPawnCoordinateForPlayerTwo(int gridSize) {
    var midpoint = gridSize / 2;
    return new Coordinate(midpoint, gridSize - 1);
  }

  private static Coordinate getStartingPawnCoordinateForPlayerThree(int gridSize) {
    var midpoint = gridSize / 2;
    return new Coordinate(0, midpoint);
  }

  private static Coordinate getStartingPawnCoordinateForPlayerFour(int gridSize) {
    var midpoint = gridSize / 2;
    return new Coordinate(gridSize - 1, midpoint);
  }
}
