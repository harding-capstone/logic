package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.BoardState;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.match.MatchState;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum PlaceWallTurnValidator implements TurnValidator {
  INSTANCE;

  @Override
  public boolean isTurnValid(Turn turn, MatchState matchState) {
    if (turn instanceof PlaceWallTurn) {
      return isPlaceWallTurnValid((PlaceWallTurn) turn, matchState);
    } else {
      throw new IllegalArgumentException("Turn is not a PlaceWallTurn " + turn);
    }
  }

  public boolean isPlaceWallTurnValid(PlaceWallTurn turn, MatchState matchState) {
    var player = turn.getCauser();
    var board = matchState.getBoardState();
    return doesPlayerHaveWalls(player, matchState)
        && canPlaceWall(turn, board);
  }

  public boolean canPlaceWall(PlaceWallTurn turn, BoardState boardState) {
    return isLocationOpen(turn, boardState)
        && willWallBlock(turn, boardState.getBoardLayout());
  }

  public boolean isWallCell(PlaceWallTurn turn, BoardLayout boardLayout) {
    var firstCoordinate = turn.getFirstCoordinate();
    var secondCoordinate = turn.getSecondCoordinate();

    return boardLayout.canHoldWall(firstCoordinate)
        && boardLayout.canHoldWall(secondCoordinate);
  }

  public boolean isLocationOpen(PlaceWallTurn turn, BoardState boardState) {
    var firstCoordinate = turn.getFirstCoordinate();
    var secondCoordinate = turn.getSecondCoordinate();

    return boardState.isEmpty(firstCoordinate)
        && boardState.isEmpty(secondCoordinate);
  }

  // TODO
  // Implement Djikstra's shortest path algorithm
  public boolean willWallBlock(PlaceWallTurn turn, BoardLayout boardLayout) {
    return false;
  }

  public boolean doesPlayerHaveWalls(Player player, MatchState matchState) {
    return matchState.getRemainingWallCount(player) > 0;
  }
}
