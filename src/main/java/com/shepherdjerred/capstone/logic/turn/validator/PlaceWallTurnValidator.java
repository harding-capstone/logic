package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum PlaceWallTurnValidator implements TurnValidator {
  INSTANCE;

  @Override
  public boolean isTurnValid(Turn turn, Match match) {
    if (turn instanceof PlaceWallTurn) {
      return isPlaceWallTurnValid((PlaceWallTurn) turn, match);
    } else {
      throw new IllegalArgumentException("Turn is not a PlaceWallTurn " + turn);
    }
  }

  public boolean isPlaceWallTurnValid(PlaceWallTurn turn, Match match) {
    var player = turn.getCauser();
    var board = match.getBoard();
    return doesPlayerHaveWalls(player, match)
        && canPlaceWall(turn, board);
  }

  public boolean canPlaceWall(PlaceWallTurn turn, Board board) {
    return isLocationOpen(turn, board)
        && willWallBlock(turn, board);
  }

  public boolean isWallCell(PlaceWallTurn turn, Board board) {
    var firstCoordinate = turn.getFirstCoordinate();
    var secondCoordinate = turn.getSecondCoordinate();

    return board.isWallCell(firstCoordinate)
        && board.isWallCell(secondCoordinate);
  }

  public boolean isLocationOpen(PlaceWallTurn turn, Board board) {
    var firstCoordinate = turn.getFirstCoordinate();
    var secondCoordinate = turn.getSecondCoordinate();

    return board.isCellEmpty(firstCoordinate)
        && board.isCellEmpty(secondCoordinate);
  }

  // TODO
  // Implement Djikstra's shortest path algorithm
  public boolean willWallBlock(PlaceWallTurn turn, Board board) {
    return false;
  }

  public boolean doesPlayerHaveWalls(Player player, Match match) {
    return match.getRemainingWallCount(player) > 0;
  }
}
