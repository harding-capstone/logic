package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum PlaceWallTurnValidator implements TurnValidator {
  INSTANCE;

  @Override
  public boolean isTurnValid(Turn turn, Board board) {
    if (turn instanceof PlaceWallTurn) {
      return isPlaceWallTurnValid((PlaceWallTurn) turn, board);
    } else {
      throw new IllegalArgumentException("Turn is not a PlaceWallTurn " + turn);
    }
  }

  public boolean isPlaceWallTurnValid(PlaceWallTurn turn, Board board) {
    // TODO
    return true;
  }
}
